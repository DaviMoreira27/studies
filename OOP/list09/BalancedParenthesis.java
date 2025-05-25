import java.util.*;

public class BalancedParenthesis {

    public Deque<Character> deque = new LinkedList<Character>();

    // ()[]]}}{{(()))()}}
    public boolean isUnbalanced(String expression)
        throws NoSuchElementException {
        for (int i = 0; i < expression.length(); i++) {
            System.out.format("Char: %c\n", expression.charAt(i));
            if (
                expression.charAt(i) == '{' ||
                expression.charAt(i) == '(' ||
                expression.charAt(i) == '['
            ) {
                deque.push(expression.charAt(i));
            }

            if (
                expression.charAt(i) == '}' ||
                expression.charAt(i) == ')' ||
                expression.charAt(i) == ']'
            ) {
                deque.pop();
            }
        }

        return this.deque.size() > 0;
    }
}
