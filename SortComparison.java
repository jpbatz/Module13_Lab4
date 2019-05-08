/**
 * Joanne Hayashi
 * EN.605.202.84.SP19
 * 
 * Module 13: Lab 4 - Sort Comparison
 * Class: SortComparison.java
 * - Runs the 5 sort methods on input files of 50, 500, 1000, 2000, and 5000 
 *   items
 * - Reports runtime metrics
 *   For extremely low run times, runs multiple and reports average runtime.
 * - Prints output for input file of 50 items
 */
package Module13_Lab4;

import java.io.BufferedReader;
import java.io.BufferedWriter;
//import java.io.FileReader;
//import java.io.FileWriter;
import java.io.IOException;

public class SortComparison {

   // RunMetric[] runMetrics;
   // int metricsIndex;
   int numItems = 0;
   int numIterations;
   
   long startTime;
   long endTime;
   long runTime;
   long totalRuntime;
   long avgRuntime;
   
   BufferedReader  cmdlist;   // contains run command lines
   BufferedReader  input;     // asc, ran, dup, rev input files
   BufferedWriter  report;    // contains runtime metrics
   BufferedWriter  output;    // contain sorted output for 50 items
   
   ProjectUtils utils;
   IterativeQuicksort iqs;
   HeapSort hs;
   
   public SortComparison() {
      // metricsIndex = 0;
      numItems = 0;
      numIterations = 1; // default
      
      startTime = 0;
      endTime = 0;
      runTime = 0;
      totalRuntime = 0;
      avgRuntime = 0;
      
      this.utils = new ProjectUtils();
      this.iqs = new IterativeQuicksort();
      this.hs = new HeapSort();

      // runMetrics = new RunMetric[numItems];
   }
   
