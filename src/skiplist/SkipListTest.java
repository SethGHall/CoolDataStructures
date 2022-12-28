/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skiplist;

/**
 *
 * @author Seth
 */
public class SkipListTest {
    public static void main(String[] args)
    {
        System.out.println("=============SKIP LIST SET================");
        SkipListSet<Integer> skippy = new SkipListSet<>();
        for(int i=1;i<=20;i++)
            skippy.add(i);
        System.out.println("Size of Skip list is "+skippy.size());
        System.out.println(skippy.toString());
        
        for(int i=20;i>=0;i--)
            System.out.println(skippy.add(i));
        System.out.println("Size of Skip list is "+skippy.size());
        System.out.println(skippy.toString());
    }
}
