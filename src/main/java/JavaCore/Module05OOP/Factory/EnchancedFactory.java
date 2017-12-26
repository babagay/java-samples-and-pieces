package JavaCore.Module05OOP.Factory;

import JavaCore.Module05OOP.Player.Player;
import JavaCore.Module05OOP.PlayerMP3.Enchanced.LG;
import JavaCore.Module05OOP.PlayerMP3.Enchanced.Sony;
import JavaCore.Module05OOP.PlayerMP3.PlayerEnchanced;

import java.util.HashMap;

public class EnchancedFactory<P extends PlayerEnchanced> extends PlayerFactory<P, EnchancedFactory>
{
    @Override
    protected Player buildPlayer(String vendor) throws Exception
    {
        Player player;

        switch ( vendor )
        {
            case "Sony":
                player = new Sony( "Sony player" );
                break;
            case "LG":
                player = new LG( "LG player" );
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
                case "LG":
                    player = new LG( price );
                    break;
                case "Sony":
                    player = new Sony( price );
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
