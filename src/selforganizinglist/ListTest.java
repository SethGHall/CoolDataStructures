/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package selforganizinglist;

import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Seth
 */
public class ListTest {
    public static void main(String[] args)
    {
        List<String> list = new SelfOrganizingLinkedList<>();
        
        list.add("apple");
        list.add("banana");
        list.add("beans");
        list.add("carrot");
        list.add("brocolli");
        list.add("cauliflower");
        list.add("peas");
        
        System.out.println(list);
        System.out.println(list.contains("beans"));
        System.out.println(list.indexOf("beans"));
        System.out.println(list.indexOf("peas"));
        System.out.println(list);
        
        System.out.println(list);
        list.contains("beans");
        System.out.println(list.lastIndexOf("carrot"));
        System.out.println(list.indexOf("cauliflower"));
        System.out.println(list.lastIndexOf("apple"));
        System.out.println(list);
        
        Iterator<String> it = list.iterator();
        while(it.hasNext())
            System.out.println(it.next());
    }
}
