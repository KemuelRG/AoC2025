package src.java.day11.a;

import src.java.common.LoaderFactory;
import java.util.*;

public class Day11A {
    public static void main(String[] args) {
        List<String> input = LoaderFactory.getLoaderFor("day11/input11.txt").loadInputs();
        Graph graph = InputParser.parse(input);
        PuzzleSolver solver = new PuzzleSolver();
        long result = solver.solve(graph);
        System.out.println("Total different paths from 'you' to 'out': " + result);
    }

    public record Node(String code) {}

    public static class Graph {
        private final Map<Node, List<Node>> adjacencyMap;
        private final Map<Node, Long> memo;

        public Graph(Map<Node, List<Node>> adjacencyMap) {
            this.adjacencyMap = adjacencyMap;
            this.memo = new HashMap<>();
        }

        public long getNumberOfPossiblePaths(Node start, Node target) {
            memo.clear();
            return dfs(start, target);
        }

        private long dfs(Node current, Node target) {
            if (current.equals(target)) {
                return 1L;
            }
            if (memo.containsKey(current)) {
                return memo.get(current);
            }
            long count = 0;
            List<Node> neighbors = adjacencyMap.getOrDefault(current, Collections.emptyList());
            for (Node neighbor : neighbors) {
                count += dfs(neighbor, target);
            }
            memo.put(current, count);
            return count;
        }
    }

    public static class InputParser {
        public static Graph parse(List<String> input) {
            Map<Node, List<Node>> adjacencyMap = new HashMap<>();
            for (String line : input) {
                if (line.isBlank()) continue;
                String[] parts = line.split(":");
                Node current = new Node(parts[0].trim());
                List<Node> neighbors = new ArrayList<>();
                if (parts.length > 1 && !parts[1].trim().isEmpty()) {
                    String[] neighborCodes = parts[1].trim().split("\\s+");
                    for (String code : neighborCodes) {
                        neighbors.add(new Node(code));
                    }
                }
                adjacencyMap.put(current, neighbors);
            }
            return new Graph(adjacencyMap);
        }
    }

    public static class PuzzleSolver {
        public long solve(Graph graph) {
            Node startNode = new Node("you");
            Node targetNode = new Node("out");
            return graph.getNumberOfPossiblePaths(startNode, targetNode);
        }
    }
}