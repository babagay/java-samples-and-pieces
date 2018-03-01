package SamplesAndPieces.Concurrency;

import JavaCore.Module05Poly.Garden.GardenFlower;
import JavaCore.Module05Poly.Garden.Rose;
import JavaCore.Module05Poly.Garden.Tulip;
import JavaCore.Module05Poly.Interface.Flower;

import java.util.concurrent.*;

public class Future {
    
    public static void main (String[] args) throws ExecutionException, InterruptedException
    {
        Callable<Flower> callable = () -> new Rose( 120 );
        
        FutureTask task = new FutureTask( callable ); // OK
        
        // FutureTask task = new FutureTask( () -> new Tulip() ); // OK
        
        task.run(); // [!] без вызова run() таска зависает в статусе NEW и главный поток зависает тоже
        
        // task.cancel( true ); // [!] не понятно, зачем
        
        worker( task );
    }
    
    static void worker (FutureTask<Flower> task)
    {
        ExecutorService pool = Executors.newFixedThreadPool( 2 );
        
        Thread thread = new Thread( () -> {
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
