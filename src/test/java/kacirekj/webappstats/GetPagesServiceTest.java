package kacirekj.webappstats;

import kacirekj.webappstats.service.GetPagesService;
import org.junit.Test;

import static org.junit.Assert.*;

public class GetPagesServiceTest {

    @Test
    public void processKeyword() {
    }

    @Test
    public void stringToInt() {
        GetPagesService g = new GetPagesService();
        int i = g.stringToInt("123 456");
        assertEquals(123456, i);
    }
}