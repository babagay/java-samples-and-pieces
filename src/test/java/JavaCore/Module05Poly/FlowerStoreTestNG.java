package JavaCore.Module05Poly;

import JavaCore.Module05Poly.Garden.Chamomile;
import JavaCore.Module05Poly.Garden.Rose;
import JavaCore.Module05Poly.Garden.Tulip;
import JavaCore.Module05Poly.Interface.Flower;
import org.testng.Assert;
import org.testng.annotations.*;

import java.net.URL;

/**
 * http://testng.org/doc/documentation-main.html#annotations
 *
 * Assert.fail( "Not yet implemented" );
 */

public class FlowerStoreTestNG
{
    FlowerStore flowerStore;

    @BeforeClass(groups = {"second"})
    public void setUp()
    {
        // code that will be invoked when this test is instantiated

        flowerStore = new FlowerStore();
    }

    /**
     * Вызывается перед каждым тестом
     */
    @BeforeMethod
    void foo(){
        System.out.println("BeforeMethod");
    }

    @Test(description = "sell method testing", groups = {"first"})
    public void sellTest()
    {
        flowerStore.sell( 3,2,1 );
        Flower[] flowers = flowerStore.getFlowers();

        Assert.assertEquals( flowers[5].getClass().getSimpleName(), Tulip.class.getSimpleName(), "Ожидалось, что будет [Tulip]" );
    }

    @Test(description = "sellSequence method testing", groups = {"second"})
    public void sellSequenceTest()
    {
        flowerStore.sellSequence( 1, 2, 3 );
        Flower[] flowers = flowerStore.getFlowers();

        Assert.assertEquals( flowers[0].getClass().getSimpleName(), Rose.class.getSimpleName(), "Ожидалось, что будет [Rose]" );
        Assert.assertEquals( flowers[3].getClass().getSimpleName(), Chamomile.class.getSimpleName(), "Ожидалось, что будет [Chamomile]" );
        Assert.assertEquals( flowers[5].getClass().getSimpleName(), Tulip.class.getSimpleName(), "Ожидалось, что будет [Tulip]\n" );

        Assert.assertEquals( flowerStore.getWallet(), 375, "Ожидаемая сума: 375\n" );
    }

    @Test(description = "loader testing", groups = {"second"})
    public void flowerLoaderTest()
    {
        URL resource = this.getClass().getResource( "bouquet.txt" );
        Flower[] flowers = FlowerLoader.load( resource.getPath(), "sequential" );

        Assert.assertEquals( flowers[5].getClass().getSimpleName(), Tulip.class.getSimpleName(), "Ожидалось, что будет [Tulip]\n" );
        Assert.assertEquals( flowers[6].getClass().getSimpleName(), Chamomile.class.getSimpleName(), "Ожидалось, что будет [Chamomile]" );
        Assert.assertEquals( flowers[7].getClass().getSimpleName(), Tulip.class.getSimpleName(), "Ожидалось, что будет [Tulip]\n" );
    }
}
