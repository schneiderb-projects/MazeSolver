import java.util.ArrayList;
import java.util.Arrays;

//findNeighbors: create ordered array from vertice list by X and then Y and one by Y and then X

public class ScanTwice {
	static int maze2[][] = {{1, 1, 1, 1, 1, 0, 1, 1, 1, 1},
			{1, 1, 1, 0, 0, 0, 0, 0, 1, 1},
			{1, 1, 0, 0, 1, 0, 1, 0, 0, 1},
			{1, 0, 0, 1, 1, 0, 1, 1, 0, 1},
			{1, 1, 0, 0, 1, 0, 1, 1, 0, 1},
			{1, 0, 0, 1, 1, 0, 1, 0, 0, 1},
			{1, 1, 0, 0, 1, 0, 1, 0, 1, 1},
			{1, 0, 0, 1, 1, 1, 1, 0, 0, 1},
			{1, 1, 0, 0, 0, 0, 1, 0, 1, 1},
			{1, 1, 1, 1, 1, 0, 1, 1, 1, 1}};
	int NORTH = 0;
	int SOUTH = 1;
	int EAST = 2;
	int WEST = 3;


	ArrayList<Vertice> sortedByX;
	ArrayList<Vertice> sortedByY;
	ArrayList<Vertice> finalPath = new ArrayList<Vertice>();
	ArrayList<Vertice> vertices;
	static Vertice startVert;
	Vertice endVert;
	ArrayToImage display;
	Vertice vertArray[][];
	//ArrayToImage display = Driver.getDisplay();

	ScanTwice(){}

	public static void main(String args[]) {
		ScanTwice scantron = new ScanTwice();
		//Prim genMaze = new Prim(5000, 5000,);
		//int[][] maze = genMaze.getMaze();
		//scantron.convert(maze);
	}

	int[][] convert(int maze[][])
	{
		System.out.println("Graphing Method Using Scan Twice: ");
		double milis = System.currentTimeMillis();
		sortedByY = new ArrayList<Vertice>();
		sortedByX = new ArrayList<Vertice>();
		vertArray = new Vertice[maze.length][maze[0].length];
		for(int i = 0; i < maze.length; i++)
			Arrays.fill(vertArray[i], null);
		display = Driver.getDisplay();
		int startPoint = 0;
		int endPoint = 0;
		for(int i = 0; i < maze[0].length; i++)
		{
			if(maze[0][i] == 0)
			{
				startPoint = i;
			}
		}
		sortedByY.add(new Vertice(false,false,true,false,startPoint,0));
		vertArray[0][startPoint] = sortedByY.get(sortedByY.size()-1);
		boolean north;
		boolean south;
		boolean east;
		boolean west;
		//scan top to bottom
		for(int i = 1; i < maze.length-1; i++)
		{
			for(int j = 1; j < maze[0].length-1; j++)
			{
				north = false;
				south = false;
				east = false;
				west = false;
				//is it an intersection
				if(maze[i][j] == 0 && maze[i][j+1] + maze[i][j-1] < 2 && maze[i+1][j] + maze[i-1][j] < 2)
				{
					if (maze[i][j+1] == 0)
						east = true;
					if (maze[i][j-1] == 0)
						west = true;
					if (maze[i+1][j] == 0)
						south = true;
					if (maze[i-1][j] == 0)
						north = true;
					sortedByY.add(new Vertice(north,east,south,west,j,i));
					vertArray[i][j] = sortedByY.get(sortedByY.size()-1);
				}
				//is it a dead end?
				else if (maze[i][j] == 0 && maze[i][j+1] + maze[i][j-1] + maze[i+1][j] + maze[i-1][j] > 2)
				{
					if (maze[i][j+1] == 0)
						east = true;
					else if (maze[i][j-1] == 0)
						west = true;
					else if (maze[i+1][j] == 0)
						south = true;
					else if (maze[i-1][j] == 0)
						north = true;
					sortedByY.add(new Vertice(north,east,south,west,j,i));
					vertArray[i][j] = sortedByY.get(sortedByY.size()-1);
				}
			}
		}
		for(int i = 0; i < maze[0].length; i++)
		{
			if(maze[maze.length-1][i] == 0)
			{
				endPoint = i;
			}
		}
		sortedByY.add(new Vertice(true,false,false,false,endPoint,maze.length-1));
		startVert = sortedByY.get(0);
		endVert = sortedByY.get(sortedByY.size()-1);
		vertArray[maze.length-1][endPoint] = sortedByY.get(sortedByY.size()-1);
		//scan array of vertices to find left to right scanned array
		for(int i = 0; i < maze[0].length; i++)
		{
			for(int j = 0; j < maze.length; j++)
			{
				if(vertArray[j][i] != null)
				{
					sortedByX.add(vertArray[j][i]);
				}
			}
		}
		connectHorizontalVertices();
		connectVerticalVertices();
		/*		System.out.println("Sorted By Y");
		for (Vertice v: sortedByY)
		{
			System.out.println("(" + v.getX() + ", " + v.getY() + ") " + v.getNeighbors().size());
		}

		System.out.println("Sorted By X");
		for (Vertice v: sortedByX)
		{
			System.out.println("(" + v.getX() + ", " + v.getY() + ") " + v.getNeighbors().size());
		}*/
		
		double milis2 = System.currentTimeMillis();
		findPath(startVert,null);
		System.out.println("Time to find path: " + ((double)(System.currentTimeMillis())- milis2)*.001);
		//System.out.println(sortedByX.size());

		maze = drawLines(maze, finalPath);
		double endMilis = System.currentTimeMillis();
		System.out.println("Graphing Method Using Scan Twice Total Time: " + (endMilis - milis)*.001);
		return maze;
	}

