import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
class QuadTree {
	
	Node root;
	
	public QuadTree() {
		
		//Initialize the quad tree with a LeafNode that stores an initial space of 100x100
		
		root = new LeafNode(-50,50, 100, 100);
	}//end of QuadTree Constructor
	
	
	//A method that processes a command 
	public void processCommand(String command) {
		
		String [] parts = command.trim().split(" ");
		String action = parts[0];
		
		switch (action.toLowerCase()) {
		
		case "insert":
			int x = Integer.parseInt(parts[1]);
			int y = Integer.parseInt(parts[2]);
			int width = Integer.parseInt(parts[3]);
			int height = Integer.parseInt(parts[4]);
			
			//Creates a new rectangle and inserts the rectangle into the quad tree
			Rectangle rect = new Rectangle(x,y,width,height);
			root.insert(rect);
			System.out.println("Inserted: " + rect);
			break;
			
		case "find":
			x = Integer.parseInt(parts[1]);
			y = Integer.parseInt(parts[2]);
			
			Rectangle foundRectangle = root.find(x, y);
			if (foundRectangle  != null) {
				
				System.out.print(foundRectangle);
				
			}//end of if statement
			else {
				System.out.println("Nothing is at (%d, %d).%n");
				System.exit(0);
				
			}//end of if else statement
			
			break;
		
		case "delete":
			x = Integer.parseInt(parts[1]);
			y = Integer.parseInt(parts[2]);
			//width = Integer.parseInt(parts[3]);
			//height = Integer.parseInt(parts[4]);
			
			boolean deleted = root.delete(x, y);
			
			if(deleted) {
				
			}else {
				System.out.println("Nothing to delete at (" + x + ", " + y + ")");
				System.exit(0);
			}
			
			break;
			
		case "update":
			x = Integer.parseInt(parts[1]);
			y = Integer.parseInt(parts[2]);
			int newWidth = Integer.parseInt(parts[3]);
			int newHeight = Integer.parseInt(parts[4]);
			
			Rectangle rectangleToUpdate = root.find(x, y);
			
			if(rectangleToUpdate != null) {
				rectangleToUpdate.width = newWidth;
				rectangleToUpdate.height = newHeight;
				System.out.println("Updated: " + rectangleToUpdate);
				
				
			}//end of if statement 
			else {
				
				System.out.println("Nothing to update at (%d,%d).%n");
				System.exit(0);
				
			}//end of if else statement 
			
			break;
			
		case "dump":
			break;
			
		default:
			System.out.println("Unknown command: " + action);
		}//end of switch case 
		
	}//end of processCommand method
	
	
	//Method that prints the entire quad tree
	public void printQuadTree() {
		
		printNode(root, 0);
		
	}//end of printQuadTree method
	
	//A method that helps print to a specific node and its children 
	private void printNode(Node node, int level) {
		String indent = "\t".repeat(level);
        System.out.println(indent + node);

        // If the node is an InternalNode, print its children
        if (node instanceof InternalNode) {
            InternalNode internalNode = (InternalNode) node;
            if (internalNode.northwest != null) printNode(internalNode.northwest, level + 1);
            if (internalNode.northeast != null) printNode(internalNode.northeast, level + 1);
            if (internalNode.southwest != null) printNode(internalNode.southwest, level + 1);
            if (internalNode.southeast != null) printNode(internalNode.southeast, level + 1);
        //end of if statement 

        // If it's a LeafNode, print all rectangles in that node
        if (node instanceof LeafNode) {
            LeafNode leafNode = (LeafNode) node;
            for (Rectangle rect : leafNode.getRectangles()) {
                System.out.println(indent + "\t" + rect);
            }//end of for loop 
        }
       }
		
	}//end of the printNode method 

	 // Method to read and process the .cmmd file
    public void readAndProcessFile(String filePath) {
    	
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            
            while ((line = br.readLine()) != null) {
                // Split commands by ';' to process multiple commands in one line
                String[] commands = line.split(";");
                
                for (String command : commands) {
                    if (!command.trim().isEmpty()) {
                        processCommand(command.trim());
                    }//end of 
                }//end of for loop 
                
            }//end of while loop
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }//end of catch
    }//end of readAndProcessFile method

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if (args.length < 1) {
	            System.out.println("Usage: java QuadTree <file.cmmd>");
	            return;
	        }

	        // Initialize the QuadTree
	        QuadTree quadTree = new QuadTree();

	        // Read the .cmmd file and process commands
	        String filePath = args[0];
	        quadTree.readAndProcessFile(filePath);

	}//end of main method 
	

}//end of QuadTree class


