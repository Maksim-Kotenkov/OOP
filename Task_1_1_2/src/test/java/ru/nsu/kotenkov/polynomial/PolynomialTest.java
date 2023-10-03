package ru.nsu.kotenkov.polynomial;


import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


/**
 * Тестирующий класс.
 */
public class PolynomialTest {
    @Test
    @DisplayName("Init test")
    void checkInit() {
        Polynomial p1 = new Polynomial(new int[] {4, 3, 6, 7});
        assertArrayEquals(new int[] {4, 3, 6, 7}, p1.getCfs());
    }

    @Test
    @DisplayName("Check the degree of polynomial")
    void checkDegree() {
        Polynomial p1 = new Polynomial(new int[] {4, 3, 6, 7});
        if (p1.getDegree() == 4) {
            assertTrue(true);
        } else {
            fail("Not correct degree");
        }
    }

    @Test
    @DisplayName("Evaluation")
    void checkEval() {
        Polynomial p1 = new Polynomial(new int[] {4, 3, 6, 7});

        assertEquals(256, p1.evaluate(3));
    }

    @Test
    @DisplayName("Degree of p1 <= degree of p2")
    void checkSumP1SmallOrEqual() {
        Polynomial p1 = new Polynomial(new int[] {2, 3});
        Polynomial p2 = new Polynomial(new int[] {4, 3, 6, 7});
        assertArrayEquals(new int[] {6, 6, 6, 7}, p1.plus(p2).getCfs());
    }

    @Test
    @DisplayName("Degree of p1 > degree of p2")
    void checkSumP2Small() {
        Polynomial p1 = new Polynomial(new int[] {4, 3, 6, 7});
        Polynomial p2 = new Polynomial(new int[] {1, 2, 3});
        p1.plus(p2);
        assertArrayEquals(new int[] {5, 5, 9, 7}, p1.getCfs());
    }

    @Test
    @DisplayName("Sum with negative")
    void checkSumNegative() {
        Polynomial p1 = new Polynomial(new int[] {-2, -3});
        Polynomial p2 = new Polynomial(new int[] {4, 3, 6, 7});
        assertArrayEquals(new int[] {2, 0, 6, 7}, p1.plus(p2).getCfs());
    }

    @Test
    @DisplayName("Degree of p1 <= degree of p2")
    void checkSubP1SmallOrEqual() {
        Polynomial p1 = new Polynomial(new int[] {2, 3});
        Polynomial p2 = new Polynomial(new int[] {4, 3, 6, 7});
        assertArrayEquals(new int[] {2, 0, 6, 7}, p1.minus(p2).getCfs());
    }

    @Test
    @DisplayName("Degree of p1 > degree of p2")
    void checkSubP2Small() {
        Polynomial p1 = new Polynomial(new int[] {4, 3, 6, 7});
        Polynomial p2 = new Polynomial(new int[] {1, 2, 3});
        p1.minus(p2);
        assertArrayEquals(new int[] {3, 1, 3, 7}, p1.getCfs());
    }

    @Test
    @DisplayName("Sub with negative")
    void checkSubNegative() {
        Polynomial p1 = new Polynomial(new int[] {-2, -3});
        Polynomial p2 = new Polynomial(new int[] {4, 3, 6, 7});
        assertArrayEquals(new int[] {6, 6, 6, 7}, p2.minus(p1).getCfs());
    }

    @Test
    @DisplayName("Degree of p1 <= degree of p2")
    void checkMulP1SmallOrEqual() {
        Polynomial p1 = new Polynomial(new int[] {2, 3});
        Polynomial p2 = new Polynomial(new int[] {2, 3});
        assertArrayEquals(new int[] {4, 12, 9}, p1.times(p2).getCfs());
    }

    @Test
    @DisplayName("Degree of p1 > degree of p2")
    void checkMulP2Small() {
        Polynomial p1 = new Polynomial(new int[] {4, 3, 6, 7});
        Polynomial p2 = new Polynomial(new int[] {1, 2, 3});
        p1.times(p2);
        assertArrayEquals(new int[] {4, 11, 24, 28, 32, 21}, p1.getCfs());
    }

    @Test
    @DisplayName("Mul with negative")
    void checkMulNegative() {
        Polynomial p1 = new Polynomial(new int[] {-2, -3});
        Polynomial p2 = new Polynomial(new int[] {4, 3, 6, 7});
        assertArrayEquals(new int[] {-8, -18, -21, -32, -21}, p1.times(p2).getCfs());
    }

