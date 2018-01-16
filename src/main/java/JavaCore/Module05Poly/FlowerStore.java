package JavaCore.Module05Poly;

import JavaCore.Module05Poly.Garden.Chamomile;
import JavaCore.Module05Poly.Garden.GardenFlower;
import JavaCore.Module05Poly.Garden.Rose;
import JavaCore.Module05Poly.Garden.Tulip;
import JavaCore.Module05Poly.Interface.Flower;
import com.google.common.collect.Iterables;

import java.net.URL;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * todo переключить на FlowerStore.Wallet
 */
public class FlowerStore
{

    private Iterator<String> iterator;

    private List<String> flowerTypes = new LinkedList();

    private Flower[] flowers;

    private Wallet wallet;

    private int summ = 0;

    private URL resource;

    private final static String FL = System.getProperty( "file.separator" );
    private final static String USER_DIR = System.getProperty( "user.dir" );
    private final static String STORE_FILENAME = "bouquet.txt";
    private final static String STORE_FILEPATH = USER_DIR + FL + "src" + FL + "main" + FL + "resources" + FL + "JavaCore" +
            FL + "Module05Poly" + FL + STORE_FILENAME;

    public FlowerStore()
    {
        setFlowerTypes();

        wallet = new Wallet();

        resource = this.getClass().getResource( "bouquet.txt" );
    }

    public static void main(String[] args)
    {
        Flower[] flowers;

        FlowerStore store = new FlowerStore();

        // [A]: сгенерировать новый букет
        flowers = store.sellSequence( 2, 3, 5 );

        // [B]: сгенерировать букет из данных текстового файла
        //flowers = FlowerLoader.load( store.resource.getPath(), "sequential" );

        store.printFlowers( flowers );

        // [С]: сохранить конфигурацию букета в файл
        FlowerSaver.save( flowers );

       



        // todo tests

    }

    public Flower[] getFlowers()
    {
        return flowers;
    }

    public Flower[] sell(int roseCount, int chamomileCount, int tulipCpount)
    {
        int count = roseCount + chamomileCount + tulipCpount;

        flowers = new Flower[count];

        for ( int i = 0; i < count; i++ )
        {
            if ( roseCount-- > 0 )
            {
                flowers[i] = new Rose();
            }
            else if ( chamomileCount-- > 0 )
            {
                flowers[i] = new Chamomile();
            }
            else if ( tulipCpount-- > 0 )
            {
                flowers[i] = new Tulip();
            }

            increaseWallet( (GardenFlower) flowers[i] );
        }

        return flowers;
    }

    // fixme: не работает конструкция  case FlowerType.get( ROSE ):
    public Flower[] sellSequence(int roseCount, int chamomileCount, int tulipCpount)
    {
        int count = roseCount + chamomileCount + tulipCpount;

        flowers = new Flower[count];

        for ( int i = 0; i < count; i++ )
        {

            Flower flower = null;

            switch ( getNextFlowerType() )
            {
                case "Rose":
                    if ( roseCount-- > 0 )
                    {
                        flower = new Rose();
                    }
                    else if ( chamomileCount-- > 0 )
                    {
                        flower = new Chamomile();
                        iterator.next();
                    }
                    else if ( tulipCpount-- > 0 )
                    {
                        flower = new Tulip();
                        iterator.next();
                        iterator.next();
                    }
                    break;
                case "Chamomile":
                    if ( chamomileCount-- > 0 )
                    {
                        flower = new Chamomile();
                    }
                    else if ( tulipCpount-- > 0 )
                    {
                        flower = new Tulip();
                        iterator.next();
                    }
                    else if ( roseCount-- > 0 )
                    {
                        flower = new Rose();
                        iterator.next();
                        iterator.next();
                    }
                    break;
                case "Tulip":
                    if ( tulipCpount-- > 0 )
                    {
                        flower = new Tulip();
                    }
                    else if ( roseCount-- > 0 )
                    {
                        flower = new Rose();
                        iterator.next();
                    }
                    else if ( chamomileCount-- > 0 )
                    {
                        flower = new Chamomile();
                        iterator.next();
                        iterator.next();
                    }
                    break;
            }

            flowers[i] = flower;

            increaseWallet( (GardenFlower) flower );
        }

        return flowers;
    }

    public int getWallet()
    {
        return wallet.getSumm();
    }

    private void increaseWallet(GardenFlower flower)
    {
        wallet.increase( flower.getPrice() );
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

        if ( iterator.hasNext() )
        {
            flowerType = iterator.next();
        }

        return flowerType;
    }

    private void printFlowers(Flower[] bouquet)
    {
        if ( bouquet != null )
        {
            Arrays.stream( bouquet ).forEach( System.out::println );
        }
    }

    class Wallet
    {
        private int summ = 0;

        public Wallet()
        {
            this( 0 );
        }

        public Wallet(int summ)
        {
            this.summ = summ;
        }

        public int getSumm()
        {
            return this.summ;
        }

        public int increase(int summ)
        {
            this.summ = this.summ + summ;

            return this.summ;
        }
    }
}
