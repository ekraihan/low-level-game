package game_engine;

import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Sprite
 *
 * A class to be extended to provide sprite actions
 */
public class Sprite extends ImageView {
    /// The velocity of the sprite in vector format
    private Point2D velocity_;

    /// The direction the sprite is pointing in vector format
    private Point2D image_direction_;

    /// The sprite's current boundary action
    private BoundaryAction boundaryAction_;

    /// The size of the sprite
    private Dimension2D size_;

    /// The source of the sprite's image
    private String image_source_;

    /// A boolean saying whether or not the sprite is *visible* on the screen. A sprite
    /// can be not visible, but still be in the game
    private boolean visible_;

    /**
     * constructor
     *
     * set the boundary action to continue and start the sprite off not moving
     */
    protected Sprite() {
        boundaryAction_ = BoundaryAction.CONTINUE;
        velocity_ = new Point2D(0,0);
    }

    /**
     * overloaded constructor
     *
     * set the sprite's image, set the boundary action to continue, and start the sprite off not moving
     * @param         image_source          the source of the sprite's image
     */
    public Sprite(String image_source) {
        image_source_ = image_source;
        setImage(image_source);
        boundaryAction_ = BoundaryAction.CONTINUE;
        velocity_ = new Point2D(0,0);
    }

    /**
     * set the sprite's boundary action
     */
    public final Sprite setBoundaryAction(BoundaryAction boundaryAction) {
        this.boundaryAction_ = boundaryAction;
        return this;
    }

    /**
     * set the sprite's image and resize the sprite's "fit" dimensions to match the image's dimensions
     */
    public final Sprite setImage(String image_source) {
        image_source_ = image_source;
        Image image = new Image(image_source);
        setImage(image);
        setFitHeight(image.getHeight());
        setFitWidth(image.getWidth());
        return this;
    }

    /**
     * get the x and y of the *center* of the sprite
     */
    public final Point2D getPosition() {
        return new Point2D(getX()+getFitWidth()/2, getY()+getFitHeight()/2);
    }

    /**
     * set the x and y of the *center* of the sprite
     */
    public final Sprite setPosition(Point2D point) {
        setX(point.getX()-getFitWidth()/2);
        setY(point.getY()-getFitHeight()/2);
        return this;
    }

    /**
     * get the y of the top of the sprite
     */
    public final double getTop() {
        return getY();
    }

    /**
     * get the x of the right of the sprite
     */
    public final double getRight() {
        return getX() + getFitWidth();
    }

    /**
     * get the x of the left of the sprite
     */
    public final double getLeft() {
        return getX();
    }

    /**
     * get the y of the bottom of the sprite
     */
    public final double getBottom() {
        return getY() + getFitHeight();
    }

    /**
     * set the y of the bottom of the sprite
     */
    public final Sprite setBottom(double bottom) {
        setY(bottom-getFitHeight());
        return this;
    }

    /**
     * get the y of the top of the sprite
     */
    public final Sprite setTop(double top) {
        setY(top);
        return this;
    }

    /**
     * get the sprite's image direction
     */
    public final Point2D getImageDirection() {
        return image_direction_;
    }

    /**
     * Apply the the given force in the given direction
     *
     * @param           direction           the direction in which to apply the force
     * @param           force               the amount of force to apply
     * @return
     */
    public final Sprite addVector(double direction, double force) {
        direction -= 90;
        double angle = direction * (Math.PI / 180);
        velocity_ = velocity_.add(Math.cos(angle)*force, Math.sin(angle)*force);
        return this;
    }

    /**
     * set the sprite's velocity as a vector
     */
    public final Sprite setVelocity(Point2D velocity) {
        this.velocity_ = velocity;
        return this;
    }

    /**
     * get the sprite's velocity as a vector
     */
    public final Point2D getVelocity() {
        return velocity_;
    }

    /**
     * Change the sprite's size, with the scaling centering around the center of the sprite
     * @param               dimensions              The sprite's new dimensions
     */
    public final Sprite setSize(Dimension2D dimensions) {
        size_ = dimensions;
        Point2D currentPosition = getPosition();
        setFitHeight(dimensions.getHeight());
        setFitWidth(dimensions.getWidth());
        setY(currentPosition.getY() - getFitHeight()/2);
        setX(currentPosition.getX() - getFitWidth()/2);
        return this;
    }

    /**
     * Get the size of the sprite
     */
    public Dimension2D getSize() {
        return size_;
    }

    /**
     * Rotate the sprite by `amount`
     */
    public final Sprite rotate(double amount) {
        setRotate(getRotate() + amount);
        return this;
    }

    /**
     * set the image angle
     */
    public final Sprite setImageAngle(double angle) {
        setRotate(angle-90);
        return this;
    }

    /**
     * Set the image size to the current size times some scale amount
     */
    public final void scale(double scaleAmount) {
        setSize(new Dimension2D(fitWidthProperty().get()*scaleAmount, fitHeightProperty().get()*scaleAmount));
    }

    /**
     * take away the image
     * @return
     */
    public final Sprite hide() {
        imageProperty().setValue(null);
        setVisible(false);
        return this;
    }

    /**
     * add the image
     */
    public final void show() {
        imageProperty().setValue(new Image(image_source_));
        setVisible(true);
    }

    /**
     * get the boundary action
     */
    public final BoundaryAction getBoundaryAction() {
        return boundaryAction_;
    }

    /**
     * flip the visibility on if it is currently off, and vice versa
     */
    public final void toggleVisiblity() {
        if (imageProperty().get() == null)
            show();
        else
            hide();
    }

    /**
     * change the sprite's x and y based off of the velocity
     */
    final void update() {
        setX(getX() + velocity_.getX());
        setY(getY() + velocity_.getY());
    }

    /**
     * a method to be overwritten that cleans up the sprite
     */
    public void destroy() { }

    /**
     * stop the sprite and hide it
     */
    protected final void kill() {
        setVelocity(new Point2D(0,0));
        hide();
    }

    @Override
    public boolean isResizable() {
        return true;
    }
}
