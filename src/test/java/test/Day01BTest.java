package test;

import org.junit.jupiter.api.Test;
import software.aoc.day01.b.Dial;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day01BTest {

    private static final String orders = """
                                          L68
                                          L30
                                          R48
                                          L5
                                          R60
                                          L55
                                          L1
                                          L99
                                          R14
                                          L82
                                          """;

    @Test
    public void given_orders_should_account_the_times_that_dial_crosses_zero() {
        List<String> ordersList = Arrays.stream(orders.split("\\n")).map(String::trim).toList();

        assertEquals(6, new Dial().executeAll(ordersList).zeroHits());
        assertEquals(0, new Dial().executeAll(List.of("L1")).zeroHits());
        assertEquals(1, new Dial().executeAll(List.of("L1", "R1", "R50")).zeroHits());
        assertEquals(6, new Dial().executeAll(List.of("L51", "L500")).zeroHits());
    }

    @Test
    public void reward() throws Exception {
        Path path = Paths.get("src/test/resources/day01/orders.txt");

        List<String> ordersList;
        try (Stream<String> lines = Files.lines(path)) {
            ordersList = lines
                    .filter(line -> !line.isBlank())
                    .map(String::trim)
                    .toList();
        }

        Dial estadoInicial = new Dial();
        Dial estadoFinal = estadoInicial.executeAll(ordersList);

        int password = estadoFinal.zeroHits();

        System.out.println("***********************************");
        System.out.println("SOLUCIÓN DAY 1 - PART B: " + password);
        System.out.println("***********************************");

        assertEquals(6892, password, "La contraseña de la parte B debe ser 6892");
    }
}