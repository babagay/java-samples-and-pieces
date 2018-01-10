package JavaCore.Module05Poly;

public class FlowerStore {
    
    public FlowerStore ()
    {
        System.out.println( "FlowerStore" );
    }
    
    public static void main (String[] args)
    {
        
        FlowerStore store = new FlowerStore();
        
        // todo сделать поток rx и применить к нему foreach
        
        
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
            int index = i+1;
            
            if ( index % 2 == 0 && chamomileCount-- > 0 ) {
                flowers[i] = new Chamomile();
            }
            else if ( index % 3 == 0 && tulipCpount-- > 0 ) {
                flowers[i] = new Tulip();
            } else if ( roseCount-- > 0 ) {
                //todo вставлять другие цветы, если нету роз. И так в кажой секции
                    flowers[i] = new Rose();
            }
        }
    
        return flowers;
    }
    
    private void printFlowers (Flower[] bouquet)
    {
        for ( Flower flower : bouquet ) {
            System.out.println( flower );
        }
    }
}
