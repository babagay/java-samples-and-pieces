package JavaCore.Module06;

import JavaCore.Module05Poly.Garden.Chamomile;
import JavaCore.Module05Poly.Garden.GardenFlower;
import JavaCore.Module05Poly.Garden.Rose;
import JavaCore.Module05Poly.Garden.Tulip;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class MyHashMapTest
{
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
    }

    @Test
    public void putTest()
    {
        Assert.assertEquals(map.size(),3,"should be 3.");
        map.put( "extra Rose", expensiveRose );
        Assert.assertEquals(map.size(),4,"should be 4.");
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
        Assert.assertEquals( map.size(), 3, "should be 3" );
    }

    @Test
    public void clearTest()
    {
        Assert.assertEquals( map.size(), 3, "should be 3" );
        map.clear();
        Assert.assertEquals( map.size(), 0, "should be 0" );
    }

    @Test
    public void removeTest()
    {
        Assert.assertNotNull( map.get( "Chamomile" ) );
        map.remove( "Chamomile" );
        Assert.assertNull( map.get( "Chamomile" ), "should be Null" );
    }
}
