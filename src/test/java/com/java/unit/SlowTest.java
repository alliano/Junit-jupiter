package com.java.unit;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

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
    public void testSlow() throws InterruptedException {

        Thread.sleep(10_000);
    }
}
