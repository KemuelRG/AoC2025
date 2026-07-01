package day03.a;

import common.LoaderFactory;
import java.util.List;

public class Day03A {
    public static void main(String[] args) {
        List<String> input = LoaderFactory.getLoaderFor("day03/input03.txt").loadInputs();
        BankOptimizer optimizer = new TwoBatteriesOptimizer();
        PuzzleSolver solver = new PuzzleSolver();
        int solution = solver.getSolution(input, optimizer);
        System.out.println("The solution is: " + solution);
    }

    public interface BankOptimizer {
        int findMaxJoltage(String bank);
    }

    public static class TwoBatteriesOptimizer implements BankOptimizer {

        @Override
        public int findMaxJoltage(String bank) {
            int max = 0;
            int length = bank.length();
            for (int i = 0; i < length - 1; i++) {
                for (int j = i + 1; j < length; j++) {
                    int combination = Integer.parseInt(
                            bank.substring(i, i + 1) + bank.substring(j, j + 1)
                    );
                    if (combination > max) {
                        max = combination;
                    }
                }
            }
            return max;
        }
    }

    public static class PuzzleSolver {
        public int getSolution(List<String> input, BankOptimizer optimizer) {
            return input.stream()
                    .mapToInt(optimizer::findMaxJoltage)
                    .sum();
        }
    }
}