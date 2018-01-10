package JavaCore.Module05Poly;

import com.google.common.collect.Iterables;

import java.util.*;

public class FlowerStore {
    
    private Iterator<String> iterator;
    
    private List<String> flowerTypes = new LinkedList(  );
    
    public FlowerStore ()
    {
        setFlowerTypes();
    }
    
    public static void main (String[] args)
    {
        FlowerStore store = new FlowerStore();
        
        Flower[] flowers = store.sellSequence( 1, 2, 3 );
        
        store.printFlowers( flowers );
    }
    
    public Flower[] sell (int roseCount, int chamomileCount, int tulipCpount)
    {
        int count = roseCount + chamomileCount + tulipCpount;
        
        Flower[] flowers = new Flower[count];
        
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
        }
        
        return flowers;
    }
    
    public Flower[] sellSequence (int roseCount, int chamomileCount, int tulipCpount)
    {
        int count = roseCount + chamomileCount + tulipCpount;
        
        Flower[] flowers = new Flower[count];
        
        for ( int i = 0; i < count; i++ ) {
            
            Flower flower = null;
            
            switch ( getNextFlowerType() ){
                case "Rose":
                    if ( roseCount-- > 0 ) { flower = new Rose(); }
                    else if ( chamomileCount-- > 0 ) { flower = new Chamomile(); }
                    else if ( tulipCpount-- > 0 ) { flower = new Tulip(); }
                    break;
                case "Chamomile":
                    if ( chamomileCount-- > 0 ) { flower = new Chamomile(); }
                    else if ( tulipCpount-- > 0 ) { flower = new Tulip(); }
                    else if ( roseCount-- > 0 ) { flower = new Rose(); }
                    break;
                case "Tulip":
                    if ( tulipCpount-- > 0 ) { flower = new Tulip(); }
                    else if ( roseCount-- > 0 ) { flower = new Rose(); }
                    else if ( chamomileCount-- > 0 ) { flower = new Chamomile(); }
                    break;
            }
    
            flowers[i] = flower;
        }
        
        return flowers;
    }
    
    private void setFlowerTypes()
    {
        flowerTypes.add( Rose.class.getSimpleName() );
        flowerTypes.add( Chamomile.class.getSimpleName() );
        flowerTypes.add( Tulip.class.getSimpleName() );
    
        iterator =   Iterables.cycle( flowerTypes ).iterator();
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
        Arrays.stream( bouquet ).forEach( System.out::println );
    }
}
