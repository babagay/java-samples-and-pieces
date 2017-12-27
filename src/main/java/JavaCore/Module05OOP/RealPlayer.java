package JavaCore.Module05OOP;

import JavaCore.Module05OOP.Builder.PlayerBuilder;
import JavaCore.Module05OOP.PlayerMP3.Enchanced.LG;
import JavaCore.Module05OOP.PlayerMP3.Extra.Digital;
import JavaCore.Module05OOP.Song.PlayList;
import JavaCore.Module05OOP.Song.SongMP3;
import com.gluonhq.particle.application.ParticleApplication;
import com.gluonhq.particle.state.StateManager;
import javafx.scene.Scene;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

import javax.inject.Inject;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Optional;

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


        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
    }

    @Override
    public void postInit(Scene scene)
    {
        scene.getStylesheets().add( RealPlayer.class.getResource( "style.css" ).toExternalForm() );

        setTitle( "RealPlayer Desktop Application" );

        getParticle().buildMenu( "File -> [signin,---, exit]", "Help -> [about]" );

        getParticle().getToolBarActions().addAll( actions( "signin" ) );




        // [!] пробросить переменную через StateManager
        getParticle().getStateManager().setProperty( "foo", "bar" );
        //userName = stateManager.getProperty( "foo" ).orElse( "" ).toString();





        // todo создать MP3 player Digital (сперва в него надо встроить поддержку MP3)

        // todo создать плэйлист из SongMP3-песен и загрузить его в плеер

        // todo Play btn

        // todo Stop btn
    }

    public static void _main(String[] args)
    {

        RealPlayer realPlayer = new RealPlayer();

        try
        {
            // step 1 - берем URL
            URL resource = realPlayer.getClass().getResource( "foo.mp3" );

            // [!] так не работает:
            // URI uri = new URI( "/foo.mp3" );

            // step 2 - берем file
            File file = new File( resource.toURI() );

            // File file = Paths.get( resource.toURI() ).toFile(); // OK

            FileInputStream fileInputStream = new FileInputStream( file );

            AdvancedPlayer player = new AdvancedPlayer( fileInputStream );


            player.setPlayBackListener( new PlaybackListener()
            {
                public int pausedOnFrame = 0;

                @Override
                public void playbackFinished(PlaybackEvent evt)
                {
                    pausedOnFrame = evt.getFrame();
                }
            } );

            player.play();
        }
        catch ( JavaLayerException e )
        {
            e.printStackTrace();
        }
        catch ( FileNotFoundException e )
        {
            e.printStackTrace();
        }
        catch ( URISyntaxException e )
        {
            e.printStackTrace();
        }

    }


}
