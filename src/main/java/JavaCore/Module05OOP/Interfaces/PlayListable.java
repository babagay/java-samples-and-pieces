package JavaCore.Module05OOP.Interfaces;

import JavaCore.Module05OOP.Player.Player;

public interface PlayListable<T> extends Player
{
    public void setPlayList(T list);

    public T getPlayList();
}
