package test;

import org.junit.jupiter.api.Test;
import software.aoc.day11.NetworkGraph;
import software.aoc.day11.NetworkParser;
import software.aoc.day11.ReactorManager;
import software.aoc.day11.a.MemoizedDfsPathCounter;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day11ATest {

    private final String devices = """
                              aaa: you hhh
                              you: bbb ccc
                              bbb: ddd eee
                              ccc: ddd eee fff
                              ddd: ggg
                              eee: out
                              fff: out
                              ggg: out
                              hhh: ccc fff iii
                              iii: out
                              """;

    private long calculatePaths(String input) {
        List<String> lines = Arrays.asList(input.split("\\n"));
        NetworkGraph graph = new NetworkParser().parse(lines);
        return new ReactorManager(new MemoizedDfsPathCounter()).analyzeDataFlow(graph, "you", "out");
    }

    @Test
    public void given_devices_should_count_number_of_paths_to_out() {
        assertEquals(5, calculatePaths(devices));
    }

    @Test
    public void reward() throws Exception {
        List<String> lines = Files.readAllLines(Paths.get("src/test/resources/day11/orders.txt"));
        NetworkGraph graph = new NetworkParser().parse(lines);

        long result = new ReactorManager(new MemoizedDfsPathCounter()).analyzeDataFlow(graph, "you", "out");

        assertEquals(719L, result);
    }
}