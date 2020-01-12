import java.io.IOException;
import java.util.ArrayList;
public class RandomMaze2 {
	static ArrayToImage display;
	static int xSize = 100;
	static int ySize = 100;
	static int maze[][] = new int[xSize][ySize];
	static ArrayList<Coordinate> usedSpaces = new ArrayList<Coordinate>();
	static boolean mazeComplete = false;
	static void createRandomMaze() throws InterruptedException
	{
		int count = 0;
		do {
			int x = (int)(Math.random()*(xSize-2)+1);
			int y = (int)(Math.random()*(ySize-2)+1);
			if(maze[y][x] == 0) {
				maze[y][x] = 1;
				count++;
			}
		}while(count<(xSize*ySize)/6);
	}

	public RandomMaze2() throws InterruptedException, IOException
	{
		for(int i = 0; i < maze.length; i++)
		{
			maze[maze.length-1][i] = 1;
			maze[i][maze[0].length-1] = 1;
			maze[0][i] = 1;
			maze[i][0] = 1;
		}
		for(int i = 1; i < maze.length-1; i+=3)
			for(int j = 1; j < maze[0].length-1; j++)
				maze[i][j] = (i+j)%2;
		for(int i = 1; i < maze.length-1; i+=2)
			for(int j = 1; j < maze.length-1; j++)
			{
				maze[j][i] = 0;
			}
		createRandomMaze();
		int initialX = ((int)(Math.random()*(xSize-2))+1);
		int finalX = ((int)(Math.random()*(xSize-2))+1);
		maze[0][initialX] = 0;
		maze[1][initialX] = 0;
		maze[maze.length-1][finalX] = 0;
		maze[maze.length-2][finalX] = 0;
	}
	int[][] getMaze()
	{
		return maze;
	}

	public static void main(String args[]) throws IOException, InterruptedException
	{	
		for(int i = 0; i < maze.length; i++)
		{
			maze[maze.length-1][i] = 1;
			maze[i][maze[0].length-1] = 1;
			maze[0][i] = 1;
			maze[i][0] = 1;
		}
		for(int i = 1; i < maze.length-1; i+=3)
			for(int j = 1; j < maze[0].length-1; j++)
				maze[i][j] = (i+j)%2;
		for(int i = 1; i < maze.length-1; i+=2)
			for(int j = 1; j < maze.length-1; j++)
			{
				maze[j][i] = 0;
			}
		createRandomMaze();
		int initialX = ((int)(Math.random()*(xSize-2))+1);
		int finalX = ((int)(Math.random()*(xSize-2))+1);
		maze[0][initialX] = 0;
		maze[1][initialX] = 0;
		maze[maze.length-1][finalX] = 0;
		maze[maze.length-2][finalX] = 0;
		usedSpaces.add(new Coordinate(0,finalX));
		usedSpaces.add(new Coordinate(1,finalX));
		display = new ArrayToImage(maze);
	}
}