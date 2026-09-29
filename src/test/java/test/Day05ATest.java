package test;

import org.junit.jupiter.api.Test;
import software.aoc.day05.CafeteriaManager;
import software.aoc.day05.InventoryParser;
import software.aoc.day05.a.MergedIntervalRule;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day05ATest {

    public final static String ranges = """
                                        3-5
                                        10-14
                                        16-20
                                        12-18
                                        """;

    public final static String ingredients = """
                                             1
                                             5
                                             8
                                             11
                                             17
                                             32
                                             """;

    private long calculateFreshIngredients(String rangesInput, String idsInput) {
        List<String> combinedLines = new java.util.ArrayList<>(Arrays.asList(rangesInput.split("\\n")));
        combinedLines.add("");
        combinedLines.addAll(Arrays.asList(idsInput.split("\\n")));

        InventoryParser.ParsedData data = InventoryParser.parse(combinedLines);
        CafeteriaManager manager = new CafeteriaManager(new MergedIntervalRule(data.ranges()));
        return manager.countFreshIngredients(data.availableIds());
    }

    @Test
    public void given_ranges_should_count_fresh_ingredients() {
        assertEquals(3, calculateFreshIngredients("10-20", "1\n5\n10\n15\n20"));
        assertEquals(3, calculateFreshIngredients(ranges, ingredients));
    }

    @Test
    public void reward() throws Exception {
        List<String> lines = Files.readAllLines(Paths.get("src/test/resources/day05/orders.txt"));
        InventoryParser.ParsedData data = InventoryParser.parse(lines);

        long result = new CafeteriaManager(new MergedIntervalRule(data.ranges())).countFreshIngredients(data.availableIds());

        assertEquals(611L, result);
    }
}