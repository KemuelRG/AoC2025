package src.java.day05.a;

import src.java.common.LoaderFactory;
import java.util.List;

public class Day05A {
    public static void main(String[] args) {
        List<String> inputs = LoaderFactory.getLoaderFor("day05/input05.txt").loadInputs();
        List<Range> ranges = inputs.stream()
                .filter(s -> s.contains("-"))
                .map(IdParser::parse)
                .toList();
        List<Long> availableIds = inputs.stream()
                .filter(s -> !s.contains("-") && !s.isBlank())
                .map(Long::parseLong)
                .toList();
        PuzzleSolver solver = new PuzzleSolver();
        long solution = solver.solve(availableIds, ranges);
        System.out.println("Fresh ingredients count: " + solution);
    }

    public record Range(long first, long last) {
        public static Range of(long first, long last) {
            return new Range(first, last);
        }

        public boolean contains(long id) {
            return id >= first && id <= last;
        }
    }

    public static class IdParser {
        public static Range parse(String input) {
            String[] parts = input.split("-");
            return Range.of(Long.parseLong(parts[0]), Long.parseLong(parts[1]));
        }
    }

    public static class PuzzleSolver {
        public long solve(List<Long> availableIds, List<Range> ranges) {
            return availableIds.stream()
                    .filter(id -> isFresh(id, ranges))
                    .count();
        }

        private boolean isFresh(long id, List<Range> ranges) {
            return ranges.stream().anyMatch(range -> range.contains(id));
        }
    }
}