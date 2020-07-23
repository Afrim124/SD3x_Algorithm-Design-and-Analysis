import static org.junit.Assert.*;

import java.io.*;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class MyTests {
	
	private int choose(int n, int k) {
		if (k > n / 2) {
			k = n - k;
		}

		double denominator = 1.0D;
		double numerator = 1.0D;

		for (int i = 1; i <= k; ++i) {
			denominator *= (double) i;
			numerator *= (double) (n + 1 - i);
		}

		return (int) (numerator / denominator);
	}
	private Graph setup(String filename) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(filename));
		String line = br.readLine();
		int n = Integer.parseInt(line);
		line = br.readLine();

		Graph g;
		for (g = new Graph(n); line != null; line = br.readLine()) {
			String[] pair = line.split(",");
			int u = Integer.parseInt(pair[0]);
			int v = Integer.parseInt(pair[1]);
			g.addEdge(u, v);
		}

		br.close();
		return g;
	}
	@Test
	public void testMaxPathsLevel4Graph() throws IOException {
		Graph g = this.setup("files/maxPaths5LevelGraph.txt");

		try {
			Assert.assertTrue(
					"numShortestPaths() is incorrect on test with large graph 2, where two nodes have many distinct shortest paths",
					g.numShortestPaths(0, 51) == 100000);
			Assert.assertTrue(
					"numShortestPaths() is incorrect on test with large graph 2, where two nodes have many distinct shortest paths",
					g.numShortestPaths(51, 0) == 100000);

			for (int j = 1; j <= 10; ++j) {
				for (int k = 41; k <= 50; ++k) {
					Assert.assertTrue(
							"numShortestPaths() is incorrect on test with large graph 2, between two nodes with many distinct shortest paths",
							g.numShortestPaths(j, k) == 1000);
				}
			}
		} catch (NullPointerException var4) {
			Assert.fail("numShortestPaths() uses/returns a null value, make sure to implement all required functions");
		} catch (Exception var5) {
			Assert.fail("numShortestPaths() test throws exception " + var5 + " on large graph 2");
		}

	}
	@Test(timeout = 50L)
	public void testGridGraph() throws IOException {
		Graph g = this.setup("files/largeGridGraph.txt");

		try {
			for (int i = 0; i < 10; ++i) {
				for (int j = 0; j < 10; ++j) {
					int v = 10 * i + j;
					int n = i + j;
					Assert.assertTrue(
							"numShortestPaths() is incorrect on graph with exponential number of distinct shortest paths",
							g.numShortestPaths(0, v) == this.choose(n, i));
					Assert.assertTrue(
							"numShortestPaths() is incorrect on graph with exponential number of distinct shortest paths",
							g.numShortestPaths(v, 0) == this.choose(n, i));
				}
			}
		} catch (NullPointerException var7) {
			Assert.fail("numShortestPaths() uses/returns a null value, make sure to implement all required functions");
		} catch (Exception var8) {
			Assert.fail("numShortestPaths() throws exception " + var8 + " on large graph 3");
		}

	}
}
