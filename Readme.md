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

# Enable & Disable berdasarkan kondisi System Property

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
    // named => key dari system properties nya, matches => value dari system properties yang kita inginkan
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

# Enable Disable Berdasarkan Environment Variable

Untuk kondisi nilai dari environment variable, kita bisa menggunakan beberapa annotations.
@EnableEnvironmentVariable() untuk penanda bahwa unit test boleh berjalan jika environment variabel nya sesuai dengan yang ditentukan.

example : 
``` java
    @Test @EnabledIfEnvironmentVariable(named = "profile", matches = "dev")
    public void testEnabledIfEnvironmentVariable(){
        System.out.println("EnabledIfEnvironmentVariable jalan");
    }
```

@DisableIfEnvironmentVariable() untuk penanda bahwa unit test tidak boleh berjalan jika environment variable sesuai dengan yang kita tentukan.

example : 
``` java
    @Test @DisabledIfEnvironmentVariable(named = "profile", matches = "dev")
    public void testDisabledIfEnvironmentVariable(){
        System.out.println("DisabledIfEnvironmentVariable");
    }
```

jika kondisinya lebih dari satu, kita bisa menggunakan @EnableEnvironmentVariables() dan @DisableIfEnvironmentVariables().

example : 
``` java
    // jika profile nya itu dev dan psql_passwad itu mangekyo pada environment variable, maka unit test ini akan dijalankan
    // simple nya jika 2 kondisi @EnabledIfEnvironmentVariable() ini terpenuhi maka akan di jalankan unit test ini
    @Test @EnabledIfEnvironmentVariables(value = {@EnabledIfEnvironmentVariable(named = "profile", matches = "dev"), @EnabledIfEnvironmentVariable(named = "psql_passwad", matches = "mangekyo")})
    public void testEnabledIfEnvironmentVariables() {
        System.out.println("testEnabledIfEnvironmentVariables jalan");
    }

    // jika profile nya itu prod dan test pada environment variable maka test ini tidak akan dijalankan
    @Test @DisabledIfEnvironmentVariables(value = {@DisabledIfEnvironmentVariable(named = "profile", matches = "prod"), @DisabledIfEnvironmentVariable(named = "profile", matches = "test")})
    public void testDisabledIfEnvironmentVariables() {
        System.out.println("testDisabledIfEnvironmentVariables");
    }
```

# Menggunakan Tag

class atau method/function dalam unit test bisa kita tambahkan tag(tanda), dengan cara mengannotasi dengan anotasi @Tag.
dengan menambahakan @Tag pada unit test, ktia bisa fleksibel ketika ingin menjalankan class atau unit test tertentu.
Jika kita menambahkan @Tag pada class unit test, secara otomatis semua method/function pada class unit test tesebut akan memiliki tag tersebut.
Jika kita inign menabahkan beberapa tag pada class atau mehtod unit test, kita bisa menggunakan annotasi @Tags.

example :
``` java

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

// annotasi @Tag ini akan diturunkan atau di wariskan ke method-metod dalam class ini
@Tag(value = "test-intregation-services")
public class IntregationTest {
    
    @Test // jadi secara tidak langsung semua method method ini memiliki annotasi @Tag
    public void testPaymentService() {
        System.out.println("test payment service");
    }

    @Test
    public void testAccountService() {
        System.out.println("test account service");
    }

    @Test
    public void testOtpservice() {
        System.out.println("test otp service");
    }
}
```

jika kita ingin tagnya itu lebih dari 1 kita bisa menggunakan annotati @Tags.

example :
``` java
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

@Tags(value = {
    @Tag(value = "test-intregation-service"),
    @Tag(value = "test-services")
})
public class IntregationTest {
    
    @Test
    public void testPaymentService() {
        System.out.println("test payment service");
    }

    @Test
    public void testAccountService() {
        System.out.println("test account service");
    }

    @Test
    public void testOtpservice() {
        System.out.println("test otp service");
    }
}
```
cara menjalankan tag nya, kita bisa menggunakan command maven, dengan command mvn test -Dgroups=nama-tag1, nama-tag2, maka class unit test yang berjalan itu hanya class unit test yaang kita sebuatkan tagnya dalam contoh kali ini unit test yang dijalankan hanya unit test yang memiliki tag nama-tag1, nama-tag2.

