package JavaCore.Module06;

import JavaCore.Module05Poly.Garden.Chamomile;
import JavaCore.Module05Poly.Garden.GardenFlower;
import JavaCore.Module05Poly.Garden.Rose;
import JavaCore.Module05Poly.Garden.Tulip;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class MyQueueTest
{
    MyQueue<GardenFlower> queue;

    Rose expensiveRose;

    int expensiveRosePrice = 240;

    @BeforeMethod
    private void fillStack()
    {
        queue = new MyQueue<>();

        expensiveRose = new Rose( expensiveRosePrice );

        queue.add( new Chamomile(  ) );
        queue.add( new Rose(  ) );
        queue.add( new Tulip(  ) );
    }

    @Test
    public void addTest()
    {
        queue.clear();
        queue.add( expensiveRose );

        Assert.assertNotNull( queue.peek(), "should not be not NULL" );
        Assert.assertEquals( queue.peek().getPrice(), expensiveRose.getPrice(), "price should be " + expensiveRosePrice );
    }

    @Test
    public void clearTest()
    {
        Assert.assertEquals(queue.size(),3,"should be 3");

        queue.clear();

        Assert.assertEquals(queue.size(),0,"should be 0");
    }


    @Test
    public void sizeTest()
    {
        Assert.assertEquals(queue.size(),3,"should be 3");
    }

    @Test
    public void removeTest()
    {
        queue.remove( 1 );
        queue.remove(0);
        Assert.assertEquals( queue.peek().getClass().getSimpleName(), "Tulip", "should be Tulip" );
    }

    @Test
    public void peekTest()
    {
        GardenFlower flower = queue.peek();

        Assert.assertEquals( flower instanceof Chamomile, true, "should be Chamomile" );
    }


    @Test
    public void pollTest()
    {
        GardenFlower flower = queue.poll();

        Assert.assertEquals( flower instanceof Chamomile, true, "should be Chamomile" );

        flower = queue.peek();

        Assert.assertEquals( flower instanceof Rose, true, "should be Rose" );

        queue.clear();
        queue.add( new Rose( 120 ) );
        queue.add( new Rose( 130 ) );
        queue.add( new Rose( 140 ) );

        queue.poll();
        queue.poll();

        Assert.assertEquals( queue.poll().getPrice(), 140,"should be 140" );
    }
}
