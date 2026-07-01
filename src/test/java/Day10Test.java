import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import day10.a.Day10A;
import day10.b.Day10B;

public class Day10Test {

    @Test
    void testDay10A() {
        // target: 1 (bit 0), buttons: [1]
        Day10A.Machine machine = new Day10A.Machine(1, List.of(1));
        Day10A.MachineAnalyzer analyzer = new Day10A.MachineAnalyzer();
        Day10A.PuzzleSolver solver = new Day10A.PuzzleSolver();

        long result = solver.solve(List.of(machine), analyzer);
        assertEquals(1, result);
    }

    @Test
    void testDay10B() {
        Day10B.Machine machine = new Day10B.Machine(List.of(10), List.of(List.of(0)));
        Day10B.MachineAnalyzer analyzer = new Day10B.MachineAnalyzer();
        Day10B.PuzzleSolver solver = new Day10B.PuzzleSolver();

        long result = solver.solve(List.of(machine), analyzer);
        assertEquals(10, result);
    }
}