package com.alerts;
import com.data_management.Patient;

/**
 * Defines the interface for health monitoring strategies.
 */

public interface AlertStrategy {
    /**
     * Evaluates patient data to determine if an alert should be triggered.
     *
     * @param patient the patient whose records need evaluating
     * @return true if an alert condition is met, false otherwise
     */
    boolean checkAlert(Patient patient);
}