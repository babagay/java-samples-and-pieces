package JavaCore.Module05.Vehicle;

public class CarWheel
{
    private double tearWearOutFactor;

    public CarWheel()
    {
        tearWearOutFactor = 1.0; // new one
    }

    public CarWheel(double tearWearOutFactor)
    {
        if ( tearWearOutFactor >= 0.0 && tearWearOutFactor <= 1.0 )
            this.tearWearOutFactor = tearWearOutFactor;
    }

    /**
     *   Сменить шину на новую
     */
    public void renovateTire()
    {
        tearWearOutFactor = 1.0;
    }

    /**
     * Стереть шину на X%
     */
    public void wipeOffTireByPercent(int wipeFactor)
    {
        if(wipeFactor >= 0 && wipeFactor <= 100)
        {
            tearWearOutFactor = tearWearOutFactor * wipeFactor/100;
        }
    }

    /**
     * Получить состояние шины
     */
    public double getTireState()
    {
        return tearWearOutFactor;
    }

    @Override
    public String toString()
    {
        return "CarWheel{" +
                "tearWearOutFactor=" + tearWearOutFactor +
                '}';
    }
}
