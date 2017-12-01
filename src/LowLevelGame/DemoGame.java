package LowLevelGame;

import game_engine.BoundaryAction;
import game_engine.Game;
import game_engine.Sprite;
import game_engine.SpriteManager;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * DemoGame
 *
 * A game to demonstrate usage of my game engine.
 *
 * This is a platform game where the Hero is static in the Y direction, is able to jump,
 * and is able to shoot. The rest of the scene (buildings, background, bad guys, and platforms)
 * move relative to the character. If the good guy hits a bad guy, the game stops and is over. If
 * the hero makes if to the end of the level, the game is over.
 */
public class DemoGame extends Game {
    private final static Dimension2D GAME_DIMENSIONS = new Dimension2D(1600, 1000);

    /// Variables defining positions of sprites
    private final static double BUILDING_BOTTOM = 700;
    private final static double BUILDING_SPREAD = 300;
    private final static double START = -400;
    private final static double HERO_BOTTOM = BUILDING_BOTTOM+50;
    private final static double GAME_PLAY_UPPER = 450;
    private final static double GAME_PLAY_HEIGHT = 300;
    private final static double GAME_PLAY_WIDTH = 20000;

    /// Variables defining number of sprites
    private final static int NUM_BUILDINGS = 50;
    private final static int NUM_ROAD_PIECES = 80;
    private final static int NUM_BAD_GUYS = 40;

    /// The Hero's max speed
    private final static double MAX_SPEED = 40;

    /// The Hero
    private Hero hero;

    /// Bullets that the hero can shoot
    private List<Bullet> bullets;

    /// All the scene objects will be aggregated into this list
    private List<Sprite> allScene = new ArrayList<>();

    /// The buildings of the scen
    private Vector<Sprite> buildings = new Vector<>();

    /// The road pieces of the scene
    private Vector<Sprite> roadPieces = new Vector<>();

    /// The bad guys of the scene
    private List<BadGuy> badGuys;

    /// The platforms of the scene
    private List<Platform> platforms = new ArrayList<>();

    /**
     * Build all sprites and define all actions
     */
    public void init() {
        buildSky();
        buildRoad();
        buildBuildings();
        buildBadGuys();
        buildPlatforms();
        buildHero();

        addBulletActions();
        addSceneActions();
        addHeroActions();
    }

    /**
     * Build the sky object
     */
    private void buildSky() {
        addSprite(new Background("sky1.png", GAME_DIMENSIONS));
    }

    /**
     * Create a long line of road pieces and add all the pieces to the game
     */
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

    /**
     * Create a long line of randomly spaced buildings and add them to the game
     */
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

    /**
     * Create many hidden bullets, create a hero, and add all the bullets to the hero
     */
    private void buildHero() {
        bullets = Stream.generate(Bullet::new).limit(100).collect(Collectors.toList());
        bullets.forEach(Bullet::hide);
        bullets.forEach(bullet -> bullet.setBoundaryAction(BoundaryAction.DIE));
        addSprites(bullets);

        hero = new Hero(bullets);
        hero.setSize(new Dimension2D(80,80))
                .setBottom(HERO_BOTTOM)
                .setX(400);
        addSprite(hero);
    }

