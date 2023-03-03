package com.java.unit;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(value = Lifecycle.PER_CLASS)
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
public class LifecycleTest {

    /**
     * jika kita menggunakan MethodOrderer.OrderAnnotation.class
     * ini artinya pembuatan obejek untuk unit test nya hanya 1x dan utnk method unit test selanjutnya
     * akan menggunakan objeck yang sama, gambaraya sebagai berikut :
     * 
     * LifecycleTest lifeCycle = new LifecycleTest();
     * lifeCycle.test1();
     * lifeCycle.test2();
     * lifeCycle.test3();
     * lifeCycle.test4();
     * 
     * jikalau kita menggunakan Lifecycle.PER_METHOD itu tiap method unit test itu akan membuat instance
     * atau objek baru, gambaranya sebagai berikut : 
     * 
     * Lifecycele lifeCycl1 = new LifeCycle();
     * lifeCycle1.test1();
     * 
     * Lifecycele lifeCycl2 = new LifeCycle();
     * lifeCycle2.test2();
     * 
     * Lifecycele lifeCycl3 = new LifeCycle();
     * lifeCycle3.test3();
     * 
     * 
     * Lifecycele lifeCycl4 = new LifeCycle();
     * lifeCycle4.test4();
     */


     // disinini karna kita menggunakan LifeCycele.PER_CLASS maka nilai dari property 
     // counter akan selalu di tambhkan tiap method test dijalankan
    private int counter = 0;

    @Test @Order(value = 4)
    public void test1() {
        this.counter++;
        System.out.println("nilai counter : "+this.counter);
    }

    @Test @Order(value = 2)
    public void test2() {
        this.counter++;
        System.out.println("nilai counter :  "+this.counter);
    }
    
    @Test @Order(value = 1)
    public void test3() {
        this.counter++;
        System.out.println("nilai counter :  "+this.counter);
    }

    @Test @Order(value = 3)
    public void test4() {
        this.counter++;
        System.out.println("nilai counter :  "+this.counter);
    }
}
