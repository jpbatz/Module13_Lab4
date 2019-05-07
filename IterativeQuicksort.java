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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Stack;

// Java implementation of iterative quick sort 
// Ref: https://www.geeksforgeeks.org/java-program-for-iterative-quick-sort/
// by Rajat Mishra
public class IterativeQuicksort {
//   BufferedReader input;
//   BufferedWriter output;
   
   InsertionSort is = new InsertionSort();
   ProjectUtils utils = new ProjectUtils();
   
   // driver code to test above
//   public static void main(String args[]) {
//
//      int arrSize = 0;
//      int[] refNumbers; // original input values for restoring between sorts
//      int[] numbers; // working space to sort reference numbers
////      int[] unsorted = {34, 32, 43, 12, 11, 32, 22, 21, 32};
//
//      IterativeQuicksort iq = new IterativeQuicksort();
//
//      // opens input file handler
//      iq.input = iq.openInputFileHandler(iq.input, args[1]);
//
//      arrSize = Integer.valueOf(args[0]);
//      refNumbers = new int[arrSize];
//      numbers = new int[arrSize];
//
//      // loads the reference array from input file
//      // System.out.println("Input values to reference array:");
//      for (int i = 0; i < arrSize; i++) {
//         try {
//            refNumbers[i] = Integer.parseInt(iq.input.readLine());
//         } catch (IOException ioe) {
//            System.out.println(ioe);
//            System.exit(1);
//         }
//         // System.out.println(refNumbers[i]);
//      }
//
//      iq.copyArray(refNumbers, numbers);
//
//      iq.quicksort("qs", numbers, 0, numbers.length - 1);
////      iq.Quicksort("qs", unsorted, 0, unsorted.length - 1);
//      iq.printArr(numbers, numbers.length);
////      iq.printArr(unsorted, unsorted.length);
//
//      // closes input file handler(s)
//      iq.closeInputFileHandler(iq.input);
//   }

   // Ref: https://javarevisited.blogspot.com/2016/09/
   // iterative-quicksort-example-in-java-without-recursion.html
   // #ixzz5n6BmPVfj
   public void quicksort(String sortType, int arr[], int lowerIndex, int upperIndex) {

//      int pivotIndex = 0;
      int pivotIndex;
      int end;
      int start;
      Stack<Integer> stack = new Stack<Integer>();
//      stack.push(0);
      stack.push(lowerIndex);
//      stack.push(arr.length);
      stack.push(upperIndex);
      while (!stack.isEmpty()) {
         
         end = (int) stack.pop();
         start = (int) stack.pop();
         
         // partition sizes of one and two are already sorted
//         if ((end - start) <2) { // two items
         if ((end - start + 1) <= 2) { // two items
            if (arr[start] > arr[end]) {
               utils.swap(arr, start, end);
            }
            continue;
         }
         
         // three items
         if (sortType.equals("qs")){
            pivotIndex = partition(arr, start, end, "qs");
//            System.out.println("qs pivotIndex = " + pivotIndex);
         } else if (sortType.equals("qs_k50")) {
            if ((end - start + 1) == 50) {
               // System.out.println("*** PARTITION SIZE = 50 ***");
               is.insertionSort(arr, start, end);
            }
            pivotIndex = partition(arr, start, end, "qs_k50");
//            System.out.println("qs_k50 pivotIndex = " + pivotIndex);
         } else if (sortType.equals("qs_k100")){
            if ((end - start + 1) == 100) {
               // System.out.println("*** PARTITION SIZE = 100 ***");
               is.insertionSort(arr, start, end);
            }
            pivotIndex = partition(arr, start, end, "qs_k100");
//            System.out.println("qs_k100 pivotIndex = " + pivotIndex);
         } else { // if (sortType.equals("qs_mot")){
//            this.printArray(arr);
            pivotIndex = partition(arr, start, end, "qs_mot");
//            System.out.println("qs_mot pivotIndex = " + pivotIndex);
         }
         
         stack.push(pivotIndex + 1);
         stack.push(end);
         stack.push(start);
         stack.push(pivotIndex); 
         
      }

   }
   // Ref: zyBook Module 9: Chapter 7.2 Quicksort
   // modified for first item is pivot
  
