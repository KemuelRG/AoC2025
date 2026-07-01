package day01.a;

import common.LoaderFactory;
import java.util.ArrayList;
import java.util.List;

public class Day01A {
    public static void main(String[] args) {
        List<String> input = LoaderFactory.getLoaderFor("day01/input01.txt").loadInputs();
        SafeDial dial = new SafeDial();
        RotationInvoker invoker = new RotationInvoker();
        SafeOpener opener = new SafeOpener(dial, invoker);

        int solution = opener.calculatePassword(input);
        System.out.println("The password is: " + solution);
    }

    public static class SafeDial {
        private int position = 50;
        private int password = 0;
        public void rotateRigth(int numberOfPositions){
            position += numberOfPositions;
            recalculatePosition();
        }

        public void rotateLeft(int numberOfPositions){
            position -= numberOfPositions;
            recalculatePosition();
        }
        private void recalculatePosition() {
            position = ((position % 100) + 100) % 100;
            checkZero();
        }

        private void checkZero() {
            if(position == 0) password++;
        }
        public int getPassword(){
            return password;
        }
    }

    public interface Command{
        void execute();
    }

    public static class InstructionParser {
        public static Command parse(String s, SafeDial dial) {
            String direction = s.substring(0, 1);
            int numberOfPositions = Integer.parseInt(s.substring(1));
            return switch (direction) {
                case "L" -> () -> dial.rotateLeft(numberOfPositions);
                case "R" -> () -> dial.rotateRigth(numberOfPositions);
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

        public SafeOpener(SafeDial dial, RotationInvoker rotationInvoker) {
            this.dial = dial;
            this.rotationInvoker = rotationInvoker;
        }

        public int calculatePassword(List<String> rotations) {
            rotations.stream()
                    .map(r -> InstructionParser.parse(r, dial))
                    .forEach(rotationInvoker::takeRotation);
            rotationInvoker.placeRotations();
            return dial.getPassword();
        }
    }
}