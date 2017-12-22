package JavaCore.Module05OOP;

import JavaCore.Module05OOP.Factory.PlayerFactory;
import JavaCore.Module05OOP.Factory.SimpleBuilder;
import JavaCore.Module05OOP.Player.Player;

public class App
{
    public static void main(String[] args)
    {

        PlayerFactory simpleGenerator = new SimpleBuilder();

        try
        {
            Player sonyPlayer = simpleGenerator.create( "Sony" );

            System.out.println(sonyPlayer);
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
    }
}
