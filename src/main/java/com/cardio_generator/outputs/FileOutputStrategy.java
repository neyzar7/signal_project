package com.cardio_generator.outputs;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ConcurrentHashMap;

/** 
 * This class is responsible for writing the data to a file for different patients and alerts.
 * It implements the OutputStrategy interface.
 * */


public class FileOutputStrategy implements OutputStrategy {

    private String baseDirectory;

    public final ConcurrentHashMap<String, String> file_map = new ConcurrentHashMap<>();

    /**
     * This constructor initializes the base directory.
     * @param baseDirectory The base directory where the files will be written
     */

    public FileOutputStrategy(String baseDirectory) {

        this.baseDirectory = baseDirectory; // corrected casing of BaseDirectory
    }

        /**
     * This method writes the data to a file.
     * @param patientId The ID of the patient
     * @param timeStamp The time the data was generated
     * @param label The label of the data
     * @param data The data to be written
     * @throws IOException If an error occurs while writing to the file
     */

    @Override
    public void output(int patientId, long timeStamp, String label, String data) {{ // corrected casing of timestamp
        try {
            // Create the directory
            Files.createDirectories(Paths.get(baseDirectory));
        } catch (IOException e) {
            System.err.println("Error creating base directory: " + e.getMessage());
            return;
        }
        // Set the FilePath variable
        String filePath = file_map.computeIfAbsent(label, k -> Paths.get(baseDirectory, label + ".txt").toString()); // corrected casing of FilePath

        // Write the data to the file
        try (PrintWriter out = new PrintWriter(
                Files.newBufferedWriter(Paths.get(filePath), StandardOpenOption.CREATE, StandardOpenOption.APPEND))) {
            out.printf("Patient ID: %d, Timestamp: %d, Label: %s, Data: %s%n", patientId, timeStamp, label, data);
        } catch (Exception e) {
            System.err.println("Error writing to file " + filePath + ": " + e.getMessage());
        }
    }
}
}