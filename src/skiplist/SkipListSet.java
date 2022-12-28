package skiplist;

/******************************************************************************************
PROGRAM NAME:   THE SKIP LIST SET OF MISERY AND DISPAIR
FILE:           SkipListSet.java
AUTHER:         Seth Hall
DESCRIPTION:    This stores elements in a skip list set which user can contiguously add
                or remove elements from it.
*******************************************************************************************/
import java.util.AbstractSet;         //useful imports for this program
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

public class SkipListSet<E extends Comparable<E>> extends AbstractSet<E>
{
    private int numElements;
    private int numLevels;
    private Node<E> firstNode;    //points to first node

    //constructor calls super class and sets up two nodes with values null
    //at beginning and end of list also initialises fields
    public SkipListSet()
    {
        super();
        numElements = 0;
        numLevels = 1;
        firstNode = new Node<E>(null);
        firstNode.next = new Node<E>(null);
    }
    //constructor that accepts a collection, loops through the elements adding
    //them to the list
    public SkipListSet(Collection<? extends E> c)
    {
        this();
        for (E element:c)
            add(element);
    }
    //this add method add the element at a random generated number of levels in
    //the skip list set and returns true if element not in the list and it was
    //added, if it already in list it could still generate adding that number on more
    //levels but will return false,
    
    @Override
    public boolean add(E o)
    {   Random generator = new Random();
        int levelCount = 0;
        boolean rand;
        do
        {   rand = generator.nextBoolean();   //generate boolean value
            levelCount++;
            //if levelcount greater than levels create a new level
            if(levelCount > numLevels)
            {   Node<E> newNode = new Node<>(null);
                newNode.next = new Node<>(null);
                newNode.down = firstNode;
                Node<E> currentNode = firstNode;
                while(currentNode.next != null)
                    currentNode = currentNode.next;
                newNode.next.down = currentNode;
                firstNode = newNode;
                numLevels++;
            }
        }while(rand);  
        
        Node<E> previouslyAdded = null;
        Node<E> currentNode  = firstNode;
        numElements++;
        boolean addSuccess = true;
        int currentLevel = numLevels;
        while(currentNode != null && addSuccess)
        {
            if(currentNode.element != null && currentNode.element.equals(o))
            {   if(previouslyAdded != null)
                    previouslyAdded.down = currentNode;
                numElements--;
                addSuccess = false;
            }
            else if(currentNode.next.element == null || o.compareTo(currentNode.next.element) < 0)
            {   //Need to chain in but only on desired level
                if(currentLevel <= levelCount)
                {   Node<E> newNode = new Node<>(o);
                    newNode.next = currentNode.next;
                    currentNode.next = newNode;

                    if(previouslyAdded != null)
                        previouslyAdded.down = newNode;

                    previouslyAdded = newNode;
                }
                currentLevel --;
                currentNode = currentNode.down;
               
            }
            else
                currentNode = currentNode.next;
            
        }
        return addSuccess;   //return true if added successfully and didnt already exist
    }
    

    //removes all instances of this element in the skip list
    public boolean remove(E o)
    {
        Node<E> currentNode = firstNode;
        boolean found = false;
        //loop down list until null or found
        while(currentNode !=null  && !found)
        {
            //loop through list until null or element is smaller than currentNodes next element
            while(currentNode.next.element != null && (currentNode.next.element.compareTo(o)<0))
                currentNode = currentNode.next;
            //if next element is the element to be removed set found flag true else move down
            if(currentNode.next.element != null && currentNode.next.element.compareTo(o) == 0)
                found = true;
            else
                currentNode=currentNode.down;
        }
        //if the element is found remove it
        if(found)
        {
            numElements--;               //decrease number of elements
            //loops until current node is null and moves down the list
            while(currentNode != null)
            {
                currentNode.next = currentNode.next.next;
                currentNode = currentNode.down;
                if(currentNode != null)
                {
                    //loops across list until it finds the element or until null
                    while(currentNode.next != null && currentNode.next.element.compareTo(o)<0)
                        currentNode = currentNode.next;
                }
            }
            return true;
        }
        else
            return false;
    }
    //returns an iterator to iterate through bottom level of the skip list
    public Iterator<E> iterator()
    {
        return new LinkedIterator<E>(firstNode);
    }
    //returns the amount of elements there are in this collection
    public int size()
    {
        return numElements;
    }
    //clears all elements from the skip list
    public void clear()
    {
        firstNode = new Node<E>(null);
        firstNode.next = new Node<E>(null);
        numLevels = 1;
        numElements = 0;
    }
    //returns true if an element o is in the skip list set
    public boolean contains(E o)
    {
        Node<E> currentNode = firstNode;
        boolean found = false;
        while(currentNode !=null  && !found)
        {
            while(currentNode.next.element != null && (currentNode.next.element.compareTo(o)<0))
                currentNode = currentNode.next;

            if(currentNode.next.element != null && currentNode.next.element.compareTo(o)==0)
                found = true;
            else
                currentNode=currentNode.down;
        }
        return found;
    }
    //toString method that prints out the elements at each level including the
    //null elements at each end on each level from 1 to n
    public String toString()
    {
        Node<E> startNode = firstNode;
        String str="";
        int levelCount = numLevels;
        while(startNode!= null)
        {
            Node<E> currentNode = startNode;
            str+="level_" + levelCount-- + " = [ ";
            while(currentNode!= null)
            {
                str += currentNode.element+" ";
                currentNode = currentNode.next;
            }
            str+="]   \n";
            startNode = startNode.down;
        }
        return str;
    }
    //inner class for handling the nodes
    private class Node<E>
    {
        public E element;
        public Node<E> next;
        public Node<E> down;

