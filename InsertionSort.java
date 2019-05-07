/**
 * Joanne Hayashi
 * EN.605.202.84.SP19
 * 
 * Module 13: Lab 4 - Sort Comparison
 * Class: InsertionSort.java
 * Iterative Insertion Sort:
 * Applies insertion sort to input data.
 */
package Module13_Lab4;

public class InsertionSort {

   // for testing - remove for submission
   // public static void main(String[] args) {
   // InsertionSort is = new InsertionSort();
   // int numbers[] = {16, 19, 11, 15, 10, 12, 14};
   // is.insertionSort(numbers, 0, numbers.length);
   // is.printArray(numbers);
   // }

   ProjectUtils utils = new ProjectUtils();
   
   // Ref: https://www.codesdope.com/blog/article/
   // sorting-an-array-using-insertion-sort-in-java/
   public void insertionSort(int[] arr, int startIndex, int endIndex) {

      // first item at index 0 is already sorted for itself
      for (int currIndex = 1; currIndex < arr.length; currIndex++) {

         // comparison index starts at current index
         int compIndex = currIndex;

         // move current item to its sorted position:
         // compare each item before current index
         // and swap if current item value is less
         while (compIndex > 0) {
            // swap with previous item if out of order
            if (arr[compIndex - 1] > arr[compIndex]) {
               utils.swap(arr, compIndex, (compIndex - 1));
            } else { // already in order, move on to next item
               break;
            }
            compIndex--;
         }
      }
   }

//   public void printArray(int[] arr) {
//      for (int item : arr) {
//         System.out.print(item + " ");
//      }
//      System.out.println();
//   }
//
//   private void swap(int[] arr, int leftIndex, int rightIndex) {
//      int temp = arr[leftIndex];
//      arr[leftIndex] = arr[rightIndex];
//      arr[rightIndex] = temp;
//      return;
//   }

}
