package JavaCore.Module05OOP.Song;

import java.io.File;

public class SongMP3 extends Song
{
    private File file;

    public SongMP3(String name)
    {
        super( name );
    }

    public SongMP3(File file)
    {
        super( file.getName() );

        this.file = file;
    }

    public File getFile()
    {
        return file;
    }
}
