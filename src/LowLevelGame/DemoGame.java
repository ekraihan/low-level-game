package LowLevelGame;

import com.sun.tools.hat.internal.util.VectorSorter;
import game_engine.Game;
import game_engine.Sprite;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class DemoGame extends Game {
    private final static Dimension2D GAME_DIMENSIONS = new Dimension2D(1600, 1000);

    private final static int STREET = 730;
    private final static double BUILDING_BOTTOM = 700;
    private final static double BUILDING_SPREAD = 100;
    private final static double START = -200;
    private final static double HERO_BOTTOM = BUILDING_BOTTOM+50;
    private final static double GAME_PLAY_UPPER = 450;
    private final static double GAME_PLAY_HEIGHT = 300;
    private final static double GAME_PLAY_WIDTH = 20000;

    private final static int NUM_BUILDINGS = 100;
    private final static int NUM_ROAD_PIECES = 50;
    private final static int NUM_PLATFORMS = 50;
    private final static double MAX_SPEED = 40;

    private Hero hero;

    private List<Sprite> allScene = new ArrayList<>();

    private Vector<Sprite> buildings = new Vector<>();
    private Vector<Sprite> roadPieces = new Vector<>();
    private List<BadGuy> badGuys;
    private List<Platform> platforms = new ArrayList<>();

    public void init() {
        buildSky();
        buildRoad();
        buildBuildings();
        buildBadGuys();
        buildPlatforms();
        buildHero();

        addSceneActions();
        addHeroActions();
    }

    private void buildSky() {
        addSprite(new Background("sky1.png", GAME_DIMENSIONS));
    }

    private void buildRoad() {
        double roadStart = START-100 ;
        for (int i = 0; i < NUM_ROAD_PIECES; i++) {
            Road road = new Road(BUILDING_BOTTOM+100, roadStart+= 200);
            road.setFitWidth(600);
            roadPieces.add(road);
        }

        addSprites(roadPieces);
        allScene.addAll(roadPieces);
    }

    private void buildBuildings() {
        Random random = new Random();
        double buildingStart = START;
        for (int i = 0; i < NUM_BUILDINGS; i++) {
            buildings.add(
                    random.nextBoolean() ?
                            new Tower(BUILDING_BOTTOM, buildingStart+=BUILDING_SPREAD) :
                            new House(BUILDING_BOTTOM, buildingStart+=BUILDING_SPREAD)
            );
        }
        addSprites(buildings);
        allScene.addAll(buildings);
    }

    private void buildHero() {
        List<Bullet> bullets = Stream.generate(Bullet::new).limit(100).collect(Collectors.toList());
        bullets.forEach(Bullet::hide);
        addSprites(bullets);

        hero = new Hero(bullets);
        hero.setSize(new Dimension2D(80,80))
                .setBottom(HERO_BOTTOM)
                .setX(400);
        addSprite(hero);
    }

    private void buildBadGuys() {
        badGuys = Stream.generate(BadGuy::new).limit(NUM_PLATFORMS).collect(Collectors.toList());
        badGuys.forEach(
                badGuy -> badGuy.setPosition(
                        new Point2D(
                                new Random().nextDouble()*GAME_PLAY_WIDTH+GAME_DIMENSIONS.getWidth(),
                                new Random().nextDouble()*GAME_PLAY_HEIGHT + GAME_PLAY_UPPER
                        )
                )
        );
        allScene.addAll(badGuys);
    }

    private void buildPlatforms() {
        badGuys.forEach(badGuy -> {
            Platform platform = new Platform();
            platform.setTop(badGuy.getBottom()).setX(badGuy.getX() - new Random().nextDouble()*400);
            platforms.add(platform);
        });

        addSprites(platforms);
        addSprites(badGuys);
        allScene.addAll(platforms);
    }

    private void addHeroActions() {
        addKeyAction(KeyCode.UP, () -> {
            System.out.println(hero.getY());
            if (hero.getBottom() >= HERO_BOTTOM)
                hero.jump();
        });
        addKeyAction(KeyCode.SPACE, hero::fire);

        addAction(() -> {
            if (hero.getBottom() > HERO_BOTTOM) {
                hero.setVelocity(new Point2D(0,0));
                hero.setBottom(HERO_BOTTOM);
            } else {
                hero.addVector(180, 2);
            }
        });

        addKeyAction(KeyCode.G, () -> hero.grow(1.01));
        addKeyAction(KeyCode.S, () -> hero.shrink(0.99));
    }

    private void addSceneActions() {
        addKeyAction(
                KeyCode.RIGHT,
                () -> {
                    if (hero.turnedLeft()) hero.turnRight();
                    if (buildings.firstElement().getVelocity().getX() > -MAX_SPEED){
                        allScene.forEach(sprite -> sprite.addVector(-90, 1));
                    }
                }
        );

        addKeyAction(KeyCode.LEFT,
                () -> {
                    if (hero.turnedRight()) hero.turnLeft();
                    if (buildings.firstElement().getVelocity().getX() < MAX_SPEED){
                        allScene.forEach(sprite -> sprite.addVector(90, 1));
                    }
                }
        );

        addConditionalAction(
                () -> buildings.firstElement().getX() > -50,
                () -> allScene.forEach(building -> {
                    building.setVelocity(new Point2D(0,0));
                    building.setX(building.getX()-1);
                })
        );

        addConditionalAction(
                () -> buildings.lastElement().getX() < GAME_DIMENSIONS.getWidth()+50,
                () -> allScene.forEach(sprite -> {
                    sprite.setVelocity(new Point2D(0,0));
                    sprite.setX(sprite.getX()+1);
                })
        );

        addConditionalAction(
                () -> buildings.lastElement().getVelocity().getX() > 0,
                () -> allScene.forEach(sprite -> sprite.addVector(-90, .5))
        );

        addConditionalAction(
                () -> buildings.lastElement().getVelocity().getX() < 0,
                () -> allScene.forEach(sprite -> sprite.addVector(90, .5))
        );

        addConditionalAction(
                () -> Math.abs(buildings.lastElement().getVelocity().getX()) < .1,
                () -> allScene.forEach(sprite -> sprite.setVelocity(new Point2D(0,0)))
        );
    }

    public Dimension2D getDimensions() {
        return GAME_DIMENSIONS;
    }


}
