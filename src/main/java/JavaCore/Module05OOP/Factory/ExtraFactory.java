package JavaCore.Module05OOP.Factory;

import JavaCore.Module05OOP.Player.Player;

public class ExtraFactory extends PlayerFactory
{

    @Override
    protected Player buildPlayer(String vendor)
    {
        return null;
    }
}
