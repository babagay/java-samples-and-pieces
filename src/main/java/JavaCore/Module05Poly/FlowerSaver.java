package JavaCore.Module05Poly;


import JavaCore.Module05Poly.Interface.Flower;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

// todo singleton

/**
 * синглтон-фабрика, для хранения одного единственного элемента
 * https://habrahabr.ru/post/321152/
 */
public enum FlowerSaver
{
    FLOWER_SAVER;

    private String text = "";

    private HashMap<String, Integer> flowerMap;

    private Flower[] bouquet;

    private final static String FL = System.getProperty( "file.separator" );
    private final static String USER_DIR = System.getProperty( "user.dir" );
    private static String fileNameDefault;

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

    // todo исправить ошибки
    // todo сделать, чтобы bufferedWriter отпускал файл (lock?)

    public final static void save(String fileName, Flower[] bouquet)
    {
        FlowerSaver saver = getInstance();

        saver.bouquet = bouquet;

        saver.path = saver.getBasePath() + fileName;

        saver.flowerToString();

        final BufferedWriter bufferedWriter = saver.getBufferedWriter();

        saver.observable = io.reactivex.Observable.create( emitter -> {


            bufferedWriter.write( saver.text );

            emitter.onNext( "written" );
        } );

        saver.observable.doFinally( saver::close );




//        try
//        {
//            bufferedWriter.set( saver.getBufferedWriter() );
//            bufferedWriter.get().write( saver.text );
//            bufferedWriter.get().flush();
//        }
//        catch ( IOException e )
//        {
//            e.printStackTrace();
//        }
//        finally
//        {
//            try
//            {
//                if ( bufferedWriter.get() != null )
//                {
//                    bufferedWriter.get().close();
//                }
//            }
//            catch ( IOException e )
//            {
//                e.printStackTrace();
//            }
//        }

    }

    private String path;
    private BufferedWriter bufferedWriter;
    private io.reactivex.Observable<String> observable;

    public static Observable<String> getObservable()
    {
        return getInstance().observable;
    }
//    private void setObservable()
//    {
//
//
//        observable = io.reactivex.Observable.create( new ObservableOnSubscribe<String>()
//        {
//            @Override
//            public void subscribe(ObservableEmitter<String> emitter1) throws Exception
//            {
//                emitter1.onNext( "" );
//            }
//        } );
//
//        observable.doFinally( () -> close() );
//
//
//    }

    private void close()
    {
        try
        {
            bufferedWriter.close();
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
    }

    private BufferedWriter getBufferedWriter()
    {
        if ( bufferedWriter == null )
        {
            try
            {
                bufferedWriter = new BufferedWriter( new FileWriter( new File( path ), false ) );
            }
            catch ( IOException e )
            {
                e.printStackTrace();
            }
        }

        return bufferedWriter;
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
