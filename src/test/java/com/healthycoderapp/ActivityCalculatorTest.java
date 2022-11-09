package com.healthycoderapp;

import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class ActivityCalculatorTest {

    @ParameterizedTest
    @CsvSource({"18", "32", "54", "65", "78", "94", "23"})
    void testTrue(Integer age){
        // when
        boolean result = Calculator.verify(age);
        // then
        assertTrue(result);
    }

    @ParameterizedTest
    @CsvSource({"0", "3", "4", "6", "12", "17", "12"})
    void testFalse(Integer age){
        // when
        boolean result = Calculator.verify(age);
        // then
        assertFalse(result);
    }

    @ParameterizedTest
    @CsvSource({"-1", "-5", "-15", "-28", "-34", "-48", "-59"})
    void testInvalidAge(Integer age){
        // when
        Executable executable = () -> Calculator.verify(age);
        // then
        assertThrows(NumberFormatException.class, executable);
    }

    @ParameterizedTest
    @CsvSource({"-1", "-5", "-15", "-28", "-34", "-48", "-59"})
    void testExceptionMessage(Integer age){
        // given
        String expected = "Invalid age";
        String resultMessage = "";
        // when
        try {
            boolean result = Calculator.verify(age);
        } catch (NumberFormatException ex) {
            resultMessage = ex.getMessage();
        }
        assertEquals(expected, resultMessage);
    }

    public static class Calculator {
        public static boolean verify(int age) {
            boolean result = false;
            if(age < 0) throw new NumberFormatException("Invalid age");
            if(age >= 18) result = true;
            return result;
        }
    }
}
