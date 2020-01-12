import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

//findNeighbors: create ordered array from vertice list by X and then Y and one by Y and then X

public class ConvertMazeToGraph {
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
	static final String MERGESORT = "Merge Sort";
	static final String INSERTIONSORT = "Insertion Sort";
	static final String FINDVERTICESTWICE  = "Find Vertices Twice";

	static ArrayList<Vertice> vertices = new ArrayList<Vertice>();
	static ArrayList<Vertice> vertices2 = new ArrayList<Vertice>();
	static ArrayList<Vertice> vertices3 = new ArrayList<Vertice>();

	ArrayList<Vertice> sortedByX;
	ArrayList<Vertice> sortedByY;
	static Vertice startVert;
	Vertice endVert;
	ArrayToImage display;
	//ArrayToImage display = Driver.getDisplay();

	int[][] convert(int maze[][],String sortingAlgorithm)
	{
		display = Driver.getDisplay();
		//display = new ArrayToImage(Driver.emptyMaze);
		System.out.println("Graphing Method Using " + sortingAlgorithm + ": ");
		double milis = System.currentTimeMillis();
		int startPoint = 0;
		int endPoint = 0;
		for(int i = 0; i < maze[0].length; i++)
		{
			if(maze[0][i] == 0)
			{
				startPoint = i;
			}
		}
		vertices.add(new Vertice(false,false,true,false,startPoint,0));
		startVert = vertices.get(0);
		boolean north;
		boolean south;
		boolean east;
		boolean west;
		for(int i = 1; i < maze.length-1; i++)
		{
			for(int j = 1; j < maze[0].length-1; j++)
			{
				north = false;
				south = false;
				east = false;
				west = false;
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
					vertices.add(new Vertice(north,east,south,west,j,i));
				}
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
					vertices.add(new Vertice(north,east,south,west,j,i));
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
		vertices.add(new Vertice(true,false,false,false,endPoint,maze.length-1));
		endVert = vertices.get(vertices.size()-1);
		/*for(int i = 0; i < vertices.size(); i++)
		{
			System.out.print(i + ": (" + vertices.get(i).getX() + ","+vertices.get(i).getY()+"): ");
			System.out.print("North: " + vertices.get(i).isNorth() + ", ");
			System.out.print("East: " + vertices.get(i).isEast() + ", ");
			System.out.print("South: " + vertices.get(i).isSouth() + ", ");
			System.out.print("West: " + vertices.get(i).isWest() + ", ");
			System.out.println();
		}*/
		//		System.out.println("Time to find vertices: "+milis*.001);
		//		System.out.println("Number of vertices: "+vertices.size());
		double endMilis = System.currentTimeMillis();
		System.out.println("time to find vertices: " + (endMilis - milis)*.001);
		sortedByY = vertices;
		connectHorizontalVertices();
		if(sortingAlgorithm.equals(MERGESORT))
		{
			sortedByX = mergeSort(vertices);
		}
		else
		{
			sortedByX = insertionSort(vertices);
		}
		connectVerticalVertices();
		//connectVertices(vertices.get(1),vertices);
		//for(Vertice x: vertices)
		//maze[x.getY()][x.getX()] = 2;
		milis=System.currentTimeMillis()-milis;
		findPath();
		/*for(Vertice x: vertices)
		{
			maze[x.getY()][x.getX()] = 2;
			display.refreshImage(maze);
		}*/
		/*for(int i = 0;i<vertices.size();i++)
		{
			System.out.println("(" + vertices.get(i).getX() + ","+vertices.get(i).getY()+"): "+vertices.get(i).getNeighbors().size());
		}*/
		milis = System.currentTimeMillis() - milis;
		//System.out.println("Graph solver: " + milis*0.001);
		maze = drawLines(maze);
		endMilis = System.currentTimeMillis();
		System.out.println("Graphing Method Using " + sortingAlgorithm + " Total Time: " + (endMilis - milis)*.001);
		return maze;
	}

	void findPath(){
		double milis = System.currentTimeMillis();
		boolean pathFound = false;
		while(!pathFound)
		{
			pathFound = true;
			for(int i = 0; i < vertices.size(); i++)
			{
				if(vertices.get(i).getNeighbors().size() == 1)
				{
					if(vertices.get(i).equals(startVert) == false && vertices.get(i).equals(endVert) == false)
					{
						pathFound = false;
						vertices.get(i).getNeighbors().get(0).removeNeighbor(vertices.remove(i));
					}
				}
			}
		}
		double endMilis = System.currentTimeMillis();
		System.out.println("time to delete nodes: " + (endMilis - milis)*.001);
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
		for(int i = 1; i < vertices.size();i++)
		{
			if(vertices.get(i).isWest())
				vertices.get(i).setNeighbor(vertices.get(i-1));
			if(vertices.get(i).isEast())
				vertices.get(i).setNeighbor(vertices.get(i+1));
		}
		double endMilis = System.currentTimeMillis();
		System.out.println("time to connect horizontal vertices: " + (endMilis - milis)*.001);
	}

	ArrayList<Vertice> getVertices()
	{
		return vertices;
	}

	public ArrayList<Vertice> mergeSort(ArrayList<Vertice> array)
	{
		//		double milis = System.currentTimeMillis();
		//		ArrayList<Integer> startPoints = new ArrayList<Integer>(); 
		//		for(int i = 1; i<array.size();i++)
		//			if(array.get(i-1).getY()<array.get(i).getY())
		//				startPoints.add(i);
		//		//startPoints.add(array.size());
		//		ArrayList<ArrayList<Vertice>> partitions = new ArrayList<ArrayList<Vertice>>();
		//		int endPointGap = 1;
		//		boolean sorted = false;
		//		int index1;
		//		int index2;
		//		int currentIndex = 0;
		//		for(int i = 0; i < startPoints.size();i++)
		//		{
		//			partitions.add(new ArrayList<Vertice>());
		//		}
		//		for(int i = 0;i<array.size()-1;i++)
		//		{
		//			if(i==startPoints.get(currentIndex)&&i!=startPoints.get(startPoints.size()-1))
		//			{
		//				currentIndex++;
		//			}
		//			partitions.get(currentIndex).add(array.get(i));
		//		}
		//		ArrayList<Vertice> temp = new ArrayList<Vertice>();
		//		int currentSmallestIndex = 0;
		//		startPoints.add(0,0);
		//		partitions.add(new ArrayList<Vertice>());
		//		partitions.get(partitions.size()-1).add(array.get(array.size()-1));
		//		while(partitions.size() > 0)
		//		{
		//			currentSmallestIndex = 0;
		//			for(int i = 1; i < partitions.size(); i++)
		//			{
		//				if(partitions.get(i).get(0).getX() < partitions.get(currentSmallestIndex).get(0).getX())
		//				{
		//					currentSmallestIndex = i;
		//				}
		//				else if(partitions.get(i).get(0).getX() == partitions.get(currentSmallestIndex).get(0).getX())
		//				{
		//					if(partitions.get(i).get(0).getY() < partitions.get(currentSmallestIndex).get(0).getY())
		//						currentSmallestIndex = i;
		//				}
		//			}
		//			temp.add(partitions.get(currentSmallestIndex).remove(0));
		//			if(partitions.get(currentSmallestIndex).size() == 0)
		//			{
		//				partitions.remove(currentSmallestIndex);
		//				partitions.trimToSize();
		//			}
		//		}
		//		array = temp;
		//		double endMilis = System.currentTimeMillis();
		//		System.out.println("time to merge sort: " + (endMilis - milis)*.001);
		//		return array;			

		return mergeSort(0, array.size()-1, array);
	}

	ArrayList<Vertice> mergeSort(int lower, int upper, ArrayList<Vertice> array) {
		if(lower == upper)
			return new ArrayList<Vertice>(Arrays.asList(array.get(lower)));
		int mid = (upper + lower) / 2;
		return merge(mergeSort(lower, mid, array), mergeSort(mid + 1, upper, array));
	}

	ArrayList<Vertice> merge(ArrayList<Vertice> array1, ArrayList<Vertice> array2) {
		ArrayList<Vertice> merged = new ArrayList<Vertice>();

		int index1 = 0;
		int index2 = 0;

		Vertice c1 = array1.get(index1);
		Vertice c2 = array2.get(index2);

		while(index1 < array1.size() && index2 < array2.size()){
			if((c1.getX() < c2.getX()) || (c1.getX() == c2.getX() && c1.getY() < c2.getY())) {
				merged.add(c1);
				c1 = array1.get(index1++);
			}
			else {
				merged.add(c2);
				c2 = array2.get(index2++);
			}
		}

		merged.addAll(array1.subList(index1, array1.size()));
		merged.addAll(array2.subList(index2, array2.size()));

		return merged;
	}

	public static ArrayList<Vertice> insertionSort(ArrayList<Vertice> array)  
	{ 
		double milis = System.currentTimeMillis();
		boolean unsorted = true;
		int currentIndex = 0;
		while(unsorted)
		{
			//System.out.println("Sorting: "+ currentIndex + "/" + vertices.size());
			for(int i = currentIndex+1; i<array.size();i++)
			{
				if (array.get(i).getX() < array.get(currentIndex).getX()){
					array.add(currentIndex,array.remove(i));
				}
				else if(array.get(i).getX() == array.get(currentIndex).getX())
				{
					if(array.get(i).getY()<array.get(currentIndex).getY())
					{
						array.add(currentIndex,array.remove(i));
					}
				}
			}
			currentIndex++;
			if(currentIndex == array.size()-1)
				unsorted = false;
		}
		double endMilis = System.currentTimeMillis();
		System.out.println("time to insertion sort: " + (endMilis - milis)*.001);
		return array;
	} 
	int[][] drawLines(int maze[][])
	{
		int startY;
		int endY;
		ArrayList<Vertice> temp = (ArrayList<Vertice>) vertices.clone();
		boolean finished = false;			
		Vertice v =temp.get(0);
		Vertice t;
		int count = 0;
		boolean entered = false;
		int displayRate = vertices.size()/100;
		if (displayRate == 0)
			displayRate = 1;
		while(!finished)
		{
			count++;
			if(v.getNeighbors().size()==0)
			{
				entered = false;
				for(int i = 0; i < temp.size();i++)
				{
					if(temp.get(i).getNeighbors().size()>0)
					{
						v=temp.get(i);
						entered = true;
						continue;
					}
					else
						temp.remove(i);
				}
				if(entered == false)
					finished = true;
			}
			else if(v.getX()==v.getNeighbors().get(0).getX())
			{
				if(v.getY() < v.getNeighbors().get(0).getY())
				{
					startY = v.getY();
					endY = v.getNeighbors().get(0).getY();
				}
				else
				{
					startY = v.getNeighbors().get(0).getY();
					endY = v.getY();
				}
				for(int i = startY;i<endY+1;i++)
				{
					maze[i][v.getX()] = 2;
					//Driver.getDisplay().refreshImage(maze);
				}
				temp.remove(v);
				t=v.getNeighbors().get(0);
				v.getNeighbors().get(0).removeNeighbor(v);
				v=t;
			}
			else 
				//(v.getY()==v.getNeighbors().get(0).getY())
			{
				if(v.getX() < v.getNeighbors().get(0).getX())
				{
					startY = v.getX();
					endY = v.getNeighbors().get(0).getX();
				}
				else
				{
					startY = v.getNeighbors().get(0).getX();
					endY = v.getX();
				}
				for(int i = startY;i<endY+1;i++)
				{
					maze[v.getY()][i] = 2;
				}
				temp.remove(v);
				t=v.getNeighbors().get(0);
				v.getNeighbors().get(0).removeNeighbor(v);
				v=t;
			}
			if(count%displayRate == 0)
				display.refreshImage(maze);
		}
		display.refreshImage(maze);
		return maze;
	}

}
