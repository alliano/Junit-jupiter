package com.java.unit;

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
