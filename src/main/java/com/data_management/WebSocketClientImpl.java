package com.data_management;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import java.net.URI;

/**
 * A custom WebSocket client that connects to a real-time data stream and continuously
 * processes incoming patient data.
 * 
 * This class extends {@link WebSocketClient} to handle network lifecycle events and
 * parse comma-separated data payloads into patient records, which are then stored
 * securely in the provided {@link DataStorage}[cite: 18].
 */
public class WebSocketClientImpl extends WebSocketClient {
    private final DataStorage dataStorage;

    /**
     * Constructs a new WebSocket client intended for real-time data ingestion.
     *
     * @param serverUri   the URI of the WebSocket server to connect to
     * @param dataStorage the storage system where parsed patient data will be appended
     */
    public WebSocketClientImpl(URI serverUri, DataStorage dataStorage) {
        super(serverUri);
        this.dataStorage = dataStorage;
    }

    /**
     * Triggered when the connection to the WebSocket server is successfully established.
     *
     * @param handshakedata details about the handshake with the server
     */
    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("Successfully connected to the signal generator WebSocket server.");
    }

    /**
     * Triggered when a new message is received from the WebSocket stream.
     * 
     * This method expects a specific comma-separated payload format:
     * {@code patientId,timestamp,label,data}. It parses these tokens, validates them,
     * and streams the extracted numerical values and record types directly into the
     * data storage framework[cite: 18].
     *
     * @param message the raw string payload received from the server
     */
    @Override
    public void onMessage(String message) {
        try {
            // Parsing incoming comma-separated values: patientId,timestamp,label,data
            String[] tokens = message.split(",");
            if (tokens.length != 4) {
                System.err.println("Incomplete transmission or corrupted data message dropped: " + message);
                return;
            }

            int patientId = Integer.parseInt(tokens[0].trim());
            long timestamp = Long.parseLong(tokens[1].trim());
            String recordType = tokens[2].trim();
            double measurementValue = Double.parseDouble(tokens[3].trim());

            // Appending directly to patient storage records in real-time
            dataStorage.addPatientData(patientId, measurementValue, recordType, timestamp);
            
        } catch (NumberFormatException e) {
            System.err.println("Data format error | Failed parsing numerical value from stream: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected parsing runtime error: " + e.getMessage());
        }
    }

    /**
     * Triggered when the WebSocket connection is terminated either by the client or the server.
     *
     * @param code   the exit code indicating the reason for closure
     * @param reason additional information regarding the closure
     * @param remote indicates whether the closure was initiated by the remote host
     */
    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("WebSocket connection closed. Code: " + code + " | Reason: " + reason);
    }

    /**
     * Triggered when a network or pipeline error occurs during the WebSocket session.
     *
     * @param ex the exception thrown during the error event
     */
    @Override
    public void onError(Exception ex) {
        System.err.println("WebSocket pipeline error encountered: " + ex.getMessage());
    }
}