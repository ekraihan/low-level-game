package LowLevelGame;

import game_engine.Sprite;

/**
 * Created by eliaskraihanzel on 11/20/17.
 */
public class Building extends Sprite {
    Building(String image, double bottom) {
        super(image);
        setBottom(bottom);
    }
}
