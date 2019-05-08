/**
 * Joanne Hayashi
 * EN.605.202.84.SP19
 * 
 * Module 13: Lab 4 - Sort Comparison
 * Class: ProjectUtils.java
 * Commonly used methods
 */

package Module13_Lab4;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ProjectUtils {

   /**
    *  method: printArray() - displays contents of an array
    *  @param arr - array to display
    *  @return - none
    *  @ref Project0.java
    */
   public void printArray(int[] arr) {
      for (int item : arr) {
         System.out.print(item + " ");
      }
      System.out.println();
   }
   
   /**
    *  method: swap() - swaps two array items
    *  @param arr - array containing items to swap
    *  @param leftIndex - index containing first item
    *  @param rightIndex - index containing second item
    *  @return - none
    *  @ref Project0.java
    */
   public void swap(int[] arr, int leftIndex, int rightIndex) {
      int temp = arr[leftIndex];
      arr[leftIndex] = arr[rightIndex];
      arr[rightIndex] = temp;
      return;
   }

   /**
    *  method: copyArray() - copies contents of one array to another
    *  @param srcArr - source array
    *  @param destArr - destination array
    *  @return - none
    *  @ref Project0.java
    */
   public void copyArray(int[] srcArr, int[] destArr) {
      for (int i = 0; i < srcArr.length; i++) {
         destArr[i] = srcArr[i];
      }
   }
   
   /**
    *  method: openInputFileHandler() - opens input file handler
    *  @param br - input file handler to open
    *  @param arg - input file name to attach to input file handler
    *  @return - input file handler
    *  @ref Project0.java
    */
   public BufferedReader openInputFileHandler(BufferedReader br, 
                                              String infileName) {
      try {
         br = new BufferedReader(new FileReader(infileName));
      } catch (IOException ioe) {
         System.err.println(ioe.toString());
         System.exit(1);
      }
      return br;
   }
   
   /**
    *  method: openOutputFileHandler() - opens output file handler
    *  @param bw - output file handler to open
    *  @param arg - output file name to attach to output file handler
    *  @return - output file handler
    *  @ref Project0.java
    */
   public BufferedWriter openOutputFileHandler(BufferedWriter bw, 
                                               String outfileName) {
      try {
         bw = new BufferedWriter(new FileWriter(outfileName));
      } catch (IOException ioe) {
         System.err.println(ioe.toString());
         System.exit(1);
      }
      return bw;
   }
   
   /**
    *  method: closeInputFileHandler() - closes input file handler
    *  @param br - input file handler to close
    *  @return - none
    *  @ref Project0.java
    */
   public void closeInputFileHandler(BufferedReader br) {
      try {
         br.close();
      } catch (IOException ioe) {
         System.err.println(ioe.toString());
         return;
      }
   }
   
   /**
    *  method: closeOutputFileHandler() - closes output file handler
    *  @param bw - output file handler to close
    *  @return - none
    *  @ref Project0.java
    */
   public void closeOutputFileHandler(BufferedWriter bw) {
      try {
         bw.close();
      } catch (IOException ioe) {
         System.err.println(ioe.toString());
         return;
      }
   }
   
   /**
    *  method: writeOut() - writes a string to the output file handler
    *  @param text - text to write
    *  @param output - output file handler
    *  @return - none
    *  @ref Project0.java
    */
   public void writeTextOut(String text, BufferedWriter output) {
      // System.out.println(text);
      try {
         output.write(text + "\n");
      } catch (IOException ioe) {
         System.err.println(ioe.toString());
         return;
      }
      return;
   }
   /**
    *  method: writeArrayOut() - writes an array to the output file handler
    *  @param arr - array to write
    *  @param output - output file handler
    *  @return - none
    *  @ref Project0.java
    */
   public void writeArrayOut(int[] arr, BufferedWriter output) {
      for (int i=0; i<=arr.length-1; i++) {
         writeTextOut(String.valueOf(arr[i]), output);
      }
      System.out.println();
   }
}
