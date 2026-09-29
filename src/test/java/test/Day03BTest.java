package test;

import org.junit.jupiter.api.Test;
import software.aoc.day03.EscalatorPowerSystem;
import software.aoc.day03.b.MaxTwelveDigitJoltage;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day03BTest {

    public final static String batteryBanks = """
                                              987654321111111
                                              811111111111119
                                              234234234234278
                                              818181911112111
                                              """;

    private long calculateJoltage(String input) {
        return new EscalatorPowerSystem(new MaxTwelveDigitJoltage())
                .calculateTotalJoltage(Arrays.stream(input.split("\\n")).map(String::trim));
    }

    @Test
    public void given_battery_bank_should_account_joltage() {
        assertEquals(996248294345L, calculateJoltage("989216248294345"));
        assertEquals(837364723224L, calculateJoltage("108337364723224"));
        assertEquals(316263670993L, calculateJoltage("123016263670993"));
    }

    @Test
    public void given_multiple_battery_banks_should_account_joltage() {
        assertEquals(1112232511964L, calculateJoltage("989216248294\n123016263670"));
        assertEquals(234625646853L, calculateJoltage("123512314532\n111113332321"));
        assertEquals(3121910778619L, calculateJoltage(batteryBanks));
    }

    @Test
    public void reward() throws Exception {
        long result = new EscalatorPowerSystem(new MaxTwelveDigitJoltage())
                .calculateTotalJoltage(Files.lines(Paths.get("src/test/resources/day03/orders.txt")));

        assertEquals(172664333119298L, result);
    }
}