example :

``` bash
mvn test -Dgroups=test-services,test-intregation-service
```

# Mengubah Urutan Eksekusi Unit Test

JUnit mendukung perubahan urutan eksekusi unit test jika kita mau menggunakan annotasi @TestMethodOrder(value = MethodOrderer).
Ada beberapa class yang kita bisa gunakan untuk mengurutkan eksekusi unit test.
MethodName => eksekusi unit test akan di urutkan berdasarkan alphanumeric.
Random => ururan eksekusi unit test akan secara acak.
OrderAnnotation => urutan eksekusi unit test nya akan mengikuti annotation @Order yang ada pada tiap tiap mehtod unit test nya.

example :

menggunakan MethodOrderer.OrderAnnotation

``` java
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
public class LifecycleTest {
    
    /**
     * disini kita mengguanakan pengurutan menggunakan MethodOrderer.OrderAnnotation.class
     * urutan method ekseskuisi ini akan diururtkan berdasarkan parameter yang ada pada annotasi @Order
     */

    @Test @Order(value = 4)
    public void test1() {
        System.out.println("tes 1");
    }

    @Test @Order(value = 2)
    public void test2() {
        System.out.println("test 2");
    }
    
    @Test @Order(value = 3)
    public void test3() {
        System.out.println("test 3");
    }

    @Test @Order(value = 1)
    public void test4() {
        System.out.println("test 4");
    }
}
```

menggunakan MethodOrderer.Random.class

example :
``` java

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(value = MethodOrderer.Random.class)
public class LifecycleTest {

    /**
     * disini kita menggunakan MethodOrderer.Random.class
     * maka eksekusi unit test nya akan dilakukan secara acak
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
```

menggunakan MethodOrderer.MethodName.class

example :
``` java
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
```

# Siklus Hidup Test

Secara default siklus hidup(LifeCycle) object class itu independent permethod unit test nya, artinya object class unit test akan selalu dibuat baru permethod unit test nya, oleh karna itu method unit test tidakbisa bergantung kepada method unit test yang lainya.
Cara pembuatan object di JUnit test ditentukan oleh annotation @TestInstance(), yang mna default parameternya itu adalah Lifecycle.PER_METHOD, artinya tiap method akan dibuatkan sebuah instance atau object baru.
Jikalau kita nga mau tiap method nya itu menginstance object baru kita bisa ubah parameter dari @TestInstance() dengan Lifecycle.PER_CLASS, dengan demikian instance atau object test hanya dibuat satu kali per class, dan method unit test akan menggunakan object atau instance test yang sama.
Hal ini bisa kita manfaatkan ketika kita membuat method unit test yang tergantung pada method test lainya.

example :
``` java
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
```

# Keuntungan menggunakan Lifecycle.PER_CLASS
 
 salahsatu keuntungan menggunakan Ligfecycle.PER_CLASS adalah kita bisa menggunakan @BeforeAll dan @AfterAll tampa menggunakan method static, dan masih banyak lagi keuntungan nya.

# Nested Test

Saat membuat unit test, adabaiknya ukuran test class nya tidak terlalu besar, karna jikalau terlau besar itu akan susah di baca dan dimengerti.
Jika test class sudah semakin besar, ada baiknya kita pecah menjadi beberapa test class, lalu kita grouping sesuai dengan jenis method test nya.
Junit mendukung pembuatan nested class test (class unit test didalam class unit test), jadi kita bisa memecah class test, tampa harus membuat class di file yang berbeda, kita bisa cukup menggunkan inner class.
Untuk memberitau bahwa inner class tersebuat adalah class unit test kita bisa meng annotasi inner class tersebut dengan ananotasi @Nested

example :
``` java
import java.util.LinkedList;
import java.util.Queue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName(value = "A Queue")
public class QueueTest {
    
    private Queue<String> queue;

    // ini adalah Nested class atau inner class annotasi 
    // @Nested untuk memberitahu bahwa class ini adalah class unit test
    @Nested @DisplayName(value = "When new")
    public class WhenNew {

        @BeforeEach
        public void setup() {
            queue = new LinkedList<String>();
        }

        @Test @DisplayName(value = "When offer 1, the size must be 1")
        public void whenOfferQueue() {
            queue.offer("Alliano");
            Assertions.assertEquals(1, queue.size());

            Assertions.assertEquals("Alliano", queue.poll());
        }

    }
}
```

