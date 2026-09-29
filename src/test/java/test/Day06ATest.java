package test;

import org.junit.jupiter.api.Test;
import software.aoc.day06.CephalopodCalculator;
import software.aoc.day06.MathProblem;
import software.aoc.day06.StandardMathProvider;
import software.aoc.day06.a.HorizontalWorksheetParser;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day06ATest {

    public final static String worksheet = """
                                           123 328  51 64 \s
                                            45 64  387 23 \s
                                             6 98  215 314\s
                                           *   +   *   +  \s
                                           """;

    private long calculateHorizontal(String input) {
        List<String> lines = Arrays.asList(input.split("\\n"));
        List<MathProblem> problems = new HorizontalWorksheetParser().parse(lines);
        return new CephalopodCalculator(new StandardMathProvider()).calculateGrandTotal(problems);
    }

    @Test
    public void given_operands_and_operators_should_solve_worksheet() {
        assertEquals(68, calculateHorizontal("""
                                                    1  2  3\s
                                                   10 11 15\s
                                                   *  +  * \s
                                                   """));
        assertEquals(4277556, calculateHorizontal(worksheet));
    }

    @Test
    public void reward() throws Exception {
        List<String> lines = Files.readAllLines(Paths.get("src/test/resources/day06/orders.txt"));
        List<MathProblem> problems = new HorizontalWorksheetParser().parse(lines);

        long result = new CephalopodCalculator(new StandardMathProvider()).calculateGrandTotal(problems);

        assertEquals(7326876294741L, result);
    }
}