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
    private final static int FRAME_RATE = 32;

    private final Timer timer;

    private final Dimension2D dimensions;

    private List<Sprite> sprite_list;

    private HashMap<BooleanSupplier, Runnable> conditionalActions;

    private List<Runnable> actions;

    private EnumSet<KeyCode> keySet;

    public Game() {
        this.actions = new ArrayList<>();
        this.timer = new Timer();
        this.dimensions = getDimensions();
        this.sprite_list = new ArrayList<>();
        this.conditionalActions = new HashMap<>();
        this.keySet = EnumSet.noneOf(KeyCode.class);
    }

    private void update() {
        sprite_list.forEach(sprite -> {
            sprite.update();
            sprite.getBoundaryAction().check(this, sprite);
        });
        conditionalActions.forEach((condition, action) -> {
            if (condition.getAsBoolean())
                action.run();
        });
        actions.forEach(Runnable::run);
    }

    protected final void addSprite(Sprite sprite) {
        sprite_list.add(sprite);
    }

    protected final <T extends Sprite> void addSprites(List<T> sprites) {
        sprite_list.addAll(sprites);
    }

    protected final void addConditionalAction(BooleanSupplier condition, Runnable action) {
        conditionalActions.put(condition,action);
    }

    protected final void addKeyAction(KeyCode keyCode, Runnable action) {
        conditionalActions.put(() -> keyPressed(keyCode), action);
    }

    protected final void addAction(Runnable action) {
        actions.add(action);
    }

    protected final void addKeyActions(List<KeyCode> keyCodes, Runnable action) {
        conditionalActions.put(() -> keyCodes.stream().allMatch(this::keyPressed), action);
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
//        scene.setOnMouseMoved(event ->
//                System.out.println(
//                        "Y: " + event.getY() + "\n"
//                        + "X: " + event.getX()
//                )
//        );

        stage.setTitle("Low Level Game");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                update();
            }
        }, 0, 1000/FRAME_RATE);
    }

    public void stop() {
        timer.cancel();
    }

    public abstract void init() throws Exception;

    protected abstract Dimension2D getDimensions();
}
