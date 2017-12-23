package JavaCore.Module05OOP.Factory;

import JavaCore.Module05OOP.Player.Player;
import JavaCore.Module05OOP.PlayerMP3.Elenberg;
import JavaCore.Module05OOP.PlayerMP3.PlayerSimple;
import JavaCore.Module05OOP.PlayerMP3.Xiaomi;

// todo заинжектить PlayList


public class SimpleBuilder extends PlayerFactory
{
    @Override
    protected PlayerSimple buildPlayer(String vendor) throws Exception
    {
        Player player = null;

        switch ( vendor ){
            case "Elenberg":
                player = new Elenberg("Elenberg player");
                break;
            case "Xiaomi":
                player = new Xiaomi("Xiaomi player");
                break;
             default:
                 throw new Exception( "Недопустимый производитель" );
        }

        return (PlayerSimple) player;
    }
}
