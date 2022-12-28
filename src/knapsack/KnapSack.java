/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package knapsack;

/**
 *
 * @author Seth
 * @param <E>
 */
public interface KnapSack<E> {
    //addith weight returns false if cannot fit
    public boolean add(E element, int amount);
    public boolean add(E element);
    public boolean removeAll(E element);
    public boolean remove(E element, int amount);
    public boolean contains(E element);
    public E grab();
    public int numberUniqueItems();
    public int currentQuantity();     
}
