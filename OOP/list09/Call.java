enum CallType {
    COMMON,
    URGENT,
}

public class Call {

    public CallType ct;
    public String problem;

    public Call(CallType type, String pr) {
        this.ct = type;
        this.problem = pr;
    }
}
