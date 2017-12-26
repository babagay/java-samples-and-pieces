package JavaCore.Module05OOP.PlayerMP3;

import JavaCore.Module05OOP.Gadget.Gadget;
import JavaCore.Module05OOP.Interfaces.AudioPlaybackable;
import JavaCore.Module05OOP.Song.Song;

abstract public class PlayerSimple extends Gadget implements AudioPlaybackable
{
    protected double price;

    protected Song currentSong;

    public PlayerSimple(String name)
    {
        this.name = name;
    }

    public PlayerSimple(double price)
    {
        this.price = price;
    }

    public double getPrice()
    {
        return price;
    }

    @Override
    public void playSong()
    {
        play();
    }

    public void play()
    {
        if ( currentSong != null )
        {
            System.out.println( "Playing: " + currentSong );
        }
        else
        {
            System.out.println( "No songs" );
        }
    }

    public PlayerSimple setCurrentSong(Song song)
    {
        currentSong = song;

        return this;
    }

    protected Song getFirstSong()
    {
        return currentSong;
    }
}
