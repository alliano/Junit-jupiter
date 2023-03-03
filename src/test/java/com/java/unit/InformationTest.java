package com.java.unit;

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
