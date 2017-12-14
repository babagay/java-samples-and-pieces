package JavaCore.Module04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Задание 1
 * Написать функцию которая считает в консоли до числа Х.
 * Аргументы функции: число Х
 * <p>
 * Например Х = 5.
 * Вывод программы:
 * 1
 * 2
 * 3
 * 4
 * 5
 * <p>
 * Задание 2
 * Написать функцию drawRectangle которая рисует в консоли прямоугольник из символов '+'
 * Аргументы функции: ширина прямоугольника в символах, высота прямоугольника в символах
 * <p>
 * Например 3 на 2
 * Вывод программы:
 * + + +
 * + + +
 * <p>
 * Задание 3
 * Перегрузить функцию drawRectangle (задание 2) таким образом,
 * чтобы она могла принимать на вход только 1 параметр (ширина квадрата) и рисовать квадрат с равными сторонами
 * <p>
 * Например 2
 * Вывод программы:
 * + +
 * + +
 * <p>
 * Например 3
 * Вывод программы:
 * + + +
 * + + +
 * + + +
 * <p>
 * Задание 4
 * Написать функцию getMax которая принимает на вход два аргумента в виде чисел. А возвращает максимальное из этих двух.
 * Также, необходимо перегрузить эту функцию для работы с разными числовыми типами переменных (int, float)
 * Задание 5
 * Решить задачу 1, без использования циклов. Используя рекурсию.
 * Задание 6
 * Решить задачу 2, без использования циклов. Используя рекурсию.
 * Задание 7 (дополнительно)
 * Написать программу, в которой выполнены все шесть предыдущих заданий.
 * Каждое задание выполняется в отдельной функции. В пределах этой же функции происходит ввод с консоли необходимых данных.
 * Программа спрашивает у пользователя какую задачу он хочет решить (от 1 до 6).
 * Затем программа вызывает соответствующую функцию для решения этой задачи.
 * По окончанию решения задачи, программа спрашивает пользователя, хочет ли он продолжить решать задачи. Если да - опять спрашивает какую задачу
 *
 */
public class App
{
    private static BufferedReader bufferedReader;

    private static String userInput = "";

    private static Scanner scanner;

    static
    {
        bufferedReader = new BufferedReader( new InputStreamReader( System.in ) );

        scanner = new Scanner( System.in );
    }

    public static void main(String[] args) throws Exception
    {
        do
        {
            clearConsole();

            printMenu();

            try
            {
                switch ( scanner.nextInt() )
                {
                    case 1:
                        enumerateNumbersTo( 5 );
                        break;
                    case 2:
                        drawRectangle( 10, 20 );
                        break;
                    case 3:
                        drawRectangle( 3 );
                        break;
                    case 4:
                        getMaxNumber( 2.0, 2.0 );
                        break;
                    case 5:
                        printNumberSequenceRecursively( 50 );
                        break;
                    case 6:
                        drawRectangleRecursively(10, 5);
                        break;
                    default:
                        invalidInputHandler();
                        break;
                }
            }
            catch ( InputMismatchException e )
            {
                invalidInputHandler();
            }

            continueMessage();
        }
        while ( getUserInput().equals( "y" ) ? true : false );

        scanner.close();
    }


    /**
     * считает в консоли до числа Х.
     */
    public static void enumerateNumbersTo(int X)
    {
        for ( int i = 0; i < X;  )
        {
            System.out.println(++i);
        }
    }

    /**
     * рисует в консоли прямоугольник из символов '+'
     * todo валидация
     */
    public static void drawRectangle(int W, int H)
    {
        for ( int j = 0; j < H; j++ )
        {
            for ( int i = 0; i < W; i++ )
            {
                drawSymbol();
            }

            jumpToNewLine();
        }
    }


    /**
     * рисует квадрат
     */
    public static void drawRectangle(int W)
    {
        drawRectangle( W, W );
    }

