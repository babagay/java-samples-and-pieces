package JavaCore.Module05OOP.PlayerMP3.Simple;

import JavaCore.Module05OOP.PlayerMP3.PlayerSimple;

public class Xiaomi extends PlayerSimple
{
    public Xiaomi(String name)
    {
        super( name );
    }

    public Xiaomi(final double price)
    {
        super(price);
    }

    @Override
    public void playSong()
    {
        System.out.println("Error");
    }

    @Override
    public String toString()
    {
        return "Xiaomi{" +
                "name='" + name + '\'' +
                '}';
    }
}
