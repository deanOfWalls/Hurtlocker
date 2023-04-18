import org.junit.Assert;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainApplicationTest {
    @Test
    public void testReadRawDataToString() throws Exception {
        Main main = new Main();
        String result = main.readRawDataToString();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isEmpty());
    }

    @Test
    public void testItemPricesAreValid() throws Exception {
        Main main = new Main();
        String rawData = main.readRawDataToString();
        Pattern pattern = Pattern.compile("(?i)price:(\\d+\\.\\d{2});");
        Matcher matcher = pattern.matcher(rawData);
        while (matcher.find()) {
            String price = matcher.group(1);
            Assert.assertTrue(Double.parseDouble(price) > 0);
        }
    }

    @Test
    public void testPatternMatch() throws Exception {
        String input = "name:Milk;price:3.23;type:Food;expiration:1/25/2016";
        Pattern pattern = Pattern.compile("(?i)name:([\\w\\s]+);price:(\\d+\\.\\d{2});type:(\\w+);expiration:(\\d{1,2}/\\d{1,2}/\\d{4})");
        Matcher matcher = pattern.matcher(input);
        Assert.assertTrue(matcher.find());
        Assert.assertEquals("Milk", matcher.group(1));
        Assert.assertEquals("3.23", matcher.group(2));
        Assert.assertEquals("Food", matcher.group(3));
        Assert.assertEquals("1/25/2016", matcher.group(4));
    }

}
