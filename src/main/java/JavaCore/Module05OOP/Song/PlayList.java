package JavaCore.Module05OOP.Song;

import scala.util.parsing.combinator.testing.Str;

import java.util.ArrayList;

public class PlayList<T extends Song>
{
    private ArrayList<T> list;

    public PlayList(ArrayList<T> list)
    {
        this.list = list;
    }

    public T getSongByName(String name)
    {
        // todo
        return list.get( 0 );
    }
}
