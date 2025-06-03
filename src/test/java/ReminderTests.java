import java.time.ZonedDateTime;
import java.time.ZoneId;
import org.junit.Test;
import static org.junit.Assert.*;

public class ReminderTests {



    @Test
    public void testGetReminderDateReturnsCorrectValue() {
        Reminder reminder = new Reminder();

        ZonedDateTime expectedReminder = ZonedDateTime.of(2025, 12, 10, 12, 0, 0, 0, ZoneId.of("Europe/London"));

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
