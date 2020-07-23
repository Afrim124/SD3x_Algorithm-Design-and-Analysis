
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class MST {
	protected static int vertex_number;
	protected static Color[] colours;
	protected static Color[] colours2;
	protected static Boolean[] ap; //Articulation points
	protected static Integer[] num_children;
	protected static int discover_count;
	protected static Integer[] dfs_number;
	protected static Integer[] low;
	protected static boolean[][] visited;
	protected static Boolean[] has_back_edge;
	protected static boolean treeDoesntContainHeavy;
	protected static Object[] ba_temp;
	protected static Map<Integer,List<Integer>> back_edges;
	
	public MST (WeightedUndirectedGraph G) {
		vertex_number=G.size();
		colours = new Color[vertex_number];
		colours2 = new Color[vertex_number];
		ap = new Boolean[vertex_number];
		num_children = new Integer[vertex_number];
		dfs_number = new Integer[vertex_number]; 
		low = new Integer[vertex_number];
		visited = new boolean[vertex_number][vertex_number];
		has_back_edge = new Boolean[vertex_number];
		ba_temp= new Object[vertex_number];
		back_edges = new HashMap<Integer,List<Integer>>(vertex_number);
		for(int i=0;i<vertex_number;i++) {
			colours[i]=MST.Color.WHITE;
			colours2[i]=MST.Color.WHITE;
			ap[i]=false;
			has_back_edge[i]=false;
			num_children[i]=0;
			dfs_number[i]=0;
			low[i]=0;
			ba_temp[i] = new ArrayList<Integer>();
			back_edges.put(i, (List<Integer>)ba_temp[i]);
			for (int j=0;j<vertex_number;j++)
				visited[i][j]=false;
		}
		discover_count=1;
		treeDoesntContainHeavy=true;
	}
	/**
	 * This function takes in a graph, with weights classified as light, medium or heavy
	 * It should output a list of all edges in the graph that must be a part of
	 * any minimum spanning tree
	 * 
	 * You should use the Tuple class provided to output edges.  For instance, to output
	 * the edge (u,v), you would add "new Tuple(u,v)" to your list
	 * 
	 * @param g
	 * @return
	 */
	public void articulation_vertices(WeightedUndirectedGraph G) {
		for(int u=0;u<vertex_number;u++) {
			if (colours[u]==MST.Color.WHITE) {
				articulation_vertex_visit(u,G);
				if (num_children[u] > 1) {
					ap[u]= true;
				} else { 
					ap[u]=false;
				}
			}
		}
	}
	
	protected void articulation_vertex_visit(int v, WeightedUndirectedGraph G) {
		dfs_number[v]=discover_count;
		discover_count++;
		low[v]= dfs_number[v];
		colours[v]=MST.Color.GREY;
		List<Integer> neighbors = G.neighbors(v);
		for (Integer u:neighbors) {
			if(colours[u]==MST.Color.WHITE) {
				visited[v][u]=true;
				articulation_vertex_visit(u,G);
				num_children[v]++;
				if (low[u]<low[v])
					low[v]=low[u];
				if (low[u] >= dfs_number[v])
					ap[v]=true;
			} else if (colours[u]==MST.Color.GREY&!visited[u][v]) {
				has_back_edge[v]=true;
				ba_temp[v] = back_edges.get(v);
				((List<Integer>) ba_temp[v]).add(u);
				back_edges.put(v, (List<Integer>)ba_temp[v]);
			}
			if (dfs_number[u] < low[v])
				low[v]= dfs_number[u];
			if (num_children[v] > 1) {
				ap[v]= true;
			
			} 

		}
		colours[v]= MST.Color.BLACK;
	}
	
	public static Set<Tuple> mustExist(WeightedUndirectedGraph<Weight> g) {
	Set<Tuple> answerSet = new TreeSet<>();
	if (g.size()==2) {
		if (g.weight(0, 1)==MST.Weight.LIGHT) {
			answerSet.add(new Tuple(0,1));
		}
	} else {
	MST MST1 = new MST(g); 
	MST1.articulation_vertices(g); 
//	for (int i=0;i<MST1.ap.length;i++) {
//		if (MST1.ap[i]) {System.out.println("De vertex "+i+" is een articulation point.");}
//	}
//	for (Integer i=0;i<has_back_edge.length;i++) {
//		if (has_back_edge[i]) {System.out.println("De vertex "+i+" heeft een back edge naar: " + back_edges.get(i));}
//		}
	for (int u=0; u<g.size();u++) {
		List<Integer> neighbors = g.neighbors(u);
		for(int w:neighbors) {
			if (has_back_edge[u]&&back_edges.get(u).contains(w)&&MST1.getOppositeIfItHasLowerWeight(u,w,g)!=null) {
				answerSet.add(MST1.getOppositeIfItHasLowerWeight(u,w,g));
			} else if (MST1.ap[u]&MST1.ap[w]) {
				answerSet.add(new Tuple(u,w));
			}
		}
	}
	}
	return answerSet;
	}
	
    protected Tuple getOppositeIfItHasLowerWeight(int u,int v, WeightedUndirectedGraph G) {
    	Tuple oppositeTuple=null;
    	List<Integer> ba_temp2;
   		if(u-v>=3) {
    		if(back_edges.get(u).size()>1) {
    			ba_temp2=back_edges.get(u);
    			for(int i=0;i<ba_temp2.size();i++) {
    				Integer w = ba_temp2.get(i);
    				if(u-w>1) { //Node has multiple back edges.
    					if((G.weight(u,v)==MST.Weight.HEAVY&(G.weight(w,v+1)==MST.Weight.MEDIUM|G.weight(w,v+1)==MST.Weight.LIGHT))||(G.weight(u,v)==MST.Weight.MEDIUM&G.weight(w,v+1)==MST.Weight.LIGHT)) {
    						oppositeTuple = new Tuple(w, v+1);		//Remove node from cycle
    					}
   					} else if (u-w==1&w>0) { //Korte back edge
   						if((G.weight(u,w)==MST.Weight.HEAVY&(G.weight(u, u+1)==MST.Weight.MEDIUM|G.weight(u, u+1)==MST.Weight.LIGHT))||(G.weight(u,v)==MST.Weight.MEDIUM&G.weight(u, u+1)==MST.Weight.LIGHT)) {
    						oppositeTuple = new Tuple(u, u+1);		
    					} else if(G.weight(u,w)==MST.Weight.LIGHT) {
    						oppositeTuple = new Tuple(u, w);	
    					}
   					}
    			}
    		}
    	} else if (u-v==2) {
    			if (G.neighbors(u).contains(u-1)) {
    				if ((G.weight(u,v)==MST.Weight.HEAVY&((G.weight(u,u-1)==MST.Weight.MEDIUM|G.weight(u,u-1)==MST.Weight.LIGHT)))||(G.weight(u,v)==MST.Weight.MEDIUM&G.weight(u,u-1)==MST.Weight.LIGHT)){
    					oppositeTuple=new Tuple(u, u-1);
    				}
    			}
    			else if (G.neighbors(u).contains(u+1)) {
    				if ((G.weight(u,v)==MST.Weight.HEAVY&((G.weight(u,u+1)==MST.Weight.MEDIUM|G.weight(u,u+1)==MST.Weight.LIGHT)))||(G.weight(u,v)==MST.Weight.MEDIUM&G.weight(u,u+1)==MST.Weight.LIGHT)) {
    					oppositeTuple=new Tuple(u, u+1);
    				}
    			}
    	}
  		return oppositeTuple;
	}
	/**
	 * This function takes in a graph, with weights classified as light, medium or heavy
	 * It should output a list of all edges in the graph that must NOT be a part of
     * any minimum spanning tree
	 * 
	 * @param g
	 * @return the set of edges (unordered tuples of vertices) that must not
	 * exist in any MST
	 */	
	public void DFS(WeightedUndirectedGraph G) {
			for(int u=0;u<vertex_number;u++) {
				if (colours2[u]==MST.Color.WHITE) {
					DFS_VISIT(u,G);
				}
			}

		}
		
	protected void DFS_VISIT(int v, WeightedUndirectedGraph G) {
			colours2[v]=MST.Color.GREY;
			List<Integer> neighbors = G.neighbors(v);
			for (Integer u:neighbors) {
				if(colours2[u]==MST.Color.WHITE) {
					visited[v][u]=true;
					treeDoesntContainHeavy = (G.weight(v, u)!=MST.Weight.HEAVY);
					DFS_VISIT(u,G);
				}
				else if (colours2[u]==MST.Color.GREY&!visited[u][v])
					has_back_edge[v]=true;
			}
			colours2[v]= MST.Color.BLACK;
		}
		
		
    public static Set<Tuple> mustNotExist(WeightedUndirectedGraph<Weight> g) {
    	Set<Tuple> answerSet = new TreeSet<>();
    	MST MST1 = new MST(g);
    	MST1.DFS(g);
//    	for (int i=0;i<has_back_edge.length;i++) {
//		if (has_back_edge[i]) {System.out.println("De vertex "+i+" heeft een back edge.");}
//		}
		for (int u=0; u<g.size();u++) {
			List<Integer> neighbors = g.neighbors(u);
			for(int w:neighbors) {
				if (MST1.has_back_edge[u]&g.weight(u,w)==MST.Weight.HEAVY&treeDoesntContainHeavy)
				{
					answerSet.add(new Tuple(u,w));
				}
			}
		}
		return answerSet;
    }

	/**
	 * Edges in this MST are classified as light, medium or heavy
	 * (their actual edge weights are not known)
	 */
	public enum Weight {
		LIGHT, MEDIUM, HEAVY
	}
//	Colors for the DFS's
	public enum Color {
		WHITE, GREY, BLACK
	}

	private static WeightedUndirectedGraph<Weight> buildGraph(String filename) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(filename));
		String line = br.readLine();
		int n = Integer.parseInt(line);
		WeightedUndirectedGraph<Weight> g = new WeightedUndirectedGraph(n);

		for (line = br.readLine(); line != null; line = br.readLine()) {
			String[] edge = line.split(",");
			int u = Integer.parseInt(edge[0]);
			int v = Integer.parseInt(edge[1]);
			int wVal = Integer.parseInt(edge[2]);
			Weight weight = wVal == 0 ? Weight.LIGHT : (wVal == 1 ? Weight.MEDIUM : Weight.HEAVY);
			g.addEdge(u, v, weight);
		}

		br.close();
		return g;
	}
	
//	public static void main(String[] args) {
//	}
}
