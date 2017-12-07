package JavaCore.Module04;

/**
 *
 * [практика]
 * Написать рекурсивную функцию возведения числа-Х в степень-N
 * Даны два целых числа A и В (каждое в отдельной строке). Выведите все числа от A до B включительно, в порядке возрастания
 *
 * Больше задач здесь: https://habrahabr.ru/post/275813/
 *
 */
public class Extra
{

    public static void main(String[] args) throws Exception
    {
        System.out.println("2 в степени 3: " + power( 2, 8));

        System.out.println("Вывод чисел в диапазоне [A,B]");
        printNumbersRange(17,21);

        detectPrimality(19);
    }

    public static double power(int X, int N)
    {
        if ( N == 0 )
            return 1;
        else if ( N < 0 )
            return 1.0 / powerRecursively( X, -N, 1 );
        else
            return powerRecursively( X, N, 1 );
    }

    /**
     * Рекурсивная функция возведения в степень
     * [!] Хвостовая рекурсия
     */
    private static int powerRecursively(int X, int N, int acc)
    {
        if ( N == 0 )
        // Условие выхода из рекурсии
            return acc;
        else
            return powerRecursively( X, --N, X * acc );
    }

    /**
     * числа от A до B включительно, в порядке возрастания или убывания
     */
    private static int printNumbersRange(int A, int B)
    {
        System.out.println(A);

        // Условие выхода (базовый случай), вариант 1. В этой ситуации можно убрать проверку в строке S2, оставив только else,
        // что позволяет убрать return в конце функции
        // if ( A == B ) return A;

        if ( A < B )
            return printNumbersRange( ++A, B );
        else if ( A > B ) // S2
            return printNumbersRange( --A, B );

        // Условие выхода (базовый случай), вариант 2. Явное добавление if( A == B ) ничего не меняет.
        return A;
    }


    // Не работает
    private static String drawRectagle(int x, int y)
    {
        System.out.println("+ ");

        if ( y == 0 ) return ""; // Basic case
        // {

        if ( x > 1 )
            return drawRectagle( x - 1, y ) + "+ ";

            return drawRectagle( x, y - 1 ) + "\n";
        // }
    }

    public static void detectPrimality(int A) throws Exception
    {
        if(A <= 0)
            throw new Exception( "A must be greater then zero" );

        boolean numberIsSimple = isNumberPrime( A, 2 ); // Чтобы снизить сложность с О(n) до О(log n), надо не уменьшать, последовательно увеличивать делитель и прекратить рекурсию, когда он перевалит за половину числа А

        System.out.println("Число " + A + " простое: " + numberIsSimple);
    }

    /**
     * Проверка числа на простоту
     */
    private static boolean isNumberPrime(int A, int divider)
    {
        if ( A == 1 || divider > A/2 ) // Базовый случай
            return true;

        else if ( (A % divider) == 0 ) // Базовый случай
            return false;

        return isNumberPrime( A, ++divider ); //  Шаг рекурсии / рекурсивное условие
    }


}
