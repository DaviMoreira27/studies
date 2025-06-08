package exec07;

import java.io.*;
import java.util.Map;

public class RegisterManager {
    private final File file;
    private final Index index = new Index();
    private int idCounter = 0;

    public RegisterManager(String fileName) {
        this.file = new File(fileName);
    }

    public void addIndex(String name, String phone, String city) throws IOException {
        Register reg = new Register(name, phone, city);
        try (RandomAccessFile raf = new RandomAccessFile(file, "rw")) {
            raf.seek(raf.length());
            long offset = raf.getFilePointer();
            raf.writeBytes(" " + reg.newLine());
            index.addIndex(idCounter++, offset);
        }
    }

    public void registerRemover(int id) throws IOException {
        Long offset = index.getOffset(id);
        if (offset == null) {
            System.out.println("Registro " + id + " nao encontrado.");
            return;
        }
        try (RandomAccessFile raf = new RandomAccessFile(file, "rw")) {
            raf.seek(offset);
            raf.writeByte('*');
            index.indexRemover(id);
            System.out.println("Registro removido: " + id);
        }
    }

    public void listAll() throws IOException {
        System.out.println("--- Registros Atuais ---");
        try (RandomAccessFile raf = new RandomAccessFile(file, "r")) {
            for (Map.Entry<Integer, Long> entry : index.getTodos().entrySet()) {
                raf.seek(entry.getValue());
                String linha = raf.readLine();
                if (linha != null && !linha.startsWith("*")) {
                    System.out.println("[" + entry.getKey() + "] " + linha.trim());
                }
            }
        }
    }

    public void compact() throws IOException {
        File temp = new File("temp.txt");
        try (RandomAccessFile rafOld = new RandomAccessFile(file, "r");
                RandomAccessFile rafNew = new RandomAccessFile(temp, "rw")) {

            index.indexClear();
            idCounter = 0;

            String linha;
            long offset;
            while ((linha = rafOld.readLine()) != null) {
                offset = rafNew.getFilePointer();
                if (!linha.startsWith("*")) {
                    rafNew.writeBytes(" " + linha + "\n");
                    index.addIndex(idCounter++, offset);
                }
            }
        }
        file.delete();
        temp.renameTo(file);
        System.out.println("Arquivo compactado com sucesso.");
    }

    public void deleteFile() {
        file.delete();
    }
}
