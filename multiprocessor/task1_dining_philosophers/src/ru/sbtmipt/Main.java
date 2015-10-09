package ru.sbtmipt;

public class Main {
    private static final int N = 5;
    private static final long simulationTime = 5000000000L;

    public static void main(String[] args) {
        Philosopher[] philosophers = new Philosopher[N];
        Table table = new Table(N);

        for (int i = 0; i < N; i++) {
            philosophers[i] = new Philosopher(i, table, simulationTime);
            philosophers[i].start();
        }

        // let philosophers do their job
        for (int i = 0; i < N; i++) {
            try {
                philosophers[i].join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        // check results
        table.printCounters();
    }
}
