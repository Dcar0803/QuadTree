import java.util.ArrayList;
import java.util.List;

class LeafNode extends Node {
    private List<Rectangle> rectangles;
    private static final int MAX_RECTANGLES = 4;

    public LeafNode(int x, int y, int width, int height) {
        super(x, y, width, height);
        rectangles = new ArrayList<>();
    }

    @Override
    public Node insert(Rectangle rect) {
        if (rectangles.size() < MAX_RECTANGLES) {
            rectangles.add(rect);
            return this;
        } else {
            return split().insert(rect);
        }
    }

    private InternalNode split() {
        InternalNode internalNode = new InternalNode(x, y, width, height);
        for (Rectangle rect : rectangles) {
            internalNode.insert(rect);
        }
        rectangles.clear();
        return internalNode;
    }

    @Override
    public Rectangle find(int x, int y) {
        for (Rectangle rect : rectangles) {
            if (rect.x == x && rect.y == y) return rect;
        }
        return null;
    }

    @Override
    public boolean delete(int x, int y) {
        return rectangles.removeIf(rect -> rect.x == x && rect.y == y);
    }

    public List<Rectangle> getRectangles() {
        return rectangles;
    }

    @Override
    public String toString() {
        return "LeafNode at (" + x + ", " + y + ") with " + rectangles.size() + " rectangles";
    }
}
