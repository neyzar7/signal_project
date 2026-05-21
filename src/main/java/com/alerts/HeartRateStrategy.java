package com.alerts;

import java.util.List;

import com.data_management.Patient;
import com.data_management.PatientRecord;

/**
 * A concrete strategy that monitors patient records for abnormal 
 * heart rates and ECG anomalies.
 */

public class HeartRateStrategy implements AlertStrategy {

    @Override
    public boolean checkAlert(Patient patient) {
        List<PatientRecord> records = patient.getRecords(0, Long.MAX_VALUE);

        for (PatientRecord record : records) {
            // Evaluates both standard HeartRate and ECG anomalies
            if (record.getRecordType().equals("HeartRate") || record.getRecordType().equals("ECG")) {
                double val = record.getMeasurementValue();

                // Simple critical threshold check for heart rate
                if (val > 120 || val < 50) {
                    return true;
                }
            }
        }
        return false;
    }
}