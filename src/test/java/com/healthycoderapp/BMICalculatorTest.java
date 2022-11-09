package com.healthycoderapp;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BMICalculatorTest {

    @Test
    void test(){
       assertTrue(BMICalculator.isDietRecommended(89.0, 1.72));
    }

}