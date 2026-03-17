package org.jrsa.agent.detectors;

public final class DetectionResult {

    private final boolean suspicious;
    private final String reason;

    public DetectionResult(boolean suspicious, String reason) {
        this.suspicious = suspicious;
        this.reason = reason;
    }

    public boolean isSuspicious() {
        return suspicious;
    }

    public String getReason() {
        return reason;
    }
}