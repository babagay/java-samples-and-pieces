package JavaCore.Module05OOP.PlayerMP3.Extra;

import JavaCore.Module05OOP.Song.SongMP3;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * http://www.cyberforum.ru/java-j2se/thread959537.html
 *
 * Структура класса:
 * 1) [companion] object
 * 2) Поля: abstract, override, public, internal, protected, private
 * 3) Блок инициализации: init, конструкторы
 * 4) Абстрактные методы
 * 5) Переопределенные методы родительского класса (желательно в том же порядке, в каком они следуют в родительском классе)
 * 6) Реализации методов интерфейсов (желательно в том же порядке, в каком они следуют в описании класса, соблюдая при этом порядок описания этих методов в самом интерфейсе)
 * 7) public методы
 * 8) internal методы
 * 9) protected методы
 * 10) private методы
 * 11) inner классы
 */
public class Digital extends Panasonic
{
    protected AdvancedPlayer player = null;

    protected Thread playingSongThread;

    protected int pausedOnFrame = 0;

    protected boolean playStatus = false;

    public Digital(String name)
    {
        super( name );
    }

    // [!] Метод пришел из реализации предка, поэтому, название не трогаем (можно было переписать название метода)
    @Override
    protected void play()
    {
        File file = getCurrentSongMP3().getFile();

        System.out.println("playing: " + file.getName());

        playingSongThread = new Thread( () ->
        {
            initPlayer( file );

            playbackStart();
        });

        playingSongThread.start();
    }

    @Override
    protected SongMP3 getFirstSong()
    {
        return (SongMP3) super.getFirstSong();
    }

    @Override
    public String toString()
    {
        return "Digital{" +
                "name='" + name + '\'' +
                '}';
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void pauseSong()
    {
        playbackPause();
    }

    public void stopSong()
    {
        try
        {
            playingSongThread.interrupt();

            playbackStop();

            System.out.println("Thread interrupted");
        }
        catch ( Throwable e )
        {
            e.printStackTrace();
        }
    }

    // [!] видимо, не очень хорошо, что данный класс знает про конкретного потомка Song
    protected SongMP3 getCurrentSongMP3()
    {
        return (SongMP3) currentSong;
    }

    private void playbackStart()
    {
        if ( playStatus == false ){
            System.out.println("Frame: " + pausedOnFrame);
            try
            {
                player.play(pausedOnFrame,Integer.MAX_VALUE);
            }
            catch ( Throwable e )
            {
               // e.printStackTrace();
                System.out.println(e.getMessage());
            }
        } else {
            playbackPause();
        }
    }

    private void playbackPause()
    {
        playStatus = false;
        player.stop();
    }

    private void playbackStop()
    {
        playStatus = false;
        player.close();
    }

    private void playbackResume()
    {
        playbackStart();
    }

    private void initPlayer(File file)
    {
        try
        {
            FileInputStream fileInputStream = new FileInputStream( file );

            player = new AdvancedPlayer( fileInputStream );
        }
        catch ( JavaLayerException e )
        {
            e.printStackTrace();
        }
        catch ( FileNotFoundException e )
        {
            e.printStackTrace();
        }

        player.setPlayBackListener( new PlaybackListener()
        {
            @Override
            public void playbackFinished(PlaybackEvent evt)
            {
                evt.setSource( player );
                pausedOnFrame = evt.getFrame();
                super.playbackFinished( evt );
            }

            @Override
            public void playbackStarted(PlaybackEvent evt)
            {
                evt.setSource( player );
                evt.setFrame( pausedOnFrame );
                super.playbackStarted( evt );
            }
        } );

        try
        {
            player.play();
        }
        catch ( JavaLayerException e )
        {
            e.printStackTrace();
        }
    }



}
