package com.alerts;

import java.util.List;
import java.util.stream.Collectors;

import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;

/**
 * The {@code AlertGenerator} class is responsible for monitoring patient data
 * and generating alerts when certain predefined conditions are met. This class
 * relies on a {@link DataStorage} instance to access patient data and evaluate
 * it against specific health criteria.
 */
public class AlertGenerator {
    private DataStorage dataStorage;

    /**
     * Constructs an {@code AlertGenerator} with a specified {@code DataStorage}.
     *
     * @param dataStorage the data storage system that provides access to patient data
     */
    public AlertGenerator(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
    }

    /**
     * Evaluates the specified patient's data to determine if any alert conditions
     * are met. If a condition is met, an alert is triggered via the {@link #triggerAlert} method.
     *
     * @param patient the patient data to evaluate for alert conditions
     */
    public void evaluateData(Patient patient) {
        // Retrieve all records to evaluate historical trends and thresholds
        List<PatientRecord> records = patient.getRecords(0, Long.MAX_VALUE);
        
        if (records == null || records.isEmpty()) {
            return;
        }

        String patientIdStr = String.valueOf(patient.getPatientId());

        // Evaluate single-record thresholds and time-based proximity conditions
        for (int i = 0; i < records.size(); i++) {
            PatientRecord currentRecord = records.get(i);
            
            checkCriticalThresholds(patientIdStr, currentRecord);
            checkLowSaturation(patientIdStr, currentRecord);
            checkManualAlert(patientIdStr, currentRecord);
            
            // Methods that require historical context (looking backwards from current record)
            checkHypotensiveHypoxemia(patientIdStr, records, i);
            checkRapidSaturationDrop(patientIdStr, records, i);
            checkECGAbnormalities(patientIdStr, records, i);
        }

        // Evaluate continuous trends across the entire dataset
        checkBloodPressureTrends(patientIdStr, records, "SystolicBloodPressure");
        checkBloodPressureTrends(patientIdStr, records, "DiastolicBloodPressure");
    }

    /**
     * 1. Blood Pressure Critical Threshold Alerts
     */
    private void checkCriticalThresholds(String patientId, PatientRecord record) {
        if (record.getRecordType().equals("SystolicBloodPressure")) {
            if (record.getMeasurementValue() > 180 || record.getMeasurementValue() < 90) {
                triggerAlert(new ManualAlert(patientId, "Critical Systolic BP", record.getTimestamp()));
            }
        } else if (record.getRecordType().equals("DiastolicBloodPressure")) {
            if (record.getMeasurementValue() > 120 || record.getMeasurementValue() < 60) {
                triggerAlert(new ManualAlert(patientId, "Critical Diastolic BP", record.getTimestamp()));
            }
        }
    }

    /**
     * 1. Blood Pressure Trend Alerts
     * Checks for 3 consecutive readings where each reading changes by more than 10 mmHg in the same direction.
     */
    private void checkBloodPressureTrends(String patientId, List<PatientRecord> records, String bpType) {
        List<PatientRecord> bpRecords = records.stream()
                .filter(r -> r.getRecordType().equals(bpType))
                .collect(Collectors.toList());

        for (int i = 2; i < bpRecords.size(); i++) {
            double r1 = bpRecords.get(i - 2).getMeasurementValue();
            double r2 = bpRecords.get(i - 1).getMeasurementValue();
            double r3 = bpRecords.get(i).getMeasurementValue();

            // Check increasing trend
            if ((r2 - r1 > 10) && (r3 - r2 > 10)) {
                triggerAlert(new ManualAlert(patientId, bpType + " Increasing Trend", bpRecords.get(i).getTimestamp()));
            }
            // Check decreasing trend
            if ((r1 - r2 > 10) && (r2 - r3 > 10)) {
                triggerAlert(new ManualAlert(patientId, bpType + " Decreasing Trend", bpRecords.get(i).getTimestamp()));
            }
        }
    }

    /**
     * 2. Blood Saturation Low Alert
     */
    private void checkLowSaturation(String patientId, PatientRecord record) {
        if (record.getRecordType().equals("Saturation") && record.getMeasurementValue() < 92) {
            triggerAlert(new ManualAlert(patientId, "Low Oxygen Saturation", record.getTimestamp()));
        }
    }

