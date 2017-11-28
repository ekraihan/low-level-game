package LowLevelGame;

import game_engine.Sprite;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;

public class Hero extends Sprite {
    private final static String HERO_RIGHT = "heroRight.PNG";
    private final static String HERO_LEFT = "heroLeft.PNG";
    private boolean turnedRight = true;
    Hero() {
        super(HERO_RIGHT);
    }

    public void jump() {
        addVector(0, 20);
        System.out.println("JUMP!!!");
    }

    public void fire() {
        System.out.println("FIRE!!!");
    }

    public void turnLeft() {
        turnedRight = false;
        Dimension2D size = getSize();
        Point2D position = getPosition();
        setImage(HERO_LEFT);
        setSize(size);
        setPosition(position);
    }

    public void turnRight() {
        turnedRight = true;
        Dimension2D size = getSize();
        Point2D position = getPosition();
        setImage(HERO_RIGHT);
        setSize(size);
        setPosition(position);
    }

    public boolean turnedRight() {
        return turnedRight;
    }

    public boolean turnedLeft() {
        return !turnedRight;
    }

    public void grow(double scaleAmount) {
        Point2D position = getPosition();
        scale(scaleAmount);
        setPosition(position);
    }

    public void shrink(double scaleAmount) {
        Point2D position = getPosition();
        scale(scaleAmount);
        setPosition(position);
    }

}