package game_engine;

public abstract class CollisionManager {

    public static boolean spritesColliding(Sprite sprite1, Sprite sprite2) {
        return !(sprite1.get_bottom() < sprite2.get_top() ||
                 sprite2.get_bottom() < sprite1.get_top() ||
                 sprite1.get_right() < sprite2.get_left() ||
                 sprite2.get_right() < sprite2.get_left());
    }
}
