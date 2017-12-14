package JavaCore.Module2;

import java.awt.*;
import java.io.FileNotFoundException;
import java.util.Formatter;

/**
 * [Вывод ограниченного набора цветов]
 * https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println
 * https://stackoverflow.com/questions/1448858/how-to-color-system-out-println-output
 * https://stackoverflow.com/questions/565252/how-to-set-a-strings-color
 */
public class ColoredOutput
{
    public static final String ANSI_RESET =  "\u001B[0m";
    public static final String ANSI_BLACK =  "\u001B[30m";
    public static final String ANSI_RED =    "\u001B[31m";
    public static final String ANSI_GREEN =  "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE =   "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN =   "\u001B[36m";
    public static final String ANSI_WHITE =  "\u001B[37m";

    public static void main(String[] args)
    {
        String s = "dfvcdfv";
        Formatter formatter = new Formatter();
        //            StringBuilder builder = new StringBuilder( s );
//        formatter = new Formatter();
        Formatter result = formatter.format( "|%3$2s|", s, "asd", "9" );


//        System.out.println( formatter );

//        System.out.println( "------------" );
//
//        System.out.println( result.toString() );
//
//        System.out.format("|%20s %s |%n", "Full Name: ", "John");
//        System.out.format("|%20s %s |%n", "E-mail Address: ", "john@gmail.com");
//        System.out.format("%20s %s %n", "City: ", "New York");
//        System.out.format("%20s %s %n", "Country: ", "United States");
//
        // [!] Изменение цвета действует от знака \033[47m до бесконечности, т.е. , пока не встретится другой подобный знак
//        System.out.println("\033[31m;1m Red"); // красный шрифт
//        System.out.println("\033[47m BLUE"); // серый бэкграунд
//        System.out.println("\033[35m MAGENTA");
//        System.out.println("\033[36m CYAN");
//        System.out.println("[33;42m CYAN");



//        ColorRGBA red = new ColorRGBA(1.0f, 0.0f, 0.0f, 1.0f);

//        String str = "It's a \u001b[31mRainbow\u001b[34;43m colored\u001b[39m world.";
//        for (ANSIAttrString ats : new ANSIAttrString(str)) {
//            System.out.println(ats.state + ": " + ats.string);
//        }

        // Выводит красный текст
        System.out.println("\033[31;1m red text \033[0m  ");
//        System.out.println("\033[32;1myellow text\033[0m  ");
//        System.out.println("\033[34;1mBlue text\033[0m  ");
//        System.out.println("\033[35;1m Magenta text \033[0m");
//        System.out.println("\033[45;2mred text\033[0m  "); // background
//        System.out.println("\033[31;3mred text\033[0m  ");
//        System.out.println("\033[36;3m Cred text\033[0m  ");
//        System.out.println("\033[36;4m Spred text\033[0m  ");

        // 24bit color tests
        System.out.println("\033[38;2;37;37;37m Ash text \033[0m  ");

        for ( int i = 0; i < 90; i++ )
        {
            //System.out.println("\033[38;2;"+i+";"+i+";"+i+"m Fred text "+i+"  \033[0m  ");
        }






    }

//    static class ColorRGBA {
//
//        ColorRGBA(float red,
//                  float green,
//                  float blue,
//                  float alpha)
//        {
//
//        }
//    }
}
