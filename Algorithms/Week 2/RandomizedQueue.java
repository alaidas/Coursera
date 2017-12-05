import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;

import edu.princeton.cs.algs4.StdOut;

public class RandomizedQueue<Item> implements Iterable<Item> {
    
   private int index;
   private Item[] items;
    
   public RandomizedQueue()                 // construct an empty randomized queue
   {
       resize(1);
   }
   
   public boolean isEmpty()                 // is the queue empty?
   {
       return index == 0;
   }
   
   public int size()                        // return the number of items on the queue
   {
       return index;
   }
   
   public void enqueue(Item item)           // add the item
   {
       if (item == null) throw new java.lang.IllegalArgumentException();
       
       if (index == items.length) resize(items.length * 2);
       
       items[index++] = item;
   }
   
   public Item dequeue()                    // remove and return a random item
   {
       if (index == 0) throw new java.util.NoSuchElementException();
       
       int indexAt = StdRandom.uniform(0, index);
       Item item = items[indexAt];
       
       items[indexAt] = items[--index];
       items[index] = null;
       
       return item;
   }
   
   public Item sample()                     // return (but do not remove) a random item
   {
       if (index == 0) throw new java.util.NoSuchElementException();
       
       int indexAt = StdRandom.uniform(0, index);
       return items[indexAt];
   }
   
   public Iterator<Item> iterator()         // return an independent iterator over items in random order
   {
       return new RandomIterator(shuffle(), index);
   }
   
   private class RandomIterator implements Iterator<Item> {
       
        private int current = 0;
        private final Item[] shuffledItems;
        private final int totalLength;
        
        public RandomIterator(Item[] items, int length)
        {
            shuffledItems = items;
            totalLength = length;
        }
        
        public boolean hasNext()  { return current < totalLength; }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
                                   
            if (!hasNext()) throw new java.util.NoSuchElementException();
            
            return shuffledItems[current++];
        }
    }
   
   private void resize(int newSize)
   {
       Item[] newItems = (Item[]) new Object[newSize];
       
       if (items != null)
       {
           for(int i = 0; i < index; i++)
               newItems[i] = items[i];
       }
       
       items = newItems;           
   }
   
   private Item[] shuffle()
   {
       Item[] newItems = (Item[]) new Object[index];
       
       for(int i = 0; i < newItems.length; i++)
               newItems[i] = items[i];
       
       StdRandom.shuffle(newItems, 0, newItems.length);
       return newItems;
   }
   
   public static void main(String[] args)   // unit testing (optional)
   {
       RandomizedQueue<String> rq = new RandomizedQueue<String>();
       
       rq.enqueue("1");
       rq.enqueue("2");
       rq.enqueue("3");
       rq.enqueue("4");
       rq.enqueue("5");
       rq.enqueue("6");
       
       //StdOut.printf("%s\n", rq.sample());
       
       for(String s : rq)
           StdOut.printf("%s ", s);
       
       StdOut.printf("\n");
       
       for(String s : rq)
           StdOut.printf("%s ", s);
   }
}