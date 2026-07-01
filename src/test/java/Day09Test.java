import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import day09.a.Day09A;
import day09.b.Day09B;

public class Day09Test {

    @Test
    void testDay09A() {
        List<Day09A.Coordinate> coords = List.of(
                Day09A.Coordinate.of(0, 0),
                Day09A.Coordinate.of(2, 2)
        );
        Day09A.PuzzleSolver solver = new Day09A.PuzzleSolver();

        long result = solver.solve(coords);
        assertEquals(9, result); // width(3) * height(3)
    }

    @Test
    void testDay09B() {
        List<Day09B.Coordinate> coords = List.of(
                Day09B.Coordinate.of(0, 0),
                Day09B.Coordinate.of(2, 0),
                Day09B.Coordinate.of(2, 2),
                Day09B.Coordinate.of(0, 2)
        );
        Day09B.Polygon polygon = new Day09B.Polygon(coords);
        Day09B.PuzzleSolver solver = new Day09B.PuzzleSolver();

        long result = solver.solve(polygon);
        assertEquals(9, result);
    }
}