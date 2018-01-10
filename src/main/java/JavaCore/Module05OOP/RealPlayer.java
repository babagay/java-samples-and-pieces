package JavaCore.Module05OOP;

import JavaCore.Module05OOP.Builder.PlayerBuilder;
import JavaCore.Module05OOP.PlayerMP3.Extra.Digital;
import JavaCore.Module05OOP.Song.PlayList;
import JavaCore.Module05OOP.Song.SongMP3;
import com.gluonhq.particle.application.Particle;
import com.gluonhq.particle.application.ParticleApplication;
import javafx.scene.Scene;

import java.io.File;
import java.net.URL;

import static org.controlsfx.control.action.ActionMap.actions;

/**
 * http://docs.gluonhq.com/particle/1.1.3/
 */
public class RealPlayer
        extends ParticleApplication
{

    private PlayerBuilder builder;

    private int pausedOnFrame = 0;

    private PlayList<SongMP3> playList;

    private Digital digitalPlayer;

    public RealPlayer()
    {
        super("Real MP3 Player");

        builder = new PlayerBuilder();

        try
        {
            digitalPlayer = (Digital) builder.setMnemonicType( "Digital" ).getPlayer();

            playList = new PlayList<>(  );

            // step 1 - берем URL
            URL resource = this.getClass().getResource( "foo.mp3" );

            // step 2 - берем file
            File file = new File( resource.toURI() );

            SongMP3 song = new SongMP3( file );

            playList.putSong( song );

            digitalPlayer.setPlayList( playList );

            AppState.getInstance().put( "player", digitalPlayer );

            // [!]  из-за того, что getProperty() возвращает строку, данный способ проброса объектов не работает:
            // getApp().getStateManager().setProperty( "player", digitalPlayer );
            // Вместо этого можно использовать синглтон или IoC-контейнер
            // [реализации IoC] https://ru.stackoverflow.com/questions/638264/%D0%92-%D1%87%D0%B5%D0%BC-%D0%BE%D1%82%D0%BB%D0%B8%D1%87%D0%B8%D0%B5-service-locator-%D0%BE%D1%82-ioc-container
            //        Optional<Digital> player = getApp().getStateManager().getProperty( "player" )
            //                .filter( Digital.class::isInstance )
            //                .map( Digital.class::cast )
            //                ;
            //        Digital pl = (Digital) player.orElse( PlayerBuilder.create( "Digital" ) );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
    }

    @Override
    public void postInit (Scene scene)
    {
        scene.getStylesheets().add( RealPlayer.class.getResource( "style.css" ).toExternalForm() );

        setTitle( "RealPlayer Desktop Application" );

        getApp().buildMenu( "File -> [signin,---, exit]", "Help -> [about]" );

        getApp().getToolBarActions().addAll( actions( "signin" ) );
    }
    
    private Particle getApp ()
    {
        return getParticle();
    }
/*
    public static void _main (String[] args)
    {
        RealPlayer realPlayer = new RealPlayer();

        try {
            // step 1 - берем URL
            URL resource = realPlayer.getClass().getResource( "foo.mp3" );

            // [!] так не работает:
            // URI uri = new URI( "/foo.mp3" );

            // step 2 - берем file
            File file = new File( resource.toURI() );

            // File file = Paths.get( resource.toURI() ).toFile(); // OK

            FileInputStream fileInputStream = new FileInputStream( file );

            AdvancedPlayer player = new AdvancedPlayer( fileInputStream );

            player.setPlayBackListener( new PlaybackListener() {
                public int pausedOnFrame = 0;

                @Override
                public void playbackFinished (PlaybackEvent evt)
                {
                    pausedOnFrame = evt.getFrame();
                }
            } );

            player.play();
        }
        catch ( JavaLayerException e ) {
            e.printStackTrace();
        }
        catch ( FileNotFoundException e ) {
            e.printStackTrace();
        }
        catch ( URISyntaxException e ) {
            e.printStackTrace();
        }

    }
*/

}
