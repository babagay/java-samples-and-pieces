package SamplesAndPieces.Precourse;

/**
 * Tasks for independent work (Precource, Module 2. Data types)
 */
public class Arithmetical
{
    /**
     * В переменных q и w хранятся два натуральных числа. Создайте программу, выводящую на экран результат деления q на w с остатком.
     * Пример вывода программы (для случая, когда в q хранится 21, а в w хранится 8):21/8 = 2 и 5 в остатке
     */
    static void ostatok()
    {
        int q = 21, v = 8;

        double t = q/v;

        int tInt = (int) t;

        int ostatok = q - (v * tInt);

        System.out.println("Ostatok()");
        System.out.println(tInt);
        System.out.println(ostatok);
    }

    /**
     * В переменной n хранится натуральное двузначное число.
     * Создайте программу, вычисляющую и выводящую на экран сумму цифр числа n.
     */
    static int sumDigits()
    {
        int a = 58;

        int A = (int) a / 10;
        int B = a - (A * 10);

        return A + B;
    }

    /**
     * В переменной n хранится вещественное число с ненулевой дробной частью.
     * Создайте программу, которая округляет число n до ближайшего целого и выводящую результат на экран.
     */
    static void theNearestWhole(double v)
    {
        String[] fractionalArr = { };

        String[] s = (new Double( v )).toString().split( "\\." );

        int t, tail = 0, intPart = 0;

        try
        {
            fractionalArr = s[1].split( "" );
            intPart = Integer.parseInt( s[0] );
        }
        catch ( Exception e )
        {
        }

        for ( int i = fractionalArr.length - 1; i >= 0; i-- )
        {
            int j = i - 1;

            int N = Integer.parseInt( fractionalArr[i] );

            if ( N >= 5 )
            {
                if ( j >= 0 )
                {
                    t = Integer.parseInt( fractionalArr[j] );
                    if ( t < 5 )
                    {
                        t++;
                        fractionalArr[j] = "" + t;
                    }
                }
            }

            tail = Integer.parseInt( fractionalArr[i] );
        }

        if ( tail >= 5 )
        {
            intPart++;
        }

        System.out.println( "int part of : " + intPart );
    }

    /**
     * В переменной n хранится натуральное трёхзначное число.
     * Создайте программу, вычисляющую и выводящую на экран сумму цифр числа n.
     */
    static int sumTheDigitsNumber(int n)
    {
        int summ = 0,
                t,
                d,
                digit = 0,
                number;

        do
        {
            t = (int) Math.pow( 10, ++digit );

            d = n - (n / t) * t;

            number = (int) (d / Math.pow( 10, digit - 1 ));

            summ += number;

        }
        while ( (double) n / t > 1.0 );

        return summ;
    }


    static boolean sleepIn(boolean weekday, boolean vacation)
    {
        boolean alive = false;

        boolean weekend = !weekday;

        if ( weekend || vacation )
        {
            alive = true;
        }

        return alive;
    }


    static int sum(int a, int b)
    {
        return a == b ? (a + b) * 2 : a + b;
    }


    static int diff21(int n)
    {
        final int threshold = 21;

        int diff;

        if ( n > 0 )
        {
            if ( n >= threshold )
            {
                diff = n - threshold;
            }
            else
            {
                diff = threshold - n;
            }
        }
        else
        {
            diff = n * (-1) + threshold;
        }

        if ( n > threshold ) return 2 * diff;

        return diff;
    }



    static boolean parrotTrouble(boolean talking, int hour)
    {
        if ( !talking ) return false;

        if ( hour < 7 || hour > 20 ) return true;

        return false;
    }



    static boolean makes10(int A, int B)
    {
        boolean result;

        final int treshold = 10;

        if ( A == treshold || B == treshold )
        {
            result = true;
        }
        else
        {
            result = A + B == treshold;
        }

        return result;
    }

}
