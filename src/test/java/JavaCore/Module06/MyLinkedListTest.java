package JavaCore.Module06;

import JavaCore.Module05Poly.Garden.Chamomile;
import JavaCore.Module05Poly.Garden.GardenFlower;
import JavaCore.Module05Poly.Garden.Rose;
import JavaCore.Module05Poly.Garden.Tulip;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class MyLinkedListTest
{
    MyLinkedList<GardenFlower> list;

    Rose expensiveRose;

    int expensiveRosePrice = 270;

    @BeforeMethod
    private void fillStack()
    {
        list = new MyLinkedList<>();

        expensiveRose = new Rose( expensiveRosePrice );

        list.add( new Rose(  ) );
        list.add( new Chamomile(  ) );
        list.add( new Tulip(  ) );
    }

    @Test
    public void getTest()
    {
        GardenFlower flower = list.get( 1 );

        Assert.assertEquals( flower instanceof Chamomile, true, "should be Chamomile" );
    }


    @Test
    public void sizeTest()
    {
        Assert.assertEquals(list.size(),3,"should be 3");
    }

    @Test
    public void removeTest()
    {
        GardenFlower flower = list.get( 1 );

        Assert.assertEquals( flower instanceof Chamomile, true, "should be Chamomile" );

        list.remove( 1 );

        Assert.assertEquals( flower instanceof Tulip, true, "should be Tulip" );
    }


    @Test
    public void clearTest()
    {
        Assert.assertEquals(list.size(),3,"should be 3");

        list.clear();

        Assert.assertEquals(list.size(),0,"should be 0");
    }

    @Test
    public void addTest()
    {
        Assert.assertEquals(list.size(),3,"should be 3");

        list.add( expensiveRose );

        Assert.assertEquals(list.size(),4,"should be 4");

        GardenFlower flower = list.get( 3 );

        Assert.assertEquals( flower instanceof Rose, true, "should be Rose" );

        Assert.assertEquals( flower.getPrice(), expensiveRosePrice, "should be " + expensiveRosePrice );
    }

}
