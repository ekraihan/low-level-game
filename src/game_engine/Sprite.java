package game_engine;

import javafx.animation.Animation;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Sprite extends ImageView {
    private Point2D velocity_, acceleration_;
    private Point2D image_direction_;
    private Dimension2D dimensions_;

    public enum BoundaryAction {
        DIE, CONTINUE, BOUNCE
    }

    public enum State {
        HIDDEN, DEAD, VISIBLE
    }

    public Sprite(String image_source) {
        this.setImage(new Image(image_source));
    }

    public Sprite set_image_(String image_source) {
        this.setImage(new Image(image_source));
        return this;
    }

//    public Point2D get_position_() {
//        return position_;
//    }


    public Point2D get_image_direction_() {
        return image_direction_;
    }

    public Point2D get_velocity_() {
        return velocity_;
    }

    public Point2D get_acceleration_() {
        return acceleration_;
    }

    public Sprite add_vector(double force, double direction) {
        return this;
    }

    public Sprite set_velocity(Point2D velocity) {
        this.velocity_ = velocity;
        return this;
    }

    public Sprite change_image_angle_by() {
        return this;
    }

    public Sprite set_image_angle() {
        return this;
    }

    final void update() {
        setX(getX() + velocity_.getX());
        setY(getY() + velocity_.getY());
    }

}
