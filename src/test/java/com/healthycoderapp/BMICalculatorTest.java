package com.healthycoderapp;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BMICalculatorTest {

    @Test
    // should.. when
    void shouldReturnTrueWhenDietRecommended() {
        // given
        double weight = 89.0;
        double height = 1.72;
        // when
        boolean recommended = BMICalculator.isDietRecommended(weight, height);
        // then
        assertTrue(recommended);
    }

    @Test
    // always test the opposite result of logic
    void shouldReturnFalseWhenDietNotRecommended() {
        double weight = 50.0;
        double height = 1.92;
        boolean recommended = BMICalculator.isDietRecommended(weight, height);
        assertFalse(recommended);
    }
}