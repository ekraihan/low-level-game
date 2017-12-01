package LowLevelGame;

import game_engine.Sprite;
import javafx.geometry.Dimension2D;

/**
 * A simple platform sprite
 */
public class Platform extends Sprite{
    private final static String IMAGE_SOURCE = "platform.png";
    Platform() {
        super(IMAGE_SOURCE);
        setSize(new Dimension2D(400, 30));
    }
}
