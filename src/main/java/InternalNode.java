import java.util.ArrayList;
import java.util.List;

class InternalNode extends Node {
    Node northeast, southeast, southwest, northwest;

    public InternalNode(int x, int y, int width, int height) {
        super(x, y, width, height);
        int halfWidth = width / 2;
        int halfHeight = height / 2;

        northwest = new LeafNode(x, y, halfWidth, halfHeight);
        northeast = new LeafNode(x + halfWidth, y, halfWidth, halfHeight);
        southwest = new LeafNode(x, y + halfHeight, halfWidth, halfHeight);
        southeast = new LeafNode(x + halfWidth, y + halfHeight, halfWidth, halfHeight);
    }

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
        return deleted;
    }
}
