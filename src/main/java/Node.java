/**
 * Abstract class representing a node in a QuadTree.
 */
public abstract class Node {
    int x, y, width, height;

    /**
     * Initializes a node with specified bounds.
     *
     * @param x      X-coordinate of node's origin
     * @param y      Y-coordinate of node's origin
     * @param width  Width of the area covered by the node
     * @param height Height of the area covered by the node
     */
    public Node(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public abstract Node insert(Rectangle rect);
    public abstract Rectangle find(int x, int y);
    public abstract boolean delete(int x, int y);
}
