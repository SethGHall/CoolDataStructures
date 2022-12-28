package deque;

/**
   Seth Hall
*/
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayDeque<E> implements DequeADT<E>
{
   private final int INITIAL_CAPACITY = 5;
   private int numElements;
   private int frontIndex; // index of the current front of deque
   private int rearIndex; // index of one past current rear of deque
   // array of elements between frontIndex (incl) and rearIndex (excl)
   private E[] elements;

   // default constructor that creates a new deque
   // that is initially empty
   public ArrayDeque()
   {  numElements = 0;
      frontIndex = 0;
      rearIndex = 0;
      elements = (E[])(new Object[INITIAL_CAPACITY]); // unchecked
   }

   // constructor for creating a new deque which
   // initially holds the elements in the collection c
   public ArrayDeque(Collection<? extends E> c)
   {  this();
      for (E element : c)
         enqueueRear(element);
   }

   // adds one element to the rear of this deque
   public void enqueueRear(E element)
   {  if (numElements >= elements.length)
         expandCapacity();
      elements[rearIndex++] = element;
      if (rearIndex >= elements.length)
         rearIndex = 0;
      numElements++;
   }

   // removes and returns the front element of the deque
   public E dequeueFront() throws NoSuchElementException
   {  if (numElements > 0)
      {  E frontElement = elements[frontIndex];
         elements[frontIndex++] = null;
         if (frontIndex >= elements.length)
            frontIndex = 0;
         numElements--;
         return frontElement;
      }
      else
         throw new NoSuchElementException();
   }

   // returns without removing the front element of this deque
   public E first() throws NoSuchElementException
   {  if (numElements > 0)
         return elements[frontIndex];
      else
         throw new NoSuchElementException();
   }

   // adds one element to the front of this deque
   public void enqueueFront(E element)
   {  if (numElements >= elements.length)
         expandCapacity();
      frontIndex--;
      if (frontIndex < 0)
         frontIndex = elements.length-1;
      elements[frontIndex] = element;
      numElements++;
   }

   // removes and returns the rear element of the deque
   public E dequeueRear() throws NoSuchElementException
   {  if (numElements > 0)
      {  rearIndex--;
         if (rearIndex < 0)
            rearIndex = elements.length-1;
         E rearElement = elements[rearIndex];
         elements[rearIndex] = null;
         numElements--;
         return rearElement;
      }
      else
         throw new NoSuchElementException();
   }

   // returns without removing the rear element of this deque
   public E last() throws NoSuchElementException
   {  if (numElements > 0)
         return elements[rearIndex<=0 ? elements.length-1 : rearIndex-1];
      else
         throw new NoSuchElementException();
   }

   // returns true if this deque contains no elements
   public boolean isEmpty()
   {  return (numElements==0);
   }

   // returns the number of elements in this deque
   public int size()
   {  return numElements;
   }

   // returns a string representation of the deque from front to rear
   public String toString()
   {  String output = "[";
   
//      for(int i=0;i<elements.length;i++)
//      {
//          output+=elements[i];
//          if(i<elements.length-1)
//              output+=",";
//      }
      int numObtained = 0, index = frontIndex;
      while (numObtained<numElements)
      {  output += elements[index++];
         numObtained++;
         if (index >= elements.length)
            index = 0;
         if (numObtained < numElements)
            output += ",";
     }
      output += "]";
      return output;
   }

   // helper method which doubles the current size of the array
   private void expandCapacity()
   {  E[] largerArray =(E[])(new Object[elements.length*2]);//unchecked
      // copy the elements array to the largerArray
      int numObtained = 0, index = frontIndex;
      while (numObtained<numElements)
      {  largerArray[numObtained++] = elements[index++];
         if (index >= elements.length)
            index = 0;
      }
      frontIndex = 0;
      rearIndex = numElements;
      elements = largerArray;
   }
   @Override
    public Iterator<E> iterator() {
        return new ArrayIterator();
    }

    @Override
    public void clear() {
        elements = (E[])(new Object[20]);
        numElements = 0;
        frontIndex=0;
        rearIndex=0;
    }
    
    public class ArrayIterator implements Iterator<E>
    {
        private int position;
        
        private ArrayIterator() {
            position = frontIndex;
            
        }

        @Override
        public boolean hasNext() {
              
            if(position != rearIndex)
                return true;
            else return false;
        }

        @Override
        public E next() {
           E value = elements[position];
           position++;
           if(position >= elements.length)
                position = 0;  
           return value;
        }
        
    }
   // driver main method to test the class
   public static void main(String[] args)
   {  DequeADT<String> deque = new ArrayDeque<>();
      System.out.println("ENQUEING ELEMENTS TO REAR ");
      deque.enqueueRear("cow"); deque.enqueueRear("fly");
      deque.enqueueRear("dog"); deque.enqueueRear("bat");
      deque.enqueueRear("fox"); deque.enqueueRear("cat");
      System.out.println("DEQUE IS "+deque);
      System.out.println("ENQUEING EEL AND ANT TO FRONT");
      deque.enqueueFront("eel"); deque.enqueueFront("ant");
      System.out.println("DEQUE IS "+deque);
      System.out.println("removing from rear "+deque.dequeueRear());
      System.out.println("removing from front "+deque.dequeueFront());
      System.out.println();
      System.out.println("DEQUE IS "+deque);
      System.out.println("ENQUEING ZEBRA ON BOTH SIDES");
      deque.enqueueRear("Zebra");
      deque.enqueueFront("Zebra2");
      System.out.println("DEQUE IS "+deque);
      
      System.out.println("ITERATE THROUGH");
      Iterator<String> it = deque.iterator();
      while(it.hasNext())
           System.out.println(">"+it.next());
      
      
      
      System.out.println("Removing 2 elements from both sides");
      System.out.println(deque.dequeueRear());
      System.out.println(deque.dequeueRear());
      System.out.println(deque.dequeueFront());
      System.out.println(deque.dequeueFront());
      System.out.println("DEQUE IS "+deque);
   }

}
