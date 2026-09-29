package test;

import org.junit.jupiter.api.Test;
import software.aoc.day05.InventoryParser;
import software.aoc.day05.b.TotalFreshnessCalculator;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day05BTest {

    public final static String ranges = """
                                        3-5
                                        10-14
                                        16-20
                                        12-18
                                        """;

    private long calculateTotalFreshness(String rangesInput) {
        InventoryParser.ParsedData data = InventoryParser.parse(Arrays.asList(rangesInput.split("\\n")));
        return new TotalFreshnessCalculator().calculateTotalFresh(data.ranges());
    }

    @Test
    public void given_ranges_should_count_fresh_ingredients() {
        assertEquals(11, calculateTotalFreshness("10-20"));
        assertEquals(14, calculateTotalFreshness(ranges));
    }

    @Test
    public void reward() throws Exception {
        List<String> lines = Files.readAllLines(Paths.get("src/test/resources/day05/orders.txt"));
        InventoryParser.ParsedData data = InventoryParser.parse(lines);

        long result = new TotalFreshnessCalculator().calculateTotalFresh(data.ranges());

        assertEquals(345995423801866L, result);
    }
}