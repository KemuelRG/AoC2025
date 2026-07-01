import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import day04.a.Day04A;
import day04.b.Day04B;

public class Day04Test {

    @Test
    void testDay04A() {
        List<String> mockData = List.of("@@.", ".@.", "...");
        Day04A.InventoryMatrix matrix = new Day04A.InventoryMatrixBuilder().from(mockData).build();
        Day04A.MatrixAnalyzer analyzer = new Day04A.MatrixAnalyzer(matrix);
        Day04A.PuzzleSolver solver = new Day04A.PuzzleSolver();

        int result = solver.solve(matrix, analyzer);
        assertEquals(3, result);
    }

    @Test
    void testDay04B() {
        List<String> mockData = List.of("@@.", ".@.", "...");
        Day04B.InventoryMatrix matrix = new Day04B.InventoryMatrixBuilder().from(mockData).build();
        Day04B.PuzzleSolver solver = new Day04B.PuzzleSolver();

        int result = solver.solve(matrix);
        assertEquals(3, result);
    }
}