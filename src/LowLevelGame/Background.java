package LowLevelGame;

import game_engine.Sprite;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;

/**
 * Created by eliaskraihanzel on 11/21/17.
 */
public class Background extends Sprite {
    Background(String image, Dimension2D dimensions){
        super(image);
        setSize(new Dimension2D(dimensions.getWidth(),dimensions.getHeight()));
        setPosition(new Point2D(dimensions.getWidth()/2,dimensions.getHeight()/2));
    }
}
