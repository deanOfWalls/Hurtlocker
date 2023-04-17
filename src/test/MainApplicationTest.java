import org.junit.Assert;
import org.junit.Test;

import java.util.regex.Pattern;

public class MainApplicationTest {
    @Test
    public void testReadRawDataToString() throws Exception {
        Main main = new Main();
        String result = main.readRawDataToString();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isEmpty());
    }




}
