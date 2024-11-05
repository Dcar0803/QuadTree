import java.util.List;
import java.util.ArrayList;

class InternalNode extends Node {
    Node northeast;
    Node southeast;
    Node southwest;
    Node northwest;

    // Default Constructor
    public InternalNode(int x, int y, int width, int height) {
        super(x, y, width, height);

        int halfWidth = width / 2;
        int halfHeight = height / 2;

        // Creating the child nodes representing the four quadrants
        northwest = new LeafNode(x, y, halfWidth, halfHeight); // Top-left region
        northeast = new LeafNode(x + halfWidth, y, halfWidth, halfHeight); // Top-right region
        southwest = new LeafNode(x, y + halfHeight, halfWidth, halfHeight); // Bottom-left region
        southeast = new LeafNode(x + halfWidth, y + halfHeight, halfWidth, halfHeight); // Bottom-right region
    }

    @Override
    public void insert(Rectangle rect) {
        if (rect.x < x + width / 2) {
            if (rect.y < y + height / 2) {
                southwest.insert(rect);
            } else {
                northwest.insert(rect);
            }
        } else {
            if (rect.y < y + height / 2) {
                southeast.insert(rect);
            } else {
                northeast.insert(rect);
            }
        }
    }

    @Override
    public Rectangle find(int x, int y) {
        if (x < this.x + width / 2) {
            return (y < this.y + height / 2) ? southwest.find(x, y) : northwest.find(x, y);
        } else {
            return (y < this.y + height / 2) ? southeast.find(x, y) : northeast.find(x, y);
        }
    }

    @Override
    public boolean delete(int x, int y) {
        boolean deleted = false;

        if (x < this.x + width / 2) {
            deleted = (y < this.y + height / 2) ? southwest.delete(x, y) : northwest.delete(x, y);
        } else {
            deleted = (y < this.y + height / 2) ? southeast.delete(x, y) : northeast.delete(x, y);
        }

        if (deleted && shouldMerge()) {
            mergeToLeaf();
        }
        return deleted;
    }

    // Check if the node should merge back into a LeafNode
    private boolean shouldMerge() {
        int totalRectangles = getRectanglesFromNode(northwest).size()
                            + getRectanglesFromNode(northeast).size()
                            + getRectanglesFromNode(southwest).size()
                            + getRectanglesFromNode(southeast).size();

        return totalRectangles <= 4;
    }

    private void mergeToLeaf() {
        List<Rectangle> allRectangles = new ArrayList<>();
        allRectangles.addAll(getRectanglesFromNode(northwest));
        allRectangles.addAll(getRectanglesFromNode(northeast));
        allRectangles.addAll(getRectanglesFromNode(southwest));
        allRectangles.addAll(getRectanglesFromNode(southeast));

        northwest = null;
        northeast = null;
        southwest = null;
        southeast = null;

        LeafNode leaf = new LeafNode(x, y, width, height);
        allRectangles.forEach(leaf::insert);
    }

    private List<Rectangle> getRectanglesFromNode(Node node) {
        if (node instanceof LeafNode) {
            return ((LeafNode) node).getRectangles();
        }
        return new ArrayList<>();
    }
}
