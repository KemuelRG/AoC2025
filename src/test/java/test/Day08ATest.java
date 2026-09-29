package test;

import org.junit.jupiter.api.Test;
import software.aoc.day08.JunctionParser;
import software.aoc.day08.Point3D;
import software.aoc.day08.a.PlaygroundOptimizer;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day08ATest {

    private final String inputBoxes = """    
                        162,817,812
                        57,618,57
                        906,360,560
                        592,479,940
                        352,342,300
                        466,668,158
                        542,29,236
                        431,825,988
                        739,650,466
                        52,470,668
                        216,146,977
                        819,987,18
                        117,168,530
                        805,96,715
                        346,949,466
                        970,615,88
                        941,993,340
                        862,61,35
                        984,92,344
                        425,690,689
                        """;

    private long calculateLargestCircuits(String input, int connections) {
        List<String> lines = Arrays.asList(input.split("\\n"));
        List<Point3D> points = new JunctionParser().parse(lines);
        return new PlaygroundOptimizer().calculateLargestCircuitsMetric(points, connections);
    }

    @Test
    public void given_boxes_should_account_largest_circuits() {
        assertEquals(6, calculateLargestCircuits("""
                                          1,2,3
                                          9,8,7
                                          10,11,12
                                          13,1,15
                                          2,3,4
                                          2,2,2
                                          10,10,10
                                          23,23,23
                                          """, 7));
        assertEquals(40, calculateLargestCircuits(inputBoxes, 10));
    }

    @Test
    public void reward() throws Exception {
        List<String> lines = Files.readAllLines(Paths.get("src/test/resources/day08/orders.txt"));
        List<Point3D> points = new JunctionParser().parse(lines);

        long result = new PlaygroundOptimizer().calculateLargestCircuitsMetric(points, 1000);

        assertEquals(96672L, result);
    }
}