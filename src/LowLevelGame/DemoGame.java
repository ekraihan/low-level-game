package LowLevelGame;


import game_engine.CollisionManager;
import game_engine.ConditionalAction;
import game_engine.Game;
import game_engine.Sprite;
import javafx.application.ConditionalFeature;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import sun.tools.tree.ConditionalExpression;

import java.util.concurrent.locks.Condition;
import java.util.function.Predicate;

public class DemoGame extends Game {
    private static final String image_source = "math.png";

    Gun gun = new Gun(image_source);
    Gun gun2 = new Gun(image_source);

    public void init() {
        set_dimensions(1000,1000);

        gun.set_position(new Point2D(500,0));
        gun2.set_position(new Point2D(500,1000));
        gun2.set_velocity(new Point2D(0,-10));

        add_sprite(gun);
        add_sprite(gun2);

        add_conditional_action(new ConditionalAction() {
            public boolean condition() {
                return CollisionManager.spritesColliding(gun, gun2);
            }

            public void action() {
                System.out.println("Hit!");
            }
        });
    }
}
