package LowLevelGame;

import game_engine.Sprite;
import javafx.geometry.Point2D;

public class Hero extends Sprite {
    private final static String imageSource = "hero.PNG";
    Hero() {
        super(imageSource);
    }

    public void jump() {
        addVector(0, 20);
        System.out.println("JUMP!!!");
    }

    public void fire() {
        System.out.println("FIRE!!!");
    }

}