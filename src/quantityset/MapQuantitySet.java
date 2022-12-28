package quantityset;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Set;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Seth
 */
public class MapQuantitySet<E> implements QuantitySet<E>{

    private Map<E,Integer> map;
    private int totalQuantity;
    
    public MapQuantitySet()
    {   map = new HashMap<>();
        totalQuantity = 0;
    }
    @Override
    public boolean add(E element, int amount) {
      
        if(!map.containsKey(element))
        {   map.put(element, amount);
            totalQuantity+= amount;
            return true;
        }
        Integer value = map.get(element);
        value+=amount;
        totalQuantity+= amount;
        map.replace(element, value);
        return false;
    }

    @Override
    public boolean remove(E element, int amount) {
       if(!map.containsKey(element))
            return false;
        
        Integer v = map.get(element);
       
        totalQuantity-=Math.min(amount, v);
        v-=amount;
        
        if(v <=0)
            map.remove(element);
        else 
            map.replace(element, v);
        return true;
    }

    @Override
    public E grab() {
        
        if(totalQuantity == 0)
            throw new NoSuchElementException("KnapSack Empty: Nothing to Grab..");
        Set<Map.Entry<E,Integer>> set = map.entrySet();
        Random rand = new Random();
        int x = rand.nextInt(totalQuantity);
        int acc = 0;
        boolean found = false;
        E element = null;
        for(Map.Entry<E,Integer> entry:set)
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
    public int getQuantity(E element) {
        return map.get(element);
    }

    @Override
    public int totalQuantity() {
       return totalQuantity;
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean contains(E o) {
        return map.containsKey((E)o);
    }

    @Override
    public Iterator<E> iterator()
    {
       return map.keySet().iterator();
    }
    @Override
    public boolean add(E e) {
        return add(e,1);
    }

    @Override
    public boolean removeAll(E o) {
        return remove(o,1);
    }

    @Override
    public void clear() {
        map.clear();
    }
    
}
