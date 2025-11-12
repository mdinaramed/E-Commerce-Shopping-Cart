package csv;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class InventoryCsvRepo {
    private static final String HEADER = "flower,qty";

    private Path file(String branch) {
        return Path.of("data", "inventory_" + branch + ".csv");
    }

    public Map<String, Integer> load(String branch) throws IOException {
        Map<String, Integer> map = new HashMap<>();
        for (String[] r : Csv.read(file(branch))) {
            map.put(r[0].toLowerCase(), Integer.parseInt(r[1]));
        }
        return map;
    }

    public void saveAll(String branch, Map<String, Integer> map) throws IOException {
        List<String[]> rows = new ArrayList<>();
        map.forEach((k, v) -> rows.add(new String[]{k.toLowerCase(), String.valueOf(v)}));
        Csv.writeAll(file(branch), HEADER, rows);
    }

    public synchronized int tryReserve(String branch, String flower, int want) throws IOException {
        Map<String, Integer> inv = load(branch);
        int have = inv.getOrDefault(flower.toLowerCase(), 0);
        int take = Math.max(0, Math.min(have, want));
        inv.put(flower.toLowerCase(), have - take);
        saveAll(branch, inv);
        return take;
    }
}