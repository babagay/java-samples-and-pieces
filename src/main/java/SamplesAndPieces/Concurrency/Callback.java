package SamplesAndPieces.Concurrency;

import JavaCore.Module05Poly.Garden.Rose;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class Callback
{
    static Rose doSomeWork() throws InterruptedException
    {
        System.out.println("doSomeWork start working");

        Thread.sleep( 42 );

        System.out.println("doSomeWork end working");

        return new Rose( 200 );
    }


    public static void main(String[] args) throws ExecutionException, InterruptedException
    {

        /**
         * Пример колбэка
         *
         * [A] результат работы программы без futureTask.get():
         * Экзекьютор запущен
         * Вызываем doSomeWork()
         * doSomeWork start working
         * ... задержка
         * doSomeWork end working
         *
         * [B] результат работы программы c futureTask.get():
         * Экзекьютор запущен
         * Вызываем doSomeWork()
         * doSomeWork start working
         * Экзекьютор остановлен
         * ... задержка
         * doSomeWork end working
         * результат фьючерса: Rose{price=200}
         */
        ExecutorService executorService = Executors.newCachedThreadPool();

        // FutureTask<String> futureTask = new FutureTask( App::doSomeWork ); // OK

        FutureTask<Rose> futureTask = new FutureTask( () -> {
            System.out.println("Вызываем doSomeWork() ");
            return doSomeWork();
        } );

        executorService.execute( futureTask );

        System.out.println("Экзекьютор запущен");

        executorService.shutdown();

        System.out.println("Экзекьютор остановлен");

        System.out.println( "результат фьючерса: " + futureTask.get() );
    }
}
