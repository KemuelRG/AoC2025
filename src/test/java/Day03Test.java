import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.math.BigInteger;
import java.util.List;
import day03.a.Day03A;
import day03.b.Day03B;

public class Day03Test {

    @Test
    void testDay03A() {
        Day03A.BankOptimizer optimizer = new Day03A.TwoBatteriesOptimizer();
        Day03A.PuzzleSolver solver = new Day03A.PuzzleSolver();

        int result = solver.getSolution(List.of("12345", "67890"), optimizer);
        assertEquals(135, result);
    }

    @Test
    void testDay03B() {
        Day03B.BankOptimizer<BigInteger> optimizer = new Day03B.TwelveBatteriesOptimizer();
        Day03B.PuzzleSolver solver = new Day03B.PuzzleSolver();

        BigInteger result = solver.getSolution(List.of("1234567890123", "9876543210987"), optimizer);
        assertEquals(new BigInteger("1222222212110"), result);
    }
}