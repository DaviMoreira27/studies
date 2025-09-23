package Observer;

import java.util.ArrayList;
import java.util.List;

public abstract class Publisher {

    private List<Subscriber> subscribers = new ArrayList<Subscriber>();
    private boolean hasChanged;

    public void addObserver(Subscriber b) {
        this.subscribers.add(b);
    }

    public void removeObserver(Subscriber b) {
        this.subscribers.remove(b);
    }

    protected void notifyObservers() {
        if (hasChanged()) {
            for (Subscriber s : subscribers) {
                s.update(this);
            }

            this.hasChanged = false;
        }
    }

    protected void notifyObserver(Subscriber b) {
        if (hasChanged()) {
            b.update(this);
            this.hasChanged = false;
        }
    }

    protected boolean hasChanged() {
        return this.hasChanged;
    }

    protected void setChanged() {
        this.hasChanged = true;
    }
}
