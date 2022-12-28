/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package knapsack;

import java.util.NoSuchElementException;
import java.util.Random;

/**
 *
 * @author Seth
 */
public class LinkedKnapSack<E> implements KnapSack<E>{

    private int numElements;
    private int capacity;
    private int maxCapacity;
    private Node<E> firstNode;
    
    public LinkedKnapSack(int maxCapacity)
    {   
        this.maxCapacity = maxCapacity;
        capacity = 0;
        firstNode = null;
        numElements = 0;
    }
    public LinkedKnapSack()
    {   this(10);
    }
    @Override
    public boolean add(E element) {
        return add(element,1);
    }
    @Override
    public boolean add(E element, int weight) {
        
        if(weight+capacity <= maxCapacity)
        {   Node<E> current = firstNode;
            boolean found = false;
            while(current != null && !found)
            {   if(current.element.equals(element))
                {   found = true;
                    current.weight += weight;
                }
                else current = current.next;
            }        
            if(!found)
            {
                Node<E> newNode = new Node<>(element, weight);
                newNode.next = firstNode;
                firstNode = newNode;
                numElements++;
            }
            capacity += weight;
            return true;
        }
        else return false;
        
    }

    @Override
    public boolean removeAll(E element) {
        
        return remove(element, currentQuantity());
//        Node<E> toRemove = null;
//        boolean found = true;
//        if(firstNode != null && firstNode.element.equals(element))
//        {   toRemove = firstNode;
//            firstNode = firstNode.next;
//           
//        }
//        else
//        {   Node<E> current = firstNode;
//            while(current.next != null && toRemove==null)
//            {   if(current.next.element.equals(element))
//                {   toRemove = current.next;
//                    current.next = current.next.next;
//                }
//                else current = current.next; 
//            }
//        }
//        if(toRemove != null){
//            numElements--;
//            capacity -= toRemove.weight;
//            return true;
//        }
//        return false;
    }

    @Override
    public boolean remove(E element, int weight) 
    {
        
        boolean found = false;

        if(firstNode != null && firstNode.element.equals(element))
        {   capacity -= Math.min(weight, firstNode.weight);
            firstNode.weight-=weight;
            if(firstNode.weight <= 0)
            {   firstNode = firstNode.next;
                numElements--;
            }
            found = true;
        }
        else
        {   Node<E> current = firstNode;
            while(current.next != null && !found)
            {
                if(current.next.element.equals(element))
                {   capacity -= Math.min(weight, current.next.weight);
                    current.next.weight-=weight;
                    if(current.next.weight <= 0)
                    {   current.next = current.next.next;
                        numElements--;
                    }
                    found = true;
                }
                else current = current.next;
            }
            
        }
        return found;
    }

    @Override
    public boolean contains(E element) {
        Node<E> current = firstNode;
        while(current != null)
        {   
            if(current.element.equals(element))
                return true;
            current = current.next;
        }
        return false;
    }

    @Override
    public E grab() throws NoSuchElementException
    {
        if(firstNode == null)
            throw new NoSuchElementException("Knapsack is empty, nothing to grab..");
        Random rand = new Random();
        int x = rand.nextInt(capacity);  
        int weightAcc = 0;
        
        Node<E> current = firstNode;
        E element = firstNode.element;
        while(current != null)
        {
            weightAcc += current.weight;
            if(weightAcc > x)
            {
                element = current.element;
                current = null;
            }
            else 
                current = current.next;
        }
        return element;
    }

    @Override
    public int numberUniqueItems() {
        return numElements;
    }

    @Override
    public int currentQuantity() {
        return capacity;
    }



    private class Node<E>
    {
        public E element;
        public Node<E> next;
        public int weight;
        
        public Node(E element, int weight)
        {   this.element = element;
            this.weight = weight;
            next = null;
        }
        
        
    }
    public String toString()
    {
        Node<E> current = firstNode;
        String s = "{";
        while(current != null)
        {   s+= current.element+":"+current.weight;
            if(current.next != null)
                s+=",";
            current = current.next;
        }
        return s+"}";
    }
}
