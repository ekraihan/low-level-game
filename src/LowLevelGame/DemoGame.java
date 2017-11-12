package LowLevelGame;


import game_engine.Game;
import game_engine.Sprite;
import javafx.geometry.Point2D;

public class DemoGame extends Game {
    private static final String image_source = "math.png";

    public void init() {
        set_dimensions(1000,1000);
        Sprite sprite = new Sprite(image_source);
        sprite.setFitWidth(200);
        sprite.setFitHeight(100);
        sprite.set_velocity(new Point2D(4,10));
        this.add_sprite(sprite);
    }
}
