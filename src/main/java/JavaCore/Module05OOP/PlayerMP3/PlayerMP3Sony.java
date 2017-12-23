package JavaCore.Module05OOP.PlayerMP3;

public class PlayerMP3Sony extends PlayerEnchanced
{

    public String vendor = "SONY";

    public PlayerMP3Sony(String name)
    {
        super( name );
    }

    @Override
    public void setPlayList(Object list)
    {

    }

    @Override
    public Object getPlayList()
    {
        return null;
    }

    @Override
    public String toString()
    {
        return "PlayerMP3Sony{" +
                "vendor='" + vendor + '\'' +
                '}';
    }
}
