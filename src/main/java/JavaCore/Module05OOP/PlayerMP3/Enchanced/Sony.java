package JavaCore.Module05OOP.PlayerMP3.Enchanced;

import JavaCore.Module05OOP.PlayerMP3.PlayerEnchanced;
import JavaCore.Module05OOP.Song.PlayList;
import JavaCore.Module05OOP.Song.Song;

public class Sony extends PlayerEnchanced
{
    public String vendor = "SONY";

    public Sony(String name)
    {
        super( name );
    }

    public Sony(final double price)
    {
        super( price );
    }

    @Override
    public PlayList getPlayList()
    {
        return playList;
    }

    @Override
    public void playSong()
    {
        Song song = getLastSong();

        setCurrentSong(song);

        super.play();
    }


    @Override
    public String toString()
    {
        return "Sony{" +
                "vendor='" + vendor + '\'' +
                '}';
    }
}
