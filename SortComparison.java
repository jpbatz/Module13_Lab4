/**
 * Joanne Hayashi
 * EN.605.202.84.SP19
 * 
 * Module 13: Lab 4 - Sort Comparison
 * Class: SortComparison.java
 * - Runs the 5 sort methods on input files of 50, 500, 1000, 2000, and 5000 
 *   items.
 * - Collects runtime metrics
 *   For extremely low run times, runs multiple and reports average runtime.
 * - Prints output for input file of 50 items
 */
package Module13_Lab4;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import Module8Lab2.RunMetric;

public class SortComparison {

   RunMetric[] runMetrics;
   int metricsIndex;
   int numItems = 0;

   BufferedReader  input;
   BufferedWriter  output;
   
   public SortComparison() {
      metricsIndex = 0;
      numItems = 0;
      
      // get number of items in input file
      // numItems = ;
      
      runMetrics = new RunMetric[numItems];
   }
   
   // ***** PROGRAM ENTRY POINT *****
   public static void main(String[] args) {

      // runtime metrix measurements
      long startTime = 0;
      long endTime = 0;
      long runTime = 0;
      int[] refNumbers;  // original input values for restoring between sorts
      int[] numbers;     // working space to sort reference numbers
      int arrSize = 0;
      
      SortComparison sc = new SortComparison();
      Quicksort qs = new Quicksort();

      // ===== verify commandline arguments =====
      
      // verify 2 command line arguments
      // Ref: Project0.java
//      System.out.println("#args = " + args.length);
      if (args.length != 3) {
         System.out.println(
            "Usage: " + "\n" + 
            "\tjava SortComparison " + 
            "<# of items> " + 
            "<input file list> " + 
            "<output filename>" + "\n\n" + 
            "\twhere # of items is: 50 | 500 | 1k | 2k | 5k | 10k | 20k");
         System.exit(1);
      }
      
      if ((!args[0].equalsIgnoreCase("10")) && 
          (!args[0].equalsIgnoreCase("50")) && 
          (!args[0].equalsIgnoreCase("500")) && 
          (!args[0].equalsIgnoreCase("1K")) && 
          (!args[0].equalsIgnoreCase("2K")) && 
          (!args[0].equalsIgnoreCase("5K")) && 
          (!args[0].equalsIgnoreCase("10K")) && 
          (!args[0].equalsIgnoreCase("20K"))) {
         System.out.println("Enter: Invalid Input Size");
         System.exit(1);
      }
      
      // ==== open i/o file handlers =====
      
      // opens input file handler
      sc.input = sc.openInputFileHandler(sc.input, args[1]);
      
      // opens output file handler
      sc.output = sc.openOutputFileHandler(sc.output, args[2]);
      
      // set array size for input size values
      switch (args[0].toUpperCase()) {
         case "10": {
            arrSize = 10;
            break;
         }
         case "50": {
            arrSize = 50;
            break;
         }
         case "500": {
            arrSize = 500;
            break;
         }
         case "1K": {
            arrSize = 1000;
            break;
         }
         case "2K": {
            arrSize = 2000;
            break;
         }
         case "5K": {
            arrSize = 5000;
            break;
         }
         case "10K": {
            arrSize = 10000;
            break;
         }
         case "20K": {
            arrSize = 20000;
            break;
         }
         default: {
            System.out.println("Switch: Invalid Input Size");
            System.exit(1);
         }
      }
      
      
      // import integers from input file(s)
      //for each file
      // for each item in file
      // numbers = new int[];
      
      // dev:
      refNumbers = new int[arrSize];
      numbers = new int[arrSize];

      // loads the reference array
//      System.out.println("Input values to reference array:");
      for (int i=0; i<arrSize; i++) {
         try {
            refNumbers[i] = Integer.parseInt(sc.input.readLine());
         } catch (IOException ioe) {
            System.out.println(ioe);
            System.exit(1);
         }
//         System.out.println(refNumbers[i]);
      }
      
      System.out.println();
      
      sc.copyArray(refNumbers, numbers);
//      System.out.println("Input numbers[]:");
//      sc.printArray(numbers, sc.output);
      
      // sorts the array
      startTime = System.nanoTime();
      
      qs.quicksort(numbers, 0, numbers.length-1);
      System.out.println("Sorted numbers[]:");
      sc.printArray(numbers, sc.output);
      
      endTime = System.nanoTime();
      runTime = endTime - startTime;
      
      System.out.println("Runtime = " + (runTime) + " nSec");
      System.out.println("Runtime = " + (runTime/1000) + " uSec");
      System.out.println("Runtime = " + (runTime/1000000) + " mSec");
      //
      
      startTime = System.nanoTime();
      
      // run sort type (may need to run multiple - reset order per pass)
      // run on 50, 500, 1k, 2k, 5k, 10k, 20k items
      //qs.quicksort(numbers, i, k);
      
      endTime = System.nanoTime();
      
//      sc.writeOut("...End\n", output);
//      sc.print50(output);

//      runTime = endTime - startTime;
//      sc.saveMetrics(totalNumMoves, runTime, output);
      

      // ==== close i/o file handlers =====

      // closes input file handler(s)
      sc.closeInputFileHandler(sc.input);
      
      // closes output file handler(s)
      sc.closeOutputFileHandler(sc.output);
      
   }
   
   
   // ***** PRIVATE METHOD(S) *****
   
