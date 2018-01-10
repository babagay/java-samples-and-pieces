package JavaCore.Module2;

import java.util.Scanner;

/**
 * Создать новый проект на своем компе
 * Создать новый проект на GitHub и объединить проект локальный с проектом на GitHub
 * Написать программу, которая делает следующее:
 * спрашивает у пользователя имя (String)
 * спрашивает у пользователя город проживания (String)
 * спрашивает у пользователя возраст (int)
 * спрашивает у пользователя хобби (String)
 * перед каждым вводом данных, программа должна вывести информацию пользователю с требованием ввода соответствующей информации.
 * программа выводит красиво оформлено всю информацию о пользователе в трех разных вариантах:
 * ----------------------------------
 * Вариант 1 (табличный):
 * Имя:               хххххх
 * Город:            хххххх
 * Возраст:        хххххх
 * Хобби:           хххххх
 * ----------------------------------
 * Вариант 2 (текстовый):
 * Человек по имени хххххх живет в городе хххххх.
 * Этому человеку хххххх лет и любит он заниматься хххххх.
 * ----------------------------------
 * Вариант 3 (иной):
 * хххххх - имя
 * хххххх - город
 * хххххх - возраст
 * хххххх - хобби
 * ----------------------------------
 */
public class App
{
    public static void main(String[] args)
    {
        Scanner console = new Scanner( System.in );
        Validator validator = new Validator();

        try
        {
            System.out.print( "First name: " );
            String userName = console.nextLine();
            validator.isValid( "userName", userName );

            System.out.print( "City of residence: " );
            String userLocation = console.nextLine();
            validator.isValid( "location", userLocation );

            // FIXME: nextInt() looses an input
            System.out.print( "Age: " );
            String userAge = console.nextLine();
            validator.isValid( "age", userAge );

            System.out.println( "Hobby: " );
            String userActivity = console.nextLine();
            validator.isValid( "hobby", userActivity );


            System.out.println( "\nName: \t" + userName );
            System.out.println( "City: \t" + userLocation );
            System.out.println( "Age: \t" + userAge );
            System.out.println( "Hobby: \t" + userActivity );

            System.out.println( "\nMan who's name is " + userName + " lives in " + userLocation + ". Hi is " + userAge + " years old and he likes to make " + userActivity + "\n");

            System.out.println( userName + " - name" );
            System.out.println( userLocation + " - city" );
            System.out.println( userAge + " - age" );
            System.out.println( userActivity + " - hobby" );

        }
        catch ( Exception e )
        {
            System.out.println( "error: " + e.getMessage() );
        }
        finally
        {
            console.close();
        }

    }

    static class Validator
    {

        final static int AGE_MIN = 0;
        final static int AGE_MAX = 100;

        /**
         * @param type  userName|location|age|hobby
         * @param value
         * @throws Exception
         */
        void isValid(String type, Object value) throws Exception
        {
            boolean result = false;

            switch ( type )
            {
                case "userName":
                    result = isNameValid( (String) value );
                    break;
                case "location":
                    result = isLocationValid( (String) value );
                    break;
                case "age":
                    result = isAgeValid( Integer.parseInt( (String) value ) );
                    break;
                case "hobby":
                    result = isHobbyValid( (String) value );
                    break;
            }

            if ( !result )
            {
                throw new Exception( "Value [" + value + "] is not a valid [" + type + "]" );
            }
        }

        private boolean isNameValid(String name)
        {
            return name.matches( "[a-zA-Z]{4,100}" );
        }

        private boolean isLocationValid(String val)
        {
            return val.matches( "[a-zA-Z\\s]{4,100}" );
        }

        private boolean isAgeValid(Integer age)
        {
            return age > AGE_MIN && age < AGE_MAX;
        }

        private boolean isHobbyValid(String hobby)
        {
            return hobby.matches( "[a-zA-Z\\s]{4,200}" );
        }

    }
}
