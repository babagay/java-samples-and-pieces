package SamplesAndPieces.Concurrency;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.schedulers.Schedulers;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Сделать библиотеку Observable
 * Она кидает сообщение : Свободно
 * <p>
 * Подписчики-потоки устремляются туда
 * <p>
 * У нее есть Atomic-поле
 * Чел занял библиотеку и изменилоь поле. Другие не зайдут
 * <p>
 * Чел покинул библиотеку - снова летит сообщение: Свободно
 * <p>
 * Толпа снова ломится туда
 * <p>
 * После выхода чел ждет рандомное число секунд (отдыхает)
 * <p>
 * [!] Можно стартануть потоки одновременно - CountDownLatch
 * <p>
 *
 * <p>
 */
public class Library {

    Room room;

    static Random random = new Random();
    static int min = 500;
    static int max = 1000;

    static final int WORKING_PERIOD_MS = 12000;

    public static void main(String[] args)
    throws InterruptedException
    {

        Library library = new Library();
        library.room = library.new Room();

        for ( int i = 0; i < 5; i++ ) {
            Thread thread = new Thread(
                    () ->
                            library.room.observable.subscribe(
                                    r -> {
                                        String name = "П" + Thread.currentThread().getName().replaceAll( "([a-zA-Z])", "" );

                                        if ( r.equals( "Room is free!" )
                                                ) {
                                            System.out.println( name + "" +
                                                    " пробует войти" );
                                            if ( library.room.takeRoom(
                                                    name ) ) {
                                                System.out.println( name
                                                        + " вошел!" );
                                                // Do some work
                                                int workPeriod = random.nextInt( (max - min) + 1 ) + min;
                                                Thread.sleep( workPeriod );
                                                System.out.println( name
                                                        + " работал (мс) " + workPeriod + " " );
                                                // and release
                                                library.room.releaseRoom
                                                        ( name );
                                                // Отдохнуть
                                                int rest = random.nextInt( (max - min) + 1 ) + min + 2000;
                                                Thread.sleep( rest );
                                                System.out.println( name
                                                        + " отдыхал (мс) " + rest );
                                            }
                                            else {
                                                System.out.println( name
                                                        + " не удалось войти" );
                                            }
                                        }
                                        else {
                                            System.out.println( "поток "
                                                    + name +
                                                    " отвергнут и ждет (" + r + ")" );
                                            int wait = random.nextInt( (
                                                    max - min) + 1 ) +
                                                    min;
                                            Thread.sleep( wait );
                                            System.out.println( "после "
                                                    + wait +
                                                    " мс " +
                                                    "ожидания поток " + name + " продолжает стучаться..." );
                                        }
                                    },
                                    e -> System.out.println( "ERROR" ),
                                    () ->
                                            System.out.println( "Complete" )

                            ) );
            thread.start();
        }

        Thread.sleep( WORKING_PERIOD_MS );

        System.out.println("рабочий день окончен");
    }

    class Room {

        AtomicBoolean blocked;

        Observable<String> observable;
        ConnectableObservable<String> isEmptyNotifier;

        Room()
        {
            blocked = new AtomicBoolean();
            blocked.set( false );
            createObservable();
        }

        /**
         * Schedulers.single() - все в одном потоке. Логика программы ломается, т.к. комнатой пользуется единственный поток. Конструкция вырождается в аналог Observable.create
         * Schedulers.computation() - вызов onNext() в отдельном потоке. Число потоков = числу ядер
         * Schedulers.newThread() - каждый вызов onNext() происходит в отдельном потоке
         */
        void createObservable()
        {
            observable = Observable.interval( 100, 100, TimeUnit.MILLISECONDS, Schedulers.newThread() )
                    .take( WORKING_PERIOD_MS, TimeUnit.MILLISECONDS )
                    //.takeWhile( aLong -> isRoomFree() )
                    .map( time -> {
                        if ( isRoomFree() ) {
                            return "Room is free!";
                        }
                        else {
                            return "buisy";
                        }
                    } );

//                        observable = Observable.create( emitter -> {
//                            if ( isRoomFree() )
//                            {
//                                emitter.onNext( "Room is free!" );
//                            }
//                        } );

//            isEmptyNotifier = observable.replay( Schedulers.newThread() );
//            isEmptyNotifier.connect();
        }

        boolean isRoomFree()
        {
            return blocked.get() == false;
        }

        /**
         * @return true if room become blocked. False if room is already occupied
         */
        boolean takeRoom(String name)
        {
            if ( isRoomFree() ) {
                blocked.set( true );
                System.out.println( "Room предоставляется для " + name );
                return true;
            }
            else {
                System.out.println( name + " отказано в доступе" );
            }

            return false;
        }

        void releaseRoom(String name)
        {
            System.out.println( name + " освобождает комнату" );
            blocked.set( false );
        }
    }
}
