package src.java.day06.b;

import src.java.common.LoaderFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Day06B {
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
            if (input == null || input.isEmpty()) return operations;
            int maxLength = input.stream().mapToInt(String::length).max().orElse(0);
            List<String> normalizedInput = input.stream()
                    .map(s -> String.format("%-" + maxLength + "s", s))
                    .toList();
            int operatorRowIdx = normalizedInput.size() - 1;
            List<Long> currentOperands = new ArrayList<>();
            Operator currentOperator = null;
            for (int col = 0; col < maxLength; col++) {
                boolean isAllSpaces = true;
                for (String row : normalizedInput) {
                    if (row.charAt(col) != ' ') {
                        isAllSpaces = false;
                        break;
                    }
                }
                if (isAllSpaces) {
                    if (!currentOperands.isEmpty() && currentOperator != null) {
                        Collections.reverse(currentOperands);
                        operations.add(new Operation(new ArrayList<>(currentOperands), currentOperator));
                        currentOperands.clear();
                        currentOperator = null;
                    }
                } else {
                    StringBuilder numBuilder = new StringBuilder();
                    for (int row = 0; row < operatorRowIdx; row++) {
                        char c = normalizedInput.get(row).charAt(col);
                        if (c != ' ') {
                            numBuilder.append(c);
                        }
                    }
                    if (!numBuilder.isEmpty()) {
                        currentOperands.add(Long.parseLong(numBuilder.toString()));
                    }
                    char opChar = normalizedInput.get(operatorRowIdx).charAt(col);
                    if (opChar != ' ') {
                        currentOperator = Operator.of(String.valueOf(opChar));
                    }
                }
            }
            if (!currentOperands.isEmpty() && currentOperator != null) {
                Collections.reverse(currentOperands);
                operations.add(new Operation(new ArrayList<>(currentOperands), currentOperator));
            }
            Collections.reverse(operations);
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