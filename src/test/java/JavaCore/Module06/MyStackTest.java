package JavaCore.Module06;

import JavaCore.Module05Poly.Garden.Chamomile;
import JavaCore.Module05Poly.Garden.GardenFlower;
import JavaCore.Module05Poly.Garden.Rose;
import JavaCore.Module05Poly.Garden.Tulip;
import JavaCore.Module05Poly.Interface.Flower;
import org.testng.Assert;
import org.testng.annotations.*;

public class MyStackTest
{
    MyStack<GardenFlower> stack;

    Rose expensiveRose;

    int expensiveRosePrice = 200;

    @BeforeMethod
    private void fillStack()
    {
        stack = new MyStack();

        expensiveRose = new Rose( expensiveRosePrice );

        stack.push( new Rose() );
        stack.push( new Tulip() );
        stack.push( new Chamomile() );
    }

    @Test
    public void pushTest()
    {
        stack.push( expensiveRose );

        Assert.assertNotNull( stack.peek(), "should not be NULL" );
        Assert.assertEquals( stack.peek().getPrice(), expensiveRose.getPrice(), "price should be " + expensiveRosePrice );
    }

    @Test
    public void removeTest()
    {
        stack.remove(1);
        stack.remove(0);

        Assert.assertNotNull( stack.peek(), "should not be NULL" );
        Assert.assertEquals( stack.peek().getClass().getSimpleName(), Chamomile.class.getSimpleName(), "Should be Chamomile");
    }

    @Test
    public void clearTest()
    {
        Assert.assertNotNull( stack.peek(), "should not be NULL" );

        stack.clear();

        Assert.assertEquals( stack.peek(), null, "should be NULL");
    }

    @Test
    public void sizeTest()
    {
        Assert.assertEquals(stack.size(), 3, "should be 3");
    }

    @Test
    public void peekTest()
    {
        Assert.assertNotNull( stack.peek(), "should not be NULL" );
        Assert.assertEquals( stack.peek().getClass().getSimpleName(), Chamomile.class.getSimpleName(), "Should be Chamomile");
    }

    @Test
    public void popTest()
    {
        Flower flower = stack.pop();

        Assert.assertEquals(stack.size(), 2, "should be 2");
        Assert.assertEquals( stack.peek().getClass().getSimpleName(), Tulip.class.getSimpleName(), "Should be Tulip");
    }
}
