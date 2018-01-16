package JavaCore.Module05Poly;

import JavaCore.Module05Poly.Garden.Chamomile;
import JavaCore.Module05Poly.Garden.Rose;
import JavaCore.Module05Poly.Garden.Tulip;
import JavaCore.Module05Poly.Interface.Flower;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.URL;

/**
 * http://testng.org/doc/documentation-main.html#annotations
 *
 * Assert.fail( "Not yet implemented" );
 */

public class FlowerStoreTestNG
{
    FlowerStore flowerStore;
    
    Flower[] bouquet;

    @BeforeClass(groups = {"first","second","third"})
    public void setUp()
    {
        // code that will be invoked when this test is instantiated

        flowerStore = new FlowerStore();
    }
    
    @BeforeClass(groups = {"third"})
    public void setUpSecond ()
    {
//            flowerStore.sellSequence( 2, 4, 7 );
//            FlowerSaver.save( flowerStore.getFlowers() );
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
        flowerStore = new FlowerStore();
        flowerStore.sellSequence( 1, 2, 3 );
        Flower[] flowers = flowerStore.getFlowers();

        Assert.assertEquals( flowers[0].getClass().getSimpleName(), Rose.class.getSimpleName(), "Ожидалось, что будет [Rose]" );
        Assert.assertEquals( flowers[3].getClass().getSimpleName(), Chamomile.class.getSimpleName(), "Ожидалось, что будет [Chamomile]" );
        Assert.assertEquals( flowers[5].getClass().getSimpleName(), Tulip.class.getSimpleName(), "Ожидалось, что будет [Tulip]\n" );

        Assert.assertEquals( flowerStore.getWallet(), 375, "Ожидаемая сума: 375\n" );
    }

    @Test(description = "saver testing", groups = {"third"})
    public void saverLoaderTest()
    {
        flowerStore.sellSequence( 1, 2, 3 );
        FlowerSaver.save( flowerStore.getFlowers() );
    }


    // FIXME
    @Test(description = "loader testing", groups = {"third"})
    public void flowerLoaderTest()
    {
        // [!] этот файл готов записаться на диск, но висит в памяти, ...
        flowerStore.sellSequence( 2, 4, 7 );
        FlowerSaver.save( flowerStore.getFlowers() );

        // [!] поэтому здесь поднимается старая версия файла
        // либо отваливается сразу, если её нет
        URL resource = this.getClass().getResource( "bouquet.txt" );
        Flower[] flowersRestored = FlowerLoader.load( resource.getPath(), "sequential" );

        Assert.assertEquals( flowersRestored[5].getClass().getSimpleName(), Tulip.class.getSimpleName(), "Ожидалось, что будет [Tulip]\n" );
        Assert.assertEquals( flowersRestored[6].getClass().getSimpleName(), Chamomile.class.getSimpleName(), "Ожидалось, что будет [Chamomile]" );
        Assert.assertEquals( flowersRestored[7].getClass().getSimpleName(), Tulip.class.getSimpleName(), "Ожидалось, что будет [Tulip]\n" );
    }
}
