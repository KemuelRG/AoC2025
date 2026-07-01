import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import day05.a.Day05A;
import day05.b.Day05B;

public class Day05Test {

    @Test
    void testDay05A() {
        List<Day05A.Range> ranges = List.of(Day05A.Range.of(1, 10));
        List<Long> availableIds = List.of(5L, 15L);
        Day05A.PuzzleSolver solver = new Day05A.PuzzleSolver();

        long result = solver.solve(availableIds, ranges);
        assertEquals(1, result); // 5L está dentro del rango
    }

    @Test
    void testDay05B() {
        List<Day05B.Range> ranges = List.of(
                Day05B.Range.of(1, 5),
                Day05B.Range.of(4, 10)
        );
        Day05B.PuzzleSolver solver = new Day05B.PuzzleSolver();

        long result = solver.solve(ranges);
        assertEquals(10, result); // Rango combinado de 1 a 10 = tamaño 10
    }
}