    @Test
    @DisplayName("1'th differential")
    void checkDifferentiate1() {
        Polynomial p1 = new Polynomial(new int[] {4, 3, 6, 7});

        assertArrayEquals(new int[] {3, 12, 21, 0}, p1.differentiate(1).getCfs());
    }

    @Test
    @DisplayName("1'th differential")
    void checkNegativeDifferentiate1() {
        Polynomial p1 = new Polynomial(new int[] {4, -3, -6, 7});

        assertArrayEquals(new int[] {-3, -12, 21, 0}, p1.differentiate(1).getCfs());
    }

    @Test
    @DisplayName("2'nd differential")
    void checkDifferentiate2() {
        Polynomial p1 = new Polynomial(new int[] {4, 3, 6, 7});

        assertArrayEquals(new int[] {12, 42, 0, 0}, p1.differentiate(2).getCfs());
    }

    @Test
    @DisplayName("A lot differential")
    void checkDifferentiate5() {
        Polynomial p1 = new Polynomial(new int[] {4, 3, 6, 7});

        assertArrayEquals(new int[] {0, 0, 0, 0}, p1.differentiate(5).getCfs());
    }

    @Test
    @DisplayName("Equals")
    void checkEqualityTrue() {
        Polynomial p1 = new Polynomial(new int[] {4, 3, 6, 7});
        Polynomial p2 = new Polynomial(new int[] {4, 3, 6, 7});

        assertEquals(p1, p2);
    }

    @Test
    @DisplayName("Not equals")
    void checkEqualityFalse() {
        Polynomial p1 = new Polynomial(new int[] {4, 3, 6, 7});
        Polynomial p2 = new Polynomial(new int[] {2, 9});

        if (!p1.equals(p2)) {
            assertTrue(true);
        } else {
            fail();
        }
    }

    @Test
    @DisplayName("Equals")
    void checkEqualityComplicated() {
        Polynomial p1 = new Polynomial(new int[] {4, 3, 6, 7, 0, 0});
        Polynomial p2 = new Polynomial(new int[] {4, 3, 6, 7});

        assertEquals(p1, p2);
    }

    @Test
    @DisplayName("Equals")
    void checkEqualityComplicated2() {
        Polynomial p1 = new Polynomial(new int[] {4, 3, 6, 7});
        Polynomial p2 = new Polynomial(new int[] {4, 3, 6, 7, 0, 0});

        assertEquals(p1, p2);
    }

    @Test
    @DisplayName("Equals")
    void checkEqualityNegative() {
        Polynomial p1 = new Polynomial(new int[] {4, -3, 6, 7});
        Polynomial p2 = new Polynomial(new int[] {4, -3, 6, 7, 0, 0});

        assertEquals(p1, p2);
    }

    @Test
    @DisplayName("toString check")
    void checkStringView() {
        Polynomial p1 = new Polynomial(new int[] {4, 3, 6, 7});

        String res = p1.toString();

        Assertions.assertEquals("7x^3 + 6x^2 + 3x + 4", res);
    }

    @Test
    @DisplayName("toString check with negative")
    void checkNegativeStringView() {
        Polynomial p1 = new Polynomial(new int[] {-4, 3, -6, 7});

        String res = p1.toString();

        Assertions.assertEquals("7x^3 - 6x^2 + 3x - 4", res);
    }

    @Test
    @DisplayName("toString with one zero coef")
    void checkStringViewWithZero() {
        Polynomial p1 = new Polynomial(new int[] {4, 3, 0, 7});

        String res = p1.toString();

        Assertions.assertEquals("7x^3 + 3x + 4", res);
    }

    @Test
    @DisplayName("Test from the task number 1")
    void checkComplicatedTest1() {
        Polynomial p1 = new Polynomial(new int[] {4, 3, 6, 7});
        Polynomial p2 = new Polynomial(new int[] {3, 2, 8});

        Assertions.assertEquals("7x^3 + 6x^2 + 19x + 6", p1.plus(p2.differentiate(1)).toString());
    }

    @Test
    @DisplayName("Test from the task number 2")
    void checkComplicatedTest2() {
        Polynomial p1 = new Polynomial(new int[] {4, 3, 6, 7});
        Polynomial p2 = new Polynomial(new int[] {3, 2, 8});

        Assertions.assertEquals(3510, p1.times(p2).evaluate(2));
    }
}
