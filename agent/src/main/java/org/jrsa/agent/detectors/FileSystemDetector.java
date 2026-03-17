package org.jrsa.agent.detectors;

public final class FileSystemDetector {

    private FileSystemDetector() {}

    public static DetectionResult analyze(String path) {

        if (path.contains("windows")
                || path.contains("system32")) {

            return new DetectionResult(
                    true,
                    "Write to sensitive path: " + path
            );
        }

        return new DetectionResult(false,
                "Write allowed: " + path);
    }
}
//
//package com.restapiproject.intern.detectors;
//
//import com.restapiproject.intern.detectors.DetectionResult;
//
//public final class FileWriteDetector {
//
//    private FileWriteDetector() {}
//
//    public static DetectionResult analyze(String path) {
//
//        if (path.contains("/etc")
//                || path.contains("webapps")
//                || path.contains(".jsp")
//                || path.contains(".php")) {
//
//            return new DetectionResult(
//                    true,
//                    "Sensitive file write: " + path
//            );
//        }
//
//        return new DetectionResult(false, "Normal file write");
//    }
//}