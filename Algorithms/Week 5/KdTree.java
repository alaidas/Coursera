import edu.princeton.cs.algs4.*;

public class KdTree {
    
   private final SET<Point2D> set = new SET<Point2D>();
    
   public KdTree() // construct an empty set of points             
   {
   }
   
   public boolean isEmpty() // is the set empty? 
   {
       return set.isEmpty();
   }
   
   public int size() // number of points in the set 
   {
       return set.size();
   }
   
   public void insert(Point2D p) // add the point to the set (if it is not already in the set)
   {
       if (contains(p)) return;
       
       set.add(p);
   }
   
   public boolean contains(Point2D p) // does the set contain point p? 
   {
       return set.contains(p);
   }
   
   public void draw() // draw all points to standard draw 
   {
       //StdDraw.setPenColor(StdDraw.BLACK);
       //StdDraw.setPenRadius(0.01);
       
       for (Point2D p : set)
           p.draw();
   }
       
   public Iterable<Point2D> range(RectHV rect)  // all points that are inside the rectangle (or on the boundary)            
   {
       SET<Point2D> result = new SET<Point2D>();
             
       for (Point2D p : set)
       {
           if (rect.contains(p))
               result.add(p);
       }
       
       return result;
   }
       
   public Point2D nearest(Point2D p) // a nearest neighbor in the set to point p; null if the set is empty
   {
       Point2D nearestPoint = null;
       
       for (Point2D current : set)
       {
           if (nearestPoint == null)
           {
               nearestPoint = current;
               continue;
           }
               
           if (p.distanceTo(current) < p.distanceTo(nearestPoint))
               nearestPoint = current;                     
       }
       
       return nearestPoint;
   }  
   
   private static class Node {
       private Point2D p;      // the point
       private RectHV rect;    // the axis-aligned rectangle corresponding to this node
       private Node lb;        // the left/bottom subtree
       private Node rt;        // the right/top subtree
   }
}