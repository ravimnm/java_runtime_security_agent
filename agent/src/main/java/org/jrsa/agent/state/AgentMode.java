package org.jrsa.agent.state;

public enum AgentMode {

    INITIALIZATION,

    DETECT_ONLY,        // Default safe mode

    SIMULATE_BLOCK,     // Log as blocked, allow execution

    BLOCK,              // Enforce via exception

    PASS_THROUGH        // Self-preservation mode
}