import java.util.ArrayList;
import java.util.List;

class LeafNode extends Node{
	//List that stores the rectangles contained in the leaf node
	private List<Rectangle> rectangles;
	
	public LeafNode(int x, int y, int width, int height) {
		super(x, y, width, height); //Call the parent constructor
		
		this.rectangles = new ArrayList<>();
	}//end of LeafNode constructor
	
	
	@Override
	public void insert(Rectangle rect) {
		
		//Checks if there is already a rectangle at the specified position
		for(Rectangle r: rectangles){
			
			//Checks whether the (x,y) of  the new rectangle is equal to a rectangle (x,y)
			if (r.x == rect.x && r.y == rect.y){ 
				
				System.out.println("You can not double insert at a position");
				System.exit(0); //Terminates the program
				
			}//end of if statement
			
		}//end of for loop
		
		//Checks if there 5 or less rectangles in the leaf node
		
		if (rectangles.size() < 5) {
			rectangles.add(rect);	
		}else {
			split();
			insert(rect);
			
			
		}//end of if else statement
		
	}//end of insert method 
	
	@Override
	public Rectangle find(int x, int y) {
		//Finds the rectangle at the specified X and Y positions
		
		for (Rectangle rect : rectangles) {
			if(rect.x == x && rect.y == y) {
				return rect;
				
			}//end of if statement 
			
		}//end of for loop
		
		return null; //The function will return no rectangle if the specific (x, y) are not found 
		
	}//end of find method
	
	@Override
	public boolean delete(int x, int y) {
		
		for (Rectangle rect: rectangles) {
			if (rect.x == x && rect.y == y) {
				rectangles.remove(rect);
				return true;
			}//end of if statement 
			
		}//end of for loop 
		
		return false;
		
	}//end of the delete method 
	
	public void split() {
		
		InternalNode internalNode = new InternalNode(x,y,width,height);
		
		for (Rectangle rect: rectangles) {
			internalNode.insert(rect); 
		}//end of for loop
		
		//Converting leaf nodes into internal nodes
		this.children = internalNode.children;
		
		
	}//end of split method
	
	public List<Rectangle> getRectangles(){
		return rectangles;
	}//end of getRectangle method
	
	
}//end of LeafNode class

