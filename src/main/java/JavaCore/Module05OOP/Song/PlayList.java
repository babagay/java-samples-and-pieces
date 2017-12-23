package JavaCore.Module05OOP.Song;

import scala.util.parsing.combinator.testing.Str;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PlayList<T extends Song>
{
    private ArrayList<T> list;

    public PlayList()
    {
        list = new ArrayList<>(  );
    }

    public PlayList(ArrayList<T> list)
    {
        this.list = list;
    }

    public T getSongByName(String name)
    {
        T songFound = null;

        try
        {
            songFound = list.stream().filter( song -> song.getName().equals( name ) ).limit( 1 ).collect( Collectors.toList() ).get( 0 );
        }
        finally
        {
            return songFound;
        }
    }

    public void putSong(T song)
    {
        list.add( song );
    }

    @Override
    public String toString()
    {
        return "PlayList{" +
                "list=" + list +
                '}';
    }
}
