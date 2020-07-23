public class Sorting_Backup {
	
	/**
	 * Implement the mergesort function, which should sort the array of
	 * integers in place
	 * 
	 * You will probably want to use helper functions here, as described in the lecture recordings
	 * (ex. merge(), a helper mergesort function)
	 * @param arr
	 */
	public static void mergeSort(CompareInt[] arr) {
		CompareInt[] arr2= new CompareInt[arr.length];
		//System.out.println(arr.length);
		mergeSort(arr, arr2, 0, arr.length-1);
	}
	
	public static void mergeSort(CompareInt[] arr1, CompareInt[] aux, int low, int high) {
		if (high-low <= 0) return;
		//System.out.println(aux.length);
		int mid = (high+low)/2;
		//System.out.println("De high-waarde is: "+high);
		System.out.println("De high-waarde is: "+high+"en de mid-waarde is: " + mid+" en de low-waard is: "+low);
		CompareInt[] arr2= new CompareInt[high-low-1];
		mergeSort(arr1, arr2, low, mid);
		//System.out.println(high);
		mergeSort(arr1,arr2, mid+1, high);
		CompareInt[] arr3= new CompareInt[mid];
		CompareInt[] arr4= new CompareInt[high-mid];
		System.arraycopy(arr1, 0, arr3, 0, mid);
		System.arraycopy(arr1, mid+1, arr4, 0, high-mid);
		for (int i = 0; i<arr3.length;i++) {
			System.out.println("De waarde van element "+i+ "van arr3 is: "+ arr3[i].val);
		}
		for (int i = 0; i<arr4.length;i++) {
			System.out.println("De waarde van element "+i+ "van arr4 is: "+ arr4[i].val);
		}
		aux = merge(arr3, arr4);
		//System.out.println(aux.length);
		//System.out.println(arr.length-1);
		System.arraycopy(aux, 0, arr1, low , high-low);
	}
	
	public static CompareInt[] merge(CompareInt[] arrA, CompareInt[] arrB) {
		CompareInt[] arrC = new CompareInt[arrA.length+arrB.length];
//		System.out.println("De lengte van array A is: "+arrA.length);
//		System.out.println("De lengte van array B is: "+arrB.length);
//		System.out.println("De lengte van array C is: "+arrC.length);
		int i= 0;
		int j= 0;
		int k= 0;
		while (i<arrA.length && j<arrB.length) {
			if (arrA[i].compareTo(arrB[j]) < 0) {
				 arrC[k]=arrA[i];
				 i++;
				 k++;
			} else {
				 arrC[k]=arrB[j];
				 j++;
				 k++;
			}
		}
		while (i < arrA.length) {
			arrC[k++]=arrA[i++];
		}
		while (j < arrB.length) {
			arrC[k++]=arrB[j++];
		}
		return arrC;
	}
	public static void mergeSort2(CompareInt[] arr1, int low, int high) {
		if (high-low <= 0) return;
		//System.out.println(aux.length);
		int mid = (high+low)/2;
		//System.out.println("De high-waarde is: "+high);
		System.out.println("De high-waarde is: "+high+"en de mid-waarde is: " + mid+" en de low-waard is: "+low);
		mergeSort2(arr1, low, mid);
		//System.out.println(high);
		mergeSort2(arr1, mid+1, high);
		CompareInt[] arr3= new CompareInt[mid];
		CompareInt[] arr4= new CompareInt[high-mid];
		//System.arraycopy(arr1, 0, arr3, 0, mid);
		//System.arraycopy(arr1, mid+1, arr4, 0, high-mid);
		for (int i = 0; i<arr3.length;i++) {
			arr3[i]=arr1[i];
		}
		for (int i = 0; i<arr4.length;i++) {
			arr4[i]=arr1[mid+1+i];	
		}
		merge2(arr1, arr3, arr4);
		//System.out.println(aux.length);
		//System.out.println(arr.length-1);
	}
	
	public static void merge2(CompareInt[] arrC, CompareInt[] arrA, CompareInt[] arrB) {
//		CompareInt[] arrC = new CompareInt[arrA.length+arrB.length];
//		System.out.println("De lengte van array A is: "+arrA.length);
//		System.out.println("De lengte van array B is: "+arrB.length);
//		System.out.println("De lengte van array C is: "+arrC.length);
		int i= 0;
		int j= 0;
		int k= 0;
		while (i<arrA.length && j<arrB.length) {
			if (arrA[i].compareTo(arrB[j]) < 0) {
				 arrC[k]=arrA[i];
				 i++;
				 k++;
			} else {
				 arrC[k]=arrB[j];
				 j++;
				 k++;
			}
		}
		while (i < arrA.length) {
			arrC[k++]=arrA[i++];
		}
		while (j < arrB.length) {
			arrC[k++]=arrB[j++];
		}
//		return arrC;
	}
	private static CompareInt[] convert(int[] arr) {
		CompareInt[] newArr = new CompareInt[arr.length];
		for (int i = 0; i < newArr.length; i++) {
			newArr[i] = new CompareInt(arr[i]);
		}
		return newArr;
	}
	
	/**
	 * Implement the quickSelect
	 * 
	 * Again, you will probably want to use helper functions here
	 * (ex. partition(), a helper quickselect function)
	 */
	public static CompareInt quickSelect(int k, CompareInt[] arr) {
		//TODO
		return null;
	}

public static void main(String[] args) {
	CompareInt[] arr = convert(new int[]{4, 1, 3,2,5,8,7,6});
	mergeSort(arr);
	for(int i=0;i<arr.length;i++) {
		System.out.println("De waarde "+i+ " van arr is: "+ arr[i].val);
	}
}
}
