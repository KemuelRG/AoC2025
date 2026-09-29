package test;

import org.junit.jupiter.api.Test;
import software.aoc.day07.Manifold;
import software.aoc.day07.b.QuantumTachyonSimulator;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day07BTest {

    private final String tachyon_map = """
            .......S.......
            ...............
            .......^.......
            ...............
            ......^.^......
            ...............
            .....^.^.^.....
            ...............
            ....^.^...^....
            ...............
            ...^.^...^.^...
            ...............
            ..^...^.....^..
            ...............
            .^.^.^.^.^...^.
            ...............
            """;

    private long countTimelines(String input) {
        List<String> lines = Arrays.asList(input.split("\\n"));
        return new QuantumTachyonSimulator(new Manifold(lines)).countTimelines();
    }

    @Test
    public void given_tachyon_map_should_account_number_of_split_beams() {
        assertEquals(5, countTimelines("""
                                             ...S...
                                             ...^...
                                             ..^...^
                                             .^..^..
                                             .......
                                             """));
        assertEquals(40, countTimelines(tachyon_map));
    }

    @Test
    public void reward() throws Exception {
        List<String> lines = Files.readAllLines(Paths.get("src/test/resources/day07/orders.txt"));
        long result = new QuantumTachyonSimulator(new Manifold(lines)).countTimelines();

        assertEquals(3806264447357L, result);
    }
}