   // ***** PROGRAM ENTRY POINT *****
   public static void main(String[] args) {

      int[] refNumbers;  // original input values for restoring between sorts
      int[] numbers;     // working space to sort reference numbers
      int arrSize = 0;
      
      // cl (command line) vars
      String clNumItems = "";
      String clInputFilename = null;
      String clOutputFilename = null;
      String cmdline = null;
      
      String[] cmdArr = new String[4];
      String[] commandLine = new String[4];
      
      SortComparison sc = new SortComparison();

      /* ===== command line usage ===== */
      
      if ((args.length != 3) && (args.length != 4)) {
         System.out.println("Insufficient number of arguments\n");
         System.out.println(
            "Usage:\n\n\tjava SortComparison <command list filename>"
               + " <report filename>"
               + " <# items> [# iterations]");
         System.out.println("\n\tThe <command list file> has the format:");
         System.out.println("\t\t<input filename> " + 
                            "[<output filename for n=50 items>]");
         System.out.println();
         System.out.println("\t\tWhere");
         System.out.println(
            "\t\t\t<input filename> contains the input items to sort"
            + " (see Note)");
         System.out.println(
            "\t\t\t<output filename> will contain the sorted array and"
            + " is required for 50 items, only.");
         System.out.println();
         System.out.println(
            "\tThe <report filename> will contain runtime metrics.");
         System.out.println();
         System.out.println("\tThe <# of items> is: " + 
               "50 | 500 | 1k | 2k | 5k | 10k | 20k");
         System.out.println();
         System.out.println(
            "\tThe [# iterations] is the number of times each sort should run"
            + " - the average runtime will be calculated. "
            + "\n\tDefault is 1 iteration. This qualifier is optional.");
         System.out.println();
         System.out.println("\tNote: Each command list file contains a list"
            + " of input files for the same n amount of items for the\n\t    "
            + "  4 order types listed one per line, in the following order:\n"
            + "\t\tascending, random, random duplicate, reverse.");
         System.out.println();
         System.exit(1);
      } 
      
      if ((args.length == 3) || (args.length == 4)) {

         // validate number of items specified in command line
         // must be 50, 500, 1k, 2k, 5k, 10k, 20k
         clNumItems = args[2]; // string value
         sc.validateNumItems(clNumItems);
         sc.numItems = sc.getArraySize(clNumItems);
         arrSize = sc.numItems;

         if (args.length == 3) {
            // number of iterations per sort unspecified, set to default = 1
            sc.numIterations = 1;
         } else {
            // TODO consider a limit
            sc.numIterations = Integer.parseInt(args[3]);
         }

         // opens input command list file handler
         sc.cmdlist = sc.utils.openInputFileHandler(sc.cmdlist, args[0]);

         // opens output report file handler
         sc.report = sc.utils.openOutputFileHandler(sc.report, args[1]);

      }
      
      // store commands from the command line list
      for (int clIndex = 0; clIndex < NUM_ORDER_TYPES; clIndex++) {
         try {
            cmdline = sc.cmdlist.readLine();
            if (cmdline == null) {
               System.out.println("Insufficient number of commands");
               System.exit(1);
            }
         } catch (IOException ioe) {
            System.exit(1);
         }
         // System.out.println(cmdline);
         cmdArr[clIndex] = cmdline;
      }
      System.out.println();
      
      // process each input file of order: asc, ran, dup, and rev
      for (int fileIndex = 0; fileIndex < NUM_ORDER_TYPES; fileIndex++) {
         
         commandLine = cmdArr[fileIndex].split(" ");
         clInputFilename = commandLine[0];
         // opens input file handler (one for each of the four order types)
         sc.input = sc.utils.openInputFileHandler(sc.input, clInputFilename);
         
         if (sc.numItems == 50) {
            clOutputFilename = commandLine[1];
            System.out.println("Sorted Output Filename: " + clOutputFilename);
            sc.output = sc.utils.openOutputFileHandler(sc.output, clOutputFilename);
         }

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
            // System.out.println(refNumbers[i]);
         }
         System.out.println();

         // sorts the array(s) once (default) or
         // may run sort types multiple times (calculates average run times):
         // on n = 50, 500, 1k, 2k, 5k, 10k, 20k items
         sc.runAllSortTypes(refNumbers, numbers, sc.orderTypes[fileIndex], 
                            sc.numIterations);
         

         // TODO make this happen in runAllSortTypes?
         if (arrSize == 50) { // output file for n = 50, only
            
            // TODO output for each of the 5 sort types
            sc.utils.copyArray(refNumbers, numbers);
            // results from all sortTypes to respective output file
            sc.iqs.quicksort("qs", numbers, 0, numbers.length - 1);
            // sc.printArray(numbers);
            sc.utils.writeArrayOut(numbers, sc.output); // sorted array to output file
         }

         // closes input file handler(s)
         sc.utils.closeInputFileHandler(sc.input);
         
         // closes output file handler(s)
         if (sc.output != null) {
            sc.utils.closeOutputFileHandler(sc.output);
         }
         
      } // end for (iterates 4 files for 4 input order types)
      
      // ==== close i/o file handlers =====

      // closes cmdlist file handler
      sc.utils.closeInputFileHandler(sc.cmdlist);
      
