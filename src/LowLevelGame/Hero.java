package LowLevelGame;

import game_engine.Sprite;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Hero extends Sprite {
    private final static String HERO_RIGHT = "heroRight.png";
    private final static String HERO_LEFT = "heroLeft.png";
    private boolean turnedRight = true;
    private Sprite platform = null;

    private List<Bullet> bulletList;// = Stream.generate(() -> new Bullet().hide()).limit(100).collect(Collectors.toList());
    private int currentBullet = 0;
    private Timer timer = new Timer();
    private boolean canFire = false;

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

    public void jump() {
        addVector(0, 26);
    }

    public void fire() {
        if (canFire) {
            double direction = turnedRight ? 90 : -90;
            currentBullet++;
            if (currentBullet >= bulletList.size()){
                currentBullet = 0;
            }
            bulletList.get(currentBullet).setPosition(getPosition())
                    .addVector(direction, 17)
                    .show();
            canFire = false;
        }
    }

    public void turnLeft() {
        turnedRight = false;
        Dimension2D size = getSize();
        Point2D position = getPosition();
        setImage(HERO_LEFT);
        setSize(size);
        setPosition(position);
    }

    public void turnRight() {
        turnedRight = true;
        Dimension2D size = getSize();
        Point2D position = getPosition();
        setImage(HERO_RIGHT);
        setSize(size);
        setPosition(position);
    }

    public boolean turnedRight() {
        return turnedRight;
    }

    public boolean turnedLeft() {
        return !turnedRight;
    }

    public void grow(double scaleAmount) {
        Point2D position = getPosition();
        bulletList.forEach(bullet -> bullet.scale(scaleAmount));
        scale(scaleAmount);
        setPosition(position);
    }

    public void shrink(double scaleAmount) {
        Point2D position = getPosition();
        bulletList.forEach(bullet -> bullet.scale(scaleAmount));
        scale(scaleAmount);
        setPosition(position);
    }

    @Override
    public void destroy() {
        timer.cancel();
    }

    public void setPlatform(Sprite platform) {
        this.platform = platform;
    }

    public Sprite getPlatform() {
        return platform;
    }
}