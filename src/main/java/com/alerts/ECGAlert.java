package com.alerts;

/**
 * Represents a specific alert generated for abnormal heart rates and rhythms.
 */

public class ECGAlert extends Alert {
    
    public ECGAlert(String patientId, String condition, long timestamp) {
        super(patientId, condition, timestamp);
    }

    @Override
    public String getAlertMessage() {
        return "ECG Alert for Patient " + patientId + " | Condition: " + condition;
    }
}