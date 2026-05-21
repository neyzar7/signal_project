package com.alerts;

/**
 * Abstract base decorator for Alert objects.
*/
public abstract class AlertDecorator extends Alert {

    protected Alert decoratedAlert;

    /**
     * Constructs an AlertDecorator wrapping an existing alert.
     *
     * @param decoratedAlert the alert to decorate
     */
    public AlertDecorator(Alert decoratedAlert) {
        super(decoratedAlert.getPatientId(), decoratedAlert.getCondition(), decoratedAlert.getTimestamp());
        this.decoratedAlert = decoratedAlert;
    }

  
    @Override
    public String getAlertMessage() {
        return decoratedAlert.getAlertMessage();
    }
}