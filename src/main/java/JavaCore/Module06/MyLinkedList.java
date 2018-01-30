package JavaCore.Module06;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * Задание 2 - LinkedList
 * Написать свой класс MyLinkedList как аналог классу LinkedList.
 * <p>
 * Нельзя использовать массив. Каждый элемент должен быть отдельным объектом-посредником(Node - нода)
 * который хранит ссылку на прошлый и следующий элемент коллекции(двусвязный список).
 * <p>
 * Методы
 * add(T value) добавляет элемент в конец
 * <p>
 * remove(int index) удаляет элемент под индексом
 * <p>
 * clear() очищает коллекцию
 * <p>
 * size() возвращает размер коллекции
 * <p>
 * get(int index) возвращает элемент под индексом
 */
public class MyLinkedList<T> implements Iterable<T>
{
    Node<T> firstNode;
    Node<T> currentNode;
    
    int nodeCount = 0;
    
    /**
     * todo добавляет элемент в конец
     */
    public void add(T value)
    {
        Node<T> node = new Node<>(value);
    
        if ( firstNode == null )
            firstNode = node;
        
        if ( currentNode instanceof Node ) {
            currentNode.next = node;
            node.prev = currentNode;
        }
    
        currentNode = node;
        
        nodeCount++;
    }

    /**
     * todo удаляет элемент под индексом
     */
    public void remove(int index)
    {
        Iterator<T> iterator = iterator();
    
        int position = 0;
        
        if ( index == 0 )
            clear();
        
        while ( iterator.hasNext() ){
            Node<T> node = (Node<T>) iterator.next();
           if ( position++ == index ){
               Node prev = node.prev;
               Node next = node.next;
               if ( prev instanceof Node )
                   prev.next = next;
               break;
           }
        }
    }

    /**
     * todo очищает коллекцию
     */
    public void clear()
    {
        firstNode = currentNode = null;
        
        nodeCount = 0;
    }

    /**
     * todo  () возвращает размер коллекции
     */
    public int size()
    {
        return nodeCount;
    }

    /**
     * todo   возвращает элемент под индексом
     */
    public T get(int index)
    {
        ListIterator<T> iterator = iterator();
    
        int position = 0;
    
        Node<T> node = null;
        
        if ( index == 0 )
            node = firstNode;
    
        boolean nodeFound = false;
        
        while ( iterator.hasNext() && !nodeFound ){
            if ( position++ == index ){
                nodeFound = true;
                node = (Node<T>) iterator.next();
            }
        }
        
        return node instanceof Node ? node.value : null;
    }
    
    @Override
    public ListIterator<T> iterator ()
    {
        
        return new ListIterator<>();
    }
    
    @Override
    public void forEach (Consumer<? super T> action)
    {
    
    }
    
    @Override
    public Spliterator<T> spliterator ()
    {
        return null;
    }
    
    @Override
    public String toString ()
    {
        String str = "";
        
        Iterator<T> iterator = iterator();
        
        while ( iterator.hasNext() ){
            str += iterator.next();
        }
        
        return "MyLinkedList{" +
               str +
               '}';
    }
    
    private Node<T> node(int index){
        Node<T> x = firstNode;
        for ( int i = 0; i < index; i++ ) {
            x = x.next;
        }
        return x;
    }
    
    private class Node<T> {
        Node<T> prev;
        Node<T> next;
        
        T value;
    
        public Node (T value)
        {
            this.value = value;
        }
    
        @Override
        public String toString ()
        {
            return "Node{" +
                   "value=" + value +
                   '}';
        }
    }
    
    /**
     *
     */
    private class ListIterator<T> implements java.util.ListIterator<T> {
        
        int cursor;
        
        Node<T> lastReturned;
        Node<T> nextElement;
        
        public ListIterator ()
        {
            cursor = -1;
            
            nextElement = (Node<T>) firstNode;
        }
    
        public ListIterator (int cursor)
        {
            this.cursor = cursor;
            
            nextElement = (Node<T>) node( cursor );
        }
    
        @Override
        public boolean hasNext ()
        {
              return nextElement instanceof Node;
        }
    
        @Override
        public T next ()
        {
            T element = null;
            
            if ( hasNext() ) {
                element = nextElement.value;
                cursor++;
            }
            
            return element;
        }
    
        @Override
        public boolean hasPrevious ()
        {
            return false;
        }
    
        @Override
        public T previous ()
        {
            return null;
        }
    
        @Override
        public int nextIndex ()
        {
            return cursor + 1;
        }
    
        @Override
        public int previousIndex ()
        {
            return cursor;
        }
    
        @Override
        public void remove ()
        {
        
        }
    
        @Override
        public void set (T t)
        {
        
        }
    
        @Override
        public void add (T t)
        {
        
        }
    }
    

}
