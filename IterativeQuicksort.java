/**
 * Joanne Hayashi
 * EN.605.202.84.SP19
 * 
 * Module 13: Lab 4 - Sort Comparison
 * Class: IterativeQuickSort.java
 * Iterative Quicksort 1:
 * Pivot = [0]
 * Stop at partition size 1 and 2
 */
package Module13_Lab4;

import java.util.Arrays;
import java.util.Stack;

// Java implementation of iterative quick sort 
// Ref: https://www.geeksforgeeks.org/java-program-for-iterative-quick-sort/
// by Rajat Mishra
public class IterativeQuicksort {

   InsertionSort is    = new InsertionSort();
   ProjectUtils  utils = new ProjectUtils();

   // Ref: https://javarevisited.blogspot.com/2016/09/
   // iterative-quicksort-example-in-java-without-recursion.html
   // #ixzz5n6BmPVfj
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

         // three items
         if (sortType.equals("qs")) {
            pivotIndex = partition(arr, start, end, "qs");
            // System.out.println("qs pivotIndex = " + pivotIndex);
         } else if (sortType.equals("qs_k50")) {
            if ((end - start + 1) == 50) {
               // System.out.println("*** PARTITION SIZE = 50 ***");
               is.insertionSort(arr, start, end);
            }
            pivotIndex = partition(arr, start, end, "qs_k50");
            // System.out.println("qs_k50 pivotIndex = " + pivotIndex);
         } else if (sortType.equals("qs_k100")) {
            if ((end - start + 1) == 100) {
               // System.out.println("*** PARTITION SIZE = 100 ***");
               is.insertionSort(arr, start, end);
            }
            pivotIndex = partition(arr, start, end, "qs_k100");
            // System.out.println("qs_k100 pivotIndex = " + pivotIndex);
         } else { // if (sortType.equals("qs_mot")){
            // this.printArray(arr);
            pivotIndex = partition(arr, start, end, "qs_mot");
            // System.out.println("qs_mot pivotIndex = " + pivotIndex);
         }

         stack.push(pivotIndex + 1);
         stack.push(end);
         stack.push(start);
         stack.push(pivotIndex);
      }
   }

   // Ref: zyBook Module 9: Chapter 7.2 Quicksort
   // modified for first item is pivot
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
      if (qsType.equals("qs_mot")) {
         pivotValue = this.medianOfThree(arr, lowIndex, highIndex);
      } else { // qsType is "qs", "qs_k50", or "qs_k100"
         pivotValue = arr[lowIndex]; // pick first item as pivot value
      }

      while (!done) {
         // Increment l while numbers[l] < pivot
         while (arr[l] < pivotValue) {
            ++l;
         }
         // Decrement h while pivot < numbers[h]
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

   // determines next pivot item via median value of three method
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
      // System.out.println("indexes (begin): " + start + " " + middleIndex +
      // " " + end);
      threeNums[0] = arr[start];
      threeNums[1] = arr[middleIndex];
      threeNums[2] = arr[end];

      Arrays.sort(threeNums);
      medianValue = threeNums[1];
      return medianValue;
   }

}
