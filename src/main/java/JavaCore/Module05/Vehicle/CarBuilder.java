package JavaCore.Module05.Vehicle;

import java.util.Map;
import java.util.Random;

public class CarBuilder
{
    private static CarWheel tmpWheel;

    private final String CREATED_YEAR_DEFAULT = "1979";

    Car car;

    // [!] можно пасширить дополнительными параметрами, напрмиер, связанными с колесами и дверями
    // Создать тачку с 4мя колесами и 4мя дверями
    public Car buildOne(Map<String,Object> params)
    {
        String created = (String)params.get( "created" );

        if ( created == null )
            created = CREATED_YEAR_DEFAULT;

        buildCar( created );

        buildCarWheels();

        buildCarDoors();

        return car;
    }

    public void mountWheels(Car car, double number)
    {
        Random random = new Random(  );

        for ( int i = 0; i < number; i++ )
        {
            car.addWheel( buildCarWheel( random.nextInt(100) ) );
        }
    }

    private void buildCar(String created)
    {
        car = new Car(created);
    }

    private void buildCarWheels()
    {
        car.addWheel( buildCarWheel( 100 ) );
        car.addWheel( buildCarWheel( 85 ) );
        car.addWheel( buildCarWheel( 90 ) );
        car.addWheel( buildCarWheel( 50 ) );
    }

    private void buildCarDoors()
    {
        car.addDoor( buildCarDoor() );
        car.addDoor( buildCarDoor() );
        car.addDoor( buildCarDoor() );
        car.addDoor( buildCarDoor() );
    }

    private CarDoor buildCarDoor()
    {
        return new CarDoor(  );
    }

    private CarWheel buildCarWheel(int wipeOutFactor)
    {
        CarWheel wheel = new CarWheel();

        wheel.wipeOffTireByPercent( wipeOutFactor );

        return wheel;
    }
}
