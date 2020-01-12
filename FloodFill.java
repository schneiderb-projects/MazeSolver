import java.io.IOException;
import java.util.ArrayList;
public class FloodFill {
	
	//The FloodFill class uses recursion to check left right up and down and then move toward any open spaces
	
	//RandomMaze.java and RandomMaze2.java are my first 2 attempts at creating a random maze.

	//ReadFile.java is my attempt to incorporate random mazes found on google by reading in the file. It works, 
	//but the loaded mazes didn't quite match the criteria required for my algorithm, so the solving would either take
	//way to long, or cause a stack overflow

	//Prim.java is the only actually successful random maze generator. It uses a randomized version of Prim's algorithm to
	//create a maze in which any point can always be reach from any other, and all walls and hallways are only 1 unit thick

	//Most of the commented out code are my attempts to improve the code that still don't work

	static ArrayToImage display = Driver.getDisplay();
	static int count = 0;
	static boolean mazeComplete = false;
	static int maze1[][] = {{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 0, 1},
			{1, 0, 1, 1, 0, 1, 1, 0, 0, 1, 1, 0, 1, 0, 0, 1, 1, 1, 0, 1},
			{1, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 1},
			{1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 0, 0, 0, 1},
			{1, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 1, 0, 0, 0, 0, 1, 0, 1},
			{1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1, 0, 1},
			{1, 0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1},
			{1, 1, 0, 1, 1, 0, 0, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 0, 1},
			{1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 1, 1, 1, 1},
			{1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 0, 0, 1, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 1, 0, 0, 1, 1, 0, 1, 1},
			{1, 0, 1, 1, 0, 1, 1, 0, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 1, 1},
			{1, 0, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 0, 1, 1, 0, 0, 1, 1},
			{1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1},
			{1, 0, 0, 1, 1, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 1, 1},
			{1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 1, 0, 0, 0, 1, 0, 1, 0, 1, 1},
			{1, 0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1},
			{1, 1, 0, 1, 1, 0, 0, 1, 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 1, 1},
			{1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}};
	static int maze2[][] = {{1, 1, 1, 1, 1, 0, 1, 1, 1, 1},
							{1, 1, 1, 0, 0, 0, 0, 0, 0, 1},
							{1, 0, 0, 0, 1, 0, 1, 0, 0, 1},
							{1, 0, 0, 1, 1, 0, 1, 1, 0, 1},
							{1, 1, 0, 0, 1, 0, 1, 1, 0, 1},
							{1, 0, 0, 1, 1, 0, 1, 0, 0, 1},
							{1, 1, 0, 0, 1, 0, 1, 0, 1, 1},
							{1, 0, 0, 1, 1, 0, 1, 0, 0, 1},
							{1, 1, 0, 0, 1, 0, 1, 0, 1, 1},
							{1, 1, 1, 1, 1, 0, 1, 1, 1, 1}};
	static int maze3[][] = {{1,1,0,1,1,1},
			{1,0,0,0,0,1},
			{1,1,0,1,0,1},
			{1,0,0,0,1,1},
			{1,0,1,0,0,1},
			{1,0,0,1,0,1},
			{1,1,1,1,0,1},
			{1,0,0,0,0,1},
			{1,0,1,0,1,1},
			{1,1,1,0,1,1}};
	//static int randomMaze[][] = randomMazeCreate();
	static int maze[][];
	static int endPoint;
	static ArrayList<Coordinate> usedSpaces = new ArrayList<Coordinate>();
	static ArrayList<Coordinate> leftStop = new ArrayList<Coordinate>();
	static ArrayList<Coordinate> rightStop = new ArrayList<Coordinate>();
	static ArrayList<Coordinate> upStop = new ArrayList<Coordinate>();
	static ArrayList<Coordinate> delayedLeft = new ArrayList<Coordinate>();
	static ArrayList<Coordinate> delayedRight = new ArrayList<Coordinate>();
	static Prim randomMaze;
	static boolean somethingEntered = true;
	static boolean remainingMoves = true;
	static int moveCount = 0;
	static int delay = 0;
	FloodFill(int[][] aMaze) throws IOException, InterruptedException
	{
		System.out.println("Flood Fill w/ Downward Bias: ");
		double milis = System.currentTimeMillis();
		maze = aMaze;
		usedSpaces = new ArrayList<Coordinate>();
		/*ReadFiles reader = new ReadFiles("/Users/brettschneider/Desktop/orthogonal_maze_with_20_by_20_cells.png");
		maze = reader.getMaze();*/
//		boolean firstRound = true;
//		while(count<numTimes) {
//			if(!firstRound) {
//				delayedLeft = new ArrayList<Coordinate>();
//				delayedRight = new ArrayList<Coordinate>();
//				//RandomMaze2 randomMaze = new RandomMaze2();
//				randomMaze = new Prim(ySize,xSize);
//				maze = randomMaze.getMaze();
//				usedSpaces = new ArrayList<Coordinate>();
//				/*ReadFiles reader = new ReadFiles("/Users/brettschneider/Desktop/orthogonal_maze_with_20_by_20_cells.png");
//				maze = reader.getMaze();*/
//			}
//			else
//				firstRound = false;
			display.refreshImage(toArray());
			int startPoint = 0;
			for(int i = 0; i < maze[0].length; i++)
			{
				if(maze[0][i] == 0)
				{
					startPoint = i;
					usedSpaces.add(new Coordinate(0,i));
					usedSpaces.add(new Coordinate(1,i));
				}
			}
			for(int i = 0; i < maze[0].length; i++)
			{
				if(maze[maze.length-1][i] == 0)
				{
					endPoint = i;
				}
			}
			mazeComplete = false;
			floodFillSearch(new Coordinate(1,startPoint));
			display.refreshImage(toArray());
//			count++;
//		}
		//int average = moveCount/count;
		//System.out.println("FloodFill average: " + average);
			double endMilis = System.currentTimeMillis();
			System.out.println("Flood Fill w/ Horizontal Bias Toward Endpoint Total Time: " + (endMilis - milis)*.001);
	}

