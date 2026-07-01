package day09.b;

import common.LoaderFactory;

import java.util.ArrayList;
import java.util.List;

public class Day09B {
    public static void main(String[] args) {
        List<String> inputLines = LoaderFactory.getLoaderFor("day09/input09.txt").loadInputs();
        List<Coordinate> redTiles = InputParser.parse(inputLines);
        Polygon tilePattern = new Polygon(redTiles);
        PuzzleSolver solver = new PuzzleSolver();
        long maxArea = solver.solve(tilePattern);
        System.out.println("The maximum area of any valid rectangle is: " + maxArea);
    }

    public record Coordinate(long x, long y) {
        public static Coordinate of(long x, long y) {
            return new Coordinate(x, y);
        }

        public long areaWith(Coordinate other) {
            return (Math.abs(this.x - other.x) + 1) * (Math.abs(this.y - other.y) + 1);
        }
    }

    public record Edge(Coordinate coord1, Coordinate coord2) {
        public boolean isVertical() {
            return coord1.x() == coord2.x();
        }
    }

    public static class Polygon {
        private final List<Coordinate> vertices;
        private final List<Edge> edges;

        public Polygon(List<Coordinate> vertices) {
            this.vertices = vertices;
            this.edges = buildEdges(vertices);
        }

        private List<Edge> buildEdges(List<Coordinate> vertices) {
            List<Edge> edgesList = new ArrayList<>();
            int n = vertices.size();
            for (int i = 0; i < n; i++) {
                Coordinate c1 = vertices.get(i);
                Coordinate c2 = vertices.get((i + 1) % n);
                edgesList.add(new Edge(c1, c2));
            }
            return edgesList;
        }

        public List<Coordinate> getVertices() {
            return vertices;
        }

        public boolean containsRectangle(Coordinate c1, Coordinate c2) {
            if (edgeIntersectsRectangle(c1, c2)) {
                return false;
            }
            long minX = Math.min(c1.x(), c2.x());
            long maxX = Math.max(c1.x(), c2.x());
            long minY = Math.min(c1.y(), c2.y());
            long maxY = Math.max(c1.y(), c2.y());
            double midX = (minX + maxX) / 2.0 + 0.001;
            double midY = (minY + maxY) / 2.0 + 0.001;
            return isPointInsideGreenZone(midX, midY);
        }

        private boolean edgeIntersectsRectangle(Coordinate c1, Coordinate c2) {
            long minX = Math.min(c1.x(), c2.x());
            long maxX = Math.max(c1.x(), c2.x());
            long minY = Math.min(c1.y(), c2.y());
            long maxY = Math.max(c1.y(), c2.y());
            for (Edge edge : edges) {
                if (edge.isVertical()) {
                    if (edge.coord1().x() > minX && edge.coord1().x() < maxX) {
                        long edgeMinY = Math.min(edge.coord1().y(), edge.coord2().y());
                        long edgeMaxY = Math.max(edge.coord1().y(), edge.coord2().y());
                        if (edgeMinY < maxY && edgeMaxY > minY) return true;
                    }
                } else {
                    if (edge.coord1().y() > minY && edge.coord1().y() < maxY) {
                        long edgeMinX = Math.min(edge.coord1().x(), edge.coord2().x());
                        long edgeMaxX = Math.max(edge.coord1().x(), edge.coord2().x());
                        if (edgeMinX < maxX && edgeMaxX > minX) return true;
                    }
                }
            }
            return false;
        }

        private boolean isPointInsideGreenZone(double px, double py) {
            int intersections = 0;
            for (Edge edge : edges) {
                if (edge.isVertical()) {
                    if (edge.coord1().x() > px) {
                        long edgeMinY = Math.min(edge.coord1().y(), edge.coord2().y());
                        long edgeMaxY = Math.max(edge.coord1().y(), edge.coord2().y());
                        if (py > edgeMinY && py < edgeMaxY) intersections++;
                    }
                }
            }
            return (intersections % 2 != 0);
        }
    }

    public static class InputParser {
        public static List<Coordinate> parse(List<String> input) {
            return input.stream()
                    .map(line -> {
                        String[] parts = line.split(",");
                        return Coordinate.of(Long.parseLong(parts[0]), Long.parseLong(parts[1]));
                    })
                    .toList();
        }
    }

    public static class PuzzleSolver {
        public long solve(Polygon polygon) {
            long maxArea = 0;
            List<Coordinate> redTiles = polygon.getVertices();
            int n = redTiles.size();
            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    Coordinate coord1 = redTiles.get(i);
                    Coordinate coord2 = redTiles.get(j);
                    long currentArea = coord1.areaWith(coord2);
                    if (currentArea <= maxArea) continue;
                    if (polygon.containsRectangle(coord1, coord2)) {
                        maxArea = currentArea;
                    }
                }
            }
            return maxArea;
        }
    }
}