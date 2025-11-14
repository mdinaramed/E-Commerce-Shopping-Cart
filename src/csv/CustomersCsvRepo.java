package csv;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class CustomersCsvRepo {
    private static final String HEADER = "phone,name,bonus";
    private final Path path = Path.of("data", "customers.csv");
    public synchronized int addPoints(String phone, String name, int delta) throws IOException {
        Map<String, Integer> map = new LinkedHashMap<>();
        for (String[] r : Csv.read(path)) {
            map.put(r[0], Integer.parseInt(r[2]));
        }
        int newPoints = map.getOrDefault(phone, 0) + delta;
        map.put(phone, newPoints);

        List<String[]> rows = new ArrayList<>();
        map.forEach((p, pts) -> rows.add(new String[]{p, name, String.valueOf(pts)}));
        Csv.writeAll(path, HEADER, rows);
        return newPoints;
    }
}