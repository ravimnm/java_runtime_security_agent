package org.jrsa.backend.controller;

import org.springframework.web.bind.annotation.*;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private static final String LOG_FILE = "jrsa-events.log";

    @PostMapping
    public void receiveEvent(@RequestBody String event) {

        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        String logEntry = String.format(
                "{\"timestamp\":\"%s\", \"event\": %s}%n",
                timestamp,
                event
        );

        // Print to console
        System.out.println("\n🚨 JRSA EVENT RECEIVED:");
        System.out.println(logEntry);

        // Write to file
        writeToFile(logEntry);
    }

    private void writeToFile(String logEntry) {
        try (FileWriter writer = new FileWriter(LOG_FILE, true)) {
            writer.write(logEntry);
        } catch (IOException e) {
            System.err.println("[JRSA] Failed to write event to file");
            e.printStackTrace();
        }
    }
}