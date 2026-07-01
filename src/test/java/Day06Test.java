import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import day06.a.Day06A;
import day06.b.Day06B;

public class Day06Test {

    @Test
    void testDay06A() {
        List<String> input = List.of("2 3", "4 5", "* +");
        List<Day06A.Operation> ops = Day06A.InputParser.parse(input);
        Day06A.PuzzleSolver solver = new Day06A.PuzzleSolver();

        long result = solver.solve(ops);
        assertEquals(16, result);
    }

    @Test
    void testDay06B() {
        List<String> input = List.of(" 2  3", " 4  5", " * +");
        List<Day06B.Operation> ops = Day06B.InputParser.parse(input);
        Day06B.PuzzleSolver solver = new Day06B.PuzzleSolver();

        long result = solver.solve(ops);
        assertEquals(59, result);
    }
}