        public Node(E element)
        {
            this.element = element;
            next = null;
            down = null;
        }
    }
    //class for handling the skip list iterating through bottom nodes
    private class LinkedIterator<E> implements Iterator<E>
    {
        private Node<E> nextNode; // next node to use for the iterator

        // constructor which accepts a reference to first node in list
        // and prepares an iterator which will iterate through the
        // bottom level of the skip list
        public LinkedIterator(Node<E> firstNode)
        {
            nextNode = firstNode; // start with first node in list
            //loop until it gets to bottom of list
            while(nextNode.down != null)
                nextNode = nextNode.down;
            nextNode = nextNode.next;
        }
        // returns whether there is still another element
        public boolean hasNext()
        {
            return (nextNode.element!=null);
        }

        // returns the next element or throws a NoSuchElementException
        // it there are no further elements
        public E next() throws NoSuchElementException
        {
            if (!hasNext())
                throw new NoSuchElementException();
            E element = nextNode.element;
            nextNode = nextNode.next;
            return element;
        }
        // remove method not supported by this iterator
        public void remove() throws UnsupportedOperationException
        {
            throw new UnsupportedOperationException();
        }
    }
    //testing class that tests skip lists add,remove,size,iterator,toString and clear
    //methods in each case
    public static void main(String[] args)
    {
        System.out.println("=============SKIP LIST SET================");
        SkipListSet<Integer> skippy = new SkipListSet<>();
        for(int i=30;i>0;i--)
            skippy.add(new Integer(i));
        System.out.println("Size of Skip list is "+skippy.size());
        System.out.println(skippy.toString());
        System.out.println("");
        System.out.println("ITERATE THROUGH ELEMENTS");
        Iterator<Integer> iterator = skippy.iterator();
        while(iterator.hasNext())
            System.out.print(iterator.next()+" ");
        System.out.println("\n");
        System.out.println("REMOVE 12,2,0");
        if(skippy.remove(new Integer(12)))
            System.out.println("REMOVING 12 \n"+skippy.toString());
        else
            System.out.println("Didnt Remove 12");
        if(skippy.remove(new Integer(2)))
            System.out.println("REMOVING 2 \n"+skippy.toString());
        else
            System.out.println("Didnt Remove 2");
        if(skippy.remove(new Integer(0)))
            System.out.println("REMOVING 0 \n"+skippy.toString());
        else
            System.out.println("Didnt Remove 0, cause not in the list");
            System.out.println("REMOVE 19,29,12");
        if(skippy.remove(new Integer(19)))
            System.out.println("REMOVING 19 \n"+skippy.toString());
        else
            System.out.println("Didnt Remove 19");
        if(skippy.remove(new Integer(29)))
            System.out.println("REMOVING 29 \n"+skippy.toString());
        else
            System.out.println("Didnt Remove 29 " + "because not in list");
        if(skippy.remove(new Integer(12)))
            System.out.println("REMOVING 12 \n"+skippy.toString());
        else
            System.out.println("Didnt Remove 12 " + "because not in list");
        skippy.add(new Integer(2));
        System.out.println("");
        System.out.println("ADDING 2");
        System.out.println(skippy.toString());
        if(skippy.contains(new Integer(2)))
            System.out.println("SET CONTAINS 2");
        else
            System.out.println("SET DOESNT CONTAIN 2");
        if(skippy.contains(new Integer(12)))
            System.out.println("SET CONTAINS 12");
        else
            System.out.println("SET DOESNT CONTAIN 12");
        System.out.println("Size of Skip list is "+skippy.size());
        System.out.println("adding 0,19,2");
        if(skippy.add(new Integer(0)))
            System.out.println("ADDED 0");
        else
            System.out.println("ALREADY CONTAINS 0");
        if(skippy.add(new Integer(19)))
            System.out.println("ADDED 19");
        else
            System.out.println("ALREADY CONTAINS 19");
        if(skippy.add(new Integer(2)))
            System.out.println("ADDED 2");
        else
            System.out.println("ALREADY CONTAINS 2");
        System.out.println("");
        System.out.println(skippy.toString());
        System.out.println("Clearing the list");
        skippy.clear();
        System.out.println(skippy.toString());
    }
}
