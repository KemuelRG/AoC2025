package test;

import org.junit.jupiter.api.Test;
import software.aoc.day02.GiftShop;
import software.aoc.day02.a.RepeatedHalfRule;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day02ATest {

    public final static String ranges = """
                                        11-22
                                        95-115
                                        998-1012
                                        1188511880-1188511890
                                        222220-222224
                                        1698522-1698528
                                        446443-446449
                                        38593856-38593862
                                        565653-565659
                                        824824821-824824827
                                        2121212118-2121212124
                                        """;

    @Test
    public void given_id_ranges_should_sum_invalid_ids() {
        GiftShop giftShop = new GiftShop(new RepeatedHalfRule());

        assertEquals(11, giftShop.calculateInvalidIdSum("10-20"));
        assertEquals(33, giftShop.calculateInvalidIdSum("11-22"));
        assertEquals(1227775554L, giftShop.calculateInvalidIdSum(ranges.replace("\n", ",")));
    }

    @Test
    public void reward() throws Exception {
        String input = Files.readString(Paths.get("src/test/resources/day02/orders.txt"));
        GiftShop giftShop = new GiftShop(new RepeatedHalfRule());

        long result = giftShop.calculateInvalidIdSum(input);

        assertEquals(40214376723L, result);
    }
}