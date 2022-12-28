package linkedRRSet;

//Seth Hall LinkedRRSet

import java.util.Collection;
import java.util.Set;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class LinkedRRSet<E extends Comparable> extends LinkedSet<E>
{
   public LinkedRRSet(Collection<? extends E> c)
   {
      super(c);
   }
   public LinkedRRSet()
   {
      super();
   }
   public boolean add(E o)
   {
      Node<E> newNode = new Node<E>(o);
      if (firstNode == null)
      {  firstNode = newNode;
         numElements++;
         return true;
      }
      else
      {
         //Node<E> newNode = new Node<E>(o);
         Node<E> current = firstNode;
         Node<E> previous = null;
         boolean found = false;

         while (current != null && !found)
         {
            if(o.compareTo(current.element) > 0)
            {  previous = current;
               current = current.next;
            }
            else if (o.compareTo(current.element) == 0)
            {
               return false;
            }
            else
            {
               found = true;
            }
         }
         if (previous == null)
         {   newNode.next = current;
             firstNode = newNode;
         }
         else if(current == null)
         {  
            newNode.next = current;
            previous.next = newNode;
         }
         else
         {  
            newNode.next = current;
            previous.next = newNode;
         }
         numElements++;
         return true;
      }
   }
   public Set<E> retain(E start, E end)
   {  if(start != null && end !=null && start.compareTo(end) >= 0)
         throw new IllegalArgumentException("Start and End not a valid range!");
      Set<E> removedset = new LinkedRRSet<>();
      boolean foundStart = false;
      boolean foundStop = false;
      Node<E> currentNodeStart = firstNode;

      while(currentNodeStart != null && !foundStart)
      {  if(start == null || currentNodeStart.element.compareTo(start)==0)
         {   foundStart = true;
         }
         else
         {  removedset.add(currentNodeStart.element);
            currentNodeStart = currentNodeStart.next;
         }
      }
      if(!foundStart)
          throw new NoSuchElementException("Value: "+start+" NOT IN THE SET ");
      
      Node<E> currentNode = currentNodeStart;
      Node<E> initial = null;
      while(currentNode != null)
      {  if(!foundStop && (end == null || currentNode.element.compareTo(end) != 0))
         {  initial = currentNode;
            currentNode = currentNode.next;
         }
         else
         {  foundStop = true;
            removedset.add(currentNode.element);
            currentNode = currentNode.next;
         }
      }
      if(!foundStop && end != null)
          throw new NoSuchElementException("Value: "+end+" NOT IN THE SET ");
      
      firstNode = currentNodeStart;
      if(initial != null)initial.next = null;
      return removedset;
   }
    public Set<E> remove(E start, E end)
    {   if(start != null && end !=null && start.compareTo(end) >= 0)
          throw new IllegalArgumentException("Start and End not a valid range!");
        Node<E> currentNode = firstNode;
        Node<E> removeStart = firstNode;
        Node<E> prevStartNode = null;
        Set<E> removedset = new LinkedRRSet<>();
     
        boolean foundStart = false;
        boolean foundEnd = false;
        
        //find the start node
        while(removeStart != null && !foundStart)
        {
            if(start == null || removeStart.element.compareTo(start) == 0)
            {
                foundStart = true;
            }
            else
            {   prevStartNode = removeStart; 
                removeStart = removeStart.next;
            }
        }
        if(!foundStart)
            throw new NoSuchElementException("Value: "+start+" NOT IN THE SET ");
        //now find the end and add elements to remove set!
        Node<E> removeEnd = removeStart;
        
        while(removeEnd != null && !foundEnd)
        {
            
            if(end != null && removeEnd.element.compareTo(end) == 0)
            {
                foundEnd = true;
            }
            else
            {   removedset.add(removeEnd.element);
                removeEnd = removeEnd.next;
            }
        }
        if(!foundEnd && end != null)
            throw new NoSuchElementException("Value: "+end+" NOT IN THE SET ");
        
        if(prevStartNode == null)
            firstNode = removeEnd;
        else
        {
            prevStartNode.next = removeEnd;
        }
        
        return removedset;
   }
   public static void main(String[] args)
   {
      Collection<String> letters = new ArrayList<String>();
      letters.add("C");
      letters.add("B");
      letters.add("A");
      letters.add("D");
      letters.add("E");
      letters.add("G");
      letters.add("I");
      letters.add("H");
      letters.add("F");
      LinkedRRSet<String> lrrs = new LinkedRRSet<String>(letters);
      System.out.println("SET CONTAINS "+lrrs.toString());

     // try
      {
        System.out.println("REMOVING: "+lrrs.remove("B","G"));
      }
    //  catch(NoSuchElementException e)
      {
         // System.out.println(e);
      }
      System.out.println("\nSET NOW CONTAINS "+lrrs.toString());


        //System.out.println("REMOVING "+lrrs.remove(null,null));
      //System.out.println("SET NOW CONTAINS "+lrrs.toString());
   }
}
