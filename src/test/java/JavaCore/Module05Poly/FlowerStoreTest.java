package JavaCore.Module05Poly;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.model.InitializationError;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import javax.inject.Inject;

/**
 * http://arquillian.org/arquillian-core/#core-principles
 */

@RunWith( Arquillian.class )
public class FlowerStoreTest extends Arquillian
{
    @Inject
    FlowerStore flowerStore;

    static final private String[] groups = new String[1];

    {
        groups[0] = "foo";
    }

    public FlowerStoreTest() throws InitializationError
    {
       super(FlowerStoreTest.class);

    }

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass( FlowerStore.class )
                .addAsManifestResource( EmptyAsset.INSTANCE, "beans.xml");
    }

    @BeforeMethod(enabled = true )
    private void run()
    {
        int y = 0;
//        super.run( callback, testResult );
    }

    @BeforeClass
    public void arquillianBeforeClass() throws Exception
    {
        int t = 0;
//        super.arquillianBeforeClass();
    }

    @Test
    public void sellSequenceTest()
    {

        FlowerStore d = flowerStore;

        Assert.fail("Not yet implemented");

//        flowerStore.sellSequence( 1,2,3 );
//        Flower[] flowers = flowerStore.getFlowers();
//
//        Assert.assertEquals( flowers[0].getClass().getSimpleName(), Rose.class.getSimpleName(),  "Ожидалось, что будет [Rose]" );
//        Assert.assertEquals( flowers[3].getClass().getSimpleName(), Chamomile.class.getSimpleName(),  "Ожидалось, что будет [Chamomile]" );
//        Assert.assertEquals( flowers[5].getClass().getSimpleName(), Tulip.class.getSimpleName(),  "Ожидалось, что будет [Tulip]" );
    }
}
