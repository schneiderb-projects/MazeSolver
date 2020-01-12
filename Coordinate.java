public class Coordinate {
	int x;
	int y;
	Coordinate(int yPos, int xPos){
		x = xPos;
		y = yPos;
	}
	int getX()
	{
		return x;
	}
	
	int getY()
	{
		return(y);
	}
	int[] getXY()
	{	
		int[] XY = {x,y};
		return XY;
	}
}
