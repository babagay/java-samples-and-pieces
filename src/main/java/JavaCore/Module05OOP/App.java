package JavaCore.Module05OOP;

import JavaCore.Module05OOP.Builder.PlayerBuilder;
import JavaCore.Module05OOP.Factory.PlayerFactory;
import JavaCore.Module05OOP.Factory.SimpleFactory;
import JavaCore.Module05OOP.PlayerMP3.Simple.Elenberg;
import JavaCore.Module05OOP.PlayerMP3.Simple.Xiaomi;
import JavaCore.Module05OOP.Song.PlayList;
import JavaCore.Module05OOP.Song.Song;
import com.sun.xml.internal.messaging.saaj.soap.SAAJMetaFactoryImpl;

public class App
{
    private PlayerBuilder builder;

    static App app;

    public App() {
        builder = new PlayerBuilder();
    }

    public static void main(String[] args)
    {
        app = new App();

        Elenberg elenberg = null;
        Xiaomi xiaomi = null;

        try {
            elenberg = (Elenberg) app.builder.setMnemonicType("Elenberg").getPlayer();
            xiaomi = new SimpleFactory<Xiaomi>().getPlayerByVendor("Xiaomi");
        } catch (Exception e) {
            System.out.println("Exception thrown: ");
            e.printStackTrace();
        }

//        Xiaomi xiaomi = (Xiaomi) app.builder.getSimpleBuilder().getSimplePlayer( "Xiaomi" );

        System.out.println( elenberg );
        System.out.println( xiaomi );


//        PlayList<Song> pl = app.generatePlayList();





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
