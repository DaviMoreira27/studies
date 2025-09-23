import java.util.ArrayDeque;
import java.util.Deque;

public class DeckCollection {

    public Deque<Call> queue = new ArrayDeque<>();

    public void addCall(Call c) {
        if (c.ct == CallType.COMMON) {
            queue.addLast(c);
            return;
        }

        queue.push(c);
    }

    public void callPatient() {
        if (queue.size() > 0) {
            Call c = queue.pop();

            System.out.format(
                "Problem %s, Type: %s\n",
                c.problem,
                c.ct.toString()
            );
        }
    }

    public void getList() {
        int i = 1;
        for (Call c : queue) {
            System.out.format(
                "%02d - Problem %s, Type: %s\n",
                i,
                c.problem,
                c.ct.toString()
            );
            i++;
        }
    }
}
