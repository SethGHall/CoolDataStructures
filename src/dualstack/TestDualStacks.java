package dualstack;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Seth
 */
public class TestDualStacks {
    public static void main(String[] args) {
        DualStack<String> stack = new LinkedDualStack<>();
       
        stack.pushBottom("A");
        stack.pushBottom("B");
        stack.pushBottom("C");
        System.out.println(stack);
        while(!stack.isEmpty())
            System.out.println(stack.popTop());
        
        
        
    }
}
