# Assignment 3 – CO3: Exception Handling & Multithreading

**Student:** Bhagyashree  
**Register Number:** 192521338  
**Course:** Programming in Java  
**Repository:** https://github.com/bhagyashree3107/CSA09-192521338-Assignment3  
**Java version:** Fill in the exact output of `java -version`.

> Submission note: The assignment PPT specifies pair work and one joint report. Confirm with the faculty how an individual submission should be represented. This draft covers both responsibility areas for preparation, but do not invent a second student's contribution.

## Q1 – ATM Exception Handling
### Aim
Simulate ATM withdrawals and handle invalid input, invalid amounts, insufficient funds and missing card-reader file.

### Exception classification
| Failure | Exception | Category | Handling |
|---|---|---|---|
| Non-numeric input | InputMismatchException | Unchecked | Multi-catch |
| Invalid/non-positive amount | IllegalArgumentException | Unchecked | Specific catch |
| Insufficient funds | InsufficientFundsException | Checked | Specific catch |
| Missing card file | FileNotFoundException | Checked | Multi-catch |
| Close failure | IOException | Checked | Nested catch in finally |
| JVM memory exhaustion | OutOfMemoryError | Error | Not caught by ATM |

### Catch order and finally
Specific exception handlers must precede broader handlers. `finally` performs cleanup and prints the session-end message during normal JVM execution, whether the transaction succeeds or a handled exception occurs.

### Test evidence
Insert actual screenshots for: successful withdrawal; invalid account input; negative amount; insufficient balance; missing card-reader file; zero amount; uncaught ArithmeticException stack trace.

### Uncaught stack trace
Explain exception type, message, method, source file and line number from the actual trace generated on your machine.

### Alternative: validation and error codes
Use `Scanner.hasNextLong()` / `hasNextDouble()` and explicit validation; return distinct status codes. Normal validation is O(1) for a fixed number of fields. Capturing an exception stack trace takes time and memory proportional to stack depth O(d); returning a status code is O(1) additional space.

## Q2 – User-Defined Exceptions and Propagation
`ExamException` is a checked base exception. `SubmissionStorageException` represents a storage-layer failure. The storage layer catches `IOException` and wraps it while retaining the original cause. The controller catches the meaningful application exception and can display a user-friendly message.

Expected output:
```text
Saved answer01.pdf
Error: Could not store answer02.pdf
Cause: Disk quota exceeded
```
If the cause is not passed, `getCause()` returns null. A subsequent `getCause().getMessage()` would throw `NullPointerException`.

Checked exceptions require callers to catch or declare them. If the hierarchy extends `RuntimeException`, declarations and handling are optional, though explicit handling remains possible. Stack trace storage is proportional to stack depth O(d); chaining retains the cause object and its trace.

## Q3 – Resource Safety
Compare manual close with try-with-resources. A manual `close()` after the read loop can be skipped if an exception occurs before reaching it. Try-with-resources closes resources automatically, in reverse declaration order. If the body throws and close also throws, the close failure is available as a suppressed exception.

### Experimental evidence to insert
- Input files used:
- Failure injected:
- Debugger breakpoint and observed failure line:
- Number of resources observed open (only if measured):
- Report output from both versions:
- Screenshot references:

For f input files with a total of N records, processing is O(N + f) (or O(total bytes) for byte-level reading). Sequentially opening and closing one input at a time keeps the number of simultaneously open input handles O(1), plus the output handle. Opening all f files at once uses O(f) handles. Try-with-resources does not by itself make output atomic or validate incomplete/corrupt data.

## Q4 – Thread Creation and Life Cycle
The implementation demonstrates extending `Thread` and implementing `Runnable`. `start()` creates concurrent execution; direct `run()` does not. `join()` waits for completion. `sleep()` pauses the current thread and does not release held monitors. `isAlive()` can be used to inspect whether a started thread has not yet terminated.

### Ten-run observation table
| Run | Completion order |
|---|---|
| 1–10 | Fill with actual observed station order |

Record priority and daemon experiments. Priority does not guarantee order. For n scripts and p workers, idealized processing time is approximately work/p when tasks are balanced and overhead is small. Thread stacks consume memory per platform/JVM configuration; do not invent a stack-size figure.

## Q5 – Race Conditions and Synchronization
The critical section is the check that a seat is unbooked followed by setting it booked and incrementing the counter. These operations must be atomic. A synchronized instance method locks the current object; a synchronized block can protect only the critical section using the same lock.

Expected synchronized test result:
```text
Seats sold: 10
```

### Required measurements
| Version | Trial 1–10 double-book count | 10,000-book elapsed time |
|---|---|---|
| Unsynchronized | Fill actual values | Fill actual value |
| Synchronized method | Fill actual values | Fill actual value |
| Synchronized block | Fill actual values | Fill actual value |

A static synchronized method locks the Class object and is useful when state is shared statically across all instances. For s seats, a boolean array uses O(s) space. AtomicBoolean per seat also uses O(s) references/objects; ReentrantLock per seat uses O(s) locks; a concurrent map uses O(s) entries. Under contention, lock acquisition may block; CAS may retry.

## Q6 – Deadlock and Inter-Thread Communication
Producer-consumer buffer capacity is five. `wait()` must be inside synchronized code and inside a loop because wakeups do not guarantee the condition is now true. `notifyAll()` wakes all waiters so they can re-check conditions. `wait()` releases the monitor; `sleep()` does not.

The four Coffman conditions are mutual exclusion, hold-and-wait, no preemption and circular wait. Consistent lock ordering prevents circular wait. A bounded `ArrayBlockingQueue` encapsulates producer-consumer synchronization; `tryLock(timeout)` allows a transfer to abandon an unavailable lock and retry or report failure.

Expected token output:
```text
Sum of tokens collected: 210 (expected 210)
```

## Q7 – Concurrent Fault-Tolerant Processor
Design: submit one `Callable<Result>` per file to a fixed-size `ExecutorService`; each task validates one file and returns success/failure details. Collect `Future<Result>` values and store results in a `ConcurrentLinkedQueue`. Catch per-file I/O and parse errors inside each task so one corrupt/missing file does not stop the overall run.

### Required 10,000-file measurements
| Pool size | Run 1 (ms) | Run 2 (ms) | Run 3 (ms) | Average (ms) |
|---:|---:|---:|---:|---:|
| 1 | | | | |
| 2 | | | | |
| 4 | | | | |
| 8 | | | | |

Use identical input files for each run. Record success and failure counts and verify against a hand-checked sample. More threads may not help when file I/O, disk throughput, context switching or task overhead is the bottleneck. A remaining limitation could be that the demo stores all result objects in memory; very large workloads may require streaming or batched reporting.

## Individual Contribution Record
The PPT's table is intended for Student A and Student B. If faculty approves individual work, ask how to label this record. Do not attribute work to a partner who did not perform it.

## Final evidence checklist
- [ ] Compile and execute all programs on the target machine.
- [ ] Save genuine output screenshots in each `output/` directory.
- [ ] Run Q4 at least ten times and record actual finish orders.
- [ ] Run Q5 repeated trials and record actual counts/timings.
- [ ] Run Q7 with 10,000 files and pool sizes 1, 2, 4 and 8; record raw timings.
- [ ] Include debugger evidence and resource observations for Q3.
- [ ] Add exact Java version and execution commands.
- [ ] Commit each question separately and push to GitHub.
