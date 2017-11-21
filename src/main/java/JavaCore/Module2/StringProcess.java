package JavaCore.Module2;

/**
 * Пользователь вводит 2 строки.
 * Программа должна вывести на экран:
 *      a) встречается ли вторая введенная строка в первой (true / false)
 *      б) сколько раз встречается вторая строка в первой (int)
 *      в) в каком месте вторая строка встречается первый раз в первое (номер отступа от левого края, отсчет с единицы)
 *
 *  К примеру:
 *  Ввод:		“abbccddeeff”, “bc”
 *  Вывод:
 *  Встречается ли “bc” в “abbccddeeff”? - true
 *  Сколько раз встречается “bc” в “abbccddeeff”? - 1
 *  Где “bc” в “abbccddeeff” встречается в первый раз? - 3
 */
public class StringProcess
{
    public static void main(String[] args)
    {
        String first = "abbccddeeff";
        String second = "bc";

        boolean bc = first.matches( ".*"+second+".*" );

        int count = first.split( second ).length - 1;

        int indexFirst = first.indexOf( second );

        System.out.println("A " + bc);

        System.out.println("Б " + count);

        System.out.println("В " + ++indexFirst);
    }
}
