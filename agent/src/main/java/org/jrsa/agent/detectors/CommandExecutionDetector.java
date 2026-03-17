package org.jrsa.agent.detectors;

import java.util.Arrays;
import java.util.List;

public final class CommandExecutionDetector {

    private static final List<String> HIGH_RISK_KEYWORDS =
            Arrays.asList("cmd", "powershell", "bash", "sh");

    private CommandExecutionDetector() {}

    public static DetectionResult analyze(List<String> command) {

        if (command == null || command.isEmpty()) {
            return new DetectionResult(false, "Empty command");
        }

        String first = command.get(0).toLowerCase();

        for (String keyword : HIGH_RISK_KEYWORDS) {
            if (first.contains(keyword)) {
                return new DetectionResult(
                        true,
                        "High-risk command execution: " + command
                );
            }
        }

        return new DetectionResult(false, "Command allowed: " + command);
    }
}