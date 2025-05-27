package Observer;

public class Newsletter extends Publisher {

    public void publishContent() {
        System.out.println("New Content");
        this.setChanged();
        this.notifyObservers();
    }

    public void sendSpecificContent(Subscriber s) {
        System.out.println("New Content specific");
        this.setChanged();
        this.notifyObserver(s);
    }
}
