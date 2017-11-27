package LowLevelGame;

import game_engine.Game;
import game_engine.Sprite;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;

import java.util.*;

public class DemoGame extends Game {
    private final static Dimension2D GAME_DIMENSIONS = new Dimension2D(1600, 1000);

    private final static int STREET = 730;
    private final static double BUILDING_BOTTOM = 700;
    private final static double BUILDING_SPREAD = 100;
    private final static double BUILDING_START = -200;

    private final int NUM_BUILDINGS = 500;
    Guy hero =  new Guy("hero.PNG");

    List<Sprite> buildings = new ArrayList<>();

    public void init() {
        Random random = new Random();
        double buildingStart = BUILDING_START;
        for (int i = 0; i < NUM_BUILDINGS; i++) {
            buildings.add(
                    random.nextBoolean() ?
                            new Tower(BUILDING_BOTTOM, buildingStart+=BUILDING_SPREAD) :
                            new House(BUILDING_BOTTOM, buildingStart+=BUILDING_SPREAD)
            );
        }

        addSprite(new Background("sky1.png", GAME_DIMENSIONS));
        Sprite road = new Sprite("road.png").setTop(BUILDING_BOTTOM-20);
        road.printProperties();
        addSprite(road);
        addSprites(buildings);




        addKeyAction(KeyCode.RIGHT, () -> buildings.forEach(sprite -> sprite.addVector(-90, 1)));
        addKeyAction(KeyCode.LEFT, () -> buildings.forEach(sprite -> sprite.addVector(90, 1)));
        addConditionalAction(
                () -> buildings.get(0).getX() > -50,
                () -> buildings.forEach(building -> {
                    building.setVelocity(new Point2D(0,0));
                    building.setX(building.getX()-1);
                })
        );
        addConditionalAction(
                () -> buildings.get(NUM_BUILDINGS-1).getX() < GAME_DIMENSIONS.getWidth()+50,
                () -> buildings.forEach(building -> {
                    building.setVelocity(new Point2D(0,0));
                    building.setX(building.getX()+1);
                })
        );

//        addKeyAction(KeyCode.SPACE, hero::jump);
//        addAction(() -> {
//            if (hero.getBottom() > STREET) {
//                hero.setVelocity(new Point2D(0,0));
//            } else {
//                hero.addVector(180, 2);
//            }
//
//        });

//        addKeyAction(KeyCode.I, ()-> {
//            tower.printProperties();
////            house.printProperties();
//        });
//
//        addKeyAction(KeyCode.U, () -> tower.scale(.9));
//        addKeyAction(KeyCode.D, () -> tower.scale(1.1));



    }

    public Dimension2D getDimensions() {
        return GAME_DIMENSIONS;
    }
}
