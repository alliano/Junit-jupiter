package com.java.unit;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

// agar class unit test ini dieksekusi secara parallel kita harus menambahakan 
// annotasi @Execution(value = ExecutionMode)
@Execution(value = ExecutionMode.CONCURRENT)
public class SlowTest {

    @Test
    /**
     * annotasi @Timeout() memiliki 3 paramter
     * value = int => digunakan untuk lama waktu yang kita mau
     * unit = TimeUnit => digunakan untuk memilih satuan waktu yang kita mau
     * misalnya jikalau value = 4, unit = TimeUnit.SECOND maka artinya 4 detik
     * jikalau method ini eksekusinya lebih dari 4 detik maka method unti test ini akan gagal
     * @throws InterruptedException
     */
    @Timeout(value = 4, unit = TimeUnit.SECONDS)
    public void testSlow1() throws InterruptedException {

        Thread.sleep(2_000);
    }

    @Test
    @Timeout(value = 4, unit = TimeUnit.SECONDS)
    public void testSlow2() throws InterruptedException {

        Thread.sleep(2_000);
    }
    
    @Test
    @Timeout(value = 4, unit = TimeUnit.SECONDS)
    public void testSlow3() throws InterruptedException {

        Thread.sleep(2_000);
    }
}
