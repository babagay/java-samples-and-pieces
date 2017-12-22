package JavaCore.Module05OOP.Factory;

import JavaCore.Module05OOP.Player.Player;
import JavaCore.Module05OOP.PlayerMP3.PlayerMP3Panasonic;
import JavaCore.Module05OOP.PlayerMP3.PlayerMP3Sony;
import JavaCore.Module05OOP.PlayerMP3.PlayerSimple;

// todo заинжектить PlayList


public class SimpleBuilder extends PlayerFactory
{
    @Override
    protected PlayerSimple buildPlayer(String vendor) throws Exception
    {
        Player player = null;

        switch ( vendor ){
            case "Sony":
                player = new PlayerMP3Sony();
                break;
             default:
                 throw new Exception( "Недопустимый производитель" );
        }

        return (PlayerSimple) player;
    }
}
