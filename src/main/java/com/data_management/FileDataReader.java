package com.data_management;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Reads patient data from a specified directory.
 * Assumption: The files contain comma-separated values in the format:
 * PatientID, Timestamp, RecordType, MeasurementValue
 */
public class FileDataReader implements DataReader {
    private String directoryPath;

    public FileDataReader(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    @Override
    public void readData(DataStorage dataStorage) throws IOException {
        File dir = new File(directoryPath);
        if (!dir.exists() || !dir.isDirectory()) {
            throw new IOException("Invalid directory path provided.");
        }

        File[] files = dir.listFiles();
        if (files == null) return;

        for (File file : files) {
            if (file.isFile()) {
                parseFile(file, dataStorage);
            }
        }
    }

    private void parseFile(File file, DataStorage dataStorage) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    try {
                        int patientId = Integer.parseInt(parts[0].trim());
                        long timestamp = Long.parseLong(parts[1].trim());
                        String recordType = parts[2].trim();
                        double value = Double.parseDouble(parts[3].trim());
                        
                        dataStorage.addPatientData(patientId, value, recordType, timestamp);
                    } catch (NumberFormatException e) {
                        System.err.println("Skipping malformed line: " + line);
                    }
                }
            }
        }
    }
}