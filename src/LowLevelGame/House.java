package LowLevelGame;

import game_engine.Sprite;
import javafx.geometry.Dimension2D;

/**
 * Created by eliaskraihanzel on 11/20/17.
 *
 */
public class House extends Sprite {
    private final static String IMAGE_SOURCE = "house.png";
    House(double bottom, double xPosition) {
        super(IMAGE_SOURCE);
        setSize(new Dimension2D(100,100));
        setX(xPosition);
        setBottom(bottom);
    }
}
