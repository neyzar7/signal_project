package com.data_management;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * An implementation of the {@link DataReader} interface that transitions the system
 * from batch file processing to continuous, real-time WebSocket streams[cite: 19].
 * 
 * This class wraps a {@link WebSocketClientImpl} and manages its connection lifecycle,
 * routing continuous data directly into the system's storage architecture.
 */
public class WebSocketDataReader implements DataReader {
    private final String serverUrl;
    private WebSocketClientImpl client;

    /**
     * Constructs a new WebSocketDataReader with the designated server endpoint.
     *
     * @param serverUrl the complete WebSocket URL (e.g., "ws://localhost:8080") to connect to
     */
    public WebSocketDataReader(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    /**
     * Connects to the WebSocket server and begins streaming data continuously.
     * 
     * This method utilizes a blocking connection sequence to ensure the network
     * handshake is fully established before allowing the main program execution
     * to continue[cite: 19].
     *
     * @param dataStorage the target storage system for the incoming continuous data
     * @throws IOException if the connection fails, the URI is invalid, or the thread is interrupted
     */
    @Override
    public void readData(DataStorage dataStorage) throws IOException {
        try {
            URI serverUri = new URI(serverUrl);
            client = new WebSocketClientImpl(serverUri, dataStorage);
            
            // connectBlocking makes sure the client connects before continuing program execution
            boolean connected = client.connectBlocking();
            if (!connected) {
                throw new IOException("Could not establish handshake with WebSocket endpoint: " + serverUrl);
            }
        } catch (URISyntaxException e) {
            throw new IOException("Invalid WebSocket URL format configured: " + serverUrl, e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException("Connection sequence was interrupted", e);
        }
    }

    /**
     * Helper method to manually terminate the connection streams safely during 
     * system teardowns or application shutdowns[cite: 19].
     */
    public void stop() {
        if (client != null) {
            client.close();
        }
    }
}