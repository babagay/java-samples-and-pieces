package JavaCore.Module06;

import java.util.NoSuchElementException;


public class MyStack<T>
{

    private int nodeCount = 0;
    private Node<T> first;

    /**
     * добавляет элемент
     */
    public void push(T value)
    {
        first = new Node<>( value, first );
        nodeCount++;
    }

    /**
     * удаляет элемент под индексом
     */
    public void remove(int index)
    {
        if ( index >= nodeCount )
        {
            throw new NoSuchElementException( "" );
        }

        Node<T> preX = null;
        Node<T> x = first;

        for ( int i = 0; i <= index; i++ )
        {
            preX = x;
            x = x.next;
        }

        preX.next = x.next;

        nodeCount--;
    }

    /**
     * очищает коллекцию
     */
    public void clear()
    {
        first = null;
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
     * возвращает первый элемент в стеке (LIFO)
     */
    public T peek()
    {
        return first instanceof Node ? first.element : null;
    }

    /**
     * возвращает первый элемент в стеке и удаляет его из коллекции
     */
    public T pop()
    {
        Node<T> elemFirst = first;

        remove( 0 );

        return elemFirst.element;
    }

    private class Node<T>
    {
        T element;

        Node<T> next;

        public Node(T element, Node<T> next)
        {
            this.element = element;
            this.next = next;
        }

        public String toString()
        {
            return element + "";
        }
    }
}

