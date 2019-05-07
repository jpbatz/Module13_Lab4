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

   String[] orderTypes = {"asc", "ran", "dup", "rev"};
   String[] sortTypes = {"qs", "qs_k50", "qs_k100", "qs_mot", "hs"};
   
   BufferedReader  cmdlist;   // contains run command lines
   BufferedReader  input;     // asc, ran, dup, rev input files
   BufferedWriter  report;    // contains runtime metrics
   BufferedWriter  output;    // contain sorted output for 50 items
   
   ProjectUtils utils;
   IterativeQuicksort iqs;
   HeapSort hs;
   
   public SortComparison() {
      metricsIndex = 0;
      numItems = 0;
      
      this.utils = new ProjectUtils();
      this.iqs = new IterativeQuicksort();
      this.hs = new HeapSort();
      
      runMetrics = new RunMetric[numItems];
   }
   
   // ***** PROGRAM ENTRY POINT *****
   public static void main(String[] args) {

//      // runtime metrix measurements
//      long avgRuntime = 0;
      int[] refNumbers;  // original input values for restoring between sorts
      int[] numbers;     // working space to sort reference numbers
      int arrSize = 0;
      
//      String inputOrder;
//      String sortType;

      String clNumItems;
      String clInputFilename = null;
      String clReportFilename = null;
      String clOutputFilename = null;
      
      String cmdline = null;
      
      String[] cmdArr = new String[4];
      String[] commandLine = new String[4];
      String[] inputFilenames = new String[4];
      String[] reportFilenames = new String[4];
      String[] outputFilenames = new String[4];
//      String[] orderTypes = {"asc", "ran", "dup", "rev"};
//      String[] sortTypes = {"qs", "qs_k50", "qs_k100", "qs_mot", "hs"};
      
      SortComparison sc = new SortComparison();

      // ===== verify command line arguments =====
      
      if (args.length != 1) {
         System.out.println("Usage:\tjava SortComparison <input file list>");
         System.out.println("\nThe input file list has the format:");
         System.out.println("<# of items> " + 
                            "<input filename> " + 
                            "<report filename> " + 
                            "[<output filename>]");
         System.out.println("\nWhere the <# of items> is: " + 
                            "50 | 500 | 1k | 2k | 5k | 10k | 20k");
         System.out.println(
               "\nThe <report filename> contains runtime metrics.");
         System.out.println(
               "\nThe <output filename> is required fo 50 items, only.");
         System.exit(1);
      }
      

      
      // ==== open i/o file handlers =====
      
      // opens command list file handler
      sc.cmdlist = sc.openInputFileHandler(sc.cmdlist, args[0]);
      
      // store commands from the command line list
      for (int clIndex = 0; clIndex < NUM_ORDER_TYPES; clIndex++) {
         try {
            cmdline = sc.cmdlist.readLine();
            if (cmdline == null) {
               break;
            }
         } catch (IOException ioe) {
            System.exit(1);
         }
//         System.out.println(cmdline);
         cmdArr[clIndex] = cmdline;
      }
      System.out.println();
      
      // process each input file of order: asc, ran, dup, and rev
      for (int fileIndex = 0; fileIndex < NUM_ORDER_TYPES; fileIndex++) {
         
         commandLine = cmdArr[fileIndex].split(" ");

         clNumItems = commandLine[0];
         clInputFilename = commandLine[1];
         clReportFilename = commandLine[2];
         if (clNumItems.equals("50")) {
            clOutputFilename = commandLine[3];
            System.out.println("Sorted Output Filename: " + clOutputFilename);
         }

         // validate number of items specified in command line
         sc.validateNumItems(clNumItems);

         // set array size as valid number of items from command line
         arrSize = sc.getArraySize(clNumItems.toUpperCase());

         // opens report file handler
         sc.report = sc.openOutputFileHandler(sc.report, clReportFilename);

         // opens output file handler
         if (arrSize == 50) {
            sc.output = sc.openOutputFileHandler(sc.output, clOutputFilename);
         }

         // opens input file handler (one for each of the four order types)
         sc.input = sc.openInputFileHandler(sc.input, clInputFilename);
         
         refNumbers = new int[arrSize];
         numbers = new int[arrSize];

         // loads the reference array with integers from input file(s)
         // System.out.println("Input values to reference array:");
         for (int i = 0; i < arrSize; i++) {
            try {
               refNumbers[i] = Integer.parseInt(sc.input.readLine());
            } catch (IOException ioe) {
               System.out.println(ioe);
               System.exit(1);
            }
//            System.out.println(refNumbers[i]);
         }
         System.out.println();

         // sorts the array(s):
         // run sort types multiple times (calculates average run times):
         // on n = 50, 500, 1k, 2k, 5k, 10k, 20k items
         sc.runAllSortTypes(refNumbers, numbers, sc.orderTypes[fileIndex], 1);
         

         if (arrSize == 50) { // output file for n = 50, only
            
            // TODO output for each of the 5 sort types
            sc.copyArray(refNumbers, numbers);
            sc.iqs.quicksort("qs", numbers, 0, numbers.length - 1);
            // sc.printArray(numbers);
            sc.writeArrayOut(numbers, sc.output); // sorted array to output file
         }

         // closes input file handler(s)
         sc.closeInputFileHandler(sc.input);
         
         // closes report file handler(s)
         sc.closeOutputFileHandler(sc.report);
         
         // closes output file handler(s)
         if (sc.output != null) {
            sc.closeOutputFileHandler(sc.output);
         }
         
      } // end for (iterates 4 files)
      
      // ==== close i/o file handlers =====

      // closes cmdlist file handler
      sc.closeInputFileHandler(sc.cmdlist);
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
      // System.out.println(text);
      try {
         output.write(text + "\n");
      } catch (IOException ioe) {
         System.err.println(ioe.toString());
         return;
      }
      return;
   }
   
