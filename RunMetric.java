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

   public RunMetric(long size, String order, String type, long avgRuntime) {
      this.size = size;
      this.order = "";
      this.type = "";
      this.avgRuntime = avgRuntime;
   }

   /**
    * method: getAvgRuntime() - returns running time
    * 
    * @param none
    * @return runTime - running time in nSec
    */
   public long getAvgRuntime() {
      return this.avgRuntime;
   }

   /**
    * @param avgRuntime the avgRuntime to set
    */
   public void setAvgRuntime(long avgRuntime) {
      this.avgRuntime = avgRuntime;
   }

   /**
    * @return the order
    */
   public String getOrder() {
      return order;
   }

   /**
    * @param order the order to set
    */
   public void setOrder(String order) {
      this.order = order;
   }

   /**
    * @return the type
    */
   public String getType() {
      return type;
   }

   /**
    * @param type the type to set
    */
   public void setType(String type) {
      this.type = type;
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

   /**
    * @param size the size to set
    */
   public void setSize(long size) {
      this.size = size;
   }
   
   // ***** PRIVATE VARIABLE(S) *****
   // n = 50, 500, 1000, 2000, 5000, 10000, or 20000
   private long size;
   
   // order of input data (asc)ending, (ran)dom, (dup)licates, (rev)erse
   private String order;
   
   // sorting methods:
   // qs, qs_k50, qs_k100, qs_mot (median of three), hs (heap sort)
   private String type;
   
   // average runtimes
   private long avgRuntime;

}
