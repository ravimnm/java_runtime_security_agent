package org.jrsa.agent.hooks;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import net.bytebuddy.implementation.bind.annotation.Origin;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

import org.jrsa.agent.detectors.DetectionResult;
import org.jrsa.agent.detectors.ReflectionDetector;
import org.jrsa.agent.evidence.EventBuilder;
import org.jrsa.agent.evidence.SecurityEvent;
import org.jrsa.agent.performance.CircuitBreaker;
import org.jrsa.agent.performance.EventRateLimiter;
import org.jrsa.agent.state.AgentMode;
import org.jrsa.agent.state.AgentStateMachine;
import org.jrsa.agent.telemetry.EventSender;

public class ReflectionHook {

    public static Object intercept(
            @Origin Method method,
            @AllArguments Object[] args,
            @SuperCall Callable<?> zuper
    ) throws Exception {

        AgentMode mode = AgentStateMachine.getMode();

        if (mode == AgentMode.PASS_THROUGH) {
            return zuper.call();
        }

        String className = null;
        String methodName = null;

        // Extract actual target method safely
        if (args != null && args.length > 0 && args[0] instanceof Method) {
            Method target = (Method) args[0];
            className = target.getDeclaringClass().getName();
            methodName = target.getName();
        } else {
            // fallback (not ideal but prevents crash)
            className = method.getDeclaringClass().getName();
            methodName = method.getName();
        }

        DetectionResult result =
                ReflectionDetector.analyze(className, methodName);

        if (!result.isSuspicious()) {
            return zuper.call();
        }

        if (!EventRateLimiter.allow()) {
            return zuper.call();
        }

        CircuitBreaker.evaluate();

        SecurityEvent event =
                EventBuilder.reflection(result.getReason());

        System.out.println(event);
        EventSender.send(event);

        switch (mode) {

            case DETECT_ONLY:
                return zuper.call();

            case SIMULATE_BLOCK:
                System.out.println("[JRSA] Simulated block (reflection allowed)");
                return zuper.call();

            case BLOCK:
                throw new SecurityException(
                        "JRSA blocked reflection abuse"
                );

            default:
                return zuper.call();
        }
    }
}