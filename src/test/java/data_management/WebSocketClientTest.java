package data_management;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.data_management.DataStorage;
import com.data_management.PatientRecord;
import com.data_management.WebSocketClientImpl;

import java.net.URI;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WebSocketClientTest {
    private DataStorage storage;
    private WebSocketClientImpl client;

    @BeforeEach
    public void setUp() throws Exception {
        storage = DataStorage.getInstance();
        // Clear runtime mappings between tests by initializing an empty or fresh instance reference if required
        client = new WebSocketClientImpl(new URI("ws://localhost:8080"), storage);
    }

    @Test
    public void testValidDataParsingAndIngestion() {
        // Simulating standard string payload matching WebSocketOutputStrategy formatting
        String validMessage = "99,1716654000000,SystolicBloodPressure,125.5";
        client.onMessage(validMessage);

        List<PatientRecord> records = storage.getRecords(99, 1716654000000L, 1716654000000L);
        assertFalse(records.isEmpty(), "Record should be saved into DataStorage");
        assertEquals(125.5, records.get(0).getMeasurementValue());
        assertEquals("SystolicBloodPressure", records.get(0).getRecordType());
    }

    @Test
    public void testMalformedDataHandling() {
        // Passing a broken payload string with an unparseable blood pressure format token
        String malformedMessage = "99,1716654000000,SystolicBloodPressure,NOT_A_NUMBER";
        
        // Asserting that handling execution continues gracefully without throwing unhandled terminations
        assertDoesNotThrow(() -> client.onMessage(malformedMessage));
        
        List<PatientRecord> records = storage.getRecords(99, 1716654000000L, 1716654000000L);
        assertTrue(records.isEmpty(), "Malformed numeric values must not append objects inside storage logs");
    }

    @Test
    public void testIncompleteDataPayload() {
        // Missing label tokens entirely
        String incompleteMessage = "99,1716654000000,120.0";
        
        assertDoesNotThrow(() -> client.onMessage(incompleteMessage));
        List<PatientRecord> records = storage.getRecords(99, 1716654000000L, 1716654000000L);
        assertTrue(records.isEmpty(), "Incomplete transmissions must be dropped smoothly");
    }
}