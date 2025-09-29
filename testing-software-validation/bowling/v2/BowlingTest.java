import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BowlingTest {

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
}