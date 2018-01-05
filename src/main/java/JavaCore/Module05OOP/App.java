package JavaCore.Module05OOP;

import JavaCore.Module05OOP.Builder.PlayerBuilder;
import JavaCore.Module05OOP.Factory.EnchancedFactory;
import JavaCore.Module05OOP.Factory.ExtraFactory;
import JavaCore.Module05OOP.Player.Player;
import JavaCore.Module05OOP.PlayerMP3.Enchanced.LG;
import JavaCore.Module05OOP.PlayerMP3.Enchanced.Sony;
import JavaCore.Module05OOP.PlayerMP3.Extra.Panasonic;
import JavaCore.Module05OOP.PlayerMP3.Extra.Pioneer;
import JavaCore.Module05OOP.PlayerMP3.Simple.Elenberg;
import JavaCore.Module05OOP.PlayerMP3.Simple.Xiaomi;
import JavaCore.Module05OOP.Song.PlayList;
import JavaCore.Module05OOP.Song.PlayListBuilder;
import JavaCore.Module05OOP.Song.Song;

import java.util.HashMap;

public class App
{
    private PlayerBuilder builder;
    private PlayListBuilder plBuilder;

    static App app;

    public App()
    {
        builder = new PlayerBuilder();
        plBuilder = new PlayListBuilder();
    }

    public static void main(String[] args)
    {
        app = new App();

        Elenberg elenberg;
        Xiaomi xiaomi;

        LG lg;
        Sony sony;

        Pioneer pioneer;
        Panasonic panasonic;

        PlayList<Song> pl = app.plBuilder.createDefaultPlayList();

        HashMap<String, Object> elenbergParams = new HashMap<>();
        elenbergParams.put( "price", 150. );

        HashMap<String, Object> xiaomiParams = new HashMap<>();
        xiaomiParams.put( "price", 175. );

        HashMap<String, Object> lgParams = new HashMap<>();
        lgParams.put( "price", 225. );

        try
        {
            elenberg = PlayerBuilder.getPlayer( "Elenberg", elenbergParams ); // [A]
            xiaomi = (Xiaomi) app.builder.setMnemonicType( "Xiaomi" ).setParams( xiaomiParams ).getPlayer(); // [B]

            lg = (LG) app.builder.setMnemonicType( "LG" ).setParams( lgParams ).getPlayer();
            sony = new EnchancedFactory<Sony>().getPlayerByVendor( Sony.class.toString().split( "\\." )[4] ); // [C]

            pioneer = (Pioneer) app.builder.setMnemonicType( Pioneer.class.toString().split( "\\." )[4] ).getPlayer();
            panasonic = new ExtraFactory<Panasonic>().getPlayerByVendor( Panasonic.class.toString().split( "\\." )[4] );

            System.out.println( pl );

            System.out.println("\nPlayer 1");
            elenberg.setCurrentSong( new Song( "Flowers" ) ).playSong();

            System.out.println("\nPlayer 2");
            xiaomi.setCurrentSong( new Song( "Mon manège à moi" ) ).playSong();

            System.out.println("\nPlayer 3");
            lg.setPlayList( pl );
            lg.playSong();
            System.out.println("");
            lg.playAllSongs();

            System.out.println("\nPlayer 4");
            sony.setPlayList( pl );
            sony.playSong();

            System.out.println("\nPlayer 5");
            pioneer.setPlayList( pl );
            pioneer.playSong();
            pioneer.shuffle();
            System.out.println("");
            pioneer.playAllSongs();

            System.out.println("\nPlayer 6");
            panasonic.setPlayList( pl );
            panasonic.shuffle();
            panasonic.playAllSongs();

        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }


    }


}
