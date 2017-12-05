import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.Comparator;

public class Solver {
    
    private final Stack<Board> solution = new Stack<Board>();
    
    public Solver(Board initial)           // find a solution to the initial board (using the A* algorithm)
    {        
        if (initial == null) throw new java.lang.IllegalArgumentException();
        
        MinPQ<SearchNode> realQueue = new MinPQ<SearchNode>();
        MinPQ<SearchNode> twinQueue = new MinPQ<SearchNode>();
        
        SearchNode real = new SearchNode(0, initial, null);   
        SearchNode twin = new SearchNode(0, initial.twin(), null); 
        
        while (!real.board.isGoal() && !twin.board.isGoal())
        {               
            real = next(real, realQueue);
            twin = next(twin, twinQueue);
        }
        
        if (!real.board.isGoal()) return;    
        
        while (real != null)
        {
            solution.push(real.board);
            real = real.previous;
        }        
    }
    
    private SearchNode next(SearchNode current, MinPQ<SearchNode> queue)
    {
        for(Board neighbor : current.board.neighbors())
        {
            if (neighbor.equals(current.board)) continue;
            if (current.previous != null && current.previous.board.equals(neighbor)) continue;
                
            int moves = current.moves + 1;   
            SearchNode node = new SearchNode(moves, neighbor, current);
                
            queue.insert(node);   
        }            
                                
        return queue.delMin();                  
    }
    
    public boolean isSolvable()            // is the initial board solvable?
    {
        return solution.size() > 0;
    }
    
    public int moves()                     // min number of moves to solve initial board; -1 if unsolvable
    {        
        return solution.size() - 1;
    }
    
    public Iterable<Board> solution()      // sequence of boards in a shortest solution; null if unsolvable
    {
        return isSolvable() ? solution : null;
    }    
    
    private class ByManhattan implements Comparator<SearchNode>
    {
        public int compare(SearchNode board1, SearchNode board2)
        {  
            if (board1.board.manhattan() < board2.board.manhattan()) return -1;
            if (board1.board.manhattan() > board2.board.manhattan()) return +1;
            
            return 0;
        }
    }
        
    private class SearchNode implements Comparable<SearchNode> {
    
        public final Board board;
        public final SearchNode previous;
        public final int priority;
        public final int moves;
        
        public SearchNode(int moves, Board board, SearchNode previous)
        {
            this.moves = moves;
            this.priority = board.manhattan() + moves;
            this.board = board;
            this.previous = previous;
        }
        
        public int compareTo(SearchNode that) {
        
            if (that == null) return +1;
            
            if (priority < that.priority) return -1;
            if (priority > that.priority) return +1;
            
            return 0;
        }
    }  

    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
            blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        
        // solve the puzzle
        Solver solver = new Solver(initial);
        
        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }  
    
}