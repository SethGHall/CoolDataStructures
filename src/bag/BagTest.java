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
public class BagTest {
    
    public static void main(String[] args)
    {
        Bag<Integer> bag = new LinkedBag<>();
        
        for(int i=0;i<12;i++)
        {    
             System.out.println("Adding a "+i+":"+bag.add(i)+" to bag and fetching a "+bag.grab());
             System.out.println(bag.toString());
        }
        System.out.println("IS BAG FULL "+bag.isFull());
        System.out.println("IS BAG EMPTY "+bag.isEmpty());
        
        bag.remove(0);
        System.out.println(bag.toString());
        bag.remove(2);
        System.out.println(bag.toString());
        bag.remove(5);
        System.out.println(bag.toString());
        bag.remove(11);
        System.out.println(bag.toString());
        
        System.out.println("SIZE "+bag.size()+" capacity remaining "+bag.capacityRemaining());
        
       
        bag.add(30);
        System.out.println("ADDING 30 "+bag);
        
        Iterator it = bag.iterator();
        while(it.hasNext())
        {   System.out.println("Iterating "+it.next());
            
        }
        
        System.out.println("REMOVING REST OF BAG WITH GRAB ");
        while(!bag.isEmpty())
        {   Integer element = bag.grab();
            
            System.out.println("REMOVING "+element);
            bag.remove(element);
            System.out.println(bag);
        }
        System.out.println("IS BAG FULL "+bag.isFull());
        System.out.println("IS BAG EMPTY "+bag.isEmpty());
    }
}
