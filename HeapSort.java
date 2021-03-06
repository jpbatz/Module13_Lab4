/**
 * Joanne Hayashi
 * EN.605.202.84.SP19
 * 
 * Module 13: Lab 4 - Sort Comparison
 * Class: HeapSort.java
 * Iterative Heap Sort:
 * Builds heap and then extracts elements in sorted order.
 * Ref: https://www.geeksforgeeks.org/iterative-heap-sort/
 */
package Module13_Lab4;

public class HeapSort {

   ProjectUtils utils = new ProjectUtils();

   public void heapsort(int arr[], int n) {

      this.buildMaxHeap(arr, n);

      for (int i = n - 1; i > 0; i--) {
         // swap value of first indexed with last indexed
         utils.swap(arr, 0, i);

         // maintaining heap property after each swapping
         int j = 0, index;

         do {
            index = (2 * j + 1);

            // if left child is smaller than right child 
            // point index variable to right child
            if (index < (i - 1) && arr[index] < arr[index + 1])
               index++;

            // if parent is smaller than child then 
            // swap parent with child having higher value
            if (index < i && arr[j] < arr[index])
               utils.swap(arr, j, index);

            j = index;

         } while (index < i);
      }
   }
   
   // value of each child is always smaller than value of their parent
   private void buildMaxHeap(int arr[], int n) {
      for (int i = 1; i < n; i++) {
         // if child is bigger than parent
         if (arr[i] > arr[(i - 1) / 2]) {
            int j = i;
            
            // swap child and parent until parent is smaller
            while (arr[j] > arr[(j - 1) / 2]) {
               utils.swap(arr, j, (j - 1) / 2);
               j = (j - 1) / 2;
            }
         }
      }
   }
}
