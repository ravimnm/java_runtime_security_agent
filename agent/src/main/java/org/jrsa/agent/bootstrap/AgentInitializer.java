package org.jrsa.agent.bootstrap;

import java.lang.instrument.Instrumentation;
import java.util.concurrent.atomic.AtomicBoolean;

import org.jrsa.agent.instrumentation.InstrumentationRegistry;
import org.jrsa.agent.state.AgentMode;
import org.jrsa.agent.state.AgentStateMachine;

public final class AgentInitializer {

    private static final AtomicBoolean INITIALIZED = new AtomicBoolean(false);

    private AgentInitializer() {}
    public static void initialize(String args,
                              Instrumentation instrumentation,
                              boolean dynamicAttach) {

    if (!INITIALIZED.compareAndSet(false, true)) {
        return;
    }

    try {

        System.out.println("[JRSA] Starting Runtime Agent");
        System.out.println("[JRSA] Attach Mode: " + (dynamicAttach ? "DYNAMIC" : "STATIC"));
        System.out.println("[JRSA] JVM: " + System.getProperty("java.version"));

        // Initialize safety monitor
        AgentHealthMonitor.start();

        // 🔥 NEW: configure mode from args
        configureMode(args);

        // Register instrumentation
        InstrumentationRegistry.register(instrumentation);

        System.out.println("[JRSA] Initialization successful");

    } catch (Throwable t) {

        System.err.println("[JRSA] Initialization failed. Entering safe mode.");
        t.printStackTrace();
    }

    }
    private static void configureMode(String args) {

    AgentMode mode = AgentMode.DETECT_ONLY; // default

    if (args != null && args.contains("=")) {

        String[] parts = args.split("=");
        String value = parts[1].trim().toUpperCase();

        try {
            mode = AgentMode.valueOf(value);
        } catch (IllegalArgumentException e) {
            System.out.println("[JRSA] Invalid mode: " + value + ", defaulting to DETECT_ONLY");
        }
    }

    AgentStateMachine.transitionTo(mode);
    }
}