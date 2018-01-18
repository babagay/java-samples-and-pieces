package SamplesAndPieces;

/**
 * [1] https://habrahabr.ru/post/196226/
 * [2] https://habrahabr.ru/post/188010/
 */

public class MergeSort
{

    /**
     * Счетчик общего количества итераций
     */
    static int counterO = 0;

    /**
     * анализ сложности алгоритмма рекурсивного слияния.
     * Предполагаемая сложность: A.length  + B.length + A.l^2 + A.l. Т.о. , сложность оценивается в А + А^2 или просто в Z^2, где Z = (A+B)/2
     * Эксперименты показывают О(N^2), где N = A+B
     * Статья [1] с хабра указывает сложность merge, равную O(N)
     */
    public static void main(String[] args)
    {
        int[] A = {4, 55, 66, 45, 80, 116, 130, 150, 151, 159, 214, 300};
        int[] B = {7, 8, 9, 10, 11, 12, 115, 121, 138, 140, 160, 195, 200};

        int[] newArr = mergeR( A, B );

        System.out.println( counterO ); // количество итераций всего алгоритма
    }

    static int[] concat(int N, int[] arr)
    {
        int length = arr.length + 1;
        int[] newArr = new int[length];

        newArr[0] = N;

        for ( int i = 1; i < length; i++ )
        {
            newArr[i] = arr[i - 1];
            counterO++;
        }

        return newArr;
    }

    static int[] cutOffFirstElem(int[] arr)
    {
        if ( arr.length == 0 )
        {
            return arr;
        }

        int[] newArr = new int[arr.length - 1];

        for ( int i = 1; i < arr.length; i++ )
        {
            newArr[i - 1] = arr[i];
            counterO++;
        }

        return newArr;
    }

    /**
     * Объединяет (рекурсивно) 2 ОТСОРТИРОВАННЫХ массива интов
     */
    static int[] mergeR(int[] A, int[] B)
    {
        counterO++;

        if ( A.length == 0 )
        {
            return B;
        }

        else if ( B.length == 0 )
        {
            return A;
        }

        if ( A[0] < B[0] )
        {
            return concat( A[0], mergeR( cutOffFirstElem( A ), B ) );
        }
        else
        {
            return concat( B[0], mergeR( A, cutOffFirstElem( B ) ) );
        }
    }

    static int[] mergeL(int[] A, int[] B)
    {


        return A;
    }
}






























