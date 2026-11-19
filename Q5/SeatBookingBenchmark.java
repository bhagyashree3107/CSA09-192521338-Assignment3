public class SeatBookingBenchmark {
    private static final int SEATS = 10_000;

    public static void main(String[] args) {
        long start = System.nanoTime();
        SeatBooking booking = new SeatBooking(SEATS);
        for (int i = 0; i < 10_000; i++) booking.book(i % SEATS);
        long elapsed = System.nanoTime() - start;
        System.out.println("Bookings attempted: 10000");
        System.out.println("Successful bookings: " + booking.getBookingsMade());
        System.out.println("Elapsed time (ms): " + elapsed / 1_000_000.0);
    }
}
