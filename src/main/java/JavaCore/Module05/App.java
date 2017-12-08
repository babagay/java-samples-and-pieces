package JavaCore.Module05;

import JavaCore.Module05.Vehicle.Car;
import JavaCore.Module05.Vehicle.CarBuilder;
import JavaCore.Module05.Vehicle.CarDoor;
import JavaCore.Module05.Vehicle.CarWheel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;


/**
 * Задача 1
 * Создать пакет соответствующий(название на ваш вкус, но должно быть логично связано с именами классов) пакет
 * и поместить туда все последующие классы
 * Класс CarDoor
 * На прямую к переменным этого класса никто не может, только через методы
 * Хранит
 * состояние двери(открыта/закрыта)
 * состояние окна (открыто/закрыто)
 * Конструктор
 * Один без аргументов. Он должен присвоить переменным значения на случай если данных нет.
 * Один конструктор принимает оба состояния, двери и окна. Присваивает эти значения переменным внутри объекта.
 * Методы
 * открыть дверь
 * закрыть дверь
 * открыть/закрыть дверь (если дверь открыта и вызывается эта функция, значит дверь необходимо закрыть и наоборот)
 * открыть окно
 * закрыть окно
 * открыть/закрыть окно(если дверь открыта и вызывается эта функция, значит дверь необходимо закрыть и наоборот)
 * Вывести в консоль данные об объекте
 * Класс CarWheel
 * На прямую к переменным этого класса никто не может, только через методы
 * Хранит
 * Состояние целостности шины (дробное число от 0-стерта до 1-новая)
 * Конструктор
 * Аналогичный принцип как в классе CarDoor
 * Методы
 * Сменить шину на новую
 * Стереть шину на X%
 * Получить состояние (return)
 * Вывести в консоль данные об объекте
 *
 * Класс Car
 * На прямую к переменным этого класса никто не может, только через методы
 * Хранит
 * дата производства (неизменна после создания объекта)
 * тип двигателя
 * максимальная скорость машины (если она новая)
 * время разгона до 100км/ч
 * пассажировместимость
 * кол-во пассажиров внутри в данный момент
 * текущая скорость
 * массив колес
 * массив дверей
 * Конструктор
 * Нет пустого конструктора. Так как есть поля в классе, которые нельзя изменять после создания объекта. Например дата производства.
 * Конструктор с датой производства.
 * Конструктор со всеми полями, кроме массива колес и массива дверей.
 * Методы
 * Изменить текущую скорость
 * Посадить 1 пассажира в машину
 * Высадить 1 пассажира
 * Высадить всех пассажиров
 * Получить дверь по индексу
 * Получить колесо по индексу
 * Снять все колеса с машины
 * Установить на машину X новых колесу (вдобавок к имеющимся, то есть если было 4 колеса, после вызова метода с Х аргументом равным трем, колес будет 4+3=7)
 * Вычислить текущую возможную максимальную скорость (Скорость машины вычисляется так. Максимальная скорость новой машины множиться на самое стертое колесо в машине. Максимальная скорость равна 0 если в машине нет ни одного пассажира, так как некому ее вести)
 * Вывести в консоль данные об объекте (все поля и вычисленную максимальную скорость в зависимости от целостности колес и наличия водителя)
 * Задание 2 (дополнительное)
 * Создать консольный пользовательский интерфейс. В котором пользователя программа будет спрашивать какое действие выполнить и с какими параметрами.
 * Кол-во различных действий = кол-ву функций в ДЗ.
 */
public class App
{
    private static BufferedReader bufferedReader;

    private static String userInput = "";

    private static Scanner scanner;

    private static Car car;

    private static CarBuilder carBuilder;

    private static HashMap<String, Object> carDetails;

    static
    {
        bufferedReader = new BufferedReader( new InputStreamReader( System.in ) );

        scanner = new Scanner( System.in );

        carBuilder = new CarBuilder();

        carDetails = new HashMap<>();
    }



    public static void main(String[] args)
    {
        carDetails.put( "created", "1999" );

        car = carBuilder.buildOne( carDetails );

        do
        {
            printMenu();

            try
            {
                switch ( scanner.nextInt() )
                {
                    case 1:
                        setCurrentSpeed();
                        break;
                    case 2:
                        placeOnePassanger();
                        break;
                    case 3:
                        getOutOnePassanger();
                        break;
                    case 4:
                        emptyCar();
                        break;
                    case 5:
                        getDoorByIndex();
                        break;
                    case 6:
                        getWheelByIndex();
                        break;
                    case 7:
                        getWheelsOff();
                        break;
                    case 8:
                        setNewWheels();
                        break;
                    case 9:
                        car.toConsole();
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
            catch ( Exception e )
            {
                System.out.println( e.getMessage() );
            }

            continueMessage();
        }
        while ( getUserInput().equals( "y" ) ? true : false );

        scanner.close();
    }

    private static void setCurrentSpeed() throws Exception
    {
        System.out.println( "Введите скорость: " );

        double speed = scanner.nextDouble();

        car.setCurrentSpeed( speed );

        System.out.println( "Текущая скорость " + speed );
    }

    private static void placeOnePassanger()
    {
        Integer passangerNumber = car.placeOnePassangerIntoCar();

        System.out.println("В машине: " + passangerNumber);
    }

    private static void getOutOnePassanger()
    {
        Integer passangerNumber = car.getOnePassangerOut();

        System.out.println("В машине: " + passangerNumber);
    }

    private static void emptyCar()
    {
        car.emptyCar();

        Integer passangerNumber = car.getCurrentPassengerNumber();

        System.out.println("В машине: " + passangerNumber);
    }

    private static void getDoorByIndex()
    {
        System.out.println( "Введите индекс: " );

        double index = scanner.nextDouble();

        CarDoor[] doors = car.getDoors();

        System.out.println( doors[(int) index] );
    }

    private static void getWheelByIndex()
    {
        System.out.println( "Введите индекс колеса: " );

        double index = scanner.nextDouble();

        CarWheel[] wheels = car.getWheels();

        System.out.println( wheels[(int) index] );
    }

    private static void getWheelsOff()
    {
        car.resetWheels();

        System.out.println("На машине колес: " + car.getCurrentWheelCount());
    }

    private static void setNewWheels()
    {
        System.out.println( "Введите количество колес: " );

        double number = scanner.nextDouble();

        carBuilder.mountWheels(car, number);

        CarWheel[] wheels = car.getWheels();

        String result = "";

        for ( int i = 0; i < wheels.length; i++ )
        {
            result += wheels[i] + "\n";
        }

        System.out.println("Колеса: " + result);
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

    private static void invalidInputHandler()
    {
        System.out.println( "Invalid menu number" );
    }

    private static void continueMessage()
    {
        System.out.println( "\n\nПродолжить? [y/n]: " );
    }

    private static void printMenu()
    {
        System.out.println(
                "\n\n" +
                "Выберите действие от 1 до 9: \n" +
                " 1. Изменить текущую скорость \n" +
                " 2. Посадить 1 пассажира в машину '+' \n" +
                " 3. Высадить 1 пассажира + \n" +
                " 4. Высадить всех пассажиров \n" +
                " 5. Получить дверь по индексу \n" +
                " 6. Получить колесо по индексу \n" +
                " 7. Снять все колеса с машины \n" +
                " 8. Установить на машину X новых колес \n" +
                " 9. Распечатать в консоль" +
                 "\n\n"
        );
    }
}
