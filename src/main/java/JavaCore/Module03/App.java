package JavaCore.Module03;

import SamplesAndPieces.Sorting;
import scala.Int;
import scala.util.parsing.combinator.testing.Str;


import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Задание 1
 * Написать программу которая:
 * 1. на вход через консоль принимает размер массива
 * 2. на вход через консоль принимает массив чисел
 * 3. найти минимальное число в массиве и вывести в консоль (без использования сортировки)
 * 4. найти максимальное число в массиве и вывести в консоль (без использования сортировки)
 * 5. посчитать кол-во повторений числа 5 и вывести в консоль
 * 6. вывести в консоль отсортированный массив
 * Задание 2 (дополнительное)
 * Добавить к программе из задания 1 следующее:
 * 7. вывести в консоль максимальное кол-во повторений чисел в массиве
 * пример 1:
 * массив 1, 2, 3, 4, 1, 6.
 * Ответ - 2. Так как число 1 повторяется 2 раза
 * <p>
 * пример 2:
 * массив 1, 1, 1, 4, 6, 6.
 * Ответ - 3. Так как число 1 повторяется 3 раза. А число 6 повторяется 2 раза. Поскольку надо вывести максимум, выводим 3.
 * <p>
 * пример 3:
 * массив 2, 3, 3, 5, 5, 6
 * Ответ - 3. Так как 3 и 5 повторяются по 2 раза, неважно кого из них подсчитывать, цель - вывести максимум. В этом примере максимум повторений чисел является 2 раза.
 * <p>
 * 8. вывести в консоль минимальное кол-во повторений чисел в массиве
 * Задание 3 (дополнительное)
 * 9. Избавиться от пункта номер один. Массив в программе должен быть создан такого же размера как длина массива из консоли
 * пример:
 * 10, 10, 10, 10 тут размер массива 4;
 * 10, 10 тут размер массива 2
 *
 *
 */
public class App
{
    private static final int SOME_CONST = 5;

    private static boolean oneByOne = false;

    private static int arraySize = 0;

    private static Scanner scanner;

    private static  int[] userArray;

    private static Integer[] boxedArray;

    static
    {
        scanner = new Scanner( System.in );
    }

    public static void main(String[] args) throws Exception
    {
        /*
        // Варииант последовательного ввода элементов массива

        oneByOne = true;

        System.out.print( "Введите размер массива: " );

        // через консоль принимает размер массива
        arraySize = scanner.nextInt();

        if ( arraySize < 1 )
        {
            throw new Exception( "Array size must be greater than 0" );
        }

        userArray = new int[arraySize];

        // через консоль принимает массив чисел
        for ( int i = 0; i < arraySize; i++ )
        {
            System.out.print( "Введите " + (i + 1) + "-е целое число из " + arraySize + ": " );

            userArray[i] = scanner.nextInt();
        }

        boxedArray = Arrays.stream( userArray ).boxed().toArray( Integer[]::new );
        */


        // Вариант с вводом массива за один раз
        System.out.println("Введите стркоу целых чисел (разделитель - любой символ, например, пробел)");
        boxedArray = constructBoxedArrayFromIntSequence( scanner.nextLine() );


        // найти минимальное число в массиве и вывести в консоль (без использования сортировки)
        int min = getMinValueFromArray( boxedArray );
        System.out.println("Минимальное число: " + min);

        // найти максимальное число в массиве и вывести в консоль (без использования сортировки)
        int max = getMaxValueFromArray( boxedArray );
        System.out.println("максимальное число: " + max);

        // посчитать кол-во повторений числа 5 и вывести в консоль
        int consNumber = getNumberOfOverlapsWithConst( boxedArray, SOME_CONST );
        System.out.println( "Число " + SOME_CONST + " встречается в массиве " + consNumber + " раз" );

        // отсортировать массив
        Sorting.sort( "insertion", boxedArray );

        // вывести в консоль
        arrayToConsole( boxedArray );

        // вывести в консоль "максимальное кол-во повторений чисел в массиве"
        Map.Entry<Integer, Long> mostOftenFoundNumberAndFrequency = constructFrequencyMapStreamedAndGetFirst( boxedArray );

        System.out.println( "Число " + mostOftenFoundNumberAndFrequency.getKey() + " встречается в массиве " + mostOftenFoundNumberAndFrequency.getValue() + " раз(a)" );

        // вывести в консоль "минимальное кол-во повторений чисел в массиве"
        Map.Entry<Integer, Integer> leastOftenFoundNumberAndFrequency = constructFrequencyMapAndGetLeast( boxedArray );
        System.out.println( "Число " + leastOftenFoundNumberAndFrequency.getKey() + " встречается в массиве " + leastOftenFoundNumberAndFrequency.getValue() + " раз(a)" );



        if ( oneByOne ) scanner.close();
    }

