package game_engine;

import javafx.animation.Animation;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by eliaskraihanzel on 11/11/17.
 */
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

    public void set_image_(String image_source) {
        this.setImage(new Image(image_source));
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

    public void add_vector(double force, double direction) {

    }

    public void set_velocity(Point2D velocity) {
        this.velocity_ = velocity;
    }

    public void change_image_angle_by() {

    }

    public void set_image_angle() {

    }

    public void update() {
        setX(getX() + velocity_.getX());
        setY(getY() + velocity_.getY());
    }

}
