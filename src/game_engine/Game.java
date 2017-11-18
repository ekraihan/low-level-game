package game_engine;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.geometry.Dimension2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.util.*;

abstract public class Game extends Application {
    private final static int FRAME_RATE = 30;

    private Dimension2D dimensions = new Dimension2D(500, 500);

    private List<Sprite> sprite_list = new ArrayList<>();

    private ObservableMap<KeyCode, Boolean> keyMap;

    private Scene scene;

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
        scene = new Scene(root, dimensions.getWidth(), dimensions.getHeight());

        keyMap = FXCollections.observableMap(new EnumMap<KeyCode, Boolean>(KeyCode.class));
        Arrays.stream(KeyCode.values()).forEach(value -> keyMap.put(value, false));

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
