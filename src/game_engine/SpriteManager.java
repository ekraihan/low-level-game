package game_engine;

public abstract class SpriteManager {

    public static boolean spritesColliding(Sprite sprite1, Sprite sprite2) {
        return !(sprite1.getBottom() < sprite2.getTop() ||
                 sprite2.getBottom() < sprite1.getTop() ||
                 sprite1.getRight() < sprite2.getLeft() ||
                 sprite2.getRight() < sprite2.getLeft());
    }

    public static boolean spriteOffScreen(Game game, Sprite sprite) {
        return  spriteOffBottom(game, sprite) ||
                spriteOffTop(game, sprite) ||
                spriteOffLeft(game, sprite) ||
                spriteOffRight(game, sprite);
    }

    public static boolean spriteTouchingSide(Game game, Sprite sprite) {
        return  spriteTouchingLeft(game, sprite) ||
                spriteTouchingBottom(game, sprite) ||
                spriteTouchingRight(game, sprite) ||
                spriteTouchingTop(game, sprite);
    }

    public static boolean spriteTouchingBottom(Game game, Sprite sprite) {
        return sprite.getBottom() > game.getHeight() && !spriteOffBottom(game,sprite);
    }

    public static boolean spriteTouchingRight(Game game, Sprite sprite) {
        return sprite.getRight() > game.getWidth() && !spriteOffRight(game, sprite);
    }

    public static boolean spriteTouchingLeft(Game game, Sprite sprite) {
        return sprite.getLeft() < 0 && !spriteOffLeft(game,sprite);
    }

    public static boolean spriteTouchingTop(Game game, Sprite sprite) {
        return sprite.getTop() < 0 && !spriteOffTop(game, sprite);
    }

    public static boolean spriteOnSprite(Sprite top, Sprite bottom) {
        return  top.getBottom() > bottom.getTop()-8 &&
                top.getBottom() < bottom.getTop()+8&&
                top.getRight() > bottom.getLeft() &&
                top.getLeft() < bottom.getRight();
    }

    public static boolean spriteOffBottom(Game game, Sprite sprite) {
        return sprite.getTop() > game.getHeight();
    }

    public static boolean spriteOffRight(Game game, Sprite sprite) {
        return sprite.getLeft() > game.getWidth();
    }

    public static boolean spriteOffLeft(Game game, Sprite sprite) {
        return sprite.getRight() < 0;
    }

    public static boolean spriteOffTop(Game game, Sprite sprite) {
        return sprite.getBottom() < 0;
    }
}
