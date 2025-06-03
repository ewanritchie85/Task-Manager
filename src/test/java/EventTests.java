import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.ZoneId;
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

        ZonedDateTime expectedDate = ZonedDateTime.of(2025, 12, 10, 14, 0, 0, 0, ZoneId.of("Europe/London"));

        try {
            java.lang.reflect.Field dateField = Event.class.getDeclaredField("zonedDate");
            dateField.setAccessible(true);
            dateField.set(event, expectedDate);
        } catch (Exception e) {
            fail("Reflection failed to set date field: " + e.getMessage());
        }

        assertEquals(expectedDate.toLocalDateTime(), event.getDate());
    }


}
