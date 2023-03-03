package com.java.unit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledForJreRange;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariables;
import org.junit.jupiter.api.condition.DisabledIfSystemProperties;
import org.junit.jupiter.api.condition.DisabledIfSystemProperty;
import org.junit.jupiter.api.condition.DisabledOnJre;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledForJreRange;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariables;
import org.junit.jupiter.api.condition.EnabledIfSystemProperties;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.condition.OS;

public class ConditionTest {
    
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

    @Test
    @EnabledOnJre(value = {JRE.JAVA_18})
    public void testEnableOnJava18() {
        System.out.println("test untuk java versi 18");
    }

    @Test
    @DisabledOnJre(value = {JRE.JAVA_18}, disabledReason = "because this unit test not allowed in java 18")
    public void testDisableOnJava18() {
        System.out.println("test disable untuk java 18");
    }

    @Test
    @DisabledForJreRange(min = JRE.JAVA_11, max = JRE.JAVA_17)
    public void testDisableJavaFromRange11to17() {
        System.out.println("test untuk java versi 11 - 17");
    }

    @Test
    @EnabledForJreRange(min = JRE.JAVA_17, max = JRE.JAVA_18)
    public void testEnableJavaFromRange11to17() {
        System.out.println("test untuk java versi 11 - 17");
    }

    @Test
    public void testSystemProperies() {
        // ini akan menampilkan semua properties yang dimiliki oleh jdk kita
        System.getProperties().forEach( (key, value) -> {
            System.out.println(key+" : "+value);
        } );
    }

    // unit test ini akan dijalankan jika major dari java kita atau java.class.version kita adalah 62.0
    @Test @EnabledIfSystemProperty(named = "java.class.version", matches = "62.0")
    public void testEnableIfSystemProperty(){
        System.out.println("testEnableIfSystemProperty berjalan");
    }

    // Test ini tidak akan dijalankan jika user.language nya itu en
    @Test @DisabledIfSystemProperty(named = "user.language", matches = "en")
    public void testDisableIfSystemProperty(){
        System.out.println("testDisableIfSystemProperty");
    }

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

    // jika profile nya dalam environment variable nya itu dev maka unit test ini akan dijalankan
    @Test @EnabledIfEnvironmentVariable(named = "profile", matches = "dev")
    public void testEnabledIfEnvironmentVariable(){
        System.out.println("EnabledIfEnvironmentVariable jalan");
    }

    // jika profile pada environment variable nya itu dev maka unit test ini tidak akan dijalankan
    @Test @DisabledIfEnvironmentVariable(named = "profile", matches = "dev")
    public void testDisabledIfEnvironmentVariable(){
        System.out.println("DisabledIfEnvironmentVariable");
    }

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
}
