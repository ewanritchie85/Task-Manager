import java.time.LocalDateTime;
import org.junit.Test;
import static org.junit.Assert.*;

public class EventTests {

    @Test
    public void testGetNameReturnsCorrectValue() {
        Event event = new Event();
        String expectedName = "Test Event";

        try {
            java.lang.reflect.Field nameField = Event.class.getDeclaredField("name");
            nameField.setAccessible(true);
            nameField.set(event, expectedName);
        } catch (Exception e) {
            fail("Reflection failed to set name field: " + e.getMessage());
        }

        assertEquals(expectedName, event.getName());
    }

    @Test
    public void testGetDateReturnsCorrectValue() {
        Event event = new Event();

        LocalDateTime expectedDate = LocalDateTime.of(2025, 12, 10, 14, 0);

        try {
            java.lang.reflect.Field dateField = Event.class.getDeclaredField("date");
            dateField.setAccessible(true);
            dateField.set(event, expectedDate);
        } catch (Exception e) {
            fail("Reflection failed to set date field: " + e.getMessage());
        }

        assertEquals(expectedDate, event.getDate());
    }

    @Test
    public void testGetReminderDateReturnsCorrectValue() {
        Event event = new Event();

        LocalDateTime expectedReminder = LocalDateTime.of(2025, 12, 10, 12, 0);

        try {
            java.lang.reflect.Field reminderField = Event.class.getDeclaredField("reminderDate");
            reminderField.setAccessible(true);
            reminderField.set(event, expectedReminder);
        } catch (Exception e) {
            fail("Reflection failed to set reminderDate field: " + e.getMessage());
        }

        assertEquals(expectedReminder, event.getReminderDate());
    }

}
