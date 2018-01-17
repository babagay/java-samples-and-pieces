package JavaCore.Module06;

import JavaCore.Module05Poly.FlowerSaver;
import JavaCore.Module05Poly.Garden.Rose;
import JavaCore.Module05Poly.Interface.Flower;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class App
{
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException
    {
        Flower rose = new Rose();

        annotationProcess();

        MyArrayList<Flower> bouquet = new MyArrayList<>(  );

        bouquet.add( rose );

        // Flower[] flowers = bouquet.toArray();
        // FlowerSaver.save( flowers );


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
