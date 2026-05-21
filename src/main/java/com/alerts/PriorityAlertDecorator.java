package com.alerts;

/**
 * A concrete decorator that adds prioritization tagging to alerts.
 */

public class PriorityAlertDecorator extends AlertDecorator {

    public PriorityAlertDecorator(Alert decoratedAlert) {
        super(decoratedAlert);
    }

    /**
     * Enhances the original alert message with an URGENT priority tag.
     *
     * @return the prioritized alert message
     */
    @Override
    public String getAlertMessage() {
        return "[URGENT PRIORITY] " + decoratedAlert.getAlertMessage();
    }
}