# Information Test

kita juga bisa mendapatkan informasi test yang sedanga berjalan menggunakan interface TestInfo.
Kita bisa menambahkan TestInfor pada parameter unit test.

example :
``` java
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

@DisplayName(value = "unit test information")
public class InformationTest {
    
    @Test @Tag(value = "test1") @DisplayName(value = "this ini test 1")
    public void testInformation(TestInfo testInfo) {

        System.out.println(testInfo.getDisplayName());

        System.out.println(testInfo.getTags());

        System.out.println(testInfo.getTestMethod().get());
        
        System.out.println(testInfo.getTestClass().get());
    }
}
```

# Dependency Injection Unit Test

Sebenarnya fitur TestInfo yang sebelumnya kita pakai adalah bagian dari dependency Injection di JUnit.
Dependency Injection sederhananya adalah bagaimana kita memasukan dependency (Object/Instance) kedalam unit test secara otomatis tampa proses manual.
Saat kita menambahakan parameter di function atau method unit test, sebenarnya kita bisa secara otomatis meng inject parameter tersebut dengan bantuan ParameterResolver.
Contoh nya Testinfo yang kita bahas sebelumnya, sebenarnya Object dibuat oleh TestInfoParameterResolver.

Example :

``` java
import java.util.Random;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

// Contoh membuat Object yang kita akan inject di paramter nantinya
// ini kita meng implementasi ParameterResolver
public class RandomParameterResolver implements ParameterResolver {

    private final Random random = new Random();

    // method ini untuk menentukan tipe parameter apa yang kita akan Inject, dalam contoh ini adalah tipe java.util.Random
    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType() == Random.class;
    }

    // disini proses inject terjadi
    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return this.random;
    }
    
}
```
# Meregistrasikan ParameterResolver

Untuk menggunakan RandomParameterResolver yang sudah kita buat, kita bisa meregistrasikan dengan menggunakan annotasi @ExtendWith().
Jikalau Kita ingin meregistrasikan Lebih dari 1 ParameterResolvernya (dalam konteks ini adalan class RandomParameterResolver implementasi dari ParameterResolver) kita bisa menggunakan annotasi @Extensions().

example : 
``` java
// disni kita registrasikan class RandomParameterResolver.class yang kita buat tadi
@Extensions(value = {
    @ExtendWith(value = RandomParameterResolver.class)
})
public class RandomCalculatorTest {
    
    private Calculator calculator = new Calculator();

    @Test // disni proses inject akan dilakukan oleh class RandomParameterResolver.class yang mana
    // class tersebut adalah implementasi dari ParameterResolver
    public void testRandomAdd(Random random) {

        int bilanganPertama = random.nextInt();

        int bilanganKedua = random.nextInt();

        Integer result = this.calculator.add(bilanganPertama, bilanganKedua);

        int expected = (bilanganPertama+bilanganKedua);

        Assertions.assertEquals(expected, result);
    }
}
```

# Pewarisan pada Unit Test

JUnit mendukung pewarisan pada unit test, artinya jikalau kita membuat class atau interface dan menambahkan informasi pada class test tersebut, maka ketika kita membuat class unit test turunanya, secara otomatis semua fitur pada class unit test parent nya akan dimiliki oleh turunanya.
Ini sanagat cocok ketika kita sering membuat code yang mana kode tersebut dibutuhkan pada class lain.

example :
``` java
/**
 * Semua class yang extend class ini maka semua annnotasi dan method unit test di class ini
 * akan dimiliki oleh class yang meng extend class ini
 */
@Extensions(value = {
    @ExtendWith(value = RandomParameterResolver.class)
})@TestInstance(value = Lifecycle.PER_CLASS)
public class ParentCalculatorTest {

    protected Calculator calculator = new Calculator();

    @BeforeAll
    public void setup() {
        System.out.println("Before All");
    }
}
```

