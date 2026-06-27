package src.java.day03.b;

import src.java.common.LoaderFactory;
import java.math.BigInteger;
import java.util.List;

public class Day03B {
    public static void main(String[] args) {
        List<String> input = LoaderFactory.getLoaderFor("day03/input03.txt").loadInputs();
        BankOptimizer<BigInteger> optimizer = new TwelveBatteriesOptimizer();
        PuzzleSolver solver = new PuzzleSolver();
        BigInteger solution = solver.getSolution(input, optimizer);
        System.out.println("The new total output joltage is: " + solution);
    }

    public interface BankOptimizer<T extends Number> {
        T findMaxJoltage(String bank);
    }

    public static class TwelveBatteriesOptimizer implements BankOptimizer<BigInteger> {
        @Override
        public BigInteger findMaxJoltage(String bank) {
            StringBuilder result = new StringBuilder(bank);
            while (result.length() > 12) {
                removeFirstSmallerDigit(result);
            }
            return new BigInteger(result.toString());
        }

        private void removeFirstSmallerDigit(StringBuilder currentBank) {
            for (int i = 0; i < currentBank.length() - 1; i++) {
                if (currentBank.charAt(i) < currentBank.charAt(i + 1)) {
                    currentBank.deleteCharAt(i);
                    return;
                }
            }
            currentBank.deleteCharAt(currentBank.length() - 1);
        }
    }

    public static class PuzzleSolver {
        public BigInteger getSolution(List<String> input, BankOptimizer<BigInteger> optimizer) {
            return input.stream()
                    .map(optimizer::findMaxJoltage)
                    .reduce(BigInteger.ZERO, BigInteger::add);
        }
    }
}