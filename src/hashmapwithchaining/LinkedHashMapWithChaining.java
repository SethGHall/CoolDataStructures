package hashmapwithchaining;


import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;


/**
 *
 * @author Seth
 * @param <K>
 * @param <V>
 */
public class LinkedHashMapWithChaining<K,V> implements Map<K, V>{

    private MapEntryNode<K,V>[] hashTable; 
    private int numElements;
    private MapEntryNode<K,V> firstNode;
    private MapEntryNode<K,V> lastNode;
    
    public LinkedHashMapWithChaining()
    {
        hashTable = new MapEntryNode[5];
        numElements = 0;
    }
    public LinkedHashMapWithChaining(Map<? extends K,? extends V> m)
    {
        this();
        this.putAll(m);
    }
    
    
    @Override
    public int size() {
        return numElements;
    }

    @Override
    public boolean isEmpty() {
        return (numElements == 0);
    }

    @Override
    public boolean containsKey(Object key) {
        int hashCode = Math.abs(key.hashCode()) % hashTable.length;
        
        MapEntryNode<K,V> current = hashTable[hashCode];
        while(current != null)
        {
            if(key.equals(current.getKey()))
                return true;
            current = current.chain;
        }
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        MapEntryNode<K,V> current = firstNode;
        while(current != null)
        {
            if(value.equals(current.getValue()))
                return true;
            current = current.next;
        }
        return false;
    }

    @Override
    public V get(Object key) {
        int hashCode = Math.abs(key.hashCode()) % hashTable.length;
        
        MapEntryNode<K,V> current = hashTable[hashCode];
        while(current != null)
        {
            if(key.equals(current.getKey()))
                return current.getValue();
            current = current.chain;
        }
        throw new NoSuchElementException("No key "+key+" Found in the map");
    }

    @Override
    public V put(K key, V value)
    {
        if(containsKey(key))
            throw new IllegalArgumentException("Key Already Exists in HashMap, cannot add duplicate key = "+key);
        
        if((double)numElements / (double)hashTable.length >= 0.75)
            expandCapacity();
        
        
        int hashCode = Math.abs(key.hashCode()) % hashTable.length;   
        MapEntryNode<K,V> current = hashTable[hashCode];
        MapEntryNode<K,V> newEntry = new MapEntryNode<>(key,value);
        
        newEntry.chain = hashTable[hashCode];
        hashTable[hashCode] = newEntry;
        
        if(firstNode == null)
        {   firstNode = newEntry;
            lastNode = newEntry;
        }
        else
        {   newEntry.prev = lastNode;
            lastNode.next = newEntry;
            lastNode = newEntry;
        }
        numElements++;
        return value;
    }
    private void expandCapacity()
    {
        System.out.println("===================EXPANDING CAPACITY====================");
        hashTable = new MapEntryNode[hashTable.length*2];
        
        MapEntryNode<K,V> current = firstNode;
        firstNode = null;
        lastNode = null;
        numElements = 0;
        
        while(current != null)
        {   put(current.getKey(),current.getValue());
            current = current.next;
        }
        
        
    }
    @Override
    public V remove(Object key) {
        if(!containsKey((K)key))
            throw new NoSuchElementException("Key = "+key+" Does not exist in this map ");
        
        int hashCode = Math.abs(key.hashCode()) % hashTable.length;   
        MapEntryNode<K,V> removeNode = null;
        boolean found = false;
        
        if(hashTable[hashCode].getKey().equals(key))
        {   removeNode = hashTable[hashCode];
            hashTable[hashCode] = hashTable[hashCode].chain;
        }
        else{
            MapEntryNode<K,V> current = hashTable[hashCode];
            while(!found && current.chain != null)
            {   if(current.chain.getKey().equals(key))
                {   removeNode = current.chain;
                    current.chain = current.chain.chain;
                    found = true;
                }
                else current = current.chain;
            }
        } 
        if(removeNode == null)
            throw new NoSuchElementException("Key = "+key+" Does not exist in this map, did not find????");  
        
        if(removeNode.next == null)  lastNode = lastNode.prev;
        else removeNode.next.prev = removeNode.prev; 
        
        if(removeNode.prev == null) firstNode = firstNode.next;
        else removeNode.prev.next = removeNode.next;
        
        removeNode.next = null;
        removeNode.prev = null;
        numElements--;
        return removeNode.getValue(); 
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        Set<? extends Entry<? extends K, ? extends V>> set = m.entrySet();
        for(Entry<? extends K, ? extends V> entry:set)
            put(entry.getKey(),entry.getValue());
        
    }

    @Override
    public void clear() {
        hashTable = new MapEntryNode[10];
        numElements = 0;
    }

    @Override
    public Set<K> keySet() {
        Set<K> set = new LinkedHashSet<>();
        
        MapEntryNode<K,V> current = firstNode;
        while(current != null)
        {
            set.add(current.getKey());
            current = current.next;
        }
        return set;
    }

    @Override
    public Collection<V> values() {
        Collection<V> values = new ArrayList<>();
        MapEntryNode<K,V> current = firstNode;
        while(current != null)
        {
            values.add(current.getValue());
            current = current.next;
        }
        return values;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> set = new LinkedHashSet<>();
        MapEntryNode<K,V> current = firstNode;
        while(current != null)
        {
            set.add(current);
            current = current.next;
        }
        return set;
    }
    
    @Override
    public String toString()
    {
        String s = "{";
        MapEntryNode<K,V> current = firstNode;
        while(current != null)
        {
            s+="["+current.getKey()+":"+current.getValue()+"]";
            if(current.next != null)
                s+=",";
            current = current.next;
        }
        s +="}\n\n";
        
        for(int i=0;i<hashTable.length; i++)
        {
            if(hashTable[i] != null)
            {
                 current = hashTable[i];
                 s+= i+": ";
                 while(current != null)
                 {   s+= current.getKey()+"->"+current.getValue()+" ";
                     current = current.chain;
                 }
                 s+="\n";
            }
            else 
                s+= i+": null\n";
        }
        return s;
    }
    
    private class MapEntryNode<K,V> implements Entry<K, V>
    {
        public MapEntryNode<K,V> next;
        public MapEntryNode<K,V> prev;
        public MapEntryNode<K,V> chain;
        private final K key;
        private V value;
        
        public MapEntryNode(K key, V value)
        {   this.key = key;
            this.value = value;
            next = null;
            prev = null;
            chain = null;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            this.value = value;
            return value;
        }   
    }
}