   /**
    *  method: writeOut() - writes a string to the output file handler
    *  @param text - text to write.
    *  @param output - output file handler
    *  @return - none
    *  @ref Project0.java
    */
   private void writeOut(String text, BufferedWriter output) {
      System.out.println(text);
      try {
         output.write(text + "\n");
      } catch (IOException ioe) {
         System.err.println(ioe.toString());
         return;
      }
      return;
   }
   
   private void printArray(int[] arr, BufferedWriter output) {
      for (int i=0; i<=arr.length-1; i++) {
         this.writeOut(String.valueOf(arr[i]), output);
      }
      System.out.println();
   }
   
   private void copyArray(int[] srcArr, int[] destArr) {
      for (int i = 0; i < srcArr.length; i++) {
         destArr[i] = srcArr[i];
      }
   }
   /**
    * method: saveMetrics() - saves number of disks and run time
    *                         for metric object
    * @param numDisks - number of disks, n
    * @param timeElapsed - running time for n disks
    * @param output - output file handler
    * @return - none
    * @ref - Project0 (JHU)
    */
   private void saveMetrics(long numDisks, long timeElapsed, BufferedWriter output) {
      RunMetric item = new RunMetric(numDisks, timeElapsed);
      runMetrics[metricsIndex] = item;
      try {
         output.write("\nRuntime: " + runMetrics[metricsIndex].getSize() + 
                      " moves in " +  runMetrics[metricsIndex].getRuntime() + " nSec\n");
      } catch (IOException ioe) {
         System.err.println(ioe.toString());
         return;
      }
      metricsIndex++;
      return;
   }
   
   /**
    * method: getMetrics() - shows metrics for all numbers of disks n=0 to n
    * @param - none
    * @return - all run metric objects stored in the run metrics array
    * @ref: Project0 (JHU)
    */
   private String getMetrics() {
      StringBuilder metrics = new StringBuilder();
      for (int i=0; i<metricsIndex; i++) {
         metrics.append("[" + i+ "]: " + runMetrics[i].getSize() + 
                        " moves in " + runMetrics[i].getRuntime() + " nSec\n");
         }
      metrics.append("\n");
      return metrics.toString();
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
   
   // opens output file handler
   // Ref: Project0.java
   private BufferedWriter openOutputFileHandler(BufferedWriter bw, String arg) {
      try {
         bw = new BufferedWriter(new FileWriter(arg));
      } catch (IOException ioe) {
         System.err.println(ioe.toString());
         System.exit(1);
      }
      return bw;
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
   
   // closes output file handler
   // Ref: Project0.java
   private void closeOutputFileHandler(BufferedWriter bw) {
      try {
         bw.close();
      } catch (IOException ioe) {
         System.err.println(ioe.toString());
         return;
      }
   }
   
   // ***** PRIVATE VARIABLE(S) *****
   

}
