import java.util.LinkedList;
import java.util.Queue;

public class Consultancy {

    /*
        Linked List -> Muita edição, pouco acesso
        Array List -> Muito acesso, pouca edição
    */
    private Queue<String> patients = new LinkedList<String>();

    public Queue<String> addPatient(Patient p) {
        this.patients.add(p.nome);
        return this.patients;
    }

    public void callPatient() {
        if (patients.size() > 0) {
            String pName = patients.poll();
            System.out.println("Patient: " + pName);
        }
    }

    public void getAllPatients() {
        int i = 1;
        for (String nPatient : patients) {
            System.out.format("%02d - %s\n", i, nPatient);
            i++;
        }
    }
}
