package SamplesAndPieces;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Алгоритмы сортировки
 * <p>
 * TODO сделать сортировку каим-то другим типом
 * See sorting.at
 * <p>
 * Radix Sort
 * https://habrahabr.ru/post/260343/
 * http://opendatastructures.org/ods-cpp/11_2_Counting_Sort_Radix_So.html
 * <p>
 * [radix sort]
 * function bucket-sort(A, n) is
 * buckets ← новый массив из n пустых элементов
 * for i = 0 to (length(A)-1) do
 * вставить A[i] в конец массива buckets[msbits(A[i], k)]
 * for i = 0 to n - 1 do
 * next-sort(buckets[i])
 * return Конкатенация массивов buckets[0], ..., buckets[n-1]
 */

public class Sorting
{
    public static void main(String[] args)
    {
        Integer[] arr = listToArray( getListOfIntsRandom() ); // создаем массив c помощью генератора случайных чисел getListOfIntsRandom()

        long start = System.currentTimeMillis(); // засекаем время старта

        insertionSort(arr); // сортировка! selectionSort(arr)

        long end = System.currentTimeMillis(); // засекаем время завершения

        System.out.println( end - start ); // вывод результата

//        System.out.println( Arrays.toString(arr) );

    }

    public static void sort(String sortingType, Integer[] arr)
    {
        switch ( sortingType ){
            case "selection":
                selectionSort( arr );
            case "insertion":
                insertionSort( arr );
            default:
                radixSort( arr );
             break;
        }
    }


    static ArrayList<Integer> getListOfIntsRandom()
    {
        ArrayList<Integer> list = new ArrayList();

        int min = 0;
        int max = 1200; // 120K ::

        for (int i = min; i < max; i++)
        {
            list.add( new Random().nextInt((max - min) + 1) + min );
        }

        return list;
    }

    static ArrayList<Integer> getListOfInts()
    {
        ArrayList<Integer> list = new ArrayList();

        list.add(1);
        list.add(10);
        list.add(4);
        list.add(3);
        list.add(6);
        list.add(8);
        list.add(15);
        list.add(2);
        list.add(5);
        list.add(35);
        list.add(7);
        list.add(1);
        list.add(6);

        return list;
    }


    private static Integer[] listToArray(List list)
    {
        Integer[] arr = new Integer[list.size()];

        for (int i = 0; i < list.size(); i++) {
            arr[i] = Integer.parseInt(String.valueOf(list.get(i)));
        }

        return arr;
    }

    /**
     * TODO
     */
    private static void radixSort(Integer[] arr)
    {



    }



    /**
     * Ставим указатель в начало массива
     * Ищем позицию наименьшего элемента и
     *      меняем его с тем, на котором указатель
     * Сдвигаем указатель и
     *      повторяем
     *
     * Время сортировки при 120К элементов: 50 с
     */
    private static void selectionSort(Integer[] arr)
    {
        int pointer; // текущий указатель массива
        int element; // используется для свопа

        int minElemIndex;
        int minElem; // текущий минимальный элемент

        int j;

        // [?] почему arr.length - 1 вместо arr.length
        for ( pointer = 0; pointer < arr.length - 1; pointer++)
        {
            minElemIndex = pointer;
            minElem = arr[pointer];

            // находим мин элемент. Для его хранения используем временную переменную minElem
            for (j = pointer+1; j < arr.length; j++)
            {
                if( arr[j] < minElem ){
                    minElem = arr[j];
                    minElemIndex = j;
                }
            }

            // поменять местами мин элемент и тот, на котором сейчас указатель
            arr[minElemIndex] = arr[pointer];
            arr[pointer] = minElem;
        }
    }

    /**
     * Пометить второго игрока
     * Вытащить его из группы
     * Сравнить с левыми партнерами
     * Найти индекс наименьшего
     * Если надо , сдвинуть игроков
     * Вернуть игрока в массив
     * Переместить полотенце
     *
     * Время сортировки при 120К элементов: 5.5 с
     */
    private static void insertionSort(Integer[] arr)
    {
        // [!] вынесение объявления пременных из цикла дает выигрыш в 100 мс при 120К элементах массива
        int i,j;
        int pointerToPlacePlayer;
        int markedPlayer;

        for (i = 1; i < arr.length; i++)
        {
            pointerToPlacePlayer = i;
            markedPlayer = arr[pointerToPlacePlayer]; // вытащить игрока
            j = i - 1;

            // найти положение наименьшего элемента
            // [!] j >= 0 нужно ставить слева, тогда при j = -1 проверка даст false и компилятор сразу пойдёт дальше
            //     Если же написать ( arr[j] > markedPlayer && j >= 0 ), тогда при j = -1 будет nullPointerException при попытке взятия arr[-1] элемента
            while (j >= 0 && arr[j] > markedPlayer)
            {
                pointerToPlacePlayer = j;
                arr[j + 1] = arr[j];
                j--;
            }

            // вернуть игрока в команду
            arr[pointerToPlacePlayer] = markedPlayer;
        }
    }

    // todo сравнить с моей реализацией
    private static void insertionSortFromBook(Integer[] arr)
    {
        int i,j;

        for (int out = 1; out < arr.length; out++)
        {
            int temp = arr[out];
            int in = out;

            while (in > 0 && arr[in-1] >= temp){
                arr[in] = arr[in-1];
                --in;
            }

            arr[in] = temp;
        }
    }






























}
