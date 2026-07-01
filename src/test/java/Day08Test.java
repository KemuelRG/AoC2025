import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import day08.a.Day08A;
import day08.b.Day08B;

public class Day08Test {

    @Test
    void testDay08A() {
        List<Day08A.JunctionBox> boxes = List.of(
                new Day08A.JunctionBox(1, new Day08A.Coordinates(0, 0, 0)),
                new Day08A.JunctionBox(2, new Day08A.Coordinates(1, 1, 1))
        );
        Day08A.CircuitManager manager = new Day08A.CircuitManager(boxes);
        Day08A.PuzzleSolver solver = new Day08A.PuzzleSolver();

        long result = solver.solve(manager);
        assertEquals(2, result);
    }

    @Test
    void testDay08B() {
        List<Day08B.JunctionBox> boxes = List.of(
                new Day08B.JunctionBox(1, new Day08B.Coordinates(0, 0, 0)),
                new Day08B.JunctionBox(2, new Day08B.Coordinates(2, 2, 2))
        );
        Day08B.CircuitManager manager = new Day08B.CircuitManager(boxes);
        Day08B.PuzzleSolver solver = new Day08B.PuzzleSolver();

        long result = solver.solve(manager);
        assertEquals(0, result);
    }
}