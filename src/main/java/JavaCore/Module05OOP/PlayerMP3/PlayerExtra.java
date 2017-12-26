package JavaCore.Module05OOP.PlayerMP3;

import JavaCore.Module05OOP.Interfaces.Shuffleable;

abstract public class PlayerExtra extends PlayerEnchanced implements Shuffleable
{
    public PlayerExtra(String name)
    {
        super( name );
    }

    public PlayerExtra(double price)
    {
        super(price);
    }

    @Override
    public void shuffle()
    {
        System.out.println("shuffle() not implemented");
    }
}
