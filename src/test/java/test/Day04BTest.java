package test;

import org.junit.jupiter.api.Test;
import software.aoc.day04.Grid;
import software.aoc.day04.FewerThanFourRule;
import software.aoc.day04.b.IterativeForkliftOptimizer;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day04BTest {

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

    private long calculateAllAccessibleRolls(String input) {
        List<String> lines = Arrays.stream(input.replace("·", ".").split("\\n"))
                .map(String::trim)
                .toList();
        return new IterativeForkliftOptimizer(new FewerThanFourRule()).simulateRemovalProcess(new Grid(lines));
    }

    @Test
    public void given_diagram_should_account_number_of_rolls_accessible() {
        assertEquals(9, calculateAllAccessibleRolls("""
                                             ··@@·@··
                                             ·@·@@···
                                             ··@@··@·
                                             """));
        assertEquals(43, calculateAllAccessibleRolls(diagram));
    }

    @Test
    public void reward() throws Exception {
        List<String> lines = Files.readAllLines(Paths.get("src/test/resources/day04/orders.txt"));
        long result = new IterativeForkliftOptimizer(new FewerThanFourRule()).simulateRemovalProcess(new Grid(lines));

        assertEquals(9144L, result);
    }
}