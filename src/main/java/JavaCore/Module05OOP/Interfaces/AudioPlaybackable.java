package JavaCore.Module05OOP.Interfaces;

import JavaCore.Module05OOP.Player.Player;

public interface AudioPlaybackable extends Player
{
    void playSong();

    void stopSong();
}
