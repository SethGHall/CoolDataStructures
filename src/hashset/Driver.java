package hashset;


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
 */
public class Driver {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Set<String> set = new HashSetWithChaining<>(6,0.75f);
        set.add("Seth");
        set.add("Bob");
        set.add("Ian");
        set.add("Adam");
        System.out.println("Creating Set, initial capacity=6.. Addding Seth,Bob,Adam,Ian");
        System.out.println(set);
        System.out.println("Size is: "+set.size());
        System.out.println("Addding Jill, Amy, Nat, Seth, Bob, Simon");
        set.add("Jill");
        set.add("Amy");
        set.add("Nat");
        set.add("Seth");
        set.add("Bob");
        set.add("Andy");
        set.add("Simon");
        System.out.println("Size is: "+set.size());
        System.out.println(set);
        System.out.println("Contains Seth? "+set.contains("Seth")+" Contains Nat? "+set.contains("Nat")+ " Contains Gary "+set.contains("Gary"));
        System.out.print("Iteraing! ");
        Iterator<String> it = set.iterator();
        while(it.hasNext())
           System.out.print(it.next()+" ");
        System.out.println();
        System.out.println("REMOVING Seth, Adam, Bob");
        set.remove("Seth");
        set.remove("Adam");
        set.remove("Bob");
        System.out.println("Size is: "+set.size());
        System.out.println(set);
        
    }
    
}
