package com.alerts;

/**
 * A concrete factory responsible for creating BloodPressureAlert instances.
 */

public class BloodPressureAlertFactory extends AlertFactory {
    
    @Override
    public Alert createAlert(String patientId, String condition, long timestamp) {
        return new BloodPressureAlert(patientId, condition, timestamp);
    }
}
