package JavaCore.Module05OOP.Factory;

import JavaCore.Module05OOP.Player.Player;
import JavaCore.Module05OOP.PlayerMP3.Extra.Panasonic;
import JavaCore.Module05OOP.PlayerMP3.Extra.Pioneer;
import JavaCore.Module05OOP.PlayerMP3.PlayerExtra;
import JavaCore.Module05OOP.PlayerMP3.Simple.Elenberg;
import JavaCore.Module05OOP.PlayerMP3.Simple.Xiaomi;

import java.util.HashMap;

public class ExtraFactory<P extends PlayerExtra> extends PlayerFactory<P, ExtraFactory>
{
    @Override
    protected Player buildPlayer(String vendor) throws Exception
    {
        Player player = null;

        switch ( vendor )
        {
            case "Pioneer":
                player = new Pioneer( "Pioneer player" );
                break;
            case "Panasonic":
                player = new Panasonic( "Panasonic player" );
                break;
            default:
                throw new Exception( "Недопустимый производитель [" + vendor + "]" );
        }

        return player;
    }

    @Override
    protected Player buildPlayer(String vendor, HashMap<String, Object> params) throws Exception
    {
        Player player;

        if ( params.get( "price" ) != null ){
            double price = (Double) params.get( "price" );
            switch ( vendor )
            {
                case "Panasonic":
                    player = new Panasonic( price );
                    break;
                case "Pioneer":
                    player = new Pioneer( price );
                    break;
                default:
                    throw new Exception( "Недопустимый производитель [" + vendor + "]" );
            }
        } else {
            player = buildPlayer(vendor);
        }

        return player;
    }


    @Override
    public P getPlayerByVendor(String vendor)
    {
        return super.getPlayerByVendor( vendor );
    }
}
