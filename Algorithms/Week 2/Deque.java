import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {
    
   private int itemsCount;
   
   private Node first;
   private Node last;
        
   public Deque()                           // construct an empty deque
   {
       
   }
   
   public boolean isEmpty()                 // is the deque empty?
   {
       return itemsCount == 0;
   }
   
   public int size()                        // return the number of items on the deque
   {
       return itemsCount;
   }
   
   public void addFirst(Item item)          // add the item to the front
   {       
       if (item == null) throw new java.lang.IllegalArgumentException();
       
       Node node = new Node(item);
       
       if (itemsCount == 0)
       {
           first = node;
           last = node;
       }
       else
       {
           node.next = first;
           first.prev = node;
           first = node;
       }
       
       itemsCount++;
   }
   
   public void addLast(Item item)           // add the item to the end
   {
       if (item == null) throw new java.lang.IllegalArgumentException();
       
       Node node = new Node(item);
       
       if (itemsCount == 0)
       {
           first = node;
           last = node;
       }
       else
       {
           node.prev = last;
           last.next = node;
           last = node;
       }
                  
       itemsCount++;
   }
   
   public Item removeFirst()                // remove and return the item from the front
   {
       if (itemsCount == 0) throw new java.util.NoSuchElementException();
       
       Item value = first.value;
       
       itemsCount--;
       if (itemsCount == 0)
       {
           first = null;
           last = null;
           
           return value;
       }
       
       first = first.next;
       first.prev = null;
       
       return value;
   }
   
   public Item removeLast()                 // remove and return the item from the end
   {
       if (itemsCount == 0) throw new java.util.NoSuchElementException();
       
       Item value = last.value;
       
       itemsCount--;
       if (itemsCount == 0)
       {
           first = null;
           last = null;
           
           return value;
       }
       
       last = last.prev;
       last.next = null;
       
       return value;
   }
   
   public Iterator<Item> iterator()         // return an iterator over items in order from front to end
   {
       return new ListIterator();
   }
   
   private class ListIterator implements Iterator<Item> {
       
        private Node current = first;
        
        public boolean hasNext()  { return current != null; }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
                                   
            if (!hasNext()) throw new NoSuchElementException();
            
            Item item = current.value;
            current = current.next; 
            
            return item;
        }
    }
   
   private class Node
   {
       public Node next;
       public Node prev;
       
       public Item value;
       
       public Node(Item item)
       {
           value = item;
       }
   }
   
   public static void main(String[] args)   // unit testing (optional)
   {
       Deque<String> q = new Deque<String>();
       
       if (!q.isEmpty()) throw new java.lang.IllegalArgumentException();
       if (q.size() != 0) throw new java.lang.IllegalArgumentException();
       
       
       q.addFirst("3");
       if (q.isEmpty()) throw new java.lang.IllegalArgumentException();
       if (q.size() != 1) throw new java.lang.IllegalArgumentException();
       
       q.addFirst("2");
       if (q.isEmpty()) throw new java.lang.IllegalArgumentException();
       if (q.size() != 2) throw new java.lang.IllegalArgumentException();
                    
       q.addFirst("1");
       if (q.size() != 3) throw new java.lang.IllegalArgumentException();       
                  
       q.addLast("4");       
       q.addLast("5");

       q.removeLast();
       q.removeFirst();
       q.removeLast();
       
       q.removeFirst();
       q.removeLast();
       
       for(String s : q)
           StdOut.printf("%s\n", s);
   }   
}