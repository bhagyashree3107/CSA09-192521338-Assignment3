# Q5 – Race Conditions and Synchronization

Run:
```bash
javac SeatBooking.java
java SeatBooking
javac SeatBooking.java SeatBookingBenchmark.java
java SeatBookingBenchmark
```
The synchronized method makes the check-then-book critical section atomic for this `SeatBooking` object. Expected output for the supplied 100-user/10-seat test is `Seats sold: 10`.

For the required unsynchronized experiment, remove `synchronized` from `book`, run repeated trials, and record actual counts. A race may not reproduce on every run; a latch improves simultaneous start but does not guarantee a duplicate every time. Record what actually happens rather than inventing a double-book count. For a synchronized-block variant, put only the check/update statements inside `synchronized (this)`.
