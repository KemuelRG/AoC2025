package src.java.day07.b;

import src.java.common.LoaderFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Day07B {
    public static void main(String[] args) {
        List<String> input = LoaderFactory.getLoaderFor("day07/input07.txt").loadInputs();
        String[][] matrix = input.stream()
                .map(s -> s.split(""))
                .toArray(String[][]::new);
        TachyonDiagram diagram = new TachyonDiagram(matrix);
        PuzzleSolver solver = new PuzzleSolver();
        long result = solver.solve(diagram);
        System.out.println("Total active timelines: " + result);
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

        public int getWidth() { return matrix[0].length; }
        public int getHeight() { return matrix.length; }

        public String getValueInPosition(int row, int column) {
            return matrix[row][column];
        }
    }

    public static class Node {
        private final Coordinate position;
        private Node leftChild;
        private Node rightChild;

        private Node(Coordinate position) {
            this.position = position;
        }

        public static Node of(Coordinate position) {
            return new Node(position);
        }

        public Coordinate getPosition() { return position; }
        public Node getLeftChild() { return leftChild; }
        public void setLeftChild(Node leftChild) { this.leftChild = leftChild; }
        public Node getRightChild() { return rightChild; }
        public void setRightChild(Node rightChild) { this.rightChild = rightChild; }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return Objects.equals(position, node.position);
        }

        @Override
        public int hashCode() {
            return Objects.hash(position);
        }
    }

    public static class Graph {
        private final List<Node> nodes;
        private final Map<Node, Long> memo = new HashMap<>();

        public Graph(List<Node> nodes) {
            this.nodes = nodes;
        }

        public long getNumberOfPathsTaken() {
            if (nodes.isEmpty()) return 0;
            return countTimelines(nodes.get(0));
        }

        private long countTimelines(Node current) {
            if (memo.containsKey(current)) {
                return memo.get(current);
            }
            long count = 0;
            if (current.getLeftChild() != null) {
                count += countTimelines(current.getLeftChild());
            } else {
                count++;
            }
            if (current.getRightChild() != null) {
                count += countTimelines(current.getRightChild());
            } else {
                count++;
            }
            memo.put(current, count);
            return count;
        }
    }

    public static class TachyonDiagramAnalyzer {
        private TachyonDiagram diagram;
        private List<Node> nodeList;

        public long findAllTimelines(TachyonDiagram diagram) {
            this.diagram = diagram;
            this.nodeList = new ArrayList<>();
            buildGraph();
            Graph graph = new Graph(nodeList);
            return graph.getNumberOfPathsTaken();
        }

        private void buildGraph() {
            Coordinate startPos = findSource();
            if (startPos == null) return;
            Node rootNode = null;
            for (int row = startPos.row() + 1; row < diagram.getHeight(); row++) {
                if (diagram.getValueInPosition(row, startPos.col()).equals("^")) {
                    rootNode = Node.of(Coordinate.of(row, startPos.col()));
                    nodeList.add(rootNode);
                    break;
                }
            }
            if (rootNode != null) {
                recursiveGraphBuild(rootNode);
            }
        }

        private Coordinate findSource() {
            for (int r = 0; r < diagram.getHeight(); r++) {
                for (int c = 0; c < diagram.getWidth(); c++) {
                    if (diagram.getValueInPosition(r, c).equals("S")) {
                        return Coordinate.of(r, c);
                    }
                }
            }
            return null;
        }

        private void recursiveGraphBuild(Node node) {
            int leftCol = node.getPosition().col() - 1;
            int rightCol = node.getPosition().col() + 1;
            for (int row = node.getPosition().row() + 1; row < diagram.getHeight(); row++) {
                if (diagram.getValueInPosition(row, leftCol).equals("^")) {
                    Coordinate hitCoords = Coordinate.of(row, leftCol);
                    Node target = linkOrMakeNode(hitCoords);
                    node.setLeftChild(target);
                    break;
                }
            }
            for (int row = node.getPosition().row() + 1; row < diagram.getHeight(); row++) {
                if (diagram.getValueInPosition(row, rightCol).equals("^")) {
                    Coordinate hitCoords = Coordinate.of(row, rightCol);
                    Node target = linkOrMakeNode(hitCoords);
                    node.setRightChild(target);
                    break;
                }
            }
        }

        private Node linkOrMakeNode(Coordinate coords) {
            for (Node existing : nodeList) {
                if (existing.getPosition().equals(coords)) {
                    return existing;
                }
            }
            Node newNode = Node.of(coords);
            nodeList.add(newNode);
            recursiveGraphBuild(newNode);
            return newNode;
        }
    }

    public static class PuzzleSolver {
        public long solve(TachyonDiagram diagram) {
            TachyonDiagramAnalyzer analyzer = new TachyonDiagramAnalyzer();
            return analyzer.findAllTimelines(diagram);
        }
    }
}