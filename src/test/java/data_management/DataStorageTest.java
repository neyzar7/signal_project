package data_management;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.data_management.DataStorage;
import com.data_management.PatientRecord;
import java.util.List;

class DataStorageTest {

    @Test
    void testAddAndGetRecords() {
        // Initialize storage directly
        DataStorage storage = new DataStorage();
        
        // Manually inject test data
        storage.addPatientData(1, 100.0, "WhiteBloodCells", 1714376789050L);
        storage.addPatientData(1, 200.0, "WhiteBloodCells", 1714376789051L);
        storage.addPatientData(1, 300.0, "WhiteBloodCells", 1714376790000L); // Outside time range

        // Test filtering
        List<PatientRecord> records = storage.getRecords(1, 1714376789050L, 1714376789051L);
        
        assertEquals(2, records.size(), "Should only retrieve records within the time window");
        assertEquals(100.0, records.get(0).getMeasurementValue()); 
        assertEquals(200.0, records.get(1).getMeasurementValue()); 
    }
}