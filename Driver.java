//Brett Schneider
//Description: Runs a couple variations of maze solving algorithms to solve a randomly generated maze
//
//Under the hood:
//	Random Maze Generation: Uses a randomized version of Prim's algorithm to create a maze. The mazes
//	have no loops (think spanning tree) and only one solution. Each maze starts at the only open space
// 	in the very top of the maze and ends at the only space in the very bottom of the maze. Maze is stored
//	as a binary array where 1 = wall and 0 = corridor
//
//	FloodFill: The flood fill classes use a recursive algorithm to brute force the maze until it reaches
//	the end point. The algorithm calls itself every time it reaches some sort of turn or intersection. At a
//	end, the current "search thread" will terminate and the next one in the queue will go until it either
//	hits a dead end or finds the end of the maze. FloodFill tends to have a bias toward going down.
//
//	FloodFillAlwaysTowardEndPoint: Same as FloodFill but has a purposeful bias to always try to be moving
//	toward end point. This one was my first attempt to improve my maze solving algorithm, but it ended up
//	being equally as bad as FLoodFill. The 2 flood fill methods were my first attempts at maze solving and 
//	neither of them are at all efficient
//
//	ConvertMazeToGraph: Scans the maze and creates a graph with vertices at each intersection, turn & dead
//	end in the maze. Initially, each vertex only stores the location, and the direction(s) of neighboring 
//	vertices. Next, each vertex is connected to any horizontally neighboring vertices. Since the graph is 
//	scanned from top to bottom going left to right (the numerical order of the index's of the maze array)
//	connecting horizontally neighboring vertices is easy because all you need to do is look at either the
//	previous or following element in the array of vertices. In order to connect vertically neighboring vertices
//	the array of vertices was re-sorted to be order by their y values. 2 options for sorting algorithms are 
//	available: Insertion Sort (just runs an insertion sort) or Merge Sort (uses each row as the partitions to merge).
//	Next, every vertex was removed and deleted from it's neighbors list of neighboring vertices. This is repeated
//	until every vertex has exactly 2 neighbors (excluding the start and end vertices). This technique is significantly 
//	faster than the initial flood fill attempts, but still requires a lot of time to sort vertices for larger mazes
//
//	ScanTwice: Also converts the maze to a graph, but instead of sorting the vertices, it scans the maze again but
//	horizontally this time, resulting in one vertices list sorted by x and one sorted by y. Everything after that is
// 	the same as ConvertMazeToGraph. Basically I realized that scanning was happening a lot faster than the sorting was
//	so I figured I should probably just do it twice instead.
//
//	ArrayToImage: This converts the array of the maze consisting of 0 = corridor, 1 = wall, or 2 = solution path. The
// 	array is converted to a buffered image with 0 represented as black, 1 represented as white, and 2 represented as 
//	red. This image is then passed into a JFrame to be displayed.
//
//Warnings: 
//	1. there are a good amount of Class and Methods which are unused. Those are all vestigial ideas
//	which didn't work out, but I kept them anyway in case anything in them became usefull later. RandomMaze and
//	RandomMaze2 are my first attempts at generating mazes before actually researching any ways to do it. They 
//	sort of work... ReadFiles turns an imported image of a maze to a binary array as described above. While it does
//	work, maze images I found on the Internet had very inconsistent formating, so it would have taken a lot of overhead
//	to be able to make them into solvable formats. My forth iteration of maze solving is the Prim class currently
//	being used. Kruskal was my fifth attempt at improving my mazes, but it has some stack overflow issues with large mazes.
//		
//	2. All of the maze solving programs run exponentially slower as the maze size gets larger. If you input a large number
//	be ready to wait for a little while, and it still might not work (see Bugs)
// 
//Bugs: 
//	1. The insertion sort algorithm crashes sometimes and I'm not sure why. Since the merge sort is significantly
//	faster and usually works (see 2), I haven't taken the time to fix it. Because of that, when you "run all algorithms
//	consecutively", the insertion sort version won't run, but you can run it in the graphing method menu
//
//	2. Merge sort works perfectly with smaller numbers but sometimes freaks out if you try it with a bigger maze (about
//	9 million pixels). Absolutely no idea why.
//
//  3. for some reason the Kruskal algorithm just breaks around 500x500 mazes. Not sure why, but the Prim algorithm works
// great so just haven't fixed it


