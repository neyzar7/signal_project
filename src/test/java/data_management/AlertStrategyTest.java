package data_management;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.alerts.BloodPressureStrategy;
import com.alerts.HeartRateStrategy;
import com.alerts.OxygenSaturationStrategy;
import com.data_management.Patient;

class AlertStrategyTest {

    // Blood Pressure Strategy Tests

    @Test
    void testBloodPressureCriticalHigh() {
        Patient patient = new Patient(1);
        patient.addRecord(185.0, "SystolicBloodPressure", 1000L);
        
        BloodPressureStrategy strategy = new BloodPressureStrategy();
        assertTrue(strategy.checkAlert(patient), "Should trigger alert for systolic > 180");
        
        System.out.println("TEST PASSED: Blood Pressure Critical High successfully tested!");
    }

    @Test
    void testBloodPressureIncreasingTrend() {
        Patient patient = new Patient(2);
        patient.addRecord(110.0, "SystolicBloodPressure", 1000L);
        patient.addRecord(125.0, "SystolicBloodPressure", 2000L);
        patient.addRecord(140.0, "SystolicBloodPressure", 3000L);
        
        BloodPressureStrategy strategy = new BloodPressureStrategy();
        assertTrue(strategy.checkAlert(patient), "Should trigger alert for increasing trend");
        
        System.out.println("TEST PASSED: Blood Pressure Increasing Trend successfully tested!");
    }

    @Test
    void testBloodPressureNormal() {
        Patient patient = new Patient(3);
        patient.addRecord(120.0, "SystolicBloodPressure", 1000L);
        patient.addRecord(125.0, "SystolicBloodPressure", 2000L);
        
        BloodPressureStrategy strategy = new BloodPressureStrategy();
        assertFalse(strategy.checkAlert(patient), "Should NOT trigger alert for normal readings");
        
        System.out.println("TEST PASSED: Blood Pressure Normal Range successfully tested!");
    }

    // Oxygen Saturation Strategy Tests

    @Test
    void testLowSaturation() {
        Patient patient = new Patient(4);
        patient.addRecord(91.0, "Saturation", 1000L);
        
        OxygenSaturationStrategy strategy = new OxygenSaturationStrategy();
        assertTrue(strategy.checkAlert(patient), "Should trigger alert for saturation < 92%");
        
        System.out.println("TEST PASSED: Oxygen Low Saturation successfully tested!");
    }

    @Test
    void testRapidSaturationDrop() {
        Patient patient = new Patient(5);
        patient.addRecord(98.0, "Saturation", 1000000L);
        patient.addRecord(92.0, "Saturation", 1005000L); 
        
        OxygenSaturationStrategy strategy = new OxygenSaturationStrategy();
        assertTrue(strategy.checkAlert(patient), "Should trigger alert for rapid saturation drop");
        
        System.out.println("TEST PASSED: Oxygen Rapid Drop successfully tested!");
    }

    // Heart Rate Strategy Tests

    @Test
    void testHeartRateAbnormalHigh() {
        Patient patient = new Patient(6);
        patient.addRecord(125.0, "HeartRate", 1000L);
        
        HeartRateStrategy strategy = new HeartRateStrategy();
        assertTrue(strategy.checkAlert(patient), "Should trigger alert for high heart rate");
        
        System.out.println("TEST PASSED: Heart Rate Abnormal High successfully tested!");
    }
    
    @Test
    void testHeartRateNormal() {
        Patient patient = new Patient(7);
        patient.addRecord(80.0, "HeartRate", 1000L);
        
        HeartRateStrategy strategy = new HeartRateStrategy();
        assertFalse(strategy.checkAlert(patient), "Should NOT trigger alert for normal heart rate");
        
        System.out.println("TEST PASSED: Heart Rate Normal Range successfully tested!");
    }
}