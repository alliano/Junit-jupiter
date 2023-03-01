# Membuat Test

Untuk membuat Test di JUnit, kita cukup membuat class, lalu menambahkan method-method test nya
Method akan dianggap sebuah unit test jika di tambahkan annotation @Test
Source code test disimpan di bagian folder test pada project maven, bukan di Main folder
Biasanya untuk membuat class unutk test, rata-rata orang akan membuat nama class nya sama dengan nama class yang akan di test, tetapi di akhiri dengan kata Test, Misal jika nama Class yang ingin di test adalah PaymentGateway maka nama class test nya PaymentGatewayTest.

example :

disini kita punya kelas Calculator
``` java
package com.java.unit;

public class Calculator {
    
    public Integer add(Integer first, Integer second) {
        return first + second;
    }
}
```

maka class unit test nya kita beri nama CalculatorTest
``` java
public class CalculatorTest {

    private final Calculator calculator = new Calculator();

    @Test
    public void testAddSuccess(){
      
        Integer result = this.calculator.add(10, 10);
        System.out.println(result);

    }
}
```

# Assertions

Saat membuat test, kita harus memastikan bahwa unit test yang kita buat sesuai dengan ekpetasi yang kita inginkan
Jika manual, kita bisa melakukan menggunakan pengecekan if else namun itu tidak direkomendasikan
example : 
``` java
    @Test
    public void testAddSuccess(){
      
        Integer result = this.calculator.add(10, 10);

        // membuat test secara manual tampa menggunakan bantuan Class Assertions
        if(result != 20) {
            throw new RuntimeException("Test Gagal");
        }
    }
```

Junit memiliki fitur yang melakukan assertions, yaitu memastikan bahwa unit test yang kita buat sesuai dengan kondisi yang kita inginkan
example : 
``` java
    @Test
    public void testAddSuccess(){
      
        Integer result = this.calculator.add(10, 10);

        // menggunakan bantuan Class Assertions untuk membantu pengecekan, apakah variabel result nilai nya
        // sesuai dengan expetasi kita (20) atau tidak
        Assertions.assertEquals(20, result);
    }
```
Assertions di JUnit di representasikan oleh class Assertions, dan didalamnya terdapat banyak sekali method static yang kita bisa gunakan untuk pengecekan expetasi kita.

# Menggagalkan Test

Dalam pembuatan unit test terkadang kita berharap untuk menggagalkan program yang kita jalankan, kita tidak hanya ingin mengetes success nya aja, namun kita juga ingin mengetahui bagaimna jika program kita gagal.
Adakalanya kita ingin mengetes sebuah exception yang kita buat sebelumnya misalnya 
Assetions juga bisa digunakan untuk mengecek apakah sebuah exception terjadi pada program yang kita test

example :
``` java
    // ini untuk mengetes method divide dengan expetasi success 
    @Test
    public void testDivideSuccess() {

        Integer result = calculator.divide(10, 10);

        // expetasi kita disini 10 : 10 itu nga null
        Assertions.assertNotNull(result);
        // expetasi kita disini 10 : 10 itu hasil nya 1
        Assertions.assertEquals(1, result);
    }

    // ini untuk mengetes method divide denga expetasi gagal (terjadi exception)
    @Test
    public void testDivideFail() {
        // expetasi ktia kalai ini calculator.devide(10, 0); harus melemparkan sebuah exception yang mana exception tersebuat adalah IllegalArgumentException
        // disini harus terjadi sebuah exception jikalau tidak terjadi maka unit tes kita ini gagal
        Assertions.assertThrows(IllegalArgumentException.class, () -> {calculator.divide(10, 0);});
    }
```

# Mengubah Nama Test

Terkadang lumayan susah mebuat nama unit test sesuai dengan sekenario atau kasus yang merepresentasikan dari unit test yang akan kita buat.
Jika kita ingin menambahkan deskripsi untuk tiap-tiap unit test kita, kita bisa menggunakan sebuah annotation @DisplayName(value = "message or description")
Dengan annotasi @DisplayName() kita bisa menambahkan deskripsi unit test nya
@DisplayName bisa kita gunakan di level method ataupun class
exaample: 
``` java
    // ini kita pakai @DisplayName() pada level Mehod
    @DisplayName(value = "to test a exception that throw by method decide(integer, integer)")
    @Test
    public void testDivideFail() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {calculator.divide(10, 0);});
    }
```

# Menggunakan Display Name Generator

JUnit mendukung pembuatan DisplayName secara otomatis menggunakan generator
Yang perlu kita lakukan adalah membuat class turunan dari Interface DisplayNameGenerator, lalu menambahkan annotation @DisplayNameGeneration ke test Class nya .
example :
``` java
import java.lang.reflect.Method;

import org.junit.jupiter.api.DisplayNameGenerator;

public class SimpleDisplayNameGenerator implements DisplayNameGenerator {

    @Override
    public String generateDisplayNameForClass(Class<?> testClass) {
        return "Test "+testClass.getSimpleName();
    }

    @Override
    public String generateDisplayNameForNestedClass(Class<?> nestedClass) {
        throw new UnsupportedOperationException("Unimplemented method 'generateDisplayNameForNestedClass'");
    }

    @Override
    public String generateDisplayNameForMethod(Class<?> testClass, Method testMethod) {
        return "Test"+testClass.getSimpleName()+testMethod.getName();
    } 
    
}
```

``` java
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
```

# Menonaktifkan Unit Test

Kadang ada kalanya kita ingin mengnonakrifkan Unit test, misal karna terjadi error pada unitest tesesebut atau sebagainya, yang mingkin kita belum sempat memperbaikinya.
Sebenarnya cara yang paling mudah untuk menonaktifkan Unit test adalah dengan menghapus annotasi @Test pada method unit test, namun jikalau kita lakukan hal tersebut, kita tidak bisa mendeteksi kalau ada unit test yang di disabled.
Untuk menonaktifkan Unit test secara benar, kita bisa menggunakan annotasi @Disabled 

example : 
``` java

    @Test
    @Disabled
    public void testDisabledAnAnnotation() {
        // comming soon
    }


```