    public static Integer[] constructBoxedArrayFromIntSequence ( String sequence )
    {
        return Arrays.stream( sequence.split( "\\D" ) ).mapToInt( Integer::parseInt ).boxed().toArray( it -> new Integer[it] );
    }


    /**
     * Сформировать карту "элемент массива": <сколько раз встречается в массиве> с испольхованием Stream API
     * Если несколько чисел имеют одинаковую частоту, вернется минимальное число
     */
    public static Map.Entry<Integer, Long> constructFrequencyMapStreamedAndGetFirst(Integer[] arr)
    {
        // Сперва (в результате действия Collect()) мы получаем карту Map<Integer, Long>
        // Затем сортируем полученную карту по значению
        // И берем первый элемент отсортированной карты
        Optional<Map.Entry<Integer, Long>> pair =
                Arrays.stream( arr )
                        .collect(
                                Collectors.groupingBy(
                                        Function.identity(),
                                        Collectors.counting()
                                )
                        )
                        .entrySet()
                        .stream()
                        .sorted( (a, b) -> b.getValue().compareTo( a.getValue() ) )
                        .findFirst();

        return pair.get();
    }

    /**
     * Сформировать карту типа "элемент массива": <сколько раз встречается в массиве>
     *     Если несколько чисел имеют одинаковую частоту, вернется минимальное число
     */
    public static Map.Entry<Integer, Integer> constructFrequencyMapAndGetLeast(Integer[] arr)
    {
        Map<Integer, Integer> map = new HashMap<>(  );

        for ( Integer item : arr )
        {
            Integer counter = map.get( item ) == null ? 0 : map.get( item );

            map.put( item, ++counter );
        }

        SortedSet<Map.Entry<Integer, Integer>> sortedSet = new TreeSet<>(
                Comparator.comparing( Map.Entry::getValue )
        );

        sortedSet.addAll( map.entrySet() );

        return sortedSet.first();
    }


    private static void arrayToConsole(Integer[] arr)
    {
        for ( int i = 0; i < arr.length; i++ )
        {
            System.out.println( arr[i] );
        }
    }

    private static int getMinValueFromArray(Integer[] arr) throws Exception
    {
        if ( arr.length == 0 )
        {
            throw new Exception( "array is empty" );
        }

        int min = arr[0];

        for ( int i = 0; i < arr.length; i++ )
        {
            if ( arr[i] < min )
            {
                min = arr[i];
            }
        }

        return min;
    }


    private static int getMaxValueFromArray(Integer[] arr) throws Exception
    {
        if ( arr.length == 0 )
        {
            throw new Exception( "array is empty" );
        }

        int max = arr[0];

        for ( int i = 0; i < arr.length; i++ )
        {
            if ( arr[i] > max )
            {
                max = arr[i];
            }
        }

        return max;
    }

    private static int getNumberOfOverlapsWithConst(Integer[] arr, int someNumber)
    {
        int consNumber = 0;

        for ( int i = 0; i < arr.length; i++ )
        {
            if ( arr[i] == someNumber )
            {
                consNumber++;
            }
        }

        return consNumber;
    }

    @Override
    public String toString()
    {
        return "App Java Core - module 3";
    }

    public int getFoo()
    {
        return foo;
    }

    public void setFoo(int foo)
    {
        this.foo = foo;
    }

    private int foo = 0;


}
