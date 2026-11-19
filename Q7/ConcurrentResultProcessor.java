import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ConcurrentResultProcessor {
    static class Result {
        final String file;
        final boolean success;
        final int score;
        final String message;
        Result(String file, boolean success, int score, String message) {
            this.file = file; this.success = success; this.score = score; this.message = message;
        }
    }

    static class ScoreTask implements Callable<Result> {
        private final Path file;
        ScoreTask(Path file) { this.file = file; }

        @Override public Result call() {
            try {
                if (!Files.exists(file)) {
                    return new Result(file.toString(), false, 0, "Missing file");
                }
                String raw = Files.readString(file).trim();
                int score = Integer.parseInt(raw);
                if (score < 0 || score > 100) {
                    return new Result(file.toString(), false, 0, "Score outside 0..100");
                }
                return new Result(file.toString(), true, score, "Processed");
            } catch (IOException | NumberFormatException e) {
                return new Result(file.toString(), false, 0,
                        "Invalid/corrupt file: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int poolSize = args.length > 0 ? Integer.parseInt(args[0]) : 4;
        Path inputDir = Path.of("scores");
        try {
            Files.createDirectories(inputDir);
            // Small deterministic demonstration dataset; missing/corrupt examples included.
            if (!Files.exists(inputDir.resolve("student01.txt")))
                Files.writeString(inputDir.resolve("student01.txt"), "86");
            if (!Files.exists(inputDir.resolve("student02.txt")))
                Files.writeString(inputDir.resolve("student02.txt"), "91");
            if (!Files.exists(inputDir.resolve("student03.txt")))
                Files.writeString(inputDir.resolve("student03.txt"), "not-a-number");
            if (!Files.exists(inputDir.resolve("student04.txt")))
                Files.writeString(inputDir.resolve("student04.txt"), "73");
        } catch (IOException e) {
            System.err.println("Could not prepare demo data: " + e.getMessage());
            return;
        }

        List<Path> files = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            files.add(inputDir.resolve(String.format("student%02d.txt", i)));
        }

        ConcurrentLinkedQueue<Result> results = new ConcurrentLinkedQueue<>();
        ExecutorService executor = Executors.newFixedThreadPool(poolSize);
        List<Future<Result>> futures = new ArrayList<>();
        long start = System.nanoTime();

        for (Path file : files) futures.add(executor.submit(new ScoreTask(file)));

        for (Future<Result> future : futures) {
            try {
                Result result = future.get();
                results.add(result);
            } catch (ExecutionException e) {
                results.add(new Result("unknown", false, 0, "Unexpected task failure: " + e.getCause()));
            }
        }
        executor.shutdown();
        long elapsed = System.nanoTime() - start;

        int success = 0, failure = 0, totalScore = 0;
        for (Result result : results) {
            if (result.success) { success++; totalScore += result.score; }
            else failure++;
            System.out.println(result.file + " -> " + result.message);
        }
        System.out.println("Successes: " + success);
        System.out.println("Failures: " + failure);
        System.out.println("Total score of successful files: " + totalScore);
        System.out.println("Pool size: " + poolSize);
        System.out.println("Elapsed time (ms): " + elapsed / 1_000_000.0);
    }
}
