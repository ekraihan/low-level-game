package game_engine;

import javafx.application.Application;
import javafx.geometry.Dimension2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.util.*;
import java.util.function.BooleanSupplier;

/**
 * Game
 *
 * A game engine extending the javafx Application object. Extending this class
 * allows a developer to add sprites and actions to the game loop
 */
abstract public class Game extends Application {
    /// Frame rate of the game
    private final static int FRAME_RATE = 32;

    /// Timer used to implement game loop
    private final Timer timer;

    /// Dimensions of the game application
    private final Dimension2D dimensions;

    /// A list of all sprites in the game
    private List<Sprite> sprite_list;

    /// A map with an action to be run if the BooleanSupplier returns true
    private HashMap<BooleanSupplier, Runnable> conditionalActions;

    /// Actions that are run constantly in the game loop
    private List<Runnable> actions;

    private EnumSet<KeyCode> keySet;

    /// The root of all the objects in the javafx application
    private Group root;

    /**
     * constructor
     * Initialize private variables
     */
    public Game() {
        this.actions = new ArrayList<>();
        this.timer = new Timer();
        this.dimensions = getDimensions();
        this.sprite_list = new ArrayList<>();
        this.conditionalActions = new HashMap<>();
        this.keySet = EnumSet.noneOf(KeyCode.class);
    }

    /**
     * update
     *
     * For each sprite, conditional action, and action, update or run the sprites and actions
     */
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

    /**
     * Add a sprite to the sprite list
     * @param           sprite          A sprite object
     */
    protected final void addSprite(Sprite sprite) {
        sprite_list.add(sprite);
    }

    /**
     * Add a list of sprites to the sprite list
     * @param               sprites         An object extending Sprite
     * @param               <T>             A sprite type
     */
    protected final <T extends Sprite> void addSprites(List<T> sprites) {
        sprite_list.addAll(sprites);
    }

    /**
     * add a conditional action to the conditionalActions list
     *
     * @param           condition           A boolean supplier
     * @param           action              A runnable action
     */
    protected final void addConditionalAction(BooleanSupplier condition, Runnable action) {
        conditionalActions.put(condition,action);
    }

    /**
     * add an action and a boolean supplier that checks if a key has been pressed
     * to the conditionalActions list
     *
     * @param           keyCode             A key code
     * @param           action              A runnable action
     */
    protected final void addKeyAction(KeyCode keyCode, Runnable action) {
        conditionalActions.put(() -> keyPressed(keyCode), action);
    }

    /**
     * Add a generic action to the actions list
     * @param           action              A generic runnable action
     */
    protected final void addAction(Runnable action) {
        actions.add(action);
    }

    /**
     * check to see if a key is in the keySet, meaning that the key is being pressed
     *
     * @param           key             A key code
     * @return          whether or not the key is being pressed
     */
    protected final Boolean keyPressed(KeyCode key) {
        return keySet.contains(key);
    }

    /**
     * @return          The height of the game
     */
    public final double getHeight() {
        return dimensions.getHeight();
    }

    /**
     * @return          The width of the game
     */
    public final double getWidth() {
        return dimensions.getWidth();
    }

    /**
     * Initialize the game Group, add the sprite list to the group, and add the group to the scene.
     * Set listeners on the scene that will add and remove keys from the keySet if a key is pressed or released.
     * Show the game and start a timer that will run the update() method every frame
     *
     * @param           stage           The stage on which the game will be displayed
     */
    @Override
    public void start(Stage stage) {
        root = new Group();
        sprite_list.forEach(sprite -> root.getChildren().add(sprite));
        Scene scene = new Scene(root, dimensions.getWidth(), dimensions.getHeight());

        scene.setOnKeyPressed(event -> keySet.add(event.getCode()));
        scene.setOnKeyReleased(event -> keySet.remove(event.getCode()));

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

    /**
     * cancel the timer and call the destroy method on each sprite
     */
    public void stop() {

        timer.cancel();
        sprite_list.forEach(Sprite::destroy);
    }

    /**
     * a method that subclasses are required to define that will set up the game
     */
    public abstract void init();

    /**
     * a method that subclasses must define to define the dimensions of the game
     * @return      An Dimension2D object defining the size of the game
     */
    protected abstract Dimension2D getDimensions();
}
