package LowLevelGame;

import game_engine.Sprite;

import java.util.Timer;
import java.util.TimerTask;

public class BadGuy extends Sprite{
    private final static String IMAGE_SOURCE = "robot.png";

    BadGuy() {
        super(IMAGE_SOURCE);
    }
    public void hit() {
        kill();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                addVector(180, 1);
                rotate(10);

            }
        }, 0, 20);
    }
}
