package LowLevelGame;

import game_engine.Sprite;
import javafx.geometry.Dimension2D;

/**
 * Created by eliaskraihanzel on 11/20/17.
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
