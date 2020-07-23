
import java.util.*;
import java.io.*;

/**
 * Arbitrage class
 *
 */
public class Arbitrage {
	
	/**
	 * 
	 * This function reads in a file containing exchange rate information
	 * (we've done this for you),
	 * and should create a weighted, directed graph
	 * 
	 * The output should be a list of currencies (i.e. vertices) that you can follow
	 * to create an arbitrage opportunity
	 * 
	 * e.x. if trading from currency 1 to 2 to 3 back to 1 yields an arbitrage
	 * opportunity, you should output a list containing {1, 2, 3, 1}
	 * 
	 * If no arbitrage opportunity is present, you should output an empty list
	 */
	@SuppressWarnings("unchecked")
	public static List<Integer> arbitrageOpportunity(String filename) throws IOException {
		
		//parses the input file into a list of exchange rates
		//see the comments above readExchangeRates for details on its output
		double[][] exchangeRates = readExchangeRates(filename);
		
		//TODO: model the exchange rates as a graph and output the value of
		//an arbitrage opportunity (see the writeup)
		int n = exchangeRates.length;
		WeightedDigraph graph = new WeightedDigraph(n);
		for (int i=0;i<n;i++) {
			for (int j=0;j<n;j++) {
				graph.addEdge(i,j,-1*Math.log10(exchangeRates[i][j]));
			}
		}
//		System.out.println(graph);
		//Multiply the graph n* to get negative weight cycles.
		WeightedDigraph<Double> resultGraph=graph;
		for (int k=0;k<n-1;k++) {
			resultGraph=multiply(resultGraph,graph);
		}
		
		List<Integer> output = new ArrayList<Integer>();
		for (Integer i=0;i<n;i++) {
			if ((double)resultGraph.weight(i,i)<0.0) {
				for (int j=0;j<n;j++) {
					output.add(i+j>n?j:i+j);
				}
				output.add(i);
				break;
			}

		}
		return output;
	}
	public static WeightedDigraph multiply(WeightedDigraph graph, WeightedDigraph graph2) {
		int n = graph.size();
		WeightedDigraph<Double> resultGraph= new WeightedDigraph<>(n);
		double sum[]= new double[n];
		for (int i=0; i<n; i++) {
			for (int k=0;k<n;k++) {
				for (int j=0; j<n; j++) {
						double graphWeight=(double)graph.weight(i,j);
						double graph2Weight=(double)graph2.weight(j,k);
						sum[j]=  graphWeight+graph2Weight; 
					}
					resultGraph.addEdge(i, k, getMinValue(sum));
				}
				
			}
		return resultGraph;
	}

	public static double getMinValue(double[] array) {
	    double minValue = array[0];
	    for (int i = 1; i < array.length; i++) {
	        if (array[i] < minValue) {
	            minValue = array[i];
	        }
	    }
	    return minValue;
	}

	
	/**
	 * You don't have to modify this function
	 * 
	 * Parses a file containing exchange rates between countries into an NxN 2d array
	 * 
	 * In general, if we have two countries i and j,
	 * arr[i][j] gives the exchange rate from country i to j.
	 * 
	 * I.e. if arr[i][j] = 4.00, then 1 quantity of currency i can be exchanged
	 * for 4.00 quantities of currency j
	 */
	public static double[][] readExchangeRates(String filename) throws IOException {
		//System.out.println("starting to read exchange rates");
		BufferedReader br = new BufferedReader(new FileReader(filename));
		
		//first line contains the number of countries
		int n = Integer.parseInt(br.readLine());
		double[][] exchangeRates = new double[n][n];
		
		//parse the file as a 2d array
		//in general, element j in row i has the exchange rate from country i to country j
		for (int i = 0; i < n; i++) {
			String[] line = br.readLine().split(" ");
			for (int j = 0; j < n; j++) {
				double rate = Double.parseDouble(line[j]);
				exchangeRates[i][j] = rate;
			}
		}
		br.close();
		return exchangeRates;
		
	}
//	
//	public static void main(String[] args) {
//		List<Integer> steps = new LinkedList<Integer>();
//		try {
//			steps = arbitrageOpportunity("arbitrageTriangle.txt");
//		} catch (IOException e) {
//			System.out.println("File couldn't be found.");
//		}
//		System.out.println(steps);
//	}
}
