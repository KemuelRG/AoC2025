package test;

import org.junit.jupiter.api.Test;
import software.aoc.day10.b.FactoryManager;
import software.aoc.day10.b.FactoryParser;
import software.aoc.day10.b.Machine;
import software.aoc.day10.b.MemoizedMachineSolver;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day10BTest {

    private final String machines = """
            [.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}
            [...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}
            [.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}
            """;

    private long calculateMinimumPresses(String input) {
        List<Machine> parsedMachines = new FactoryParser().parse(input);
        return new FactoryManager(new MemoizedMachineSolver()).configureAll(parsedMachines);
    }

    @Test
    public void given_joltage_requirements_should_count_minimum_button_presses() {
        assertEquals(33, calculateMinimumPresses(machines));
    }

    @Test
    public void reward() throws Exception {
        String input = Files.readString(Paths.get("src/test/resources/day10/orders.txt"));
        List<Machine> parsedMachines = new FactoryParser().parse(input);

        long result = new FactoryManager(new MemoizedMachineSolver()).configureAll(parsedMachines);

        assertEquals(15688L, result);
    }
}