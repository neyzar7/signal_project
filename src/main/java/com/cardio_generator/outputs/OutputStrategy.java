package com.cardio_generator.outputs;

/**
 * this interface defines the output strategy for the generated patient data.
 * Implemented by TcpOutputStrategy, FileOutputStrategy, ConsoleOutputStrategy, WebSocketOutputStrategy
 * @see FileOutputStrategy
 * @see TcpOutputStrategy
 * @see ConsoleOutputStrategy
 * @see WebSocketOutputStrategy
 */

public interface OutputStrategy {
    void output(int patientId, long timestamp, String label, String data);
}
