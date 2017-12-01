package LowLevelGame;

import game_engine.Sprite;

/**
 * A Tower who's bottom is determined thought the constructor parameters
 */
public class Tower extends Sprite {
    private final static String IMAGE_SOURCE = "tower.png";
    Tower(double bottom, double xPosition) {
        super(IMAGE_SOURCE);
        scale(.5);
        setX(xPosition);
        setBottom(bottom);
    }
}
