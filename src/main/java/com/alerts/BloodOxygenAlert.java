package com.alerts;

/**
 * Represents a specific alert generated for blood oxygen (SpO2) anomalies.
 */

public class BloodOxygenAlert extends Alert {
    
    public BloodOxygenAlert(String patientId, String condition, long timestamp) {
        super(patientId, condition, timestamp);
    }

    @Override
    public String getAlertMessage() {
        return "Blood Oxygen Alert for Patient " + patientId + " | Condition: " + condition;
    }
}