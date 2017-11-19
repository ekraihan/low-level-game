package game_engine;

import javafx.application.Application;
import javafx.geometry.Dimension2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.util.*;
import java.util.function.BooleanSupplier;

abstract public class Game extends Application {
    private final static int FRAME_RATE = 30;

    private final Dimension2D dimensions;

    private List<Sprite> sprite_list;

    private HashMap<BooleanSupplier, Runnable> actions;

    private EnumSet<KeyCode> keySet;

    public Game() {
        this.dimensions = getDimensions();
        this.sprite_list = new ArrayList<>();
        this.actions = new HashMap<>();
        this.keySet = EnumSet.noneOf(KeyCode.class);
    }

    private void update() {
        sprite_list.forEach(sprite -> {
            sprite.update();
            sprite.getBoundaryAction().check(this, sprite);
        });
        actions.forEach((condition, action) -> {
            if (condition.getAsBoolean())
                action.run();
        });
    }

    protected final Sprite addSprite(Sprite sprite) {
        sprite_list.add(sprite);
        return sprite;
    }

    protected final void addConditionalAction(BooleanSupplier condition, Runnable action) {
        actions.put(condition,action);
    }

    protected final void addKeyAction(KeyCode keyCode, Runnable action) {
        actions.put(() -> keyPressed(keyCode), action);
    }

    protected final Boolean keyPressed(KeyCode key) {
        return keySet.contains(key);
    }

    public final double getHeight() {
        return dimensions.getHeight();
    }

    public final double getWidth() {
        return dimensions.getWidth();
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

    public abstract Dimension2D getDimensions();
}
