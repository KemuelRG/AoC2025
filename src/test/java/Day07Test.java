import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import day07.a.Day07A;
import day07.b.Day07B;

public class Day07Test {

    @Test
    void testDay07A() {
        String[][] matrix = {
                {".", ".", "."},
                {"^", ".", "^"},
                {".", "S", "."}
        };
        Day07A.TachyonDiagram diagram = new Day07A.TachyonDiagram(matrix);
        Day07A.PuzzleSolver solver = new Day07A.PuzzleSolver();

        long result = solver.solve(diagram);
        assertEquals(0, result);
    }

    @Test
    void testDay07B() {
        String[][] matrix = {
                {".", "^", "."},
                {"^", ".", "^"},
                {".", "S", "."}
        };
        Day07B.TachyonDiagram diagram = new Day07B.TachyonDiagram(matrix);
        Day07B.PuzzleSolver solver = new Day07B.PuzzleSolver();

        long result = solver.solve(diagram);
        assertEquals(0, result);
    }
}