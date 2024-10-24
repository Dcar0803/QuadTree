class Rectangle{
	
	int  x, y; 
	int width, height;
	
	public Rectangle(int x, int y, int width, int height){
		
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
	}// End of Rectangle constructor 
	
	@Override
    public String toString() {
		 return String.format("Rectangle at (%d, %d): %dx%d", x, y, width, height);
    }
	
}//end of Rectangle class