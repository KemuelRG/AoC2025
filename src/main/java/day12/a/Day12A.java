package day12.a;

import common.LoaderFactory;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day12A {
    public static void main(String[] args) {
        List<String> input = LoaderFactory.getLoaderFor("day12/input12.txt").loadInputs();
        ParsingResult parsedData = InputParser.parse(input);
        PuzzleSolver solver = new PuzzleSolver();
        int solution = solver.solve(parsedData);
        System.out.println("Regions that can fit all presents: " + solution);
    }

    public record Point(int r, int c) implements Comparable<Point> {
        public Point rotate() { return new Point(c, -r); }
        public Point mirror() { return new Point(-r, c); }

        @Override
        public int compareTo(Point o) {
            if (this.r != o.r) return Integer.compare(this.r, o.r);
            return Integer.compare(this.c, o.c);
        }
    }

    public static class Shape {
        private final int id;
        private final int area;
        private final Set<List<Point>> orientations = new HashSet<>();

        public Shape(int id, List<String> gridLines) {
            this.id = id;
            List<Point> basePoints = new ArrayList<>();
            for (int r = 0; r < gridLines.size(); r++) {
                String line = gridLines.get(r);
                for (int c = 0; c < line.length(); c++) {
                    if (line.charAt(c) == '#') {
                        basePoints.add(new Point(r, c));
                    }
                }
            }
            this.area = basePoints.size();
            generateAllOrientations(basePoints);
        }

        private void generateAllOrientations(List<Point> base) {
            List<Point> current = base;
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 4; j++) {
                    orientations.add(normalize(current));
                    current = current.stream().map(Point::rotate).toList();
                }
                current = current.stream().map(Point::mirror).toList();
            }
        }

        private List<Point> normalize(List<Point> pts) {
            int minR = pts.stream().mapToInt(Point::r).min().orElse(0);
            int minC = pts.stream().mapToInt(Point::c).min().orElse(0);
            return pts.stream()
                    .map(p -> new Point(p.r() - minR, p.c() - minC))
                    .sorted()
                    .toList();
        }

        public int getArea() { return area; }
        public Set<List<Point>> getOrientations() { return orientations; }
    }

    public record PuzzlePiece(int instanceId, Shape shape) {}
    public record Region(int width, int height, List<PuzzlePiece> pieces) {}
    public record ParsingResult(List<Shape> shapes, List<Region> regions) {}

    public static class InputParser {
        public static ParsingResult parse(List<String> input) {
            List<Shape> shapes = new ArrayList<>();
            List<Region> regions = new ArrayList<>();
            Pattern shapePattern = Pattern.compile("^(\\d+):$");
            Pattern regionPattern = Pattern.compile("^(\\d+)x(\\d+): (.*)$");
            int i = 0;
            while (i < input.size()) {
                String line = input.get(i);
                if (line.isBlank()) { i++; continue; }
                Matcher shapeMatcher = shapePattern.matcher(line);
                if (shapeMatcher.matches()) {
                    int shapeId = Integer.parseInt(shapeMatcher.group(1));
                    List<String> gridLines = new ArrayList<>();
                    i++;
                    while (i < input.size() && !input.get(i).isBlank() && !input.get(i).contains("x")) {
                        gridLines.add(input.get(i));
                        i++;
                    }
                    shapes.add(new Shape(shapeId, gridLines));
                    continue;
                }
                Matcher regionMatcher = regionPattern.matcher(line);
                if (regionMatcher.matches()) {
                    int w = Integer.parseInt(regionMatcher.group(1));
                    int h = Integer.parseInt(regionMatcher.group(2));
                    String[] quantities = regionMatcher.group(3).trim().split("\\s+");
                    List<PuzzlePiece> piecesToPlace = new ArrayList<>();
                    int instanceCounter = 0;
                    for (int sId = 0; sId < quantities.length; sId++) {
                        int q = Integer.parseInt(quantities[sId]);
                        for (int k = 0; k < q; k++) {
                            piecesToPlace.add(new PuzzlePiece(instanceCounter++, shapes.get(sId)));
                        }
                    }
                    regions.add(new Region(w, h, piecesToPlace));
                }
                i++;
            }
            return new ParsingResult(shapes, regions);
        }
    }

    public static class DLX {
        static class Node {
            Node L, R, U, D;
            ColumnNode C;
        }

        static class ColumnNode extends Node {
            int size = 0;
            String name;
            public ColumnNode(String name) {
                this.name = name;
                L = R = U = D = this;
                C = this;
            }
        }

        private final ColumnNode root;

        public DLX(int numPrimaryCols, List<String> secondaryColNames) {
            root = new ColumnNode("ROOT");
            ColumnNode current = root;
            for (int i = 0; i < numPrimaryCols; i++) {
                ColumnNode col = new ColumnNode("P" + i);
                col.L = current;
                col.R = root;
                current.R = col;
                root.L = col;
                current = col;
            }
            for (String name : secondaryColNames) {
                ColumnNode col = new ColumnNode(name);
                secondaryColsMap.put(name, col);
            }
            current = (ColumnNode) root.R;
            int idx = 0;
            while (current != root) {
                primaryColsArray.add(current);
                current = (ColumnNode) current.R;
                idx++;
            }
        }

        private final List<ColumnNode> primaryColsArray = new ArrayList<>();
        private final Map<String, ColumnNode> secondaryColsMap = new HashMap<>();

        public void addRow(int primaryIdx, List<String> secondaryNames) {
            Node prev = null;
            prev = appendToColumn(primaryColsArray.get(primaryIdx), prev);
            for (String name : secondaryNames) {
                ColumnNode col = secondaryColsMap.get(name);
                if (col != null) {
                    prev = appendToColumn(col, prev);
                }
            }
        }

        private Node appendToColumn(ColumnNode col, Node prev) {
            Node node = new Node();
            node.C = col;
            node.D = col;
            node.U = col.U;
            col.U.D = node;
            col.U = node;
            col.size++;
            if (prev == null) {
                node.L = node.R = node;
            } else {
                node.L = prev;
                node.R = prev.R;
                prev.R.L = node;
                prev.R = node;
            }
            return node;
        }

        private void cover(ColumnNode c) {
            c.R.L = c.L;
            c.L.R = c.R;
            for (Node i = c.D; i != c; i = i.D) {
                for (Node j = i.R; j != i; j = j.R) {
                    j.D.U = j.U;
                    j.U.D = j.D;
                    j.C.size--;
                }
            }
        }

        private void uncover(ColumnNode c) {
            for (Node i = c.U; i != c; i = i.U) {
                for (Node j = i.L; j != i; j = j.L) {
                    j.C.size++;
                    j.D.U = j;
                    j.U.D = j;
                }
            }
            c.R.L = c;
            c.L.R = c;
        }

        public boolean search() {
            if (root.R == root) return true;
            ColumnNode c = (ColumnNode) root.R;
            int minSize = c.size;
            for (ColumnNode j = (ColumnNode) c.R; j != root; j = (ColumnNode) j.R) {
                if (j.size < minSize) {
                    minSize = j.size;
                    c = j;
                }
            }
            if (minSize == 0) return false;
            cover(c);
            for (Node r = c.D; r != c; r = r.D) {
                for (Node j = r.R; j != r; j = j.R) cover(j.C);
                if (search()) return true;
                for (Node j = r.L; j != r; j = j.L) uncover(j.C);
            }
            uncover(c);
            return false;
        }
    }

    public static class PuzzleSolver {
        public int solve(ParsingResult parsedData) {
            int validRegions = 0;
            for (Region region : parsedData.regions()) {
                int gridArea = region.width() * region.height();
                int piecesArea = region.pieces().stream().mapToInt(p -> p.shape().getArea()).sum();
                if (piecesArea > gridArea) {
                    continue;
                }
                List<String> cellNames = new ArrayList<>();
                for (int r = 0; r < region.height(); r++) {
                    for (int c = 0; c < region.width(); c++) {
                        cellNames.add(r + "," + c);
                    }
                }
                DLX dlx = new DLX(region.pieces().size(), cellNames);
                for (int i = 0; i < region.pieces().size(); i++) {
                    PuzzlePiece piece = region.pieces().get(i);
                    for (List<Point> orientation : piece.shape().getOrientations()) {
                        int maxR = orientation.stream().mapToInt(Point::r).max().orElse(0);
                        int maxC = orientation.stream().mapToInt(Point::c).max().orElse(0);
                        for (int rOffset = 0; rOffset < region.height() - maxR; rOffset++) {
                            for (int cOffset = 0; cOffset < region.width() - maxC; cOffset++) {
                                List<String> coveredCells = new ArrayList<>();
                                for (Point p : orientation) {
                                    coveredCells.add((p.r() + rOffset) + "," + (p.c() + cOffset));
                                }
                                dlx.addRow(i, coveredCells);
                            }
                        }
                    }
                }
                if (dlx.search()) {
                    validRegions++;
                }
            }
            return validRegions;
        }
    }
}