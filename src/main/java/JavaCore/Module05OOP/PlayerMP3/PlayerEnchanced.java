package JavaCore.Module05OOP.PlayerMP3;

import JavaCore.Module05OOP.Interfaces.PlayListable;
import JavaCore.Module05OOP.Song.PlayList;
import JavaCore.Module05OOP.Song.Song;

abstract public class PlayerEnchanced extends PlayerSimple implements PlayListable<PlayList>
{
    protected PlayList playList;

    public PlayerEnchanced(String name)
    {
        super( name );
    }

    public PlayerEnchanced(double price)
    {
        super(price);
    }

    @Override
    public void setPlayList(PlayList list)
    {
        this.playList = list;
    }

    public void  playAllSongs()
    {
        if ( playList != null ){
            playList.getStream().forEach( t -> {
                Song song = (Song) t;
                setCurrentSong( song ).play();
            } );
        }
    }

    @Override
    public void playSong()
    {
        updateCurrentSong();

        super.playSong();
    }

    @Override
    protected Song getFirstSong()
    {
        if ( playList != null )
        {
            return playList.getFirst();
        }

        return null;
    }

    protected void updateCurrentSong()
    {
        currentSong = getFirstSong();
    }

    protected Song getLastSong()
    {
        return playList.getLast();
    }

    @Override
    public PlayList getPlayList()
    {
        return playList;
    }
}
