# Q4 – Thread Creation and Life Cycle

Run:
```bash
javac ScannerStations.java
java ScannerStations
```
Run at least ten times and record the order in which station completion messages appear. The order can vary because the scheduler decides when runnable threads execute. `start()` asks the JVM to schedule a new thread; calling `run()` directly is an ordinary method call on the current thread. `join()` makes the main thread wait for each station to finish.

For priority/daemon experiments, create a separate copy or modify a station's priority before `start()`. Priority is only a scheduling hint and does not guarantee completion order. A daemon thread does not keep the JVM alive after all non-daemon threads finish.
