package game_engine;

import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.concurrent.locks.Condition;

public class Sprite extends ImageView {
    private Point2D velocity_;
    private Point2D image_direction_;
    private BoundaryAction boundaryAction_;
    private Dimension2D size_;
    private String image_source_;

    protected Sprite() {
        boundaryAction_ = BoundaryAction.CONTINUE;
        velocity_ = new Point2D(0,0);
    }

    public Sprite(String image_source) {
        image_source_ = image_source;
        setImage(image_source);
        boundaryAction_ = BoundaryAction.CONTINUE;
        velocity_ = new Point2D(0,0);
    }

    public final Sprite setBoundaryAction(BoundaryAction boundaryAction) {
        this.boundaryAction_ = boundaryAction;
        return this;
    }

    public final Sprite setImage(String image_source) {
        image_source_ = image_source;
        Image image = new Image(image_source);
        setImage(image);
        setFitHeight(image.getHeight());
        setFitWidth(image.getWidth());
        return this;
    }

    @Override
    public boolean isResizable() {
        return true;
    }

    public final Point2D getPosition() {
        return new Point2D(getX()+getFitWidth()/2, getY()+getFitHeight()/2);
    }

    public final Sprite setPosition(Point2D point) {
        setX(point.getX()-getFitWidth()/2);
        setY(point.getY()-getFitHeight()/2);
        return this;
    }

    public final double getTop() {
        return getY();
    }

    public final double getRight() {
        return getX() + getFitWidth();
    }

    public final double getLeft() {
        return getX();
    }

    public final double getBottom() {
        return getY() + getFitHeight();
    }

    public final Sprite setBottom(double bottom) {
        setY(bottom-getFitHeight());
        return this;
    }

    public final Sprite setTop(double top) {
        setY(top);
        return this;
    }

    public final Point2D getImageDirection() {
        return image_direction_;
    }

    public final Point2D getVelocity() {
        return velocity_;
    }

    public final Sprite addVector(double direction, double force) {
        direction -= 90;
        double angle = direction * (Math.PI / 180);
        velocity_ = velocity_.add(Math.cos(angle)*force, Math.sin(angle)*force);
        return this;
    }

    public final Sprite setVelocity(Point2D velocity) {
        this.velocity_ = velocity;
        return this;
    }

    public final Sprite setSize(Dimension2D dimensions) {
        size_ = dimensions;
        Point2D currentPosition = getPosition();
        setFitHeight(dimensions.getHeight());
        setFitWidth(dimensions.getWidth());
        setY(currentPosition.getY() - getFitHeight()/2);
        setX(currentPosition.getX() - getFitWidth()/2);
        return this;
    }

    public Dimension2D getSize() {
        return size_;
    }

    public final Sprite rotate(double amount) {
        setRotate(getRotate() + amount);
        return this;
    }

    public final Sprite setImageAngle(double angle) {
        setRotate(angle-90);
        return this;
    }

    public final void scale(double scaleAmount) {
        setSize(new Dimension2D(fitWidthProperty().get()*scaleAmount, fitHeightProperty().get()*scaleAmount));
    }

    public final Sprite hide() {
        imageProperty().setValue(null);
        return this;
    }

    public final void show() {
        imageProperty().setValue(new Image(image_source_));
    }

    public final BoundaryAction getBoundaryAction() {
        return boundaryAction_;
    }

    public final void toggleVisiblity() {
        if (imageProperty().get() == null)
            show();
        else
            hide();
    }

    final void update() {
        setX(getX() + velocity_.getX());
        setY(getY() + velocity_.getY());
    }

    public void destroy() { }

    protected final void kill() {
        setVelocity(new Point2D(0,0));
        hide();
    }

    public final void printProperties() {
        System.out.println(
                "[Sprite]: " + image_source_ + "\n"
                + "[Velocity]: " + velocity_ + "\n"
                + "[Bottom]: " + getBottom() + "\n"
                + "[Position]: " + getPosition() + "\n"
                + "[Fit Height]: " + getFitHeight() + "\n"
        );
    }
}