    /**
     * 2. Blood Saturation Rapid Drop Alert
     * Triggers if saturation drops by 5% or more within a 10-minute interval (600,000 ms).
     */
    private void checkRapidSaturationDrop(String patientId, List<PatientRecord> records, int currentIndex) {
        PatientRecord current = records.get(currentIndex);
        if (!current.getRecordType().equals("Saturation")) return;

        long tenMinutesAgo = current.getTimestamp() - 600000;

        for (int j = currentIndex - 1; j >= 0; j--) {
            PatientRecord previous = records.get(j);
            if (!previous.getRecordType().equals("Saturation")) continue;
            
            // Stop looking if we go past the 10-minute window
            if (previous.getTimestamp() < tenMinutesAgo) break;

            if (previous.getMeasurementValue() - current.getMeasurementValue() >= 5) {
                triggerAlert(new ManualAlert(patientId, "Rapid Saturation Drop", current.getTimestamp()));
                break; // Alert once for the drop in this window
            }
        }
    }

    /**
     * 3. Combined Alert: Hypotensive Hypoxemia Alert
     * Triggers when Systolic BP < 90 AND Saturation < 92%.
     * Assumption: "Combined" means these readings occur in close proximity (e.g., within 1 minute of each other).
     */
    private void checkHypotensiveHypoxemia(String patientId, List<PatientRecord> records, int currentIndex) {
        PatientRecord current = records.get(currentIndex);
        
        boolean isLowSys = current.getRecordType().equals("SystolicBloodPressure") && current.getMeasurementValue() < 90;
        boolean isLowSat = current.getRecordType().equals("Saturation") && current.getMeasurementValue() < 92;

        if (!isLowSys && !isLowSat) return;

        long oneMinuteWindow = 60000; 

        // Look nearby for the correlating condition
        for (PatientRecord nearbyRecord : records) {
            if (Math.abs(nearbyRecord.getTimestamp() - current.getTimestamp()) <= oneMinuteWindow) {
                if (isLowSys && nearbyRecord.getRecordType().equals("Saturation") && nearbyRecord.getMeasurementValue() < 92) {
                    triggerAlert(new ManualAlert(patientId, "Hypotensive Hypoxemia", current.getTimestamp()));
                    return;
                }
                if (isLowSat && nearbyRecord.getRecordType().equals("SystolicBloodPressure") && nearbyRecord.getMeasurementValue() < 90) {
                    triggerAlert(new ManualAlert(patientId, "Hypotensive Hypoxemia", current.getTimestamp()));
                    return;
                }
            }
        }
    }

    /**
     * 4. ECG Data Alerts (Sliding Window Implementation)
     * Triggers if a peak occurs far beyond the current average.
     * Assumption: Using a window of the last 5 readings. If current is 20% higher than average, trigger.
     */
    private void checkECGAbnormalities(String patientId, List<PatientRecord> records, int currentIndex) {
        PatientRecord current = records.get(currentIndex);
        if (!current.getRecordType().equals("ECG")) return;

        int windowSize = 5;
        double sum = 0;
        int count = 0;

        for (int j = currentIndex - 1; j >= 0 && count < windowSize; j--) {
            if (records.get(j).getRecordType().equals("ECG")) {
                sum += records.get(j).getMeasurementValue();
                count++;
            }
        }

        if (count == windowSize) {
            double average = sum / windowSize;
            // Define "abnormal peak" as 20% higher than the recent average
            if (current.getMeasurementValue() > average * 1.20) {
                triggerAlert(new ManualAlert(patientId, "Abnormal ECG Peak", current.getTimestamp()));
            }
        }
    }

    /**
     * 5. A Triggered Alert
     * Triggers when the generator manually emits an "Alert" record type.
     */
    private void checkManualAlert(String patientId, PatientRecord record) {
        if (record.getRecordType().equals("Alert")) {
            triggerAlert(new ManualAlert(patientId, "Manual Alert Triggered", record.getTimestamp()));
        }
    }

    /**
     * Triggers an alert for the monitoring system.
     *
     * @param alert the alert object containing details about the alert condition
     */
    private void triggerAlert(Alert alert) {
        // Outputting to console so you can verify it in your IDE terminal
        System.out.println("ALERT TRIGGERED: Patient " + alert.getPatientId() + 
                           " | Condition: " + alert.getCondition() + 
                           " | Time: " + alert.getTimestamp());
    }
}