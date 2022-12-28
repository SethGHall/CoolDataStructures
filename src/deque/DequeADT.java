package deque;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.Iterator;

/**
 *
 * @author sehall
 */
interface DequeADT<E> {
    public void enqueueRear(E element);
    
    public void enqueueFront(E element);
    
    public E dequeueRear(); 
     
    public E dequeueFront();
    
    public E first();
    
    public E last();
    
    public boolean isEmpty();
    
    public int size();
    
    public void clear();
   
    public Iterator<E> iterator();
}
