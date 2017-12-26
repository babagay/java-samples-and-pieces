package JavaCore.Module05OOP.Factory;

import JavaCore.Module05OOP.Interfaces.Factory;
import JavaCore.Module05OOP.Player.Player;

import java.util.HashMap;

/**
 * [паттерны]
 * http://designpatternsphp.readthedocs.io/ru/latest/Creational/README.html
 */


public abstract class PlayerFactory<T, B extends PlayerFactory> implements Factory
{
    public Player create(String vendor) throws Exception
    {
        return buildPlayer( vendor );
    }

    public Player create(String vendor, HashMap<String,Object> params) throws Exception
    {
        return buildPlayer( vendor, params );
    }

    public B getSpecificFactory()
    {
        return (B) getFactory();
    }

    abstract protected Player buildPlayer(String vendor) throws Exception;

    abstract protected Player buildPlayer(String vendor, HashMap<String,Object> params) throws Exception;

    public T getPlayerByVendor(String vendor)
    {
        T player = null;

        try
        {
            player = (T) getSpecificFactory().create( vendor );
        }
        finally
        {
            return player;
        }
    }
}
