package org.jrsa.agent.hooks;

import net.bytebuddy.implementation.bind.annotation.SuperCall;
import net.bytebuddy.implementation.bind.annotation.Origin;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

import org.jrsa.agent.detectors.DeserializationDetector;
import org.jrsa.agent.detectors.DetectionResult;
import org.jrsa.agent.evidence.EventBuilder;
import org.jrsa.agent.evidence.SecurityEvent;
import org.jrsa.agent.state.AgentMode;
import org.jrsa.agent.state.AgentStateMachine;
import org.jrsa.agent.telemetry.EventSender;

public class DeserializationHook {

    public static Object intercept(
            @Origin Method method,
            @SuperCall Callable<?> zuper
    ) throws Exception {

        // Execute original method
        Object obj = zuper.call();

        if (obj == null) {
            return null;
        }

        // Run detection
        DetectionResult result =
                DeserializationDetector.analyze(
                        obj.getClass().getName()
                );

        if (!result.isSuspicious()) {
            return obj;
        }

        // Build correct event
        SecurityEvent event =
                EventBuilder.deserialization(
                        result.getReason()
                );

        System.out.println(event);

        // Send to backend
        EventSender.send(event);

        // Apply response policy
        AgentMode mode = AgentStateMachine.getMode();

        if (mode == AgentMode.BLOCK) {
            throw new SecurityException(
                    "JRSA blocked deserialization"
            );
        }

        if (mode == AgentMode.SIMULATE_BLOCK) {
            System.out.println("[JRSA] Simulated block (deserialization allowed)");
        }

        return obj;
    }
}