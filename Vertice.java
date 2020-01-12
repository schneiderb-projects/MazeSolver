import java.util.ArrayList;

public class Vertice {
	boolean north, south, east, west;
	int x, y;
	ArrayList<Vertice> neighbors = new ArrayList<Vertice>();
	Vertice(boolean aNorth, boolean aEast, boolean aSouth, boolean aWest, int aX, int aY)
	{
		north = aNorth;
		south = aSouth;
		east = aEast;
		west = aWest;
		x = aX;
		y = aY;
	}
	public void setX(int aX)
	{
		x = aX;
	}
	public boolean isNorth() {
		return north;
	}
	public boolean isSouth() {
		return south;
	}
	public boolean isEast() {
		return east;
	}
	public boolean isWest() {
		return west;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public ArrayList<Vertice> getNeighbors() {
		return neighbors;
	}
	public void setNeighbor(Vertice vertice) {
		neighbors.add(vertice);
	}
	public Vertice removeNeighbor(Vertice v)
	{
		neighbors.remove(v);
		return v;
	}
	public boolean isRightVertice() {
		if(south && east)
			return true;
		if(south && west)
			return true;
		if(north && east)
			return true;
		if(north && west)
			return true;
		return false;
	}
	
	public String toString() {
		return "x = " + x +", y = " + y;
	}
}
