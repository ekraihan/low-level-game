package LowLevelGame;

import game_engine.Sprite;
import javafx.geometry.Dimension2D;

/**
 * Created by eliaskraihanzel on 11/27/17.
 */
public class Road extends Sprite {
    private final static String IMAGE_SOURCE = "road.png";
    Road(double bottom, double xPosition) {
        super(IMAGE_SOURCE);
        setSize(new Dimension2D(100,100));
        setX(xPosition);
        setBottom(bottom);
    }
}
