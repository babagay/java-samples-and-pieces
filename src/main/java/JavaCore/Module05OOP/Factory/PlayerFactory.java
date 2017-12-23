package JavaCore.Module05OOP.Factory;

import JavaCore.Module05OOP.Interfaces.AudioPlaybackable;
import JavaCore.Module05OOP.Interfaces.Builder;
import JavaCore.Module05OOP.Player.Player;
import JavaCore.Module05OOP.PlayerMP3.PlayerSimple;

/**
 * [паттерны]
 * http://designpatternsphp.readthedocs.io/ru/latest/Creational/README.html
 */


public abstract class PlayerFactory<T, B extends PlayerFactory> implements Builder
{
    public Player create(String vendor) throws Exception
    {
        return buildPlayer( vendor );
    }

    public B getSpecialBuilder()
    {
        return (B) getBuilder();
    }

    abstract protected Player buildPlayer(String vendor) throws Exception;

    public T getSimplePlayer(String vendor)
    {
        T player = null;

        try
        {
            player = (T) getSpecialBuilder().create( vendor );
        }
        finally
        {
            return player;
        }
    }
}
