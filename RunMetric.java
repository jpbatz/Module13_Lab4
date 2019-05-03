/**
 * Joanne Hayashi
 * EN.605.202.84.SP19
 * 
 * Module 13: Lab 4 - Sort Comparison
 * Class: RunMetric.java
 * A runtime metric object constructor
 * Ref: Project0 (JHU)
 */
package Module13_Lab4;

public class RunMetric {

   public RunMetric(long size, long runTime) {
      this.size = size;
      this.runTime = runTime;
   }

   /**
    * method: getRuntime() - returns running time
    * 
    * @param none
    * @return runTime - running time in nSec
    */
   public long getRuntime() {
      return this.runTime;
   }

   /**
    * method: getSize() - returns problem size
    * 
    * @param none
    * @return size - problem size
    */
   public long getSize() {
      return this.size;
   }

   // ***** PRIVATE VARIABLE(S) *****

   private long size;
   private long runTime;

}
