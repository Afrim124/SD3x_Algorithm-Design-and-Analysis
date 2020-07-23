
import static org.junit.Assert.assertEquals;

import java.lang.reflect.Array;
import java.util.*;

public class GreedyDynamicAlgorithms {

	/**
	 * Goal: find the smallest number of red intervals to select, such that
	 * every blue interval overlaps with at least one of the selected red intervals.
	 * Output this number
	 * 
	 * @param red - the list of red intervals
	 * @param blue - the list blue intervals
	 * @return
	 */
	public static int optimalIntervals(ArrayList<Interval> red, ArrayList<Interval> blue) {
		Map<Integer, Integer> map= new HashMap();
		int i=0;
		for(Interval a: red){
			map.put(i,0);
				for(Interval b: blue) {
				if ((a.start>=b.start && a.start<=b.finish)& !(a.finish>=b.start && a.finish<=b.finish)) {
					int temp = map.get(i);
					map.put(i, ++temp);
				}
				if (a.start>=b.start && a.start<=b.finish) {
					int temp = map.get(i);
					map.put(i, ++temp);
				}
				if ((a.finish>=b.start && a.finish<=b.finish)&!(a.start>=b.start && a.start<=b.finish)) {
					int temp = map.get(i);
					map.put(i, ++temp);
				}
				if (a.finish>=b.start && a.finish<=b.finish) {
					int temp = map.get(i);
					map.put(i, ++temp);
				}
				if (a.start<b.start && a.finish>b.finish) {
					int temp = map.get(i);
					map.put(i, ++temp);
				}
			}
			i++;
		}
		ArrayList<Integer> array1 = new ArrayList();
		ArrayList<Integer> array2 = new ArrayList();
		Iterator<Map.Entry<Integer, Integer>> it = map.entrySet().iterator(); 
			while (it.hasNext()) {
			    Map.Entry<Integer, Integer> pair = it.next();
			    array1.add(pair.getKey());
			    array2.add(pair.getValue());
			}
		ArrayList<Interval> maxIntervals = new ArrayList();
		ArrayList<Interval> blue2 = new ArrayList();
		ArrayList<Interval> maxIntervals2 = new ArrayList();
		for(int j= 0; j<red.size();j++) {
				int max_int=findMaxValues(array2);
				array2.remove(max_int);
				maxIntervals.add(red.get(array1.get(max_int)));
				array1.remove(max_int);
				blue2 = intersects(maxIntervals,blue);
				int k=0;
				for (Interval a:blue) {
					if(blue2.contains(a))
						k++;
				}
				if (k==blue.size()) {
					maxIntervals2= intersects2(maxIntervals, blue);
						return maxIntervals2.size();
				}
		}
		return -1;
	}
	
	private static ArrayList<Interval> intersects (ArrayList<Interval> red2, ArrayList<Interval> blue) {
		ArrayList<Interval> temp = new ArrayList();
		for(Interval a: red2){
			for(Interval b: blue) {
				if ((a.start<b.start && a.finish>b.finish)|(a.start>=b.start && a.start<=b.finish)|(a.finish>=b.start && a.finish<=b.finish) ) {
					temp.add(b);
				}
			}
		}
		Set<Interval> blue3 = new HashSet();
		for (Interval a:temp ) {
			blue3.add(a);
		}
		ArrayList<Interval> blue4 = new ArrayList();
		for (Interval a: blue3)
		blue4.add(a);
		return blue4;
	}
	
	private static ArrayList<Interval> intersects2 (ArrayList<Interval> maxInterval, ArrayList<Interval> blue){
		ArrayList<Interval> temp=maxInterval;
		for (int i=1;i<maxInterval.size();i++) {
			long times= factorial(maxInterval.size())/(factorial(maxInterval.size()-i)*i);
			for (int j=0;j<times;j++) {
				if(intersects(maxInterval.get(j), blue).contains(intersects(maxInterval.get(((j+1)==maxInterval.size()?0:j+1)), blue))){
					temp.remove(i);
				}
			}
		}
		return temp;
	}
	
