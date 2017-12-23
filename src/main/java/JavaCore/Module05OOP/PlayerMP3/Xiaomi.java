package JavaCore.Module05OOP.PlayerMP3;

public class Xiaomi extends PlayerSimple
{
    public Xiaomi(String name)
    {
        super( name );
    }

    @Override
    public String toString()
    {
        return "Xiaomi{" +
                "name='" + name + '\'' +
                '}';
    }
}
