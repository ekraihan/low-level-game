package game_engine;

/**
 * SpriteManage
 *
 * A helper class that can be used to manage sprite and game interactions
 */
public abstract class SpriteManager {

    /**
     * Assume the sprites are not colliding. If they are both visible, assume they are colliding.
     * If they meet any condition that implies they are not colliding, set colliding to false
     *
     * @return          whether or not the sprites are colliding
     */
    public static boolean spritesColliding(Sprite sprite1, Sprite sprite2) {
        boolean colliding = false;
        if (sprite1.isVisible() && sprite2.isVisible()) {
            colliding = true;
            if (sprite1.getBottom() < sprite2.getTop() ||
                    sprite2.getBottom() < sprite1.getTop() ||
                    sprite1.getRight() < sprite2.getLeft() ||
                    sprite2.getRight() < sprite1.getLeft()) {
                colliding = false;
            }
        }
       return colliding;
    }

    /**
     * Determine whether or not the sprite is off the screen
     */
    public static boolean spriteOffScreen(Game game, Sprite sprite) {
        return  spriteOffBottom(game, sprite) ||
                spriteOffTop(game, sprite) ||
                spriteOffLeft(game, sprite) ||
                spriteOffRight(game, sprite);
    }

    /**
     * Determine whether or not the sprite touching a side
     */
    public static boolean spriteTouchingSide(Game game, Sprite sprite) {
        return  spriteTouchingLeft(game, sprite) ||
                spriteTouchingBottom(game, sprite) ||
                spriteTouchingRight(game, sprite) ||
                spriteTouchingTop(game, sprite);
    }

    /**
     * Determine whether or not the sprite is touching the bottom of the screen
     */
    public static boolean spriteTouchingBottom(Game game, Sprite sprite) {
        return sprite.getBottom() > game.getHeight() && !spriteOffBottom(game,sprite);
    }

    /**
     * Determine whether or not the sprite is touching the right of the screen
     */
    public static boolean spriteTouchingRight(Game game, Sprite sprite) {
        return sprite.getRight() > game.getWidth() && !spriteOffRight(game, sprite);
    }

    /**
     * Determine whether or not the sprite is touching the left of the screen
     */
    public static boolean spriteTouchingLeft(Game game, Sprite sprite) {
        return sprite.getLeft() < 0 && !spriteOffLeft(game,sprite);
    }

    /**
     * Determine whether or not the sprite is touching the top of the screen
     */
    public static boolean spriteTouchingTop(Game game, Sprite sprite) {
        return sprite.getTop() < 0 && !spriteOffTop(game, sprite);
    }

    /**
     * Determine whether or not the `top` sprite is resting on top of the `bottom` sprite
     */
    public static boolean spriteOnSprite(Sprite top, Sprite bottom) {
        return  top.getBottom() > bottom.getTop()-8 &&
                top.getBottom() < bottom.getTop()+8&&
                top.getRight() > bottom.getLeft() &&
                top.getLeft() < bottom.getRight();
    }

    /**
     * Determine whether or not the sprite is off the bottom of the screen
     */
    public static boolean spriteOffBottom(Game game, Sprite sprite) {
        return sprite.getTop() > game.getHeight();
    }

    /**
     * Determine whether or not the sprite is off the right of the screen
     */
    public static boolean spriteOffRight(Game game, Sprite sprite) {
        return sprite.getLeft() > game.getWidth();
    }

    /**
     * Determine whether or not the sprite is off the left of the screen
     */
    public static boolean spriteOffLeft(Game game, Sprite sprite) {
        return sprite.getRight() < 0;
    }

    /**
     * Determine whether or not the sprite is off the top of the screen
     */
    public static boolean spriteOffTop(Game game, Sprite sprite) {
        return sprite.getBottom() < 0;
    }
}
