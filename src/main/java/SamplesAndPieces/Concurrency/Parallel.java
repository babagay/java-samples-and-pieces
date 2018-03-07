package SamplesAndPieces.Concurrency;

import io.reactivex.subjects.AsyncSubject;

import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

import static SamplesAndPieces.Concurrency.Callback.doSomeWork;

/**
 * Executors
 * https://tproger.ru/translations/java8-concurrency-tutorial-1/
 * Latch
 * https://code.i-harness.com/ru/q/214543
 *
 * Результат: 25 sec на проход (с параллельными вычислениями)
 */
public class Parallel {

    public static void main(String[] args)
    throws Exception
    {
        /**
         * Размер массива
         */
        int size = 80_000_000;

        /**
         * количество прогонов функции
         */
        int N = 2;

        /**
         * 1 - параллельные вычисления
         * 2 - параллельные вычисления с пулом потоков
         * 3 - последовательные вычисления
         */
        int processingType = 1;

        double[] result;

        Parallel parallelProcessor = new Parallel();

        int[] testArray = generateArray( size );

        long startTime = System.currentTimeMillis();

        for ( int i = 0; i < N; i++ ) {
            switch ( processingType ){
                case 1:
                    result = parallelProcessor.processArray( testArray );
                    break;
                case 2:
                    break;
                case 3:
                    break;
            }
        }

        long endTime = System.currentTimeMillis();

        System.out.println( "Execution time (sec): " + (endTime - startTime)/1000 );
    }

    double[] processArray(int[] arrToProcess)
    throws Exception
    {
        int size = arrToProcess.length;

        double[] result = new double[size];



//        Callable<double[]>[] callableArr = new Callable[1];
//
//        AtomicInteger completedThreadsNumber = new AtomicInteger( 0 );

//        AsyncSubject<Integer> arbiter = AsyncSubject.create();
//
//        arbiter.subscribe(
//                x -> {
//                },
//                c -> {
//                },
//                () -> {
//                    // System.out.println( "calculation finished" );
//                    callableArr[0] = () -> result;
//                } );






        int availableProcessors = Runtime.getRuntime().availableProcessors();

        CountDownLatch latch = new CountDownLatch( availableProcessors );

        int elementsPerThread = size / availableProcessors;

        int rate = 0;

//        Supplier<Integer> updateCounter = () -> {
//            int t = completedThreadsNumber.get();
//            completedThreadsNumber.set( ++t );
//            if ( t == availableProcessors )
//                arbiter.onComplete();
//
//            return t;
//        };

        for ( int i = 0; i < availableProcessors; i++ ) {

            int from = elementsPerThread * rate++;
            int to = (i == availableProcessors - 1) ? size : elementsPerThread * rate;

            Thread thread = new Thread( () -> {

                for ( int j = from; j < to; j++ ) {

                    synchronized ( result ) {
                        result[j] = Math.sin( arrToProcess[j] ) + Math.cos( arrToProcess[j] );
                    }
                }
                // updateCounter.get();
                latch.countDown();
            } );
            thread.start();
        }

        latch.await();

        return result;
    }

    static int[] generateArray(int size)
    {
        Random random = new Random(  );

        int[] a = new int[size];
        for ( int i = 0; i < size; i++ ) {
            a[i] = random.nextInt( 50 );
        }
        return a;
    }
}
