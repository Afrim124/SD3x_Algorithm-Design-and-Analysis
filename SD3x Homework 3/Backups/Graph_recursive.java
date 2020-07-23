
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import org.w3c.dom.Node;

/**
 * A undirected graph class
 * 
 * Also includes functions for running dfs and bfs
 *
 */
public class Graph {
	
	private int n; //number of vertices
	private LinkedList<Integer>[] adj; //adjacency list
	private Maze.Move[][] desc;
	
	/**
	 * Constructs a graph with n vertices (containing no edges)
	 * 
	 * @param n - the number of vertices in the graph
	 */
	@SuppressWarnings("unchecked")
	public Graph(int n) {
		this.n = n;
		adj = (LinkedList<Integer>[]) new LinkedList[n];
		desc = new Maze.Move[n][];

		for (int i = 0; i < n; i++) {
			adj[i] = new LinkedList<>();
			desc[i] = new Maze.Move[4];
		}
		
		
	}
	public Graph(String filename) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(filename));
		String line = br.readLine();
		this.n = Integer.parseInt(line);
		adj = (LinkedList<Integer>[]) new LinkedList[n];
		desc = new Maze.Move[n][];
		
		for (int i = 0; i < n; i++) {
			adj[i] = new LinkedList<>();
			desc[i] = new Maze.Move[4];
		}
		line = br.readLine();
		while (line != null) {
			String[] values = line.split(",", 2);
			int startNode = (int)Integer.parseInt(values[0]);
			int endNode = (int)Integer.parseInt(values[1]);
			adj[startNode].add(endNode);
			adj[endNode].add(startNode);
			line = br.readLine();
		}
		br.close();
	}
	
	public boolean isNeighbor(Integer start, Integer end) {
		return adj[start].contains(end);
	}
	public boolean containsNode(Integer node) {
		return adj.length > node;
	}
	
	public class Node {
		private Integer number;
		private LinkedList<Integer> neighbors;
		private Maze.Move[] descr;
		private boolean visited;
		private boolean finished;
		
		Node(Integer number){
			this.number = number;
			neighbors = adj[number];
			descr = desc[number];
			visited = false;
			finished = false;
		}
		
		public Integer getNumber(){
			return number;
		}
		
		public LinkedList<Integer> getNeighbors(){
			return neighbors;
		}
		
		
		public Maze.Move getDescription(Integer neighbor){
			return descr[neighbors.indexOf(neighbor)];
		}
		public void setVisited() {
			visited = true;
		}
		
		public void setFinished() {
			finished = true;
		}
		
		public boolean getVisited() {
			return visited;
		}
		
		public boolean getFinished() {
			return finished;
		}
		
		
	}
	/**
	 * add an edge between vertices v and w
	 */
	public void addEdge(int v, int w) {
		adj[v].add(w); //add w to v's adjacency list
		adj[w].add(v);
	}
	
	/**
	 * add an edge between vertices v and w
	 */
	public void addEdgeForMaze(int v, int w) {
		adj[v].add(w); //add w to v's adjacency list
//		adj[w].add(v);
	}
	
	/**
	 * outputs the neighbors of a vertex v
	 */
	public List<Integer> neighbors(int v) {
		return adj[v];
	}
	
	/**
	 * @return the number of vertices in the graph
	 */
	public int size() {
		return n;
	}
	
	public void addDescription(int v, Maze.Move desc2) {
		// TODO Auto-generated method stub
		for(int i=0;i<4;i++) {
			if(desc[v][i]==null) {
					desc[v][i]=desc2;
				    break;
			}
		}
	}
	
	
	public class DepthFirstSearch {
		private Set<Node> marked;
		private Graph graph;
		private ArrayList<Node> nodeList = new ArrayList<Node>();
		private Stack<Maze.Move> moveStack= new Stack<Maze.Move>();
		private List<Maze.Move> moveList = new ArrayList<Maze.Move>();
		
		public DepthFirstSearch(Graph graphToSearch) {
			marked = new HashSet<Node>();
			graph  = graphToSearch;
			for (Integer i=0; i<graph.n;i++) {
				if (adj[i] != null) {
					nodeList.add(graph.new Node(i));	
				}
			}
		}
		
		public Node getNode(Integer number) {
			return nodeList.get(number);
		}
		

		public boolean dfs(Node endNode, Node start) {
			if (!containsNode(start)) {
				return false;
			}
			
			if (start.equals(endNode)) {
				return true;
			} else {
				marked.add(start);
				start.setVisited();
				for (Integer neighbor : start.getNeighbors()) {
					Node neighborNode = getNode(neighbor);
					if(neighborNode.getVisited() == false) {
						moveStack.push(start.getDescription(neighbor));
						if (dfs(endNode, neighborNode)) 
							return true;
					} 
				}
			start.setFinished();
			moveStack.pop();
			return false;
			}
		}

		public boolean containsNode(Node start) {
			return (start.getNeighbors() != null);
		}
		
		public List<Maze.Move> getNodesVisited() {
			for (Maze.Move move:moveStack) {
				moveList.add(move);
			}
			return moveList;
		}

//		public boolean allNeighborsVisited(Node start) {
//			boolean notVisited= true;
//			for (Integer neighbor : start.getNeighbors()) {
//				notVisited = !nodeList.get(neighbor).getVisited();
//			}
//			return !notVisited;
//		}
	}
	/**
	 * returns the number of shortest paths from s to t
	 * 
	 * ex. if the shortest path from s to t is of length 4, and there
	 * are two distinct paths from s to t of length 4, then you should return 2
	 * 
	 * @param s the source vertex
	 * @param t the destination vertex
	 * @return
	 */

	public class BreadthFirstSearch {
		protected Set<Integer> marked;
		protected Queue<Integer> depth  = new LinkedList<Integer>();
		protected Graph graph;
		protected Queue<Integer> toExplore = new LinkedList<Integer>();
		protected int oldDepth;
		public boolean found;
		public Integer depthFound;
		public int nrOfPaths;

		public BreadthFirstSearch(Graph graphToSearch) {
			marked = new HashSet<Integer>();
			graph = graphToSearch;
			depthFound=graph.n; //Maximum value
			nrOfPaths=0;
			oldDepth = 0;
			found = false;
			depth.add(0);
		}
		
		public boolean bfs(int start, int elementToFind) {
			if (!graph.containsNode(start) || !graph.containsNode(elementToFind)) {
					return false;
			}
			if (start == (elementToFind)) {
				return true;
			}
	
			marked.add(start);
			toExplore.add(start);

//			String sep = "";

			while (!toExplore.isEmpty()) {
				Integer current = toExplore.remove();
				int currDepth = depth.poll();
				if (currDepth > oldDepth) {
//				      System.out.println();
				      oldDepth = currDepth;
//				      sep = "";
				    }
//				System.out.print(sep + current);
//			    sep = " ";
	
				for (Integer neighbor : graph.neighbors((int)current)) {
					if (!marked.contains(neighbor)) {
						if (neighbor==elementToFind) {
							if (currDepth<=depthFound) {
								depthFound=currDepth;
								nrOfPaths++;
								found = true;
								return true;
							}
						}
						depth.add(currDepth + 1);
						bfs(neighbor,elementToFind);
					}
				}
			}
			return false;
		}
		

	}
	BreadthFirstSearch bfs1 = null;

	public int minDistance(int src, int dest) {
		bfs1 = new BreadthFirstSearch(this);
		bfs1.bfs(src, dest);
		if (bfs1.found)
			return bfs1.depthFound;
		return -1;
	}


	public int numShortestPaths(int s, int t) {
		this.minDistance(s, t);
//		if (this.isNeighbor(s, t)) return 1;
//		System.out.println("Depth found is: " + bfs1.depthFound);
//		System.out.println("Nr. of paths: " + bfs1.nrOfPaths);
		if (bfs1.found)
			return bfs1.nrOfPaths;
		return -1;
	}

	public static void main(String[] args) {
		Graph g = null;
		try {
		g = new Graph("files/maxPaths5LevelGraph.txt");
		}  catch (IOException e1) {
			System.out.println("File doesn't exist.");
		}
//        System.out.println(g.minDistance(0, 3));
        System.out.println(g.numShortestPaths(0, 51));
//        System.out.println(g.numShortestPaths(0, 2));
//        System.out.println(g.numShortestPaths(0, 1));
//        System.out.println(g.numShortestPaths(1, 2));
	}
}
