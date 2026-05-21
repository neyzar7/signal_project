package com.alerts;

/**
 * A concrete factory responsible for creating BloodOxygenAlert instances.
 */

public class BloodOxygenAlertFactory extends AlertFactory{

    @Override
    public Alert createAlert(String patientId, String condition, long timestamp) {
        return new BloodOxygenAlert(patientId, condition, timestamp);
    }
    
}