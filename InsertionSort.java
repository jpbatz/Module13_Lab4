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

   ProjectUtils utils = new ProjectUtils();

   /**
    *  method: insertionSort() - applies insertion sort to an array
    *  @param arr - array to sort
    *  @param startIndex - start index of array to sort
    *  @param endIndex - end index of array to sort
    *  @return - none
    *  @ref Project0.java
    */
   // Ref: https://www.codesdope.com/blog/article/
   // sorting-an-array-using-insertion-sort-in-java/
   public void insertionSort(int[] arr, int startIndex, int endIndex) {

//      int compIndex; // tracks index for comparison
//      int partitionSize = endIndex - startIndex + 1;
      
      // first item at first index is already sorted for itself
      for (int currIndex = 1; currIndex < arr.length; currIndex++) {
//      for (int currIndex = startIndex; currIndex < partitionSize; currIndex++) {

         // comparison index starts at current index
         int compIndex = currIndex;

         // move current item to its sorted position:
         // compare each item before current index
         // and swap if current item value is less
         while (compIndex > 0) {
            if (arr[compIndex - 1] > arr[compIndex]) {
               // swap with previous item if out of order
               utils.swap(arr, compIndex, (compIndex - 1));
            } else {
               // already in order, move on to next item
               break;
            }
            compIndex--;
         }
      }
   }
}
