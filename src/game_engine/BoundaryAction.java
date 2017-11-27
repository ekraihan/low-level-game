package game_engine;

import javafx.geometry.Point2D;
import javafx.scene.transform.Affine;

import java.util.function.BiConsumer;

import static game_engine.SpriteManager.*;

public enum BoundaryAction {
        DIE((game, sprite) -> {
                if (SpriteManager.spriteOffScreen(game, sprite)){
                        sprite.kill();
                }
        }),

        CONTINUE((game, sprite) -> {}),

        BOUNCE((game, sprite) -> {
                if (spriteTouchingBottom(game, sprite) || spriteTouchingTop(game,sprite))
                {
                        Affine matrix = new Affine(1, 0, 0, 0, -1, 0);
                        sprite.setVelocity(matrix.transform(sprite.getVelocity()));
                }

                if (spriteTouchingLeft(game, sprite) || spriteTouchingRight(game,sprite))
                {
                        Affine matrix = new Affine(-1, 0, 0, 0, 1, 0);
                        sprite.setVelocity(matrix.transform(sprite.getVelocity()));
                }
        }),

        WRAP((game, sprite) -> {
                if (spriteOffLeft(game,sprite))
                        sprite.setX(game.getWidth());

                if (spriteOffRight(game ,sprite))
                        sprite.setX(-sprite.getFitWidth());

                if (spriteOffTop(game ,sprite))
                        sprite.setY(game.getHeight());

                if (spriteOffBottom(game,sprite))
                        sprite.setY(-sprite.getFitHeight());
        }),

        STOP((game, sprite) -> {
                if (spriteTouchingSide(game, sprite))
                        sprite.setVelocity(new Point2D(0,0));
        });

        private BiConsumer<Game, Sprite> action;
        BoundaryAction(BiConsumer<Game, Sprite> action) {
                this.action = action;
        }

        public void check(Game game, Sprite sprite) {
                action.accept(game,sprite);
        }


}