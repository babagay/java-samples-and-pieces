package SamplesAndPieces.Concurrency;

import JavaCore.Module05Poly.Garden.GardenFlower;
import JavaCore.Module05Poly.Garden.Rose;
import JavaCore.Module05Poly.Garden.Tulip;
import JavaCore.Module05Poly.Interface.Flower;

import java.util.concurrent.*;

public class Future {
    
    static Callable<Flower> callable;
    static FutureTask task;
    
    public static void main (String[] args) throws ExecutionException, InterruptedException
    {
        callable = () -> {
            System.out.println( "callable works" );
            
            return new Rose( 120 );
        };
        
        System.out.println( "Start" );
        
        task = new FutureTask( callable );
        
        // FutureTask task = new FutureTask( () -> new Tulip() ); // OK
        
        Thread thread = new Thread( () -> {
            // todo: проблема в том, что даже в отдельном потоке задержка в 4 с останавливает общий поток
            try {
                System.out.println("starting task...");
                Thread.sleep( 4000 );
                task.run(); // [!] без вызова run() таска зависает в статусе NEW и главный поток зависает тоже. Callable
                // не вызывается
            }
            catch ( InterruptedException e ) {  }
            
        } );
        thread.run();
        
        // task.cancel( true ); // [!] не понятно, зачем
        
        worker( task );
        
        System.out.println( "End" ); // Выведется раньше, чем результат фьюче таска
    }
    
    static void worker (FutureTask<Flower> task)
    {
        ExecutorService pool = Executors.newFixedThreadPool( 2 );
        
        Thread thread = new Thread( () -> {
            
            try {
                Thread.sleep( 2000 );
            }
            catch ( InterruptedException e ) {
                e.printStackTrace();
            }
            
            GardenFlower f = null;
            try {
                f = (GardenFlower) task.get();
            }
            catch ( InterruptedException e ) {
                e.printStackTrace();
            }
            catch ( ExecutionException e ) {
                e.printStackTrace();
            }
            System.out.println( f.getPrice() );
        } );
        
        pool.submit( thread );
        
        pool.shutdown(); // [!] Если делать через ThreadPool, нужен вызов shutdown(), иначе основной поток зависнет
    }
    
}
