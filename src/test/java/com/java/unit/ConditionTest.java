package com.java.unit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledForJreRange;
import org.junit.jupiter.api.condition.DisabledOnJre;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledForJreRange;
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
}
