import java.time.LocalDateTime;
import org.junit.Test;
import static org.junit.Assert.*;

public class ReminderTests {



    @Test
    public void testGetReminderDateReturnsCorrectValue() {
        Reminder reminder = new Reminder();

        LocalDateTime expectedReminder = LocalDateTime.of(2025, 12, 10, 12, 0);

        try {
            java.lang.reflect.Field reminderField = Reminder.class.getDeclaredField("reminderDate");
            reminderField.setAccessible(true);
            reminderField.set(reminder, expectedReminder);
        } catch (Exception e) {
            fail("Reflection failed to set reminderDate field: " + e.getMessage());
        }

        assertEquals(expectedReminder, reminder.getReminderDate());
    }

}
