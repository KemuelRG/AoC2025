package day08.a;

import common.LoaderFactory;

import java.util.*;

public class Day08A {
    public static void main(String[] args) {
        List<String> inputLines = LoaderFactory.getLoaderFor("day08/input08.txt").loadInputs();
        List<JunctionBox> boxes = new ArrayList<>();
        long idCounter = 0;
        for (String line : inputLines) {
            String[] parts = line.split(",");
            Coordinates coords = new Coordinates(
                    Long.parseLong(parts[0]),
                    Long.parseLong(parts[1]),
                    Long.parseLong(parts[2])
            );
            boxes.add(new JunctionBox(idCounter++, coords));
        }
        CircuitManager manager = new CircuitManager(boxes);
        PuzzleSolver solver = new PuzzleSolver();
        long result = solver.solve(manager);
        System.out.println("The product of the 3 largest circuits is: " + result);
    }

    public record Coordinates(long x, long y, long z) {
        public double euclideanDistanceTo(Coordinates other) {
            return Math.sqrt(Math.pow(this.x - other.x, 2) +
                    Math.pow(this.y - other.y, 2) +
                    Math.pow(this.z - other.z, 2));
        }
    }

    public record Connection(long sourceId, long destinyId, double distance) {}

    public static class JunctionBox {
        private final long id;
        private final Coordinates coordinates;

        public JunctionBox(long id, Coordinates coordinates) {
            this.id = id;
            this.coordinates = coordinates;
        }

        public long getId() { return id; }
        public Coordinates getCoordinates() { return coordinates; }
    }

    public static class Circuit {
        private final List<JunctionBox> boxes = new ArrayList<>();

        public Circuit(JunctionBox initialBox) {
            boxes.add(initialBox);
        }

        public void merge(Circuit other) {
            this.boxes.addAll(other.getBoxes());
            other.clear();
        }

        public List<JunctionBox> getBoxes() { return boxes; }
        public void clear() { boxes.clear(); }
        public int size() { return boxes.size(); }
    }

    public static class CircuitManager {
        private final List<JunctionBox> boxes;
        private final List<Connection> connections = new ArrayList<>();
        private final Map<Long, Circuit> boxToCircuitMap = new HashMap<>();
        private final List<Circuit> activeCircuits = new ArrayList<>();

        private int connectionIndex = 0;

        public CircuitManager(List<JunctionBox> boxes) {
            this.boxes = boxes;
            initializeCircuits();
            generateAndSortConnections();
        }

        private void initializeCircuits() {
            for (JunctionBox box : boxes) {
                Circuit circuit = new Circuit(box);
                activeCircuits.add(circuit);
                boxToCircuitMap.put(box.getId(), circuit);
            }
        }

        private void generateAndSortConnections() {
            for (int i = 0; i < boxes.size(); i++) {
                for (int j = i + 1; j < boxes.size(); j++) {
                    JunctionBox b1 = boxes.get(i);
                    JunctionBox b2 = boxes.get(j);
                    double dist = b1.getCoordinates().euclideanDistanceTo(b2.getCoordinates());
                    connections.add(new Connection(b1.getId(), b2.getId(), dist));
                }
            }
            connections.sort(Comparator.comparingDouble(Connection::distance));
        }

        public void connectOneCircuit() {
            if (connectionIndex >= connections.size()) return;
            Connection shortest = connections.get(connectionIndex++);
            Circuit c1 = boxToCircuitMap.get(shortest.sourceId());
            Circuit c2 = boxToCircuitMap.get(shortest.destinyId());
            if (c1 != c2) {
                for (JunctionBox box : c2.getBoxes()) {
                    boxToCircuitMap.put(box.getId(), c1);
                }
                c1.merge(c2);
                activeCircuits.remove(c2);
            }
        }

        public List<Circuit> getActiveCircuits() {
            return activeCircuits;
        }
    }

    public static class PuzzleSolver {
        public long solve(CircuitManager manager) {
            for (int i = 0; i < 1000; i++) {
                manager.connectOneCircuit();
            }
            return manager.getActiveCircuits().stream()
                    .sorted(Comparator.comparingInt(Circuit::size).reversed())
                    .limit(3)
                    .mapToLong(Circuit::size)
                    .reduce(1L, (a, b) -> a * b);
        }
    }
}