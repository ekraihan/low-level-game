package LowLevelGame;

import game_engine.Sprite;
import javafx.geometry.Point2D;

import java.util.Timer;
import java.util.TimerTask;

public class BadGuy extends Sprite{
    private final static String IMAGE_SOURCE = "robot.png";
    private boolean isHit = false;

    BadGuy() {
        super(IMAGE_SOURCE);
        scale(1.7);
    }

    public void hit() {
       isHit = true;
    }

    public boolean isHit() {
        return isHit;
    }
}
