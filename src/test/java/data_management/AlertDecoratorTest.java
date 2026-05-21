package data_management;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.alerts.Alert;
import com.alerts.BloodOxygenAlert;
import com.alerts.BloodPressureAlert;
import com.alerts.ECGAlert;
import com.alerts.PriorityAlertDecorator;
import com.alerts.RepeatedAlertDecorator;

class AlertDecoratorTest {

    // Priority Decorator Tests

    @Test
    void testPriorityDecorator() {
        // 1. Create a basic concrete alert
        Alert basicAlert = new BloodPressureAlert("101", "Critical High BP", 1500L);
        
        // 2. Wrap it in the Priority Decorator
        Alert priorityAlert = new PriorityAlertDecorator(basicAlert);

        // 3. Verify the wrapper didn't lose the original data
        assertEquals("101", priorityAlert.getPatientId(), "Patient ID should be preserved");
        
        // 4. Verify the message was successfully modified dynamically
        String expectedMessage = "[URGENT PRIORITY] Blood Pressure Alert for Patient 101 | Condition: Critical High BP";
        assertEquals(expectedMessage, priorityAlert.getAlertMessage(), "Message should include Priority tag");
        
        System.out.println("TEST PASSED: PriorityAlertDecorator successfully tested!");
    }

    // Repeated Decorator Tests

    @Test
    void testRepeatedDecorator() {
        // 1. Create a basic concrete alert
        Alert basicAlert = new BloodOxygenAlert("102", "Low SpO2 Drop", 2500L);
        
        // 2. Wrap it in the Repeated Decorator
        Alert repeatedAlert = new RepeatedAlertDecorator(basicAlert);

        // 3. Verify the message was successfully modified
        String expectedMessage = "Blood Oxygen Alert for Patient 102 | Condition: Low SpO2 Drop (Repeated Alert: Condition Re-evaluated)";
        assertEquals(expectedMessage, repeatedAlert.getAlertMessage(), "Message should include Repeated tag");
        
        System.out.println("TEST PASSED: RepeatedAlertDecorator successfully tested!");
    }

    // Combined Decorator Tests 

    @Test
    void testCombinedDecorators() {
        // 1. Create a basic concrete alert
        Alert basicAlert = new ECGAlert("103", "Irregular Heartbeat", 3500L);
        
        // 2. Wrap it in both decorators
        Alert repeatedAlert = new RepeatedAlertDecorator(basicAlert);
        Alert urgentAndRepeatedAlert = new PriorityAlertDecorator(repeatedAlert);

        // 3. Verify both modifications are present
        String expectedMessage = "[URGENT PRIORITY] ECG Alert for Patient 103 | Condition: Irregular Heartbeat (Repeated Alert: Condition Re-evaluated)";
        assertEquals(expectedMessage, urgentAndRepeatedAlert.getAlertMessage(), "Message should include BOTH Priority and Repeated tags");
        
        System.out.println("TEST PASSED: Combined Decorators successfully tested!");
    }
}