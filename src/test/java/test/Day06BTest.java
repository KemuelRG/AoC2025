package test;

import org.junit.jupiter.api.Test;
import software.aoc.day06.CephalopodCalculator;
import software.aoc.day06.MathProblem;
import software.aoc.day06.StandardMathProvider;
import software.aoc.day06.b.VerticalWorksheetParser;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day06BTest {

    public final static String worksheet = """
                                           123 328  51 64 \s
                                            45 64  387 23 \s
                                             6 98  215 314\s
                                           *   +   *   +  \s
                                           """;

    private long calculateVertical(String input) {
        List<String> lines = Arrays.asList(input.split("\\n"));
        List<MathProblem> problems = new VerticalWorksheetParser().parse(lines);
        return new CephalopodCalculator(new StandardMathProvider()).calculateGrandTotal(problems);
    }

    @Test
    public void given_operands_and_operators_should_solve_worksheet() {
        assertEquals(67, calculateVertical("""
                                                                                   1  2  3\s
                                                                                  10 11 15\s
                                                                                  *  +  * \s
                                                                                  """));
        assertEquals(3263827, calculateVertical(worksheet));
    }

    @Test
    public void reward() throws Exception {
        List<String> lines = Files.readAllLines(Paths.get("src/test/resources/day06/orders.txt"));
        List<MathProblem> problems = new VerticalWorksheetParser().parse(lines);

        long result = new CephalopodCalculator(new StandardMathProvider()).calculateGrandTotal(problems);

        assertEquals(10756006415204L, result);
    }
}