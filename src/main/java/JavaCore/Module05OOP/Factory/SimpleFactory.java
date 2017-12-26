package JavaCore.Module05OOP.Factory;

import JavaCore.Module05OOP.Player.Player;
import JavaCore.Module05OOP.PlayerMP3.Simple.Elenberg;
import JavaCore.Module05OOP.PlayerMP3.PlayerSimple;
import JavaCore.Module05OOP.PlayerMP3.Simple.Xiaomi;




public class SimpleFactory<T> extends PlayerFactory
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
                 throw new Exception( "Недопустимый производитель [" + vendor + "]" );
        }

        return (PlayerSimple) player;
    }

    @Override
    public T getPlayerByVendor(String vendor) {
        //todo тут vendor не нужен, т.к. есть уже конкретный тип Т
        return (T) super.getPlayerByVendor(vendor);
    }
}
