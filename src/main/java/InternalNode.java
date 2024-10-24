import java.util.List;
import java.util.ArrayList;


class InternalNode extends Node {
	
	Node northeast;
	Node southeast;
	Node southwest;
	Node northwest;
	
	//Default Constructor
	public InternalNode(int x, int y, int width, int height) {
		super(x,y,width,height);
		
		int halfWidth = width / 2;
		int halfHeight = height / 2;
		
		//Creating the child nodes representing the four quadrants
		northwest = new LeafNode(x, y, halfWidth, halfHeight); //Top-left region
		northeast = new LeafNode(x, y, halfWidth, halfHeight); // Top-right region
		southwest = new LeafNode(x, y, halfWidth, halfHeight); //Bottom-left region 
		southeast = new LeafNode(x, y, halfWidth, halfHeight); //Bottom-right region
		
	}//End of Internal Node constructor
	
	@Override
	public void insert(Rectangle rect) {
		//Nested if else statements for inserting 
		if (rect.x < x + width / 2) {
			if(rect.y < y + height /2) {
				southwest.insert(rect);
			}else {
				northwest.insert(rect);
				
			}//end of if else statement nested 
			
		}else {
			
			if (rect.y < y + height / 2) {
				southeast.insert(rect);
			}else {
				northeast.insert(rect);
				
			}//end of inner if else statement of else 
			
		}//end of outer if else statement
		
	}//end of insert method for Internal Node
	
	
	public Rectangle find(int x, int y) {
		
		if (x < this.x + width / 2) {
			
			if (y < this.y + height / 2) {
				return southwest.find(x, y);
				
			}//end of inner if statement
			
			else {
				return northwest.find(x, y);
			}//end of else statement in if statement 
			
		}//end of outer if statement
		
		else {
			if (y < this.y + height / 2) {
				return southeast.find(x, y);
			}//end of if statement in else statement
			
			else {
				return northeast.find(x, y);
				
			}//end of inner else statement in outer else statement
			
		}//end of else statement 
		
	}//end of find method 
	
	public boolean delete(int x, int y) {
		
		boolean deleted = false;
		
		if (x < this.x + width /2) {
			if( y < this.y + height / 2) {
				deleted =  southwest.delete(x, y);
			}//end of inner if statement 
			
			else {
				deleted = northwest.delete(x, y);
			}//end of else statement 
			
		}//end of outer if statement 
		
		else {
			if (y < this.y + height / 2) {
				deleted =  southeast.delete(x, y);
			}//end of inner if statement 
			else {
				deleted = northeast.delete(x, y);
			}
		}//end of outer else statement 
		
		//This checks if the Internal Node should turn back into a LeafNode after deletion
		
		if ( deleted && shouldMerge()) {
			
			mergeToLeaf();
			
		}//end of if statement 
		return deleted;
	}//end of delete method
	
	
	// Check if the node should merge back into a LeafNode
	private boolean shouldMerge() {
		
	    int totalRectangles = 0;
	    
	    totalRectangles += getRectanglesFromNode(northwest).size();
	    totalRectangles += getRectanglesFromNode(northeast).size();
	    totalRectangles += getRectanglesFromNode(southwest).size();
	    totalRectangles += getRectanglesFromNode(southeast).size();
	    
	    // If total rectangles in children are less than the threshold, merge
	    return totalRectangles <= 5;
	    
	}//end of shouldMerge method 

	// Helper method to extract rectangles from a node (can be LeafNode or InternalNode)
	private List<Rectangle> getRectanglesFromNode(Node node) {
	    if (node instanceof LeafNode) {
	        
	    	return ((LeafNode) node).getRectangles();
	    }
	    // InternalNodes should return an empty list
	    return new ArrayList<>();
	}

	// Merge internal node back into a LeafNode
	private void mergeToLeaf() {
	    LeafNode newLeaf = new LeafNode(x, y, width, height);

	    // Collect all rectangles from child nodes
	    newLeaf.getRectangles().addAll(getRectanglesFromNode(northwest));
	    newLeaf.getRectangles().addAll(getRectanglesFromNode(northeast));
	    newLeaf.getRectangles().addAll(getRectanglesFromNode(southwest));
	    newLeaf.getRectangles().addAll(getRectanglesFromNode(southeast));

	    // Replace the internal node with the new leaf node
	    this.children = newLeaf.children;
	}

}//end of InternalNode class