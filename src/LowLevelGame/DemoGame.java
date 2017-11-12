package LowLevelGame;


import game_engine.Game;
import game_engine.Sprite;
import javafx.geometry.Point2D;

/**
 * Created by eliaskraihanzel on 11/11/17.
 */
public class DemoGame extends Game {
    private static final String image_source = "math.png";

    public static void main(String[] args) {
        launch();
    }

    public void init() {
        Sprite blah = new Sprite(image_source);
        blah.set_velocity(new Point2D(4,6));
        this.add_sprite(blah);
    }
}
