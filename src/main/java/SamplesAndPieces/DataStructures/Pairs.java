package SamplesAndPieces.DataStructures;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Pairs with Specific Difference
 * Given an array arr of distinct integers and a nonnegative integer k,
 * write a function findPairsWithGivenDifference that returns an array of all pairs [x,y] in arr,
 * such that x - y = k. If no such pairs exist, return an empty array.
 *
 * Note: the order of the pairs in the output array should maintain the order of the y element in the original array.
 *
 * input:  arr = [0, -1, -2, 2, 1], k = 1
 * output: [[1, 0], [0, -1], [-1, -2], [2, 1]]
 */
public class Pairs
{
    public static void main(String[] args)
    {
        int[] input = {0, -1, -2, 2, 1};
        int[][] output = findPairsWithGivenDifference(input, 1);

        System.out.println(output);
    }

    static int[][] findPairsWithGivenDifference(int[] arr, int k) {


        List<int[]> res = new ArrayList();

        int[][] result = {};

        int[] tmp = arr;

        Arrays.stream(arr)
                .map(x -> {
                    for (int i = 0; i < tmp.length; i++)
                    {
                        if((x - tmp[i]) == k ){
                            res.add(new int[]{x,tmp[i]});
                        }
                    }

                    return 1; // лишнее
                });



        return (int[][]) res.toArray();
    }
}
