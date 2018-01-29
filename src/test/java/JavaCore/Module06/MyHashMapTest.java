package JavaCore.Module06;

import JavaCore.Module05Poly.Garden.Chamomile;
import JavaCore.Module05Poly.Garden.GardenFlower;
import JavaCore.Module05Poly.Garden.Rose;
import JavaCore.Module05Poly.Garden.Tulip;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Можно добавить тесты на проверку добавления/удаления из конекретных корзин и
 * когда один элемент в корзине, либо несколько
 */
public class MyHashMapTest
{
    private static final int INIT_BOUQUET_SIZE = 6;

    MyHashMap<String,GardenFlower> map;

    Rose expensiveRose;

    int expensiveRosePrice = 220;

    @BeforeMethod
    private void fillStack()
    {
        map = new MyHashMap<>();

        expensiveRose = new Rose( expensiveRosePrice );

        map.put( "Rose", new Rose(  ) );
        map.put( "Tulip", new Tulip(  ) );
        map.put( "Chamomile", new Chamomile(  ) );

        map.put( "Chamomile2", new Chamomile(  ) );
        map.put( "Chamomile3", new Chamomile(  ) );
        map.put( "Chamomile4", new Chamomile(  ) );
    }

    @Test
    public void putTest()
    {
        Assert.assertEquals(map.size(),INIT_BOUQUET_SIZE,"should be " + INIT_BOUQUET_SIZE);
        map.put( "extra Rose", expensiveRose );
        Assert.assertEquals(map.size(),INIT_BOUQUET_SIZE + 1,"should be " + INIT_BOUQUET_SIZE + 1);
        map.put( "extra Rose", expensiveRose );
        map.put( "extra Rose", expensiveRose );
        map.put( "extra Rose", expensiveRose );
        Assert.assertEquals(map.size(),INIT_BOUQUET_SIZE + 1,"should be " + INIT_BOUQUET_SIZE + 1);
    }

    @Test
    public void getTest()
    {
        map.put( "extra Rose", expensiveRose );
        GardenFlower flower = map.get( "extra Rose" );
        Assert.assertNotNull( flower );
        Assert.assertEquals( flower.getPrice(), expensiveRose.getPrice(), "should be " + expensiveRosePrice );
    }

    @Test
    public void sizeTest()
    {
        Assert.assertEquals( map.size(), INIT_BOUQUET_SIZE, "should be " + INIT_BOUQUET_SIZE );
    }

    @Test
    public void clearTest()
    {
        Assert.assertEquals( map.size(), INIT_BOUQUET_SIZE, "should be " + INIT_BOUQUET_SIZE );
        map.clear();
        Assert.assertEquals( map.size(), 0, "should be 0" );
        Assert.assertNull( map.get( "Rose" ), "should be null" );
    }

    @Test
    public void removeTest()
    {
        Assert.assertNotNull( map.get( "Chamomile" ) );
        map.remove( "Chamomile" );
        Assert.assertNull( map.get( "Chamomile" ), "should be Null" );
    }
}
