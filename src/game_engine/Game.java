package game_engine;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by eliaskraihanzel on 11/11/17.
 */
public class Game extends Application{

    private final int FRAME_RATE = 20;
    private List<Sprite> sprite_list = new ArrayList<>();

    private void update() {
        sprite_list.forEach(Sprite::update);
    }

    public void add_sprite(Sprite sprite) {
        sprite_list.add(sprite);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Group root = new Group();
        sprite_list.forEach(sprite -> root.getChildren().add(sprite));

        Scene scene = new Scene(root, 400, 300);

        stage.setTitle("Low Level Game");
        stage.setScene(scene);
        stage.show();

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                update();
            }
        }, 0, FRAME_RATE);
    }
}
