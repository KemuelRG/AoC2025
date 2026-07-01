package day06.a;

import common.LoaderFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day06A {
    public static void main(String[] args) {
        List<String> input = LoaderFactory.getLoaderFor("day06/input06.txt").loadInputs();
        List<Operation> operations = InputParser.parse(input);
        PuzzleSolver solver = new PuzzleSolver();
        long result = solver.solve(operations);
        System.out.println("The grand total is: " + result);
    }

    public enum Operator {
        MULTIPLY("*") {
            @Override
            public long apply(List<Long> operands) {
                return operands.stream().reduce(1L, (a, b) -> a * b);
            }
        },
        ADDITION("+") {
            @Override
            public long apply(List<Long> operands) {
                // Suma funcional
                return operands.stream().reduce(0L, Long::sum);
            }
        };

        private final String symbol;

        Operator(String symbol) {
            this.symbol = symbol;
        }

        public static Operator of(String s) {
            return Arrays.stream(values())
                    .filter(operator -> operator.symbol.equals(s))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Unknown operator: " + s));
        }

        public abstract long apply(List<Long> operands);
    }

    public record Operation(List<Long> operands, Operator operator) {
        public long execute() {
            return operator.apply(operands);
        }
    }

    public static class InputParser {
        public static List<Operation> parse(List<String> input) {
            List<Operation> operations = new ArrayList<>();
            List<String[]> rows = input.stream()
                    .map(String::trim)
                    .map(s -> s.replaceAll("\\s{2,}", " ").split(" "))
                    .toList();
            int numberOfProblems = rows.get(0).length;
            int numOperandRows = rows.size() - 1;
            for (int col = 0; col < numberOfProblems; col++) {
                List<Long> operandsList = new ArrayList<>();
                for (int row = 0; row < numOperandRows; row++) {
                    operandsList.add(Long.parseLong(rows.get(row)[col]));
                }
                Operator operator = Operator.of(rows.get(numOperandRows)[col]);
                operations.add(new Operation(operandsList, operator));
            }
            return operations;
        }
    }

    public static class PuzzleSolver {
        public long solve(List<Operation> operations) {
            return operations.stream()
                    .mapToLong(Operation::execute)
                    .sum();
        }
    }
}