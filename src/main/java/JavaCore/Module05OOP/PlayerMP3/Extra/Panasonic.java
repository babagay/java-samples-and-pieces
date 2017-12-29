package JavaCore.Module05OOP.PlayerMP3.Extra;

import JavaCore.Module05OOP.PlayerMP3.PlayerExtra;
import JavaCore.Module05OOP.Song.Song;

import java.util.Random;

public class Panasonic extends PlayerExtra
{
    private String vendor = "Panasonic";

    public Panasonic(String name)
    {
        super( name );
    }

    public Panasonic(final double price)
    {
        super( price );
    }

    private Song[] songArr;

    @Override
    public void shuffle()
    {
        if ( playList != null )
        {

            Random rnd = new Random();
            int minimum = 0;
            int maximum = playList.getListSize() - 1;

            songArr = new Song[playList.getListSize()];

            playList.getStream().forEach( t -> {
                int i = minimum + rnd.nextInt( (maximum - minimum) + 1 );

                putSongToTmpArray( i, (Song) t );
            } );

            playList.emptyList();
            playList.putAll( songArr );
        }
    }

    private boolean putSongToTmpArray(int position, Song song)
    {
        if ( position >= songArr.length )
        {
            position = 0;
        }

        if ( songArr[position] == null )
        {
            songArr[position] = song;
        }
        else
        {
            putSongToTmpArray( ++position, song );
        }

        return true;
    }

    @Override
    public String toString()
    {
        return "Panasonic{" +
                "vendor='" + vendor + '\'' +
                '}';
    }

}
