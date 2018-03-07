package SamplesAndPieces.Concurrency;

import io.reactivex.subjects.AsyncSubject;

public class Parallel {
    
    public static void main (String[] args) throws InterruptedException
    {
        int size = 13;
        
        int[] a = generateArray( size );
    
        // todo киать некст, как только == 4 , кинуть комплит.
        // В обработчике комплита кинуть событие завершения вычислений,
        // которое отловить в основнм потоке. Но как создать ретурн?
        AsyncSubject<Integer> s = AsyncSubject.create();
        s.onNext(1);
        s.onNext(2);
        s.subscribe(
                System.out::println,
                c -> {},
                () -> System.out.println("STOP"));
        s.onComplete();
        s.onNext(3);
   
      
        
        
        Integer[] result = new Integer[size];
    
        int availableProcessors = Runtime.getRuntime().availableProcessors();
    
        int workerDoneCount = availableProcessors;
    
        int elementsPerThread = size/availableProcessors;
        
        int rate = 0;
        
        for ( int i = 0; i < availableProcessors; i++ ) {
            
            int from = elementsPerThread * rate++;
            int to = (i == availableProcessors - 1) ? size : elementsPerThread * rate;
            
            Thread thread = new Thread( () -> {
        
                for ( int j = from; j < to; j++ ) {
            
                    synchronized ( result ) {
                        result[j] = a[j] * a[j];
                    }
                }
            } );
            thread.start();
        }
        
        
        
        Thread.sleep( 1300 );
        for ( int i = 0; i < size; i++ ) {
            System.out.println(i + ": " + result[i]);
        }
    }
    
    static int[] generateArray(int size){
        int[] a = new int[size];
        for ( int i = 0; i < size; i++ ) {
            a[i] = i;
        }
        return a;
    }
}
