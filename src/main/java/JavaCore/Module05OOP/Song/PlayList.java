package JavaCore.Module05OOP.Song;




import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PlayList<T extends Song>
{
    private ArrayList<T> list;

    public PlayList()
    {
        list = new ArrayList<>();
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

    public void putAll(T[] songs)
    {
        for ( int i = 0; i < songs.length; i++ )
        {
            putSong( songs[i] );
        }
    }

    public void emptyList()
    {
        list = new ArrayList<T>();
    }

    public Song getFirst()
    {
        if ( list != null && !list.isEmpty() )
        {
            return list.get( 0 );
        }

        return null;
    }

    public Song getLast()
    {
        if ( list != null && !list.isEmpty() )
        {
            return list.get( list.size() - 1 );
        }

        return null;
    }

    public Integer getListSize()
    {
        return list.size();
    }

    public Stream<T> getStream()
    {
        return list.stream();
    }

    public void revert()
    {
        Collections.reverse( list );
    }

    @Override
    public String toString()
    {
        return "PlayList{" +
                "list=" + list +
                '}';
    }
}
