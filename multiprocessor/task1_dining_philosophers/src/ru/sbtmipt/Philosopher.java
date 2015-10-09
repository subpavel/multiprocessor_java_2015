package ru.sbtmipt;

public class Philosopher extends Thread {

    private static final long THINKING_TIME = 100000;
    private static final long EATING_TIME = 50000;

    private final long simulationTime;

    private final int philId;
    private final Table table;

    public Philosopher(int philId, Table table, long simulationTime) {
        this.philId = philId;
        this.simulationTime = simulationTime;
        this.table = table;
    }

    @Override
    public void run() {
        long start = System.nanoTime();
        long end;
        do {
            end = System.nanoTime();
            think();
            eat();
        } while (start + simulationTime >= end);
    }

    void eat() {
        System.out.println("Phil " + philId + " is trying to pick up forks");
        table.pickUpForks(philId);

        System.out.println("Phil " + philId + " has started eating");
        simulateSleep(EATING_TIME);
        System.out.println("Phil " + philId + " has ended eating ");

        table.putDownForks(philId);
    }

    void think() {
        System.out.println("Phil " + philId + " has started thinking");
        simulateSleep(THINKING_TIME);
        System.out.println("Phil " + philId + " has ended thinking");
    }

    public int getPhilId() {
        return philId;
    }

    private void simulateSleep(long sleepingTime) {
        long start = System.nanoTime();
        long end;
        do {
            end = System.nanoTime();
        } while (start + sleepingTime >= end);
    }
}
