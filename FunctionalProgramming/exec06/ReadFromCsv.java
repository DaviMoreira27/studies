import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadFromCsv {

    public static List<DataFormat> readCsv(String filePath) throws IOException {
        List<DataFormat> dataList = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length != 5) {
                continue;
            }
            DataFormat data = new DataFormat();

            data.country = parts[0].trim();
            data.confirmed = Integer.parseInt(parts[1].trim());
            data.deaths = Integer.parseInt(parts[2].trim());
            data.recovery = Integer.parseInt(parts[3].trim());
            data.active = Integer.parseInt(parts[4].trim());
            dataList.add(data);
        }

        reader.close();
        return dataList;
    }
}
