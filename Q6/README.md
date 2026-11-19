# Q6 – Deadlock and Inter-Thread Communication

Run:
```bash
javac TokenCounter.java WalletDeadlockDemo.java
java TokenCounter
java WalletDeadlockDemo
```
TokenCounter completes the supplied TODO 1 and TODO 2 using `while`, `wait()` and `notifyAll()`. Expected token sum is 210. The buffer count is constrained to 0..5 by synchronized access.

WalletDeadlockDemo demonstrates prevention, not the intentionally deadlocking version: both transfers acquire wallet locks in ascending ID order. The four Coffman conditions are mutual exclusion, hold-and-wait, no preemption and circular wait. A consistent global lock order prevents circular wait.

To compare with `ArrayBlockingQueue<Integer>(5)`, use `put()` and `take()`; these methods block when full/empty and encapsulate the coordination logic.
