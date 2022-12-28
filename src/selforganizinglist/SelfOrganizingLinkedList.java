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
 * @param <E>
 */
public class SelfOrganizingLinkedList<E> implements List<E>{

    public Node<E> first;
    public Node<E> last;
    private int numElements;
    
    public SelfOrganizingLinkedList()
    {   first = null;
        last = null;
        numElements = 0;
    }
    public SelfOrganizingLinkedList(Collection<? extends E> coll)
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
    public Iterator<E> iterator() {
        return listIterator();
    }

    @Override
    public Object[] toArray() {
        if(numElements == 0)
            throw new NoSuchElementException(" No elements in collection to add to Array ");
        Object[] oArray = new Object[numElements];
        Node<E> current = first;
        int count = 0;
        while(current != null)
        {   oArray[count] = current.element;
            count++;
        }
        return oArray;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if(numElements == 0)
            throw new NoSuchElementException(" No elements in collection to add to Array ");
        int length = Math.min(a.length,numElements);
        Node<E> current = first;
        for(int i=0;i<length;i++)
        {    a[i] = (T)current.element;
             current = current.next;
        }
        return a;
    }

    @Override
    public boolean add(E e) {
        Node<E> newNode = new Node<E>(e);
        if(first == null)
        {   first = newNode;
            last = newNode;
        }
        else
        {
            newNode.prev = last;
            last.next = newNode;
            last = newNode;
        }
        numElements++;
        return true;
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
        boolean found = false;
        Node<E> current = last;
        int index = size()-1;
        while(current != null)
        {   if(c.contains(current.element))
            {   found = true;
            }
            else
            {   remove(index);
            }
            current = current.prev;
            index--;
        }
        return found;
    }

    @Override
    public void clear() {
        numElements = 0;
        first = null;
        last = null;
    }

    @Override
    public E get(int index) {
        Node<E> current = null;
        if (index < 0 || index >= numElements)
            throw new NoSuchElementException("index "+index+ " out of bounds");
        if(index > numElements/2)
        {   current = last;
            for(int i=numElements-1;i>index; i--)
                current = current.prev;
        }
        else
        {     current = first;
              for(int i=0;i<index;i++)
                   current = current.next;
        }
        return current.element;
    }

    @Override
    public E set(int index, E element) {
        Node<E> current = null;
        if (index < 0 || index >= numElements)
            throw new NoSuchElementException("index "+index+ " out of bounds");
        if(index > numElements/2)
        {   current = last;
            for(int i=numElements-1;i>index; i--)
                current = current.prev;
        }
        else
        {     current = first;
              for(int i=0;i<index;i++)
                   current = current.next;
        }
        current.element = element;
        return current.element;
    }

    @Override
    public void add(int index, E element) {
        add(element);
    }

    @Override
    public E remove(int index) {
        if (index < 0 || index >= numElements)
            throw new NoSuchElementException("index "+index+ " out of bounds");
        
        if(index == 0)
        {   Node<E> nodeToRemove = first;
            first = first.next;
            nodeToRemove.next = null;
            if(first != null)
                first.prev = null;
            if(numElements == 1)
                last = null;
            numElements--;
            return nodeToRemove.element;
        }
        else if(index == numElements-1)
        {
            Node<E> nodeToRemove = last;
            last = last.prev;
            nodeToRemove.prev = null;
            if(last != null)
                last.prev = null;
            if(numElements == 1)
                first = null;
            numElements--;
            return nodeToRemove.element;
        }
        else
        {   Node<E> nodeToRemove = first;
            for(int i=0;i<index;i++)
                nodeToRemove = nodeToRemove.next;
            
            nodeToRemove.prev.next = nodeToRemove.next;
            nodeToRemove.next.prev = nodeToRemove.prev;
            nodeToRemove.next = null;
            nodeToRemove.prev = null;
            numElements--;
            return nodeToRemove.element;
        }
    }
        @Override
    public boolean remove(Object o) {
        if(numElements == 0)
            return false;
        if(first.element.equals(o))
        {  Node<E> nodeToRemove = first;
           first = first.next;
           nodeToRemove.next = null;
           if(first != null)
               first.prev = null;
           if(numElements == 1)
               last = null;
        }
        else if(last.element.equals(o))
        {  Node<E> nodeToRemove = last;
           last = last.prev;
           nodeToRemove.prev = null;
           if(last != null)
               last.next = null;
           if(numElements == 1)
               first = null;
        }
        else
        {
            Node<E> nodeToRemove = first.next;
            boolean found = false;
            while(nodeToRemove != null && !found)
            {
                if(nodeToRemove.element.equals(o))
                    found = true;
                else nodeToRemove = nodeToRemove.next;
            }
            if(!found) return false;
            
            nodeToRemove.prev.next = nodeToRemove.next;
            nodeToRemove.next.prev = nodeToRemove.prev;
            nodeToRemove.prev = null;
            nodeToRemove.next = null;
        }
        numElements--;
        return true;
    }
    @Override
    public boolean contains(Object o) {
        Node<E> current = first;
        boolean found = false;
        
        while(!found && current !=null)
        {
            if(current.element.equals(o))
            {   found = true;
                current.accessCount++;
            }
            else current = current.next;
        }
        //BUBBLE THAT
        if(found)
            bubbleToFront(current,0);
        return found;
    }
    
