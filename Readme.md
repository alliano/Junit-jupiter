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
Yang perlu kita lakukan adalah membuat class turunan dari Interface DisplayNameGenerator, lalu menambahkan annotation @DisplayNameGeneration ke test Class nya.

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

# Sebelum dan Sesudah Unit Test

Kadang kita ingin menjalankan kode yang sama sebelum dan sesudah eksekusi unit test
Hal ini sebenarnya bisa dilakukan secara manual di method test @Test nya, namun hal ini akan membuat banyak kode duplikat dan redundan.
JUnit memiliki annotation @BeforeEach dan @AfterEach.
@BeforeEach diguakan untuk menandai method yang akan di eksekusi sebelum unit test dijalankan.
@AfterEach digunakan untuk menandai mehthod yang akan di eksekusi setelah unit test dijalankan.
Perlu di inigat bahwa ini akan selalu di eksekusi setipa kali sebuah mehtod @Test, bukan sekali untuk class test saja, misal dalam classTest kita memiliki 10 method Unit test maka Mehtod yang kita annotasi sebagai @BeforEach atau @AfterEach ini akan di eksekusi sebanyak 10 kali.

example : 
``` java
    @BeforeEach
    public void setUp() {
        System.out.println("Before Each");
    }

    @AfterEach
    public void tireDown() {
        System.out.println("After Each");
    }
```

# Sebelum dan Sesudah Unit Test (eksekusi hanya 1x)

@BeforeEach dan @AfterEach akan dieksekusi setiap kali method @Test di jalankan.
Namun kadang kita ingin melakukan sesuatu sebelum unit test berjalan, atau setelah semua unit test selesai di jalankan.
Ini bisa dilakukan dengan menggunakan annotation @BeforeAll dan @AfterAll.
@BeforeAll dan @AfterAll hanya akan 1x di eksekusi saat Class unit test di jalankan berbeda dengan annotasi @BeforeEach dan @AfterEach
Namun perlu di ingat @BeforeAll dan @AfterAll hanya bisa di pakai di method static.

example :
``` java
    @BeforeAll
    public static void beforeAll() {
        System.out.println("Before All");
    }

    @AfterAll
    public static void afterAll() {
        System.out.println("After All");
    }
```

# Membatalkan unit test

Kadang kita ingin membatalkan Unit test pada keadaan tertentu terjadi.
Untuk membatalkanya, kita bisa menggunakan Exception TestAbortedException.
Jikalau Junit mendapatkan exception tersebut maka unit test yang throw Exception tersebuat akan di batalkan.

example :
``` java
    @Test
    public void testAbortedException() {

        var profile = System.getenv("DEV");
        
        if(!"DEV".equals(profile)) {
            throw new TestAbortedException("Test di batalkan karna tidak dalam mode DEVELOPMENT");
        }
        else {
            // unit test
        }
    }
```

# Assumption

Sebelumnya kita sudah tau jika ingin membatalkan unit test, kita bisa menggunakan exception TestAbortedException.
Namun ada cara yang lebih tepat, yaitu dengan menggunakan Assumption.
Penggunaan Assumption mirip dengan Assertion, jika nilai tidak sama maka method Assuption akan throw TestAbortedException, sehingga secara otomatis akan membatalkan Unit test yang throw exception tersebut.

example : 
``` java
    @Test
    public void testAssumption() {

        String profile = "DEV";

        // disini ekpetasi kita variabel profile itu samadengan PROD jikalau bukan PROD maka
        // class Assumption ini akan throw TestAbortedException dan unit test kita ini akan dibatalkan
        // kita bisa menggunakan ini untuk kasus yang mana kita ingin suatu kondisi terpenuhi, dan jikalau
        // kondisi tidak terpenuhi maka unit test harus di batalkan
        Assumptions.assumeTrue(profile.equals("PROD"));
    }
```

# Test Berdasarkan Kondisi

Sebenarnya kita bisa menggunakan Assumption untuk menjalankan unit test berdasarkan kondisi tertentu.
Namun JUnit menyediakan fitur yang lebih mudah untuk pengecekan beberapa kondisi seperti, sistem operasi, versi java, System property, dan lain lain.
Ini lebih mudah dibandingkan menggunakan Assertions.

# Cek Berdasarkan System Operasi

Untuk Kondisi system operasi, kita bisa menggunakan beberapa annotation.
@EnableOnOs(value = OS) digunakan untuk penanda bahwa unit test boleh berjalan pada system operasi yang sudah di tentukan.
@DisableOnOs(value = OS) digunakan utntuk penanda bahwa unit test tidak boleh berjalan di system operasi yang ditentukan.

