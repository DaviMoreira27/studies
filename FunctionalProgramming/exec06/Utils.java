import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Utils {

    public static DataInputFormat readNumbers() throws Exception {
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        String[] parts = line.trim().split("\\s+");

        if (parts.length != 4) {
            scanner.close();
            throw new Exception(
                "A linha deve conter exatamente 4 números inteiros separados por espaço."
            );
        }

        DataInputFormat input = new DataInputFormat();
        input.n1 = Integer.parseInt(parts[0]);
        input.n2 = Integer.parseInt(parts[1]);
        input.n3 = Integer.parseInt(parts[2]);
        input.n4 = Integer.parseInt(parts[3]);

        scanner.close();
        return input;
    }

    public static int firstTask(List<DataFormat> data, DataInputFormat input) {
        List<DataFormat> confirmedBiggerThanN1 = data
            .stream()
            .filter(d -> d.confirmed > input.n1)
            .collect(Collectors.toList());

        int sum = confirmedBiggerThanN1
            .stream()
            .map(d -> d.active)
            .reduce(0, (subtotal, element) -> subtotal + element);

        return sum;
    }

    public static int secondTask(List<DataFormat> data, DataInputFormat input) {
        List<DataFormat> topN2ByActive = data
            .stream()
            .sorted((a, b) -> Integer.compare(b.active, a.active))
            .limit(input.n2)
            .collect(Collectors.toList());

        List<DataFormat> bottomN3ByConfirmed = topN2ByActive
            .stream()
            .sorted(Comparator.comparingInt(d -> d.confirmed))
            .limit(input.n3)
            .collect(Collectors.toList());

        int sum = bottomN3ByConfirmed.stream().mapToInt(d -> d.deaths).sum();

        return sum;
    }

    public static List<String> thirdTask(
        List<DataFormat> data,
        DataInputFormat input
    ) {
        List<DataFormat> topN4Countries = data
            .stream()
            .sorted((a, b) -> Integer.compare(b.confirmed, a.confirmed))
            .limit(input.n4)
            .collect(Collectors.toList());

        List<String> alphabeticalCountriesByN3 = topN4Countries
            .stream()
            .map(d -> d.country)
            .sorted()
            .limit(input.n4)
            .collect(Collectors.toList());

        return alphabeticalCountriesByN3;
    }
}
