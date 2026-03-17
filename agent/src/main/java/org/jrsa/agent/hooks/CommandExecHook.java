package org.jrsa.agent.hooks;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

import org.jrsa.agent.detectors.CommandExecutionDetector;
import org.jrsa.agent.detectors.DetectionResult;
import org.jrsa.agent.evidence.EventBuilder;
import org.jrsa.agent.evidence.SecurityEvent;
import org.jrsa.agent.performance.CircuitBreaker;
import org.jrsa.agent.performance.EventRateLimiter;
import org.jrsa.agent.state.AgentMode;
import org.jrsa.agent.state.AgentStateMachine;
import org.jrsa.agent.telemetry.EventSender;

public class CommandExecHook {

    public static Object intercept(
            @AllArguments Object[] args,
            @SuperCall Callable<?> zuper
    ) throws Exception {

        AgentMode mode = AgentStateMachine.getMode();

        // Fast path
        if (mode == AgentMode.PASS_THROUGH) {
            return zuper.call();
        }

        List<String> command = normalizeCommand(args);

        if (command == null || command.isEmpty()) {
            return zuper.call();
        }

        DetectionResult result =
                CommandExecutionDetector.analyze(command);

        // Early exit if clean
        if (!result.isSuspicious()) {
            return zuper.call();
        }

        // Rate limiting
        if (!EventRateLimiter.allow()) {
            return zuper.call();
        }

        // Circuit breaker
        CircuitBreaker.evaluate();

        SecurityEvent event =
                EventBuilder.commandExecution(result.getReason());

        // Always log locally
        System.out.println(event);

        // Async send (safe if implemented)
        EventSender.send(event);

        switch (mode) {

            case DETECT_ONLY:
                return zuper.call();

            case SIMULATE_BLOCK:
                System.out.println("[JRSA] Simulated block (execution allowed)");
                return zuper.call();

            case BLOCK:
                throw new SecurityException(
                        "JRSA blocked command execution"
                );

            default:
                return zuper.call();
        }
    }

    /**
     * Safe normalization for multiple input types
     */
    private static List<String> normalizeCommand(Object[] args) {

        if (args == null || args.length == 0 || args[0] == null) {
            return null;
        }

        Object arg = args[0];

        try {
            if (arg instanceof List) {
                return (List<String>) arg;
            }

            if (arg instanceof String[]) {
                return Arrays.asList((String[]) arg);
            }

            if (arg instanceof String) {
                // Basic split (still not shell-perfect but safe fallback)
                return Arrays.asList(((String) arg).split(" "));
            }

        } catch (Exception ignored) {
        }

        return null;
    }
}