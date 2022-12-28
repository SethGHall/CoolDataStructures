/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trie;

import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 *
 * @author Seth
 */
public class Trie {

    private TrieNode root;
    private int numWords;
    
    public Trie()
    {
        root = new TrieNode();
    }
    
    
    public boolean contains(String element)
    {
        TrieNode current = root;
        for(int i=0;i<element.length();i++)
        {  
            TrieNode child;
            if(current.children.containsKey(element.charAt(i)))
            {    child = current.children.get(element.charAt(i));
                 current = child;
            }
            else
            {   return false;
            }
        }
        return (current.element != null && element.equals(current.element));
    }
    
    public boolean removeAll(String prefix)
    {
        TrieNode current = root;
        Stack<TrieNode> stack = new Stack<>();
        for(int i=0;i<prefix.length();i++)
        {
            if(current.children.containsKey(prefix.charAt(i)))
            {    TrieNode child = current.children.get(prefix.charAt(i));
                 stack.push(current);
                 current = child;
            }
            else return false;
        }
        
        boolean stop = false;
        int charPosition = prefix.length() - 1;
        current.children.clear();
        current.element = null; 
         
        while(!stack.isEmpty() && !stop)
        {
           if(current.children.isEmpty() && current.element == null)
           {   current = stack.pop();
               current.children.remove(prefix.charAt(charPosition));
               charPosition--;
           }
           else stop = true;
        }
        return true;
    }
    public boolean remove(String element)
    {
        TrieNode current = root;
        //NEED to find the node with element set it to null, if no children remove it from parent, do this by popping back with a stack?? possible repeat
        Stack<TrieNode> stack = new Stack<>();
        for(int i=0;i<element.length();i++)
        {  
            TrieNode child;
            if(current.children.containsKey(element.charAt(i)))
            {    child = current.children.get(element.charAt(i));
                 stack.push(current);
                 
                 current = child;
            }
            else
            {
                return false;
            }
        }
        if(current.element == null)
            return false;
        current.element = null;
        
        int charPosition = element.length() - 1;
        while(!stack.isEmpty())
        {
           if(current.children.isEmpty() && current.element == null)
           {
               current = stack.pop();
               current.children.remove(element.charAt(charPosition));
               charPosition--;
           }
           else
               return true;
        }
        return true;
        //now backtrack back up the tree removing path if no children
    }
    
    public boolean add(String element)
    {   TrieNode current = root;
        for(int i=0;i<element.length();i++)
        {  
            TrieNode child;
            
            if(current.children.containsKey(element.charAt(i)))
                child = current.children.get(element.charAt(i));
            else
            {
                child = new TrieNode();
                current.children.put(element.charAt(i),child);
            }
            current = child;
        }     
        if(current.element != null && current.element.equals(element))
            return false;
        current.element = element;
        numWords++;
        return true;
    }
    
    public boolean startsWith(String prefix)
    {
        TrieNode current = root;
        for(int i=0;i<prefix.length();i++)
        {  
            TrieNode child;
            if(current.children.containsKey(prefix.charAt(i)))
            {    child = current.children.get(prefix.charAt(i));
            }
            else
            {
                return false;
            }
            current = child;
        }
       return true;
    }
    
    public Set<String> suggestions(String prefix)
    {
        Set<String> set = new TreeSet<>();
        
        //use prefix to navigate to correct posistion add prefix (element) if exists
        
        TrieNode current = root;
        for(int i=0;i<prefix.length();i++)
        { 
            if(current.children.containsKey(prefix.charAt(i)))
            {    TrieNode child = current.children.get(prefix.charAt(i));
                 current = child;
            }
            else return set;
        }
        
        
        // use recursive dfs along each subprefix!
        recursiveWordFind(set,current);
        return set;
    }
    
    private void recursiveWordFind(Set<String> set, TrieNode current)
    {
        if(current.element != null)
            set.add(current.element);
        Set<Character> keys = current.children.keySet();
        
        for(Character c:keys)
        {   recursiveWordFind(set,current.children.get(c));
        }
        
    }
    
    @Override
    public String toString()
    {
        return recursiveString(root, 0);
    }
    
    private String recursiveString(TrieNode current, int level)
    {
        String levelString = "";
        if(current.children.size() > 0)
        {   
            Set<Character> chars = current.children.keySet();
            String tabs="";
            for(int i=0;i<level;i++)
                tabs += "\t";
            
            for(Character c : chars)
            {
                TrieNode child = current.children.get(c);
                
                //System.out.print(tabs+"["+c+"]");
                levelString+=tabs+" ["+c+"]";
                if(child.element != null) 
                    levelString+=" >> "+child.element; //System.out.print(" >> "+child.element);
                levelString +="\n";
                levelString += recursiveString(child, level+1);
                
            }   
        }
        return levelString;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Trie trie = new Trie();
        trie.add("a");
        trie.add("then");
        trie.add("there");
        trie.add("the");
        trie.add("their");
        trie.add("he");
        trie.add("time");
        trie.add("tim");
        trie.add("her");
        trie.add("timtam");
        trie.add("help");
        trie.add("hell");
        trie.add("to");
        trie.add("tod");
        trie.add("today");
        trie.add("tomorrow");
        trie.add("tonight");
        trie.add("toni");
        trie.add("ton");
        trie.add("tom");
        System.out.println(trie);
        
        System.out.println(">>>>>>LETS DO SOME REMOVES<<<<<<<<<<<<<<");
       trie.remove("help");
        trie.remove("tomorrow");
        //trie.removeAll("t");
        System.out.println(trie);
        System.out.println(">>>>>>LETS DO Gets!<<<<<<<<<<<<<<");
        
        Set<String> words = trie.suggestions("the");
        System.out.println("Suggestions amount "+words.size());
        System.out.println(words);
        
    }
     
    private class TrieNode
    {
        public String element;
        public Map<Character,TrieNode> children;
        
        public TrieNode()
        {
            children = new TreeMap<>();
            element = null;
        }
    }
    
}
