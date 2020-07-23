
import static org.junit.Assert.assertEquals;

import java.util.*;

public class Huffman {
	
	private String input;
	private Node huffmanTree; //the huffman tree
	private Map<Character, String> mapping; //maps characters to binary strings
	
	
	/**
	 * The Huffman constructor
	 * 
	 */
	public Huffman(String input) {
		
		this.input = input;
		Map<Character, String> mapping = new HashMap<>();
		
		//first, we create a map from the letters in our string to their frequencies
		Map<Character, Integer> freqMap = getFreqs(input);
		
		//we'll be using a priority queue to store each node with its frequency,
		//as we need to continually find and merge the nodes with smallest frequency
		PriorityQueue<Node> huffman = new PriorityQueue<>();
		
		/*
		 * TODO:
		 * 1) add all nodes to the priority queue
		 * 2) continually merge the two lowest-frequency nodes until only one tree remains in the queue
		 * 3) Use this tree to create a mapping from characters (the leaves)
		 *    to their binary strings (the path along the tree to that leaf)
		 *    
		 * Remember to store the final tree as a global variable, as you will need it
		 * to decode your encrypted string
		 */
		Set<Map.Entry<Character, Integer>> entrySet = freqMap.entrySet();
		Iterator<Map.Entry<Character, Integer>> iterator = entrySet.iterator();
		while(iterator.hasNext()) {
			Map.Entry<Character, Integer> entry = iterator.next();
			Node tempNode = new Node(entry.getKey(), entry.getValue(), null, null);
			huffman.add(tempNode);
		}
		while(huffman.size()>2) {
			Node tempNode1 = huffman.poll();
			Node tempNode2 = huffman.poll();
			Node zNode = mergeNodes(tempNode1, tempNode2);
			huffman.add(zNode);
		}
		if (huffman.size()==2) {
			Node left = huffman.poll();
			Node right = huffman.poll();
			huffmanTree = new Node('~',left.freq+right.freq, left, right );
		}
		mapping= createMapping(huffmanTree, ""); //Arbitrary starting direction
		this.mapping=mapping;
	}
	
	
	private Map<Character, String> createMapping (Node root, String dir) {
		Map<Character, String> mapping = new HashMap<>();
		if (root.left==null & root.right==null) { //is a leaf
			mapping.put(root.letter, dir);
		} else {
			if (root.left!=null) {
				mapping.putAll(createMapping(root.left, dir+ "0" ));
			}
			if (root.right!=null) {
				mapping.putAll(createMapping(root.right, dir+ "1"));
			}
		}
		return mapping;
	}
		
	public Node mergeNodes(Node A, Node B) {
		Node zNode=new Node('~', A.freq+B.freq, A, B); // Chose the character '~' as the identifier of zNodes.
		return zNode;
	}
	/**
	 * Use the global mapping to convert your input string into a binary string
	 */
	public String encode() {
		String result = "";
		for(Character a: input.toCharArray()) {
			result+= mapping.get(a);
		}
		return result;
	}
	
	/**
	 * Use the huffmanTree to decrypt the encoding back into the original input
	 * 
	 * You should convert each prefix-free group of binary numbers in the
	 * encoding to a character
	 * 
	 * @param encoding - the encoded string that needs to be decrypted
	 * @return the original string (should be the same as "input")
	 */
	public String decode(String encoding) {
		String result = "";
		int i = 0;
		while (i < encoding.length()) {
			Node n = huffmanTree;
			while (!n.isLeaf()) {
				if (encoding.charAt(i) == '0') {
					n = n.left;
				} else {
					n = n.right;
				}
				i++;
			}
			// n is now a leaf 
			result += n.letter;
		}
		return result;
	}
	
	/**
	 * This function tells us how well the compression algorithm worked
	 * 
	 * note that a char is represented internal using 8 bits
	 * 
	 * ex. if the string "aabc" maps to "0 0 10 11", we would have
	 * a compression ratio of (6) / (8 * 4) = 0.1875
	 */
	public static double compressionRatio(String input) {
		Huffman h = new Huffman(input);
		String encoding = h.encode();
		int encodingLength = encoding.length();
		int originalLength = 8 * input.length();
		return encodingLength / (double) originalLength;
	}
	
	/**
	 * We've given you this function, which helps you create
	 * a frequency map from the input string
	 */
	private Map<Character, Integer> getFreqs(String input) {
		Map<Character, Integer> freqMap = new HashMap<>();
		for (char c : input.toCharArray()) {
			if (freqMap.containsKey(c)) {
				freqMap.put(c, freqMap.get(c) + 1);
			} else {
				freqMap.put(c, 1);
			}
		}
		return freqMap;
	}


	/**
	 * An inner Node class to build your huffman tree
	 * 
	 * Each node has:
	 * a frequency - the sum of the frequencies of all the node's leaves
	 * a letter - the character that this node represents (only for leaves)
	 * left and right children
	 */
	private class Node implements Comparable<Node> {
		private Character letter; //the letter of this node (only for leaves)
		private int freq; //frequency of this node
		private Node left; //add a 0 to you string
		private Node right; //add a 1 to your string
		
		public Node(Character letter, int freq, Node left, Node right) {
			this.letter = letter;
			this.freq = freq;
			this.left = left;
			this.right = right;
		}
		
		public boolean isLeaf() {
			return left == null && right == null;
		}
		
		@Override
		public int compareTo(Node o) {
			return this.freq - o.freq;
		}
	}
	/*
	public static void main(String[] args) {
		String input = "hottentottententententoonstelling";
		Huffman h = new Huffman(input);
		//Set<Map.Entry<Character, String>> entrySet = h.mapping.entrySet();
		//Iterator<Map.Entry<Character, String>> iterator = entrySet.iterator();
		/*while(iterator.hasNext()) {
			Map.Entry<Character, String> entry = iterator.next();
			System.out.println(entry);
		}
		String encoding = h.encode();
		//assertEquals(input, h.decode(encoding));
		//assertEquals("huffman abc compression", Huffman.compressionRatio(input), 0.20833, 0.01);
		
	}
	*/
	
}



