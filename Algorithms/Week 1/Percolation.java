import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdOut;

public class Percolation {
   
   private final WeightedQuickUnionUF grid;
   private final int gridSizeN;
   private final int topNode;
   private final int bottomNode;
   
   private boolean[] openSites;
   private int openSitesCount;
    
   public Percolation(int n)                
   {      
       gridSizeN = n;
       topNode = gridSizeN * gridSizeN;
       bottomNode = gridSizeN * gridSizeN + 1;
       
       grid = new WeightedQuickUnionUF(gridSizeN * gridSizeN + 2);
       openSites = new boolean[gridSizeN * gridSizeN];              
   }
   
   public void open(int row, int col)    // open site (row, col) if it is not open already
   {
       int position = getPosition(row, col);       
       
       if (position < gridSizeN)
       {
           grid.union(topNode, position);
       }
       
       if (position >= gridSizeN * gridSizeN - gridSizeN)
       {
           grid.union(bottomNode, position);
       }
       
       if (position - gridSizeN >= 0 && isAlreadyOpen(position - gridSizeN))
       {
           grid.union(position - gridSizeN, position);
       }
       
       if (position + gridSizeN < topNode && isAlreadyOpen(position + gridSizeN))
       {
           grid.union(position + gridSizeN, position);
       }
       
       int nextPosition = position - 1;
       if (col - 1 > 0 && isAlreadyOpen(nextPosition))
       {
           grid.union(nextPosition, position);
       }
       
       nextPosition = position + 1;
       if (col + 1 <= gridSizeN && isAlreadyOpen(position + 1))
       {
           grid.union(nextPosition, position);
       }
       
       if (!openSites[position])
       {
           openSites[position] = true;
           openSitesCount++;
       }
   } 
   
   public boolean isOpen(int row, int col)  // is site (row, col) open?
   {          
       return isAlreadyOpen(getPosition(row, col));
   }
   
   public boolean isFull(int row, int col)  // is site (row, col) full?
   {       
       return grid.connected(topNode, getPosition(row, col));
   }
   
   public int numberOfOpenSites()       // number of open sites
   {   
       return openSitesCount;
   }
   
   public boolean percolates()              // does the system percolate?
   {
       return grid.connected(topNode, bottomNode);
   }

   private boolean isAlreadyOpen(int position)
   {             
       return openSites[position];
   }
   
   private int getPosition(int row, int col)
   {
       row--;
       col--;
       return row * gridSizeN + col;
   }
}