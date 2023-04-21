import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] a) throws Exception {
        // Read in the contents of the file "RawData.txt" as a string and replace any "^" characters with ";".
        var r = Files.readString(Paths.get("src/main/resources/RawData.txt")).replace("^", ";");

        // Define a regular expression pattern to match lines in the format "name:<name>;price:<price>;type:<type>;expiration:<expiration>".
        var p = Pattern.compile("(?i)name:(\\w+);price:(\\d+\\.\\d{2});type:(\\w+);expiration:(\\d{1,2}/\\d{1,2}/\\d{4})");

        /*REGEX EXPLANATION:
        (?i) - turns on case insensitive matching
        name: - matches literal string 'name'
        (\w+) - matches one or more word characters (letters, digits, or underscores) and captures them as a group. this group is for product name)
        ;price: - matches literal string ';price:'
        (\d+\.\d{2}) - matches one or more digits followed by a decimal point followed by exactly two digits and captures as a group (this is for product price)
        ;type: - matches literal string ';type:'
        (\w+) - matches one or more word characters (letters, digits, underscores) and captures as group. this group is for product type)
        ;expiration: - matches literal string ';expiration:'
        (\d{1,2}/\d{1,2}/\d{4}) - matches a date in the format MM/DD/YYYY and captures as group. allows for month and day to be one or two digits and year must be four
         */


        // Create a new empty HashMap to hold the results.
        var t = new HashMap<String, Map<String, Integer>>();

        // Use the regular expression pattern to find matches in the input string and process them.
        p.matcher(r).results().forEach(m -> {
            // Extract the name from the match and convert it to lowercase.
            var n = m.group(1).toLowerCase();

            // Extract the price from the match and use it as a key in the HashMap. If the key doesn't exist, create a new entry with a value of 1. If it does exist, increment the value by 1.
            t.computeIfAbsent(n, k -> new HashMap<>()).merge(m.group(2), 1, Integer::sum);
        });

        // Print out the results.
        t.forEach((k, v) -> {
            // Print out the name and the total number of times it was seen.
            System.out.printf("name: %s seen: %d times%n============= \t =============%n", k, v.values().stream().mapToInt(Integer::intValue).sum());

            // Print out each price and the number of times it was seen.
            v.forEach((kk, vv) -> System.out.printf("Price: \t%s\t seen: %d times%n-------------\t -------------%n", kk, vv));

            // Print a blank line to separate the output for each name.
            System.out.println();
        });

        // Print out the number of times an error occurred (i.e., a line in the input file didn't match the regular expression pattern).
        System.out.printf("Errors \t\t seen: %d times", t.getOrDefault("", new HashMap<>()).getOrDefault("", 0));
    }
}





//
//
//
//
//
//import org.apache.commons.io.IOUtils;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//public class Main {
//
//    public String readRawDataToString() throws IOException {
//        // Get the ClassLoader for this class and load the "RawData.txt" file as a string
//        ClassLoader classLoader = getClass().getClassLoader();
//        String result = IOUtils.toString(classLoader.getResourceAsStream("RawData.txt"));
//
//        // Replace all occurrences of "^" with ";"
//        result = result.replace("^", ";");
//        return result;
//    }
//
//    public static void main(String[] args) throws Exception {
//        // Call the readRawDataToString method to get the data from the "RawData.txt" file
//        String rawData = (new Main()).readRawDataToString();
//
//        // Create a regular expression pattern to match each item in the data
//        Pattern pattern = Pattern.compile("(?i)name:([\\w\\s]+);price:(\\d+\\.\\d{2});type:(\\w+);expiration:(\\d{1,2}/\\d{1,2}/\\d{4})");
//        //expected results milk 8, bread 6, apples 4, cookies 8 (fix one typo co0kies)
//
//
//        // Create a HashMap to store the results
//        Map<String, Map<String, Integer>> itemsMap = new HashMap<>();
//
//        // Use the regular expression pattern to find all matches in the raw data and add them to the HashMap
//        Matcher matcher = pattern.matcher(rawData);
//        while (matcher.find()) {
//            String name = matcher.group(1).toLowerCase().replaceAll("\\s+", "");
//            String price = matcher.group(2);
//            if (!itemsMap.containsKey(name)) {
//                itemsMap.put(name, new HashMap<>());
//            }
//            Map<String, Integer> itemData = itemsMap.get(name);
//            itemData.put(price, itemData.getOrDefault(price, 0) + 1);
//        }
//
//        // Print out the results for each item in the HashMap
//        for (Map.Entry<String, Map<String, Integer>> entry : itemsMap.entrySet()) {
//            System.out.println("name:\t" + entry.getKey() + "\tseen: " + entry.getValue().values().stream().reduce(0, Integer::sum) + " times");
//            System.out.println("============= \t =============");
//            for (Map.Entry<String, Integer> priceEntry : entry.getValue().entrySet()) {
//                System.out.println("Price: \t" + priceEntry.getKey() + "\tseen: " + priceEntry.getValue() + " times");
//                System.out.println("-------------\t-------------");
//            }
//            System.out.println();
//        }
//
//        // Print out the total number of errors found
//        System.out.println("Errors \t\tseen: " + itemsMap.getOrDefault("", new HashMap<>()).getOrDefault("", 0) + " times");
//    }
//}