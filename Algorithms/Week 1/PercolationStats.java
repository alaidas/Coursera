import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {
             
   private final double mean;
   private final double stddev;
   private final double confidence;
    
   public PercolationStats(int n, int trials)    // perform trials independent experiments on an n-by-n grid
   {       
       double[] data = new double[trials];
       int gridSize = n * n;
       
       for (int i = 0; i < trials; i++)
       {
           Percolation p = new Percolation(n);
           
           while (!p.percolates() && p.numberOfOpenSites() < gridSize)
           {
               int row = StdRandom.uniform(1, n + 1);
               int col = StdRandom.uniform(1, n + 1);           
               
               if (!p.isOpen(row, col))
                   p.open(row, col);              
           }
           
           data[i] = p.numberOfOpenSites() / ((double) gridSize);
       }
       
       mean = StdStats.mean(data);
       stddev = StdStats.stddev(data);     
       confidence = 1.96 * stddev / Math.sqrt(trials);
   }
    
   public double mean()                          // sample mean of percolation threshold
   {
       return mean;
   }
   
   public double stddev()                        // sample standard deviation of percolation threshold
   {
       return stddev;
   }
   
   public double confidenceLo()                  // low  endpoint of 95% confidence interval
   {
       return mean - confidence;
   }
   
   public double confidenceHi()                  // high endpoint of 95% confidence interval
   {
       return mean + confidence;
   }

   public static void main(String[] args)        // test client (described below)
   {
       int n = Integer.parseInt(args[0]);
       int trial = Integer.parseInt(args[1]);
       
       PercolationStats ps = new PercolationStats(n, trial);
   
       StdOut.printf("%-24s = %f\n", "mean", ps.mean());
       StdOut.printf("%-24s = %f\n", "stddev", ps.stddev());
       StdOut.printf("%-24s = [%f, %f]\n", "95%% confidence interval", ps.confidenceLo(), ps.confidenceHi());
   }
}