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
 * @param <E>
 */
public class LinkedBag<E> implements Bag<E>{

    private int numItems;
    private int maxCapacity;
    private Random rand;
    public Node<E> firstNode;
    public Node<E> lastNode;
    
    public LinkedBag()
    {
        this(10);
    }
    public LinkedBag(int capacity)
    {   maxCapacity = capacity; 
        numItems = 0;
        rand = new Random();
        firstNode = null;
        lastNode = null;
    }
    
    
    
    @Override
    public boolean add(E item) {
        if(numItems == maxCapacity)
            return false;
        else
        {
            Node<E> newNode = new Node<>(item);
            if(firstNode == null)
            {
                firstNode = newNode;
                lastNode = newNode;
            }
            else
            {
                newNode.prev = lastNode;
                lastNode.next = newNode;
                lastNode = newNode;
            }
            numItems++;
            return true;
        }
    }

    @Override
    public E grab() {
        if(numItems == 0)
            throw new NoSuchElementException("Nothing in bag to Grab!");
        int num = rand.nextInt(numItems);
        Node<E> current = firstNode;
        for(int i=0;i<num;i++)
            current = current.next;
        
        return current.element;
    }

    @Override
    public boolean remove(E item) {
        if(numItems == 0)
            return false;
        
        Node<E> current = firstNode;
        boolean found = false;
        while(current!=null & !found)
        {
            if(current.element.equals(item))
            {
                if(current.prev != null)
                {    current.prev.next = current.next;
                }
                else
                    firstNode = firstNode.next;
                if(current.next != null)
                    current.next.prev = current.prev;
                else
                    lastNode = lastNode.prev;
                
                current.next = null;
                current.prev = null;
                numItems--;
                found = true;
            }
            else
            {
                current = current.next;
            }
        }
        
        return found;
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
        firstNode = null;
        lastNode = null;
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
        return new BagIterator<>(firstNode);
    }

    @Override
    public E[] toArray() {
        if(numItems == 0)
            throw new NoSuchElementException("No elements to put in Array");
        E[] newArray = (E[])new Object[numItems];
        
        Node<E> current = firstNode;
        int i=0;
        while(current != null)
        {   newArray[i] = current.element;
            i++;
        }
        return newArray;
    }
    
    public String toString()
    {
        Node<E> current = firstNode;
        String s = "{";
        while(current != null)
        {
            s+=current.element;
            if(current.next != null)
                s+=",";
            current = current.next;
        }
        return s+="}";
    }
    
    private class Node<E>
    {
        public Node<E> next;
        public Node<E> prev;
        public E element;
        
        public Node(E element)
        {   this.element = element;
            next = null;
            prev = null;
        }
        
    }
    
    private class BagIterator<E> implements Iterator<E>{
        
        private Node<E> current;
        
        public BagIterator(Node<E> current)
        {
            this.current = current;
        }  
        @Override
        public boolean hasNext() {
            return (current != null);
        }

        @Override
        public E next() {
            E element = current.element;
            current = current.next;
            return element;
        }
        
    }
}
