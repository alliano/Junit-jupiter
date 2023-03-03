package com.java.unit;

import java.util.Random;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.Extensions;

// disni kita registrasikan class RandomParameterResolver.class yang kita buat tadi
@Extensions(value = {
    @ExtendWith(value = RandomParameterResolver.class)
})
public class RandomCalculatorTest {
    
    private Calculator calculator = new Calculator();

    @Test // disni proses inject akan dilakukan oleh class RandomParameterResolver.class yang mana
    // class tersebuat adalah implementasi dari ParameterResolver
    public void testRandomAdd(Random random) {

        int bilanganPertama = random.nextInt();

        int bilanganKedua = random.nextInt();

        Integer result = this.calculator.add(bilanganPertama, bilanganKedua);

        int expected = (bilanganPertama+bilanganKedua);

        Assertions.assertEquals(expected, result);
    }
}
