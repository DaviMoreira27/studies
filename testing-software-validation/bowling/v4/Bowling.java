public class Bowling {
    public int moveValue(char c) {
        if (c == 'X') {
            return 10;
        } else if (c == '-') {
            return 0;
        } else if (Character.isDigit(c)) {
            return Character.getNumericValue(c);
        } else {
            throw new IllegalArgumentException("Invalid character, is not a valid score: " + c);
        }
    }

    public boolean isStrike(char c) {
        return c == 'X';
    }

    public boolean isSpare(char c) {
        return c == '/';
    }

    public int nextThrow(int index, String s) {
        char c = s.charAt(index);
        if (this.isStrike(c))
            return 10;
        if (c == '-')
            return 0;
        if (this.isSpare(c)) {
            int previousMove = moveValue(s.charAt(index - 1));
            return 10 - previousMove;
        }
        return Character.getNumericValue(c);
    }

    public int twoNextThrows(int index, String s) {
        int sum = this.nextThrow(index, s);
        if (this.isStrike(s.charAt(index))) {
            sum += this.nextThrow(index + 2, s);
        } else {
            sum += this.nextThrow(index + 1, s);
        }
        return sum;
    }

    public int[] scoreFrame(int currentIndex, String s) {
        char first = s.charAt(currentIndex);

        // Strike
        if (this.isStrike(first)) {
            int points = 10 + this.twoNextThrows(currentIndex + 1, s);
            return new int[] { points, 1 };
        }

        // Spare
        char second = s.charAt(currentIndex + 1);
        if (this.isSpare(second)) {
            int points = 10 + this.nextThrow(currentIndex + 2, s);
            return new int[] { points, 2 };
        }

        // Default
        int points = moveValue(first) + moveValue(second);
        return new int[] { points, 2 };
    }

}
