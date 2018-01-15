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
import java.nio.file.ReadOnlyFileSystemException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static JavaCore.Module05Poly.FlowerType.CHAMOMILE;
import static JavaCore.Module05Poly.FlowerType.ROSE;
import static JavaCore.Module05Poly.FlowerType.TULIP;

/**
 * todo singleton
 * https://habrahabr.ru/post/129494/
 * http://www.mantonov.com/2011/02/4-thread-safe-java.html
 *
 * Вложенные классы
 * https://juja.com.ua/java/inner-and-nested-classes/
 *
 * Функциональные интерфейсы
 * https://javanerd.ru/%D0%BE%D1%81%D0%BD%D0%BE%D0%B2%D1%8B-java/%D1%84%D1%83%D0%BD%D0%BA%D1%86%D0%B8%D0%BE%D0%BD%D0%B0%D0%BB%D1%8C%D0%BD%D1%8B%D0%B5-%D0%B8%D0%BD%D1%82%D0%B5%D1%80%D1%84%D0%B5%D0%B9%D1%81%D1%8B-%D0%B2-java-8/
 * https://jsehelper.blogspot.com/2016/05/java-8-1.html
 * http://javanese.online/%D0%BE%D1%81%D0%BD%D0%BE%D0%B2%D1%8B_JVM-%D0%BF%D1%80%D0%BE%D0%B3%D1%80%D0%B0%D0%BC%D0%BC%D0%B8%D1%80%D0%BE%D0%B2%D0%B0%D0%BD%D0%B8%D1%8F/%D0%9E%D0%9E%D0%9F/%D1%84%D1%83%D0%BD%D0%BA%D1%86%D0%B8%D0%BE%D0%BD%D0%B0%D0%BB%D1%8C%D0%BD%D1%8B%D0%B5_%D0%B8%D0%BD%D1%82%D0%B5%D1%80%D1%84%D0%B5%D0%B9%D1%81%D1%8B/
 * https://metanit.com/java/tutorial/9.3.php
 */
final public class FlowerLoader
{
    private static class LazyHolder {
        public static final FlowerLoader loaderInstance = new FlowerLoader();
    }
    
    private FlowerLoader()
    {
        flowerMap = new HashMap<>(  );

        flowerList = new ArrayList<Flower>(  );
    }

    private Flower[] bouquet;

    private ArrayList flowerList;

    private HashMap<String,Integer> flowerMap;

    private static FlowerLoader instance;

    private Observable<String> stringObservable;

    private String generatorType = "simple";

    static Flower[] load(String filePath)
    {
        FlowerLoader loader = getInstance();

        loader.parseFile( filePath );

        loader.generateBouquet();

        return loader.bouquet;
    }

    static Flower[] load(String filePath, String generatorType)
    {
        FlowerLoader loader = getInstance();

        loader.parseFile( filePath );

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

    private void parseFile(String filePath)
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

            loader.fetchFlowers();
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

        else if ( type.equals( "sequential" ) ){
            sequentialGenerator();
        }
    }

    private void fetchFlowers()
    {
        stringObservable.subscribe(
                str -> {

                    String flowerType = str.split( ":" )[0];
                    Integer count = Integer.valueOf(  str.split( ":" )[1] );

                    flowerMap.put( flowerType, count );
                }
        );
    }

    /**
     * todo: проверить варианты
     * Object[] d = flo.parallelStream().collect( Collectors.toCollection( ArrayList::new ) ).toArray();
     * Object[] a = flo.toArray();
     */
    private void simpleGenerator()
    {
        String flowerType;
        Integer flowerCount;

        for ( Map.Entry<String, Integer> entry : flowerMap.entrySet() ){
            flowerType = entry.getKey();
            flowerCount = entry.getValue();

            try
            {
                for ( int i = 0; i < flowerCount; i++ )
                    flowerList.add( createFlower( flowerType ) );
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
            else if ( flowerType.equals( FlowerType.get( TULIP ) ) ){
                tulipCount = flowerCount;
            }
            else if ( flowerType.equals( FlowerType.get( CHAMOMILE ) ) ){
                chamomileCount = flowerCount;
            }
        }

        store.sellSequence( roseCount, chamomileCount, tulipCount );

        bouquet = store.getFlowers();
    }

    private void flowerListToArray()
    {
        bouquet = new Flower[ flowerList.size() ];

        final Integer[] index = new Integer[]{0};

        flowerList.parallelStream().forEach( flower -> bouquet[index[0]++] = (Flower)flower );
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
            throw new Exception( "Недопустимый тип: [" + flowerType + "]" );

        return gardenFlower;
    }

}
