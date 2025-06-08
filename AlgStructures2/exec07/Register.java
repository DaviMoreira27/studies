package exec07;

public class Register {
    String name;
    String phone;
    String city;

    public Register(String name, String phone, String city) {
        this.name = name;
        this.phone = phone;
        this.city = city;
    }

    public String newLine() {
        return name + "|" + phone + "|" + city + "\n";
    }
}
