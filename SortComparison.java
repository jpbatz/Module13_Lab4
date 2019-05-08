/**
 * Joanne Hayashi
 * EN.605.202.84.SP19
 * 
 * Module 13: Lab 4 - Sort Comparison
 * Class: SortComparison.java
 * - Runs the 5 sort methods on input files of 50, 500, 1000, 2000, and 5000 
 *   items once by default or multiple times as qualified on the command line
 * - Reports runtime metrics, including average runtime for multiple runs
 * - Displays results and metrics
 * - Prints to output file for input file of 50 items
 */
package Module13_Lab4;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class SortComparison {

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
      
      // user selected options
      numItems = 0;                          // number of n items to sort
      numIterations = 1;                     // default # of sort repetitions
      
      // runtime metrics
      startTime = 0;                         // start time (nano seconds)
      endTime = 0;                           // end time  (nano seconds)
      runTime = 0;                           // run time diff
      totalRuntime = 0;                      // total of multiple run times
      avgRuntime = 0;                        // average of multiple runs
      
      this.utils = new ProjectUtils();       // commonly used utility methods
      this.iqs = new IterativeQuicksort();   // quicksort methods
      this.hs = new HeapSort();              // heapsort methoods
   }

   /* ***** PROGRAM ENTRY POINT ***** */

   public static void main(String[] args) {

      int[] refNumbers;  // original input values for restoring between sorts
      int[] numbers;     // working space to sort reference numbers
      int arrSize = 0;   // accommodates n items
      
      // cl (command line) vars
      String cmdline = null;  // line from file list contains file name(s)
      String clNumItems = ""; // stores # items from cl arg
      String clInputFilename = null;   // stores data filename from cl arg
      String clOutputFilename = null;  // stores output filename for n=50
      
      String[] cmdArr = new String[4]; // stores each line from file list
      String[] commandLine = new String[4];  // stores each item from line
      
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
      
      /* ===== process command line args ===== */
      
      if ((args.length == 3) || (args.length == 4)) {

         // validate number of items specified in command line
         // must be 50, 500, 1k, 2k, 5k, 10k, 20k
         clNumItems = args[2]; // string value
         sc.validateNumItems(clNumItems);
         sc.numItems = sc.getArraySize(clNumItems); // numeric value
         arrSize = sc.numItems;

         if (args.length == 3) {
            // number of iterations per sort unspecified, set to default = 1
            sc.numIterations = 1;
         } else {
            // consider a limit
            sc.numIterations = Integer.parseInt(args[3]);
         }

         // opens i/o file handler(s): input command list, output report */
         sc.cmdlist = sc.utils.openInputFileHandler(sc.cmdlist, args[0]);
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
         cmdArr[clIndex] = cmdline;
      }
      System.out.println();
      
      // process each input file of order: asc, ran, dup, and rev
      for (int fileIndex = 0; fileIndex < NUM_ORDER_TYPES; fileIndex++) {
         
         commandLine = cmdArr[fileIndex].split(" ");
         clInputFilename = commandLine[0];
         sc.input = sc.utils.openInputFileHandler(sc.input, clInputFilename);
         
         if (sc.numItems == 50) {
            clOutputFilename = commandLine[1];
            System.out.println(
               "Sorted Output Filename: " + clOutputFilename);
            sc.output = 
               sc.utils.openOutputFileHandler(sc.output, clOutputFilename);
         }

         refNumbers = new int[arrSize];
         numbers = new int[arrSize];

         // loads the reference array with integers from input file(s)
         for (int i = 0; i < arrSize; i++) {
            try {
               refNumbers[i] = Integer.parseInt(sc.input.readLine());
            } catch (IOException ioe) {
               System.out.println(ioe);
               System.exit(1);
            }
         }
         System.out.println();

         /* ===== sorts input items ===== */
         
         // sorts the array(s) once (default) or
         // multiple times (calculates average run times):
         // on n = 50, 500, 1k, 2k, 5k, 10k, 20k items
         sc.runAllSortTypes(refNumbers, numbers, sc.orderTypes[fileIndex], 
                            sc.numIterations);

         /* ==== close i/o file handlers: input data and output n=50 ===== */
         sc.utils.closeInputFileHandler(sc.input);
         if (sc.output != null) {
            sc.utils.closeOutputFileHandler(sc.output);
         }
         
      } // end for (iterates 4 files for 4 input order types)
      
      /* ==== close i/o file handlers: cmdlist and report ===== */
      sc.utils.closeInputFileHandler(sc.cmdlist);
      sc.utils.closeOutputFileHandler(sc.report);
   }
   
   // ***** PRIVATE METHOD(S) *****
   
   /**
    * method: getArraySize() - converts number if items from string to integer
    * @param arg - command line qualifier for number of items to sort
    * @return numeric value of number of items to sort form input
    */
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
   
   /**
    * method: runAllSortTypes() - runs all 5 sort types with repetition
    * @param refArr - input array of items to restore working arr between runs
    * @param arr - input items to be sorted
    * @param orderType - asc, ran, dup, or rev ordered data
    * @param numIterations - number of repetitions to run to get avg metrics
    */
   private void runAllSortTypes(int[] refArr, int[] arr, String orderType, 
                                int numIterations) {
      
      int size = refArr.length;
      int firstIndex = 0;
      int lastIndex = size - 1;
      
      String reportStr = "";
      String[] subHeadings = {"Quicksort 1 - Pivot First Item:",
            "Quicksort 2 - k=50 Insertion Sort:",
            "Quicksort 3 - k=100 Insertion Sort:",
            "Quicksort 4 - Median of Three:",
            "Heap Sort:"};
      
      reportStr = "=== " + this.getHeader(orderType, size) + " ===\n";
      this.utils.writeTextOut(reportStr, this.report);
      
      for (int index = 0; index < NUM_SORT_TYPES; index++) {
         
         // sort types: "qs", "qs_k50", "qs_k100", "qs_mot"
         if (!this.sortTypes[index].equals("hs")) {
            this.resetTimeVars();
            for (int i = 0; i < numIterations; i++) {
               utils.copyArray(refArr, arr);
               // utils.printArray(arr);
               this.startTime = System.nanoTime();
               this.iqs.quicksort(this.sortTypes[index], arr, firstIndex,
                     lastIndex);
               this.endTime = System.nanoTime(); // stop metric
               this.runTime = this.endTime - this.startTime; // calcs metric
               this.totalRuntime += this.runTime;
            }
         
         // sort type: "hs"
         } else if (this.sortTypes[index].equals("hs")) {
            this.resetTimeVars();
            for (int i = 0; i < numIterations; i++) {
               utils.copyArray(refArr, arr);
               this.startTime = System.nanoTime();
               this.hs.heapsort(arr, arr.length);
               this.endTime = System.nanoTime(); // stop metric
               this.runTime = this.endTime - this.startTime; // calculate metric
               this.totalRuntime += this.runTime;
            }
         }
         reportStr = subHeadings[index];
         this.utils.writeTextOut(reportStr, this.report);
         utils.printArray(arr);
         this.generateMetrics();
      }
      if (this.numItems == 50) {
         this.utils.writeArrayOut(arr, this.output);
      }
      return;
   }
   
   /**
    * method: generateMetrics() - calculates and displays runtime metrics
    *    saves to report file
    * @return average runtime per sort type (single or repetitions)
    */
   private long generateMetrics() {
      String reportStr;
      reportStr = "    Number of Runs = " + this.numIterations;
      this.utils.writeTextOut(reportStr, this.report);
      reportStr = "Cumulative Runtime = " + this.totalRuntime + " nSec";
      this.utils.writeTextOut(reportStr, this.report);
      this.avgRuntime = this.totalRuntime/this.numIterations;
      reportStr = 
            "   Average Runtime = " + this.avgRuntime + " nSec";
      this.utils.writeTextOut(reportStr, this.report);
      reportStr = 
            "   Average Runtime = " + (this.avgRuntime / 1000) + " uSec";
      this.utils.writeTextOut(reportStr, this.report);
      reportStr = 
            "   Average Runtime = " + (this.avgRuntime / 1000000) + " mSec";
      this.utils.writeTextOut(reportStr, this.report);
      reportStr = "";
      this.utils.writeTextOut(reportStr, this.report);
      return this.avgRuntime;
   }
   
   /**
    * method: resetTimeVars() - clears runtime metric vars for new run
    * @param none
    * @return none
    */
   private void resetTimeVars() {
      this.startTime = 0;
      this.endTime = 0;
      this.runTime = 0;
      this.totalRuntime = 0;
      this.avgRuntime = 0;
   }
   
   /**
    * method: getHeader() - generates header for sort order type and # items
    * @param sortType - sort type asc, ran, dup, and rev
    * @param numItems - number if input items to sort
    * @return header string for order type and number of input items
    */
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
    * method: validateNumItems() - validates command line argument for n items
    * @param numItems - command line argument indicating # if items to sort
    * @return none
    */
   private void validateNumItems(String numItems) {
      if ((!numItems.equalsIgnoreCase("50")) && 
          (!numItems.equalsIgnoreCase("500")) && 
          (!numItems.equalsIgnoreCase("1K")) && 
          (!numItems.equalsIgnoreCase("2K")) && 
          (!numItems.equalsIgnoreCase("5K")) && 
          (!numItems.equalsIgnoreCase("10K")) && 
          (!numItems.equalsIgnoreCase("20K"))) {
         System.out.println(
               "[SortComparison - validateNumItems()] Invalid # of items");
         System.exit(1);
      }
      return;
   }
   
   /* ***** PRIVATE VARIABLE(S) ***** */
   
   // order types: asc, ran, dup, rev
   private static int NUM_ORDER_TYPES = 4;
   private String[] orderTypes = {"asc", "ran", "dup", "rev"};
   
   // sort types: "qs", "qs_k50", "qs_k100", "qs_mot", "hs"
   private static int NUM_SORT_TYPES = 5;
   private String[] sortTypes = {"qs", "qs_k50", "qs_k100", "qs_mot", "hs"};
   
}
