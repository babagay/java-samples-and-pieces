package SamplesAndPieces.Concurrency;


import io.reactivex.Observable;

import java.util.Random;
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
 * Чел покинул библиотеку - снова летит сообщение Свободно
 * <p>
 * Толпа снова ломится туда
 * <p>
 * После выхода чел ждет рандомное число секунд (отдыхает)
 * <p>
 * [!] Можно стартануть потоки одновременно - CountDownLatch
 *
 * рабочий день окончен
 *
 * todo many subscribers situation
 */
public class Library
{

    Room room;

    static Random random = new Random();
    static int min = 500;
    static int max = 1000;

    public static void main(String[] args)
    {

        Library library = new Library();
        library.room = library.new Room();


        for ( int i = 0; i < 5; i++ )
        {
            Thread thread = new Thread( () -> {
                library.room.observable.subscribe( r -> {

                    if ( r.equals( "Room is free!" ) )
                    {
                        String name = Thread.currentThread().getName();
                        System.out.println( name + " пробует войти" );
                        if ( library.room.takeRoom( name ) ){
                            System.out.println(name + " вошел");
                            // Do some work
                            int workPeriod = random.nextInt((max - min) + 1) + min;
                            Thread.sleep( workPeriod );
                            System.out.println(name + " работал " + workPeriod + " с");
                            // and release
                            library.room.releaseRoom( name );
                            // Отдохнуть
                            Thread.sleep( random.nextInt((max - min) + 1) + min );
                        }
                    } else
                        Thread.sleep( random.nextInt((max - min) + 1) + min );
                } );
            } );
            thread.start();
        }
    }

    class Room
    {

        AtomicBoolean blocked;

        Observable observable;

        Room()
        {
            blocked = new AtomicBoolean();
            blocked.set( false );
            createObservable();
        }

        void createObservable()
        {
            observable = Observable.create( emitter -> {
                while ( isRoomFree() )
                {
                    emitter.onNext( "Room is free!" );
                }
            } );
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
            if ( isRoomFree() )
            {
                blocked.set( true );
                System.out.println( "Room предоставляется для " + name );
                return true;
            }
            else
            {
                System.out.println(name + " отказано в доступе");
            }

            return false;
        }

        void releaseRoom(String name){
            System.out.println(name + " освобождает комнату");
            blocked.set( false );
        }
    }
}
