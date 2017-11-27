package LowLevelGame;

import game_engine.Sprite;

/**
 * Created by eliaskraihanzel on 11/21/17.
 */
public class Hero extends Sprite {
    Hero(String image) {
        super(image);
    }

    public void jump() {
        addVector(0, 20);
    }

}