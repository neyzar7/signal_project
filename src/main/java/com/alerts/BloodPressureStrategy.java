package com.alerts;

import java.util.List;

import com.data_management.Patient;
import com.data_management.PatientRecord;

/**
 * A concrete strategy that checks for critical thresholds and 
 * increasing trends in a patient's blood pressure readings.
 */

public class BloodPressureStrategy implements AlertStrategy {

    @Override
    public boolean checkAlert(Patient patient) {
        // Fetch all records for the patient
        List<PatientRecord> records = patient.getRecords(0, Long.MAX_VALUE);
        
        double previous1 = -1; 
        double previous2 = -1;

        for (PatientRecord record : records) {
            if (record.getRecordType().equals("SystolicBloodPressure")) {
                double val = record.getMeasurementValue();

                // Condition 1: Critical Threshold
                if (val > 180 || val < 90) {
                    return true;
                }

                // Condition 2: Increasing Trend (3 consecutive readings increasing by > 10)
                if (previous2 != -1 && previous1 != -1) {
                    if ((val - previous1 > 10) && (previous1 - previous2 > 10)) {
                        return true;
                    }
                }
                
                previous2 = previous1;
                previous1 = val;
            }
        }
        return false;
    }
}