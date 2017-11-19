package game_engine;

import javafx.geometry.Point2D;

import java.util.function.BiConsumer;

import static game_engine.CollisionManager.*;

public enum BoundaryAction {
        DIE((game, sprite) -> {
                if (CollisionManager.spriteOffScreen(game, sprite)){
                        sprite.kill();
                }
        }),

        CONTINUE((game, sprite) -> {}),

        BOUNCE((game, sprite) -> {
                if (spriteTouchingBottom(game, sprite) || spriteTouchingTop(game,sprite))
                {
                        System.out.println("Touching Bottom Or Top");
                }

                if (spriteTouchingLeft(game, sprite) || spriteTouchingRight(game,sprite))
                {
                        System.out.println("Touching Left Or Right");
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