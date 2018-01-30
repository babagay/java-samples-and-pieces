package JavaCore.Module06;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyQueue<T> implements Iterable
{
    private Node<T> first;
    private Node<T> last;

    private int nodeCount = 0;

    /**
     * добавляет элемент в конец
     */
    public void add(T value)
    {
        if ( nodeCount == 0 )
        {
            first = new Node<T>( value, null, null );
            last = first;
        }

        if ( nodeCount == 1 )
        {
            last = new Node<T>( value, first, null );
            first.next = last;
        }

        if ( nodeCount > 1 )
        {
            Node<T> tmp = last;
            last = new Node<>( value, last, null );
            tmp.next = last;
        }

        nodeCount++;
    }

    /**
     * удаляет элемент под индексом
     */
    public void remove(int index)
    {
        Node node = node( index );

        Node previousNode = node.prev;

        if ( previousNode instanceof Node )
        {
            previousNode.next = node.next;
        }

        nodeCount--;

        if ( index == nodeCount - 1 )
        {
            last = node.next;
        }

        if ( index == 0 )
        {
            first = node.next;
        }
    }

    /**
     * очищает коллекцию
     */
    public void clear()
    {
        first = last = null;
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
     * возвращает первый элемент в очереди (FIFO)
     */
    public T peek()
    {
        return first.element;
    }

    /**
     * возвращает первый элемент в очереди и удаляет его из коллекции
     */
    public T poll()
    {
        try
        {
            return first.element;
        }
        finally
        {
            if ( first.next instanceof Node )
            {
                first = first.next;
                nodeCount--;
            }
        }
    }

    @Override
    public Iterator iterator()
    {
        return new Iter();
    }

    @Override
    public String toString()
    {
        Iterator iterator = iterator();

        String str = "";

        while ( iterator.hasNext() )
        {
            str += iterator.next() + "\n";
        }

        return "MyQueue{" +
                "nodeCount=" + nodeCount + "\n" + str +
                '}';
    }

    private Node<T> node(int index)
    {
        Node<T> x = first;

        for ( int i = 0; i < index; i++ )
        {
            x = x.next;
        }

        return x;
    }

    private class Node<T>
    {
        T element;
        Node<T> next;
        Node<T> prev;

        Node(T element, Node<T> prev, Node<T> next)
        {
            this.element = element;
            this.prev = prev;
            this.next = next;
        }

        public String toString()
        {
            return element + "";
        }
    }

    private class Iter<Node> implements Iterator<T>
    {
        private int cursor;
        private MyQueue<T>.Node<T> lastReturned;
        private MyQueue<T>.Node<T> next;

        Iter()
        {
            clear();
        }

        @Override
        public boolean hasNext()
        {
            return cursor < nodeCount - 1;

            // return next instanceof MyQueue.Node; // [!] Аналогично
        }

        @Override
        public T next()
        {
            if ( cursor++ == nodeCount ) throw new NoSuchElementException( "No such element" );

            lastReturned = next;

            next = (lastReturned instanceof MyQueue.Node) ? lastReturned.next : null;

            return lastReturned.element;
        }

        private void clear()
        {
            cursor = -1;
            next = first;
            lastReturned = null;
        }
    }
}
