package hashmapwithchaining;


import java.util.Iterator;
import java.util.Map;
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
public class Test {
    
    public static void main(String[] args)
    {
        Map<String,String> map = new LinkedHashMapWithChaining<>();
        map.put("1", "one");
        map.put("7", "seven");
        map.put("13", "thirteen");
        map.put("4", "four");
        System.out.println(map);
        map.put("0", "zero");
        System.out.println(map);
        map.put("100", "hundred");
        map.put("200", "two hundred");
        map.put("-4", "negative 4");
        System.out.println(map);
        
        System.out.println("Getting "+map.get("1"));
        System.out.println("Getting "+map.get("7"));
        System.out.println("Getting "+map.get("100"));
        
        System.out.println("get values "+map.values());
        System.out.println("get values "+map.keySet());
        
        System.out.println("Contains Key? "+map.containsKey("1"));
        System.out.println("Contains Key? "+map.containsKey("7"));
        System.out.println("Contains Key? "+map.containsKey("100"));
        System.out.println("Contains Key? "+map.containsKey("17"));
        
        
        System.out.println("Contains Value? "+map.containsValue("one"));
        System.out.println("Contains Value? "+map.containsValue("seven"));
        System.out.println("Contains Value? "+map.containsValue("hundred"));
        System.out.println("Contains Value? "+map.containsValue("seventeen"));
        System.out.println("\n");
        map.remove("200");
        map.remove("7");
        map.remove("1");
        System.out.println(map);
        System.out.println("Size of map is "+map.size());
        
        System.out.println("get values "+map.values());
   
        Set<String> keys = map.keySet();
        System.out.println("keys are "+keys);
      
        Iterator<String> it = keys.iterator();
        
        while(!map.isEmpty())
        {    
            System.out.println("REMOVING "+map.remove(it.next())+"\n");
            System.out.println(map);
        }  
        
        map.put("666","six hundred and sixty six");
        map.put("111","one hundred and eleven");
        System.out.println(map);
    }
}
