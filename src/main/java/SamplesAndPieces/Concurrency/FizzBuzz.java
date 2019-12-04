package SamplesAndPieces.Concurrency;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Phaser;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

/**
 * https://leetcode.com/problems/fizz-buzz-multithreaded/
 * Цикл от 1 до n
 * На каждое число д.б. вывод в виде fizz|buzz|fizzbuzz|currentValue
 * Инстанс FizzBuzz передается четырем потокам
 * Thread A will call fizz()
 * Thread B will call buzz()
 * ...
 * Как разделить обязанности?
 * Если currentValue делится на 3, дергать первый поток??
 * Не понятно: все потоки отдельно выполняют весь набор итераций или на каждой итерации вызывается один из потоков?
 *
 * [!]
 * Результат: 1 2 Fizz(3) 4 Buzz(5) Fizz(6) 7 8 Fizz(9) Buzz(10) Fizz(12) 11 13 14 Buzz(15) Fizz(15) FizzBuzz(15)
 * Стабильность: отутсвует. периодически порядок цифр ломается. Особенно, если вставить в любом месте println()
 * Проблема: на числе 15 должен быть выведен ТОЛЬКО FizzBuzz, но срабатывают и два других кейса
 * [!] Как вариант, Можно попробовать складывать результат в массив (потокобезопасный список),
 *      где каждый результат идет в ячейку, у которой индекс соответсвует номеру текущей итерации.
 *      но. как гарантировать, что на числе 15 получится именно FizzBuzz?
 *      Получается, нужно добавить весовой коэффициент каждому потоку.
 *      У потока Number: 0
 *      У потока Fizz: 1
 *      У потока Buzz: 2
 *      У потока FizzBuzz: 3
 *      тогда, массиве результатов храним ноду, у которой есть Weight = 0 и result
 *      Когда ноду модифицирует поток Number, он оставляет Weight как есть
 *      Когда её изменяет Fizz, он проверяет, если Weight < 1, сделать Weight = 1 и обновить result
 *      Когда ноду изменяет Buzz, он проверяет, если Weight < 2, сделать Weight = 2 и обновить result
 *      Когда ноду изменяет FizzBuzz, он проверяет, если Weight < 3, сделать Weight = 3 и обновить result
 *      По завершении, проитерировать список и распечатать result каждой ноды
 *      Потокобезопасность надо обеспечивать самому, дополнительно синхронизируясь по каждой ноде
 */
public class FizzBuzz
{
    public static void main(String[] args) throws InterruptedException, ExecutionException
    {
        int N = 15;

        FizzBuzz fizzBuzz = new FizzBuzz(N);

        Phaser phaser = new Phaser();

//        IntConsumer printNumber1 = System.out::println;
//        fizzBuzz.number(printNumber);

        // ExecutorService pool = Executors.newFixedThreadPool(4);

//        phaser.arrive();
//        phaser.arrive();
//        phaser.arrive();
//        phaser.arrive();



        new Thread(new Worker(phaser,
                i -> {
                    try
                    {
                        fizzBuzz.fizz(i);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
                , fizzBuzz)).start();

        new Thread(new Worker(phaser,
                i -> {
                    try
                    {
                        fizzBuzz.buzz(i);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
                , fizzBuzz)).start();

        new Thread(new Worker(phaser,
                i -> {
                    try
                    {
                        fizzBuzz.fizzbuzz(i);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
                , fizzBuzz)).start();

        new Thread(new Worker(phaser,
                fizzBuzz::number,
                fizzBuzz)).start();

        // [!] если здесь раскоментить, то нужно раскоментить ту же строку и внутри потоков
        // phaser.arriveAndDeregister();
        // Внутри потоков можно как раскоментить дерегистер, так и закоментить

        System.out.println("Phase count is " + phaser.getPhase());


//        Runnable printFizz = () -> {
//
//            for (int i = 1; i < fizzBuzz.n; i++)
//            {
//                try
//                {
//                    fizzBuzz.fizz(i);
//                    phaser.arriveAndAwaitAdvance();
//                }
//                catch (InterruptedException e)
//                {
//                    e.printStackTrace();
//                }
//            }
//            phaser.arriveAndDeregister();
//        };
//
//        Runnable printBuzz = () -> {
//            phaser.arrive();
//            for (int i = 1; i < fizzBuzz.n; i++)
//            {
//                try
//                {
//                    fizzBuzz.buzz(i);
//                    phaser.arriveAndAwaitAdvance();
//                }
//                catch (InterruptedException e)
//                {
//                    e.printStackTrace();
//                }
//            }
//        };
//
//        Runnable printFizzBuzz = () -> {
//            phaser.arrive();
//            for (int i = 1; i < fizzBuzz.n; i++)
//            {
//                try
//                {
//                    fizzBuzz.fizzbuzz(i);
//                    phaser.arriveAndAwaitAdvance();
//                }
//                catch (InterruptedException e)
//                {
//                    e.printStackTrace();
//                }
//            }
//        };
//
//        Runnable printNumber = () -> {
//            phaser.arrive();
//            for (int i = 1; i < fizzBuzz.n; i++)
//            {
//                fizzBuzz.number(i);
//                phaser.arriveAndAwaitAdvance();
//
//            }
//        };

//        pool.submit(printFizz);
//        pool.submit(printBuzz);
//        pool.submit(printFizzBuzz);
//        pool.submit(printNumber);


    }

    private int n;

    public FizzBuzz(int n)
    {
        this.n = n;
    }

    // printFizz.run() outputs "fizz".
    // fizz(Runnable printFizz)
    public void fizz(int u) throws InterruptedException
    {
        if ((u % 3) == 0)
        {
            System.out.print("Fizz(" + u + ") ");
        }
    }

    // printBuzz.run() outputs "buzz".
    // buzz(Runnable printBuzz)
    public void buzz(int u) throws InterruptedException
    {
        if ((u % 5) == 0)
        {
            System.out.print("Buzz(" + u + ") ");
        }
    }

    // printFizzBuzz.run() outputs "fizzbuzz".
    public void fizzbuzz(int u) throws InterruptedException
    {
        if ((u % 3) == 0 && (u % 5) == 0)
        {
            System.out.print("FizzBuzz(" + u + ") ");
        }
    }

    public void number(int u)
    {
        if ((u % 3) != 0 && (u % 5) != 0)
        {
            System.out.print(u + " ");
        }
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    // public void number(IntConsumer printNumber) throws InterruptedException {
    //    printNumber.accept(n);
    //  }

    static class Worker implements Runnable
    {

        Phaser phaser;
        Consumer<Integer> consumer;
        FizzBuzz fizzBuzz;

        public Worker(Phaser pha, Consumer<Integer> consumer, FizzBuzz fizzBuzz)
        {
            phaser = pha;
            phaser.register();

            // [!] Если это раскоментить, весь порядок вывода нарушается
            // System.out.println("UnarrivedParties " + phaser.getUnarrivedParties());

            this.consumer = consumer;
            this.fizzBuzz = fizzBuzz;
        }

        @Override
        public void run()
        {
            for (int i = 1; i <= fizzBuzz.n; i++)
            {
                consumer.accept(i);
                phaser.arriveAndAwaitAdvance(); // после каждой итерации ждать остальных
            }

            phaser.arriveAndDeregister(); // по завершении снять регистрацию
        }
    }
}
