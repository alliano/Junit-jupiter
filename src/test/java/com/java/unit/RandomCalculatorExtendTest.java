package com.java.unit;

import java.util.Random;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

/**
 * Kelas ini akan memwarisi atau memiliki apapun itu property ataupun method ataupun annotation
 * yang ada pada parenc class nya, yaitu ParentCalculatorTest
 */
public class RandomCalculatorExtendTest extends ParentCalculatorTest {
    
    @Test
    public void testAddCalculator(Random random, TestInfo testInfo) {
        
        int bilanganPertama = random.nextInt();

        int bilanganKedua = random.nextInt();

        int expected = (bilanganPertama+bilanganKedua);

        Integer result = this.calculator.add(bilanganPertama, bilanganKedua);

        Assertions.assertEquals(expected, result);

        System.out.println(testInfo.getTestClass().get());
    }

    @AfterAll
    public void tearDown() {
        System.out.println("After All");
    }


    @DisplayName(value = "Test Repetitions")
    // parameter value adalah jumlah repetisi yang kita mau inginkan
    @RepeatedTest(value = 10, name = "{displayName} ke {currentRepetition} dari {totalRepetitions}")
    public void repeatedTest() {
        System.out.println("Repetition Test");
    }
}
