package src.java.day04.b;

import src.java.common.LoaderFactory;

import java.util.ArrayList;
import java.util.List;

public class Day04B {
    public static void main(String[] args) {
        List<String> input = LoaderFactory.getLoaderFor("day04/input04.txt").loadInputs();
        InventoryMatrix initialMatrix = new InventoryMatrixBuilder().from(input).build();
        PuzzleSolver solver = new PuzzleSolver();
        int solution = solver.solve(initialMatrix);
        System.out.println("Total accessible paper rolls removed: " + solution);
    }

    public record Coordinate(int row, int col) {
        public static Coordinate of(int row, int col) {
            return new Coordinate(row, col);
        }
    }

    public static class InventoryMatrix {
        private final int[][] grid;

        public InventoryMatrix(int[][] grid) {
            this.grid = grid;
        }

        public int[][] getGridCopy() {
            int[][] clone = new int[grid.length][grid.length];
            for (int r = 0; r < grid.length; r++) {
                System.arraycopy(grid[r], 0, clone[r], 0, grid[r].length);
            }
            return clone;
        }
    }

    public static class MutableInventory {
        private final int[][] grid;

        public MutableInventory(InventoryMatrix matrix) {
            this.grid = matrix.getGridCopy();
        }

        public int getRows() { return grid.length; }
        public int getCols() { return grid[0].length; }

        public int getValue(Coordinate coordinate) {
            return grid[coordinate.row()][coordinate.col()];
        }

        public void removeRoll(Coordinate coordinate) {
            grid[coordinate.row()][coordinate.col()] = 0;
        }
    }

    public static class InventoryMatrixBuilder {
        private List<String> data;

        public InventoryMatrixBuilder from(List<String> data) {
            this.data = data;
            return this;
        }

        public InventoryMatrix build() {
            if (data == null || data.isEmpty()) {
                throw new IllegalArgumentException("No data provided");
            }
            int rows = data.size();
            int cols = data.get(0).length();
            int[][] grid = new int[rows][cols];
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    grid[r][c] = data.get(r).charAt(c) == '@' ? 1 : 0;
                }
            }
            return new InventoryMatrix(grid);
        }
    }

    public static class MatrixAnalyzer {
        private final MutableInventory matrix;

        public MatrixAnalyzer(MutableInventory matrix) {
            this.matrix = matrix;
        }

        public int getNumberOfItemsSurrounding(Coordinate position) {
            int count = 0;
            int rows = matrix.getRows();
            int cols = matrix.getCols();
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (i == 0 && j == 0) continue;
                    int nRow = position.row() + i;
                    int nCol = position.col() + j;
                    if (nRow >= 0 && nRow < rows && nCol >= 0 && nCol < cols) {
                        count += matrix.getValue(Coordinate.of(nRow, nCol));
                    }
                }
            }
            return count;
        }
    }

    public static class PuzzleSolver {
        public int solve(InventoryMatrix initialMatrix) {
            MutableInventory mutableInventory = new MutableInventory(initialMatrix);
            MatrixAnalyzer analyzer = new MatrixAnalyzer(mutableInventory);
            int totalRemoved = 0;
            while (true) {
                List<Coordinate> toRemoveThisStep = new ArrayList<>();
                for (int r = 0; r < mutableInventory.getRows(); r++) {
                    for (int c = 0; c < mutableInventory.getCols(); c++) {
                        Coordinate current = Coordinate.of(r, c);
                        if (mutableInventory.getValue(current) == 1) {
                            if (analyzer.getNumberOfItemsSurrounding(current) < 4) {
                                toRemoveThisStep.add(current);
                            }
                        }
                    }
                }
                if (toRemoveThisStep.isEmpty()) {
                    break;
                }
                for (Coordinate coord : toRemoveThisStep) {
                    mutableInventory.removeRoll(coord);
                }
                totalRemoved += toRemoveThisStep.size();
            }
            return totalRemoved;
        }
    }
}