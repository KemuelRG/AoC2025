package src.java.day01.b;

import src.java.common.LoaderFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntConsumer;

public class Day01B {

    public static void main(String[] args) {
        List<String> input = LoaderFactory.getLoaderFor("day01/input01.txt").loadInputs();
        SafeDial dial = new SafeDial();
        PuzzleSolver solver = new PuzzleSolver();
        dial.addObserver(solver);
        RotationInvoker invoker = new RotationInvoker();
        SafeOpener opener = new SafeOpener(dial, invoker, solver);

        int solution = opener.calculatePassword(input);
        System.out.println("The solution is: " + solution);
    }

    public static class SafeDial {
        private int position = 50;
        private final List<IntConsumer> observers = new ArrayList<>();

        public void addObserver(IntConsumer observer) {
            this.observers.add(observer);
        }

        private void notifyObservers(int laps) {
            observers.forEach(observer -> observer.accept(laps));
        }

        public void rotateRight(int numberOfPositions) {
            int completeLaps = numberOfPositions / 100;
            int mod = numberOfPositions % 100;
            int finalPosition = position + mod;

            if (finalPosition >= 100) {
                finalPosition -= 100;
                completeLaps++;
            }

            position = finalPosition;
            notifyObservers(completeLaps);
        }

        public void rotateLeft(int numberOfPositions) {
            int completeLaps = numberOfPositions / 100;
            int mod = numberOfPositions % 100;
            int finalPosition = position - mod;

            if (finalPosition < 0) {
                finalPosition += 100;
                if (position != 0) completeLaps++;
            } else if (finalPosition == 0 && position != 0) {
                completeLaps++;
            }

            position = finalPosition;
            notifyObservers(completeLaps);
        }
    }

    public static class PuzzleSolver implements IntConsumer {
        private int password = 0;

        @Override
        public void accept(int laps) {
            password += laps;
        }

        public int getSolution() {
            return password;
        }
    }

    public interface Command {
        void execute();
    }

    public static class InstructionParser {
        public static Command parse(String s, SafeDial dial) {
            String direction = s.substring(0, 1);
            int numberOfPositions = Integer.parseInt(s.substring(1));

            return switch (direction) {
                case "L" -> () -> dial.rotateLeft(numberOfPositions);
                case "R" -> () -> dial.rotateRight(numberOfPositions);
                default -> throw new IllegalArgumentException("Invalid direction: " + direction);
            };
        }
    }

    public static class RotationInvoker {
        private final List<Command> commands = new ArrayList<>();

        public void takeRotation(Command rotation) {
            commands.add(rotation);
        }

        public void placeRotations() {
            commands.forEach(Command::execute);
        }
    }

    public static class SafeOpener {
        private final SafeDial dial;
        private final RotationInvoker rotationInvoker;
        private final PuzzleSolver puzzleSolver;

        public SafeOpener(SafeDial dial, RotationInvoker rotationInvoker, PuzzleSolver puzzleSolver) {
            this.dial = dial;
            this.rotationInvoker = rotationInvoker;
            this.puzzleSolver = puzzleSolver;
        }

        public int calculatePassword(List<String> rotations) {
            rotations.stream()
                    .map(r -> InstructionParser.parse(r, dial))
                    .forEach(rotationInvoker::takeRotation);
            rotationInvoker.placeRotations();
            return puzzleSolver.getSolution();
        }
    }
}