package test;

import org.junit.jupiter.api.Test;
import software.aoc.day04.Grid;
import software.aoc.day04.FewerThanFourRule;
import software.aoc.day04.a.ForkliftOptimizer;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day04ATest {

    public final static String diagram = """
                                        ..@@.@@@@.
                                        @@@.@.@.@@
                                        @@@@@.@.@@
                                        @.@@@@..@.
                                        @@.@@@@.@@
                                        .@@@@@@@.@
                                        .@.@.@.@@@
                                        @.@@@.@@@@
                                        .@@@@@@@@.
                                        @.@.@@@.@.
                                        """;

    private long calculateAccessibleRolls(String input) {
        List<String> lines = Arrays.stream(input.replace("·", ".").split("\\n"))
                .map(String::trim)
                .toList();
        return new ForkliftOptimizer(new FewerThanFourRule()).countAccessibleRolls(new Grid(lines));
    }

    @Test
    public void given_diagram_should_account_number_of_rolls_accessible() {
        assertEquals(7, calculateAccessibleRolls("""
                                             ··@@·@··
                                             ·@·@@···
                                             ··@@··@·
                                             """));
        assertEquals(13, calculateAccessibleRolls(diagram));
    }

    @Test
    public void reward() throws Exception {
        List<String> lines = Files.readAllLines(Paths.get("src/test/resources/day04/orders.txt"));
        long result = new ForkliftOptimizer(new FewerThanFourRule()).countAccessibleRolls(new Grid(lines));

        assertEquals(1367L, result);
    }
}