import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import day11.a.Day11A;
import day11.b.Day11B;

public class Day11Test {

    @Test
    void testDay11A() {
        List<String> input = List.of("you: a b", "a: out", "b: out", "out:");
        Day11A.Graph graph = Day11A.InputParser.parse(input);
        Day11A.PuzzleSolver solver = new Day11A.PuzzleSolver();

        long result = solver.solve(graph);
        assertEquals(2, result);
    }

    @Test
    void testDay11B() {
        List<String> input = List.of(
                "svr: dac fft", "dac: fft out", "fft: dac out", "out:"
        );
        Day11B.Graph graph = Day11B.InputParser.parse(input);
        Day11B.PuzzleSolver solver = new Day11B.PuzzleSolver();

        long result = solver.solve(graph);
        System.out.println(result);
        assertEquals(0, result); // Remplazar por la aserción correcta
    }
}