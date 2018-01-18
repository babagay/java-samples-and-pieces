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

        annotationProcess();

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

        // Так работает
        Flower singleFlower = (Flower)flowerSet[0];

        // Так нет
        // FlowerSaver.save( flowerSet );


    }

    private static void annotationProcess() throws IllegalAccessException, InstantiationException, InvocationTargetException
    {
        Class<MyArrayList> clazz = MyArrayList.class;

        Method[] methods = clazz.getMethods();

        for ( Method method: methods ){
            if ( method.isAnnotationPresent( Foo.class ) ){
//                System.out.println(method.getGenericReturnType());
//                System.out.println( method.getAnnotatedReturnType() );

                Foo a = method.getAnnotation( Foo.class );

                String[] f = a.tags();

            }
        }
    }
}
