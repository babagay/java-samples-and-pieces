package JavaCore.Module05Poly;

import JavaCore.Module05Poly.Garden.Chamomile;
import JavaCore.Module05Poly.Garden.GardenFlower;
import JavaCore.Module05Poly.Garden.Rose;
import JavaCore.Module05Poly.Garden.Tulip;
import JavaCore.Module05Poly.Interface.Flower;
import com.google.common.collect.Iterables;
import io.reactivex.Observable;

import java.io.*;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static com.google.common.base.Ascii.FS;

/**
 * todo переключить на FlowerStore.Wallet
 */
public class FlowerStore {

    private Iterator<String> iterator;

    private List<String> flowerTypes = new LinkedList(  );

    private Flower[] flowers;

    private Wallet wallet;

    private int summ = 0;

    private URL resource;
    
    private final static String FL = System.getProperty("file.separator");
    private final static String USER_DIR = System.getProperty("user.dir");
    private final static String STORE_FILENAME = "bouquet.txt";
    private final static String STORE_FILEPATH = USER_DIR + FL + "src" + FL + "main" + FL + "resources" + FL + "JavaCore" +
                                                 FL + "Module05Poly" + FL + STORE_FILENAME;

    public FlowerStore ()
    {
        setFlowerTypes();

        wallet = new Wallet();

        resource = this.getClass().getResource( "bouquet.txt" );
    }

    public static void main (String[] args)
    {
        Flower[] flowers;

        FlowerStore store = new FlowerStore();

        // [A]
         flowers = store.sellSequence( 12, 14, 15 );

        // [B]
        // flowers = FlowerLoader.load( store.resource.getPath(), "sequential" );

        store.printFlowers( flowers );
    
     
    
     
            FlowerSaver.save(STORE_FILEPATH, flowers);
        
    
    
        // todo tests

    }

    public Flower[] getFlowers()
    {
        return flowers;
    }

    public Flower[] sell (int roseCount, int chamomileCount, int tulipCpount)
    {
        int count = roseCount + chamomileCount + tulipCpount;

        flowers = new Flower[count];

        for ( int i = 0; i < count; i++ ) {
            if ( roseCount-- > 0 ) {
                flowers[i] = new Rose();
            }
            else if ( chamomileCount-- > 0 ) {
                flowers[i] = new Chamomile();
            }
            else if ( tulipCpount-- > 0 ) {
                flowers[i] = new Tulip();
            }

            increaseWallet( (GardenFlower) flowers[i] );
        }

        return flowers;
    }

    public Flower[] sellSequence (int roseCount, int chamomileCount, int tulipCpount)
    {
        int count = roseCount + chamomileCount + tulipCpount;

        flowers = new Flower[count];

        for ( int i = 0; i < count; i++ ) {

            Flower flower = null;

            switch ( getNextFlowerType() ){
                case "Rose":
                    if ( roseCount-- > 0 ) { flower = new Rose(); }
                    else if ( chamomileCount-- > 0 ) { flower = new Chamomile(); iterator.next(); }
                    else if ( tulipCpount-- > 0 ) { flower = new Tulip(); iterator.next(); iterator.next(); }
                    break;
                case "Chamomile":
                    if ( chamomileCount-- > 0 ) { flower = new Chamomile(); }
                    else if ( tulipCpount-- > 0 ) { flower = new Tulip(); iterator.next(); }
                    else if ( roseCount-- > 0 ) { flower = new Rose(); iterator.next(); iterator.next(); }
                    break;
                case "Tulip":
                    if ( tulipCpount-- > 0 ) { flower = new Tulip(); }
                    else if ( roseCount-- > 0 ) { flower = new Rose(); iterator.next(); }
                    else if ( chamomileCount-- > 0 ) { flower = new Chamomile(); iterator.next(); iterator.next(); }
                    break;
            }

            flowers[i] = flower;

            increaseWallet( (GardenFlower) flower );
        }

        return flowers;
    }

    public int getWallet()
    {
//        return wallet.getSumm();

        return summ;
    }

    private void increaseWallet(GardenFlower flower)
    {
        summ += flower.getPrice();

//        wallet.increase( flower.getPrice() );
    }

    private void setFlowerTypes()
    {
        flowerTypes.add( Rose.class.getSimpleName() );
        flowerTypes.add( Chamomile.class.getSimpleName() );
        flowerTypes.add( Tulip.class.getSimpleName() );

        iterator = Iterables.cycle( flowerTypes ).iterator();
    }

    private String getNextFlowerType()
    {
        String flowerType = "";

        if( iterator.hasNext() ) {
            flowerType = iterator.next();
        }

        return flowerType;
    }

    private void printFlowers (Flower[] bouquet)
    {
        if ( bouquet != null )
            Arrays.stream( bouquet ).forEach( System.out::println );
    }

    // FIXME
    class Wallet
    {
        private int summ = 0;
        private int su;

        public Wallet()
        {
            this(0);
        }

        public Wallet(int summ)
        {
            this.su = summ;
        }

        public int getSumm()
        {
            return su;
        }

        public int increase(int summ)
        {
            int s = su + summ;
            return s;
        }
    }
}
