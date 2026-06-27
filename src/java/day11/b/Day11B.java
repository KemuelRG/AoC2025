package src.java.day11.b;

import src.java.common.LoaderFactory;
import java.util.*;

public class Day11B {
    public static void main(String[] args) {
        List<String> input = LoaderFactory.getLoaderFor("day11/input11.txt").loadInputs();
        Graph graph = InputParser.parse(input);
        PuzzleSolver solver = new PuzzleSolver();
        long result = solver.solve(graph);
        System.out.println("Total paths visiting both dac and fft: " + result);
    }

    public record Node(String code) {}

    public static class Graph {
        private final Map<Node, List<Node>> adjacencyMap;
        private final Map<Node, Long> memo;

        public Graph(Map<Node, List<Node>> adjacencyMap) {
            this.adjacencyMap = adjacencyMap;
            this.memo = new HashMap<>();
        }

        public long getPaths(Node start, Node target) {
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
                    for (String code : parts[1].trim().split("\\s+")) {
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
            Node svr = new Node("svr");
            Node out = new Node("out");
            Node dac = new Node("dac");
            Node fft = new Node("fft");
            long pathsViaDacThenFft = graph.getPaths(svr, dac) *
                    graph.getPaths(dac, fft) *
                    graph.getPaths(fft, out);
            long pathsViaFftThenDac = graph.getPaths(svr, fft) *
                    graph.getPaths(fft, dac) *
                    graph.getPaths(dac, out);
            return pathsViaDacThenFft + pathsViaFftThenDac;
        }
    }
}