package SamplesAndPieces.Rx;

import io.reactivex.Observable;

import java.util.concurrent.TimeUnit;

public class Interval {
    
    public static void main (String[] args) throws InterruptedException
    {
        Observable.interval( 100, 100, TimeUnit.MILLISECONDS )
                  .map( time -> "time: " + time )
                  .take( 10 )
                  .subscribe( r -> System.out.println(r));
        
        Thread.sleep(1000000);
    }
}
