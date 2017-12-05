import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdRandom;

public class Board {
    
    private final int n;
    private int[][] blocks;
    
    private int hamming = 0;
    private int manhattan = 0;
    
    private final int[] emptyPosition = new int[2];
    private Queue<Board> neighbors = null;   
    
    public Board(int[][] blocks)           // construct a board from an n-by-n array of blocks
                                           // (where blocks[i][j] = block in row i, column j)
    {
        n = blocks.length;
        this.blocks = new int[n][n];
        
        for(int i = 0; i < n; i++)
        {
            for(int j = 0; j < n; j++)
            {
                this.blocks[i][j] = blocks[i][j];
                
                if (this.blocks[i][j] == 0) 
                {
                    setEmptyBlockPosition(i, j);                    
                    continue;
                }
                
                int[] position = getPosition(this.blocks[i][j]);                
                if (i != position[0] || j != position[1])
                {                                        
                    hamming++;
                    manhattan += Math.abs(i - position[0]) + Math.abs(j - position[1]);
                }
            }
        }                       
    }
    
    public int dimension()                 // board dimension n
    {
        return n;
    }
    
    public int hamming()                   // number of blocks out of place
    {
        return hamming;
    }
    
    public int manhattan()                 // sum of Manhattan distances between blocks and goal
    {
        return manhattan;
    }
    
    public boolean isGoal()                // is this board the goal board?
    {
        return hamming == 0;
    }
    
    public Board twin()                    // a board that is obtained by exchanging any pair of blocks
    {
        int[][] movedBlocks = copy();        
        
        int row1 = StdRandom.uniform(0, n);
        int col = StdRandom.uniform(0, n);
        
        if (movedBlocks[row1][col] == 0)
        {
            if (row1 + 1 < n) row1++;
            else row1--;
        }
        
        int row2;
        int col2 = col;
        
        if (row1 + 1 < n) row2 = row1 + 1;
        else row2 = row1 - 1;
            
        if (movedBlocks[row2][col2] == 0)
        {
            if (col2 + 1 < n) col2++;
            else col2--;
        }
        
        exchange(movedBlocks, row1, col, row2, col2);
        
        return new Board(movedBlocks);
    }
    
    public boolean equals(Object y)        // does this board equal y?
    {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        
        Board otherBoard = (Board)y;
        if (otherBoard.blocks.length != n) return false;
        
        for(int i = 0; i < n; i++)
        {
            for(int j = 0; j < n; j++)
            {
                if (otherBoard.blocks[i][j] != blocks[i][j]) return false;
            }
        }
        
        return true;
    }
    
    public Iterable<Board> neighbors()     // all neighboring boards
    {
        if (neighbors == null)
        {
            neighbors = new Queue<Board>();
            initNeighbors();
        }
        
        return neighbors;
    }
    
    public String toString()               // string representation of this board (in the output format specified below)
    {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }
    
    private void initNeighbors()
    {
        int row = emptyPosition[0];
        int col = emptyPosition[1];
        
                
        //left
        if (row - 1 >= 0)
            addWithExchange(row, col, row - 1, col);
        
        //right
        if (row + 1 < n)
            addWithExchange(row, col, row + 1, col);
        
        //up
        if (col - 1 >= 0)
            addWithExchange(row, col, row, col - 1);
        
        //down
        if (col + 1 < n)
            addWithExchange(row, col, row, col + 1);
    }
    
    private void addWithExchange(int row1, int col1, int row2, int col2)
    {
        int[][] movedBlocks = copy();        
        exchange(movedBlocks, row1, col1, row2, col2);
           
        neighbors.enqueue(new Board(movedBlocks));
    }
    
    private void exchange(int[][] blocks, int row1, int col1, int row2, int col2)
    {
        int tmp = blocks[row1][col1];
        blocks[row1][col1] = blocks[row2][col2];
        blocks[row2][col2] = tmp;   
    }
    
    private int[][] copy()
    {
        int[][] copyBlocks = new int[n][n];
        
        for(int i = 0; i < n; i++)
        {
            for(int j = 0; j < n; j++)
            {                    
                copyBlocks[i][j] = blocks[i][j];
            }
        }
        
        return copyBlocks;
    }
    
    private void setEmptyBlockPosition(int row, int col)
    {
        emptyPosition[0] = row;
        emptyPosition[1] = col;
    }
        
    private int[] getPosition(int index)
    {               
        int[] result = new int[2];
        index--;
        
        result[0] = index / n;
        result[1] = index % n;
        
        return result;
    }
}