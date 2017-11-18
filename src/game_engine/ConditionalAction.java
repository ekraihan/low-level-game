package game_engine;

public abstract class ConditionalAction implements Runnable {

    @Override
    public void run() {
        if (condition())
            action();
    }

    public abstract boolean condition();

    public abstract void action();
}
