package src.java.day09.a;

import src.java.common.LoaderFactory;
import java.util.List;

public class Day09A {
    public static void main(String[] args) {
        List<String> inputLines = LoaderFactory.getLoaderFor("day09/input09.txt").loadInputs();
        List<Coordinate> coordinates = InputParser.parse(inputLines);
        PuzzleSolver solver = new PuzzleSolver();
        long maxArea = solver.solve(coordinates);
        System.out.println("The maximum area of any rectangle is: " + maxArea);
    }

    public record Coordinate(long x, long y) {

        public static Coordinate of(long x, long y) {
            return new Coordinate(x, y);
        }

        public long areaWith(Coordinate other) {
            long width = Math.abs(this.x - other.x) + 1;
            long height = Math.abs(this.y - other.y) + 1;
            return width * height;
        }
    }

    public static class InputParser {
        public static List<Coordinate> parse(List<String> input) {
            return input.stream()
                    .map(line -> {
                        String[] parts = line.split(",");
                        return Coordinate.of(Long.parseLong(parts[0]), Long.parseLong(parts[1]));
                    })
                    .toList();
        }
    }

    public static class PuzzleSolver {
        public long solve(List<Coordinate> coordinates) {
            long maxArea = 0;
            int n = coordinates.size();
            for (int i = 0; i < n; i++) {
                Coordinate firstCoord = coordinates.get(i);
                for (int j = i + 1; j < n; j++) {
                    Coordinate secondCoord = coordinates.get(j);
                    long currentArea = firstCoord.areaWith(secondCoord);
                    if (currentArea > maxArea) {
                        maxArea = currentArea;
                    }
                }
            }
            return maxArea;
        }
    }
}