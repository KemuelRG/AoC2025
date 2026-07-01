package day02.b;

import common.LoaderFactory;

import java.util.Arrays;
import java.util.List;

public class Day02B {

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
        public boolean isInvalid(long id) {
            String idStr = String.valueOf(id);
            int length = idStr.length();
            for (int seedLength = 1; seedLength <= length / 2; seedLength++) {
                if (length % seedLength == 0) {
                    String seed = idStr.substring(0, seedLength);
                    int repetitionsRequired = length / seedLength;
                    if (idStr.equals(seed.repeat(repetitionsRequired))) {
                        return true;
                    }
                }
            }
            return false;
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