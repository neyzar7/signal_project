package data_management; // Adjust if your test package is strictly com.data_management

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.Test;

import com.data_management.DataStorage;
import com.data_management.HealthDataSimulator;

class SingletonTest {

    // DataStorage Singleton Tests

    @Test
    void testDataStorageSingleton() {
        // Try to get two separate instances
        DataStorage instance1 = DataStorage.getInstance();
        DataStorage instance2 = DataStorage.getInstance();

        // Verify they are not null
        assertNotNull(instance1, "Instance 1 should not be null");
        
        // Verify they are the same object in memory
        assertSame(instance1, instance2, "Both DataStorage variables should point to the exact same instance");
        
        System.out.println("TEST PASSED: DataStorage Singleton successfully tested!");
    }

    // HealthDataSimulator Singleton Tests

    @Test
    void testHealthDataSimulatorSingleton() {
        // Try to get two separate instances
        HealthDataSimulator instance1 = HealthDataSimulator.getInstance();
        HealthDataSimulator instance2 = HealthDataSimulator.getInstance();

        // Verify they are not null
        assertNotNull(instance1, "Instance 1 should not be null");
        
        // Verify they are the same object in memory
        assertSame(instance1, instance2, "Both HealthDataSimulator variables should point to the exact same instance");
        
        System.out.println("TEST PASSED: HealthDataSimulator Singleton successfully tested!");
    }
}