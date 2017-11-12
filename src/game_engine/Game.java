package game_engine;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Dimension2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

abstract public class Game extends Application {

    private final static int FRAME_RATE = 30;
    private Dimension2D dimensions = new Dimension2D(500, 500);

    private List<Sprite> sprite_list = new ArrayList<>();

    private void update() {
        sprite_list.forEach(Sprite::update);
    }

    protected final void add_sprite(Sprite sprite) {
        sprite_list.add(sprite);
    }

    protected final void set_dimensions(int width, int height) {
        dimensions = new Dimension2D(width, height);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Group root = new Group();
        sprite_list.forEach(sprite -> root.getChildren().add(sprite));

        Scene scene = new Scene(root, dimensions.getWidth(), dimensions.getHeight());

        stage.setTitle("Low Level Game");
        stage.setScene(scene);
        stage.show();

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                update();
            }
        }, 0, 1000/FRAME_RATE);
    }

    public abstract void init() throws Exception;
}