import java.io.IOException;
import java.util.Scanner;
public class Driver {
	//hand made mazes used for testing
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
			{1, 1, 1, 0, 0, 0, 0, 0, 1, 1},
			{1, 1, 0, 0, 1, 0, 1, 0, 0, 1},
			{1, 0, 0, 1, 1, 0, 1, 1, 0, 1},
			{1, 1, 0, 0, 1, 0, 1, 1, 0, 1},
			{1, 0, 0, 1, 1, 0, 1, 0, 0, 1},
			{1, 1, 0, 0, 1, 0, 1, 0, 1, 1},
			{1, 0, 0, 1, 1, 1, 1, 0, 0, 1},
			{1, 1, 0, 0, 0, 0, 1, 0, 1, 1},
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

	static int maze [][];
	public static int round;
	public static int[][] emptyMaze = {{1}};
	public static ArrayToImage display;
	public static void main(String args[]) throws Exception
	{
		display = new ArrayToImage(emptyMaze);
		//get user input: decide which method to use, get maze dimensions
		while(true)
		{
			round = 0;
			System.out.println("\n1. Scan Maze Twice (Fastest) | 2. Graphing Method (faster) | 3. Flood Fill Method (slowest) | 4. Run all algorithms consecutively");
			int input = inputValue("Please choose a type of maze solving algorithm (1-4): ",4);
			if(input == 1)
			{
				maze = buildMazeOptions(5000);

				//find vertices twice
				ScanTwice scantron = new ScanTwice();
				scantron.convert(maze);
			}
			else if(input == 2)
				graphToMazeOptions();
			else if(input == 3)
				floodFillOptions();
			else
				runAllOptions();
		}
	}

	static int[][] buildMazeOptions(int recommendedMax) throws Exception {
		int x = inputValue("Enter X value (Under " + recommendedMax + " for solving time under 1 minute): ",30000);
		int y = inputValue("Enter Y value (Under " + recommendedMax + " for solving time under 1 minute): ",30000);
		int type = inputValue("Select algorithm to draw maze (1 for Prim(faster), 2 for Kruskal(don't go over 400x400)): ", 2);
		return type == 1 ? Prim.buildMaze(x, y) : Kruskal.buildMaze(x,y);
	}

