import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

public class Kruskal {
	// initialize the maze
	static int[][] buildMaze(int x, int y) throws Exception {
		int[][] maze;
		ArrayToImage display;
		HashSet<HashSet<Node>> sets = new HashSet<HashSet<Node>>();
		x /= 2;
		y /= 2;
		Node[][] nodes = new Node[x][y];
		for (int i = 0; i < x; i++)
			for (int j = 0; j < y; j++) {
				Node n = new Node((i * 2) + 1, (j * 2) + 1);
				nodes[i][j] = (n);

				HashSet<Node> hs = new HashSet<Node>();
				hs.add(n);
				sets.add(hs);
			}
		//initialize the maze
		maze = initMaze(x, y, nodes);
		display = Driver.getDisplay();
		LinkedList<Edge> edges = new LinkedList<Edge>();
		for (int i = 0; i < x; i++)
			for (int j = 0; j < y; j++)
				for (int a = 0; a < 2; a++)
					for (int b = 0; b < 2; b++) {
						try {
							if (a + b != 0 && a + b != 2)
								edges.push(new Edge(nodes[i][j], nodes[i + a][j + b]));
						} catch (Exception e) {

						}
					}
		Collections.shuffle(edges);
		int count = 0;

		int dispRate = (((x * 2) + 1) * ((y * 2) + 1)) / 80;

		int last = sets.size();

		boolean finished = false;

		//build maze using Kruskal's algorithm
		while (sets.size() > 1) {
			count++;
			Edge e = edges.removeFirst();

			union(e.n1, e.n2, sets);

			if (sets.size() < last)
				maze = buildMaze(e, maze);

			if (dispRate == 0 || count % dispRate == 0)
				display.refreshImage(maze);

			last = sets.size();
		}

		finished = false;
		int index;

		//add entrance
		while (!finished) {
			index = (int) (Math.random() * maze[0].length);
			if (maze[1][index] == 0) {
				maze[0][index] = 0;
				finished = true;
			}
		}

		finished = false;

		//add exit
		while (!finished) {
			index = (int) (Math.random() * maze[0].length);
			if (maze[maze.length - 2][index] == 0) {
				maze[maze.length - 1][index] = 0;
				finished = true;
			}
		}

		display.refreshImage(maze);
		
		return maze;
	}

	static int[][] initMaze(int x, int y, Node[][] n) {
		x = x * 2 + 1;
		y = y * 2 + 1;
		int[][] maze = new int[x][y];
		for (int[] row : maze)
			Arrays.fill(row, 1);
		for (Node[] row : n)
			for (Node e : row)
				maze[e.x][e.y] = 0;
		return maze;
	}

	static int[][] buildMaze(Edge e, int[][] maze) {
		int x1 = e.n1.x;
		int y1 = e.n1.y;
		int x2 = e.n2.x;
		int y2 = e.n2.y;
		if (x1 == x2) {
			maze[x1][y1 + 1] = 0;
		} else if (y1 == y2) {
			maze[x1 + 1][y1] = 0;
		}
		return maze;
	}

	static void union(Node n1, Node n2, HashSet<HashSet<Node>> sets) throws Exception {
		LinkedList<HashSet<Node>> ll = find(n1, n2, sets);

		if (ll != null) {
			int bigger, smaller;
			if (ll.get(0).size() > ll.get(1).size()) {
				bigger = 0;
				smaller = 1;
			} else {
				bigger = 1;
				smaller = 0;
			}
			int s = sets.size();
			HashSet<Node> smallSet = ll.get(smaller);
			sets.remove(smallSet);

			HashSet<Node> bigSet = ll.get(bigger);
			sets.remove(bigSet);

			merge(bigSet, smallSet);

			sets.add(bigSet);

			if(sets.size() == s) {
				String hashes = "";
				for (HashSet n: sets)
					hashes += ", " + n.hashCode();
				System.out.println("Size: " + s 
						+ "\nSets: " + sets
						+ "\nBigger = " + bigger + ", Smaller = " + smaller 
						+ "\nItem Hash: " + ll.get(smaller).hashCode()
						+ "\nAll Hash in Set: " + hashes.substring(2) 
						+ "\nFound by remove?: " + sets.remove(ll.get(smaller))
						+ "\nFound by contains?: " + sets.contains(ll.get(smaller))
						+ "\nIterable first: " + sets.contains(sets.iterator().next()));
			}
		}
	}

	private static void merge(HashSet<Node> big, HashSet<Node> small) {
		for (Node n : small)
			big.add(n);
	}

	static LinkedList<HashSet<Node>> find(Node n1, Node n2, HashSet<HashSet<Node>> sets) throws Exception {
		Iterator<HashSet<Node>> it = sets.iterator();
		while (it.hasNext()) {
			HashSet<Node> hs = it.next();
			boolean containsN1 = hs.contains(n1);
			boolean containsN2 = hs.contains(n2);
			if (containsN1 && containsN2) {
				return null;
			}

			if (containsN1) {
				LinkedList<HashSet<Node>> ll = new LinkedList<HashSet<Node>>(
						Arrays.asList(hs, findSpecificSet(n2, it)));
				return ll;
			}

			if (containsN2) {
				LinkedList<HashSet<Node>> ll = new LinkedList<HashSet<Node>>(
						Arrays.asList(hs, findSpecificSet(n1, it)));
				return ll;
			}
		}
		throw new Exception("ERROR: Nodes not found");
	}

	static HashSet<Node> findSpecificSet(Node n, Iterator<HashSet<Node>> it) {
		while (true) {
			HashSet<Node> hs = it.next();
			if (hs.contains(n)) {
				return hs;
			}
		}
	}

	static class Node {
		int x, y;

		Node(int aX, int aY) {
			x = aX;
			y = aY;
		}

		public String toString() {
			String s = "(" + Integer.toString(x) + "," + Integer.toString(y) + ")";
			return s;
		}
	}

	static class Edge {
		Node n1;
		Node n2;
		int weight;

		Edge(Node aN1, Node aN2) {
			n1 = aN1;
			n2 = aN2;
		}

		public String toString() {
			String s = n1.toString() + "----" + n2.toString();
			return s;
		}
	}
}