	boolean checkSorted() {
		for(int i = 0; i < sortedByX.size()-2; i++)
		{
			if(sortedByX.get(i).getX() > sortedByX.get(i+1).getX())
				return false;
			if (sortedByX.get(i).getX() == sortedByX.get(i+1).getX())
				if(sortedByX.get(i).getY() > sortedByX.get(i+1).getY())
					return false;
		}
		for(int i = 0; i < sortedByX.size()-2; i++)
		{
			if(sortedByY.get(i).getY() > sortedByY.get(i+1).getY())
				return false;
			if (sortedByY.get(i).getY() == sortedByY.get(i+1).getY())
				if(sortedByY.get(i).getX() > sortedByY.get(i+1).getX())
					return false;
		}
		return true;
	}

	Boolean findPath(Vertice current, Vertice last) {
		if (current.equals(endVert))
		{
			finalPath.add(current);
			return true;
		}
		ArrayList<Vertice> neighbors = current.getNeighbors();
		for (int i = 0; i < neighbors.size(); i++)
		{
			if (neighbors.get(i).equals(last))
				continue;
			else if (findPath(neighbors.get(i), current) == true)
			{
				finalPath.add(current);
				return true;
			}
		}
		return false;
	}
	/*void findPath(){
		double milis = System.currentTimeMillis();
		boolean pathFound = false;
		Vertice start = sortedByY.get(0);
		Vertice end = sortedByY.get(sortedByY.size()-1);
		while(!pathFound)
		{
			pathFound = true;
			for(int i = 0; i < sortedByX.size(); i++)
			{
				if(sortedByX.get(i).getNeighbors().size() == 1 
						&& sortedByX.get(i).equals(start) == false 
						&& sortedByX.get(i).equals(end) == false)
				{
					pathFound = false;
					sortedByX.get(i).getNeighbors().get(0).removeNeighbor(sortedByX.remove(i));
				}
			}
		}
		double endMilis = System.currentTimeMillis();
		System.out.println("time to delete nodes: " + (endMilis - milis)*.001);
	}*/
	public ArrayList<Vertice> getSortedByX() {
		return sortedByX;
	}

	public ArrayList<Vertice> getSortedByY() {
		return sortedByY;
	}

	private void connectVerticalVertices() {
		double milis = System.currentTimeMillis();
		for(int i = 0; i < sortedByX.size();i++)
		{
			if(sortedByX.get(i).isNorth())
				sortedByX.get(i).setNeighbor(sortedByX.get(i-1));
			if(sortedByX.get(i).isSouth())
				sortedByX.get(i).setNeighbor(sortedByX.get(i+1));
		}
		double endMilis = System.currentTimeMillis();
		System.out.println("time to connect vertical vertices: " + (endMilis - milis)*.001);
	}

	private void connectHorizontalVertices() {
		double milis = System.currentTimeMillis();
		for(int i = 1; i < sortedByY.size();i++)
		{
			if(sortedByY.get(i).isWest())
				sortedByY.get(i).setNeighbor(sortedByY.get(i-1));
			if(sortedByY.get(i).isEast())
				sortedByY.get(i).setNeighbor(sortedByY.get(i+1));
		}
		double endMilis = System.currentTimeMillis();
		System.out.println("time to connect horizontal vertices: " + (endMilis - milis)*.001);
	}


	int[][] drawLines(int maze[][], ArrayList<Vertice> vertList)
	{
		int displayRate = sortedByX.size()/100;
		if (displayRate == 0)
			displayRate = 1;
		int big = 0;
		int small = 0;
		for(int i = finalPath.size()-1; i > 0; i--)
		{
			
			if(finalPath.get(i).getX() == finalPath.get(i-1).getX())
			{
				big = Math.max(finalPath.get(i).getY(), finalPath.get(i-1).getY());
				small = Math.min(finalPath.get(i).getY(), finalPath.get(i-1).getY());
				
				for(int j = small; j <= big; j++)
					maze[j][finalPath.get(i).getX()] = 2;
			}
			else
			{
				big = Math.max(finalPath.get(i).getX(), finalPath.get(i-1).getX());
				small = Math.min(finalPath.get(i).getX(), finalPath.get(i-1).getX());
				
				for(int j = small; j <= big; j++)
					maze[finalPath.get(i).getY()][j] = 2;
			}
			if(i%displayRate == 0&&maze.length*maze[0].length<100000000)
				display.refreshImage(maze);
		}
		display.refreshImage(maze);
		return maze;
	}


}