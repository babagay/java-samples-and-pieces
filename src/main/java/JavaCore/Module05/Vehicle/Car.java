package JavaCore.Module05.Vehicle;

import java.util.Arrays;

public class Car
{
    private String created;
    private String engine;
    private Double maximumSpeed;

    /**
     * За сколько секунд машина набирает 100км/ч
     */
    private Double acceleration;

    private Integer seatNumber;

    private Integer currentPassengerNumber;
    private Double currentSpeed;

    private CarWheel[] wheels;
    private CarDoor[] doors;

    private final int DEFAULT_DOORS_COUNT = 2;
    private final int DEFAULT_WHEELS_COUNT = 4;


    private int currentDoorCount = 0;

    public int getCurrentDoorCount()
    {
        return currentDoorCount;
    }

    public int getCurrentWheelCount()
    {
        return currentWheelCount;
    }

    private int currentWheelCount = 0;

    public Car(String created)
    {
        this(
                created,
                "default engine",
                90.0,
                20.0,
                4,
                0,
                0.0
        );
    }

    public Car(String created, String engine, Double maximumSpeed, Double acceleration, Integer seatNumber, Integer currentPassengerNumber, Double currentSpeed)
    {
        this.created = created;
        this.engine = engine;
        this.maximumSpeed = maximumSpeed;
        this.acceleration = acceleration;
        this.seatNumber = seatNumber;
        this.currentPassengerNumber = currentPassengerNumber;
        this.currentSpeed = currentSpeed;

        resetWheels();

        resetDoors();
    }

    public void setCurrentSpeed(Double currentSpeed) throws Exception
    {
        validateSpeed( currentSpeed );

        this.currentSpeed = currentSpeed;
    }

    public void resetWheels()
    {
        wheels = new CarWheel[DEFAULT_WHEELS_COUNT];

        currentWheelCount = 0;
    }

    public void resetDoors()
    {
        doors = new CarDoor[DEFAULT_DOORS_COUNT];

        currentDoorCount = 0;
    }

    public String getCreated()
    {
        return created;
    }

    public String getEngine()
    {
        return engine;
    }

    public Double getMaximumSpeed()
    {
        return maximumSpeed;
    }

    public Double getAcceleration()
    {
        return acceleration;
    }

    public Integer getSeatNumber()
    {
        return seatNumber;
    }

    public Integer getCurrentPassengerNumber()
    {
        return currentPassengerNumber;
    }

    public Double getCurrentSpeed()
    {
        return currentSpeed;
    }

    public CarWheel[] getWheels()
    {
        return wheels;
    }

    public CarDoor[] getDoors()
    {
        return doors;
    }

    public Integer placeOnePassangerIntoCar()
    {
        if ( currentPassengerNumber < seatNumber )
            currentPassengerNumber++;

        return currentPassengerNumber;
    }

    public Integer getOnePassangerOut()
    {
        if ( currentPassengerNumber > 0 )
            currentPassengerNumber--;

        return currentPassengerNumber;
    }

    public void emptyCar()
    {
        currentPassengerNumber = 0;
    }

    public CarDoor getDoorByIndex(int index) throws ArrayIndexOutOfBoundsException
    {
        return doors[index];
    }

    public CarWheel getWeelByIndex(int index) throws ArrayIndexOutOfBoundsException
    {
        return wheels[index];
    }

    public void removeAllWeels()
    {
        wheels = new CarWheel[4];
    }

    /**
     * @Deprecated
     */
    public void addWheel(int number)
    {
        while ( number > 0 )
        {
            CarWheel carWheel = new CarWheel();

            addWheelDynamically(carWheel);

            number--;
        }
    }

    public void addWheel(CarWheel wheel)
    {
        addWheelDynamically(wheel);
    }

    public void addDoor(CarDoor door)
    {
        addDoorDynamically( door );
    }


    public double getCurrentPossibleMaxSpeed()
    {
        if ( currentPassengerNumber == 0 )
            return 0.0;

        double theWorstWheelWapeOutFactor = 1.0;

        for ( CarWheel wheel :
                wheels )
        {
            if ( wheel.getTireState() < theWorstWheelWapeOutFactor )
                theWorstWheelWapeOutFactor = wheel.getTireState();
        }

        return maximumSpeed * theWorstWheelWapeOutFactor;
    }

    public void toConsole()
    {
        System.out.printf( toString() );
    }

    @Override
    public String toString()
    {
        return "Car{" +
                "created='" + created + '\'' +
                ", engine='" + engine + '\'' +
                ", maximumSpeed=" + maximumSpeed +
                ", acceleration=" + acceleration +
                ", seatNumber=" + seatNumber +
                ", currentPassengerNumber=" + currentPassengerNumber +
                ", currentSpeed=" + currentSpeed +
                ", wheels=" + Arrays.toString( wheels ) +
                ", doors=" + Arrays.toString( doors ) +
                ", current Possible Max Speed=" + getCurrentPossibleMaxSpeed() +
                '}';
    }


    private void addWheelDynamically(CarWheel wheel)
    {
        if ( wheels.length <= currentWheelCount )
        {
            CarWheel[] wheelsExtended = Arrays.copyOf( wheels, wheels.length + 2 );
            wheels = wheelsExtended;
        }

        wheels[currentWheelCount++] = wheel;
    }

    private void addDoorDynamically(CarDoor door)
    {
        if ( doors.length <= currentDoorCount )
        {
            CarDoor[] doorsExtended = Arrays.copyOf( doors, doors.length + 2 );
            doors = doorsExtended;
        }

        doors[currentDoorCount++] = door;
    }

    private void validateSpeed(double speed) throws Exception
    {
        if ( speed > 0 && speed < 250 ){
            // OK
        } else {
            throw new Exception( "Invalid speed: " + speed );
        }
    }
}
