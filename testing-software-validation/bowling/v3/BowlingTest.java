import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BowlingTest {
    // V1

    @Test
    public void testMoveValueStrike() {
        Bowling b = new Bowling();
        assertEquals(10, b.moveValue('X'));
    }

    @Test
    public void testMoveValueMiss() {
        Bowling b = new Bowling();
        assertEquals(0, b.moveValue('-'));
    }

    @Test
    public void testMoveValueDigit() {
        Bowling b = new Bowling();
        assertEquals(5, b.moveValue('5'));
        assertEquals(9, b.moveValue('9'));
    }

    // The char / should not work because we need context to define how much he values
    @Test
    public void testMoveValueInvalid() {
        Bowling b = new Bowling();
        assertThrows(IllegalArgumentException.class, () -> {
            b.moveValue('/');
        });
    }

    // V2

    @Test
    public void testIsStrike() {
        Bowling b = new Bowling();
        assertTrue(b.isStrike('X'));
        assertFalse(b.isStrike('5'));
    }

    @Test
    public void testIsSpare() {
        Bowling b = new Bowling();
        assertTrue(b.isSpare('/'));
        assertFalse(b.isSpare('9'));
    }

    // V3

    @Test
    public void testNextThrow() {
        Bowling b = new Bowling();
        assertEquals(10, b.nextThrow(0, "X12")); // strike
        assertEquals(2, b.nextThrow(1, "12-")); // digit
        assertEquals(0, b.nextThrow(2, "12-")); // miss
    }

    @Test
    public void testNextThrowSpare() {
        Bowling b = new Bowling();
        assertEquals(5, b.nextThrow(1, "5/3")); // spare depois de 5
    }

    @Test
    public void testTwoNextThrows() {
        Bowling b = new Bowling();
        assertEquals(3, b.twoNextThrows(0, "12-")); // 1+2
        assertEquals(13, b.twoNextThrows(0, "X53")); // 10+3
    }

}