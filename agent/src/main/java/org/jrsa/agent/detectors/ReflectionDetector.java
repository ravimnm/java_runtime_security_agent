package org.jrsa.agent.detectors;

public final class ReflectionDetector {

    private ReflectionDetector() {}

    public static DetectionResult analyze(
            String className,
            String methodName
    ) {

        if ("java.lang.Runtime".equals(className)
                && "exec".equals(methodName)) {

            return new DetectionResult(
                    true,
                    "Reflective access to Runtime.exec"
            );
        }

        return new DetectionResult(false,
                "Reflective call: " + className + "." + methodName);
    }
}