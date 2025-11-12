package csv;

import filials.BranchType;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class InventoryCsvRepo {

    public int previewQty(BranchType branch, String flower) {
        Map<String, Integer> map = readQtyMap(PathsMap.inventory(branch));
        return map.getOrDefault(norm(flower), 0);
    }
    public int tryReserve(BranchType branch, String flower, int items) throws IOException {
        String path = PathsMap.inventory(branch);
        Map<String, Integer> map = readQtyMap(path);
        String key = norm(flower);

        Integer available = map.get(key);
        if (available == null) {
            return 0;
        }

        int reserved = Math.min(available, Math.max(items, 0));
        map.put(key, available - reserved);
        writeQtyMap(path, map);
        return reserved;
    }

    private static String norm(String s) {
        return s == null ? "" : s.trim().toLowerCase();
    }
    private Map<String, Integer> readQtyMap(String path) {
        Map<String, Integer> map = new LinkedHashMap<>();
        File f = new File(path);
        if (!f.exists()) return map;

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(f), StandardCharsets.UTF_8))) {

            String line;
            boolean header = true;
            while ((line = br.readLine()) != null) {
                if (header) { header = false; continue; }
                String[] parts = line.split(",", -1);
                if (parts.length < 2) continue;

                String name = norm(parts[0]);
                int qty = safeInt(parts[1]);
                if (!name.isEmpty()) {
                    map.put(name, Math.max(qty, 0));
                }
            }
        } catch (IOException ignored) {}
        return map;
    }
    private void writeQtyMap(String path, Map<String, Integer> map) throws IOException {
        File f = new File(path);
        File dir = f.getParentFile();
        if (dir != null && !dir.exists()) dir.mkdirs();

        try (BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(f, false), StandardCharsets.UTF_8))) {
            bw.write("flower,qty\n");
            for (Map.Entry<String, Integer> e : map.entrySet()) {
                String name = e.getKey();
                if (name == null || name.isEmpty()) continue;
                int qty = Math.max(e.getValue() == null ? 0 : e.getValue(), 0);
                bw.write(name + "," + qty + "\n");
            }
        }
    }
    private int safeInt(String s) {
        try { return Integer.parseInt(s.trim()); }
        catch (Exception e) { return 0; }
    }
}