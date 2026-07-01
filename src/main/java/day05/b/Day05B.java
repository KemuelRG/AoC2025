package day05.b;

import common.LoaderFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Day05B {
    public static void main(String[] args) {
        List<String> inputs = LoaderFactory.getLoaderFor("day05/input05.txt").loadInputs();
        List<Range> ranges = inputs.stream()
                .filter(s -> s.contains("-"))
                .map(IdParser::parse)
                .toList();
        PuzzleSolver solver = new PuzzleSolver();
        long solution = solver.solve(ranges);
        System.out.println("Total fresh ingredient IDs: " + solution);
    }

    public record Range(long first, long last) {
        public static Range of(long first, long last) {
            return new Range(first, last);
        }

        public long size() {
            return last - first + 1;
        }
    }

    public static class IdParser {
        public static Range parse(String input) {
            String[] parts = input.split("-");
            return Range.of(Long.parseLong(parts[0]), Long.parseLong(parts[1]));
        }
    }

    public static class PuzzleSolver {
        public long solve(List<Range> inputRanges) {
            if (inputRanges == null || inputRanges.isEmpty()) return 0;
            List<Range> sortedRanges = inputRanges.stream()
                    .sorted(Comparator.comparingLong(Range::first))
                    .toList();
            List<Range> mergedRanges = new ArrayList<>();
            Range current = sortedRanges.get(0);
            for (int i = 1; i < sortedRanges.size(); i++) {
                Range next = sortedRanges.get(i);
                if (current.last() >= next.first()) {
                    current = Range.of(current.first(), Math.max(current.last(), next.last()));
                } else {
                    mergedRanges.add(current);
                    current = next;
                }
            }
            mergedRanges.add(current);
            return mergedRanges.stream()
                    .mapToLong(Range::size)
                    .sum();
        }
    }
}