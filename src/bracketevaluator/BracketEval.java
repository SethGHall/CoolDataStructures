package bracketevaluator;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import helperclasses.StackADT;
import helperclasses.ArrayStack;
import java.util.NoSuchElementException;

/**
 *
 * @author sehall
 */
public class BracketEval {
    
    private final String opening = "{[(<";
    private final String closing = "}])>";
    
    public boolean isOpening(char o)
    {
        return (opening.contains(""+o));
    }
    public boolean isClosing(char c)
    {
        return (closing.contains(""+c));
    }
    public boolean checkPair(char o, char c)
    {
        int indexOpening = opening.indexOf(o);
        int indexClosing = closing.indexOf(c);
        
        return (indexOpening == indexClosing);
    }
    public static void main(String[] args)
    {
        String s = "{(<{eeeek>>){}{…}(e(e)e){hello}}";
        StackADT<Character> stack = new ArrayStack<Character>();
        BracketEval eval = new BracketEval();
        boolean success = true;
        for(int i=0;i<s.length();i++)
        {
            char x = s.charAt(i);
            if(eval.isOpening(x))
            {   
                stack.push(x);
            }
            else if(eval.isClosing(x))
            {   char y = ' ';
                try{
                     y = stack.pop();
                }catch(NoSuchElementException e){}
                if(eval.checkPair(y, x))
                {
                    System.out.println("PROCESSED MATCHING PAIR "+y+" "+x+" match at closing index "+i);
                }
                else
                {
                    System.out.println("NO MATCH "+y+" "+x+" with closing index at "+i);
                    success = false;
                }
            }
            else
            { 
                System.out.println("Ignoring "+x);
            } 
        }
        if(stack.isEmpty() && success)
            System.out.println("BRACKETS ALL SUCCESSFULLY EVALUATED");
        else
            System.out.println("BRACKETS DID NOT MATCH IN STRING ");
    }
    
}
