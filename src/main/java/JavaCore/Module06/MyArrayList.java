package JavaCore.Module06;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static java.lang.reflect.Array.newInstance;

/**
 * todo тесты:
 * add
 * get
 * remove
 * clear
 * size
 *
 * todo toArray()
 *
 * todo оставить либо count, либо currentIndex, либо переименовать currentIndex
 *
 * https://ru.stackoverflow.com/questions/264255/generic-%D0%B8-%D0%BC%D0%B0%D1%81%D1%81%D0%B8%D0%B2%D1%8B
 */

@FunctionalInterface
interface ArraySupplier<T> {
    T[] get(int length);
}

public class MyArrayList<T> implements Iterable<T>
{
    private T[] elements;

    private final static int INITIAL_SIZE = 10;

    private final static int INCREASE_RATE_EXPONENT = 1;

    private static final double CRITICAL_CAPACITY_RATE = 0.75;

    private int size;

    /**
     * Количество элементов в массиве
     */
    private int count = 0;

    private int currentIndex = 0;

    private MyArrayListIterator<T> arrayListIterator;

    public MyArrayList()
    {
       this(INITIAL_SIZE);
    }

    public MyArrayList(int size)
    {
        this.size = size;

        initArray();

        arrayListIterator = new MyArrayListIterator<>();
    }

    @Override
    public String toString()
    {
        String[] s = new String[1];
        s[0] = "";

        Arrays.stream( elements ).forEach( e -> {
            if ( e != null )
            {
                s[0] += "    " + e.toString() + "\n";
            }
        } );

        return "MyArrayList {\n" +
                "  [Size]: " + count + "\n" +
                "  [Elements]: \n" + s[0] +
                '}';
    }

    /**
     * добавляет элемент в конец
     */
    public void add(T element)
    {
        checkUpSize();

        elements[currentIndex++] = element;

        count++;
    }

    /**
     * https://www.mkyong.com/java/java-how-to-join-arrays/
     */
    public void remove(int index) throws IndexOutOfBoundsException
    {
        if ( index >= count )
            throw new IndexOutOfBoundsException("Не верный индекс");

        T[] starting = Arrays.copyOfRange( elements, 0, index );
        T[] tailing = Arrays.copyOfRange( elements, index + 1, currentIndex );

        Class<T> elementClass = getComponentType( starting );

        final T[] result = (T[]) newInstance( elementClass, starting.length + tailing.length );

        int offset = 0;

        System.arraycopy( starting, 0, result, offset, starting.length );

        offset += starting.length;
        System.arraycopy( tailing, 0, result, offset, tailing.length );

        elements = result;

        count--;

        currentIndex--;
    }

    /**
     * очищает коллекцию
     */
    public void clear()
    {
        this.size = INITIAL_SIZE;
        initArray();

        count = 0;
        currentIndex = 0;
    }

    /**
     * возвращает размер коллекции (количество элементов)
     */
    public int size()
    {
        return count;
    }

    /**
     * возвращает элемент под индексом
     */
    public T get(int index)
    {
        return elements[index];
    }

    public Iterator<T> iterator()
    {
        return arrayListIterator;
    }

    /**
     * todo. Сейчас метод возвращает Object[]
     *
     * (A)
     *  Class<? extends T[]> newType - параметр метода
     *  T[] copy = (T[]) Array.newInstance(newType.getComponentType(), newLength)
     *
     *
     *   elements.getClass()
     *
     *   (B)
     *   final T[] result = (T[]) Array.newInstance(arrays[0].getClass().getComponentType(), length);
     *   https://www.mkyong.com/java/java-how-to-join-arrays/
     *
     *   (C)
     *    Arrays.copyOf(elementData, size);
     */

    public T[] toArray()
    {
        T[] copy = (T[]) Array.newInstance( getComponentType( elements ), size() );

        for ( int j = 0; j < size(); j++ )
        {
            copy[j] = elements[j];
        }

        return copy;
    }

    static <T> T[] newArray(int len, T...arr)
    {
        return Arrays.copyOf(arr,len);
    }

    /**
     * [проблема выведения типа Т]
     *
     * Такой код работает
     *  MyArrayList<Rose> bouquetRose = new MyArrayList<>( Rose[]::new );
     *  Но здесь использован перегруженный конструктор, в который явно передается тип Rose[]
     */
    private   ArraySupplier<T> supplier = null;

    public MyArrayList(ArraySupplier<T> supplier)
    {
        this.supplier = supplier;

        elements = supplier.get( 10 );
    }

    /**
     * При таком варианте нужно знать имя класса
     *
     * Class<T> fl = null;
     *
     * try
     * {
     * fl = (Class<T>) Class.forName( "JavaCore.Module05Poly.Interface.Flower" );
     * }
     * catch ( ClassNotFoundException e )
     * {
     * e.printStackTrace();
     * }
     *
     * T[] g = (T[]) Array.newInstance( fl, 1 );
     *
     * return g;
     */

    /** @Foo(type = "T", tags = {"Tag one"}) */

    /**
     * Создать массив
     */
    private void initArray()
    {
        elements = (T[]) new Object[size];
    }

    /**
     * Увеличить размер массива в 2 раза,
     * в случае достижения порога
     */
    private void checkUpSize()
    {
        int threshold = (int) (size * CRITICAL_CAPACITY_RATE);

        if ( currentIndex > threshold ){

            size <<= INCREASE_RATE_EXPONENT;

            elements = Arrays.copyOf( elements, size );
        }
    }

    private Class<T> getComponentType(T[] array)
    {
        Class arrayClass = array.getClass();
        Class componentClass = arrayClass.getComponentType();

        return (Class<T>) componentClass;
    }

    private class MyArrayListIterator<T> implements Iterator<T>
    {
        private int currentIndex;
        private T current;

        @Override
        public boolean hasNext()
        {
            return currentIndex != size();
        }

        @Override
        public T next()
        {
            if ( currentIndex > size() ) throw new NoSuchElementException( "No such element" );

            current = (T) elements[currentIndex];

            currentIndex++;

            return current;
        }

        MyArrayListIterator()
        {
            clear();
        }

        // [!] нужны ли эти методы c currentIndex
        public int getCurrentIndex()
        {
            return currentIndex;
        }

        public int increaseCurrentIndex()
        {
            return ++currentIndex;
        }

        public int decreaseCurrentIndex()
        {
            return --currentIndex;
        }

        public void setCurrentIndex(int index)
        {
            currentIndex = index;
        }

        public void remove()
        {
            currentIndex = 0;
        }
    }
}
