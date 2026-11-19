# Q7 – Concurrent Fault-Tolerant Result Processor

Run:
```bash
javac ConcurrentResultProcessor.java
java ConcurrentResultProcessor 1
java ConcurrentResultProcessor 2
java ConcurrentResultProcessor 4
java ConcurrentResultProcessor 8
```
The program creates a small demo set under `scores/`, including one corrupt file and one missing file, then processes five paths with a fixed thread pool. Each `Callable` returns a `Result`; `Future.get()` collects each task result; `ConcurrentLinkedQueue` stores the results safely. A failed file is recorded without stopping other tasks.

For the assignment's required 10,000-file experiment, extend the data-generation loop and file list to 10,000 files, then run each pool size (1, 2, 4, 8) multiple times. Record actual elapsed times from your machine. The included five-file run validates the logic but is not a substitute for the required 10,000-file measurement.
