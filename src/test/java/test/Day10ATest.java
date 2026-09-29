package test;

import org.junit.jupiter.api.Test;
import software.aoc.day10.a.BfsInitializationOptimizer;
import software.aoc.day10.a.FactoryManager;
import software.aoc.day10.a.FactoryParser;
import software.aoc.day10.a.Machine;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day10ATest {

    private final String machines = """
            [.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}
            [...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}
            [.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}
            """;

    private long calculateMinimumPresses(String input) {
        List<String> lines = Arrays.asList(input.split("\\n"));
        List<Machine> parsedMachines = new FactoryParser().parse(lines);
        return new FactoryManager(new BfsInitializationOptimizer()).calculateTotalPresses(parsedMachines);
    }

    @Test
    public void given_light_target_should_count_minimum_button_presses() {
        assertEquals(7, calculateMinimumPresses(machines));
    }

    @Test
    public void reward() throws Exception {
        List<String> lines = Files.readAllLines(Paths.get("src/test/resources/day10/orders.txt"));
        List<Machine> parsedMachines = new FactoryParser().parse(lines);

        long result = new FactoryManager(new BfsInitializationOptimizer()).calculateTotalPresses(parsedMachines);

        assertEquals(396L, result);
    }
}