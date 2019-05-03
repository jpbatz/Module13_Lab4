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
      
      SortComparison sc = new SortComparison();
      
      // ===== verify commandline arguments =====
      
      // verify 2 command line arguments
      // Ref: Project0.java
      if (args.length != 2) {
         System.out.println(
            "Usage: " + "\n" + 
            "\tjava SortComparison " + 
            "<# of items> " + 
            "<input file list> " + 
            "<output filename>" + "\n\n" + 
            "\twhere # of items is: 50 | 500 | 1k | 2k | 5k | 10k | 20k");
         System.exit(1);
      }
      
      // ==== open i/o file handlers =====
      
      // opens input file handler
      // Ref: Project0.java
      try {
         sc.input = new BufferedReader(new FileReader(args[0]));
      } catch (IOException ioe) {
         System.err.println(ioe.toString());
         System.exit(1);
      }
      
      // opens output file handler
      // Ref: Project0.java
      try {
         sc.output = new BufferedWriter(new FileWriter(args[1]));
      } catch (IOException ioe) {
         System.err.println(ioe.toString());
         System.exit(1);
      }
      

      
      startTime = System.nanoTime();
      // run sort type (may need to run multiple - reset order per pass)
      // run on 50, 500, 1k, 2k, 5k, 10k, 20k items
      endTime = System.nanoTime();
      
//      sc.writeOut("...End\n", output);
//      sc.print50(output);
      
//      runTime = endTime - startTime;
//      sc.saveMetrics(totalNumMoves, runTime, output);
      
      
      
      
      // ==== close i/o file handlers =====

      // closes input file handler
      // Ref: Project0.java
      try {
         sc.input.close();
      } catch (IOException ioe) {
         System.err.println(ioe.toString());
         return;
      }
      
      // closes output file handler
      // Ref: Project0.java
      try {
         sc.output.close();
      } catch (IOException ioe) {
         System.err.println(ioe.toString());
         return;
      }
      
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
      try {
         output.write(text + "\n", 0, text.length());
      } catch (IOException ioe) {
         System.err.println(ioe.toString());
         return;
      }
      return;
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
   
   
   // ***** PRIVATE VARIABLE(S) *****
   

}
