package org.jrsa.agent.detectors;

import java.util.Arrays;
import java.util.List;

public final class DeserializationDetector {

    private static final List<String> HIGH_RISK_CLASSES =
            Arrays.asList(
                    "java.lang.Runtime",
                    "java.lang.ProcessBuilder"
            );

    private DeserializationDetector() {}

    public static DetectionResult analyze(String className) {

        if (className == null) {
            return new DetectionResult(false, "Null class");
        }

        for (String risky : HIGH_RISK_CLASSES) {
            if (className.equals(risky)) {
                return new DetectionResult(
                        true,
                        "Deserialization of high-risk class: " + className
                );
            }
        }

        return new DetectionResult(false,
                "Deserialized: " + className);
    }
}