package org.jrsa.agent.bootstrap;
import java.lang.instrument.Instrumentation;

public final class JrsaAgent {

    private JrsaAgent() {}

    /**
     * Static attach (-javaagent)
     */
    public static void premain(String agentArgs, Instrumentation inst) {
        AgentInitializer.initialize(agentArgs, inst, false);
    }

    /**
     * Dynamic attach (VirtualMachine.attach)
     */
    public static void agentmain(String agentArgs, Instrumentation inst) {
        AgentInitializer.initialize(agentArgs, inst, true);
    }
}