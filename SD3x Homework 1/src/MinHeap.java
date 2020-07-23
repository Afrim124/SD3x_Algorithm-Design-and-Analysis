

/**
 * A Heap implementation class
 * 
 * @param heap the array that holds the heap data
 * @param size the number of elements currently stored in the heap
 */
public class MinHeap {
	
	CompareInt[] heap;
	int size;

	/**
	 * Constructs a new heap with maximum capacity n
	 * Remember to index your heap at 1 instead of 0!
	 * @param n the maximum number of elements allowed in the heap
	 */
	public MinHeap(int n) {
		heap = new CompareInt[n+1];
		size = 0;
	}
	
	/**
	 * Adds an element to the heap
	 * 
	 * @param val the value to be added to the heap
	 */
	public void add(CompareInt val) {
		if (size>=heap.length-1) {
			throw new IllegalArgumentException();
		}
		int N=this.size;
		this.heap[N+1]= val;
		swim(this, N+1);
		this.size++;
	}
	
	public void swim(MinHeap heap2, int k){
		while (k > 1 && heap2.heap[k/2].compareTo(heap2.heap[k])>0) {
			CompareInt temp = heap2.heap[k];
			heap2.heap[k]=heap2.heap[k/2];
			heap2.heap[k/2]=temp;
			k=k/2;
		}
	}
	/**
	 * Extracts the smallest element from the heap
	 */
	public CompareInt extractMin() {
		return extractMin(this,1);
	}
	
	public CompareInt extractMin(MinHeap heap2, int k) {
		int N=heap2.size;
		CompareInt min = heap2.heap[1];
		heap2.heap[1] = heap2.heap[N];
		sink(heap2,1);
		heap2.size--;
		return min;
		
	}
	
	public void sink(MinHeap heap2, int k){
		int N=heap2.size;
		while(2*k+1<=N) {
			int smallest = 2*k;
			if (heap2.heap[2*k].compareTo(heap2.heap[2*k+1])>0)
				smallest = 2*k+1;
			if (heap2.heap[k].val<heap2.heap[smallest].val)
				break;
			CompareInt temp = heap2.heap[k];
			heap2.heap[k]=heap2.heap[smallest];
			heap2.heap[smallest]=temp;
			k = smallest;
		}
	}
	
	public static CompareInt[] convert(int[] arr) {
		CompareInt[] newArr = new CompareInt[arr.length];
		for (int i = 0; i < newArr.length; i++) {
			newArr[i] = new CompareInt(arr[i]);
		}
		return newArr;
	}
	/*
	public static void main(String[] args) {
		CompareInt[] arr = convert(new int[]{4, 1, 3,2,5,8,7,6});
		MinHeap h = new MinHeap(arr.length);
		//add all the elements to a min-heap
		for (int i = 0; i < arr.length; i++) {
			h.add(arr[i]);
		}
		//remove the first k-1 elements (i.e. the k-1 smallest element)
		int k=7;
		for (int i = 0; i < k-1; i++) {
			h.extractMin();
		}
		System.out.println(k+ "de element in de minheap is: "+ h.extractMin().val);
	}
	*/
}
