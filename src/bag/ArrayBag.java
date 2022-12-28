/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bag;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

/**
 *
 * @author sehall
 */
public class ArrayBag<E> implements Bag<E> {

    private int numItems;
    private int maxCapacity;
    private E[] elements;
    private Random rand;
    
    public ArrayBag()
    {
        this(10);
    }
    public ArrayBag(int capacity)
    {   maxCapacity = capacity; 
        numItems = 0;
        elements = (E[])new Object[capacity];
        rand = new Random();
    }
    
    
    @Override
    public boolean add(E item) {
        if(numItems < maxCapacity)
        {
            elements[numItems] = item;
            numItems++;
            return true;
        }
        else return false;
    }

    @Override
    public E grab() {
        if(numItems == 0)
            throw new NoSuchElementException("Noting in bag to Grab!");
        return elements[rand.nextInt(numItems)];
    }

    @Override
    public boolean remove(E item) {
        boolean found = false;
        for(int i=0;i<numItems;i++)
        {
            if(item.equals(elements[i]))
            {
                elements[i] = null;
                elements[i] = elements[numItems-1];
                elements[numItems-1] = null;
                numItems--;
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
       return numItems;
    }

    @Override
    public int capacityRemaining() {
        return maxCapacity-numItems;
    }

    @Override
    public void clear() {
        elements = null;
        elements = (E[])new Object[maxCapacity];
    }

    @Override
    public boolean isEmpty() {
        return (numItems == 0);
    }

    @Override
    public boolean isFull() {
        return (maxCapacity == numItems);
    }

    @Override
    public Iterator<E> iterator() {
        return new BagIterator<>(elements);
    }

    @Override
    public E[] toArray() {
        if(numItems == 0)
            throw new NoSuchElementException("No elements to put in Array");
        E[] newArray = (E[])new Object[numItems];
        for(int i=0;i<numItems;i++)
            newArray[i] = elements[i];
        return newArray;
    }
    
    @Override
    public String toString()
    {
        String output = "{";
        for(int i=0;i<numItems;i++)
        {   output+=elements[i];
            if(i<numItems-1)
                output+=",";
        }
        return output+"}";
    }
    
    private class BagIterator<E> implements Iterator<E>{
        
        private E[] elements;
        private int iterationCount;
        public BagIterator(E[] elements)
        {
            this.elements = elements;
            iterationCount = 0;
        }  
        @Override
        public boolean hasNext() {
            return (elements[iterationCount] != null);
        }

        @Override
        public E next() {
            return elements[iterationCount++];
        }
        
    }
    
}
