package test;

import org.junit.jupiter.api.Test;
import software.aoc.day12.a.DfsPackingOptimizer;
import software.aoc.day12.a.Grid;
import software.aoc.day12.a.PackingParser;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day12ATest {

    private static final String christmasTreeFarm = """
                                                    0:
                                                    ###
                                                    ##.
                                                    ##.
                                                   \s
                                                    1:
                                                    ###
                                                    ##.
                                                    .##
                                                   \s
                                                    2:
                                                    .##
                                                    ###
                                                    ##.
                                                   \s
                                                    3:
                                                    ##.
                                                    ###
                                                    ##.
                                                   \s
                                                    4:
                                                    ###
                                                    #..
                                                    ###
                                                   \s
                                                    5:
                                                    ###
                                                    .#.
                                                    ###
                                                   \s
                                                    4x4: 0 0 0 0 2 0
                                                    12x5: 1 0 1 0 2 2
                                                    12x5: 1 0 1 0 3 2
                                                    """;

    private long calculateFittingTrees(String input) {
        List<String> lines = Arrays.asList(input.split("\\n"));
        PackingParser.ParsedData data = new PackingParser().parse(lines);
        DfsPackingOptimizer optimizer = new DfsPackingOptimizer();

        long successfulPacks = 0;
        for (PackingParser.Region region : data.regions()) {
            Grid grid = new Grid(region.w(), region.h());
            if (optimizer.canPackAll(grid, region.pieces(), data.shapesVariationsCache())) {
                successfulPacks++;
            }
        }
        return successfulPacks;
    }

    @Test
    public void given_presents_and_trees_should_count_fitting_trees() {
        assertEquals(2, calculateFittingTrees(christmasTreeFarm));
    }

    @Test
    public void reward() throws Exception {
        List<String> lines = Files.readAllLines(Paths.get("src/test/resources/day12/orders.txt"));
        PackingParser.ParsedData data = new PackingParser().parse(lines);
        DfsPackingOptimizer optimizer = new DfsPackingOptimizer();

        long successfulPacks = 0;
        for (PackingParser.Region region : data.regions()) {
            Grid grid = new Grid(region.w(), region.h());
            if (optimizer.canPackAll(grid, region.pieces(), data.shapesVariationsCache())) {
                successfulPacks++;
            }
        }

        assertEquals(599L, successfulPacks);
    }
}