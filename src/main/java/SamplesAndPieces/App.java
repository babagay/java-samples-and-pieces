package SamplesAndPieces;

import scala.Int;



/**
 * Hello world!
 * [!] Где настраивать версию джавы в проекте: https://stackoverflow.com/questions/25878045/errorjava-invalid-source-release-8-in-intellij-what-does-it-mean
 * http://xidea.online/
 */
public class App
{


    public static void main(String[] args)
    {
        System.out.println( "Hello World!" );

        unpredictableResultMagic();
    }

    /**
     *  i | tmp (временная переменная)
     *  0 | 0   [initial]
     *  0 | 0   [tmp = i]
     *  0 | 1   [tmp++]
     *  1 | 1   [i = tmp]
     *  1 | 1   [tmp++, postponed]
     *  0 | 1   [i -= tmp]
     *  0 | 2   [tmp++]
     *
     */
    static void unpredictableResultMagic()
    {
        int i = 0, j = 0;
        i = ++i - i--;
        j = ++j - j++;

        boolean compat = i == j;

        System.out.println( compat );

        System.out.println( "i: " + i );
        System.out.println( "j: " + j );
    }


    /**
     * 0++ 1-1=0 ++
     * 0  1 (++) 0 1
     * Допустимые символы в именах переменных
     */
    static void variableNames()
    {
        int $_123строка3_A = 44;

        System.out.println( $_123строка3_A + " " );
    }

    static void arrayNames()
    {
        // a
        int[] a1 = new int[]{1, 2, 3};
        //        b - error: не хватает размерности
        // int []a2 = new int[];
        //        c -  error: не хватает размерности
        // int[] a3 = new int[];
        //        d
        int a4[] = new int[10];
        //        e - error
        // int[] a5 = int[10];
        //        f
        int a6[] = new int[10];
        //        g error из-за значений, не влазящих в тип byte
        // byte[] a7 = {1, 1000, 1000};
        //        h
        int[] a8 = new int[10];
        //        i
        int[] a9 = {1, 2, 3};
    }

    /**
     * bool variables
     */
    static void foo()
    {
        // error
        // boolean b = (boolean) 1;

        // error
//        boolean b = false + true ;

        boolean b = true && false;

        byte c = 127;
    }


}
