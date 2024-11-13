import java.util.List;

/**
 * Represents an internal node in a QuadTree, responsible for managing child nodes
 * that divide space into four quadrants.
 */
public class InternalNode extends Node {
    Node northeast, southeast, southwest, northwest;

    /**
     * Initializes an InternalNode with four child nodes representing quadrants.
     *
     * @param x      X-coordinate of the node's origin
     * @param y      Y-coordinate of the node's origin
     * @param width  Width of the area covered by the node
     * @param height Height of the area covered by the node
     */
    public InternalNode(int x, int y, int width, int height) {
        super(x, y, width, height);
        int halfWidth = width / 2;
        int halfHeight = height / 2;

        // Initialize four child leaf nodes
        northwest = new LeafNode(x, y, halfWidth, halfHeight);
        northeast = new LeafNode(x + halfWidth, y, halfWidth, halfHeight);
        southwest = new LeafNode(x, y + halfHeight, halfWidth, halfHeight);
        southeast = new LeafNode(x + halfWidth, y + halfHeight, halfWidth, halfHeight);
    }

    /**
     * Inserts a rectangle into the appropriate quadrant.
     *
     * @param rect Rectangle to insert
     * @return Node itself after insertion
     */
    @Override
    public Node insert(Rectangle rect) {
        if (rect.x < x + width / 2) {
            if (rect.y < y + height / 2) southwest = southwest.insert(rect);
            else northwest = northwest.insert(rect);
        } else {
            if (rect.y < y + height / 2) southeast = southeast.insert(rect);
            else northeast = northeast.insert(rect);
        }
        return this;
    }

    /**
     * Finds a rectangle at given coordinates.
     *
     * @param x X-coordinate of rectangle to find
     * @param y Y-coordinate of rectangle to find
     * @return Rectangle at specified coordinates, or null if not found
     */
    @Override
    public Rectangle find(int x, int y) {
        if (x < this.x + width / 2) {
            return (y < this.y + height / 2) ? southwest.find(x, y) : northwest.find(x, y);
        } else {
            return (y < this.y + height / 2) ? southeast.find(x, y) : northeast.find(x, y);
        }
    }

    /**
     * Deletes a rectangle at specified coordinates and collapses the node if all children are empty.
     *
     * @param x X-coordinate of rectangle to delete
     * @param y Y-coordinate of rectangle to delete
     * @return true if deletion occurred, false otherwise
     */
    @Override
    public boolean delete(int x, int y) {
        boolean deleted;
        if (x < this.x + width / 2) {
            deleted = (y < this.y + height / 2) ? southwest.delete(x, y) : northwest.delete(x, y);
        } else {
            deleted = (y < this.y + height / 2) ? southeast.delete(x, y) : northeast.delete(x, y);
        }

        if (allChildrenEmpty()) {
            return true; 
        }

        return deleted;
    }

    /**
     * Checks if all children nodes are empty.
     *
     * @return true if all children are empty leaf nodes, false otherwise
     */
    boolean allChildrenEmpty() {
        return isLeafAndEmpty(northwest) &&
               isLeafAndEmpty(northeast) &&
               isLeafAndEmpty(southwest) &&
               isLeafAndEmpty(southeast);
    }

    /**
     * Determines if a node is an empty leaf node.
     *
     * @param node Node to check
     * @return true if the node is a leaf node and empty, false otherwise
     */
    private boolean isLeafAndEmpty(Node node) {
        return (node instanceof LeafNode) && ((LeafNode) node).getRectangles().isEmpty();
    }
}
