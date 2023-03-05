package com.java.unit;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@DisplayName(value = "testMocking")
public class MockTest {
    
    @Test
    public void testMock() {

        // membuat object mocking 
        List<String> listMock = Mockito.mock(List.class);

        /** membuat perilaku object listMock
         * ketika mothod get() di panggil dengan parameter 0 maka 
         * akan object mocking nya 
         * akan mereturn object string Alliano
         * Mockito.when(listMock.get(0)).thenReturn("Alliano");
         */
        Mockito.when(listMock.get(0)).thenReturn("Alliano");

        System.out.println(listMock.get(0));
        
        System.out.println(listMock.get(0));
        
        /**
         * dan ketika method get() dengan parameter 0 pada object moking yang kita buat 
         * (listMock) dipanggil lebih dari 2 kali maka akan terjadi throwig exception
         * artinya method get(0) pada object listMock hanya boleh di panggil sebanyak
         * dua kali
         * 
         */
        Mockito.verify(listMock, Mockito.times(2)).get(0);
    }
}
