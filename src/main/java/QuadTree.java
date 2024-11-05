import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

class QuadTree {
    Node root;

    public QuadTree() {
        // Initialize the quad tree with a LeafNode that stores an initial space of 100x100
        root = new LeafNode(-50, -50, 100, 100);
    }

    // Process each command from the input file
    public void processCommand(String command) {
        String[] parts = command.trim().split(" ");
        String action = parts[0].toLowerCase();

        try {
            switch (action) {
                case "insert":
                    int x = Integer.parseInt(parts[1]);
                    int y = Integer.parseInt(parts[2]);
                    int width = Integer.parseInt(parts[3]);
                    int height = Integer.parseInt(parts[4]);

                    Rectangle rect = new Rectangle(x, y, width, height);
                    root = root.insert(rect);  // update root if root splits
                    System.out.println("Inserted: " + rect);
                    break;

                case "find":
                    x = Integer.parseInt(parts[1]);
                    y = Integer.parseInt(parts[2]);

                    Rectangle foundRectangle = root.find(x, y);
                    if (foundRectangle != null) {
                        System.out.println(foundRectangle);
                    } else {
                        System.out.printf("Nothing is at (%d, %d).%n", x, y);
                    }
                    break;

                case "delete":
                    x = Integer.parseInt(parts[1]);
                    y = Integer.parseInt(parts[2]);

                    boolean deleted = root.delete(x, y);
                    if (deleted) {
                        System.out.println("Deleted rectangle at (" + x + ", " + y + ")");
                    } else {
                        System.out.printf("Nothing to delete at (%d, %d).%n", x, y);
                    }
                    break;

                case "update":
                    x = Integer.parseInt(parts[1]);
                    y = Integer.parseInt(parts[2]);
                    int newWidth = Integer.parseInt(parts[3]);
                    int newHeight = Integer.parseInt(parts[4]);

                    Rectangle rectangleToUpdate = root.find(x, y);
                    if (rectangleToUpdate != null) {
                        rectangleToUpdate.width = newWidth;
                        rectangleToUpdate.height = newHeight;
                        System.out.println("Updated: " + rectangleToUpdate);
                    } else {
                        System.out.printf("Nothing to update at (%d, %d).%n", x, y);
                    }
                    break;

                case "dump":
                    printQuadTree();
                    break;

                default:
                    System.out.println("Unknown command: " + action);
            }
        } catch (Exception e) {
            System.err.println("Error processing command: " + e.getMessage());
        }
    }

    // Print entire QuadTree structure to validate the tree after each command
    public void printQuadTree() {
        System.out.println("QuadTree Structure:");
        printNode(root, 0);
    }

    private void printNode(Node node, int level) {
        String indent = "\t".repeat(level);
        System.out.println(indent + node);

        if (node instanceof InternalNode) {
            InternalNode internalNode = (InternalNode) node;
            printNode(internalNode.northwest, level + 1);
            printNode(internalNode.northeast, level + 1);
            printNode(internalNode.southwest, level + 1);
            printNode(internalNode.southeast, level + 1);
        } else if (node instanceof LeafNode) {
            LeafNode leafNode = (LeafNode) node;
            for (Rectangle rect : leafNode.getRectangles()) {
                System.out.println(indent + "\t" + rect);
            }
        }
    }

    public void readAndProcessFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] commands = line.split(";");
                for (String command : commands) {
                    if (!command.trim().isEmpty()) {
                        processCommand(command.trim());
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java QuadTree <file.cmmd>");
            return;
        }

        QuadTree quadTree = new QuadTree();
        quadTree.readAndProcessFile(args[0]);
    }
}