example : 
``` java
    @Test
    @EnabledOnOs(value = {OS.LINUX})
    public void testForLinux() {
        System.out.println("Unit tes ini untuk Linux");
    }

    @Test
    @DisabledOnOs(value = {OS.LINUX})
    public void testDisableForLinux() {
        System.out.println("Unit test ini bukan untuk Linux");
    }
```

# Enable Disable Java version

@EnableOnJre(value = JRE) digunakan untuk penanda bahwa unit test hanya boleh berjalan pada versi java tertentu.

example : 

``` java
    @Test
    @EnabledOnJre(value = {JRE.JAVA_18})
    public void testEnableOnJava18() {
        System.out.println("test untuk java versi 18");
    }
```

@DisableOnJre(value = JRE) digunakan untuk menandai bahwa unit tes tidak boleh berjalan pada versi java tertentu

example : 
``` java
    @Test
    @DisabledOnJre(value = {JRE.JAVA_18}, disabledReason = "because this unit test not allowed in java 18")
    public void testDisableOnJava18() {
        System.out.println("test disable untuk java 18");
    }
```

@EnableForJreRange(min = JRE, max = JRE) digunakan untuk penanda bahwa unit test boleh jalan pada range versi java tertentu.

example : 
``` java
    @Test
    @EnabledForJreRange(min = JRE.JAVA_17, max = JRE.JAVA_18)
    public void testEnableJavaFromRange11to17() {
        System.out.println("test untuk java versi 11 - 17");
    }
```

@DisableForJreRange(min = JRE, max = JRE) digunakan untuk penanda bahwa unit test ini tidak boleh jalan pada range versi java tertentu.

example : 
``` java
    @Test
    @DisabledForJreRange(min = JRE.JAVA_11, max = JRE.JAVA_17)
    public void testDisableJavaFromRange11to17() {
        System.out.println("test untuk java versi 11 - 17");
    }
```

# Kondisi System Property

Untuk kondisi dari system property, kita bisa menggunakan beberapa annotation.
System propery dapat kita lihat dengan cara sebagai berikut :
``` java
    @Test
    public void testSystemProperies() {
        // ini akan menampilkan semua properties yang dimiliki oleh jdk kita
        System.getProperties().forEach( (key, value) -> {
            System.out.println(key+" : "+value);
        } );
    }
```
@EnabledIfSystemProperty() untuk penanda bahwa unit test boleh jalan jika system property sesuai dengan yang di tentukan.

example : 
``` java
    // unit test ini akan dijalankan jika major dari java kita atau java.class.version kita adalah 62.0
    @Test @EnabledIfSystemProperty(named = "java.class.version", matches = "62.0")
    public void testEnableIfSystemProperty(){
        System.out.println("testEnableIfSystemProperty berjalan");
    }
```
@DisabledIfSystemProperty() untuk penanda bahwa unit test tidak boleh berjalan jika system property sesuai dengan yang ditentukan.

example : 
``` java
    // Test ini tidak akan dijalankan jika user.language nya itu en
    @Test @DisabledIfSystemProperty(named = "user.language", matches = "en")
    public void testDisableIfSystemProperty(){
        System.out.println("testDisableIfSystemProperty");
    }
```
Jika kondisinya lebih dar 1 kita bisa menggunakan @EnabledIfSystemProperties() atau @DisabledIfSystemProperties().

example :
``` java
    // test ini akan berjalan jika 2 kondisi yang kita buat (@EnabledIfSystemProperty) ini terpenuhi
    @Test @EnabledIfSystemProperties(value = {@EnabledIfSystemProperty(named = "native.encoding", matches = "UTF-8"), @EnabledIfSystemProperty(named = "java.vm.specification.vendor", matches = "Oracle Corporation")})
    public void testEnabledIfSystemPropertys() {
        System.out.println("testEnabledIfSystemPropertys berjalan");
    }

    // test ini tidak akan berjalan jika 2 kondisi annotasi (@DisabledIfSystemProperty) yang kita buat terpenuhi
    @Test @DisabledIfSystemProperties(value = {@DisabledIfSystemProperty(named = "java.vendor", matches = "N/A"), @DisabledIfSystemProperty(named = "os.arch", matches = "amd64")})
    public void testDisbleIfSystemProperties() {
        System.out.println("testDisbleIfSystemProperties");
    }
```