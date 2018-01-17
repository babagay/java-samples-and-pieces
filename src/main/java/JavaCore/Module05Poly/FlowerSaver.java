package JavaCore.Module05Poly;

import JavaCore.Module05Poly.Interface.Flower;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.HashMap;

public enum FlowerSaver
{
    FLOWER_SAVER;

    private String text = "";

    private HashMap<String, Integer> flowerMap;

    private Flower[] bouquet;

    private final static String FL = System.getProperty( "file.separator" );
    private final static String USER_DIR = System.getProperty( "user.dir" );
    private static String fileNameDefault;
    
    private String path;
    private BufferedWriter bufferedWriter;
    private FileWriter fileWriter;

    FlowerSaver()
    {
        // if ( ordinal() != 0 ) throw new Exception( "The only one must still alive" );

        init();

        flowerMap = new HashMap<>();
    }

    public final static void save(Flower[] bouquet)
    {
        save( fileNameDefault, bouquet );
    }

    public final static void save(String fileName, Flower[] bouquet)
    {
        FlowerSaver saver = getInstance();

        saver.bouquet = bouquet;

        saver.path = saver.getBasePath() + fileName;

        saver.flowerToString();

        Writer writer;

        try
        {
            writer = saver.getBufferedWriter();
            writer.write( saver.text );
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
        finally
        {
            saver.close();
        }

    }

    private void close()
    {
        try
        {
            fileWriter.close();
            //bufferedWriter.close();
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
    }

    private BufferedWriter getBufferedWriter() throws IOException
    {
        bufferedWriter = new BufferedWriter( getFileWriter() );
    
        return bufferedWriter;
    }

    private FileWriter getFileWriter() throws IOException
    {
        File file = new File( path );

        if ( !file.exists() )
        {
            file.createNewFile();
        }

        fileWriter = new FileWriter( file, false );

        return fileWriter;
    }

    private void flowerToString()
    {
        String newline = System.getProperty( "line.separator" );

        bouquetToMap();

        flowerMap.entrySet().stream().forEach( entry -> text += entry.getKey() + ":" + entry.getValue() + newline );
    }

    private void bouquetToMap()
    {
        Arrays.stream( bouquet ).forEach( flower -> {
            String flowerType = flower.getClass().getSimpleName().toString();

            Integer flowerCount = flowerMap.get( flowerType );

            flowerCount = flowerCount == null ? 1 : flowerCount + 1;

            flowerMap.put( flowerType, flowerCount );
        } );
    }

    private final static FlowerSaver getInstance()
    {
        return FlowerSaver.valueOf( "FLOWER_SAVER" );
    }

    private void init()
    {
        fileNameDefault = "bouquet.txt";
    }

    private String getBasePath()
    {
        return USER_DIR + FL + "src" + FL + "main" + FL + "resources" + FL + "JavaCore" +
                FL + "Module05Poly" + FL;
    }


}
