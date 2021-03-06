package org.calculator;
import com.epam.tat.module4.Calculator;;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

public class CalculatorTestSum extends CalculatorTestPrecondition{

    @Test(dataProvider = "sumDataProvider", groups = {"main"})
    public void testSumLong(long a, long b, long expected) {
        Assert.assertEquals(calc.sum(a, b),expected);
    }

    @DataProvider(name="sumDataProvider")
    public Object[][] sumDataProvider(){
        return new Object[][]{
                {100, 100, 200},
                {0, 100, 100},
                {-100, 200, 100},
        };
    }
}
