package Observer;

public class User implements Subscriber {

    private String name;

    public User(String name) {
        this.name = name;
    }

    public void update(Publisher p) {
        System.out.println(
            name + " recebeu atualização de: " + p.getClass().getSimpleName()
        );
    }

    public void unsubscribe(Publisher p) {
        p.removeObserver(this);
    }
}
