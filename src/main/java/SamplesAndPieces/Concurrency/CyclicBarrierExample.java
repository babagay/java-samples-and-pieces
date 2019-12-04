package SamplesAndPieces.Concurrency;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * We have a big matrix of random integers.
 * We wanna know the total number of occurrences of a number in this matrix.
 * To get better performance, we used the divide and conquer technique.
 * We divided the matrix into five subsets and used a thread to look for the number in each subset -
 * These threads are objects of the Searcher class.
 *
 * We used a CyclicBarrier object to synchronize the completion of the five threads and
 * execute the Grouper task to process partial results and calculate the final one.
 * Grouper compiles results of each thread into one result.
 * Grouper is called only single time.
 *
 * Each time a thread arrives at the synchronization point, it calls the await() method to notify the CyclicBarrier.
 * CyclicBarrier puts the thread to sleep until all the threads reach the synchronization point.
 *
 * When all the threads have arrived, the CyclicBarrier object wakes up all the threads that were waiting.
 * Optionally, it creates a new thread represented by Grouper to do additional tasks.
 */
public class CyclicBarrierExample
{
    final static int ROWS=10000; // 10k
    final static int NUMBERS=1000;
    final static int SEARCH=-554;
    final static int PARTICIPANTS=5; // number of threads
    final static int LINES_PARTICIPANT=2000;

//    public static void main(String[] args)
//    {
//        MatrixMock matrix = new MatrixMock(ROWS, NUMBERS, SEARCH);
//
//        Results results = new Results(ROWS);
//        Grouper grouper = new Grouper(results);
//
//        // grouper acts when all threads will be on the barrier
//        CyclicBarrier barrier = new CyclicBarrier(PARTICIPANTS,grouper);
//
//        Searcher searcher[] = new Searcher[PARTICIPANTS];
//        for (int i = 0; i < PARTICIPANTS; i++)
//        {
//            searcher[i] = new Searcher(
//                    i * LINES_PARTICIPANT,
//                    i  * LINES_PARTICIPANT + LINES_PARTICIPANT,
//                    matrix,
//                    results,
//                    5,
//                    barrier);
//        }
//
//        Arrays.stream(searcher).forEach( runnable -> new Thread(runnable).start() ); // Lunch all threads
//
//        System.out.printf("Main: The main thread has finished.\n");
//    }


    public static void main(String[] args)
    {
        int[] input = {0, -1, -2, 2, 1};
        int[][] output = findPairsWithGivenDifference(input, 1);

        System.out.println(output);
    }

    static int findPosition(int[] arr, int y){
        for (int i = 0; i < arr.length; i++)
        {
            if (arr[i] == y){
                return i;
            }
        }

        return -1;
    }

    static int[][] findPairsWithGivenDifference(int[] arr, int k) {

        List<int[]> res = new ArrayList();

        int[][] result = {};

        int[] tmp = Arrays.copyOf(arr,arr.length);

        // todo
        // Здесь формируется структура типа y:2, y:4, y: 9
        // Но ее надо отсортить по возрастанию номера позиции (т.е. по value)
        // А потом превратить в последовательность 0,1,2,..n
        HashMap<Integer, Integer> yPos = new HashMap<>(); // Index by Y like [yVal: Position]

        for (int i = 0; i < arr.length; i++)
        {
            int x = arr[i];
            for (int j = 0; j < tmp.length; j++)
            {
                int y = tmp[j];
                if ( (x-y) == k ){
                    res.add(new int[]{x,y});
                    yPos.put(y,findPosition(arr, y)); // Store position of Y in basic array
                }
            }
        }

        // Convert LIst to array of int[]
        if (res.size() > 0){
            result = new int[res.size()][];
            for (int[] mass: res){ // Бежим по выборке
                int y = mass[1]; // Берем Y
                int pos = yPos.get(y); // Берем его позицию из ранее созданного индекса
                result[pos] = mass; // Ставим айтем из выборки на нужную позицию
            }
        }


        return result;
    }
}

class MatrixMock
{
    private final int data[][];

    MatrixMock(int size, int length, int number)
    {
        int counter = 0;
        data = new int[size][length];
        Random random = new Random();

        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < length; j++)
            {
                data[i][j] = random.nextInt(10);
                if (data[i][j] == number)
                {
                    counter++;
                }
            }
        }

        System.out.printf("Mock: There are %d ocurrences of number in generated data.\n", counter, number);
    }

    /**
     * returns the row if it exists
     */
    public int[] getRow(int row)
    {
        if ((row >= 0) && (row < data.length))
        {
            return data[row];
        }
        return null;
    }
}

class Results
{
    private final int data[];

    public Results(int size)
    {
        data = new int[size];
    }

    public void setData(int position, int value)
    {
        data[position] = value;
    }

    public int[] getData()
    {
        return data;
    }
}

/**
 * look for a number in the
 * determined rows of the matrix of random numbers.
 * Perform the calculations in run() method
 * and then, call CyclicBarrier.await() which means: 'Ive done; I have result'
 */
class Searcher implements Runnable
{
    private final int firstRow;
    private final int lastRow;
    private final MatrixMock mock;
    private final Results results;
    private final int number;

    private final CyclicBarrier barrier;

    public Searcher(int firstRow, int lastRow, MatrixMock mock,
                    Results results, int number, CyclicBarrier barrier)
    {
        this.firstRow = firstRow;
        this.lastRow = lastRow;
        this.mock = mock;
        this.results = results;
        this.number = number;
        this.barrier = barrier;
    }

    @Override
    public void run()
    {
        int counter;
        System.out.printf("%s: Processing lines from %d to %d.\n", Thread.currentThread().getName(), firstRow, lastRow);

        for (int i = firstRow; i < lastRow; i++)
        {
            int row[] = mock.getRow(i);
            counter = 0;
            for (int j = 0; j < row.length; j++)
            {
                if (row[j] == number)
                {
                    counter++;
                }
            }
            results.setData(i, counter);
        }

        System.out.printf("%s: Lines processed.\n",  Thread.currentThread().getName());

        try
        {
            barrier.await();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        catch (BrokenBarrierException e)
        {
            e.printStackTrace();
        }
    }
}

/**
 * Used to compile all result into single one
 */
class Grouper implements Runnable
{
    private final Results results;

    public Grouper(Results results){
        this.results=results;
    }

    @Override
    public void run() {
        int finalResult=0;
        System.out.printf("Grouper: Processing results...\n");

        int data[]=results.getData();
        for (int number:data){
            finalResult += number;
        }

        System.out.printf("Grouper: Total result: %d.\n", finalResult);
    }
}
