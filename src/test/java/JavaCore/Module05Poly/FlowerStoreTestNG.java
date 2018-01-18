package JavaCore.Module05Poly;

import JavaCore.Module05Poly.Garden.Chamomile;
import JavaCore.Module05Poly.Garden.Rose;
import JavaCore.Module05Poly.Garden.Tulip;
import JavaCore.Module05Poly.Interface.Flower;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * http://testng.org/doc/documentation-main.html#annotations
 *
 * Assert.fail( "Not yet implemented" );
 */

public class FlowerStoreTestNG
{
    FlowerStore flowerStore;
    
    Flower[] bouquet;

    private final static String STORE_FILENAME = "bouquet.txt";
    private final static String FL = System.getProperty( "file.separator" );
    private final static String USER_DIR = System.getProperty( "user.dir" );
    private String bouquetFilePath;

    @BeforeClass(groups = {"first","second","third"})
    public void setUp()
    {
        // code that will be invoked when this test is instantiated

        flowerStore = new FlowerStore();
    }
    
    @BeforeClass(groups = {"third"})
    public void setUpЕршкв()
    {
        flowerStore.sellSequence( 2, 4, 7 );
        FlowerSaver.save( flowerStore.getFlowers() );

        bouquetFilePath = getBasePath() + STORE_FILENAME;
    }

    private String getBasePath()
    {
        return USER_DIR + FL + "src" + FL + "main" + FL + "resources" + FL + "JavaCore" +
                FL + "Module05Poly" + FL;
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

    @Test(description = "loader testing", groups = {"third"})
    public void flowerLoaderTest()
    {
        Flower[] flowersRestored = FlowerLoader.load( bouquetFilePath, "sequential" );

        Assert.assertEquals( flowersRestored[5].getClass().getSimpleName(), Tulip.class.getSimpleName(), "Ожидалось, что будет [Tulip]\n" );
        Assert.assertEquals( flowersRestored[6].getClass().getSimpleName(), Chamomile.class.getSimpleName(), "Ожидалось, что будет [Chamomile]" );
        Assert.assertEquals( flowersRestored[7].getClass().getSimpleName(), Tulip.class.getSimpleName(), "Ожидалось, что будет [Tulip]\n" );
    }
}
