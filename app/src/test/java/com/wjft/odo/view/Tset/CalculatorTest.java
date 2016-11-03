package com.wjft.odo.view.Tset;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2016/11/2.
 */
public class CalculatorTest {

    @Test
    public void testAdd() throws Exception {
        Calculator calculator = new Calculator();
        calculator.add(1,1);
        assertEquals(5, calculator.add(3, 3), 1);//预期结果    运行结果     误差值
    }
}