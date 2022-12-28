/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package selforganizinglist;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.StringTokenizer;

/**
 *
 * @author Seth
 */
public class DocumentReader {
    public static void main(String[] args)
    {   BufferedReader reader = null;
        try
        {
             reader = new BufferedReader(new FileReader("test.txt"));
        }
        catch(Exception e){
            System.out.println("Exception "+e);
        }
        if(reader != null)
        {
            String line = null;
            StringTokenizer tokenizer = null;
            List<String> list = new SelfOrganizingLinkedList<String>();
            try{
                do
                {
                    line = reader.readLine();
                    if(line != null)
                    {
                        tokenizer = new StringTokenizer(line.toLowerCase().trim(), "  ;:,.90(){}[]<>");
                        while(tokenizer.hasMoreTokens())
                        {
                            String token = tokenizer.nextToken();
                            if(!list.contains(token))
                                list.add(token);
                        }

                    }

                }while(line != null);
            }catch(Exception e){
                System.out.println("Error reading file "+e);
            }
            System.out.println("Unique words found: "+list.size());
            System.out.println(list);
            list.contains("pets");
            
            System.out.println(list);
        }
    }
}
