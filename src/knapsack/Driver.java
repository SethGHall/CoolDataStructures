/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package knapsack;

import java.util.NoSuchElementException;

/**
 *
 * @author Seth
 */
public class Driver {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        KnapSack<String> ks = new MapKnapSack<>(50);
        System.out.println("Creating KnapSack of max capacity 50");
        System.out.println("Addding 3 of {Apple, Banana, Cucumber, Dates, Grape, Kiwi, Lemon, Melon, Pineapple, Strawberry");
        ks.add("Apple");
        ks.add("Apple");
        ks.add("Apple");
        ks.add("Banana", 3);
        ks.add("Cucumber", 3);
        ks.add("Dates", 3);
        ks.add("Grape", 3);
        ks.add("Kiwi", 3);
        ks.add("Lemon", 3);
        ks.add("Melon", 3);
        ks.add("Pineapple", 3);
        ks.add("Strawberry", 3);
        System.out.println("KS="+ks);
        System.out.println("Number of Unique items: "+ks.numberUniqueItems()+" Current Capacity: "+ks.currentQuantity());
        
        System.out.println("\nTest Grab 8x..");
        String s="{";
        for(int i=0;i<8;i++)
        {
            s+=ks.grab();
            if(i<8-1)
                s+=",";
            else s+="}";    
        }
        System.out.println(s);
        System.out.println("\nAdding 2 more Strawberry: "+ks.add("Strawberry", 2)+", 5 more Apple: "+ks.add("Apple", 5)+", 10 more Lemon:"+ks.add("Lemon", 10));
        System.out.println("KS="+ks);
        System.out.println("Number of Unique items "+ks.numberUniqueItems()+" Current Capacity "+ks.currentQuantity());
        
        System.out.println("\nCan we Add 10 more Grape (should be false, sack cannot fit) "+ks.add("Grape", 10));
        System.out.println("Number of Unique items: "+ks.numberUniqueItems()+" Current Capacity: "+ks.currentQuantity());
        
        System.out.println("\nContains Apple:"+ks.contains("Apple")+", Contains Strawberry:"+ks.contains("Strawberry")+
                ", Contains Kiwi:"+ks.contains("Kiwi")+" Contains Kumara:"+ks.contains("Kumara"));
        System.out.println("Removing 3 Potato and all Pear (both not there, should be false: "+ks.remove("Potato", 3)+","+ks.removeAll("Pear"));
        System.out.println("Removing 2 Apples:"+ks.remove("Apple", 2)+ ", 1 Strawberry:"+ks.remove("Strawberry", 1)+", 5 Lemon:"+ks.remove("Lemon", 5));      
        System.out.println("\nKS="+ks);
        System.out.println("Number of Unique items: "+ks.numberUniqueItems()+" Current Capacity: "+ks.currentQuantity()+" Capacity Remaining: ");
        
        System.out.println("\nRemoving All Apple:"+ks.removeAll("Apple")+" All Strawberry:"+ks.removeAll("Strawberry")+", All Kiwi:"+ks.removeAll("Kiwi"));
        System.out.println("KS="+ks);
        System.out.println("Number of Unique items: "+ks.numberUniqueItems()+" Current Capacity: "+ks.currentQuantity());
        
        System.out.println("\nRemove 10 Melon, Pineapple, Banana (should remove all but capacity reduces only with what is there) ");
        System.out.print(ks.remove("Melon",10)+", ");
        System.out.print(ks.remove("Pineapple",10)+", ");
        System.out.println(ks.remove("Banana",10));
        System.out.println("KS="+ks);
        System.out.println("Number of Unique items: "+ks.numberUniqueItems()+" Current Capacity: "+ks.currentQuantity());
        
        System.out.println("\nCan we add 100 Brocolli? (should be false, cannot fit) - "+ks.add("Brocolli",100));
        System.out.println("Add 10 Brocolli, 1 Potato, 1 Kumara and 3 more Dates and 1 more Cucumber? ");
        ks.add("Brocolli", 10);
        ks.add("Potato");
        ks.add("Kumara");
        ks.add("Dates", 3);
        ks.add("Cucumber");
        System.out.println("KS="+ks);
        System.out.println("Number of Unique items: "+ks.numberUniqueItems()+" Current Capacity: "+ks.currentQuantity());
        
        System.out.println("\nRemove 10 random items using grab:");
        s = "";
        for(int i=0;i<10;i++)
        {   String food = ks.grab();
            s+=food;
            if(i<9)
                s+=",";
            ks.remove(food,1);
        }
        System.out.println("Removed: "+s);
        System.out.println("KS="+ks);
        System.out.println("Number of Unique items: "+ks.numberUniqueItems()+" Current Capacity: "+ks.currentQuantity());
        
         System.out.println("Remove all unique items random grab ");
        while(ks.numberUniqueItems() > 0)
        {   String food = ks.grab();
            System.out.print("Removing all: "+food);
            ks.removeAll(food);
            System.out.println(", KS="+ks);
        }
        System.out.println("Number of Unique items: "+ks.numberUniqueItems()+" Current Capacity: "+ks.currentQuantity());
        System.out.println("\nTest Grab Exception: (Should throw since empty)");
        try
        {
            ks.grab();
        }catch(NoSuchElementException e)
        {
            System.out.println("SUCCESS: Excption caught "+e);
        }
        
    }
    
}