    public static void getMaxNumber(Number A, Number B) throws Exception
    {
        Number result;

        if ( A instanceof Integer && B instanceof Integer )
        {
            result = getMax(A.intValue(),B.intValue());
        }

        else if ( A instanceof Float && B instanceof Float )
        {
            result = getMax(A.floatValue(), B.floatValue());
        }

        else if ( A instanceof Double && B instanceof Double )
        {
            result = getMax(A.doubleValue(), B.doubleValue());
        }

        else
            throw new Exception( "Недопустимый тип числа" );

        if ( result.intValue() == 0 )
        {
            System.out.println("Числа равны");
        }
        else
            System.out.println("Максимальное число: " + result);
    }

    /**
     * max int
     */
    private static int getMax(int A, int B)
    {
        return A > B ? A : ( B > A ? B : 0 );
    }

    /**
     * max float
     */
    private static float getMax(float A, float B)
    {
        if ( new Float( A ).compareTo( B ) > 0 ) return A;
        if ( new Float( A ).compareTo( B ) < 0 ) return B;

        return 0;
    }

    /**
     * max double
     */
    private static double getMax(double A, double B)
    {
        return A > B ? A : ( B > A ? B : 0 );
    }

    /**
     * При Х = 500К происходит StackOverflow
     */
    public static void printNumberSequenceRecursively(int X)
    {
        printNumberSequenceRecursively( 0, X );
    }

    private static int rectangleWidth = -1;
    private static int rectangleHeight = -1;

    private static int s = 0;

    /**
     * Решить задачу 2, без использования циклов. Используя рекурсию.
     */
    public static void drawRectangleRecursively(int W, int H)
    {
        drawRectangleRecursively( W, H, W );
    }

    private static boolean drawRectangleRecursively(int unitsLeft, int rowsLeft, int rowLength)
    {
        drawSymbol();

        if( unitsLeft > 1 )
            return drawRectangleRecursively( --unitsLeft, rowsLeft, rowLength );

        jumpToNewLine();

        if( rowsLeft > 1 )
            return drawRectangleRecursively( rowLength, --rowsLeft, rowLength );

        return true;
    }

    private static void drawSymbol()
    {
        System.out.print("+ ");
    }

    private static void jumpToNewLine()
    {
        System.out.println( "" );
    }

    /**
     * Решить задачу 1, без использования циклов. Используя рекурсию.
     * Хвостовая рекурсия
     * При большом значении X стек переполняется всеравно
     */
    private static void printNumberSequenceRecursively(int current, int X)
    {
        System.out.println( ++current );

        if ( current < X )
        {
            printNumberSequenceRecursively( current, X );
        }
    }


    private final static void clearConsole()
    {
        try
        {
            final String os = System.getProperty( "os.name" );

            if ( os.contains( "Windows" ) )
            {
                Runtime.getRuntime().exec( "cls" );
            }
            else
            {
                Runtime.getRuntime().exec( "clear" );
            }
        }
        catch ( final Exception e )
        {
            //  Handle any exceptions.
        }
    }

    private static void invalidInputHandler()
    {
        System.out.println( "Invalid menu number" );
    }

    private static void printMenu()
    {
        System.out.println( "\n\nВыберите задачу от 1 до 6: \n" +
                " 1. Вывод в консоли чисел от 1 до Х \n" +
                " 2. Вывод в консоли прямоугольника из символов '+' \n" +
                " 3. Вывод в консоли квадрата из символов + \n" +
                " 4. Максимальное число из двух \n" +
                " 5. Вывод в консоли чисел от 1 до Х рекурсивно \n" +
                " 6. Вывод в консоли прямоугольника из символов '+' рекурсивно \n" );
    }


    private static void continueMessage()
    {
        System.out.println( "\n\nПродолжить? [y/n]: " );
    }

    private static String getUserInput()
    {
        try
        {
            userInput = bufferedReader.readLine();
        }
        catch ( IOException e )
        {
            userInput = "y";
        }

        return userInput;
    }
}




































