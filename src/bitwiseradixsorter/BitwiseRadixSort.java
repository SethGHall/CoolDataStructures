/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bitwiseradixsorter;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 *
 * @author Seth
 */
public class BitwiseRadixSort {
    public static void bitwiseRadix(byte[] numbers)
    {
        Queue<Byte> q0 = new LinkedList<>();
        Queue<Byte> q1 = new LinkedList<>();
        
        int bit =1;
        
        for(int x=0;x<8;x++)
        {
            for(int i=0;i<numbers.length;i++)
            {
                //determine 1 or 0
                if((numbers[i] & bit) == 0)
                    q0.offer(numbers[i]);
                else
                    q1.offer(numbers[i]);
            }
            //now dequeue
            int index = 0;
            while(!q0.isEmpty())
            {
                numbers[index] = q0.poll();
                index++;  
            }
            while(!q1.isEmpty())
            {
                numbers[index] = q1.poll();
                index++; 
            }
            bit = (bit << 1);
        }
    }
    
    public static void main(String[] args)
    {
        byte[] numbers = new byte[200];
        Random rand = new Random();
        for(int i=0;i<numbers.length;i++)
        {
            numbers[i] = (byte)rand.nextInt(255);
        }
        System.out.println("SORTING  THESE");
        System.out.println(Arrays.toString(numbers));
        bitwiseRadix(numbers);
        System.out.println("SORTING  THESE");
        System.out.println(Arrays.toString(numbers));
        
        
    }
}
