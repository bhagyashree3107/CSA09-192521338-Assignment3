public class ScannerStations {
    static class StationThread extends Thread {
        private final int stationId;
        private final int scripts;
        StationThread(int stationId, int scripts) {
            super("Station-" + stationId);
            this.stationId = stationId;
            this.scripts = scripts;
        }
        @Override public void run() {
            process();
        }
        private void process() {
            try {
                for (int i = 1; i <= scripts; i++) {
                    Thread.sleep(40);
                }
                System.out.println(getName() + " scanned " + scripts + " scripts.");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println(getName() + " interrupted.");
            }
        }
    }

    static class RunnableStation implements Runnable {
        private final int stationId;
        private final int scripts;
        RunnableStation(int stationId, int scripts) {
            this.stationId = stationId;
            this.scripts = scripts;
        }
        @Override public void run() {
            try {
                for (int i = 1; i <= scripts; i++) Thread.sleep(40);
                System.out.println("Runnable-Station-" + stationId
                        + " scanned " + scripts + " scripts.");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread[] stations = {
            new StationThread(1, 3),
            new StationThread(2, 4),
            new StationThread(3, 2),
            new StationThread(4, 5)
        };
        for (Thread station : stations) station.start();
        for (Thread station : stations) station.join();
        System.out.println("All Thread-based stations completed.");

        Thread[] runnableStations = new Thread[4];
        for (int i = 0; i < runnableStations.length; i++) {
            runnableStations[i] = new Thread(new RunnableStation(i + 1, i + 2));
            runnableStations[i].start();
        }
        for (Thread station : runnableStations) station.join();
        System.out.println("All Runnable-based stations completed.");
    }
}
