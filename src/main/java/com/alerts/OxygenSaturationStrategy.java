package com.alerts;

import java.util.List;

import com.data_management.Patient;
import com.data_management.PatientRecord;

/**
 * A concrete strategy that observes oxygen levels for critical drops 
 * and rapid decreases in saturation over a short time period.
 */

public class OxygenSaturationStrategy implements AlertStrategy {

    @Override
    public boolean checkAlert(Patient patient) {
        List<PatientRecord> records = patient.getRecords(0, Long.MAX_VALUE);

        for (int i = 0; i < records.size(); i++) {
            PatientRecord current = records.get(i);
            
            if (!current.getRecordType().equals("Saturation")) continue;

            // Condition 1: Low Saturation
            if (current.getMeasurementValue() < 92.0) {
                return true;
            }

            // Condition 2: Rapid Drop within 10 minutes
            for (int j = i + 1; j < records.size(); j++) {
                PatientRecord future = records.get(j);
                
                if (!future.getRecordType().equals("Saturation")) continue;

                long timeDifference = future.getTimestamp() - current.getTimestamp();
                double drop = current.getMeasurementValue() - future.getMeasurementValue();

                if (timeDifference <= 600000 && drop >= 5.0) {
                    return true;
                }
            }
        }
        return false;
    }
}