//   private void printArray(int[] arr) {
//      for (int i=0; i<arr.length; i++) {
//         System.out.print(String.valueOf(arr[i]) + " ");
//      }
//      System.out.println();
//   }
   
//   public void printArray(int[] arr) {
//      for (int item : arr) {
//         System.out.print(item + " ");
//      }
//      System.out.println();
//   }
   
   
   private void writeArrayOut(int[] arr, BufferedWriter output) {
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
      // destArr = null;
      // destArr = new int[srcArr.length];
      for (int i = 0; i < srcArr.length; i++) {
         destArr[i] = srcArr[i];
      }
   }
   
   private void runAllSortTypes(int[] refArr, int[] arr, String orderType, 
                                int numIterations) {

      long startTime = 0;
      long endTime = 0;
      long runTime = 0;
      long totalRuntime = 0;
      long avgRuntime = 0;
      
      int size = refArr.length;
      int firstIndex = 0;
      int lastIndex = size - 1;
      
      String[] subHeadings = {"Quicksort 1 - Pivot First Item:",
            "Quicksort 2 - k=50 Insertion Sort:",
            "Quicksort 3 - k=100 Insertion Sort:",
            "Quicksort 4 - Median of Three:",
            "Heap Sort:"};
      
      System.out.println("=== " + this.getHeader(orderType, size) + " ===\n");
      
      for (int index = 0; index < NUM_SORT_TYPES; index++) {
         
         // sort types: "qs", "qs_k50", "qs_k100", "qs_mot"
         if (!this.sortTypes[index].equals("hs")) {
            System.out.println(subHeadings[index]);
            this.resetTimeVars(startTime, endTime, runTime, totalRuntime, avgRuntime);
            for (int i = 0; i < numIterations; i++) {
               this.copyArray(refArr, arr);
               utils.printArray(arr);
               startTime = System.nanoTime();
//               this.iqs.quicksort(this.sortTypes[index], arr, 0, arr.length - 1);
               this.iqs.quicksort(this.sortTypes[index], arr, firstIndex, lastIndex);
//               System.out.println("RunAll sortTypes: " + this.sortTypes[index]);
               endTime = System.nanoTime(); // stop metric
               runTime = endTime - startTime; // calculate metric
               // System.out.println(" Round " + (i+1) + ": " + runTime);
               totalRuntime += runTime;
            }
            utils.printArray(arr);
            this.displayMetrics(totalRuntime, numIterations);
         
         // sort type: "hs"
         } else if (this.sortTypes[index].equals("hs")) {
            System.out.println(subHeadings[index]);
            this.resetTimeVars(startTime, endTime, runTime, totalRuntime, avgRuntime);
            for (int i = 0; i < numIterations; i++) {
               this.copyArray(refArr, arr);
               utils.printArray(arr);
               startTime = System.nanoTime();
               this.hs.heapsort(arr, arr.length);
               endTime = System.nanoTime(); // stop metric
               runTime = endTime - startTime; // calculate metric
               // System.out.println("Round " + (i+1) + ": " + runTime);
               totalRuntime += runTime;
            }
//            this.hs.printArray(arr);
            utils.printArray(arr);
            this.displayMetrics(totalRuntime, numIterations);
         }
         
         
      }
      
//      System.out.println("Quicksort 2 - k=50 Insertion Sort:");
//      this.resetTimeVars(startTime, endTime, runTime, totalRuntime, avgRuntime);
//      for (int i = 0; i < numIterations; i++) {
//         this.copyArray(refArr, arr);
//         startTime = System.nanoTime();
//         this.iqs.quicksort("qs_k50", arr, 0, arr.length-1);
//         endTime = System.nanoTime(); // stop metric
//         runTime = endTime - startTime; // calculate metric
//         // System.out.println("    Round " + (i+1) + ": " + runTime);
//         totalRuntime += runTime;
//      }
//      this.displayMetrics(totalRuntime, numIterations);
//      
//      System.out.println("Quicksort 3 - k=100 Insertion Sort:");
//      this.resetTimeVars(startTime, endTime, runTime, totalRuntime, avgRuntime);
//      for (int i = 0; i < numIterations; i++) {
//         this.copyArray(refArr, arr);
//         startTime = System.nanoTime();
//          this.iqs.quicksort("qs_k100", arr, 0, arr.length-1);
//         endTime = System.nanoTime(); // stop metric
//         runTime = endTime - startTime; // calculate metric
//         // System.out.println("    Round " + (i+1) + ": " + runTime);
//         totalRuntime += runTime;
//      }
//      this.displayMetrics(totalRuntime, numIterations);
//      
//      System.out.println("Quicksort 4 - Median of Three:");
//      this.resetTimeVars(startTime, endTime, runTime, totalRuntime, avgRuntime);
//      for (int i = 0; i < numIterations; i++) {
//         this.copyArray(refArr, arr);
//         startTime = System.nanoTime();
//         // this.iqs.quicksort("qs_mot", arr, 0, arr.length-1);
//         endTime = System.nanoTime(); // stop metric
//         runTime = endTime - startTime; // calculate metric
//         // System.out.println("    Round " + (i+1) + ": " + runTime);
//         totalRuntime += runTime;
//      }
//      this.displayMetrics(totalRuntime, numIterations);
//      
//      System.out.println("Heap Sort:");
//      this.resetTimeVars(startTime, endTime, runTime, totalRuntime, avgRuntime);
//      for (int i = 0; i < numIterations; i++) {
//         this.copyArray(refArr, arr);
//         startTime = System.nanoTime();
//         // this.iqs.heapsort(arr, 0, arr.length-1);
//         endTime = System.nanoTime(); // stop metric
//         runTime = endTime - startTime; // calculate metric
//         // System.out.println("Round " + (i+1) + ": " + runTime);
//         totalRuntime += runTime;
//      }
//      this.displayMetrics(totalRuntime, numIterations);
      
      return;
   }
   
   private long displayMetrics(long totalRuntime, int numIterations) {
      
      long avgRuntime;

      System.out.println("    Number of Runs = " + numIterations);
      System.out.println("Cumulative Runtime = " + totalRuntime + " nSec");
      avgRuntime = totalRuntime/numIterations;
      System.out.println(
            "   Average Runtime = " + avgRuntime + " nSec");
      System.out.println(
            "   Average Runtime = " + (avgRuntime / 1000) + " uSec");
      System.out.println(
            "   Average Runtime = " + (avgRuntime / 1000000) + " mSec");
      System.out.println();
      
      return avgRuntime;
   }
   
   private void resetTimeVars(long startTime, 
                              long endTime,
                              long runTime, 
                              long totalRuntime, 
                              long avgRuntime) {
      startTime = 0;
      endTime = 0;
      runTime = 0;
      totalRuntime = 0;
      avgRuntime = 0;
   }
   
   private String getHeader(String sortType, int numItems) {
      String headerString = "";
      switch(sortType) {
         case "asc": {
            headerString = 
               "Ascending Order of " + numItems + " items:";
            break;
         }
         case "ran": {
            headerString = 
               "Random Order of " + numItems + " items:";
            break;
         }
         case "dup": {
            headerString = 
               "Duplicates in Random Order of " + numItems + " items:";
            break;
         }
         case "rev": {
            headerString = 
               "Reverse Order of " + numItems + " items:";
            break;
         }
         default: {
            System.out.println(
               "[SortComparison - getHeader()]: Invalid sort type" 
                 + sortType);
            System.exit(1);
         }
      }
      return headerString;
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
   
   private void validateNumItems(String numItems) {
      // remove "10" for submission
      if ((!numItems.equalsIgnoreCase("10")) && 
          (!numItems.equalsIgnoreCase("50")) && 
          (!numItems.equalsIgnoreCase("500")) && 
          (!numItems.equalsIgnoreCase("1K")) && 
          (!numItems.equalsIgnoreCase("2K")) && 
          (!numItems.equalsIgnoreCase("5K")) && 
          (!numItems.equalsIgnoreCase("10K")) && 
          (!numItems.equalsIgnoreCase("20K"))) {
         System.out.println(
               "[SortComparison - validNumItems()] Invalid Input Size");
         System.exit(1);
      }
      return;
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
   
   // order types: asc, ran, dup, rev
   private static int NUM_ORDER_TYPES = 4;
   
   // sort types: "qs", "qs_k50", "qs_k100", "qs_mot", "hs"
   private static int NUM_SORT_TYPES = 5;

}
