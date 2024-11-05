abstract class Node {
    int x, y, width, height;

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
