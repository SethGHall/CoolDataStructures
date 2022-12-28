/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bag;

import java.util.Iterator;

/**
 *
 * @author sehall
 */
public interface Bag<E> {
    public boolean add(E item);
    public E grab();
    public boolean remove(E item);
    public int size();
    public int capacityRemaining();
    public void clear();
    public boolean isEmpty();
    public boolean isFull();
    public Iterator<E> iterator();
    public E[] toArray();
}
