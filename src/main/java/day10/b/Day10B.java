package day10.b;

import common.LoaderFactory;

import java.util.*;

public class Day10B {
    public static void main(String[] args) {
        List<String> inputLines = LoaderFactory.getLoaderFor("day10/input10.txt").loadInputs();
        List<Machine> machines = InputParser.parse(inputLines);
        MachineAnalyzer analyzer = new MachineAnalyzer();
        PuzzleSolver solver = new PuzzleSolver();
        long result = solver.solve(machines, analyzer);
        System.out.println("The fewest total button presses for joltage is: " + result);
    }

    public record Machine(List<Integer> targetJoltages, List<List<Integer>> buttonEffects) {}
    public static class InputParser {
        public static List<Machine> parse(List<String> lines) {
            List<Machine> machines = new ArrayList<>();
            for (String line : lines) {
                if (line.isBlank()) continue;
                List<Integer> targetJoltages = new ArrayList<>();
                List<List<Integer>> buttons = new ArrayList<>();
                String[] tokens = line.split("\\s+");
                for (String token : tokens) {
                    if (token.startsWith("{")) {
                        String reqs = token.substring(1, token.length() - 1);
                        for (String val : reqs.split(",")) {
                            targetJoltages.add(Integer.parseInt(val));
                        }
                    } else if (token.startsWith("(")) {
                        String btnStr = token.substring(1, token.length() - 1);
                        List<Integer> affectedCounters = new ArrayList<>();
                        if (!btnStr.isEmpty()) {
                            for (String numStr : btnStr.split(",")) {
                                affectedCounters.add(Integer.parseInt(numStr));
                            }
                        }
                        buttons.add(affectedCounters);
                    }
                }
                machines.add(new Machine(targetJoltages, buttons));
            }
            return machines;
        }
    }

    public static class MachineAnalyzer {
        private static final double EPSILON = 1e-9;

        public long getShortestPathToTarget(Machine machine) {
            List<Integer> objectives = machine.targetJoltages();
            List<List<Integer>> actions = machine.buttonEffects();
            int numRows = objectives.size();
            int numVars = actions.size();
            double[][] matrix = new double[numRows][numVars + 1];
            for (int j = 0; j < numVars; j++) {
                for (Integer rowIdx : actions.get(j)) {
                    matrix[rowIdx][j] = 1.0;
                }
            }
            for (int i = 0; i < numRows; i++) {
                matrix[i][numVars] = objectives.get(i);
            }
            solveGaussian(matrix, numRows, numVars);
            int[] pivotColForRow = new int[numRows];
            Arrays.fill(pivotColForRow, -1);
            boolean[] isFreeVar = new boolean[numVars];
            Arrays.fill(isFreeVar, true);
            for (int i = 0; i < numRows; i++) {
                for (int j = 0; j < numVars; j++) {
                    if (Math.abs(matrix[i][j]) > EPSILON) {
                        pivotColForRow[i] = j;
                        isFreeVar[j] = false;
                        break;
                    }
                }
            }
            List<Integer> freeVarIndices = new ArrayList<>();
            for (int j = 0; j < numVars; j++) {
                if (isFreeVar[j]) freeVarIndices.add(j);
            }
            double[] solution = new double[numVars];
            long result = solveFreeVarsRecursive(0, freeVarIndices, solution, matrix, pivotColForRow, 200);
            return result == Long.MAX_VALUE ? -1 : result;
        }

        private long solveFreeVarsRecursive(int idx, List<Integer> freeVars, double[] solution,
                                            double[][] matrix, int[] pivotColForRow, int limit) {
            if (idx == freeVars.size()) {
                return solveDependentVars(solution, matrix, pivotColForRow);
            }
            long minSteps = Long.MAX_VALUE;
            int varIndex = freeVars.get(idx);
            for (int val = 0; val <= limit; val++) {
                solution[varIndex] = val;
                long res = solveFreeVarsRecursive(idx + 1, freeVars, solution, matrix, pivotColForRow, limit);
                if (res != -1) {
                    minSteps = Math.min(minSteps, res);
                }
            }
            return minSteps;
        }

        private long solveDependentVars(double[] solution, double[][] matrix, int[] pivotColForRow) {
            int numRows = matrix.length;
            int numVars = solution.length;
            for (int i = numRows - 1; i >= 0; i--) {
                int pCol = pivotColForRow[i];
                if (pCol == -1) {
                    if (Math.abs(matrix[i][numVars]) > EPSILON) return Long.MAX_VALUE;
                    continue;
                }
                double sum = 0;
                for (int j = pCol + 1; j < numVars; j++) {
                    sum += matrix[i][j] * solution[j];
                }
                double rhs = matrix[i][numVars];
                double pivotVal = matrix[i][pCol];
                double valDependent = (rhs - sum) / pivotVal;
                if (valDependent < -EPSILON) return Long.MAX_VALUE;
                long rounded = Math.round(valDependent);
                if (Math.abs(valDependent - rounded) > EPSILON) return Long.MAX_VALUE;
                solution[pCol] = rounded;
            }
            long totalSteps = 0;
            for (double s : solution) totalSteps += (long) s;
            return totalSteps;
        }

        private void solveGaussian(double[][] M, int rows, int cols) {
            int pivotRow = 0;
            for (int col = 0; col < cols && pivotRow < rows; col++) {
                int maxRow = pivotRow;
                for (int i = pivotRow + 1; i < rows; i++) {
                    if (Math.abs(M[i][col]) > Math.abs(M[maxRow][col])) {
                        maxRow = i;
                    }
                }
                if (Math.abs(M[maxRow][col]) < EPSILON) continue;
                double[] temp = M[pivotRow];
                M[pivotRow] = M[maxRow];
                M[maxRow] = temp;
                double pivot = M[pivotRow][col];
                for (int j = col; j <= cols; j++) M[pivotRow][j] /= pivot;
                for (int i = 0; i < rows; i++) {
                    if (i != pivotRow) {
                        double factor = M[i][col];
                        for (int j = col; j <= cols; j++) M[i][j] -= factor * M[pivotRow][j];
                    }
                }
                pivotRow++;
            }
        }
    }

    public static class PuzzleSolver {
        public long solve(List<Machine> machines, MachineAnalyzer analyzer) {
            return machines.stream()
                    .mapToLong(analyzer::getShortestPathToTarget)
                    .sum();
        }
    }
}