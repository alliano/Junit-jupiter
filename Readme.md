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
refrence : https://junit.org/junit5/dosc/current/api/org.junit.jupiter.api/org/junit/jupiter/api/Assertions.html