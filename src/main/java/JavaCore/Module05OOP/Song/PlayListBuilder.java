package JavaCore.Module05OOP.Song;

public class PlayListBuilder
{
    public PlayList<Song> createDefaultPlayList()
    {
        PlayList<Song> playList = new PlayList<>(  );

        playList.putSong( new Song( "Beauty And The Beast" ) );
        playList.putSong( new Song( "Lonesome Crow" ) );
        playList.putSong( new Song( "We Will Rock You" ) );

//        System.out.println( "Generated list:\n" + playList );

        return playList;
    }
}
