package dualstack;


import java.util.Collection;
import java.util.NoSuchElementException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Seth
 */
public class LinkedDualStack<E> implements DualStack<E>{

    private int numElements;
    private Node<E> top;
    private Node<E> bottom;
    
    public LinkedDualStack()
    {   
        numElements = 0;
        top = null;
        bottom = null;
    }
    public LinkedDualStack(Collection<? extends E> coll, boolean top)
    {   this();
        for(E element:coll)
        {    if(top) this.pushTop(element);
             else this.pushBottom(element);
        }
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
    public E peekTop() {
        if(numElements == 0)
            throw new NoSuchElementException("No element to peek from top");
        return top.element;
    }

    @Override
    public E peekBottom() {
        if(numElements == 0)
            throw new NoSuchElementException("No element to peek from bottom");
        return bottom.element;
    }

    @Override
    public E popTop() {
        if(numElements == 0)
            throw new NoSuchElementException("No element to pop from top");
        
        E element = top.element;
        top = top.prev;
        
        if(top == null)
            bottom = null;
        numElements--;
        return element;
    }

    @Override
    public E popBottom() {
        if(numElements == 0)
            throw new NoSuchElementException("No element to pop from bottom");
        
        E element = bottom.element;
        bottom = bottom.next;
        
        if(bottom == null)
            top = null;
        
        numElements--;
        return element;
    }

    @Override
    public void pushTop(E element) {
       
        Node<E> newNode = new Node<>(element); 
        if(top == null)
        {   top = newNode;
            bottom = newNode;
        }
        else
        {
            newNode.prev = top;
            top.next = newNode;
            top = newNode;
        }
        numElements++;
    }

    @Override
    public void pushBottom(E element) {       
        Node<E> newNode = new Node<>(element); 
        if(bottom == null)
        {   top = newNode;
            bottom = newNode;
        }
        else
        {
            newNode.next =bottom;
            bottom.prev = newNode;
            bottom = newNode;
        }
        numElements++;
    }
    
    private class Node<E>
    {   public E element;
        public Node<E> next;
        public Node<E> prev;
        
        public Node(E element)
        {   this.element = element;
            next = null;
            prev = null;
        }
    }
    
    @Override
    public String toString()
    {   String output = "bottom->[";
        Node<E> current = bottom;
        while(current != null)
        {  output+=current.element;  
           current = current.next;
           if(current != null)
               output+=",";
        }    
        output+="]<-top";
        return  output;
    }
    
}