    @Override
    public int indexOf(Object o) {
        Node<E> current = first;
        boolean found = false;
        int index = 0;
        
        while(!found && current != null)
        {   if(current.element.equals(o))
            {    found = true;
                current.accessCount++;
            }
            else
            {   index++;
                current = current.next;
            }
            
        }
        if(found)
        {
            return bubbleToFront(current,index);
        }
        else return -1;
    }
    private int bubbleToFront(Node<E> node, int index)
    {
        while(node.prev != null && node.accessCount >= node.prev.accessCount)
        {
            //swap the element
            E tempElement =  node.element;
            int tempCounter = node.accessCount;
            node.element = node.prev.element;
            node.accessCount = node.prev.accessCount;
            
            node.prev.element = tempElement;
            node.prev.accessCount = tempCounter;
            //move node
            index--;
            node = node.prev;
        }
        return index;
    }
    @Override
    public int lastIndexOf(Object o) {
        Node<E> current = last;
        boolean found = false;
        int index = numElements-1;
        
        while(!found && current != null)
        {   if(current.element.equals(o))
            {    found = true;
                current.accessCount++;
            }
            else
            {   index--;
                current = current.prev;
            }
        }
        if(found) return bubbleToFront(current,index);
        else return -1;
    }

    @Override
    public ListIterator<E> listIterator() {
        return listIterator(0);
    }
  
    @Override
    public ListIterator<E> listIterator(int index) {
        return new LinkedListIterator(index,first); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        if(fromIndex <0 || toIndex <= 0 || fromIndex >= numElements 
                        || toIndex > numElements || fromIndex <= toIndex)
                        throw new IndexOutOfBoundsException(fromIndex+" inclusive and "+toIndex+" exclusive are invalid indices");
        
        SelfOrganizingLinkedList newList = new SelfOrganizingLinkedList(this);
        Node<E> current = first;
        for(int i=0;i<toIndex;i++)
        {   if(i>=fromIndex)
                newList.add(current.element);
            current = current.next;
        }
        return newList;
    }
    @Override
    public String toString()
    {   String s = "[";
        Node<E> current = first;
        for(int i=0;i<numElements;i++)
        {   s+=current.element+"("+current.accessCount+")";
            if(i < numElements-1)
                s+=",";
            current = current.next;
        }
        s+="]";
        return s;
    }
    
    private class Node<E>
    {
        public Node<E> next;
        public Node<E> prev;
        public E element;
        public int accessCount;
        
        public Node(E element)
        {
            this.element = element;
            next = null;
            prev = null;
            accessCount = 1;
        }       
    }
    private class LinkedListIterator<E> implements ListIterator<E>
    {
        private int index;
        private Node<E> current;
        
        public LinkedListIterator(int index, Node<E> first)
        {   current = first;
            for(int i=0;i<index;i++)
                current = current.next;
                
            this.index = index;
        }
        @Override
        public boolean hasNext() {
            return (current != null);
        }

        @Override
        public E next() {
            E element = current.element;
            index++;
            current = current.next;
            return element;
        }

        @Override
        public boolean hasPrevious() {
            return (current.prev != null);
        }

        @Override
        public E previous() {
            E element = current.element;
            current = current.prev;
            index--;
            return element;
        }

        @Override
        public int nextIndex() {
            return index;
        }

        @Override
        public int previousIndex() {
            return index-1;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Iterator Read Only");        
        }

        @Override
        public void set(E e) {
            throw new UnsupportedOperationException("Iterator Read Only");
        }

        @Override
        public void add(E e) {
            throw new UnsupportedOperationException("Iterator Read Only");
        }
        
    }
}
