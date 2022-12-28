package deque;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author sehall
 * @param <E>
 */
public class LinkedDeque<E> implements DequeADT<E>{

    private Node<E> firstNode;
    private Node<E> lastNode;
    private int numElements;
    
    public LinkedDeque()
    {
        numElements = 0;
        firstNode = null;
        lastNode = null;
    }
    public LinkedDeque(Collection<? extends E> c, boolean atRear)
    {   this();
        Iterator<? extends E> it = c.iterator();
        while(it.hasNext())
        {
            if(atRear)
                 enqueueRear(it.next());
            else enqueueFront(it.next());
        }
    }
    
    
    @Override
    public void enqueueRear(E element) 
    {
        Node<E> newNode = new Node<>(element);
        if(lastNode == null)
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
        numElements++;
    }
    @Override
    public E dequeueFront() throws NoSuchElementException
    {   if(firstNode == null)
            throw new NoSuchElementException("Deque Appears to be Empty");
        
        Node<E> removeNode = firstNode;
        firstNode = firstNode.next;
        removeNode.next = null;
        if(firstNode == null)
            lastNode = null;
        else
            firstNode.prev = null;
        
        numElements--;
        return removeNode.element;
    }

    @Override
    public E first() throws NoSuchElementException {
        if(firstNode == null)
            throw new NoSuchElementException("Deque Appears to be Empty");
        else
            return firstNode.element;
    }

    @Override
    public void enqueueFront(E element) 
    {
        Node<E> newNode = new Node(element);
        if(firstNode == null)
        {
            firstNode = newNode;
            lastNode = newNode;
        }
        else
        {
            newNode.next = firstNode;
            firstNode.prev = newNode;
            firstNode = newNode;
        }
        numElements++;
    }

    @Override
    public E dequeueRear() throws NoSuchElementException {
        if(firstNode == null)
            throw new NoSuchElementException("Deque is Empty, cannot dequeueRear");
        
        Node<E> removeNode = lastNode;
        lastNode = lastNode.prev;
        removeNode.prev = null;
        if(lastNode == null)
            firstNode = null;
        else
            lastNode.next = null;
        
        numElements--;
        return removeNode.element;
    }

    @Override
    public E last() throws NoSuchElementException {
        if(lastNode == null)
            throw new NoSuchElementException("Deque is Empty");
        else
            return lastNode.element;
    }

    @Override
    public boolean isEmpty() {
        return (numElements == 0);
    }

    @Override
    public int size() {
        return numElements;
    }
    @Override
    public void clear()
    {   firstNode = null;
        lastNode = null;
        numElements = 0;
    }
    @Override
    public Iterator<E> iterator() {
        return new DequeIterator();
    }
    @Override
    public String toString()
    {
        String s = "[";
        Node<E> current = firstNode;
        
        while(current != null)
        {   s+=current.element;
            if(current.next != null)
                s+=",";
            current=current.next;
        }
        s+="]";
        return s;
    }
    
    
    private class Node<E>
    {
        public E element;
        public Node<E> next;
        public Node<E> prev;
        
        public Node(E element)
        {
            this.element = element;
            next = null;
            prev = null;
        }
    }
    private class DequeIterator implements Iterator<E>
    {
        private Node<E> current;
        
        public DequeIterator()
        {   current = firstNode;
        }
        @Override
        public boolean hasNext() {
            return current != null; 
        }

        @Override
        public E next() {
            if(current == null)
                throw new NoSuchElementException("No Next Element"); 
            else
            {
                E element = current.element;
                    current = current.next;
                return element;
            }
        }
    }
}
