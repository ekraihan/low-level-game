package LowLevelGame;

import game_engine.BoundaryAction;
import game_engine.Game;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;

public class DemoGame extends Game {
    private static final String image_source = "math.png";

    Gun gun = new Gun(image_source);

    public void init() {
        addSprite(gun)
                .setPosition(new Point2D(500,500))
                .setSize(new Dimension2D(1000,1000))
                .setImageAngle(45);
//                .setVelocity(new Point2D(10,10));

        addKeyAction(KeyCode.DOWN, () -> gun.scale(.99));
        addKeyAction(KeyCode.UP, () -> gun.scale(1.01));
        addKeyAction(KeyCode.RIGHT, () -> gun.rotate(4));
        addKeyAction(KeyCode.LEFT, () -> gun.rotate(-4));
//        addKeyAction(KeyCode.W, () -> gun.addVector(45, 30));
    }

    public Dimension2D getDimensions() {
        return new Dimension2D(1000,1000);
    }
}
