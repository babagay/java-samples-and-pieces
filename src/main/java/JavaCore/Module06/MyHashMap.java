package JavaCore.Module06;


import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * https://www.youtube.com/watch?v=Z0JMABjXnww
 *
 * http://info.javarush.ru/translation/2013/10/22/%D0%9A%D0%B0%D0%BA-%D1%80%D0%B0%D0%B1%D0%BE%D1%82%D0%B0%D0%B5%D1%82-HashMap-%D0%B2-Java.html
 * https://habrahabr.ru/post/128017/
 * https://www.youtube.com/watch?v=Z0JMABjXnww
 * https://javadevblog.com/stek-s-ispol-zovaniem-svyazannogo-spiska-na-java.html
 */
public class MyHashMap<T, K>
{
    private final double THRESHOLD = 0.75;
    private final int INIT_BACKETS_COUNT = 16;
    private int bucketsCount;

    private Node<T, K>[] buckets;

    private int nodeCount = 0;

    public MyHashMap()
    {
        bucketsCount = INIT_BACKETS_COUNT;

        initBackets();
    }

    /**
     * добавляет пару ключ + значение
     */
    public void put(T key, K value)
    {
        Node<T, K> node = new Node<>( key, value );

        int buscketIndex = getBucketByHash( node );

        boolean nodeIsReplaced = false;

        if ( buckets[buscketIndex] == null )
        {
            buckets[buscketIndex] = node;
            nodeCount++;
        }
        else
        {
            Node previousNode = null;

            // Аналог while ( it.hasNext() )
            for ( Iterator<Node> it = getIterator( buckets[buscketIndex] ); it.hasNext(); )
            {
                Node node1 = it.next();

                if ( node1.getKey().equals( node.getKey() ) )
                {   // Найдена нода с таким же ключом
                    Node nextNode = node1.getNext();
                    node.setNext( nextNode );

                    if ( previousNode != null )
                    {
                        previousNode.setNext( node );
                    }

                    nodeIsReplaced = true;
                }

                previousNode = node1;
            }

            if ( !nodeIsReplaced )
            {
                previousNode.setNext( node );
                nodeCount++;
            }
        }

        increaseBucketSize();
    }

    private void increaseBucketSize()
    {
        int threshold = (int) (bucketsCount * THRESHOLD);

        if ( size() > threshold ){

            bucketsCount <<= 1;

            buckets = Arrays.copyOf( buckets, bucketsCount );
        }
    }

    /**
     * удаляет пару по ключу
     */
    public void remove(T key)
    {
        NodeIterator<Node> iterator;
        Node current, prev;

        int bucketIndex = getBucketByHash( key.hashCode() );

        current = buckets[bucketIndex];

        if ( current != null )
        {
            if ( current instanceof Node )
            {
                if ( current.getKey().equals( key ) )
                {
                    buckets[bucketIndex] = null;
                    if ( current.getNext() != null )
                    {
                        buckets[bucketIndex] = current.getNext();
                    }
                }
                else
                {
                    iterator = getIterator( current );

                    boolean itemFound = false;

                    while ( iterator.hasNext() && !itemFound )
                    {
                        current = iterator.next();

                        if ( current.getKey().equals( key ) )
                        {
                            prev = iterator.prev();
                            prev.setNext( iterator.next() );
                            itemFound = true;
                        }
                    }
                }
            }
        }
    }

    /**
     * очищает коллекцию
     */
    public void clear()
    {
        nodeCount = 0;

        bucketsCount = INIT_BACKETS_COUNT;

        initBackets();
    }

    /**
     * возвращает размер коллекции
     */
    public int size()
    {
        return nodeCount;
    }

    /**
     * возвращает значение(K value) по ключу
     */
    public K get(T key)
    {
        int hash = key.hashCode();

        int bucketIndex = getBucketByHash( hash );

        if ( buckets[bucketIndex] == null )
        {
            return null;
        }

        if ( !(buckets[bucketIndex] instanceof Node) )
        {
            return null;
        }

        Iterator<Node> iterator = getIterator( buckets[bucketIndex] );

        while ( iterator.hasNext() )
        {
            Node node = iterator.next();

            if ( node.getKey().equals( key ) )
            {
                return (K) node.getValue();
            }
        }

        return null;
    }

    private void initBackets()
    {
        buckets = new Node[bucketsCount];
    }

    private int getBucketByHash(Node<T, K> node)
    {
        return getBucketByHash( node.getKey().hashCode() );
    }

    private int getBucketByHash(int hash)
    {
        return hash & (bucketsCount - 1);
    }

    private NodeIterator<Node> getIterator(Node node)
    {
        NodeIterator<Node> iterator = new NodeIterator( node );

        return iterator;
    }

    private class Node<T, K> implements Map.Entry
    {
        private final T key;
        private K value;
        private final int hash;
        private Node<T, K> next = null;

        public Node(T key, K value)
        {
            this.key = key;
            this.value = value;
            hash = key.hashCode();
        }

        public Node<T, K> getNext()
        {
            return next;
        }

        public void setNext(Node<T, K> next)
        {
            this.next = next;
        }

        @Override
        public T getKey()
        {
            return key;
        }

        @Override
        public K getValue()
        {
            return value;
        }

        @Override
        public Object setValue(Object value)
        {
            this.value = (K) value;

            return value;
        }

        @Override
        public String toString()
        {
            return "Node{" +
                    "key=" + key +
                    ", value=" + value +
                    ", hash=" + hash +
                    ", next=" + next +
                    '}';
        }
    }

    private class NodeIterator<Node> implements Iterator
    {
        private MyHashMap.Node previous;
        private MyHashMap.Node current;
        private Supplier<MyHashMap.Node> prevNodeLambda;

        private Function<MyHashMap.Node, Supplier> f = param -> {

            Supplier<MyHashMap.Node> supplier = () -> (MyHashMap.Node) param;

            return supplier;
        };

        private NodeIterator()
        {
            previous = null;

            prevNodeLambda = null;

        }

        public NodeIterator(MyHashMap.Node initialNode)
        {
            this();

            current = initialNode;
        }

        @Override
        public boolean hasNext()
        {
            return current != null;
        }

        @Override
        public Node next()
        {
            MyHashMap.Node node = current;

            savePrev( previous );

            previous = current;

            current = null;

            if ( node instanceof MyHashMap.Node &&
                    node.getNext() instanceof MyHashMap.Node )
            {
                current = node.getNext();
            }

            return (Node) node;
        }

        public Node prev()
        {
            return (Node) prevNodeLambda.get();
        }

        private void savePrev(MyHashMap.Node previous)
        {
            prevNodeLambda = f.apply( previous );
        }
    }

}