	private static ArrayList<Interval> intersects (Interval a, ArrayList<Interval> blue){
	ArrayList<Interval> temp= new ArrayList<>();
		for(Interval b: blue) {
			if ((a.start<b.start && a.finish>b.finish)|(a.start>=b.start && a.start<=b.finish)|(a.finish>=b.start && a.finish<=b.finish) ) {
				temp.add(b);
			}
		}
		return temp;
	}
	
	private static long factorial(long n) {return n <= 1 ? 1 : n*factorial(n-1);}
	
	private static int findMaxValues(ArrayList<Integer> nrs) {
		int max = nrs.get(0);
		int max_index = 0;
		for (int i = 0; i < nrs.size(); i++) {
			if (nrs.get(i) > max) {
				max=nrs.get(i);
				max_index = i;
			}
		}
		return max_index;
	}

	/**
	 * Goal: find any path of lowest cost from the top-left of the grid (grid[0][0])
	 * to the bottom right of the grid (grid[m-1][n-1]).  Output this sequence of directions
	 * 
	 * @param grid - the 2d grid containing the cost of each location in the grid.
	 * @return
	 */
	/* I chose the greedy algorithm-approach: Stepping a number of depth times through the tree with my superdeluxe 
	 * algorithm for stepping through a three with the binary representation of numbers to guide me.
	 */
	static boolean shortestPathFound=false;
	public static List<Direction> optimalGridPath(int[][] grid) {
		int current_x_index=0;
		int current_y_index=0;
		List<Direction> solution = new LinkedList();
		while(!(current_x_index==grid[0].length-1&current_y_index==grid.length-1)) {
			Direction step = stepGrid(grid,current_x_index, current_y_index);
			solution.add(step);
			if (step!=null&&step.equals(Direction.RIGHT)){
				current_x_index++;
				//System.out.print("Right, " );
			} else if (step!=null&&step.equals(Direction.DOWN)) {
				current_y_index++;
				//System.out.print("Down, " );
			} else {
				return null;
			}
		}
		return solution;
	}
	private static Direction stepGrid(int[][]grid, int x_index, int y_index) {
		Direction final_direction = null;
		int x_size=grid[0].length;
		int y_size=grid.length;
		int depth=3;
		int[] values= new int[power(2,depth)];
		for (int i=0;i<power(2,depth);i++) {
			values[i]=0;
		}
		int x_step=0;
		int y_step=0;
binary_t:	for (int i=0;i<power(2,depth);i++) {
			for (int step=0;step<depth;step++) {
				
				if (((i)/power(2,depth-step-1))%2==1) { 
					if (x_step+x_index<x_size-1) {
						x_step++;
					} else if (y_step+y_index<y_size-1) {
						y_step++;
					} else { //Reached the bottom-right position
						x_step=0;
						y_step=0;
						continue binary_t;
					}
					//System.out.print("Right, or Down" );
				} else if (((i)/power(2,depth-step-1))%2==0) {
					if (y_step+y_index<y_size-1) {
						y_step++;
					} else if (x_step+x_index<x_size-1) {
						x_step++;
					} else { //Reached the bottom-right position
						x_step=0;
						y_step=0;
						continue binary_t;
					}
					//System.out.print("Down, or Right");
					
				}
				values[i]=values[i]+grid[y_index+y_step][x_index+x_step];
			}
			x_step=0;
			y_step=0;
			//System.out.println();
		}
		int a_index=0;
		for (int a:values) {
			if (a==0)
				values[a_index]=Integer.MAX_VALUE;
			a_index++;
		}
		int min_index=findMinValues(values);
		if (min_index/power(2,depth-1)%2==1){
			return Direction.RIGHT;
		}	else if (min_index/power(2,depth-1)%2==0){
			return Direction.DOWN;
		} else return null;
	}
	private static int power(int base, int times) {
	int temp=1;
	for (int i=0;i<times;i++) {
		temp=temp*base;
	}
	return temp;
	}
	private static int findMinValues(int[] nrs) {
		int min = nrs[0];
		int min_index = 0;
		for (int i = 0; i < nrs.length; i++) {
			if (nrs[i] < min) {
				min=nrs[i];
				min_index = i;
			}
		}
		return min_index;
	}
	/**
	 * A simple Direction enum
	 * directions can be either DOWN or RIGHT
	 * You will output a list of these in the grid-path problem
	 */
	public static enum Direction {
		DOWN, RIGHT
	}

