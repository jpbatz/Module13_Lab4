/**
 * Joanne Hayashi
 * EN.605.202.84.SP19
 * 
 * Module 13: Lab 4 - Sort Comparison
 * Class: ProjectUtils.java
 * Commonly used methods
 */

package Module13_Lab4;

public class ProjectUtils {

   public static void printArray(int[] arr) {
      for (int item : arr) {
         System.out.print(item + " ");
      }
      System.out.println();
   }
   
   public static void swap(int[] arr, int leftIndex, int rightIndex) {
      int temp = arr[leftIndex];
      arr[leftIndex] = arr[rightIndex];
      arr[rightIndex] = temp;
      return;
   }

}
