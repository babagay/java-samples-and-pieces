package JavaCore.Module05Poly.Garden;

//import JavaCore.Module05Poly.Garden.GardenFlower;

public class Rose extends GardenFlower
{
    public Rose()
    {
        price = 100;
    }

    public Rose(int price)
    {
        this.price = price;
    }

    @Override
    public String toString()
    {
        return "Rose{" +
                "price=" + price +
                '}';
    }
}
