package SamplesAndPieces.Concurrency;

import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class Atomic {
    public static void main (String[] args)
    {
        AtomicInteger atomicInteger = new AtomicInteger( 0 );
        
        Thread onUpdate = new Thread( () -> {
            System.out.println("atomic value updated to " + atomicInteger.get());
        } );
    
        Scanner scanner = new Scanner( System.in );
    
        int index = scanner.nextInt();
        
        atomicInteger.set( index );
        
        
    }
}
