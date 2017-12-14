package JavaCore.Module3;

import JavaCore.AbstractTestArquillian;


import JavaCore.Module03.App;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import java.util.Map;


public class TaskTest  extends AbstractTestArquillian
{
    private JavaCore.Module03.App App;

    private int[] mockArray = {3, 3, 4, 2, 2, 2};

    private String mockArrayStr = "3 3 4 2 2 2";

    private Integer[] mockArrayBoxed;

    @BeforeClass
    public void provide()
    {
        App = new JavaCore.Module03.App();
    }


    @BeforeMethod
    public void setUp()
    {
        mockArrayBoxed = JavaCore.Module03.App.constructBoxedArrayFromIntSequence( mockArrayStr );
    }

    @Test
    public void first()
    // Взять число, которое присутсвует в массиве наибольшее число раз
    {
        Map.Entry<Integer, Long> mostOftenFoundNumberAndFrequency = JavaCore.Module03.App.constructFrequencyMapStreamedAndGetFirst( mockArrayBoxed );

        Assert.assertEquals( mostOftenFoundNumberAndFrequency.getKey(), 2, 0.0, "Ожидалось, что будет число 2" );
        Assert.assertEquals( mostOftenFoundNumberAndFrequency.getValue(), 3, 0.0, "Ожидалось, что параметра равен 3-м" );
    }
}