	/**
	 * A private Interval class to help with the interval question
	 */
	public static class Interval {
		
		int start;
		int finish;
		
		public Interval(int start, int finish) {
			this.start = start;
			this.finish = finish;
		}
		
		/**
		 * sorts a list of intervals by start time, you are free to use this on the first question
		 */
		public static void sortByStartTime(ArrayList<Interval> l) {
			Collections.sort(l, new Comparator<Interval>() {
				public int compare(Interval o1, Interval o2) {
					Interval i1 = (Interval) o1;
					Interval i2 = (Interval) o2;
					return i1.start - i2.start;
				}
			});
		}
		
		/**
		 * sorts a list of intervals by finish time, you are free to use this on the first question
		 */
		public static void sortByFinishTime(ArrayList<Interval> l) {
			Collections.sort(l, new Comparator<Interval>() {
				public int compare(Interval o1, Interval o2) {
					Interval i1 = (Interval) o1;
					Interval i2 = (Interval) o2;
					return i1.finish - i2.finish;
				}
			});
		}
	}
	/*
	public static void main(String[] args) {
		GreedyDynamicAlgorithms.Interval red1 = new GreedyDynamicAlgorithms.Interval(0, 4);
		GreedyDynamicAlgorithms.Interval red2 = new GreedyDynamicAlgorithms.Interval(2, 5);
		GreedyDynamicAlgorithms.Interval red3 = new GreedyDynamicAlgorithms.Interval(4, 8);
		GreedyDynamicAlgorithms.Interval red4 = new GreedyDynamicAlgorithms.Interval(9, 10);
		GreedyDynamicAlgorithms.Interval red5 = new GreedyDynamicAlgorithms.Interval(9, 11);
		GreedyDynamicAlgorithms.Interval red6 = new GreedyDynamicAlgorithms.Interval(10, 12);
		GreedyDynamicAlgorithms.Interval red7 = new GreedyDynamicAlgorithms.Interval(11, 12);
		GreedyDynamicAlgorithms.Interval blue1 = new GreedyDynamicAlgorithms.Interval(0, 2);
		GreedyDynamicAlgorithms.Interval blue2 = new GreedyDynamicAlgorithms.Interval(5, 5);
		GreedyDynamicAlgorithms.Interval blue3 = new GreedyDynamicAlgorithms.Interval(7, 10);
		GreedyDynamicAlgorithms.Interval blue4 = new GreedyDynamicAlgorithms.Interval(11, 13);
		ArrayList<GreedyDynamicAlgorithms.Interval> reds = new ArrayList<>();
		ArrayList<GreedyDynamicAlgorithms.Interval> blues = new ArrayList<>();
		reds.add(red1);reds.add(red2);reds.add(red3);reds.add(red4);reds.add(red5);reds.add(red6);
		reds.add(red7);
		blues.add(blue1);blues.add(blue2);blues.add(blue3);blues.add(blue4);
		int expectedOptimal = 2;
		int actualOptimal = GreedyDynamicAlgorithms.optimalIntervals(reds, blues);
		System.out.println("interval 6 red 4 blue"+ expectedOptimal + "," + actualOptimal);
	}
	*/
	/*
	public static void main(String[] args) {
		int[][] grid= {{5,1,1},{2,4,7},{2,4,5},{5,6,3}};
		List<Direction> output = new LinkedList<>();
		 output.add(Direction.DOWN);
		 output.add(Direction.DOWN);
		 output.add(Direction.RIGHT);
		 output.add(Direction.RIGHT);
		 output.add(Direction.DOWN);
		 List<Direction> list= optimalGridPath(grid);
		System.out.println("grid optimisation:"+list+",expected: "+output);
	}
	*/
}
