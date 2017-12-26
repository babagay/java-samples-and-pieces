package JavaCore.Module05OOP.PlayerMP3.Extra;

import JavaCore.Module05OOP.PlayerMP3.PlayerExtra;

public class Pioneer extends PlayerExtra
{
    // [!] Один из конструкторов (любой) обязателен к реализации - почему только один?
    public Pioneer(String name)
    {
        super( name );
    }

    public Pioneer(double price)
    {
        super( price );
    }

    public void playAllSongs()
    {
        playList.revert();

        super.playAllSongs();
    }

    @Override
    public String toString()
    {
        return "Pioneer{" +
                "name='" + name + '\'' +
                '}';
    }
}
