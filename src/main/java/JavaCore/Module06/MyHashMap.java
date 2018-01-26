package JavaCore.Module06;


import java.util.Iterator;
import java.util.Map;

/**
 * https://www.youtube.com/watch?v=Z0JMABjXnww
 *
 http://info.javarush.ru/translation/2013/10/22/%D0%9A%D0%B0%D0%BA-%D1%80%D0%B0%D0%B1%D0%BE%D1%82%D0%B0%D0%B5%D1%82-HashMap-%D0%B2-Java.html
 https://habrahabr.ru/post/128017/
 https://www.youtube.com/watch?v=Z0JMABjXnww
 https://javadevblog.com/stek-s-ispol-zovaniem-svyazannogo-spiska-na-java.html

 fixme при дебаге в массиве лежит, якобы, нода, но у нее не видно свойство next
 *
 * Задание 5 - HashMap
 * Написать свой класс MyHashMap как аналог классу HashMap.
 * <p>
 * Нужно делать с помощью односвязной Node.
 * <p>
 * Не может хранить две ноды с одинаковых ключами одновременно.
 * <p>
 * Методы
 * put(T key, K value) добавляет пару ключ + значение
 * <p>
 * remove(T key) удаляет пару по ключу
 * <p>
 * clear() очищает коллекцию
 * <p>
 * size() возвращает размер коллекции
 * <p>
 * get(T key) возвращает значение(K value) по ключу
 */
public class MyHashMap<T, K>
{
    private final double THRESHOLD = 0.75;
    private final int INIT_BACKETS_COUNT = 16;
    private int bucketsCount;

    private Node<T,K>[] buckets;

    private int nodeCount = 0;

    public MyHashMap()
    {
        bucketsCount = INIT_BACKETS_COUNT;

        initBackets();
    }

    /**
     * todo добавляет пару ключ + значение
     */
    /**
     * создаем ноду
     * ьерем хэш
     * получаем корзину
     * перебираем список на предмет ноды с заданным ключом
     * и либо заменяем, либо вставляем в конец новую ноду
     * [?] в чем хранятся ноды - в массиве или списке? Просто так, в куче , как отдельно созданные объекты
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
            for ( Iterator<Node> it = getIterator(  buckets[buscketIndex] ); it.hasNext(); )
            {
                Node node1 = it.next();

                System.out.println( node1.getKey() );

                if ( node1.getKey().equals( node.getKey() ) )
                {
                    Node nextNode = node1.getNext();
                    node.setNext( nextNode );

                    if ( previousNode != null ){
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


        // todo if THRESHOLD ... увеличить количество корзин

    }

    /**
     * todo   удаляет пару по ключу
     */
    public void remove(T key)
    {

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
     * todo  возвращает значение(K value) по ключу
     */
    public K get(T key)
    {
        // взять хэш, получить корзину, перебирать список

        int hash = key.hashCode();

        int bucketIndex = getBucketByHash( hash );

        if ( buckets[bucketIndex] == null )
            return null;

        if (  !(buckets[bucketIndex] instanceof Node) )
            return null;

        Iterator<Node> iterator = getIterator(  buckets[bucketIndex] );

        while ( iterator.hasNext() ){
            Node node = iterator.next();

            if ( node.getKey().equals( key ) )
                return (K) node.getValue();
        }

        return null;
    }


    private void initBackets()
    {
        buckets = new Node[bucketsCount];
    }

    // todo
    private int getBucketByHash(Node<T, K> node)
    {
        int hash = node.getKey().hashCode();

        return 0;
    }

    // todo
    private int getBucketByHash(int hash)
    {
        return 0;
    }

    private  NodeIterator<Node> getIterator(Node node)
    {
        NodeIterator<Node> iterator = new NodeIterator(node);

        return iterator;
    }

    private class Node<T, K> implements Map.Entry
    {
        private final T key;
        private K value;
        private final int hash;
        private Node<T,K> next = null;
        public int foo = 9;

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
    }

    private class NodeIterator<Node> implements Iterator {

        private MyHashMap.Node current;

        public NodeIterator(MyHashMap.Node initialNode)
        {
            current = initialNode;
        }

        @Override
        public boolean hasNext()
        {
            return current != null;
        }

        @Override
        public Object next()
        {
            MyHashMap.Node node = current;

            current = null;

            if ( current instanceof MyHashMap.Node &&
                    current.getNext() instanceof MyHashMap.Node )
            {
                current = current.getNext();
            }

            return node;
        }
    }

}