	static boolean leftStopFull(){
		return false;
	}
	static boolean rightStopFull(){
		return false;
	}
	static boolean upStopFull(){
		return false;
	}
	private static void addToStops(Coordinate position) {
		leftStop.add(position);
		rightStop.add(position);
		upStop.add(position);
	}
	static void floodFillSearch(Coordinate position) throws InterruptedException
	{
		//addToStops(position);
		moveCount++;
		Thread.sleep(delay);
		display.refreshImage(toArray());	
		if(position.getY() == maze.length-1)
			mazeComplete = true;
		if(position.getY() == 0||mazeComplete == true)
			return;

		Coordinate leftPos = new Coordinate(position.getY(),position.getX()-1);
		Coordinate rightPos = new Coordinate(position.getY(),position.getX()+1);
		Coordinate downPos = new Coordinate(position.getY()+1,position.getX());
		Coordinate upPos = new Coordinate(position.getY()-1,position.getX());

		/*if(position.getX() < leftStop.get(position.getY()).getX() && leftStopFull() == true)
		{
			return;
		}
		if(position.getX() > rightStop.get(position.getY()).getX() && rightStopFull() == true)
		{
			return;
		}
		if(position.getX() < rightStop.get(position.getX()).getY() && upStopFull() == true)
		{
			return;
		}*/
		//if(position.getX()<endPoint) prioritizes movement towards the location of the endpoint
		if(checkUsedSpaces(downPos) == true && maze[downPos.getY()][downPos.getX()] == 0 && mazeComplete != true)
		{
			usedSpaces.add(downPos);
			floodFillSearch(downPos);
		}
		if(checkUsedSpaces(rightPos) == true && maze[rightPos.getY()][rightPos.getX()] == 0 && mazeComplete != true)
		{
			usedSpaces.add(rightPos);
			floodFillSearch(rightPos);
		}
		if(checkUsedSpaces(leftPos) == true && leftPos.getX() > 0 && maze[leftPos.getY()][leftPos.getX()] == 0 && mazeComplete != true)
		{
			usedSpaces.add(leftPos);
			floodFillSearch(leftPos);
		}
		if(checkUsedSpaces(upPos) == true && maze[upPos.getY()][upPos.getX()] == 0 && mazeComplete != true)
		{
			usedSpaces.add(upPos);
			floodFillSearch(upPos);
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
					arrayToPrint[j][i] = 2;
			}
		}
		return arrayToPrint;
	}
}
