package org.jrsa.agent.state;

import java.util.concurrent.atomic.AtomicReference;

public final class AgentStateMachine {

    private static final AtomicReference<AgentMode> CURRENT_MODE =
            new AtomicReference<>(AgentMode.INITIALIZATION);

    private AgentStateMachine() {}

    public static AgentMode getMode() {
        return CURRENT_MODE.get();
    }

    public static void transitionTo(AgentMode newMode) {

        AgentMode previous = CURRENT_MODE.get();

        // Explicit transition rules
        switch (newMode) {

            case DETECT_ONLY:
            case SIMULATE_BLOCK:
            case BLOCK:
            case PASS_THROUGH:
                CURRENT_MODE.set(newMode);
                break;

            default:
                throw new IllegalStateException(
                        "Invalid transition from " + previous + " to " + newMode
                );
        }

        System.out.println("[JRSA] Mode transition: " + previous + " → " + newMode);
    }
}