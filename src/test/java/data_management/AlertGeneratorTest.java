package data_management;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import com.alerts.AlertGenerator;
import com.data_management.DataStorage;
import com.data_management.Patient;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class AlertGeneratorTest {
    private DataStorage storage;
    private AlertGenerator generator;
    
    // We use these to capture the System.out.println output to verify alerts
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        storage = new DataStorage();
        generator = new AlertGenerator(storage);
        System.setOut(new PrintStream(outContent)); // Redirect console output for testing
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut); // Restore normal console output after each test
    }

    @Test
    void testCriticalSystolicBloodPressureAlert() {
        // Condition: Systolic > 180 or < 90
        storage.addPatientData(1, 185.0, "SystolicBloodPressure", 1000L);
        Patient patient = storage.getAllPatients().get(0);
        
        generator.evaluateData(patient);
        
        assertTrue(outContent.toString().contains("Critical Systolic BP"), "Should trigger high systolic alert");
    }

    @Test
    void testLowSaturationAlert() {
        // Condition: Saturation < 92%
        storage.addPatientData(2, 90.0, "Saturation", 2000L);
        Patient patient = storage.getAllPatients().get(0);
        
        generator.evaluateData(patient);
        
        assertTrue(outContent.toString().contains("Low Oxygen Saturation"), "Should trigger low saturation alert");
    }

    @Test
    void testRapidSaturationDropAlert() {
        // Condition: Drop of >= 5% within 10 minutes (600,000 ms)
        storage.addPatientData(3, 98.0, "Saturation", 1000000L);
        storage.addPatientData(3, 92.0, "Saturation", 1005000L); // 5 seconds later, 6% drop
        Patient patient = storage.getAllPatients().get(0);
        
        generator.evaluateData(patient);
        
        assertTrue(outContent.toString().contains("Rapid Saturation Drop"), "Should trigger rapid drop alert");
    }

    @Test
    void testHypotensiveHypoxemiaAlert() {
        // Condition: Systolic < 90 AND Saturation < 92 near each other
        storage.addPatientData(4, 85.0, "SystolicBloodPressure", 3000000L);
        storage.addPatientData(4, 89.0, "Saturation", 3005000L); // 5 seconds later
        Patient patient = storage.getAllPatients().get(0);
        
        generator.evaluateData(patient);
        
        assertTrue(outContent.toString().contains("Hypotensive Hypoxemia"), "Should trigger combined condition alert");
    }

    @Test
    void testBloodPressureIncreasingTrendAlert() {
        // Condition: 3 consecutive readings increasing by > 10 each
        storage.addPatientData(5, 110.0, "SystolicBloodPressure", 1000L);
        storage.addPatientData(5, 125.0, "SystolicBloodPressure", 2000L); // +15
        storage.addPatientData(5, 140.0, "SystolicBloodPressure", 3000L); // +15
        Patient patient = storage.getAllPatients().get(0);
        
        generator.evaluateData(patient);
        
        assertTrue(outContent.toString().contains("Increasing Trend"), "Should trigger increasing trend alert");
    }

    @Test
    void testECGAbnormalPeakAlert() {
        // Condition: Peak is 20% higher than average of previous 5 readings
        storage.addPatientData(6, 1.0, "ECG", 1000L);
        storage.addPatientData(6, 1.0, "ECG", 2000L);
        storage.addPatientData(6, 1.0, "ECG", 3000L);
        storage.addPatientData(6, 1.0, "ECG", 4000L);
        storage.addPatientData(6, 1.0, "ECG", 5000L);
        // Average is 1.0. A peak > 1.2 should trigger it.
        storage.addPatientData(6, 1.5, "ECG", 6000L); 
        
        Patient patient = storage.getAllPatients().get(0);
        generator.evaluateData(patient);
        
        assertTrue(outContent.toString().contains("Abnormal ECG Peak"), "Should trigger abnormal ECG alert");
    }
}