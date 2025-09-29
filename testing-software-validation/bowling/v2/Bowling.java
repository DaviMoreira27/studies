public class Bowling {

    public int moveValue(char c) {
        if (c == 'X') {
            return 10;
        } else if (c == '-') {
            return 0;
        } else if (Character.isDigit(c)) {
            return Character.getNumericValue(c);
        } else {
            throw new IllegalArgumentException("Invalid character is not a valid score: " + c);
        }
    }

    public boolean isStrike(char c) {
        return c == 'X';
    }

    public boolean isSpare(char c) {
        return c == '/';
    }
}
