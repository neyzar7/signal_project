package com.alerts;

/**
 * Represents the base abstract class for all medical alerts in the system.
 */

public abstract class Alert {
    protected String patientId;
    protected String condition;
    protected long timestamp;

    /**
     * Constructs a new base Alert.
     *
     * @param patientId the unique identifier of the patient
     * @param condition the medical condition or anomaly that triggered the alert
     * @param timestamp the time the alert was triggered, in milliseconds since epoch
     */

    public Alert(String patientId, String condition, long timestamp) {
        this.patientId = patientId;
        this.condition = condition;
        this.timestamp = timestamp;
    }


    public String getPatientId() {
        return patientId;
    }


    public String getCondition() {
        return condition;
    }

    public long getTimestamp() {
        return timestamp;
    }
    

    public void message() {
        System.out.println(condition);
    }

   
    public abstract String getAlertMessage();
}