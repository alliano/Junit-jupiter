package com.java.unit;

import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

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

    @DisplayName(value = "test_repetision_info")
    @RepeatedTest(value = 10, name = "{displayName}")
    // parameter pada method ini akan di inject oleh class RepetitionInfoParameterResolver 
    // mekanisme proses injec nya sama dengan saat kita membuat RandomTestParameterResolver
    public void testRepetisionInfo(RepetitionInfo repetitionInfo, TestInfo testInfo) {
        System.out.println(testInfo.getDisplayName()+" ke "+repetitionInfo.getCurrentRepetition()+" dari "+repetitionInfo.getTotalRepetitions());
    }

    @DisplayName(value = "Test Calculator with parameter")
    @ParameterizedTest(name = "{displayName} with data {0}")
    @ValueSource(ints = {1,2,3,4,5,6,7,8,9,10})
    public void testWithParameter(int value) {
        
        int expected = (value + value);
        
        Integer result = this.calculator.add(value, value);

        Assertions.assertEquals(expected, result);

        System.out.println("Test dentgan parameter "+ value);
    }

    // method ini akan dijadikan parameter pada annotasi @MethodSource()
    public static List<Integer> parameterTestSource() {
        return List.of(1,2,3,4,5,6,7,8,9,10);
    }

    @DisplayName(value = "testMethodSource")
    @ParameterizedTest(name = "{displayName} ke {0}")
    // pada parameter @MethodSource() disni kita isikan nama method yang menjadi sumeber data yang kita
    // ingin jadikan sebagai parameter pada method unit test, dalam konteks ini method parameterTestSource()
    // yang dijadikan sebagai parameter pada annotasi @MethodSource()
    // dan nilai return dari method parameterTestSource() akan di iterasi dan tiap tiap elemen array nya
    // akan dijadikan parameter pada method unit testMethodSource() satu per satu.
    @MethodSource(value = {"parameterTestSource"})
    public void testMethodSource(int value) {

       int expected = (value / value);

       Integer result = this.calculator.divide(value, value);

       Assertions.assertEquals(expected, result);

       System.out.println("test dengan parameter "+value);
    }

}
