package com.alerts;

/**
 * Abstract base factory for creating Alert objects.
 */

public abstract class AlertFactory {
    /**
     * Creates a new Alert instance.
     *
     * @param patientId the ID of the patient
     * @param condition the condition that triggered the alert
     * @param timestamp the time at which the alert was triggered
     * @return a new Alert object
     */
    public abstract Alert createAlert
    (String patientId, 
    String condition, 
    long timestamp);
    
}
