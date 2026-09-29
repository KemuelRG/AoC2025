package test;

import org.junit.jupiter.api.Test;
import software.aoc.day11.NetworkGraph;
import software.aoc.day11.NetworkParser;
import software.aoc.day11.ReactorManager;
import software.aoc.day11.b.MandatoryNodesPathCounter;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day11BTest {

    private final String devices = """
                                  svr: aaa bbb
                                  aaa: fft
                                  fft: ccc
                                  bbb: tty
                                  tty: ccc
                                  ccc: ddd eee
                                  ddd: hub
                                  hub: fff
                                  eee: dac
                                  dac: fff
                                  fff: ggg hhh
                                  ggg: out
                                  hhh: out
                                  """;

    private long calculatePaths(String input) {
        List<String> lines = Arrays.asList(input.split("\\n"));
        NetworkGraph graph = new NetworkParser().parse(lines);
        return new ReactorManager(new MandatoryNodesPathCounter("dac", "fft")).analyzeDataFlow(graph, "svr", "out");
    }

    @Test
    public void given_devices_should_count_number_of_valid_paths_to_out() {
        assertEquals(2, calculatePaths(devices));
    }

    @Test
    public void reward() throws Exception {
        List<String> lines = Files.readAllLines(Paths.get("src/test/resources/day11/orders.txt"));
        NetworkGraph graph = new NetworkParser().parse(lines);

        long result = new ReactorManager(new MandatoryNodesPathCounter("dac", "fft")).analyzeDataFlow(graph, "svr", "out");

        assertEquals(337433554149492L, result);
    }
}