package com.alerts;

/**
 * Represents a specific alert generated manually by medical staff or external input.
 */

public class ManualAlert extends Alert {
    
    public ManualAlert(String patientId, String condition, long timestamp) {
        super(patientId, condition, timestamp);
    }

    @Override
    public String getAlertMessage() {
        return "Manual Alert for Patient " + patientId + " | Condition: " + condition;
    }
}