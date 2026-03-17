package org.jrsa.agent.evidence;

import java.time.Instant;
import java.util.List;

public class SecurityEvent {

    private String threat;
    private String detail;
    private Instant time;
    private String thread;
    private List<String> stack;

    public SecurityEvent(String threat, String detail, List<String> stack) {
        this.threat = threat;
        this.detail = detail;
        this.stack = stack;
        this.time = Instant.now();
        this.thread = Thread.currentThread().getName();
    }
    
    public String getThreat() {
        return threat;
    }

    public String getDetail() {
        return detail;
    }

    public Instant getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "\n[JRSA EVENT]\n"
                + " Threat=" + threat + "\n"
                + " Detail=" + detail + "\n"
                + " Time=" + time + "\n"
                + " Thread=" + thread + "\n"
                + " Stack=" + stack + "\n";
    }
}
