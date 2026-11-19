import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class ResourceReportDemo {
    private static List<String> readAll(Path file) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(file)) {
            String line;
            while ((line = reader.readLine()) != null) lines.add(line);
        }
        return lines;
    }

    private static void writeReport(Path output, List<String> lines) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(output,
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            writer.write("DAILY SALES SUMMARY");
            writer.newLine();
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        }
    }

    // Deliberately unsafe pattern for comparison: if read fails before close(),
    // the reader may remain open. This is for demonstration only.
    private static List<String> readManual(Path file) throws IOException {
        BufferedReader reader = Files.newBufferedReader(file);
        List<String> lines = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) lines.add(line);
        reader.close();
        return lines;
    }

    public static void main(String[] args) {
        Path inputDir = Path.of("input");
        Path output = Path.of("sales-summary.txt");
        try {
            Files.createDirectories(inputDir);
            Path store1 = inputDir.resolve("store1.txt");
            Path store2 = inputDir.resolve("store2.txt");
            if (!Files.exists(store1)) Files.writeString(store1, "Store 1: 1200\nStore 1: 800\n");
            if (!Files.exists(store2)) Files.writeString(store2, "Store 2: 950\nStore 2: 1100\n");

            List<String> all = new ArrayList<>();
            all.addAll(readAll(store1));
            all.addAll(readAll(store2));
            writeReport(output, all);
            System.out.println("Report generated: " + output);
            System.out.println("Records processed: " + all.size());
            System.out.println(Files.readString(output));
        } catch (IOException e) {
            System.err.println("Report generation failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
