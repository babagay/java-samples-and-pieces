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
    static void remainderOfNumbersDivision()
    {
        int firstVal = 21, secondVal = 8;

        double divisionResult = firstVal / secondVal;

        int divisionResultInt = (int) divisionResult;

        int remainder = firstVal - (secondVal * divisionResultInt);

        System.out.println( divisionResultInt );
        System.out.println( remainder );
    }

    /**
     * В переменной n хранится натуральное двузначное число.
     * Создайте программу, вычисляющую и выводящую на экран сумму цифр числа n.
     */
    static int sumDigits()
    {
        int a = 58;

        int A = a / 10;
        int B = a - (A * 10);

        return A + B;
    }

    /**
     * В переменной n хранится вещественное число с ненулевой дробной частью.
     * Создайте программу, которая округляет число n до ближайшего целого и выводящую результат на экран.
     */
    static void theNearestWhole(double v)
    {
        String[] integerAndFractionalPartsOfnumber = (new Double( v )).toString().split( "\\." );
        String[] fractionalPartDigitsArray = { };

        int t, tail = 0, intPart = 0, N;

        try
        {
            fractionalPartDigitsArray = integerAndFractionalPartsOfnumber[1].split( "" );
            intPart = Integer.parseInt( integerAndFractionalPartsOfnumber[0] );
        }
        catch ( Exception e )
        {
            // Дробная часть отсутсвует
        }

        for ( int i = fractionalPartDigitsArray.length - 1; i >= 0; i-- )
        {
            int j = i - 1;

            N = Integer.parseInt( fractionalPartDigitsArray[i] );

            if ( N >= 5 )
            {
                if ( j >= 0 )
                {
                    t = Integer.parseInt( fractionalPartDigitsArray[j] );
                    if ( t < 5 )
                    {
                        t++;
                        fractionalPartDigitsArray[j] = "" + t;
                    }
                }
            }

            tail = Integer.parseInt( fractionalPartDigitsArray[i] );
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

    /**
     * Переменная “weekend” истинна если сейчас выходной и переменная “vacation” истина если мы в отпуске.
     * Мы высыпаемся если сейчас выходной, либо мы в отпуске. Вернуть истинно, если мы высыпаемся:
     *   sleepIn(false, false) -> true
     *   sleepIn(true, false) -> false
     *   sleepIn(false, true) -> true
     */
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

    /**
     * Дано два целочисленных значения, вернуть их сумму. Но если они одинаковы, тогда вернуть удвоенную сумму данных чисел.
     *  sum(1, 2) → 3
     *  sum(3, 2) → 5
     *  sum(2, 2) → 8
     */
    static int sum(int a, int b)
    {
        return a == b ? (a + b) * 2 : a + b;
    }

    /**
     * Дано “int n”, вернуть абсолютную разницу между “n” и “21”, но вернуть удвоенную абсолютную разницу если n больше 21.
     *
     * diff21(19) → 2 ; diff21(-2) → 23
     * diff21(10) → 11; diff21(50) → 58
     * diff21(21) → 0
     */
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


    /**
     * У нас есть громкий говорящий попугай.
     * У нас есть параметр “hour” в диапазоне между 0..23, которое обозначает текущий час.
     * Мы в замешательстве когда попугай разговаривает, когда время до 7, и после 20.
     *
     * parrotTrouble(true, 6) → true
     * parrotTrouble(true, 7) → false
     * parrotTrouble(false, 6) → false
     */
    static boolean parrotTrouble(boolean talking, int hour)
    {
        if ( !talking ) return false;

        return hour < 7 || hour > 20;

    }


    /**
     * Дано два числа, вернуть истину если одно из них равняется “10” или их сумма равняется “10”.
     *
     * makes10(9, 10) → true
     * makes10(9, 9) → false
     * makes10(1, 9) → true
     */
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
