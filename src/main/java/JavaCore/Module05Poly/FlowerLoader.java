package JavaCore.Module05Poly;

import JavaCore.Module05Poly.Garden.Chamomile;
import JavaCore.Module05Poly.Garden.GardenFlower;
import JavaCore.Module05Poly.Garden.Rose;
import JavaCore.Module05Poly.Garden.Tulip;
import JavaCore.Module05Poly.Interface.Flower;
import io.reactivex.Observable;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

import static JavaCore.Module05Poly.FlowerType.*;

final public class FlowerLoader
{
    private static class LazyHolder
    {
        public static final FlowerLoader loaderInstance = new FlowerLoader();
    }

    private FlowerLoader()
    {
        flowerMap = new HashMap<>();

        flowerList = new ArrayList<Flower>();
    }

    private Flower[] bouquet;

    private ArrayList flowerList;

    private HashMap<String, Integer> flowerMap;

    private static FlowerLoader instance;

    private Observable<String> stringObservable;

    private String generatorType = "simple";

    private final String LS = System.getProperty( "line.separator" );

    static Flower[] load(String filePath)
    {
        FlowerLoader loader = getInstance();

        try
        {
            loader.parseFile( filePath );
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }

        loader.generateBouquet();

        return loader.bouquet;
    }

    static Flower[] load(String filePath, String generatorType)
    {
        FlowerLoader loader = getInstance();

        try
        {
            loader.parseFile( filePath );
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }

        loader.generatorType = generatorType;

        loader.generateBouquet();

        return loader.bouquet;
    }

    public static FlowerLoader getInstance()
    {
        return LazyHolder.loaderInstance;
    }

    public Flower[] getBouquet()
    {
        return bouquet;
    }

    public void setStringObservable(Observable<String> stringObservable)
    {
        this.stringObservable = stringObservable;
    }

    private void parseFile(String filePath) throws IOException
    {
        FlowerLoader loader = this;

        if ( !Files.exists( Paths.get( filePath ) ) )
            throw new IOException( "File is not exists" );

        setObservableViaChannel( filePath );

        loader.fetchFlowers();
    }

    /**
     * fixme IOException: Stream closed
     */
    private void setObservableViaReader(String filePath)
    {
        FlowerLoader loader = this;

        try (
                BufferedReader bufferedReader = new BufferedReader( new FileReader( filePath ) )
        )
        {
            loader.setStringObservable( Observable.create( emitter ->
                    {
                        String str;

                        while ( (str = bufferedReader.readLine()) != null )
                        {
                            emitter.onNext( str );
                        }
                    }
                    )
            );

        }
        catch ( FileNotFoundException e )
        {
            e.printStackTrace();
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
    }

    private void setObservableViaChannel(String filePath)
    {
        FlowerLoader loader = this;

        loader.setStringObservable( Observable.create(
                emitter ->
                {
                    AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open( Paths.get( filePath ), StandardOpenOption.READ );

                    ByteBuffer buffer = ByteBuffer.allocate( 2048 );
                    long position = 0;

                    Future<Integer> operation = fileChannel.read( buffer, position );

                    while ( !operation.isDone() ) ; // [!] Магическая строка

                    buffer.flip();
                    byte[] data = new byte[buffer.limit()];
                    buffer.get( data );
                    String payload = new String( data );
                    buffer.clear();

                    if ( !payload.equals( "" ) )
                    {
                        Arrays.stream( payload.split( LS ) ).forEach( str -> emitter.onNext( str ) );
                    }
                }
                )
        );
    }

    private void generateBouquet()
    {
        FlowerLoader loader = this;

        try
        {
            // Generation type
            loader.useGenerator( loader.generatorType );
        }
        catch ( InstantiationException e )
        {
            e.printStackTrace();
        }
        catch ( IllegalAccessException e )
        {
            e.printStackTrace();
        }
        catch ( ClassNotFoundException e )
        {
            e.printStackTrace();
        }
        catch ( ReflectiveOperationException e )
        {
            e.printStackTrace();
        }
    }

    public void useGenerator(String type) throws ReflectiveOperationException
    {
        if ( type.equals( "simple" ) )
        {
            simpleGenerator();
        }

        else if ( type.equals( "sequential" ) )
        {
            sequentialGenerator();
        }
    }

    private void fetchFlowers()
    {
        stringObservable.subscribe(
                str -> {

                    String flowerType = str.split( ":" )[0];
                    Integer count = Integer.valueOf( str.split( ":" )[1] );

                    flowerMap.put( flowerType, count );
                },
                err -> err.printStackTrace()
        );
    }

    private void simpleGenerator()
    {
        String flowerType;
        Integer flowerCount;

        for ( Map.Entry<String, Integer> entry : flowerMap.entrySet() )
        {
            flowerType = entry.getKey();
            flowerCount = entry.getValue();

            try
            {
                for ( int i = 0; i < flowerCount; i++ )
                {
                    flowerList.add( createFlower( flowerType ) );
                }
            }
            catch ( Exception e )
            {
                e.printStackTrace();
            }
        }

        flowerListToArray();
    }

    private void sequentialGenerator()
    {
        String flowerType;
        Integer flowerCount;
        FlowerStore store = new FlowerStore();
        Integer tulipCount = 0, roseCount = 0, chamomileCount = 0;

        for ( Map.Entry<String, Integer> entry : flowerMap.entrySet() )
        {
            flowerType = entry.getKey();
            flowerCount = entry.getValue();

            if ( flowerType.equals( FlowerType.get( ROSE ) ) )
            {
                roseCount = flowerCount;
            }
            else if ( flowerType.equals( FlowerType.get( TULIP ) ) )
            {
                tulipCount = flowerCount;
            }
            else if ( flowerType.equals( FlowerType.get( CHAMOMILE ) ) )
            {
                chamomileCount = flowerCount;
            }
        }

        store.sellSequence( roseCount, chamomileCount, tulipCount );

        bouquet = store.getFlowers();
    }

    private void flowerListToArray()
    {
        bouquet = new Flower[flowerList.size()];

        final Integer[] index = new Integer[]{0};

        flowerList.parallelStream().forEach( flower -> bouquet[index[0]++] = (Flower) flower );
    }

    private <T extends Flower> GardenFlower createFlower(String flowerType) throws Exception
    {
        GardenFlower gardenFlower = null;

        if ( flowerType.equals( FlowerType.get( ROSE ) ) )
        {
            Class<Rose> roseClass =
                    (Class<Rose>) Class.forName( "JavaCore.Module05Poly.Garden." + flowerType );
            Rose rose = roseClass.cast( new Rose() );
            gardenFlower = rose;
        }
        else if ( flowerType.equals( FlowerType.get( CHAMOMILE ) ) )
        {
            Class<Chamomile> chamomileClass = (Class<Chamomile>) Class.forName( "JavaCore.Module05Poly.Garden." + flowerType );
            Chamomile chamomile = chamomileClass.cast( new Chamomile() );
            gardenFlower = chamomile;
        }
        else if ( flowerType.equals( FlowerType.get( TULIP ) ) )
        {
            Class<Tulip> tulipClass = (Class<Tulip>) Class.forName( "JavaCore.Module05Poly.Garden." + flowerType );
            Tulip tulip = tulipClass.cast( new Tulip() );
            gardenFlower = tulip;
        }

        if ( gardenFlower == null )
        {
            throw new Exception( "Недопустимый тип: [" + flowerType + "]" );
        }

        return gardenFlower;
    }

}
