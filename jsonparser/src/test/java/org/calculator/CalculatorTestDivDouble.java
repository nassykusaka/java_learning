package org.calculator;
import com.epam.tat.module4.Calculator;;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;

public class CalculatorTestDivDouble extends CalculatorTestPrecondition{

    @Test(dataProvider = "divDataProvider", groups = {"smoke"})
    public void testDivDouble(double a, double b, double expected) {
        Assert.assertEquals(calc.div(a, b),expected);
    }

    @DataProvider(name="divDataProvider")
    public Object[][] divDataProvider(){
        return new Object[][]{
                {254.0, 100.0, 2.54},
                {0, 10.0, 0},
                {-10.0, 20.0, -0.5},
                {5, 2, 2.5}
        };
    }
    @Test(expectedExceptions = ArithmeticException.class, groups = {"smoke"})
    public void testDivByZero() {
        double a = 1.0;
        double b = 0.0;
        double result = calc.div(a, b);
    }
}