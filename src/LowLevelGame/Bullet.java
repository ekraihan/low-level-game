package LowLevelGame;

import game_engine.Sprite;
import javafx.geometry.Dimension2D;

public class Bullet extends Sprite {
    private final static String BULLET_IMAGE = "bullet.png";

    Bullet(){
        super(BULLET_IMAGE);
        setSize(new Dimension2D(10,10));
    }

}
