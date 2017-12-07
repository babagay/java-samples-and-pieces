package JavaCore.Module05.Vehicle;

public class Car
{
    private String created;
    private String engine;
    private Double maximumSpeed;
    private Double acceleration;
    private Integer seatNumber;

    private Integer currentPassengerNumber;
    private Double currentSpeed;

    private CarWheel[] weels;
    private CarDoor[] doors;

    /**
     * TODO инициализировать массивы самостоятельно
     */
    public Car(String created)
    {
        this.created = created;
    }

    // todo размер массивов колес и дверей?
    public Car(String created, String engine, Double maximumSpeed, Double acceleration, Integer seatNumber, Integer currentPassengerNumber, Double currentSpeed)
    {
        this.created = created;
        this.engine = engine;
        this.maximumSpeed = maximumSpeed;
        this.acceleration = acceleration;
        this.seatNumber = seatNumber;
        this.currentPassengerNumber = currentPassengerNumber;
        this.currentSpeed = currentSpeed;
    }

    /**
     * TODO Создать консольный пользовательский интерфейс.
     * В котором пользователя программа будет спрашивать какое действие выполнить и с какими параметрами.
     * Кол-во различных действий = кол-ву функций в ДЗ.
     *      Изменить текущую скорость
     *      Посадить 1 пассажира в машину
     *      Высадить 1 пассажира
     *      Высадить всех пассажиров
     *      Получить дверь по индексу
     *      Получить колесо по индексу
     *      Снять все колеса с машины
     *      Установить на машину X новых колес
     */
    public static void main(String[] args)
    {

    }

    public void setWeels(CarWheel[] weels)
    {
        this.weels = weels;
    }

    public void setDoors(CarDoor[] doors)
    {
        this.doors = doors;
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

    public CarWheel[] getWeels()
    {
        return weels;
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
        return weels[index];
    }

    public void removeAllWeels()
    {
        weels = new CarWheel[4];
    }

    public void addWeel(int number)
    {
        while ( number > 0 )
        {
            CarWheel carWheel = new CarWheel();

            addWheelDynamically(carWheel);

            number--;
        }
    }

    /**
     * TODO Вычислить текущую возможную максимальную скорость
     * (Скорость машины вычисляется так. Максимальная скорость
     * новой машины множиться на самое стертое колесо в машине.
     * Максимальная скорость равна 0 если в машине нет ни одного пассажира, так как некому ее вести)
     */
    public void getMaxSpeed()
    {

    }

    /**
     * TODO Вывести в консоль данные об объекте
     * (все поля и вычисленную максимальную скорость в зависимости от целостности колес и наличия водителя)
     */
    public void toConsole()
    {

    }

    /**
     * todo
     * взять размер массива
     * сравнить с количесвтом элементов заполненных
     * если равны, создать новый массив и скопировать в него элементы
     * добавить новый элемент
     */
    private void addWheelDynamically(CarWheel carWheel)
    {

    }
}
