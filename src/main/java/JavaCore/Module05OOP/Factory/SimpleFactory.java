package JavaCore.Module05OOP.Factory;

import JavaCore.Module05OOP.Player.Player;
import JavaCore.Module05OOP.PlayerMP3.Simple.Elenberg;
import JavaCore.Module05OOP.PlayerMP3.PlayerSimple;
import JavaCore.Module05OOP.PlayerMP3.Simple.Xiaomi;

import java.util.HashMap;


public class SimpleFactory<T> extends PlayerFactory<T, SimpleFactory>
{
    @Override
    protected PlayerSimple buildPlayer(String vendor) throws Exception
    {
        Player player = null;

        switch ( vendor )
        {
            case "Elenberg":
                player = new Elenberg( "Elenberg player" );
                break;
            case "Xiaomi":
                player = new Xiaomi( "Xiaomi player" );
                break;
            default:
                throw new Exception( "Недопустимый производитель [" + vendor + "]" );
        }

        return (PlayerSimple) player;
    }

    @Override
    protected Player buildPlayer(String vendor, HashMap<String, Object> params) throws Exception
    {
        Player player;

        if ( params.get( "price" ) != null ){
            double price = (Double) params.get( "price" );
            switch ( vendor )
            {
                case "Elenberg":
                    player = new Elenberg( price );
                    break;
                case "Xiaomi":
                    player = new Xiaomi( price );
                    break;
                default:
                    throw new Exception( "Недопустимый производитель [" + vendor + "]" );
            }
        } else {
            player = buildPlayer(vendor);
        }

        return player;
    }

    /**
     * todo: взять вендора из конкретного типа Т
     * https://habrahabr.ru/post/66593/
     */
    @Override
    public T getPlayerByVendor(String vendor)
    {
        return super.getPlayerByVendor( vendor );
    }

    /**
     * Обёртка, умеющая бросать исключения,
     * которую можно использовать в случае, когда нельзя изменить родительский класс
     */
    public T getSimplePlayerByVendor(String vendor) throws Exception
    {
        T player = super.getPlayerByVendor( vendor );

        if ( player == null )
        {
            throw new Exception( "Тип [" + vendor + "] не поддерживается" );
        }

        return player;
    }


}
