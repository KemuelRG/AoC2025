import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import day02.a.Day02A;
import day02.b.Day02B;

public class Day02Test {

    @Test
    void testDay02A() {
        List<Day02A.Range> ranges = List.of(Day02A.Range.of(10, 20));
        Day02A.IdValidator validator = new Day02A.IdValidator();
        Day02A.PuzzleSolver solver = new Day02A.PuzzleSolver();

        long result = solver.calculateSolution(ranges, validator);
        assertEquals(11, result);
    }

    @Test
    void testDay02B() {
        List<Day02B.Range> ranges = List.of(Day02B.Range.of(10, 20));
        Day02B.IdValidator validator = new Day02B.IdValidator();
        Day02B.PuzzleSolver solver = new Day02B.PuzzleSolver();

        long result = solver.calculateSolution(ranges, validator);
        assertEquals(11, result);
    }
}