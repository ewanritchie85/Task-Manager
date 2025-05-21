import org.junit.Test;
import static org.junit.Assert.*;
import java.time.LocalDateTime;

import java.util.List;

public class UtilTests {

    @Test
    public void testGetLocalDateTimeReturnsNow() {
        Util util = new Util();
        LocalDateTime before = LocalDateTime.now();
        LocalDateTime result = util.getLocalDateTime();
        LocalDateTime after = LocalDateTime.now();

        assertTrue(result.isAfter(before.minusSeconds(1)) && result.isBefore(after.plusSeconds(1)));
    }

    @Test
    public void testReadFromCSVReturnsList() {
        Util util = new Util();
        List<String[]> events = util.readFromCSV();
        assertNotNull(events);
    }

}
