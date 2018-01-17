package JavaCore.Module06;

import java.lang.reflect.Array;
import java.util.Arrays;

import static java.lang.reflect.Array.newInstance;

/**
 * https://ru.stackoverflow.com/questions/264255/generic-%D0%B8-%D0%BC%D0%B0%D1%81%D1%81%D0%B8%D0%B2%D1%8B
 */

@FunctionalInterface
interface ArraySupplier<T> {
    T[] get(int length);
}

public class MyArrayList<T>
{
    private T[] elements;

    private final static int INITIAL_SIZE = 10;

    private final static double INCREASE_RATE = 2;

    private int size;

    /**
     * Количество элементов в массиве
     */
    private int count = 0;

    private int currentIndex = 0;

    public MyArrayList()
    {
       this(INITIAL_SIZE);
    }

    public MyArrayList(int size)
    {
        this.size = size;

        initArray();

//        clear(); // [?]
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

    // todo удаляет элемент под индексом
    public void remove(int index)
    {

    }

    /**
     * очищает коллекцию
     */
    public void clear()
    {
        this.size = INITIAL_SIZE;
        initArray();
    }

    /**
     * возвращает размер коллекции
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



    public T[] toArray()
    {
        return elements;
    }

    static <T> T[] newArray(int len, T...arr)
    {
        return Arrays.copyOf(arr,len);
    }

    /**
     * Такой код работает
     *  MyArrayList<Rose> bouquetRose = new MyArrayList<>( Rose[]::new );
     * Но здесь явно передается тип Rose[]
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

    private void initArray()
    {
        elements = (T[]) new Object[size];
    }

    private void checkUpSize()
    {
        int threshold = (int) (size * 0.75);

        if ( currentIndex > threshold ){

            // todo увеличить размер в INCREASE_RATE раз
        }

    }
}