   private int partition(int arr[], int lowIndex, int highIndex, String qsType) {

      int l;
      int h;
      int pivotValue;
      int temp;
      boolean done;

      done = false;
      l = lowIndex;
      h = highIndex;
      
      /* pick pivot value */
      if (qsType.equals("qs_mot")) {
         pivotValue = this.medianOfThree(arr, lowIndex, highIndex);
      } else { // qsType is "qs", "qs_k50", or "qs_k100"
         pivotValue = arr[lowIndex]; // pick first item as pivot value
      }

      while (!done) {
         /* Increment l while numbers[l] < pivot */
         while (arr[l] < pivotValue) {
            ++l;
         }
         /* Decrement h while pivot < numbers[h] */
         while (pivotValue < arr[h]) {
            --h;
         }
         /*
          * If there are zero or one items remaining, all numbers are
          * partitioned. Return h
          */
         if (l >= h) {
            done = true;
         } else {
            /*
             * Swap numbers[l] and numbers[h], update l and h
             */
            temp = arr[l];
            arr[l] = arr[h];
            arr[h] = temp;
            ++l;
            --h;
         }
      }
      return h;
         
      // Ref: https://javarevisited.blogspot.com/2016/09/
      // iterative-quicksort-example-in-java-without-recursion.html
      // #ixzz5n6F8nAuX
      // modified, first item is pivot
//      int idx = 0;
//      int l = lowIndex;
////      int h = highIndex - 2; // original
////      int h = highIndex - 1;
//      int h = highIndex;
//      int pivotValue;
//
//      if (qsType.equals("qs_mot")) {
//         pivotValue = this.medianOfThree(arr, lowIndex, highIndex);
//         pivotValue = this.medianOfThree(arr, l, h);
//      } else { // qsType is "qs", "qs_k50", or "qs_k100"
//         pivotValue = arr[l]; // pivot value is the first item
//      }
//      
//      swap(arr, lowIndex, highIndex - 1); // why??
////      swap(arr, lowIndex, highIndex);
//      while (l < h) {
//         if (arr[l] < pivotValue) {
//            l++;
//         } else if (arr[h] >= pivotValue) {
//            h--;
//         } else {
//            swap(arr, l, h);
//         }
//      }
//      idx = h;
//      if (arr[h] < pivotValue) {
//         idx++;
//      }
//      swap(arr, highIndex - 1, idx); // why??
////      System.out.println("idx = " + idx);
//      return idx;
   }


//   private void swap(int[] arr, int leftIndex, int rightIndex) {
//      int temp = arr[leftIndex];
//      arr[leftIndex] = arr[rightIndex];
//      arr[rightIndex] = temp;
//      return;
//   }

   // determines next pivot item
   private int medianOfThree(int[] arr, int startIndex, int endIndex) {
      
      int start;
      int end;
      int medianIndex;
      int medianValue;
      int middleIndex;
      int partitionSize;
      int[] threeNums = new int[3];
      
      start = startIndex;
      if (endIndex == arr.length) {
         end = endIndex - 1;
      } else {
         end = endIndex;
      }
      
      partitionSize = end - start;
      
//      if (partitionSize > 2) { // 3 or more items in partition
         
         middleIndex = (int) ((start + end) /2);
//         System.out.println("indexes (begin): " + start + " " + middleIndex + " " + end);
         threeNums[0] = arr[start];
         threeNums[1] = arr[middleIndex];
         threeNums[2] = arr[end];

//         System.out.println("end = " + end);
         
         Arrays.sort(threeNums);
//         for (int i = 0; i < threeNums.length; i++) {
//            System.out.println("threeNums: " + threeNums[i]);
//         }
         // select the index of the median value
         medianValue = threeNums[1];
         medianIndex = middleIndex;
//         medianIndex = Arrays.asList(threeNums).indexOf(medianValue);
//         System.out.println("medianIndex = " + medianIndex);
//      } 
//      else {
//         // one or two items in the partition
//         // medianIndex is the left or sole item
//         medianValue = arr[start];
//         medianIndex = start;
//      }
//      System.out.println("indexes (end): " + start + " " + medianIndex + " " + end);
//      System.out.println("values (end): " + threeNums[0] + " " + threeNums[1] + " " + threeNums[2]);
//      System.out.println("medianValue = " + medianValue);
      return medianValue;
   }
   
   
   
   // *** REMOVE AFTER TESTING ***
   
//   // prints contents of arr
//   public void printArray(int[] arr) {
//      for (int item : arr) {
//         System.out.print(item + " ");
//      }
//      System.out.println();
//   }

   private void copyArray(int[] srcArr, int[] destArr) {
      for (int i = 0; i < srcArr.length; i++) {
         destArr[i] = srcArr[i];
      }
   }

   // opens input file handler
   // Ref: Project0.java
   private BufferedReader openInputFileHandler(BufferedReader br, String arg) {
      try {
         br = new BufferedReader(new FileReader(arg));
      } catch (IOException ioe) {
         System.err.println(ioe.toString());
         System.exit(1);
      }
      return br;
   }

   // closes input file handler
   // Ref: Project0.java
   private void closeInputFileHandler(BufferedReader br) {
      try {
         br.close();
      } catch (IOException ioe) {
         System.err.println(ioe.toString());
         return;
      }
   }

}
