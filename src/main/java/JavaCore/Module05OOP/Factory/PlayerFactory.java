package JavaCore.Module05OOP.Factory;

import JavaCore.Module05OOP.Interfaces.AudioPlaybackable;
import JavaCore.Module05OOP.Player.Player;

/**
 * [паттерны]
 * http://designpatternsphp.readthedocs.io/ru/latest/Creational/README.html
 */


//todo 3 типа билдеров - на каждый абстрактный класс
public abstract class PlayerFactory // todo интерфейс
{

    public Player create(String vendor) throws Exception
    {
       return buildPlayer( vendor );
    }

    abstract protected Player buildPlayer(String vendor) throws Exception;
}
