/**
 * Joanne Hayashi
 * EN.605.202.84.SP19
 * 
 * Module 13: Lab 4 - Sort Comparison
 * Class: IterativeQuickSort.java
 * Iterative Quicksorts:
 * - (1) General ("qs") with first item as the pivot value, stops at size < 2
 * - (2) Insertion Sort at partition size k=50 ("qs_k50")
 * - (3) Insertion SOrt at partition size k=100 ("qs_100")
 * - (4) Selected pivot is median of three ("qs_mot")
 */
package Module13_Lab4;

import java.util.Arrays;
import java.util.Stack;

public class IterativeQuicksort {

   InsertionSort is    = new InsertionSort();
   ProjectUtils  utils = new ProjectUtils();

   /**
    * method: quicksort() - iteratively runs 4 quicksort types using stacks
    * @param sortType - "qs", "qs_k50", "qs_k100", or "qs_mot"
    * @param arr - items to be sorted
    * @param lowerIndex - index of the first item in the array
    * @param upperIndex - index of the last item in the array
    * @return none
    * @ref https://javarevisited.blogspot.com/2016/09/
    * iterative-quicksort-example-in-java-without-recursion.html
    * #ixzz5n6BmPVfj
    */
   public void quicksort(String sortType, int arr[], int lowerIndex,
                         int upperIndex) {
      int pivotIndex;
      int end;
      int start;
      int partitionSize;

      Stack<Integer> stack = new Stack<Integer>();
      stack.push(lowerIndex);
      stack.push(upperIndex);
      while (!stack.isEmpty()) {

         end = (int) stack.pop();
         start = (int) stack.pop();
         partitionSize = end - start + 1;

         // partition sizes of one and two are already sorted
         if (partitionSize <= 2) { // two items
            if (arr[start] > arr[end]) {
               utils.swap(arr, start, end);
            }
            continue;
         }

         // determines pivot index for the 4 quicksort types
         if (sortType.equals("qs")) {
            pivotIndex = partition(arr, start, end, "qs");
         } else if (sortType.equals("qs_k50")) {
            if ((end - start + 1) == 50) {
               is.insertionSort(arr, start, end);
            }
            pivotIndex = partition(arr, start, end, "qs_k50");
         } else if (sortType.equals("qs_k100")) {
            if ((end - start + 1) == 100) {
               is.insertionSort(arr, start, end);
            }
            pivotIndex = partition(arr, start, end, "qs_k100");
         } else {
            pivotIndex = partition(arr, start, end, "qs_mot");
         }

         stack.push(pivotIndex + 1);
         stack.push(end);
         stack.push(start);
         stack.push(pivotIndex);
      }
   }

   /**
    * method: partition() - divides a partition into two new partitions
    * @param arr - array of items to be sorted
    * @param lowIndex - index of first item in partition
    * @param highIndex - index of last item in partition
    * @param qsType - sort type "qs", "qs_k50", "qs_k100", "qs_mot"
    * @return index of first item in the new upper partition
    * @ref zyBook Module 9: Chapter 7.2 Quicksort
    */
   private int partition(int arr[], int lowIndex, int highIndex,
                         String qsType) {
      int l;
      int h;
      int pivotValue;
      int temp;
      boolean done;

      done = false;
      l = lowIndex;
      h = highIndex;

      // pick pivot value
      if (qsType.equals("qs_mot")) { // pick median value of three
         pivotValue = this.medianOfThree(arr, lowIndex, highIndex);
      } else { // qsType is "qs", "qs_k50", or "qs_k100"
         pivotValue = arr[lowIndex]; // pick first item
      }

      while (!done) {
         // increment l while numbers[l] < pivot
         while (arr[l] < pivotValue) {
            ++l;
         }
         // decrement h while pivot < numbers[h]
         while (pivotValue < arr[h]) {
            --h;
         }
         // if there are zero or one items remaining, all numbers are
         // partitioned - return h
         if (l >= h) {
            done = true;
         } else {
            // swap numbers[l] and numbers[h], update l and h
            temp = arr[l];
            arr[l] = arr[h];
            arr[h] = temp;
            ++l;
            --h;
         }
      }
      return h;
   }

   /**
    * method: medianOfThree() - generates a median value of three items
    * @param arr - input array of items to sort
    * @param startIndex - first item's index in partition to sort
    * @param endIndex - last item's index in partition to sort
    * @return median pivot value
    */
   private int medianOfThree(int[] arr, int startIndex, int endIndex) {

      int start;
      int end;
      int medianValue;
      int middleIndex;
      int[] threeNums = new int[3];

      start = startIndex;
      if (endIndex == arr.length) {
         end = endIndex - 1;
      } else {
         end = endIndex;
      }

      middleIndex = (int) ((start + end) / 2);
      threeNums[0] = arr[start];
      threeNums[1] = arr[middleIndex];
      threeNums[2] = arr[end];

      Arrays.sort(threeNums);
      medianValue = threeNums[1];
      return medianValue;
   }

}
