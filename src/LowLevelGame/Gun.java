package LowLevelGame;


import game_engine.Sprite;

public class Gun extends Sprite{
    public Gun(String image_source) {
        super(image_source);
    }

    public void fire() {
       System.out.println("FIRE!!!");
   }
}
