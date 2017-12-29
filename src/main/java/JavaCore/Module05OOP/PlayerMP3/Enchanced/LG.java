package JavaCore.Module05OOP.PlayerMP3.Enchanced;

import JavaCore.Module05OOP.PlayerMP3.PlayerEnchanced;
import JavaCore.Module05OOP.Song.Song;

public class LG extends PlayerEnchanced
{
    String vendor = "LG";

    public LG(final double price)
    {
        super(price);
    }

    public LG(String name)
    {
        super( name );
    }



    @Override
    public String toString()
    {
        return "LG{" +
                "vendor='" + vendor + '\'' +
                ", price=" + price +
                ", name='" + name + '\'' +
                '}';
    }

}
