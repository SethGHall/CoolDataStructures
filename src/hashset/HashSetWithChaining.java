package hashset;


import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Seth
 * @param <E>
 */
public class HashSetWithChaining<E> implements Set<E>{

    private Node<E>[] hashTable; 
    private int numElements; 
    private float LOAD_FACTOR;
    private int initialCap;
    
    
    public HashSetWithChaining(int capacity, float loadFactor)
    {
         hashTable = new Node[capacity];
         this.LOAD_FACTOR = loadFactor;
         this.initialCap = capacity;
         numElements=0;
    }
    
    public HashSetWithChaining()
    {   this(10,0.75f);
        
    }
    public HashSetWithChaining(Collection<? extends E> c)
    {   this(c.size()*2, 0.75f);
        this.addAll(c);
    }
    
    @Override
    public int size() {
        return numElements;
    }

    @Override
    public boolean isEmpty() {
        return (numElements == 0);
    }

    @Override
    public boolean contains(Object o) {
        int hashCode  = Math.abs(o.hashCode())%hashTable.length;
        Node<E> current = hashTable[hashCode];
        boolean found = false;
        while(!found && current != null)
        {
           if(current.element.equals(o))
              found = true;
           else
              current = current.next;
        }
        return found;
    }

    @Override
    public Iterator<E> iterator() {
        return new HashTableIterator<>(hashTable);
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[size()];
        Iterator<E> it = iterator();
        for(int i=0;i<array.length;i++)
            array[i] = it.next();
        return array;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        a = (T[])new Object[size()];
        Iterator<E> it = iterator();
        for(int i=0;i<a.length;i++)
            a[i] = (T)it.next(); 
        return a;
    }

    @Override
    public boolean add(E e) {
        if(contains(e))
            return false;
        if((float)numElements/(float)hashTable.length >= LOAD_FACTOR)
            expandCapacity();
        int hashCode  = Math.abs(e.hashCode())%hashTable.length;
        Node<E> newNode = new Node<>(e);
        newNode.next = hashTable[hashCode];
        hashTable[hashCode] = newNode;
        numElements++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        int hashCode  = Math.abs(o.hashCode())%hashTable.length;
        if(hashTable[hashCode].element.equals(o))
        {    hashTable[hashCode] = hashTable[hashCode].next;
             numElements--;
             return true;
        }
        else
        {   Node<E> current = hashTable[hashCode];
            while(current.next != null)
            {   if(current.next.element.equals(o))
                {   current.next = current.next.next;
                    numElements--;
                    return true;
                }
                current = current.next;
            }
            return false;
        }
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for(Object e:c)
        {  if(!this.contains(e))
                return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean success = true;
        for(E o : c)
        {    boolean x = this.add(o);
             if(!x) success = false;
        }
        return success;
    }

   @Override
    public boolean removeAll(Collection<?> c) {
        boolean removedAll = true;
        for(Object o : c)
        {   boolean removed = remove((E)o);
            if(!removed)
                removedAll = false;
        }
        return removedAll;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean found = false;
        
        Set<E> toRemoveSet = new HashSetWithChaining<>();
        Iterator<E> it = iterator();
        
        while(it.hasNext())
        {   E e = it.next();
            if(c.contains(e))
            {   
                found = true;
            }
            else
            {   remove(e);
            }
        }
        return found;
    }
    @Override
    public void clear() {
        hashTable = new Node[initialCap];
        numElements=0;
    }
    @Override
   public String toString()
   {  String s = "";
      for(int i=0;i<hashTable.length;i++)
      {  Node<E> current = hashTable[i];
         s+="row "+i+": ";
         while(current != null)
         {  s+=current.element;
            if(current.next!=null)
                s+="-->";
            current = current.next;
         }
         s+="\n";
      }
      return s;
   }
    private void expandCapacity()
    {
       System.out.println("LOAD FACTOR EXCEEEDED, EXPANDING CAPACITY");
       Node<E>[] oldtable = hashTable;
       hashTable= new Node[oldtable.length*2];
       numElements = 0;
       for(int i=0;i<oldtable.length;i++)
       {
           Node<E> current = oldtable[i];
           while(current != null)
           {
               add(current.element);
               current = current.next;
           }
       }
   }

    private class HashTableIterator<E> implements Iterator<E>{
        private Node<E>[] hashtable;
        private Node<E> current;
        private int row;
        public HashTableIterator(Node<E>[] hashtable) {
            this.hashtable = hashtable;
            row = 0;
            current = null;
            findNextRow();
            
        }
        private void findNextRow()
        {   while(current == null && row < hashtable.length)
            {   current = hashtable[row]; 
                row++;
            }
        }       
        @Override
        public boolean hasNext(){
            
            return (current!=null);
                
        }

        @Override
        public E next() {
            E n = current.element;
            current = current.next;
            if(current == null)
            {   findNextRow();
            }
            return n;
        }
        
    }
    private class Node<E>
    {   public E element;
        public Node<E> next;
        public Node(E element)
        {   this.element = element;
            this.next = null;
        }
    }
}
