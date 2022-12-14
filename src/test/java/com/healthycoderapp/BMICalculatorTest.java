package com.healthycoderapp;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

class BMICalculatorTest {

    private String environment = "dev";

    @BeforeAll // always static
    static void beforeAll(){
        System.out.println("Before all unit tests");
    }

    @AfterAll
    static void afterAll(){
        System.out.println("After all unit tests");
    }

    @Nested
    @DisplayName(">>> Sample Inner Class display name")
    class isDietRecommendedTests {
        @Test
        void shouldReturnTrueWhenDietRecommended_v1() {
            // given
            double weight = 85.0;
            double height = 1.69;
            // when
            boolean recommended = BMICalculator.isDietRecommended(weight, height);
            // then
            assertTrue(recommended);
        }

        @ParameterizedTest
        @CsvSource({"85.0,1.69", "89.0,1.72", "95.0,1.75"})
        void shouldReturnTrueWhenDietRecommended_v2(Double coderWeight, Double coderHeight) {
            // given
            double weight = coderWeight;
            double height = coderHeight;
            // when
            boolean recommended = BMICalculator.isDietRecommended(weight, height);
            // then
            assertTrue(recommended);
        }

        @ParameterizedTest(name = "weight {0}, height {1}")
        @CsvFileSource(resources = "/diet-recommended-input-data.csv", numLinesToSkip = 1)
        void shouldReturnTrueWhenDietRecommended_v3(Double coderWeight, Double coderHeight) {
            // given
            double weight = coderWeight;
            double height = coderHeight;
            // when
            boolean recommended = BMICalculator.isDietRecommended(weight, height);
            // then
            assertTrue(recommended);
        }

        @Test
            // always test the opposite result of logic
        void shouldReturnFalseWhenDietNotRecommended() {
            // given
            double weight = 50.0;
            double height = 1.92;
            // when
            boolean recommended = BMICalculator.isDietRecommended(weight, height);
            // then
            assertFalse(recommended);
        }

        @Test
        void shouldThrowArithmeticExceptionWhenHeightZero() {
            // given
            double weight = 50.0;
            double height = 0.0;
            // when
            Executable executable = () -> BMICalculator.isDietRecommended(weight, height);
            // then
            assertThrows(ArithmeticException.class, executable);
        }
    }

    @Nested
    class findCoderWithWorstBMITests {
        @Test
        @DisplayName(">>> Sample Method display name")
        @Disabled // disable test
        void should_ReturnCoderWithWorstBMI_When_CoderListNotEmpty(){
            // given
            List<Coder> coders = new ArrayList<>();
            coders.add(new Coder(1.80, 60.0));
            coders.add(new Coder(1.82, 98.0));
            coders.add(new Coder(1.82,64.7));
            // when
            Coder coderWorstBMI = BMICalculator.findCoderWithWorstBMI(coders);
            // then
            assertAll(
                    () -> assertEquals(1.82, coderWorstBMI.getHeight()),
                    () -> assertEquals(98.0, coderWorstBMI.getWeight())
            );
        }

        @Test
        void should_ReturnCoderWithWorstBMIIn1Ms_When_CoderListHas1000Elements(){
            // given
            assumeTrue(BMICalculatorTest.this.environment.equals("prod")); // results in skiped test
            List<Coder> coders = new ArrayList<>();
            for (int i = 0; i < 1000; i++)
                coders.add(new Coder(1.0 + i, 10.0 + i));
            // when
            Executable executable = () -> BMICalculator.findCoderWithWorstBMI(coders);
            // then
            assertTimeout(Duration.ofMillis(10), executable);
        }

        @Test
        @DisabledOnOs(OS.WINDOWS)
        void shouldReturnNullWorstBMIWhenCoderListEmpty(){
            // given
            List<Coder> coders = new ArrayList<>();
            // when
            Coder coderWorstBMI = BMICalculator.findCoderWithWorstBMI(coders);
            // then
            assertNull(coderWorstBMI);
        }
    }

    @Nested
    class getBMIScoresTests {
        @Test
        void should_ReturnCorrectBMIScoreArray_When_CoderListNotEmpty(){
            // given
            List<Coder> coders = new ArrayList<>();
            coders.add(new Coder(1.80, 60.0));
            coders.add(new Coder(1.82, 98.0));
            coders.add(new Coder(1.82,64.7));
            double[] expected = {18.52, 29.59, 19.53};
            // when
            double[] bmiScores = BMICalculator.getBMIScores(coders);
            // then
            assertArrayEquals(expected, bmiScores);
        }
    }
}