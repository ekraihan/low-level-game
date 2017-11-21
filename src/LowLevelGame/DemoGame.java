package LowLevelGame;

import game_engine.BoundaryAction;
import game_engine.SpriteManager;
import game_engine.Game;
import game_engine.Sprite;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseDragEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DemoGame extends Game {
    private static final String tower_image = "tower.png";
    private static final String house_image = "house.png";

    private final static int STREET = 730;

    Building tower = new Building(tower_image, 700);
//    Building tower2 = new Building(tower_image);
//    Building tower3 = new Building(tower_image);
    Building house = new Building(house_image, 700);
//    Building house2 = new Building(house_image);
//    Building house3 = new Building(house_image);
    Guy hero =  new Guy("hero.PNG");

    List<Sprite> spriteList;

    public void init() throws CloneNotSupportedException {
        addSprite(new Sprite("sky1.png")
                .setSize(new Dimension2D(.74,.74))
                .setPosition(new Point2D(500,300)));
        spriteList = Arrays.asList(tower, house, new Sprite("road.png")
                .setPosition(new Point2D(500, 730))
                .setSize(new Dimension2D(1,10)));

        hero//.setPosition(new Point2D(500,720))
            .setSize(new Dimension2D(.14,.14));

        tower.setPosition(new Point2D(300,300))
            .setSize(new Dimension2D(0.5,0.5));
        house.setPosition(new Point2D(300,400))
            .setSize(new Dimension2D(4,4));

//        house2.setPosition(new Point2D(100,600))
//                .setSize(new Dimension2D(4,4));
//        house3.setPosition(new Point2D(1200,600))
//                .setSize(new Dimension2D(4,4));
//
//        tower2.setPosition(new Point2D(700,500))
//                .setSize(new Dimension2D(0.5,0.5));
//        tower3.setPosition(new Point2D(1000,500))
//                .setSize(new Dimension2D(0.5,0.5));

        addSprites(spriteList);

        addSprite(hero);

        addKeyAction(KeyCode.RIGHT, () -> spriteList.forEach(sprite -> sprite.addVector(-90, 1)));
        addKeyAction(KeyCode.LEFT, () -> spriteList.forEach(sprite -> sprite.addVector(90, 1)));
        addKeyAction(KeyCode.SPACE, hero::jump);
        addAction(() -> {
            if (hero.getBottom() > STREET) {
                hero.setVelocity(new Point2D(0,0));
            } else {
                hero.addVector(180, 2);
            }

        });

        addKeyAction(KeyCode.I, ()-> {
            tower.printProperties();
            house.printProperties();
        });

        addKeyAction(KeyCode.R, () -> tower.resize(20,20));



    }

    public Dimension2D getDimensions() {
        return new Dimension2D(1000,800);
    }
}
