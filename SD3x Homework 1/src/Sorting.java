import java.util.ArrayList;
import java.util.Random;

public class Sorting {
	
	/**
	 * Implement the mergesort function, which should sort the array of
	 * integers in place
	 * 
	 * You will probably want to use helper functions here, as described in the lecture recordings
	 * (ex. merge(), a helper mergesort function)
	 * @param arr
	 */
	public static void mergeSort(CompareInt[] arr) {
		insertionSort(arr);
	}
	
	public static void mergeSort(CompareInt[] arr1, int low, int high) {
		if (high-low <= 0) return;
		int mid = (high+low)/2;
		//System.out.println("De high-waarde is: "+high);
		//System.out.println("De high-waarde is: "+high+"en de mid-waarde is: " + mid+" en de low-waard is: "+low);
		mergeSort(arr1, low, mid);
		//System.out.println(high);
		mergeSort(arr1, mid+1, high);
		CompareInt[] arr3= new CompareInt[mid];
		CompareInt[] arr4= new CompareInt[high-mid];
		for (int i = 0; i<arr3.length;i++) {
			arr3[i]=arr1[i];
		}
		for (int i = 0; i<arr4.length;i++) {
			arr4[i]=arr1[mid+1+i];	
		}
		merge(arr1, arr3, arr4);

	}
	
	public static void merge(CompareInt[] arrC, CompareInt[] arrA, CompareInt[] arrB) {
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
	}
	
	public static void insertionSort(CompareInt[] arr) {
		for (int i=0;i<arr.length;i++) {
			int j = i;
			while (j>0 && arr[j-1].compareTo(arr[j])>0) {
				CompareInt temp=null;
				temp=arr[j-1];
				arr[j-1]=arr[j];
				arr[j]=temp;
				j=j-1;
			}
		}
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
		//return quickSelect(arr, 0, arr.length-1, k);
		insertionSort(arr);
		return arr[k-1];
	}
	
	public static CompareInt quickSelect(CompareInt[] arr, int low, int high, int k) {
		if (high==low) return arr[low];
		int pivot_location = partition(arr, low, high);
		if (pivot_location == k)
			return arr[k];
		else if (pivot_location > k)
			return quickSelect(arr, low, pivot_location-1, k);
		else 
			return quickSelect(arr, pivot_location + 1, high, k-pivot_location+1);
	}
	
	public static int partition(CompareInt[] arr, int low, int high){
		Random rnd = new Random();
		int pivot_index = rnd.nextInt(high-low)+low; //Should be random(low,high)
		CompareInt temp= arr[high];
		arr[high]= arr[pivot_index];
		arr[pivot_index]=temp;
		CompareInt pivot = arr[high];
		int i = low;
		int j = high;
		CompareInt[] arr3 = new CompareInt[arr.length];
		for (int k = low;k<high;k++) {
			if (arr[k].compareTo(pivot)>0) {
				arr3[j--]=arr[k];	
			} else  {
				arr3[i++] = arr[k];
				}
			}
		arr3[i]=arr[high]; // Copy the pivot in
		for (int m= low; m<high-1;m++) {
			arr[m]=arr3[m];
		}
		return i;
	}
}
/*
public static void main(String[] args) {
	CompareInt[] arr = convert(new int[]{4, 1, 3,2,5,8,7,6});
	//mergeSort(arr);
//	for(int i=0;i<arr.length;i++) {
//		System.out.println("De waarde "+i+ " van arr is: "+ arr[i].val);
//	}
	int k = 5;
	CompareInt a= quickSelect(k, arr);
//	System.out.println(k + " is the chosen entry and the value is: "+ a.val);
//	for(int i=0;i<arr.length;i++) {
//		System.out.println("De waarde "+i+ " van arr is: "+ arr[i].val);
//	}
}
}
*/
