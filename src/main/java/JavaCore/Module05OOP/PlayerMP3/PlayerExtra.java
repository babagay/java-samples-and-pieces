package JavaCore.Module05OOP.PlayerMP3;

import JavaCore.Module05OOP.Interfaces.Shuffleable;

abstract public class PlayerExtra extends PlayerEnchanced implements Shuffleable
{
    public PlayerExtra(String name)
    {
        super( name );
    }

    @Override
    public void shuffle()
    {

        // Some standard realization
    }
}
