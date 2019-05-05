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
      int j;

      /* Base case: If 1 or zero elements,
         partition is already sorted */
      if (i >= k) {
         return;
      }

      /* Partition the array.
         Value j is the location of last
         element in low partition. */
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
   public int partition(int[] numArray, int firstItem, int lastItem) {

      // pick first element value as pivot
      int pivot = numArray[firstItem];
      int temp;
      boolean done = false;

      // initialize variables
      int lowIndex = firstItem;
      int highIndex = lastItem;

      while (!done) {

         // increment lowIndex while numArray[lowIndex] < pivot
         while (numArray[lowIndex] < pivot) {
            ++lowIndex;
         }

         // decrement highIndex while pivot < numArray[highIndex]
         while (pivot < numArray[highIndex]) {
            --highIndex;
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
            temp = numArray[lowIndex];
            numArray[lowIndex] = numArray[highIndex];
            numArray[highIndex] = temp;
            ++lowIndex;
            --highIndex;
         }
      }
      // returns the index of the last item in the low partition
      return highIndex;
   }

   public void printArray(int[] numArray) {
      for (int i=0; i<=numArray.length-1; i++) {
//         System.out.print(numArray[i] + " "); // uncomment for submission
         System.out.print(numArray[i] + " "); // remove for submission
      }
      System.out.println("\n");
   }
}
