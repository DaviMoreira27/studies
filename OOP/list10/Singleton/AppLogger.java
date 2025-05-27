package Singleton;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AppLogger {

    private static AppLogger logger;
    private static final DateTimeFormatter formatter =
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private AppLogger() {}

    public static AppLogger getInstance() {
        if (AppLogger.logger == null) {
            logger = new AppLogger();
        }
        return AppLogger.logger;
    }

    private void log(String level, String message)
        throws FileNotFoundException, UnsupportedEncodingException, IOException {
        String timestamp = LocalDateTime.now().format(formatter);
        String thread = Thread.currentThread().getName();
        System.out.printf(
            "[%s] [%s] [%s]: %s%n",
            timestamp,
            level,
            thread,
            message
        );

        String log = String.format(
            "[%s] [%s] [%s]: %s%n",
            timestamp,
            level,
            thread,
            message
        );

        FileOutputStream fs = new FileOutputStream("app.log", true);
        OutputStreamWriter os = new OutputStreamWriter(fs, "utf-8");
        os.write(log);
        os.close();
    }

    public void info(String message) {
        try {
            log("INFO", message);
        } catch (Exception e) {
            System.out.println("Failed to write log: " + e.getMessage());
        }
    }

    public void warn(String message) {
        try {
            log("WARN", message);
        } catch (Exception e) {
            System.out.println("Failed to write log: " + e.getMessage());
        }
    }

    public void error(String message) {
        try {
            log("ERROR", message);
        } catch (Exception e) {
            System.out.println("Failed to write log: " + e.getMessage());
        }
    }
}
