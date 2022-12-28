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
public interface DualStack<E> {   
    public boolean isEmpty();
    public int size();
    public E peekTop();
    public E peekBottom();
    public E popTop();
    public E popBottom();
    public void pushTop(E element);
    public void pushBottom(E element);
}
