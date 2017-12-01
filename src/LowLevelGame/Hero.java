package LowLevelGame;

import game_engine.Sprite;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Hero
 *
 * The hero of the game who can jump, fire, turn, and change size
 */
public class Hero extends Sprite {
    /// Hero images
    private final static String HERO_RIGHT = "heroRight.png";
    private final static String HERO_LEFT = "heroLeft.png";

    /// Whether or not the hero is turned right
    private boolean turnedRight = true;

    /// The platform that the hero is currently on
    private Sprite platform = null;

    /// The hero's bullets
    private List<Bullet> bulletList;

    /// THe index of the current bullet
    private int currentBullet = 0;

    /// A timer used to meter the frequency of bullet shots
    private Timer timer = new Timer();

    /// Whether or not the sprite is allowed to shoot
    private boolean canFire = false;

    /**
     * Constructor
     *
     * Set the hero's image and schedule the hero to be allowed to shoot
     * every 200 milliseconds
     * @param           sprites         A list of bullets
     */
    Hero(List<Bullet> sprites) {
        super(HERO_RIGHT);
        bulletList = sprites;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                canFire = true;
            }
        },0, 200);
    }

    /**
     * Add an upward vector
     */
    public void jump() {
        addVector(0, 26);
    }

    /**
     * If the hero is allowed to fire, determine the hero's direction,
     * Increment the current bullet and shoot the bullet. Set canFire to false.
     */
    public void fire() {
        if (canFire) {
            double direction = turnedRight ? 90 : -90;
            currentBullet++;
            if (currentBullet >= bulletList.size()){
                currentBullet = 0;
            }
            bulletList.get(currentBullet).setPosition(getPosition())
                    .addVector(direction, 19)
                    .show();
            canFire = false;
        }
    }

    /**
     * Turn the hero to the left
     */
    public void turnLeft() {
        turnedRight = false;
        Dimension2D size = getSize();
        Point2D position = getPosition();
        setImage(HERO_LEFT);
        setSize(size);
        setPosition(position);
    }

    /**
     * Turn the hero to the right
     */
    public void turnRight() {
        turnedRight = true;
        Dimension2D size = getSize();
        Point2D position = getPosition();
        setImage(HERO_RIGHT);
        setSize(size);
        setPosition(position);
    }

    /// Is the hero turned right?
    public boolean turnedRight() {
        return turnedRight;
    }

    /// Is the hero turned left?
    public boolean turnedLeft() {
        return !turnedRight;
    }

    /**
     * Grow the hero, keeping the hero touching the bottom of the street
     */
    public void grow(double scaleAmount) {
        Point2D position = getPosition();
        bulletList.forEach(bullet -> bullet.scale(scaleAmount));
        scale(scaleAmount);
        setPosition(position);
    }

    /**
     * Shrink the hero, keeping the hero touching the bottom of the street
     */
    public void shrink(double scaleAmount) {
        Point2D position = getPosition();
        bulletList.forEach(bullet -> bullet.scale(scaleAmount));
        scale(scaleAmount);
        setPosition(position);
    }

    /**
     * Cancel the bullet timer
     */
    @Override
    public void destroy() {
        timer.cancel();
    }

    /**
     * Set the platform that the hero is currently on
     * @param platform
     */
    public void setPlatform(Sprite platform) {
        this.platform = platform;
    }

    /// Get the platform
    public Sprite getPlatform() {
        return platform;
    }
}