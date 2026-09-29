package test;

import org.junit.jupiter.api.Test;
import software.aoc.day09.MovieTheaterManager;
import software.aoc.day09.MovieTheaterParser;
import software.aoc.day09.Tile;
import software.aoc.day09.a.MaxAreaOptimizer;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day09ATest {

    private final String inputTiles = """    
                        7,1
                        11,1
                        11,7
                        9,7
                        9,5
                        2,5
                        2,3
                        7,3
                        """;

    private long calculateLargestArea(String input) {
        List<String> lines = Arrays.asList(input.split("\\n"));
        List<Tile> tiles = new MovieTheaterParser().parse(lines);
        return new MovieTheaterManager(new MaxAreaOptimizer()).calculateOptimalArea(tiles);
    }

    @Test
    public void given_tiles_should_account_largest_area() {
        assertEquals(50, calculateLargestArea(inputTiles));
    }

    @Test
    public void reward() throws Exception {
        List<String> lines = Files.readAllLines(Paths.get("src/test/resources/day09/orders.txt"));
        List<Tile> tiles = new MovieTheaterParser().parse(lines);

        long result = new MovieTheaterManager(new MaxAreaOptimizer()).calculateOptimalArea(tiles);

        assertEquals(4741451444L, result);
    }
}