      // closes report file handler(s)
      sc.utils.closeOutputFileHandler(sc.report);
   }
   
   // ***** PRIVATE METHOD(S) *****
   
   private int getArraySize(String arg) {
      int arrSize = 0;
      switch (arg.toUpperCase()) {
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
   
   private void runAllSortTypes(int[] refArr, int[] arr, String orderType, 
                                int numIterations) {
      
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
            // System.out.println(subHeadings[index]);
            this.resetTimeVars();
            for (int i = 0; i < numIterations; i++) {
               utils.copyArray(refArr, arr);
               // utils.printArray(arr);
               this.startTime = System.nanoTime();
               this.iqs.quicksort(this.sortTypes[index], arr, firstIndex,
                     lastIndex);
               // System.out.println("RunAllSortTypes sortTypes: " +
               // this.sortTypes[index]);
               this.endTime = System.nanoTime(); // stop metric
               this.runTime = this.endTime - this.startTime; // calculate metric
               // System.out.println(" Round " + (i+1) + ": " + runTime);
               this.totalRuntime += this.runTime;
            }
         
         // sort type: "hs"
         } else if (this.sortTypes[index].equals("hs")) {
            // System.out.println(subHeadings[index]);
            this.resetTimeVars();
            for (int i = 0; i < numIterations; i++) {
               utils.copyArray(refArr, arr);
               // utils.printArray(arr);
               this.startTime = System.nanoTime();
               this.hs.heapsort(arr, arr.length);
               this.endTime = System.nanoTime(); // stop metric
               this.runTime = this.endTime - this.startTime; // calculate metric
               // System.out.println("Round " + (i+1) + ": " + runTime);
               this.totalRuntime += this.runTime;
            }
         }
         
         System.out.println(subHeadings[index]);
         utils.printArray(arr);
         this.displayMetrics();
      }
      return;
   }
   
   private long displayMetrics() {

      System.out.println("    Number of Runs = " + this.numIterations);
      System.out.println("Cumulative Runtime = " + this.totalRuntime + " nSec");
      this.avgRuntime = this.totalRuntime/this.numIterations;
      System.out.println(
            "   Average Runtime = " + this.avgRuntime + " nSec");
      System.out.println(
            "   Average Runtime = " + (this.avgRuntime / 1000) + " uSec");
      System.out.println(
            "   Average Runtime = " + (this.avgRuntime / 1000000) + " mSec");
      System.out.println();
      
      return this.avgRuntime;
   }
   
   private void resetTimeVars() {
      this.startTime = 0;
      this.endTime = 0;
      this.runTime = 0;
      this.totalRuntime = 0;
      this.avgRuntime = 0;
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
   
   
   private void validateNumItems(String numItems) {
      if ((!numItems.equalsIgnoreCase("50")) && 
          (!numItems.equalsIgnoreCase("500")) && 
          (!numItems.equalsIgnoreCase("1K")) && 
          (!numItems.equalsIgnoreCase("2K")) && 
          (!numItems.equalsIgnoreCase("5K")) && 
          (!numItems.equalsIgnoreCase("10K")) && 
          (!numItems.equalsIgnoreCase("20K"))) {
         System.out.println(
               "[SortComparison - validateNumItems()] Invalid Input Size");
         System.exit(1);
      }
      return;
   }
   
   
//   /**
//    * method: saveMetrics() - saves number of items and avg run time
//    *                         for metric object
//    * @param numItems - number of input items, n
//    * @param avgTimeElapsed - avg running time for n items
//    * @param output - output file handler
//    * @return - none
//    * @ref - Project0 (JHU)
//    */
//   private void saveMetrics(long numItems, String inputOrder, String sortType,
//                            long avgTimeElapsed, BufferedWriter output) {
//      RunMetric item = new RunMetric(numItems, avgTimeElapsed);
//      runMetrics[metricsIndex] = item;
//      try {
//         output.write(runMetrics[metricsIndex].getRuntime() + 
//                    " nSec for " + runMetrics[metricsIndex].getSize() + 
//                    " items");
//      } catch (IOException ioe) {
//         System.err.println(ioe.toString());
//         return;
//      }
//      metricsIndex++;
//      return;
//   }
//   
//   /**
//    * method: getMetrics() - shows metrics for all numbers of disks n=0 to n
//    * @param - none
//    * @return - all run metric objects stored in the run metrics array
//    * @ref: Project0 (JHU)
//    */
//   private String getMetrics() {
//      StringBuilder metrics = new StringBuilder();
//      for (int i=0; i<metricsIndex; i++) {
//         metrics.append("[" + i+ "]: " + runMetrics[i].getSize() + 
//                        " moves in " + runMetrics[i].getRuntime() + " nSec\n");
//         }
//      metrics.append("\n");
//      return metrics.toString();
//   }

   
   // ***** PRIVATE VARIABLE(S) *****
   
   // order types: asc, ran, dup, rev
   private static int NUM_ORDER_TYPES = 4;
   private String[] orderTypes = {"asc", "ran", "dup", "rev"};
   
   // sort types: "qs", "qs_k50", "qs_k100", "qs_mot", "hs"
   private static int NUM_SORT_TYPES = 5;
   private String[] sortTypes = {"qs", "qs_k50", "qs_k100", "qs_mot", "hs"};
   
}
