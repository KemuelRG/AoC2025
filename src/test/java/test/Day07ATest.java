package test;

import org.junit.jupiter.api.Test;
import software.aoc.day07.Manifold;
import software.aoc.day07.a.TachyonSimulator;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day07ATest {

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

    private long countSplits(String input) {
        List<String> lines = Arrays.asList(input.split("\\n"));
        return new TachyonSimulator(new Manifold(lines)).countSplits();
    }

    @Test
    public void given_tachyon_map_should_account_number_of_split_beams() {
        assertEquals(4, countSplits("""
                                             ...S...
                                             ...^...
                                             ..^...^
                                             .^..^..
                                             .......
                                             """));
        assertEquals(21, countSplits(tachyon_map));
    }

    @Test
    public void reward() throws Exception {
        List<String> lines = Files.readAllLines(Paths.get("src/test/resources/day07/orders.txt"));
        long result = new TachyonSimulator(new Manifold(lines)).countSplits();

        assertEquals(1490L, result);
    }
}