	private static void floodFillOptions() throws Exception {
		System.out.println("\n1. Flood Fill w/ Horizontal Bias Toward End Point | 2. Flood Fill w/ Downward Bias | 3. Run both algorithms consecutively");
		int input = inputValue("Please choose a type of maze solving algorithm (1-3): ",3);
		if(input == 1)
		{
			maze = buildMazeOptions(150);

			//Flood Fill w/ Horizontal Bias Toward End Point
			display.refreshImage(maze);
			FloodFillAlwaysTowardEndpoint floodFillTE = new FloodFillAlwaysTowardEndpoint(maze);
		}
		else if(input == 2)
		{
			maze = buildMazeOptions(150);

			//Flood Fill w/ Downward Bias
			resetMaze();
			display.refreshImage(maze);
			FloodFill floodFillDownward = new FloodFill(maze);
		}
		else if(input == 3)
		{
			maze = buildMazeOptions(150);

			//Flood Fill w/ Horizontal Bias Toward Endpoint
			display.refreshImage(maze);
			FloodFillAlwaysTowardEndpoint floodFillTE = new FloodFillAlwaysTowardEndpoint(maze);

			//Flood Fill w/ Downward Bias
			resetMaze();
			round++;
			display.refreshImage(maze);
			FloodFill floodFillDownward = new FloodFill(maze);
		}
	}
	private static void graphToMazeOptions() throws Exception {
		System.out.println("\n1. Use Merge Sort Algorithm | 2. Use Insertion Sort Algorithm | 3. Scan Maze Twice (Fastest)| 4. Run all 3 algorithms consecutively");
		int input = inputValue("Please choose a type of maze solving algorithm (1-4): ",4);
		ConvertMazeToGraph grapher = new ConvertMazeToGraph();
		if(input == 1)
		{
			maze = buildMazeOptions(1500);

			//Merge Sorted
			display.refreshImage(maze);
			grapher.convert(maze,ConvertMazeToGraph.MERGESORT);
		}
		else if(input == 2)
		{
			maze = buildMazeOptions(500);

			//Insertion Sorted
			display.refreshImage(maze);
			grapher.convert(maze,ConvertMazeToGraph.INSERTIONSORT);
		}
		else if (input == 3)
		{
			maze = buildMazeOptions(2000);

			ScanTwice scantron = new ScanTwice();
			display.refreshImage(maze);
			scantron.convert(maze);
		}
		else
		{
			maze = buildMazeOptions(500);

			//Merge Sorted
			display.refreshImage(maze);
			grapher.convert(maze,ConvertMazeToGraph.MERGESORT);

			//Insertion Sorted
			resetMaze();
			round++;
			display.refreshImage(maze);
			grapher = new ConvertMazeToGraph();
			grapher.convert(maze,ConvertMazeToGraph.INSERTIONSORT);

			//twice find vertices
			resetMaze();
			round++;
			display.refreshImage(maze);
			ScanTwice scantron = new ScanTwice();
			scantron.convert(maze);
		}
	}
	private static void runAllOptions() throws Exception {
		maze = buildMazeOptions(150);

		//Merge Sorted
		display.refreshImage(maze);
		ConvertMazeToGraph grapher = new ConvertMazeToGraph();
		grapher.convert(maze,ConvertMazeToGraph.MERGESORT);

		//Insertion Sorted
		try {
			resetMaze();
			round++;
			display = new ArrayToImage(maze);
			grapher = new ConvertMazeToGraph();
			grapher.convert(maze,ConvertMazeToGraph.INSERTIONSORT);
		}
		catch (Exception e) {
			System.out.println("insertion failed");
		}

		//Flood Fill w/ Horizontal Bias Toward Endpoint
		resetMaze();
		round++;
		display.refreshImage(maze);
		FloodFillAlwaysTowardEndpoint floodFillTE = new FloodFillAlwaysTowardEndpoint(maze);

		//Flood Fill w/ Downward Bias
		resetMaze();
		round++;
		display.refreshImage(maze);
		FloodFill floodFill = new FloodFill(maze);

	}
	static int getRound() {
		return round;
	}
	static void resetMaze() {
		for(int i = 0; i < maze.length;i++)
			for(int j = 0; j < maze[0].length;j++)
				if(maze[i][j] == 2)
					maze[i][j] = 0;
	}
	static ArrayToImage getDisplay()
	{
		return display;
	}
	Driver getDriver()
	{
		return this;
	}
	public static int inputValue(String message, int maxValue)
	{
		Scanner reader = new Scanner(System.in);
		if (maxValue == -1)
		{
			reader.close();
			return 0;
		}
		else {
			boolean invalidInput = true;
			int input = 0;
			while (invalidInput == true)
			{
				System.out.print(message);
				try {
					input = reader.nextInt();
					if (input >= 1 && input <= maxValue)
						invalidInput = false;
					else
						System.out.println("invalid input");
				}
				catch (Exception e)
				{
					System.out.println("invalid input");
					reader.next();
				}
			}
			return input;
		}
	}
}
