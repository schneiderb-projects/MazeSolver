import java.io.IOException;
import java.util.ArrayList;
public class RandomMaze {
	static ArrayToImage display;
	static int xSize = 100;
	static int ySize = 100;
	static int maze[][] = new int[xSize][ySize];
	static ArrayList<Coordinate> usedSpaces = new ArrayList<Coordinate>();
	static boolean mazeComplete = false;
	static void createRandomMaze(Coordinate position) throws InterruptedException
	{
		//Thread.sleep(5);
		if(position.getY() == maze.length-1)
			mazeComplete  = true;
		if(mazeComplete == true)
			return;
		Coordinate currentPosition = null;
		if((int)(Math.random()*10)==0)
			createRandomMaze(usedSpaces.get((int)(Math.random()*(usedSpaces.size()-1)+1)));
		else
		{
			switch ((int)(Math.random()*9)) {
				case 0: {currentPosition = new Coordinate(position.getY(),position.getX()-1); break;}
				case 5: {currentPosition = new Coordinate(position.getY(),position.getX()-1); break;}
				case 1: {currentPosition= new Coordinate(position.getY(),position.getX()+1); break;}
				case 2: {currentPosition= new Coordinate(position.getY(),position.getX()+1); break;}
				case 3: {currentPosition = new Coordinate(position.getY()-1,position.getX()); break;}
				case 4: {currentPosition = new Coordinate(position.getY()+1,position.getX()); break;}
				case 6: {currentPosition = new Coordinate(position.getY()+1,position.getX()); break;}
				case 7: {currentPosition = new Coordinate(position.getY()+1,position.getX()); break;}
				case 8: {currentPosition = new Coordinate(position.getY()+1,position.getX()); break;}
			}
			if(currentPosition.getX() > xSize-2 || currentPosition.getX() < 1 || currentPosition.getY() < 1 && mazeComplete == false)
				createRandomMaze(position);
			if(checkUsedSpaces(currentPosition) == true && maze[currentPosition.getY()][currentPosition.getX()] == 1 && mazeComplete == false)
			{
				usedSpaces.add(currentPosition);
				createRandomMaze(currentPosition);
			}
			else if (mazeComplete == false) 
				createRandomMaze(usedSpaces.get((int)(Math.random()*(usedSpaces.size()-1)+1)));
		}
	}
	static boolean checkUsedSpaces(Coordinate position)
	{
		for(Coordinate pos:usedSpaces)
		{
			if(pos.getX() == position.getX() && pos.getY() == position.getY())
				return false;
		}
		return true;
	}
	static int[][] toArray()
	{
		int arrayToPrint[][] = maze;
		for(int j = 0; j < maze.length; j++) {
			for(int i = 0; i <maze[0].length; i++) {
				if(checkUsedSpaces(new Coordinate(j,i)) == false)
					arrayToPrint[j][i] = 0;
			}
		}
		return arrayToPrint;
	}

	private static boolean checkUsedSpaces(int[] newPosition, int[][] usedSpaces) {
		for(int pos[]:usedSpaces)
		{
			if(pos[0] == newPosition[0] && pos[1] == newPosition[1])
				return false;
		}
		return true;
	}
	
	public RandomMaze() throws InterruptedException
	{
		for(int i = 0; i < maze.length; i++)
			for(int j = 0; j < maze[0].length; j++)
				maze[i][j] = 1;
		int initialX = ((int)(Math.random()*(xSize-2))+1);
		maze[0][initialX] = 0;
		maze[1][initialX] = 0;
		usedSpaces.add(new Coordinate(0,initialX));
		usedSpaces.add(new Coordinate(1,initialX));
		int finalX = ((int)(Math.random()*(xSize-2))+1);
		maze[0][finalX] = 0;
		maze[1][finalX] = 0;
		usedSpaces.add(new Coordinate(0,finalX));
		usedSpaces.add(new Coordinate(1,finalX));
		createRandomMaze(new Coordinate(1,initialX));
	}
	
	int[][] getMaze()
	{
		return toArray();
	}
	
	public static void main(String args[]) throws IOException, InterruptedException
	{
		for(int i = 0; i < maze.length; i++)
			for(int j = 0; j < maze[0].length; j++)
				maze[i][j] = 1;
		int initialX = ((int)(Math.random()*(xSize-2))+1);
		maze[0][initialX] = 0;
		maze[1][initialX] = 0;
		usedSpaces.add(new Coordinate(0,initialX));
		usedSpaces.add(new Coordinate(1,initialX));
		createRandomMaze(new Coordinate(1,initialX));
		display = new ArrayToImage(toArray());
	}
}