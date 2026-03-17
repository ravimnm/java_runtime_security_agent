package org.jrsa.agent.hooks;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

import java.nio.file.Path;
import java.util.concurrent.Callable;

import org.jrsa.agent.detectors.DetectionResult;
import org.jrsa.agent.detectors.FileSystemDetector;
import org.jrsa.agent.evidence.EventBuilder;
import org.jrsa.agent.evidence.SecurityEvent;
import org.jrsa.agent.state.AgentMode;
import org.jrsa.agent.state.AgentStateMachine;
import org.jrsa.agent.telemetry.EventSender;

public class FileWriteHook {

    public static Object intercept(
            @AllArguments Object[] args,
            @SuperCall Callable<?> zuper
    ) throws Exception {

        Path path = (Path) args[0];

        DetectionResult result =
                FileSystemDetector.analyze(path.toString());

        if (result.isSuspicious()) {

            SecurityEvent event =
                    EventBuilder.fileWrite(result.getReason());

            System.out.println(event);
            EventSender.send(event);

            if (AgentStateMachine.getMode() == AgentMode.BLOCK) {
                throw new SecurityException("JRSA blocked file write");
            }
        }

        return zuper.call();
    }
}