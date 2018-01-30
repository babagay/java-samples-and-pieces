package JavaCore.Module06;

import JavaCore.Module05Poly.Garden.Chamomile;
import JavaCore.Module05Poly.Garden.Rose;
import JavaCore.Module05Poly.Garden.Tulip;
import JavaCore.Module05Poly.Interface.Flower;

import java.lang.reflect.InvocationTargetException;

public class App
{
    public static void main(String[] args)
    {
        Flower rose = new Rose();

        MyArrayList<Flower> bouquet = new MyArrayList<>( 2 );
 
        bouquet.add( rose );
        bouquet.add( new Rose() );
        bouquet.add( new Rose() );
        bouquet.add( new Rose() );
        bouquet.add( new Rose() );
        bouquet.add( new Rose() );
        bouquet.add( new Rose() );
        bouquet.add( new Tulip() );
        bouquet.add( new Chamomile() );
        bouquet.add( new Tulip() );
        bouquet.add( new Rose() );
        bouquet.add( new Rose() );
        bouquet.add( new Rose() );

        // [!] Object[] вместо Flower[]
        Object[] flowerSet = bouquet.toArray();

        Flower[] bouquet1 = new Flower[1];
        bouquet1[0] = (Flower) flowerSet[0];

        // Так не работает - ClassCastException
        // FlowerSaver.save( (Flower[]) flowerSet );

        // Так работает
        // FlowerSaver.save( bouquet1 );
    }

}
