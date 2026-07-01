package day07.a;

import common.LoaderFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day07A {
    public static void main(String[] args) {
        List<String> input = LoaderFactory.getLoaderFor("day07/input07.txt").loadInputs();
        String[][] matrix = input.stream()
                .map(s -> s.split(""))
                .toArray(String[][]::new);
        TachyonDiagram diagram = new TachyonDiagram(matrix);
        PuzzleSolver solver = new PuzzleSolver();
        long result = solver.solve(diagram);
        System.out.println("The beam will be split " + result + " times.");
    }

    public record Coordinate(int row, int col) {
        public static Coordinate of(int row, int col) {
            return new Coordinate(row, col);
        }
    }

    public static class TachyonDiagram {
        private final String[][] matrix;

        public TachyonDiagram(String[][] matrix) {
            this.matrix = matrix;
        }

        public int getWidth() {
            return matrix[0].length;
        }

        public int getHeight() {
            return matrix.length;
        }

        public String getValueInPosition(int row, int column) {
            return matrix[row][column];
        }
    }

    public static class TachyonDiagramAnalyzer {
        private Set<Coordinate> setOfBeamsPositions;
        private final TachyonDiagram diagram;

        public TachyonDiagramAnalyzer(TachyonDiagram diagram) {
            this.diagram = diagram;
        }

        public long countBeamsDivision() {
            setOfBeamsPositions = new HashSet<>();
            int totalTimesBeamIsDivided = 0;
            for (int row = 0; row < diagram.getHeight(); row++) {
                for (int col = 0; col < diagram.getWidth(); col++) {
                    Coordinate currentPos = Coordinate.of(row, col);
                    if (diagram.getValueInPosition(row, col).equals("S")) {
                        setOfBeamsPositions.add(currentPos);
                    }
                    continueBeamFallIfPossible(currentPos);
                    if (beamShouldBeDivided(currentPos)) {
                        totalTimesBeamIsDivided++;
                        divideBeam(currentPos);
                    }
                }
            }
            return totalTimesBeamIsDivided;
        }

        private void continueBeamFallIfPossible(Coordinate pos) {
            if (pos.row() > 0) {
                Coordinate above = Coordinate.of(pos.row() - 1, pos.col());
                if (diagram.getValueInPosition(pos.row(), pos.col()).equals(".")
                        && setOfBeamsPositions.contains(above)) {
                    setOfBeamsPositions.add(pos);
                }
            }
        }

        private boolean beamShouldBeDivided(Coordinate pos) {
            if (pos.row() > 0) {
                Coordinate above = Coordinate.of(pos.row() - 1, pos.col());
                return setOfBeamsPositions.contains(above)
                        && diagram.getValueInPosition(pos.row(), pos.col()).equals("^");
            }
            return false;
        }

        private void divideBeam(Coordinate pos) {
            setOfBeamsPositions.add(Coordinate.of(pos.row(), pos.col() - 1));
            setOfBeamsPositions.add(Coordinate.of(pos.row(), pos.col() + 1));
        }
    }

    public static class PuzzleSolver {
        public long solve(TachyonDiagram diagram) {
            TachyonDiagramAnalyzer analyzer = new TachyonDiagramAnalyzer(diagram);
            return analyzer.countBeamsDivision();
        }
    }
}