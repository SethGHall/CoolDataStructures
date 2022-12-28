/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package selforganizinglist;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 *
 * @author sehall
 */
public class SelfOrganizingArrayList<E> implements List<E>{
    private E[] elements;
    private int numElements;
    private int[] numAccesses;
    private int INITIAL_CAP = 5;
    
    public SelfOrganizingArrayList()
    {   elements = (E[])(new Object[INITIAL_CAP]); // unchecked
        numAccesses = new int[INITIAL_CAP];
        numElements = 0;
    }
    public SelfOrganizingArrayList(Collection<? extends E> coll)
    {   this();
        for(E item:coll)
            add(item);
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
        boolean found = false;
        for(int i =0;i<numElements && !found;i++)
        {
            if(o.equals(elements[i]))
            {
                found = true;
                numAccesses[i]++;
                while(checkSwap(i,elements[i]))
                    i--;
            }
        }
        return found;
    }
    
    private boolean checkSwap(int index,E elementToSwap)
    {   if(index > 0 && numAccesses[index-1] <= numAccesses[index])
        {
            elements[index] = elements[index-1];
            elements[index-1] = elementToSwap;
            
            int accesses = numAccesses[index];
            numAccesses[index] = numAccesses[index-1];
            numAccesses[index-1] = accesses;
            
            return true;
        }
        else return false;
    }

    @Override
    public Iterator<E> iterator() 
    {  return listIterator();
    }

    @Override
    public Object[] toArray() {
        if(numElements == 0)
            throw new NoSuchElementException(" No elements in collection to add to Array ");
        
        Object[] oArray = new Object[numElements];
        for(int i=0;i<oArray.length;i++)
            oArray[i] = elements[i];
        return oArray;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if(numElements == 0)
            throw new NoSuchElementException(" No elements in collection to add to Array ");
        
        int length = Math.min(a.length,numElements);
        for(int i=0;i<length;i++)
            a[i] = (T)elements[i];
        return a;
    }

    @Override
    public boolean add(E e) {
        if(numElements >= elements.length)
            expandCapacity();
        
        elements[numElements] = e;
        numAccesses[numElements] = 1;
        numElements++;
        return true;
    }
    private void expandCapacity()
    {   E[] largerArray =(E[])(new Object[elements.length*2]);//unchecked
        int[] largerAccesses = new int[elements.length*2];
        // copy the elements array to the largerArray
        for (int i=0; i<numElements; i++){
            largerArray[i] = elements[i];
            largerAccesses[i] = numAccesses[i];
        }
        elements = largerArray;
        numAccesses = largerAccesses;
    }
    @Override
    public boolean remove(Object o) {
        boolean found = false;
        int index = -1;
        for(int i=0; i<numElements && !found;i++)
        {
            if(elements[i].equals(o))
            {   index = i;
                found = true;
            }
        }
        if(found)
        {   remove(index);
        }
        return found;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        boolean found = true;
        
        for(Object element:c)
        {   boolean addFound = contains((E)element);
            if(!addFound)
                found = false;
        }
        return found;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for(E element:c)
        {   add(element);
        }
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        return addAll(c);
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
        E[] newElements = (E[])(new Object[elements.length]); // unchecked
        int[] newNumAccesses = new int[numAccesses.length];
        
        boolean found = false;
        int iterations = numElements-1;
        for(int index=iterations;index>=0;index--)
        {
            if(c.contains(elements[index]))
            {   found = true;
            }
            else
            {   remove(index);
            }
        }
        return found;
    }

    @Override
    public void clear() {
        elements = (E[])(new Object[INITIAL_CAP]); // unchecked
        numAccesses = new int[INITIAL_CAP];
        numElements = 0;
    }

    @Override
    public E get(int index) {
        if(index > size() || index < 0)
            throw new IndexOutOfBoundsException(index+" out of bounds for list size = "+numElements);
        
        return elements[index];
    }

    @Override
    public E set(int index, E element) {
        if(index > size() || index < 0)
            throw new IndexOutOfBoundsException(index+" out of bounds for list size = "+numElements);
        
        elements[index] = element;
        return elements[index];
    }

    @Override
    public void add(int index, E element) {
        add(element);
    }

    @Override
    public E remove(int index) {
        if(index >= numElements || index < 0)
            throw new IndexOutOfBoundsException(index+" out of bounds for list size = "+numElements);

        E element = elements[index];
        elements[index] = null;
        numAccesses[index] = 0;
        for(int i=index; i<numElements-1;i++)
        {
            elements[i] = elements[i+1];
            numAccesses[i] = numAccesses[i+1];
        }
        numElements--;
        return element;
    }

    @Override
    public int indexOf(Object o) {
        int index = -1;
        
        for(int i=0;i<numElements && index < 0;i++)
        {
            if(elements[i].equals(o))
            {    index = i;
                 numAccesses[index]++;
            }
        }
        while(checkSwap(index, elements[index]))
            index--;
        return index;
    }

    @Override
    public int lastIndexOf(Object o) {
        int index = -1;
        
        for(int i = numElements-1; i >= 0 && index < 0; i--)
        {
            if(elements[i].equals(o))
            {    index = i;
                 numAccesses[index]++;
            }
        }
        if(index > 0)
           while(checkSwap(index, elements[index]))
                index--;
        return index;
    }

    @Override
    public ListIterator<E> listIterator() {
        return listIterator(0); 
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return new ArrayIterator<E>(elements, index);
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        if(fromIndex <0 || toIndex <= 0 || fromIndex >= numElements 
                        || toIndex > numElements || fromIndex <= toIndex)
                        throw new IndexOutOfBoundsException(fromIndex+" inclusive and "+toIndex+" exclusive are invalid indices");
        
        SelfOrganizingArrayList newList = new SelfOrganizingArrayList(this);
        return newList;
    }
    
    @Override
    public String toString()
    {
        String s = "[";
        for(int i=0;i<numElements;i++)
        {
            s+=elements[i]+"("+numAccesses[i]+")";
            if(i < numElements-1)
                s+=",";
        }
        return s+"]";
    }
    
    public class ArrayIterator<E> implements ListIterator<E>
    {
        private E[] elements;
        private int currentIndex;
        public ArrayIterator(E[] elements, int startIndex)
        {   this.elements = elements;
            currentIndex = startIndex;
        }
        @Override
        public boolean hasNext() {
            return (currentIndex < numElements);    
        }

        @Override
        public E next() {
            if(currentIndex >= numElements)
                throw new NoSuchElementException("NO next element to Iterate ");
            E element = elements[currentIndex];
            currentIndex++;
            return element; 
        }

        @Override
        public boolean hasPrevious() {
            return (currentIndex > 0); 
        }

        @Override
        public E previous() {
            currentIndex --;
            if(currentIndex < 0)
                throw new NoSuchElementException("NO previous element to Iterate ");
            return elements[currentIndex];        
        }

        @Override
        public int nextIndex() {
            return currentIndex;
        }

        @Override
        public int previousIndex() {
            return currentIndex-1;
        }

        @Override
        public void set(E e) {
              throw new UnsupportedOperationException("Not allowed to remove with Read Only Iterator");        
        }

        @Override
        public void add(E e) {
            throw new UnsupportedOperationException("Not allowed to add with Read Only Iterator"); 
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not allowed to remove with Read Only Iterator");
        }
        
    }
}
