package test;

import org.junit.jupiter.api.Test;
import software.aoc.day03.EscalatorPowerSystem;
import software.aoc.day03.a.MaxTwoDigitJoltage;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day03ATest {

    public final static String batteryBanks = """
                                              987654321111111
                                              811111111111119
                                              234234234234278
                                              818181911112111
                                              """;

    private long calculateJoltage(String input) {
        return new EscalatorPowerSystem(new MaxTwoDigitJoltage())
                .calculateTotalJoltage(Arrays.stream(input.split("\\n")).map(String::trim));
    }

    @Test
    public void given_battery_bank_should_account_joltage() {
        assertEquals(99, calculateJoltage("989216248294"));
        assertEquals(87, calculateJoltage("108337364723"));
        assertEquals(70, calculateJoltage("123016263670"));
    }

    @Test
    public void given_multiple_battery_banks_should_account_joltage() {
        assertEquals(169, calculateJoltage("989216248294\n123016263670"));
        assertEquals(88, calculateJoltage("123512314532\n111113332321"));
        assertEquals(357, calculateJoltage(batteryBanks));
    }

    @Test
    public void reward() throws Exception {
        long result = new EscalatorPowerSystem(new MaxTwoDigitJoltage())
                .calculateTotalJoltage(Files.lines(Paths.get("src/test/resources/day03/orders.txt")));

        assertEquals(17343L, result);
    }
}