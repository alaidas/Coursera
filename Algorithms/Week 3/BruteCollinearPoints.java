import java.util.Arrays;

public class BruteCollinearPoints {
    
   private final LineSegment[] segments;
   private final int numberOfSegments;
    
   public BruteCollinearPoints(Point[] points)    // finds all line segments containing 4 points
   {            
       Slope[] foundSlopes = null;
       Slope[][] slopes = new Slope[points.length - 1][];              
       int foundLines = 0;
             
       Arrays.sort(points);       
       for(int i = 0; i < points.length - 1; i++)
       {   
           int num = 0;
           slopes[i] = new Slope[points.length - (i + 1)];

           for(int j = i + 1; j < points.length; j++)
           {
               slopes[i][num++] = new Slope(points[i], points[j], points[i].slopeTo(points[j]));
           }
                      
           Arrays.sort(slopes[i]);
       }
             
       for(int i = 0; i < slopes.length; i++)
       {
           double value = slopes[i][0].value;
           
           int foundCount = 1;
           
           Slope foundSlope = slopes[i][0];                   
           for(int j = 1; j < slopes[i].length; j++)
           {               
               if (value == slopes[i][j].value)
               {
                   foundCount++;
                   
                   if (slopes[i][j].point1.compareTo(foundSlope.point1) < 0)
                       foundSlope = new Slope(slopes[i][j].point1, foundSlope.point2, slopes[i][j].point1.slopeTo(foundSlope.point2));
                   else if (slopes[i][j].point2.compareTo(foundSlope.point2) > 0)
                       foundSlope = new Slope(foundSlope.point1, slopes[i][j].point2, foundSlope.point1.slopeTo(slopes[i][j].point2));
               }
               else 
               {
                   foundCount = 1;
                   foundSlope = slopes[i][j];
               }
               
               value = slopes[i][j].value;
               
               if (foundCount == 3)
               {
                   foundSlopes = resize(foundSlopes, foundLines + 1);
                   foundSlopes[foundLines++] = foundSlope;
               }
           }
       }
       
       numberOfSegments = foundLines;         
       segments = new LineSegment[foundLines];      
       
       for(int i = 0; i < segments.length; i++)
           segments[i] = new LineSegment(foundSlopes[i].point1, foundSlopes[i].point2);
   }   
   
   
   private Slope[] resize(Slope[] currentArray, int newLength)
   {
       Slope[] newSegments = new Slope[newLength];
       
       if (currentArray != null)
       {
           for(int i = 0; i < currentArray.length; i++)
               newSegments[i] = currentArray[i];
       }
       
       return newSegments;
   }
   
   public int numberOfSegments()        // the number of line segments
   {
       return numberOfSegments;
   }
   
   public LineSegment[] segments()                // the line segments
   {
       return segments;
   }
   
   private class Slope implements Comparable<Slope> {
              
       public final Point point1;
       public final Point point2;
       public final double value;
       
       public Slope(Point p1, Point p2, double value)
       {
           point1 = p1;
           point2 = p2;
           this.value = value;
       }
       
       public String toString() {
           return point1 + " " + point2 + " : " + value;
       }
       
       public int compareTo(Slope that) {
        
           if (that == null) return +1;
           
           if (that.value > value) return -1;
           if (that.value < value) return +1;
           
           return 0;
    }
   }
}