package JavaCore.Module05OOP.PlayerMP3.Simple;

import JavaCore.Module05OOP.PlayerMP3.PlayerSimple;

public class Elenberg extends PlayerSimple
{

    public Elenberg(String name)
    {
        super( name );
    }

    public Elenberg(final double price)
    {
        super( price );
    }

    @Override
    public void playSong()
    {
        super.playSong();
    }

    @Override
    public String toString()
    {
        return "Elenberg{" +
                "price=" + price +
                ", currentSong=" + currentSong +
                ", name='" + name + '\'' +
                '}';
    }
}
