package com.cardio_generator.generators;

import com.cardio_generator.outputs.OutputStrategy;

/**
 * this interface defines the methods that must be implemented by all patient data generators.
 * Implemented by BloodPressureDataGenerator, BloodSaturationDataGenerator, HeartRateDataGenerator, ECGDataGenerator
 * @see BloodPressureDataGenerator
 * @see BloodSaturationDataGenerator
 * @see HeartRateDataGenerator
 * @see ECGDataGenerator
 */

public interface PatientDataGenerator {
    void generate(int patientId, OutputStrategy outputStrategy);
}
