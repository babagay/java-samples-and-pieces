package JavaCore.Module05OOP.PlayerMP3;

import JavaCore.Module05OOP.Gadget.Gadget;
import JavaCore.Module05OOP.Interfaces.AudioPlaybackable;
import scala.util.parsing.combinator.testing.Str;

abstract public class PlayerSimple extends Gadget implements AudioPlaybackable
{
    public PlayerSimple(String name)
    {
        this.name = name;
    }

    @Override
    public void playSong()
    {
        System.out.println( "Playing: .." );
    }
}
