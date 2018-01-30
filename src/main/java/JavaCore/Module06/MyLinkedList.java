package JavaCore.Module06;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.function.Consumer;


public class MyLinkedList<T> implements Iterable<T>
{
    Node<T> firstNode;
    Node<T> currentNode;

    int nodeCount = 0;

    /**
     * добавляет элемент в конец
     */
    public void add(T value)
    {
        Node<T> node = new Node<>( value );

        if ( firstNode == null )
        {
            firstNode = node;
        }

        if ( currentNode instanceof Node )
        {
            currentNode.next = node;
            node.prev = currentNode;
        }

        currentNode = node;

        nodeCount++;
    }

    /**
     * удаляет элемент под индексом
     */
    public void remove(int index)
    {
        if ( index == 0 )
        {
            clear();
        }

        Node<T> node = node( index );

        Node prev = node.prev;
        Node next = node.next;

        if ( prev instanceof Node )
        {
            prev.next = next;
        }
    }

    /**
     * очищает коллекцию
     */
    public void clear()
    {
        firstNode = currentNode = null;

        nodeCount = 0;
    }

    /**
     * возвращает размер коллекции
     */
    public int size()
    {
        return nodeCount;
    }

    /**
     * возвращает элемент под индексом
     */
    public T get(int index) throws NoSuchElementException
    {
        ListIterator<T> iterator = iterator();

        int position = 0;

        T element = null;

        boolean elementFound = false;

        while ( iterator.hasNext() && !elementFound )
        {
            element = iterator.next();

            if ( position++ == index )
            {
                elementFound = true;
            }
        }

        if ( !elementFound )
        {
            throw new NoSuchElementException( "no element with index " + index );
        }

        return element;
    }

    @Override
    public ListIterator<T> iterator()
    {

        return new ListIterator<>();
    }

    @Override
    public void forEach(Consumer<? super T> action)
    {

    }

    @Override
    public Spliterator<T> spliterator()
    {
        return null;
    }

    @Override
    public String toString()
    {
        String str = "";

        Iterator<T> iterator = iterator();

        while ( iterator.hasNext() )
        {
            str += iterator.next();
        }

        return "MyLinkedList{" +
                str +
                '}';
    }

    private Node<T> node(int index)
    {
        Node<T> x = firstNode;

        for ( int i = 0; i < index; i++ )
        {
            x = x.next;
        }

        return x;
    }

    private class Node<T>
    {
        Node<T> prev;
        Node<T> next;

        T value;

        public Node(T value)
        {
            this.value = value;
        }

        @Override
        public String toString()
        {
            return "Node{" +
                    "value=" + value +
                    '}';
        }
    }

    private class ListIterator<T> implements java.util.ListIterator<T>
    {
        int cursor;

        Node<T> lastReturned;
        Node<T> nextElement;

        public ListIterator()
        {
            cursor = -1;

            nextElement = (Node<T>) firstNode;
        }

        public ListIterator(int cursor)
        {
            this.cursor = cursor;

            nextElement = (Node<T>) node( cursor );
        }

        @Override
        public boolean hasNext()
        {
            return nextElement instanceof Node;
        }

        @Override
        public T next()
        {
            if ( hasNext() )
            {
                lastReturned = nextElement;

                try
                {
                    nextElement = lastReturned.next;
                }
                catch ( Throwable t )
                {
                }

                cursor++;
            }

            return lastReturned.value;
        }

        @Override
        public boolean hasPrevious()
        {
            return false;
        }

        @Override
        public T previous()
        {
            return null;
        }

        @Override
        public int nextIndex()
        {
            return cursor + 1;
        }

        @Override
        public int previousIndex()
        {
            return cursor;
        }

        @Override
        public void remove()
        {

        }

        @Override
        public void set(T t)
        {

        }

        @Override
        public void add(T t)
        {

        }
    }


}
