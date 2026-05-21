package com.alerts;

/**
 * A concrete factory responsible for creating ECGAlert instances.
 */

public class ECGAlertFactory extends AlertFactory{

    @Override
    public Alert createAlert(String patientId, String condition, long timestamp) {
        return new ECGAlert(patientId, condition, timestamp);
    }
    
}