    /**
     * Build the bad guys, randomly place them in the game, and add all the bad guys to the scene
     */
    private void buildBadGuys() {
        badGuys = Stream.generate(BadGuy::new).limit(NUM_BAD_GUYS).collect(Collectors.toList());
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

    /**
     * Place the platforms such that each one is holding a bad guy
     */
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

    /**
     * Check for collisions between the hero and bad guys.
     * Also check for a bad guy being hit by a bullet
     */
    private void addBulletActions() {
        addAction(
                () -> badGuys.forEach(badGuy ->
                        bullets.forEach(bullet -> {
                            if (SpriteManager.spritesColliding(bullet, badGuy)) {
                                badGuy.hit();
                                bullet.hide();
                            }
                        })
                )
        );

        addAction(
                () -> badGuys.forEach(badGuy -> {
                    if (badGuy.isHit()) {
                        badGuy.rotate(10);
                        badGuy.addVector(180, 1);
                    }
                })
        );

    }

    /**
     * Add various hero actions
     */
    private void addHeroActions() {
        /// Jump if the up arrow is pressed
        addKeyAction(KeyCode.UP, () -> {
            if (hero.getBottom() >= HERO_BOTTOM)
                hero.jump();
        });

        /// Fire if the space bar is pressed
        addKeyAction(KeyCode.SPACE, hero::fire);

        /// Shrink or grow the hero if G or S is pressed
        addKeyAction(KeyCode.G, () -> hero.grow(1.1));
        addKeyAction(KeyCode.S, () -> hero.shrink(0.9));

        /// Add gravity to the hero and stop the hero from falling if he hits the street
        addAction(() -> {
            if (hero.getBottom() > HERO_BOTTOM) {
                hero.setVelocity(new Point2D(0,0));
                hero.setBottom(HERO_BOTTOM);
            } else {
                hero.addVector(180, 2);
            }
        });

        /// If the hero is falling and is on a sprite, set the hero's current platform to the platform
        /// he landed on
        addAction(() -> platforms.forEach(platform -> {
            if (SpriteManager.spriteOnSprite(hero, platform) && hero.getVelocity().getY() > 0) {
                hero.setPlatform(platform);
            }
        }));

        /// If the hero has a platform and is above the platform, set the hero to the top of the platform,
        /// Else, take the platform away from the hero
        addAction(() -> {
            if (hero.getPlatform() != null && hero.getBottom() > hero.getPlatform().getTop()) {
                hero.setVelocity(new Point2D(0,0));
                hero.setBottom(hero.getPlatform().getTop());
            } else {
                hero.setPlatform(null);
            }
        });

        /// If the hero hits a bad guy, stop the game
        addAction(() -> badGuys.forEach(badGuy -> {
            if (SpriteManager.spritesColliding(hero, badGuy) && !badGuy.isHit()) {
                stop();
            }
        }));
    }

    /**
     * Add various scene actions
     */
    private void addSceneActions() {
        /// If the right arrow is pressed, turn the hero and move the scene
        addKeyAction(
                KeyCode.RIGHT,
                () -> {
                    if (hero.turnedLeft()) hero.turnRight();
                    if (buildings.firstElement().getVelocity().getX() > -MAX_SPEED){
                        allScene.forEach(sprite -> sprite.addVector(-90, 1));
                    }
                }
        );

        /// If the left arrow is pressed, turn the hero and move the scene
        addKeyAction(KeyCode.LEFT,
                () -> {
                    if (hero.turnedRight()) hero.turnLeft();
                    if (buildings.firstElement().getVelocity().getX() < MAX_SPEED){
                        allScene.forEach(sprite -> sprite.addVector(90, 1));
                    }
                }
        );

        /// If the first building is too far to the left, stop the scene from moving
        addConditionalAction(
                () -> buildings.firstElement().getX() > -50,
                () -> allScene.forEach(building -> {
                    building.setVelocity(new Point2D(0,0));
                    building.setX(building.getX()-1);
                })
        );

        /// If the last building is too far to the right, stop the scene from moving
        addConditionalAction(
                () -> buildings.lastElement().getX() < GAME_DIMENSIONS.getWidth()+50,
                () -> allScene.forEach(sprite -> {
                    sprite.setVelocity(new Point2D(0,0));
                    sprite.setX(sprite.getX()+1);
                })
        );

        /// If the last building is moving to the right, add friction to the left
        addConditionalAction(
                () -> buildings.lastElement().getVelocity().getX() > 0,
                () -> allScene.forEach(sprite -> sprite.addVector(-90, .5))
        );

        /// If the last building is moving to the left, add friction to the right
        addConditionalAction(
                () -> buildings.lastElement().getVelocity().getX() < 0,
                () -> allScene.forEach(sprite -> sprite.addVector(90, .5))
        );

        /// If the scene is moving slow enough, stop the sprite
        addConditionalAction(
                () -> Math.abs(buildings.lastElement().getVelocity().getX()) < .1,
                () -> allScene.forEach(sprite -> sprite.setVelocity(new Point2D(0,0)))
        );
    }

    /// Return the dimensions to make them available to the game engine
    public Dimension2D getDimensions() {
        return GAME_DIMENSIONS;
    }
}
