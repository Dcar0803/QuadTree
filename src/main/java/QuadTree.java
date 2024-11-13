import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * The QuadTree class manages a QuadTree data structure that stores rectangles in a 2D space.
 * It supports operations like insertion, deletion, searching, and updating of rectangles.
 * Each command is processed through a structured input file or as individual command strings.
 */
public class QuadTree {
    Node root;

    /**
     * Initializes the QuadTree with a root LeafNode covering an area of 100x100.
     * The root node is set to a LeafNode spanning from (-50, -50) to (50, 50).
     */
    public QuadTree() {
        root = new LeafNode(-50, -50, 100, 100);
    }

    /**
     * Processes a command related to QuadTree operations. The command may include
     * actions like insert, find, delete, update, or dump to perform specific operations
     * on the QuadTree.
     *
     * @param command A string representing a command with space-separated arguments.
     */
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
                    root = root.insert(rect); // Update root if it splits
                    System.out.println("Inserted: " + rect);
                    break;

                case "find":
                    x = Integer.parseInt(parts[1]);
                    y = Integer.parseInt(parts[2]);

                    Rectangle foundRectangle = root.find(x, y);
                    if (foundRectangle != null) {
                        System.out.println("Found: " + foundRectangle);
                    } else {
                        System.out.printf("Nothing is at (%d, %d).%n", x, y);
                    }
                    break;

                case "delete":
                    x = Integer.parseInt(parts[1]);
                    y = Integer.parseInt(parts[2]);

                    boolean deleted = root.delete(x, y);
                    if (deleted) {
                        // Collapse to a single empty leaf node if all children are empty
                        if (root instanceof InternalNode && ((InternalNode) root).allChildrenEmpty()) {
                            root = new LeafNode(root.x, root.y, root.width, root.height);
                            System.out.println("All rectangles deleted; QuadTree collapsed to a single empty leaf node.");
                        } else {
                            System.out.println("Deleted rectangle at (" + x + ", " + y + ")");
                        }
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

    /**
     * Prints the entire QuadTree structure to the console, which helps with debugging.
     * Calls the helper method printNode to recursively print each node and its children.
     */
    public void printQuadTree() {
        System.out.println("QuadTree Structure:");
        printNode(root, 0);
    }

    /**
     * Recursively prints the details of each node in the QuadTree structure.
     *
     * @param node  The current node to print
     * @param level The depth level of the node in the tree, used for indentation
     */
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

    /**
     * Reads a file of commands and processes each command on the QuadTree.
     * Each command should be separated by a semicolon (;) on a single line.
     *
     * @param filePath The path to the file containing commands
     */
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

    /**
     * The main entry point for running the QuadTree program.
     * Takes a command file as input and processes each command within it.
     *
     * @param args Command line arguments, where the first argument is the file path of the command file
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java QuadTree <file.cmmd>");
            return;
        }

        QuadTree quadTree = new QuadTree();
        quadTree.readAndProcessFile(args[0]);
    }
}
