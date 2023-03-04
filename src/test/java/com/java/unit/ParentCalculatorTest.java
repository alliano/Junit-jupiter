package com.java.unit;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.Extensions;


/**
 * Semua class yang extend class ini maka semua annnotasi dan method unit test di class ini
 * akan dimiliki oleh class yang meng extend class ini
 */
@Extensions(value = {
    @ExtendWith(value = RandomParameterResolver.class)
})@TestInstance(value = Lifecycle.PER_CLASS)
public class ParentCalculatorTest {

    protected Calculator calculator = new Calculator();

    @BeforeAll
    public void setup() {
        System.out.println("Before All");
    }
}