contoh class yang meng extend 
``` java
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

    // annotasi @AfterAll hanya bisa digunakan pada method static berhubung paren class ini 
    // memiliki annotasi @TestInstance(value = Lifecycle.PER_CLASS) maka annotasi ini bisa
    // digunakan walaupun bukan pada method static, ini adalah keuntungan menggunakan
    // annotasi @TestInstance(value = Lifecycle.PER_CLASS)
    @AfterAll
    public void tearDown() {
        System.out.println("After All");
    }
}
```

# Test Berulang

JUnit mendukung eksekusi unit test berulang kali sesuai dengan jumlah yang kita tentukan.
Untuk mengulang eksekusi uniy test, kita bisa menggunakan annotation @RepetedTest() pada method unit test nya.
@RepetedTest() juga bisa digunakan untuk mengubah detail nama test nya, dan kita bisa menggunakan value {displayName} untuk mendapatkan display name, {currentRepetiton} untuk mendapatkan perulanagan keberapa saat ini, {totalRepetition} untuk mendapatkan total perulanaganya.

example : 
``` java
    @DisplayName(value = "Test Repetitions")
    // parameter value adalah jumlah repetisi yang kita mau inginkan
    @RepeatedTest(value = 10, name = "{displayName} ke {currentRepetition} dari {totalRepetitions}")
    public void repeatedTest() {
        System.out.println("Repetition Test");
    }
```

# Informasi Perulangan

@RepetedTest() juga memiliki object RepetisionInfor yang di inject oleh class RepitisionInforParameterResolver, sehingga kita bisa mendapatkan informasi ReperisionInfo melalui parameter pada unit test.

example : 
``` java
    @DisplayName(value = "test_repetision_info")
    @RepeatedTest(value = 10, name = "{displayName}")
    // parameter pada method ini akan di inject oleh class RepetitionInfoParameterResolver 
    // mekanisme proses injec nya sama dengan saat kita membuat RandomTestParameterResolver
    public void testRepetisionInfo(RepetitionInfo repetitionInfo, TestInfo testInfo) {
        System.out.println(testInfo.getDisplayName()+" ke "+repetitionInfo.getCurrentRepetition()+" dari "+repetitionInfo.getTotalRepetitions());
    }
```

# Test Dengan Parameter

Sebelumya kita sudah tau jikalau ingin menambahakan parameter di function atau method unit test nya, maka kita perlu membuat ParameterResolver.
namun jikalau terlalu banyak membuat implementasi ParameterResolver untuk meng inject parameter pada parameter Unit test kita itu agak menyulitkan kita.
JUnit memiliki fitur yang @ParameterizedTest(), yang mana annotasi ini dikhususkan utuk method unit test yang memiliki parameter.
Yang perlu kita lakukan adalah dengan mengganti annotasi @Test() menajadi @ParameterizedTest().

# Source Parameter

@ParameterizedTest() mendukung beberapa sumber parameter, yaitu:
@ValueSource(), untuk parameter yang bertipe Number, Char, Boolean, dan String.
@EnumSource(), untuk parameter yang berupa Enum.
@MethodSource(), untuk parameter yang bertipe dari static method.
@CsvSource(), untuk parameter berupa data CSV.
@CsvFileSource(), utnuk parameter berupa file CSV.
@ArgumentSource(), utnk parameter dari class ArgumentProvider.
``` java
    @DisplayName(value = "Test Calculator with parameter")
    @ParameterizedTest(name = "{displayName} with parameter {0}")
    @ValueSource(ints = {1,2,3,4,5,6,7,8,9,10})
    // nantinya value dari ints = {} itu akan di iterasi (dilakuakn perulangan) untuk dimasukan 
    // ke parameter unit unit test ini, dalam kasus kita ini kita memiliki value 1 - 10 maka 
    // nilai tersebut akan di interasi dan dimasukan sebagai parameter unit test ini 1 per 1
    public void testWithParameter(int value) {
        
        int expected = (value + value);
        
        Integer result = this.calculator.add(value, value);

        Assertions.assertEquals(expected, result);
        
        System.out.println("Test dentgan parameter "+ value);
    }
```