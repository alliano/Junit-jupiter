package com.java.unit;

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
