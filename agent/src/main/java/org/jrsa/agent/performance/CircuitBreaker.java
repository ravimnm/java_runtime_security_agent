package org.jrsa.agent.performance;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;

import org.jrsa.agent.state.AgentMode;
import org.jrsa.agent.state.AgentStateMachine;

public final class CircuitBreaker {

    private static final long MEMORY_THRESHOLD_BYTES =
            300 * 1024 * 1024; // 300MB

    private CircuitBreaker() {}

    public static void evaluate() {

        MemoryMXBean memoryBean =
                ManagementFactory.getMemoryMXBean();

        long used =
                memoryBean.getHeapMemoryUsage().getUsed();

        if (used > MEMORY_THRESHOLD_BYTES) {

            AgentStateMachine.transitionTo(
                    AgentMode.PASS_THROUGH
            );

            System.out.println(
                    "[JRSA] Circuit breaker activated - PASS_THROUGH mode"
            );
        }
    }
}