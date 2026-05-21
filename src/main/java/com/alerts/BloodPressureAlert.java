package com.alerts;

/**
 * Represents a specific alert generated for blood pressure anomalies.
 */

public class BloodPressureAlert extends Alert {
    
    public BloodPressureAlert(String patientId, String condition, long timestamp) {
        super(patientId, condition, timestamp);
    }

    @Override
    public String getAlertMessage() {
        return "Blood Pressure Alert for Patient " + patientId + " | Condition: " + condition;
    }
}