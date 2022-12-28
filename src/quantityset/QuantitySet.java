package quantityset;


import java.util.Collection;
import java.util.Iterator;
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
public interface QuantitySet<E> {
    public boolean add(E item, int amount);
    public boolean add(E item);
    public boolean contains(E item);
    public boolean remove(E item, int amount);
    public boolean removeAll(E item);
    public E grab();
    public int getQuantity(E item);
    public int totalQuantity();  
    public int size();
    public Iterator<E> iterator();
    public boolean isEmpty();
    public void clear();
}
