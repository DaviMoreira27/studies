package Singleton;

public class Login {

    public void loginUser() {
        AppLogger.getInstance().info("Logged user");
    }

    public void signUser() {
        AppLogger.getInstance().info("Signed user");
    }

    public void userError() {
        AppLogger.getInstance().error("Some error happen because yes - User");
    }
}
