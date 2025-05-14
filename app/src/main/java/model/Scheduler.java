package model;

public class Scheduler extends Thread {
    public Runnable runnable;
    public boolean running = true;

    public Scheduler(Runnable _runnable) {
        runnable = _runnable;
    }

    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            runnable.run();
        }
    }
}