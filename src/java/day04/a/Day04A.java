package src.java.day04.a;

import src.java.common.LoaderFactory;
import java.util.List;

public class Day04A {
    public static void main(String[] args) {
        List<String> input = LoaderFactory.getLoaderFor("day04/input04.txt").loadInputs();
        InventoryMatrix matrix = new InventoryMatrixBuilder().from(input).build();
        MatrixAnalyzer analyzer = new MatrixAnalyzer(matrix);
        PuzzleSolver solver = new PuzzleSolver();
        int solution = solver.solve(matrix, analyzer);
        System.out.println("Accessible paper rolls: " + solution);
    }

    public record Coordinate(int row, int col) {
        public static Coordinate of(int row, int col) {
            return new Coordinate(row, col);
        }
    }

    public record InventoryMatrix(int[][] grid) {
        public int getRows() {
            return grid.length;
        }

        public int getCols() {
            return grid[0].length;
        }

        public int getValue(Coordinate coordinate) {
            return grid[coordinate.row()][coordinate.col()];
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
                throw new IllegalArgumentException("No data provided to build the matrix");
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
        private final InventoryMatrix matrix;

        public MatrixAnalyzer(InventoryMatrix matrix) {
            this.matrix = matrix;
        }

        public int getNumberOfItemsSurrounding(Coordinate position) {
            int count = 0;
            int rows = matrix.getRows();
            int cols = matrix.getCols();
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (i == 0 && j == 0) continue;
                    int neighborRow = position.row() + i;
                    int neighborCol = position.col() + j;
                    if (neighborRow >= 0 && neighborRow < rows && neighborCol >= 0 && neighborCol < cols) {
                        count += matrix.getValue(Coordinate.of(neighborRow, neighborCol));
                    }
                }
            }
            return count;
        }
    }

    public static class PuzzleSolver {
        public int solve(InventoryMatrix matrix, MatrixAnalyzer analyzer) {
            int accessibleRolls = 0;
            for (int r = 0; r < matrix.getRows(); r++) {
                for (int c = 0; c < matrix.getCols(); c++) {
                    Coordinate current = Coordinate.of(r, c);
                    if (matrix.getValue(current) == 1) {
                        if (analyzer.getNumberOfItemsSurrounding(current) < 4) {
                            accessibleRolls++;
                        }
                    }
                }
            }
            return accessibleRolls;
        }
    }
}