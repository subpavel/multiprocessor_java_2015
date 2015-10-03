package ru.sbtmipt;

import java.util.Random;

/**
 * Created by PavelSub on 10/3/2015.
 */
public class Philosopher extends Thread {

    private int philId;
    private int health;

    public Philosopher(int philId) {
        this.philId = philId;
    }

    @Override
    public void run() {
        while (true) {
            // think
            try {
                System.out.println("Phil " + philId + " starts thinking");
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // eat
            eat();
        }
    }

    void eat() {
        // pick up forks
        // implementing partial order by always getting fork with lower number first
        System.out.println("Phil " + philId + " is getting forks");
        int firstFork = Math.min(philId, (philId + 1) % Main.N);
        int secondFork = Math.max(philId, (philId + 1) % Main.N);
        Main.forks[firstFork].lock();
        Main.forks[secondFork].lock();

        // eat
        try {
            System.out.println("Phil " + philId + " starts eating");
            Thread.sleep(3);
            health++;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            // put down forks
            Main.forks[firstFork].unlock();
            Main.forks[secondFork].unlock();
        }
    }

    public int getHealth() {
        return health;
    }

    public int getPhilId() {
        return philId;
    }
}
