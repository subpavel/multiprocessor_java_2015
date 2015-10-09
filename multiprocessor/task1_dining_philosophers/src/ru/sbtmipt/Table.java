package ru.sbtmipt;

public class Table {
    /*Two conditions of the forks*/
    private enum ForkState {
        DIRTY, CLEAN
    }

    private final int n;
    private volatile ForkState[] forkStates;

    /*Owners of the forks*/
    private volatile int[] forks;
    /*Waiting flags for the philosophers' forks*/
    private volatile boolean[][] philWaiting;
    /*Numbers of food intakes*/
    private long[] philCounters;

    public Table(int n) {
        this.n = n;
        forks = new int[n];
        philCounters = new long[n];
        forkStates = new ForkState[n];
        philWaiting = new boolean[n][2];
        for (int i = 0; i < n; i++) {
            forkStates[i] = ForkState.DIRTY;
            forks[i] = Math.min(i, (i + 1) % n);
        }
    }

    public void pickUpForks(int philId) {
        int rightFork = philId;
        int leftFork = (philId + 1) % n;

        while (true) {
            // exit if the philosopher has both forks
            if (forks[rightFork] == philId && forks[leftFork] == philId) {
                break;
            }

            // trying to get the right fork
            if (forks[rightFork] != philId && forkStates[rightFork] == ForkState.DIRTY) {
                forkStates[rightFork] = ForkState.CLEAN;
                forks[rightFork] = philId;
            } else {
                // if failed, let others know
                philWaiting[philId][0] = true;
            }

            // trying to get the left fork
            if (forks[leftFork] != philId && forkStates[leftFork] == ForkState.DIRTY) {
                forkStates[leftFork] = ForkState.CLEAN;
                forks[leftFork] = philId;
            } else {
                // if failed, let others know
                philWaiting[philId][1] = true;
            }
        }
        // no more waiting
        philWaiting[philId][0] = false;
        philWaiting[philId][1] = false;
        philCounters[philId]++;
    }

    public void putDownForks(int philId) {
        int rightFork = philId;
        int leftFork = (philId + 1) % n;

        int rightId = philId == 0 ? n - 1 : philId - 1;
        int leftId = (philId + 1) % n;

        // set the forks' states to dirty so that others could use them
        forkStates[rightFork] = ForkState.DIRTY;
        forkStates[leftFork] = ForkState.DIRTY;

        // if another philosopher is waiting for this fork, he gets it
        // the fork is cleaned before sending
        if (philWaiting[rightId][0]) { // the right neighbor's left fork
            forkStates[rightFork] = ForkState.CLEAN;
            forks[rightFork] = rightId;
        }
        if (philWaiting[leftId][1]) { // the left neighbor's right fork
            forkStates[leftFork] = ForkState.CLEAN;
            forks[leftFork] = leftId;
        }
    }

    public void printCounters() {
        System.out.println("------------------");
        System.out.println("Counters:");
        for (int i = 0; i < philCounters.length; i++) {
            long counter = philCounters[i];
            System.out.println(i + " : " + counter);
        }
    }
}
