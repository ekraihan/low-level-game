package LowLevelGame;

import game_engine.Sprite;
import javafx.geometry.Dimension2D;

/**
 * A simple bullet class with an image and a size
 */
public class Bullet extends Sprite {
    private final static String BULLET_IMAGE = "bullet.png";

    Bullet(){
        super(BULLET_IMAGE);
        setSize(new Dimension2D(10,10));
    }

}
