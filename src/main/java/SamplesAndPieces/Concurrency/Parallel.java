package SamplesAndPieces.Concurrency;

import io.reactivex.subjects.AsyncSubject;

import java.util.ArrayList;
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
 * критика ForkJoin
 * http://coopsoft.com/ar/CalamityArticle.html
 *
 * Вариант, имплементирующий обычные трэды и latch: 90 sec на проход.
 * Вариант с пулом потоков и кастомной синхронизацией занял 90 сек на один проход.
 * Вариант с одним потоком: 80 сек
 *
 * Вариант 1: если в строке [A] убрать synchronized, N = 2, время прохода - 8 сек. Странно, что не выскочило исключение из-за совместного доступа. Почеу?
 * Вариант 3, использующий тот же метод, без оператора synchronized: 70 сек на проход.
 *
 * Вариант 1 , без synchronized, N = 4: 6 сек
 * Вариант 1 , без synchronized, N = 5: 5 сек
 * Вариант 1 , без synchronized, N = 8: 7 сек
 *
 * Вариант 2 , без synchronized, N = 5: 5 сек
 * Вариант 2 , без synchronized, N = 8: 7 сек
 */
public class Parallel {

    static int processorsAvailable = Runtime.getRuntime().availableProcessors();

    public static void main (String[] args)
    throws Exception
    {
        /**
         * Размер массива
         */
        int size = 80_000_000;
        
        /**
         * количество прогонов функции
         */
        int N = 8;
        
        /**
         * 1 - параллельные вычисления
         * 2 - параллельные вычисления с пулом потоков
         * 3 - последовательные вычисления
         * 4 - вариант с ForkJoin
         * 5 - вариант с streams
         * 6 - вариант с Rx
         */
        int processingType = 4;
        
        double[] result = new double[size];
        
        Parallel parallelProcessor = new Parallel();
        
        int[] testArray = generateArray( size );
        
        long startTime = System.currentTimeMillis();
        
        for ( int i = 0; i < N; i++ ) {
            switch ( processingType ) {
                case 1:
                    result = parallelProcessor.processArray( testArray );
                    break;
                case 2:
                    result = parallelProcessor.processArray2( testArray );
                    break;
                case 3:
                    result = parallelProcessor.processArray( testArray, 1 );
                    break;
                case 4:
                    result = parallelProcessor.processArray4( testArray );
                    break;
                case 5:
                    result = parallelProcessor.processArray5( testArray );
                    break;
                case 6:
                    result = parallelProcessor.processArray6( testArray );
                    break;
            }
        }
        
        long endTime = System.currentTimeMillis();
        
        System.out.println( "Execution time (sec): " + ( endTime - startTime ) / 1000 );
    }
    
    double[] processArray (int[] arrToProcess) throws Exception
    {
        return processArray( arrToProcess, Runtime.getRuntime().availableProcessors() );
    }
    
    double[] processArray (int[] arrToProcess, int threadNumber)
    throws Exception
    {
        int size = arrToProcess.length;
        
        double[] result = new double[size];
        
        CountDownLatch latch = new CountDownLatch( threadNumber );
        
        int elementsPerThread = size / threadNumber;
        
        int rate = 0;
        
        for ( int i = 0; i < threadNumber; i++ ) {
            
            int from = elementsPerThread * rate++;
            int to = ( i == threadNumber - 1 ) ? size : elementsPerThread * rate;
            
            Thread thread = new Thread( () -> {
                
                for ( int j = from; j < to; j++ ) {
// [A]
//                    synchronized ( result ) {
                        result[j] = Math.sin( arrToProcess[j] ) + Math.cos( arrToProcess[j] );
//                    }
                }
                latch.countDown();
            } );
            thread.start();
        }
        
        latch.await();
        
        return result;
    }
    
    /**
     * Кастомная синхронизация потоков
     * @param arrToProcess
     * @return
     */
    double[] processArray2 (int[] arrToProcess)
    {
        int size = arrToProcess.length;
        
        double[] result = new double[size];

        AtomicInteger completedThreadsNumber = new AtomicInteger( processorsAvailable );
        
        ExecutorService pool = Executors.newFixedThreadPool( processorsAvailable );
        
        int elementsPerThread = size / processorsAvailable;
        
        int rate = 0;
        
        Supplier<Integer> updateCounter = () -> {
            int t = completedThreadsNumber.get();
            completedThreadsNumber.set( --t );
            return t;
        };
        
        for ( int i = 0; i < processorsAvailable; i++ ) {
            // разбить массив на равные части в соответсвии с количеством процессоров

            int from = elementsPerThread * rate++;
            int to = ( i == processorsAvailable - 1 ) ? size : elementsPerThread * rate;
            
            Thread thread = new Thread( () -> {

                for ( int j = from; j < to; j++ ) {
                    // [!] синхронизация дает просадку производительности
                    //synchronized ( result ) {
                        result[j] = compute( arrToProcess[j] );
                    //}
                }
                updateCounter.get(); // Здесь просто нужно вызвать "функцию" updateCounter()

            } );
            pool.submit( thread );
        }
        
        while ( completedThreadsNumber.get() > 0 ) {}
        pool.shutdown();
        
        return result;
    }

    // todo
    // https://itnan.ru/post.php?c=1&p=270943
    double[] processArray4 (int[] arrToProcess)
    {
        double[] result = new double[arrToProcess.length];

        new ForkJoinPool(  ).invoke( new ProcessTask( 100 ) ); // todo workload

        return result;
    }

    class ProcessTask extends RecursiveAction {

        private long workLoad;

        public ProcessTask(long workLoad) {
            this.workLoad = workLoad;
        }

        @Override
        protected void compute()
        {
            if ( workLoad > 16 ) {
                ArrayList<RecursiveAction> tasks = new ArrayList<>();
                tasks.addAll( create() );

                tasks.stream().forEach( ForkJoinTask::fork );
            } else {
                System.out.println("Doing workLoad myself: " + this.workLoad);
            }
        }

        private ArrayList<RecursiveAction> create()
        {
            ArrayList<RecursiveAction> tasks = new ArrayList<>();

            ProcessTask task = new ProcessTask(workLoad/2);

            // todo
            tasks.add( task );

            return tasks;
        }
    }

    // todo
    double[] processArray5 (int[] arrToProcess)
    {
        double[] result = new double[arrToProcess.length];

        return result;
    }

    // todo
    double[] processArray6 (int[] arrToProcess)
    {
        double[] result = new double[arrToProcess.length];

        return result;
    }
    
    static int[] generateArray (int size)
    {
        Random random = new Random();
        
        int[] a = new int[size];
        for ( int i = 0; i < size; i++ ) {
            a[i] = random.nextInt( 50 );
        }
        return a;
    }

    static double compute(int value)
    {
        return Math.sin( value ) + Math.cos( value );
    }
}
