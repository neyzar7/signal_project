package com.alerts;

/**
 * A concrete decorator that modifies an alert to indicate it is a 
 * repeated alert.
 */

public class RepeatedAlertDecorator extends AlertDecorator {

    public RepeatedAlertDecorator(Alert decoratedAlert) {
        super(decoratedAlert);
    }
    /**
     * Enhances the original alert message with a repetition tag.
     *
     * @return the repeated alert message
     */
    @Override
    public String getAlertMessage() {
        return decoratedAlert.getAlertMessage() + " (Repeated Alert: Condition Re-evaluated)";
    }
}