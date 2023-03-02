package com.java.unit;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.opentest4j.TestAbortedException;


@DisplayName(value = "to test a Calculator class")
public class CalculatorTest {

    private final Calculator calculator = new Calculator();


    @BeforeAll
    public static void beforeAll() {
        System.out.println("Before All");
    }

    @AfterAll
    public static void afterAll() {
        System.out.println("After All");
    }

    @BeforeEach
    public void setUp() {
        System.out.println("Before Each");
    }

    @AfterEach
    public void tireDown() {
        System.out.println("After Each");
    }

    @Test
    public void testAddSuccess(){
      
        Integer result = this.calculator.add(10, 10);

        // menggunakan bantuan Class Assertions untuk membantu pengecekan, apakah variabel result nilai nya
        // sesuai dengan expetasi kita (20) atau tidak
        Assertions.assertEquals(20, result); 
    }

    @Test
    public void testDivideSuccess() {

        Integer result = calculator.divide(10, 10);

        // expetasi kita disini 10 : 10 itu nga null
        Assertions.assertNotNull(result);
        // expetasi kita disini 10 : 10 itu hasil nya 1
        Assertions.assertEquals(1, result);
    }

    @DisplayName(value = "to test a exception that throw by method decide(integer, integer)")
    @Test
    public void testDivideFail() {
        // expetasi ktia kalai ini calculator.devide(10, 0); harus melemparkan sebuah exception yang mana exception tersebuat adalah IllegalArgumentException
        // disini harus terjadi sebuah exception jikalau tidak terjadi maka unit tes kita ini gagal
        Assertions.assertThrows(IllegalArgumentException.class, () -> {calculator.divide(10, 0);});
    }

    @Test
    @Disabled
    public void testDisabledAnAnnotation() {
        // comming soon
    }

    @Test
    public void testAbortedException() {

        var profile = System.getenv("PROFILE");
        
        if(!"DEV".equals(profile)) {
            throw new TestAbortedException("Test di batalkan karna tidak dalam mode DEVELOPMENT");
        }
        else {
            // unit test
        }
    }

    @Test
    public void testAssumption() {

        String profile = "DEV";

        // disini ekpetasi kita variabel profile itu samadengan PROD jikalau bukan PROD maka
        // class Assumption ini akan throw TestAbortedException dan unit test kita ini akan dibatalkan
        // kita bisa menggunakan ini untuk kasus yang mana kita ingin suatu kondisi terpenuhi, dan jikalau
        // kondisi tidak terpenuhi maka unit test harus di batalkan
        Assumptions.assumeTrue(profile.equals("PROD"));
    }



}
