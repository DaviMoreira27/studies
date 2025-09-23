package Singleton;

public class Payment {

    public void payProduct() {
        AppLogger.getInstance().info("Product payed");
    }

    public void refundProduct() {
        AppLogger.getInstance().info("Product refunded");
    }

    public void paymentError() {
        AppLogger.getInstance()
            .error("Some error happen because yes - Product");
    }
}
