public class Bowling {
    // This name is in portuguese because I don`t know if its a requirement to
    // maintain the requested name
    public int computaPlacar(String s) {
        int score = 0;
        int frame = 0;
        int i = 0;

        while (frame < 10) {
            int resultado = scoreFrame(i, s);
            score += resultado;
            i += 2;
            frame++;
        }
        return score;
    }

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
        if (isStrike(c)) {
            if (isSpare(s.charAt(index - 1))) {
                return 10;
            }
            int strikeBonus = this.twoNextThrows(index + 1, s);
            return 10 + strikeBonus;
        }
        if (c == '-')
            return 0;
        if (isSpare(c)) {
            int prev = moveValue(s.charAt(index - 1));
            return 10 - prev;
        }
        return Character.getNumericValue(c);
    }

    public int twoNextThrows(int index, String s) {
        int first = this.nextThrow(index, s);
        int second;

        if (isStrike(s.charAt(index))) {
            second = this.nextThrow(index + 2, s); 
        } else {
            second = this.nextThrow(index + 1, s);
        }

        return first + second;
    }

    public int scoreFrame(int index, String s) {
        char first = s.charAt(index);

        // Strike
        if (isStrike(first)) {
            int bonus = nextThrow(index + 1, s) + nextThrow(index + 2, s);
            return 10 + bonus; // Strike occupies 2 plays
        }

        char second = s.charAt(index + 1);

        // Spare
        if (isSpare(second)) {
            int bonus = nextThrow(index + 2, s);
            return  10 + bonus;
        }

        // Frame normal
        return moveValue(first) + moveValue(second);
    }
}
