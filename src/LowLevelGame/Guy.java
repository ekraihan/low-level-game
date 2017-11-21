package LowLevelGame;

import game_engine.Sprite;

/**
 * Created by eliaskraihanzel on 11/21/17.
 */
public class Guy extends Sprite {
    Guy(String image) {
        super(image);
    }

    public void jump() {
        addVector(0, 20);
    }

}