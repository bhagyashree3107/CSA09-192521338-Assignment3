# Q3 – Resource Safety and try-with-resources

Run:
```bash
javac ResourceReportDemo.java
java ResourceReportDemo
```
The program creates two sample store files under `input/`, reads them with try-with-resources, and writes `sales-summary.txt`.

The `readManual` method demonstrates a manual close after the read loop. It is intentionally not exception-safe: an exception during reading can skip `close()`. For a controlled failure experiment, rename one input file or make it unreadable, then compare the manual approach with try-with-resources. Do not claim an exact OS file-descriptor count unless you actually measure it with an OS tool. Java's try-with-resources closes resources in reverse order of initialization and preserves secondary close failures as suppressed exceptions.
