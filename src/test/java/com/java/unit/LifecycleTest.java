package com.java.unit;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(value = MethodOrderer.MethodName.class)
public class LifecycleTest {

    /**
     * disini kita menggunakan MethodOrderer.MethodName.class
     * ini akan mengeksekusi unit test berurutan berdasarkan nama method nya 
     * misal jika nama method nya test1, test2, test3 maka eksekusi unit testnya akan berurutan 
     * dari test1, test2, test3
     */

    @Test
    public void test1() {
        System.out.println("tes 1");
    }

    @Test
    public void test2() {
        System.out.println("test 2");
    }
    
    @Test
    public void test3() {
        System.out.println("test 3");
    }

    @Test
    public void test4() {
        System.out.println("test 4");
    }
}
