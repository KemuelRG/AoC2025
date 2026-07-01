package day10.a;

import common.LoaderFactory;

import java.util.*;

public class Day10A {
    public static void main(String[] args) {
        List<String> inputLines = LoaderFactory.getLoaderFor("day10/input10.txt").loadInputs();
        List<Machine> machines = InputParser.parse(inputLines);
        MachineAnalyzer analyzer = new MachineAnalyzer();
        PuzzleSolver solver = new PuzzleSolver();
        long result = solver.solve(machines, analyzer);
        System.out.println("The fewest total button presses required is: " + result);
    }

    public record Machine(int targetMask, List<Integer> buttonMasks) {}

    public static class InputParser {
        public static List<Machine> parse(List<String> lines) {
            List<Machine> machines = new ArrayList<>();
            for (String line : lines) {
                if (line.isBlank()) continue;
                int targetMask = 0;
                List<Integer> buttons = new ArrayList<>();
                String[] tokens = line.split("\\s+");
                for (String token : tokens) {
                    if (token.startsWith("[")) {
                        String lights = token.substring(1, token.length() - 1);
                        for (int i = 0; i < lights.length(); i++) {
                            if (lights.charAt(i) == '#') {
                                targetMask |= (1 << i);
                            }
                        }
                    } else if (token.startsWith("(")) {
                        String btnStr = token.substring(1, token.length() - 1);
                        int btnMask = 0;
                        if (!btnStr.isEmpty()) {
                            for (String numStr : btnStr.split(",")) {
                                int lightIndex = Integer.parseInt(numStr);
                                btnMask |= (1 << lightIndex);
                            }
                        }
                        buttons.add(btnMask);
                    }
                }
                machines.add(new Machine(targetMask, buttons));
            }
            return machines;
        }
    }

    public static class MachineAnalyzer {
        public long getShortestPathToTarget(Machine machine) {
            Map<Integer, Integer> visitedStates = new HashMap<>();
            Queue<Integer> queue = new LinkedList<>();
            int initialState = 0;
            queue.add(initialState);
            visitedStates.put(initialState, 0);
            while (!queue.isEmpty()) {
                int currentState = queue.poll();
                int currentPresses = visitedStates.get(currentState);
                if (currentState == machine.targetMask()) {
                    return currentPresses;
                }
                for (int buttonMask : machine.buttonMasks()) {
                    int nextState = currentState ^ buttonMask;
                    if (!visitedStates.containsKey(nextState)) {
                        visitedStates.put(nextState, currentPresses + 1);
                        queue.add(nextState);
                    }
                }
            }
            return -1;
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