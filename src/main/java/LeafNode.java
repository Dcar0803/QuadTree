import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents a leaf node in a QuadTree, holding a list of rectangles within a bounded area.
 */
public class LeafNode extends Node {
    private List<Rectangle> rectangles;
    private Set<String> uniqueLocations;
    private static final int MAX_RECTANGLES = 5;

    /**
     * Initializes a LeafNode with given bounds.
     *
     * @param x      X-coordinate of node's origin
     * @param y      Y-coordinate of node's origin
     * @param width  Width of the area covered by the node
     * @param height Height of the area covered by the node
     */
    public LeafNode(int x, int y, int width, int height) {
        super(x, y, width, height);
        rectangles = new ArrayList<>();
        uniqueLocations = new HashSet<>();
    }

    /**
     * Inserts a rectangle if not already present. If the maximum rectangle threshold is exceeded, splits the node.
     *
     * @param rect Rectangle to insert
     * @return This node if no split, or a new InternalNode if split
     */
    @Override
    public Node insert(Rectangle rect) {
        String locationKey = rect.x + "," + rect.y;

        if (uniqueLocations.contains(locationKey)) {
            return this;
        }

        rectangles.add(rect);
        uniqueLocations.add(locationKey);

        if (rectangles.size() > MAX_RECTANGLES) {
            return split();
        }

        return this;
    }

    /**
     * Converts this node to an InternalNode if it exceeds its rectangle capacity.
     *
     * @return A new InternalNode containing the rectangles from this node
     */
    private InternalNode split() {
        InternalNode internalNode = new InternalNode(x, y, width, height);
        for (Rectangle rect : rectangles) {
            internalNode.insert(rect);
        }
        rectangles.clear();
        uniqueLocations.clear();
        return internalNode;
    }

    /**
     * Finds a rectangle at specified coordinates.
     *
     * @param x X-coordinate to search
     * @param y Y-coordinate to search
     * @return Rectangle at specified coordinates, or null if not found
     */
    @Override
    public Rectangle find(int x, int y) {
        for (Rectangle rect : rectangles) {
            if (rect.x == x && rect.y == y) return rect;
        }
        return null;
    }

    /**
     * Deletes a rectangle at given coordinates.
     *
     * @param x X-coordinate of rectangle to delete
     * @param y Y-coordinate of rectangle to delete
     * @return true if deletion occurred, false otherwise
     */
    @Override
    public boolean delete(int x, int y) {
        boolean removed = rectangles.removeIf(rect -> rect.x == x && rect.y == y);
        if (removed) {
            uniqueLocations.remove(x + "," + y);
        }
        return removed;
    }

    /**
     * Retrieves the list of rectangles in this node.
     *
     * @return List of rectangles in the leaf node
     */
    public List<Rectangle> getRectangles() {
        return rectangles;
    }

    @Override
    public String toString() {
        return "LeafNode at (" + x + ", " + y + ") with " + rectangles.size() + " rectangles";
    }
}
