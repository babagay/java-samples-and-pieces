package SamplesAndPieces.Concurrency;

import java.util.Date;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// Программа с джобами и потоками.
// Есть воркеры пул принтеров, исполняющих работу. Принтеров намного меньше, чем воркеров.
// Т.о., все воркеры конкурируют за использование принтеров.
// Если принтер занят, воркер спит в ожидании.
// Состояние принтеров (свободен/занят) хранится в freePrinters.
// Во время изменения состояния freePrinters блокируется, чтобы другие воркеры не попытались его изменить.
// Для синхронизаци используется Semaphore.
// [!] Пришлось добавить защиту от попадания отрицательного значения в assignedPrinter.
// Эта защита, также, гарантия того, что воркер получит в пользование принтер.
// И это работает без семафора.
// Использование семафора же дает возможность дополнительно ограничивать использование принтеров,
// в том числе, динамически - через количество разрешений (permits).
// При permits = 0 использование принтеров прекращается.
// При permits = 1 м.б. использован только один принтер и т.д.
// Т.о., имеет смысл задавать число permits меньше или равным числу принтеров.
public class SemaphoreExample
{
    private final Semaphore semaphore;
    private final boolean freePrinters[];
    private final Lock lockPrinters;

    public static void main(String[] args)
    {
        SemaphoreExample example = new SemaphoreExample();

        // Produce 10 threads
        for (int i = 0; i < 10; i++)
        {
            Thread printJob = new Thread(() -> {
                System.out.printf("%s: Going to print a job\n", Thread.currentThread().getName()); // start printing
                example.printJob("Document-" + Thread.currentThread().getName());
                System.out.printf("%s: The document has been printed\n", Thread.currentThread().getName()); // end printing
            });
            printJob.start();
        }
    }

    public SemaphoreExample()
    {
        lockPrinters = new ReentrantLock();
        freePrinters = new boolean[3];
        for (int i = 0; i < 3; i++)
        {
            freePrinters[i] = true;
        }
        semaphore = new Semaphore(2);
    }

    public void printJob(Object document)
    {
        int assignedPrinter = -1;  // index of assigned Printer

        try
        {
            // [!] такое решение НЕ гарантирует нормальную работу.
            // Валятся исключения из-за того, что в assignedPrinter попадают значения -1.
            // В дополнение нужно юзать бесконечный цикл.
            // if( semaphore.tryAcquire(1, 2, TimeUnit.SECONDS) )
            //    semaphore.acquire();

            semaphore.acquire(); // [!] запрашивать семафор нужно здесь, а не внутри while()

            // [!] Решение работает, но зачем, тогда, семафор? РАботает и без него
            while (assignedPrinter < 0)
            {  // If All printers are hold, Worker should wait
                assignedPrinter = getPrinter(); // Request a free printer

                if (assignedPrinter < 0)
                    TimeUnit.SECONDS.sleep(1);
            }

            // simulate printing process
            long duration = (long) (Math.random() * 10);
            System.out.printf("%s - %s: PrintQueue: Printing a Job '" + document + "' in Printer %d during %d seconds\n",
                    new Date(), Thread.currentThread().getName(),
                    assignedPrinter, duration);
            TimeUnit.SECONDS.sleep(duration); // Sleeping emulates a job processing, which takes some time

        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        finally
        {
            semaphore.release();
            freePrinters[assignedPrinter] = true; // ArrayIndexOutOfBoundsException could be
            System.out.println("_Printer " + assignedPrinter + " has been released");
        }
    }

    /**
     * get the index of free printer and assign it to print the virtual job
     * (make it hold)
     */
    private int getPrinter()
    {
        int index = -1;

        lockPrinters.lock();

        for (int i = 0; i < freePrinters.length; i++)
        {
            if (freePrinters[i])
            {
                index = i;
                freePrinters[i] = false;
                System.out.println("_Printer " + i + " has been hold");
                break;
            }
        }

        lockPrinters.unlock();

        return index;
    }
}
