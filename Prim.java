import java.util.ArrayList;
import java.util.Arrays;;


public class Prim {
	static int[][] buildMaze(int width,int height) {
		double milis = System.currentTimeMillis();
		int maze[][];
		//initialize maze as all walls
		maze = new int[width][height];
		for(int i = 0; i<maze.length;i++)
			Arrays.fill(maze[i], 1);
		
		//initialize display
		ArrayToImage display = Driver.getDisplay();
		
		//initialize walls array
		ArrayList<Point> walls = new ArrayList<Point>();
		
		//randomly choose a cell to start at
		int startX = (int)(Math.random()*width), startY = (int)(Math.random()*height);
		maze[startX][startY] = 0;
		walls.add(new Point(startX+1, startY, new Point(startX, startY, null)));
		walls.add(new Point(startX, startY+1, new Point(startX, startY, null)));
		walls.add(new Point(startX-1, startY, new Point(startX, startY, null)));
		walls.add(new Point(startX, startY-1, new Point(startX, startY, null)));
		
		//start removing walls
		int wallIndex, wallX, wallY;
		Point opposite, current;
		int count = 0;
		int dispRate = (width*height)/40;
		if (width*height <= 1000)
			dispRate = 4;
		if (width*height >= 3000*3000)
			dispRate *= 4;
		
		while(!walls.isEmpty())
		{
			count++;
			wallIndex=(int)(Math.random()*walls.size());
			wallX = walls.get(wallIndex).x;
			wallY = walls.get(wallIndex).y;
			opposite = walls.get(wallIndex).opposite();
			current = walls.remove(wallIndex);
			//throw any out of bounds exceptions
			try {
				//check if opposite and current are both walls
				if (maze[wallX][wallY] + maze[opposite.x][opposite.y] == 2)
				{
					//if so, open path between them
					maze[wallX][wallY] = 0;
					maze[opposite.x][opposite.y] = 0;
					
					//add surrounding walls to the walls array
					if(maze[opposite.x-1][opposite.y] != 0)
						walls.add(new Point(opposite.x-1,opposite.y, opposite));
					if(maze[opposite.x][opposite.y-1] != 0)
						walls.add(new Point(opposite.x,opposite.y-1, opposite));
					if(maze[opposite.x+1][opposite.y] != 0)
						walls.add(new Point(opposite.x+1,opposite.y, opposite));
					if(maze[opposite.x][opposite.y+1] != 0)
						walls.add(new Point(opposite.x,opposite.y+1, opposite));
				}
			}
			catch(Exception e) {}
			//update display at given rate
			if (count%dispRate == 0)
			{
				display.refreshImage(maze);
			}
		}
		double time1 = System.currentTimeMillis()-milis;
		System.out.println("Time to generate maze: "+time1*.001);
		//initialize new maze larger than current maze
		int maze2[][] = new int[width+2][height+2];
		for (int i = 0; i<width+2;i++)
		{
			maze2[i][0] = 1;
			maze2[i][height+1] = 1;
		}
		for (int i = 0; i<height+2;i++)
		{
			maze2[0][i] = 1;
			maze2[width+1][i] = 1;
		}
		//input maze into larger maze, creates a wall buffer around the maze
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++)
			{
				maze2[i+1][j+1] = maze[i][j];
			}
		}
		//open up a start path
		boolean incomplete = true;
		int startC = 0;
		display.refreshImage(maze2);
		while(incomplete)
		{
			startC = (int)(Math.random()*height+2);
			int a = maze2[1][startC];
			int b = maze2[2][startC];
			if(a==0 || b == 0)
				incomplete = false;
		}
		maze2[1][startC] = 0;
		maze2[0][startC] = 0;
		
		//open up an end path
		incomplete = true;
		int finalC = 0;
		while(incomplete)
		{
			finalC = (int)(Math.random()*height+2);
			int a = maze2[width-1][finalC];
			if(a==0)
				incomplete = false;
		}
		maze2[width][finalC] = 0;
		maze2[width+1][finalC] = 0;
		maze = maze2;
		display.refreshImage(maze);
		milis=System.currentTimeMillis()-milis;
		System.out.println("Time to generate maze: "+milis*.001);
		
		return maze;
	}
				
	public static void main(String[] args) {
		double milis = System.currentTimeMillis();
		//initialize height and width
		int height = 100;
		int width = 100;
		
		//initialize maze as all walls
		int maze[][] = new int[width][height];
		for(int i = 0; i<maze.length;i++)
			Arrays.fill(maze[i], 1);
		
		//initialize display
		ArrayToImage display = new ArrayToImage(maze);
		
		//initialize walls array
		ArrayList<Point> walls = new ArrayList<Point>();
		
		//randomly choose a cell to start at
		int startX = (int)(Math.random()*width), startY = (int)(Math.random()*height);
		maze[startX][startY] = 0;
		walls.add(new Point(startX+1, startY, new Point(startX, startY, null)));
		walls.add(new Point(startX, startY+1, new Point(startX, startY, null)));
		walls.add(new Point(startX-1, startY, new Point(startX, startY, null)));
		walls.add(new Point(startX, startY-1, new Point(startX, startY, null)));
		
		//start removing walls
		int wallIndex, wallX, wallY;
		Point opposite, current;
		int count = 0;
		int dispRate = (width*height)/40;
		if (width*height <= 1000)
			dispRate = 4;
		if (width*height >= 3000*3000)
			dispRate *= 4;
		while(!walls.isEmpty())
		{
			count++;
			wallIndex=(int)(Math.random()*walls.size());
			wallX = walls.get(wallIndex).x;
			wallY = walls.get(wallIndex).y;
			opposite = walls.get(wallIndex).opposite();
			current = walls.remove(wallIndex);
			try {
				if (maze[wallX][wallY] + maze[opposite.x][opposite.y] == 2)
				{
					maze[wallX][wallY] = 0;
					maze[opposite.x][opposite.y] = 0;
					
					if(maze[opposite.x-1][opposite.y] != 0)
						walls.add(new Point(opposite.x-1,opposite.y, opposite));
					if(maze[opposite.x][opposite.y-1] != 0)
						walls.add(new Point(opposite.x,opposite.y-1, opposite));
					if(maze[opposite.x+1][opposite.y] != 0)
						walls.add(new Point(opposite.x+1,opposite.y, opposite));
					if(maze[opposite.x][opposite.y+1] != 0)
						walls.add(new Point(opposite.x,opposite.y+1, opposite));
				}
			}
			catch(Exception e) {}
			if (count%dispRate == 0)
			{
				display.refreshImage(maze);
			}
		}
		double time1 = System.currentTimeMillis()-milis;
		System.out.println("Time to generate maze: "+time1*.001);
		int maze2[][] = new int[width+2][height+2];
		for (int i = 0; i<width+2;i++)
		{
			maze2[i][0] = 1;
			maze2[i][height+1] = 1;
		}
		for (int i = 0; i<height+2;i++)
		{
			maze2[0][i] = 1;
			maze2[width+1][i] = 1;
		}
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++)
			{
				maze2[i+1][j+1] = maze[i][j];
			}
		}
		boolean incomplete = true;
		int startC = 0;
		display.refreshImage(maze2);
		while(incomplete)
		{
			startC = (int)(Math.random()*height+2);
			int a = maze2[1][startC];
			int b = maze2[2][startC];
			if(a==0 || b == 0)
				incomplete = false;
		}
		maze2[1][startC] = 0;
		maze2[0][startC] = 0;
		incomplete = true;
		int finalC = 0;
		while(incomplete)
		{
			finalC = (int)(Math.random()*height+2);
			int a = maze2[width-1][finalC];
			if(a==0)
				incomplete = false;
		}
		maze2[width][finalC] = 0;
		maze2[width+1][finalC] = 0;
		display.refreshImage(maze2);
		milis=System.currentTimeMillis()-milis;
		System.out.println("Time to generate maze: "+milis*.001);
	}
	
	

	static class Point {
		Integer x;
		Integer y;
		Point parent;
		public Point(int aX, int aY, Point p) {
			x = aX;
			y = aY;
			parent = p;
		}
		boolean checkEqual(Point p1, Point p2)
		{
			if (p1.x == p2.x || p1.y == p2.y)
				return true;
			return false;
		}
		// compute opposite node given that it is in the other direction from the parent
		public Point opposite() {
			if (this.x.compareTo(parent.x) != 0)
				return new Point(this.x + this.x.compareTo(parent.x), this.y, this);
			if (this.y.compareTo(parent.y) != 0)
				return new Point(this.x, this.y + this.y.compareTo(parent.y), this);
			return null;
		}
	}
}