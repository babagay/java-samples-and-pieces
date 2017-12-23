package JavaCore.Module05OOP;

import JavaCore.Module05OOP.Factory.PlayerFactory;
import JavaCore.Module05OOP.Factory.SimpleBuilder;
import JavaCore.Module05OOP.PlayerMP3.Elenberg;
import JavaCore.Module05OOP.PlayerMP3.Xiaomi;
import JavaCore.Module05OOP.Song.PlayList;
import JavaCore.Module05OOP.Song.Song;

public class App
{
    PlayerFactory simpleBuilder;

    static App app;

    public static void main(String[] args)
    {
        app = new App();

        Elenberg elenberg = (Elenberg) app.getSimpleBuilder().getSimplePlayer( "Elenberg" );
        Xiaomi xiaomi = (Xiaomi) app.getSimpleBuilder().getSimplePlayer( "Xiaomi" );

        System.out.println( elenberg );
        System.out.println( xiaomi );


        PlayList<Song> pl = app.generatePlayList();





    }

    private PlayerFactory getSimpleBuilder()
    {
        return simpleBuilder == null ? new SimpleBuilder() : simpleBuilder;
    }

    private PlayList<Song> generatePlayList()
    {
        PlayList<Song> playList = new PlayList<>(  );

        playList.putSong( new Song( "foo" ) );
        playList.putSong( new Song( "bar" ) );
        playList.putSong( new Song( "baz" ) );

        System.out.println( "Generated list:\n" + playList );

        return playList;
    }
}
