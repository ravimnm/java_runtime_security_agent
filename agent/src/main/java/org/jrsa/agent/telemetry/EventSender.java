package org.jrsa.agent.telemetry;

import org.jrsa.agent.evidence.SecurityEvent;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public final class EventSender {

    private static final String BACKEND_URL =
            "http://localhost:8080/api/events";

    private EventSender() {}

    public static void send(SecurityEvent event) {

        try {

            URL url = new URL(BACKEND_URL);

            HttpURLConnection conn =
                    (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setConnectTimeout(1000);
            conn.setReadTimeout(1000);
            conn.setDoOutput(true);

            String json = "{"
                    + "\"threat\":\"" + event.getThreat() + "\","
                    + "\"detail\":\"" + event.getDetail() + "\","
                    + "\"time\":\"" + event.getTime() + "\""
                    + "}";

            try (OutputStream os = conn.getOutputStream()) {
                os.write(json.getBytes());
            }

            conn.getResponseCode();

        } catch (Exception e) {
            // DO NOT crash application
        }
    }
}