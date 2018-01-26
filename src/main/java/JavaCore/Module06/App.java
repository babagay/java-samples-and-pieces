package JavaCore.Module06;

import JavaCore.Module05Poly.FlowerSaver;
import JavaCore.Module05Poly.Garden.Chamomile;
import JavaCore.Module05Poly.Garden.Rose;
import JavaCore.Module05Poly.Garden.Tulip;
import JavaCore.Module05Poly.Interface.Flower;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;

public class App
{
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException
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

         MyHashMap<String, Flower> map = new MyHashMap<String, Flower>();

         map.put( "Rose", new Rose(  ) );
         map.put( "Rose1", new Rose(  ) );


    }

//    private static void annotationProcess() throws IllegalAccessException, InstantiationException, InvocationTargetException
//    {
//        Class<MyArrayList> clazz = MyArrayList.class;
//
//        Method[] methods = clazz.getMethods();
//
//        for ( Method method: methods ){
//            if ( method.isAnnotationPresent( Foo.class ) ){
//
//                Foo a = method.getAnnotation( Foo.class );
//
//                String[] f = a.tags();
//
//            }
//        }
//    }
}
