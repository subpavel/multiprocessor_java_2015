package ru.sbtmipt;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static final int N = 5;
    public static Lock[] forks = new ReentrantLock[Main.N];

    public static void main(String[] args) {
        Philosopher[] philosophers = new Philosopher[N];
        for (int i = 0; i < N; i++) {
            forks[i] = new ReentrantLock();
        }
        for (int i = 0; i < N; i++) {
            philosophers[i] = new Philosopher(i);
            philosophers[i].setDaemon(true);
            philosophers[i].start();
        }

        // let philosophers do their job
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // check results
        for (Philosopher philosopher :philosophers) {
            System.out.println(philosopher.getPhilId() + " hp: " + philosopher.getHealth());
        }
    }
}
