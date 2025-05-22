package model;

public class Scheduler extends Thread {

    public Runnable runnable;
    public boolean running = true;
    public long interval = 1000;
    public long lastResetTime;

    public Scheduler(Runnable _runnable) {
        runnable = _runnable;
        lastResetTime = System.currentTimeMillis();
    }

    public void resetTimer() {
        lastResetTime = System.currentTimeMillis();
    }

    @Override
    public void run() {
        while (running) {
            try {
                long currentTime = System.currentTimeMillis();

                Thread.sleep(interval);
                runnable.run();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
