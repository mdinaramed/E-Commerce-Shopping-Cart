package csv;
import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.*;

public class Csv {
    public static Path ensureFile(Path path, String header) throws IOException {
        if (Files.notExists(path)) {
            Files.createDirectories(path.getParent());
            Files.writeString(path, header + System.lineSeparator());
        }
        return path;
    }
    public static List<String[]> read(Path path) throws IOException {
        if (Files.notExists(path)) return List.of();
        List<String[]> rows = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line; boolean headerSkipped = false;
            while ((line = br.readLine()) != null) {
                if (!headerSkipped) { headerSkipped = true; continue; }
                if (line.isBlank()) continue;
                rows.add(line.split(","));
            }
        }
        return rows;
    }
    public static synchronized void append(Path path, String header, String[] row) throws IOException {
        ensureFile(path, header);
        try (BufferedWriter bw = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
            bw.write(String.join(",", row));
            bw.newLine();
        }
    }
    public static synchronized void writeAll(Path path, String header, List<String[]> rows) throws IOException {
        ensureFile(path, header);
        try (BufferedWriter bw = Files.newBufferedWriter(path, StandardOpenOption.TRUNCATE_EXISTING)) {
            bw.write(header);
            bw.newLine();
            for (String[] r : rows) {
                bw.write(String.join(",", r));
                bw.newLine();
            }
        }
    }
    public static String now() {
        return LocalDateTime.now().toString();
    }
}