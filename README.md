Thanks for taking a look!

I wrote this kind of sporaticly with no real planning. Because of that, it's not the most well structured code. Also, I wrote this before I had any college coding classes under my belt, so I really just kind of winged it. 

	Prim: Uses a randomized version of Prim's algorithm to create a maze. The mazes have no loops (think spanning tree) and only one solution. Each maze starts at the only open space in the very top of the maze and ends at the only space in the very bottom of the maze. Maze is stored as a binary array where 1 = wall and 0 = corridor
	
	Kruskal: Uses a randomized version of Kruskal's algorithm to create a maze. The mazes have the same format as described above ^.

	FloodFill: The flood fill classes use a recursive algorithm to brute force the maze until it reaches the end point. The algorithm calls itself every time it reaches some sort of turn or intersection. At the end, the current "search thread" will terminate and the next one in the queue will go until it either hits a dead end or finds the end of the maze. FloodFill tends to have a bias toward going down.

	FloodFillAlwaysTowardEndPoint: Same as FloodFill but has a purposeful bias to always try to be moving toward end point. This one was my first attempt to improve my maze solving algorithm, but it ended up being equally as bad as FloodFill. The 2 flood fill methods were my first attempts at maze solving and neither of them are at all good, but they both do work.

	ConvertMazeToGraph: Scans the maze and creates a graph with vertices at each intersection, turn & dead end in the maze. Initially, each vertex only stores the location, and the direction(s) of neighboring vertices. Next, each vertex is connected to any horizontally neighboring vertices. Since the graph is scanned from top to bottom going left to right (the numerical order of the index's of the maze array) connecting horizontally neighboring vertices is easy because all you need to do is look at either the previous or following element in the array of vertices. In order to connect vertically neighboring vertices the array of vertices was re-sorted to be order by their y values. 2 options for sorting algorithms are available: Insertion Sort or Merge Sort. Next, every vertex was removed and deleted from it's neighbors list of neighboring vertices. This is repeated until every vertex has exactly 2 neighbors (excluding the start and end vertices). This technique is significantly faster than the initial flood fill attempts, but still requires a lot of time to sort vertices for larger mazes

	ScanTwice: Also converts the maze to a graph, but instead of sorting the vertices, it scans the maze again but horizontally this time, resulting in one vertices list sorted by x and one sorted by y. Everything after that is the same as ConvertMazeToGraph. Basically I realized that scanning was happening a lot faster than the sorting was so I figured I should probably just do it twice instead.

	ArrayToImage: This converts the array of the maze consisting of 0 = corridor, 1 = wall, or 2 = solution path. The array is converted to a buffered image with 0 represented as black, 1 represented as white, and 2 represented as red. This image is then passed into a JFrame to be displayed.

Warnings: 

	1. there are a good amount of Class and Methods which are unused. Those are all vestigial ideas which didn't work out, but I kept them anyway in case anything in them became useful later. RandomMaze and RandomMaze2 are my first attempts at generating mazes before actually researching any ways to do it. They  sort of work... ReadFiles turns an imported image of a maze to a binary array as described above. While it does work, maze images I found on the Internet had very inconsistent formatting, so it would have taken a lot of overhead to be able to make them into solvable formats. My forth iteration of maze solving is the Prim class currently being used. Kruskal was my fifth attempt at improving my mazes, but it has some issues with large mazes.
		
	2. All of the maze solving programs run exponentially slower as the maze size gets larger. If you input a large number be ready to wait for a little while, and it still might not work (see Bugs)
 
Bugs: 

	1. The insertion sort algorithm crashes sometimes and I'm not sure why. Since the merge sort is significantly faster and usually works (see 2), I haven't taken the time to fix it. Because of that, when you "run all algorithms consecutively", the insertion sort version won't run, but you can run it in the graphing method menu.

	2. Merge sort works perfectly with smaller numbers but sometimes freaks out if you try it with a bigger maze (about 9 million pixels). Absolutely no idea why.

	3. for some reason the Kruskal algorithm just breaks around 500x500 mazes. Not sure why, but the Prim algorithm works great so just haven't fixed it

	4. I just recently broke the merge sort while playing around with an idea I had. I’ll get it fixed again soon.
