package game_engine;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import javafx.geometry.Dimension2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.util.*;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Predicate;

abstract public class Game extends Application {
    private final static int FRAME_RATE = 30;

    private Dimension2D dimensions = new Dimension2D(500, 500);

    private List<Sprite> sprite_list = new ArrayList<>();

    private HashMap<BooleanSupplier, Runnable> actions = new HashMap<>();

    private EnumSet<KeyCode> keySet = EnumSet.noneOf(KeyCode.class);

    private void update() {
        sprite_list.forEach(Sprite::update);
        actions.forEach((condition, action) -> {
            if (condition.getAsBoolean())
                action.run();
        });
    }

    protected final void add_sprite(Sprite sprite) {
        sprite_list.add(sprite);
    }

    protected void add_conditional_action(BooleanSupplier condition, Runnable action) {
        actions.put(condition,action);
    }

    protected final void set_dimensions(int width, int height) {
        dimensions = new Dimension2D(width, height);
    }

    protected final Boolean key_pressed(KeyCode key) {
        return keySet.contains(key);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Group root = new Group();
        sprite_list.forEach(sprite -> root.getChildren().add(sprite));
        Scene scene = new Scene(root, dimensions.getWidth(), dimensions.getHeight());

        scene.setOnKeyPressed(event -> keySet.add(event.getCode()));
        scene.setOnKeyReleased(event -> keySet.remove(event.getCode()));

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
