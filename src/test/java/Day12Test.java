import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import day12.a.Day12A;

public class Day12Test {

    @Test
    void testDay12A() {
        // Simulamos el parseo inicial
        List<String> mockData = List.of(
                "1:", "##", "2x2: 1 0"
        );
        Day12A.ParsingResult result = Day12A.InputParser.parse(mockData);
        Day12A.PuzzleSolver solver = new Day12A.PuzzleSolver();

        int validRegions = solver.solve(result);
        assertEquals(1, validRegions);
    }
}