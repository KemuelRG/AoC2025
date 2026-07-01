import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import day01.a.Day01A;
import day01.b.Day01B;

public class Day01Test {

    @Test
    void testDay01A() {
        Day01A.SafeDial dial = new Day01A.SafeDial();
        Day01A.RotationInvoker invoker = new Day01A.RotationInvoker();
        Day01A.SafeOpener opener = new Day01A.SafeOpener(dial, invoker);

        // Simula entradas del input01.txt
        int result = opener.calculatePassword(List.of("R10", "L5"));
        assertEquals(0, result, "Debe calcular correctamente la contraseña de la parte A");
    }

    @Test
    void testDay01B() {
        Day01B.SafeDial dial = new Day01B.SafeDial();
        Day01B.PuzzleSolver solver = new Day01B.PuzzleSolver();
        dial.addObserver(solver);
        Day01B.RotationInvoker invoker = new Day01B.RotationInvoker();
        Day01B.SafeOpener opener = new Day01B.SafeOpener(dial, invoker, solver);

        int result = opener.calculatePassword(List.of("R100", "L50"));
        assertEquals(2, result, "Debe calcular correctamente la solución de la parte B");
    }
}