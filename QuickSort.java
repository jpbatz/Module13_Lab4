/**
 * Joanne Hayashi
 * EN.605.202.84.SP19
 * 
 * Module 13: Lab 4 - Sort Comparison
 * Class: QuickSort.java
 * Iterative Quicksort 1:
 * Pivot = [1]
 * Stop at partition size 1 and 2
 */
package Module13_Lab4;

public class Quicksort {

   // tester
//   public static void main(String[] args) {
//
//      Quicksort qs = new Quicksort();
//
//      int[] numbers = {15, 3, 9, 8, 5, 2, 7, 1, 6};
//
//      qs.printArray(numbers);
//      System.out.println();
//
//      qs.quicksort(numbers, 0, numbers.length-1);
//
//      qs.printArray(numbers);
//
//   }

   // Ref: Based on zyBook Module 7.2.1: Quicksort algorithm
   // recursive
   public void quicksort(int[] numArray, int i, int k) {
      int j = i;

      // Base case: If 1 or 2 elements,
      // partition is already sorted
      if ((k - i) <= 1) {
         if (numArray[i] > numArray[k]) {
            this.swap(numArray, i, k);
         }
         return;
      }

      // partition the array:
      // value j is the location of last item in low partition
      j = partition(numArray, i, k);

      System.out.println("i = " + i + " j = " + j + " j+1 = " + (j+1) + " k = " + k);
      /* Recursively sort low and high     
          partitions */
      quicksort(numArray, i, j);
      quicksort(numArray, j + 1, k);

    }
   
   
   // Ref: Based on zyBook Module  7.2.1: Quicksort partition
   // firstItem and lastItem refer to the index of the first item and 
   // the index of the last item in the original array or a sub-array,
   // respectively
   public int partition(int[] numArray, int firstIndex, int lastIndex) {

      // pick first element value as pivot
      int pivot = numArray[firstIndex];
      boolean done = false;

      // initialize variables
      int lowIndex = firstIndex;
      int highIndex = lastIndex;

      while (!done) {

         // decrement highIndex while pivot < numArray[highIndex]
         while (numArray[highIndex] > pivot) {
            --highIndex;
         }
         
         // increment lowIndex while numArray[lowIndex] < pivot
         while (numArray[lowIndex] < pivot) {
            ++lowIndex;
         }

         // if there are zero or one items remaining, all numbers are
         // partitioned. Return highIndex
         if (lowIndex >= highIndex) {
            done = true;
//         } else { // if partition size == 50 or 100...
//            // stop and do insertion sort
         } else {
            // swap numArray[lowIndex] and numArray[highIndex], 
            // update lowIndex and highIndex
            this.swap(numArray, lowIndex, highIndex);
            ++lowIndex;
            --highIndex;
         }
      }
      // returns the index of the last item in the low partition
      return highIndex;
   }

   public void printArray(int[] arr) {
      for (int i=0; i<=arr.length-1; i++) {
//         System.out.print(arr[i] + " "); // uncomment for submission
         System.out.print(arr[i] + " "); // remove for submission
      }
      System.out.println("\n");
   }
   
   private void swap(int[] arr, int leftIndex, int rightIndex) {
      int temp = arr[leftIndex];
      arr[leftIndex] = arr[rightIndex];
      arr[rightIndex] = temp;
      return;
   }
}
