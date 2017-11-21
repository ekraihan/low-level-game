package LowLevelGame;

import game_engine.BoundaryAction;
import game_engine.CollisionManager;
import game_engine.Game;
import game_engine.Sprite;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DemoGame extends Game {
    private static final String tower_image = "tower.png";
    private static final String house_image = "house.png";

    Building tower = new Building(tower_image);
    Building tower2 = new Building(tower_image);
    Building tower3 = new Building(tower_image);
    Building house = new Building(house_image);
    Building house2 = new Building(house_image);
    Building house3 = new Building(house_image);
    Guy dog =  new Guy("dogconcept.PNG");

    List<Sprite> spriteList;

    public void init() throws CloneNotSupportedException {
        addSprite(new Sprite("sky1.png")
                .setSize(new Dimension2D(.74,.74))
                .setPosition(new Point2D(500,300)));
        spriteList = Arrays.asList(tower,tower2,tower3,house,house2, house3, new Sprite("road.png")
                .setPosition(new Point2D(500, 730))
                .setSize(new Dimension2D(1,10)));

        dog.setPosition(new Point2D(500,720))
            .setSize(new Dimension2D(.14,.14));

        tower.setPosition(new Point2D(300,500))
            .setSize(new Dimension2D(0.5,0.5));
        house.setPosition(new Point2D(500,600))
            .setSize(new Dimension2D(4,4));

        house2.setPosition(new Point2D(100,600))
                .setSize(new Dimension2D(4,4));
        house3.setPosition(new Point2D(1200,600))
                .setSize(new Dimension2D(4,4));

        tower2.setPosition(new Point2D(700,500))
                .setSize(new Dimension2D(0.5,0.5));
        tower3.setPosition(new Point2D(1000,500))
                .setSize(new Dimension2D(0.5,0.5));

        addSprites(spriteList);

        addSprite(dog);

        addKeyAction(KeyCode.RIGHT, () -> spriteList.forEach(sprite -> sprite.addVector(-90, 1)));
        addKeyAction(KeyCode.LEFT, () -> spriteList.forEach(sprite -> sprite.addVector(90, 1)));

    }

    public Dimension2D getDimensions() {
        return new Dimension2D(1000,800);
    }
}
