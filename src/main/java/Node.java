abstract class Node {
	
	int x, y; //These are the node's region coordinates 
	
	int width, height; //Width and Height of the region
	
	Node[] children; //Node variable to store the node children 
	
	public Node(int x, int y, int width, int height){
		
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.children = new Node[4]; // NW, NE, SE, SW 
	}//end of Node constructor 
	
	
	//Abstract method to be implemented by the subclasses LeafNode and InternalNode
	public abstract void insert(Rectangle rect);
	
	public abstract Rectangle find(int x, int y);
	
	//Abstract method for deleting 
	public abstract boolean delete(int x, int y);
	
	
}//end of node class






