package LowLevelGame;

import game_engine.Game;
import game_engine.Sprite;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;

import java.util.*;
import java.util.stream.Stream;

public class DemoGame extends Game {
    private final static Dimension2D GAME_DIMENSIONS = new Dimension2D(1600, 1000);

    private final static int STREET = 730;
    private final static double BUILDING_BOTTOM = 700;
    private final static double BUILDING_SPREAD = 100;
    private final static double START = -200;

    private final static int NUM_BUILDINGS = 100;
    private final static int NUM_ROAD_PIECES = 50;
    private final static int MAX_SPEED = 100;

    Hero hero =  new Hero("hero.PNG");

    Vector<Sprite> buildings = new Vector<>();
    Vector<Sprite> roadPieces = new Vector<>();

    public void init() {
        buildSky();
        buildRoad();
        buildBuildings();

        addSceneActions();



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
    }

    private void addSceneActions() {
        addKeyAction(
                KeyCode.RIGHT,
                () -> {
                    if (buildings.firstElement().getVelocity().getX() > -MAX_SPEED){
                        buildings.forEach(sprite -> sprite.addVector(-90, 1));
                        roadPieces.forEach(sprite -> sprite.addVector(-90, 1));
                    }
                }
        );

        addKeyAction(KeyCode.LEFT,
                () -> {
                    if (buildings.firstElement().getVelocity().getX() < MAX_SPEED){
                        buildings.forEach(sprite -> sprite.addVector(90, 1));
                        roadPieces.forEach(sprite -> sprite.addVector(90, 1));
                    }
                }
        );

        addConditionalAction(
                () -> buildings.firstElement().getX() > -50,
                () -> {
                    buildings.forEach(building -> {
                        building.setVelocity(new Point2D(0,0));
                        building.setX(building.getX()-1);

                    });
                    roadPieces.forEach(roadPiece -> {
                        roadPiece.setVelocity(new Point2D(0,0));
                        roadPiece.setX(roadPiece.getX()-1);
                    });
                }
        );

        addConditionalAction(
                () -> buildings.lastElement().getX() < GAME_DIMENSIONS.getWidth()+50,
                () -> {
                    buildings.forEach(building -> {
                        building.setVelocity(new Point2D(0,0));
                        building.setX(building.getX()+1);
                    });
                    roadPieces.forEach(roadPiece -> {
                        roadPiece.setVelocity(new Point2D(0,0));
                        roadPiece.setX(roadPiece.getX()+1);
                    });
                }
        );
    }

    public Dimension2D getDimensions() {
        return GAME_DIMENSIONS;
    }


}
