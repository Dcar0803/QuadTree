/**
 * Represents a rectangle with a position and dimensions.
 */
public class Rectangle {
    int x, y, width, height;

    /**
     * Initializes a rectangle with specified position and dimensions.
     *
     * @param x      X-coordinate of rectangle's top-left corner
     * @param y      Y-coordinate of rectangle's top-left corner
     * @param width  Width of the rectangle
     * @param height Height of the rectangle
     */
    public Rectangle(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public String toString() {
        return String.format("Rectangle at (%d, %d) with width=%d and height=%d", x, y, width, height);
    }
}
