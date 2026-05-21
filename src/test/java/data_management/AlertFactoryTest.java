package data_management;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.alerts.Alert;
import com.alerts.AlertFactory;
import com.alerts.BloodOxygenAlert;
import com.alerts.BloodOxygenAlertFactory;
import com.alerts.BloodPressureAlert;
import com.alerts.BloodPressureAlertFactory;
import com.alerts.ECGAlert;
import com.alerts.ECGAlertFactory;



class AlertFactoryTest {

    // Blood Pressure Factory Tests

    @Test
    void testBloodPressureAlertCreation() {
        AlertFactory factory = new BloodPressureAlertFactory();
        Alert alert = factory.createAlert("101", "Critical High BP", 1500L);

        // 1. Verify it is not null
        assertNotNull(alert, "Alert should not be null");
        
        // 2. Verify the Factory returned the subclass we wanted
        assertTrue(alert instanceof BloodPressureAlert, "Factory should create a BloodPressureAlert object");
        
        // 3. Verify the data was stored correctly
        assertEquals("101", alert.getPatientId(), "Patient ID should match");
        assertEquals("Critical High BP", alert.getCondition(), "Condition should match");
        assertEquals(1500L, alert.getTimestamp(), "Timestamp should match");
        
        // 4. Verify the custom message works
        String expectedMessage = "Blood Pressure Alert for Patient 101 | Condition: Critical High BP";
        assertEquals(expectedMessage, alert.getAlertMessage(), "Alert message should match the specific format");
        
        System.out.println("TEST PASSED: BloodPressureAlertFactory successfully tested!");
    }

    // Blood Oxygen Factory Tests

    @Test
    void testBloodOxygenAlertCreation() {
        AlertFactory factory = new BloodOxygenAlertFactory();
        Alert alert = factory.createAlert("102", "Low SpO2 Drop", 2500L);

        assertNotNull(alert);
        assertTrue(alert instanceof BloodOxygenAlert, "Factory should create a BloodOxygenAlert object");
        assertEquals("102", alert.getPatientId());
        assertEquals("Low SpO2 Drop", alert.getCondition());
        
        String expectedMessage = "Blood Oxygen Alert for Patient 102 | Condition: Low SpO2 Drop";
        assertEquals(expectedMessage, alert.getAlertMessage());
        
        System.out.println("TEST PASSED: BloodOxygenAlertFactory successfully tested!");
    }

    // ECG Factory Tests

    @Test
    void testECGAlertCreation() {
        AlertFactory factory = new ECGAlertFactory();
        Alert alert = factory.createAlert("103", "Irregular Heartbeat", 3500L);

        assertNotNull(alert);
        assertTrue(alert instanceof ECGAlert, "Factory should create an ECGAlert object");
        assertEquals("103", alert.getPatientId());
        assertEquals("Irregular Heartbeat", alert.getCondition());
        
        String expectedMessage = "ECG Alert for Patient 103 | Condition: Irregular Heartbeat";
        assertEquals(expectedMessage, alert.getAlertMessage());
        
        System.out.println("TEST PASSED: ECGAlertFactory successfully tested!");
    }
}