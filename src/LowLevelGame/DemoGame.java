package LowLevelGame;

import game_engine.BoundaryAction;
import game_engine.Game;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;

import java.util.Arrays;

public class DemoGame extends Game {
    private static final String image_source = "d1.png";

    Gun gun = new Gun(image_source);

    public void init() {
        gun.setPosition(new Point2D(500,500))
                .setSize(new Dimension2D(3,3))
                .setBoundaryAction(BoundaryAction.BOUNCE)
                .setImageAngle(0);

        addSprite(gun);

        addKeyAction(KeyCode.DOWN, () -> gun.addVector(180, 1));
        addKeyAction(KeyCode.UP, () -> gun.addVector(0, 1));
        addKeyAction(KeyCode.RIGHT, () -> gun.addVector(90, 1));
        addKeyAction(KeyCode.LEFT, () -> gun.addVector(-90, 1));
        addKeyActions(Arrays.asList(KeyCode.COMMAND, KeyCode.EQUALS), () -> gun.scale(1.1));
        addKeyActions(Arrays.asList(KeyCode.COMMAND, KeyCode.MINUS), () -> gun.scale(.9));
    }

    public Dimension2D getDimensions() {
        return new Dimension2D(1000,1000);
    }
}
