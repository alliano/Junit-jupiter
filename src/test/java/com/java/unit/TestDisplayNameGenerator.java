package com.java.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(value = SimpleDisplayNameGenerator.class)
public class TestDisplayNameGenerator {

    private final Calculator calculator = new Calculator();

    @Test
    public void testDicideSuccess() {

        Integer result = this.calculator.divide(10, 10);

        Assertions.assertNotNull(result);

        Assertions.assertEquals(1, result);
    }
}
