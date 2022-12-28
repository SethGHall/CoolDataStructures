/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package knapsack;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Set;

/**
 *
 * @author Seth
 * @param <E>
 */
public class MapKnapSack<E> implements KnapSack<E> {

    private Map<E,Integer> map;
    private int capacity;
    private int maxCapacity;
    
    public MapKnapSack(int maxCapacity)
    {   
        this.maxCapacity = maxCapacity;
        capacity = 0;
        map = new LinkedHashMap<>();
    }
    public MapKnapSack()
    {   this(10);
    }
    
    
    @Override
    public boolean add(E element, int amount) {
       
        if(capacity+amount > maxCapacity)
        {   return false;
        }
        if(!map.containsKey(element))
        {   map.put(element, amount);
        }
        else 
        {   Integer value = map.get(element);
            value+=amount;
            map.replace(element, value);
        }
        capacity += amount;
        return true;
    }

    @Override
    public boolean removeAll(E element) {
        return remove(element, currentQuantity());
    }

    @Override
    public boolean remove(E element, int amount) {
        if(!map.containsKey(element))
            return false;
        
        Integer v = map.get(element);
       
        capacity-=Math.min(amount, v);
        v-=amount;
        
        if(v <=0)
            map.remove(element);
        else 
            map.replace(element, v);
        return true;
        
    }

    @Override
    public boolean contains(E element) {
        return map.containsKey(element);
    }

    @Override
    public E grab() {
        if(numberUniqueItems() <= 0)
            throw new NoSuchElementException("KnapSack Empty: Nothing to Grab..");
        Set<Entry<E,Integer>> set = map.entrySet();
        Random rand = new Random();
        int x = rand.nextInt(capacity);
        int acc = 0;

        boolean found = false;
        E element = null;
        for(Entry<E,Integer> entry:set)
        {   acc += entry.getValue();
            if(acc > x)
            {
                element = entry.getKey();
                break;
            }
        }
        return element;
    }

    @Override
    public int numberUniqueItems() {
        return map.size();
    }
    
    @Override
    public String toString()
    {   
        return map.entrySet().toString();
    }

    @Override
    public int currentQuantity() {
        return capacity;
    }

    @Override
    public boolean add(E element) {
        return add(element,1);
    }
}
