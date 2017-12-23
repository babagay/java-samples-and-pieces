package JavaCore.Module05OOP.PlayerMP3;

public class Elenberg extends PlayerSimple
{

    public Elenberg(String name)
    {
        super( name );
    }

    @Override
    public void playSong()
    {

    }

    @Override
    public String toString()
    {
        return getClass().getName() + ". Elenberg{" +
                "name='" + name + '\'' +
                '}';
    }
}
