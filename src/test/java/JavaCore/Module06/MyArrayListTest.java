package JavaCore.Module06;

import JavaCore.Module05Poly.Garden.Chamomile;
import JavaCore.Module05Poly.Garden.Rose;
import JavaCore.Module05Poly.Garden.Tulip;
import JavaCore.Module05Poly.Interface.Flower;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Iterator;

public class MyArrayListTest
{
    private MyArrayList bouquet;

    @BeforeClass
    private void setUpMock()
    {
        bouquet = new MyArrayList<Flower>( 2 );
    }

    @BeforeMethod
    private void fillBouquet()
    {
        setUpMock();

        // Add 13 elements
        bouquet.add( new Rose() );
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
    }

    @DataProvider(name = "roseBouquet")
    private Iterator<Object[]> foo()
    {
        Object[] arr = bouquet.toArray();

        return new Iterator<Object[]>()
        {
            @Override
            public boolean hasNext()
            {
                return bouquet.iterator().hasNext();
            }

            @Override
            public Object[] next()
            {
                if ( hasNext() ){
                    Object[] obj[] = new Object[1][bouquet.size()];
                    obj[0] = arr;
                }

                return null;
            }
        };
    }

    @Test(testName = "add() method test", dataProvider = "")
    public void addTest()
    {
        Assert.assertEquals( bouquet.size(), 13, "Collection size should be 13 items" );

        bouquet.add( new Tulip() );

        Assert.assertEquals( bouquet.size(), 14, "Collection size should be 14 items" );
    }

    @Test
    public void getTest()
    {
        boolean isChamomile = bouquet.get( 8 ) instanceof Chamomile;

        Assert.assertEquals( isChamomile, true, "should be a Chamomile" );
    }

    @Test
    public void removeTest()
    {
        int size = bouquet.size();

        bouquet.remove( 8 );

        boolean isTulip = bouquet.get( 8 ) instanceof Tulip;

        Assert.assertEquals( isTulip, true, "should be a Tulip" );
        Assert.assertEquals( bouquet.size(), size - 1, "should be for 1 smaller than " + size );
    }

    @Test
    public void clearTest()
    {
        bouquet.clear();

        Assert.assertEquals(bouquet.size(), 0, "should be 0");
    }

    @Test
    public void sizeTest()
    {
        int size = bouquet.size();

        bouquet.add( new Tulip() );
        bouquet.add( new Tulip() );
        bouquet.add( new Tulip() );

        Assert.assertEquals( bouquet.size(), size + 3, "should be for 3 greater than " + size);
    }

    // fixme
//    @Test(dataProvider = "roseBouquet")
//    public void barTest(Flower flower)
//    {
//        System.out.println(flower);
//    }
}
