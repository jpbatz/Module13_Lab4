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
   
   IterativeQuicksort iqs;
   
   public SortComparison() {
      metricsIndex = 0;
      numItems = 0;
      
      this.iqs = new IterativeQuicksort();
      
      runMetrics = new RunMetric[numItems];
   }
   
   // ***** PROGRAM ENTRY POINT *****
   public static void main(String[] args) {

      // runtime metrix measurements
      long startTime = 0;
      long endTime = 0;
      long runTime = 0;
      long avgRuntime = 0;
      int[] refNumbers;  // original input values for restoring between sorts
      int[] numbers;     // working space to sort reference numbers
      int arrSize = 0;
      
      String inputOrder;
      String sortType;
      
      SortComparison sc = new SortComparison();
//      IterativeQuicksort iqs = new IterativeQuicksort();


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
      
      // remove "10" for submission
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
      arrSize = sc.getArraySize(args[0].toUpperCase());
      
      // import integers from input file(s)
      
      //for each file
      //   for each item in file
      //      numbers = new int[];
      
      refNumbers = new int[arrSize];
      numbers = new int[arrSize];

      // loads the reference array
      // System.out.println("Input values to reference array:");
      for (int i = 0; i < arrSize; i++) {
         try {
            refNumbers[i] = Integer.parseInt(sc.input.readLine());
         } catch (IOException ioe) {
            System.out.println(ioe);
            System.exit(1);
         }
         // System.out.println(refNumbers[i]);
      }
      
      System.out.println();
      
      sc.copyArray(refNumbers, numbers);
      // System.out.println("Input numbers:");
      // sc.printArray(numbers, sc.output);
      

      
      // sorts the array
      
      // run sort type (may need to run multiple - reset order per pass)
      // run on 50, 500, 1k, 2k, 5k, 10k, 20k items
      
      // quicksort 1
//      startTime = System.nanoTime();   // start metric
//      iqs.quicksort(numbers, 0, numbers.length-1);
      avgRuntime = sc.runMulti(numbers, "asc", 5);
      if (arrSize == 50) { // output file for n = 50, only
         System.out.println("Sorted numbers:");
         sc.printArray(numbers, sc.output);
      }
//      endTime = System.nanoTime();     // stop  metric
//      runTime = endTime - startTime;   // calculate metric
//      // report metrics
//      System.out.println("Runtime = " + (runTime) + " nSec");
//      System.out.println("Runtime = " + (runTime/1000) + " uSec");
//      System.out.println("Runtime = " + (runTime / 1000000) + " mSec");

      // store metrics
      // sc.saveMetrics(arrSize, inputOrder, sortType, avgRunTime, output);
      
      
      // quicksort 2 (insertion sort from k=50)
      
      // quicksort 3 (insertion sort from k=100)
      
      
      // quicksort 4 (median of three)
      
      // heap sort
      
      
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
   
   private int getArraySize(String arg) {
      int arrSize = 0;
      switch (arg) {
         // remove case "10" for submission
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
      return arrSize;
   }
   
   private void copyArray(int[] srcArr, int[] destArr) {
      for (int i = 0; i < srcArr.length; i++) {
         destArr[i] = srcArr[i];
      }
   }
   
   private long runMulti(int[] arr, String sortType, int numIterations) {
      long avgRuntime = 0;
      long startTime = 0;
      long endTime = 0;
      long runTime = 0;
      long totalRuntime = 0;
      
      switch(sortType) {
         case "asc": {
            for (int i=0; i<numIterations; i++) {
               startTime = System.nanoTime();
               this.iqs.quicksort(arr, 0, arr.length-1);
               endTime = System.nanoTime();     // stop  metric
               runTime = endTime - startTime;   // calculate metric
               // report metrics
               System.out.print("Round " + (i+1) + ": ");
               System.out.println("  Runtime = " + (runTime) + " nSec");
//               System.out.println(" Runtime = " + (runTime/1000) + " uSec");
//               System.out.println(" Runtime = " + (runTime / 1000000) + " mSec");
               totalRuntime += runTime;
            }
            System.out.println("\nCumulative Runtime = " + totalRuntime + "nSec");
            break;
         }
         case "ran": {
            for (int i=0; i<numIterations; i++) {
               // runtime = 
               totalRuntime += runTime;
            }
            break;
         }
         case "dup": {
            for (int i=0; i<numIterations; i++) {
               // runtime = 
               totalRuntime += runTime;
            }
            break;
         }
         case "rev": {
            for (int i=0; i<numIterations; i++) {
               // runtime = 
               totalRuntime += runTime;
            }
            break;
         }
         default: {
            System.out.println("[SortComparison - runMulti()]: Invalid sort type " + sortType);
            System.exit(1);
         }
      }
      avgRuntime = totalRuntime/numIterations;
      System.out.println("   Average Runtime = " + avgRuntime + "nSec\n");
      return avgRuntime;
   }
   
   /**
    * method: saveMetrics() - saves number of items and avg run time
    *                         for metric object
    * @param numItems - number of input items, n
    * @param avgTimeElapsed - avg running time for n items
    * @param output - output file handler
    * @return - none
    * @ref - Project0 (JHU)
    */
   private void saveMetrics(long numItems, String inputOrder, String sortType,
                            long avgTimeElapsed, BufferedWriter output) {
      RunMetric item = new RunMetric(numItems, avgTimeElapsed);
      runMetrics[metricsIndex] = item;
      try {
         output.write(runMetrics[metricsIndex].getRuntime() + 
                    " nSec for " + runMetrics[metricsIndex].getSize() + 
                    " items");
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
