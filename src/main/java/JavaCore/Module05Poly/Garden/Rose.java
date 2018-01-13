package JavaCore.Module05Poly.Garden;

//import JavaCore.Module05Poly.Garden.GardenFlower;

public class Rose extends GardenFlower
{
    public Rose()
    {
        price = 100;
    }

    @Override
    public String toString()
    {
        return "Rose{" +
                "price=" + price +
                '}';
    }
}
