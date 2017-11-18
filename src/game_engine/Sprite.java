package game_engine;

import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Sprite extends ImageView {
    private Point2D velocity_, acceleration_;
    private Point2D image_direction_;
    private Dimension2D dimensions_;
    private State state;
    private BoundaryAction boundaryAction;

    public enum BoundaryAction {
        DIE, CONTINUE, BOUNCE
    }

    public enum State {
        HIDDEN, DEAD, ALIVE
    }

    public Sprite(String image_source) {
        Image image = new Image(image_source);
        setImage(image);
        setFitHeight(image.getHeight());
        setFitWidth(image.getWidth());

        velocity_ = new Point2D(0,0);
    }

    public Sprite set_image_(String image_source) {
        this.setImage(new Image(image_source));
        return this;
    }

    public Point2D get_position() {
        return new Point2D(getX()+getFitWidth()/2, getY()+getFitHeight());
    }

    public void set_position(Point2D point) {
        setX(point.getX()-getFitWidth()/2);
        setY(point.getY()-getFitHeight()/2);
    }

    public double get_top() {
        return getY();
    }

    public double get_right() {
        return getX() + getFitWidth();
    }

    public double get_left() {
        return getX();
    }

    public double get_bottom() {
        return getY() + getFitHeight();
    }

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

    public State get_state() {
        return state;
    }

    public BoundaryAction get_boundary_action() {
        return boundaryAction;
    }

    final void update() {
        setX(getX() + velocity_.getX());
        setY(getY() + velocity_.getY());
    }

}
