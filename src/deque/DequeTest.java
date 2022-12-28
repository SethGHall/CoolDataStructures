package deque;


import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * Used to test out most of the methods in the LinkedDeque
 * NOTE: Won't compile until LinkedDeque up and running
 * @author Seth
 */
public class DequeTest {
    public static void main(String[] args)
    {   System.out.println("============DEQUE TEST=================");
        DequeADT<String> deque = new ArrayDeque<>();
        System.out.println("Enqueue To FRONT (F) and REAR (R)");
        deque.enqueueFront("(F)");
        deque.enqueueRear("(R)");
        System.out.println("Deque is: "+deque.toString());
        System.out.println("CLEARING the Deque:");
        deque.clear();
        System.out.println("Deque is: "+deque.toString());
        
        System.out.println("Enqueue To REAR: A, B, C, D");
        deque.enqueueRear("A");
        deque.enqueueRear("B");
        deque.enqueueRear("C");
        deque.enqueueRear("D");
        System.out.println("Deque is "+deque.toString());
        System.out.println("FRONT element is: "+deque.first()+", REAR element is: "+deque.last());
        
        System.out.println("Enqueue To FRONT: E, F, G, H");
        deque.enqueueFront("E");
        deque.enqueueFront("F");
        deque.enqueueFront("G");
        deque.enqueueFront("H");
        System.out.println("Deque is "+deque.toString());
        System.out.println("Number of elements in Deque: "+deque.size());
        System.out.println("FRONT element is: "+deque.first()+", REAR element is: "+deque.last());
        System.out.println("Dequeue TWO elements from FRONT: "+deque.dequeueFront()+","+deque.dequeueFront());
        System.out.println("Dequeue TWO elements from REAR: "+deque.dequeueRear()+","+deque.dequeueRear());
        System.out.println("Number of elements in Deque: "+deque.size());
        System.out.println("Deque is: "+deque.toString());
        System.out.println("FRONT element is: "+deque.first()+", REAR element is "+deque.last());
          
        System.out.print("Testing ITERATOR: ");
        Iterator<String> iterator = deque.iterator();
        while(iterator.hasNext())
        {   System.out.print(iterator.next()+" ");
        }
        System.out.println("\nLOOP and Dequeue from REAR until empty: ");
        while(!deque.isEmpty())
        {  System.out.print(deque.dequeueRear()+" ");
        }
        System.out.println("\nDeque is: "+deque.toString());
        System.out.println("Enqueue at REAR: 1,2,3 and at FRONT: 4");
        deque.enqueueRear("1");
        deque.enqueueRear("2");
        deque.enqueueRear("3");
        deque.enqueueFront("4");
        System.out.println("Deque is: "+deque.toString());
        System.out.println("LOOP and Dequeue from FRONT until empty:");
        while(!deque.isEmpty())
        {  System.out.print(deque.dequeueFront()+" ");
        }
        System.out.println("\nDeque is: "+deque.toString());
        System.out.println("This should cause an Exception (ON PURPOSE)!! Dequeue from REAR ");
        try{
            System.out.println(deque.dequeueRear());
        }catch(NoSuchElementException e)
        {   System.out.println("EXCEPTION!!: "+e);
        }
        System.out.println("----------TESTING COMPLETE---------");
    }
}
