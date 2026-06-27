package src.java.day02.a;

import src.java.common.LoaderFactory;

import java.util.Arrays;
import java.util.List;

public class Day02A {
    public static void main(String[] args) {
        List<String> inputLines = LoaderFactory.getLoaderFor("day02/input02.txt").loadInputs();
        List<Range> ranges = inputLines.stream()
                .flatMap(line -> Arrays.stream(line.split(",")))
                .map(IdParser::parse)
                .toList();

        IdValidator validator = new IdValidator();
        PuzzleSolver solver = new PuzzleSolver();

        long solution = solver.calculateSolution(ranges, validator);
        System.out.println("The solution is: " + solution);
    }

    public record Range(long first, long last) {
        public static Range of(long first, long last) {
            return new Range(first, last);
        }
    }

    public static class IdParser {
        public static Range parse(String input) {
            String[] parts = input.trim().split("-");
            return Range.of(Long.parseLong(parts[0]), Long.parseLong(parts[1]));
        }
    }

    public static class IdValidator {
        public boolean isInvalid(long number) {
            String stringValueOfNumber = String.valueOf(number);
            if (stringValueOfNumber.length() % 2 != 0) {
                return false;
            }
            return compareParts(stringValueOfNumber);
        }

        private boolean compareParts(String stringValueOfNumber) {
            int midIndex = stringValueOfNumber.length() / 2;
            String firstHalf = stringValueOfNumber.substring(0, midIndex);
            String secondHalf = stringValueOfNumber.substring(midIndex);
            return firstHalf.equals(secondHalf);
        }
    }

    public static class PuzzleSolver {
        public long calculateSolution(List<Range> mustCheckIds, IdValidator validator) {
            long totalSum = 0;
            for (Range range : mustCheckIds) {
                for (long i = range.first(); i <= range.last(); i++) {
                    if (validator.isInvalid(i)) {
                        totalSum += i;
                    }
                }
            }
            return totalSum;
        }
    }
}