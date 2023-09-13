package ru.nsu.kotenkov.polynomial;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class PolynomialTest {
    @Test
    @DisplayName("Init test")
    void checkInit() {
        Polynomial p1 = new Polynomial(new int[] {4, 3, 6, 7});
        assertArrayEquals(new int[] {4, 3, 6, 7}, p1.cfs);
    }

    @Test
    void checkDegree() {
        Polynomial p1 = new Polynomial(new int[] {4, 3, 6, 7});
        if (p1.degree == 4) {
            assertTrue(true);
        } else {
            fail("Not correct degree");
        }
    }

    @Test
    void checkEval() {
        Polynomial p1 = new Polynomial(new int[] {4, 3, 6, 7});

        assertEquals(256, p1.evaluate(3));
    }

    @Test
    void checkSum_p1SmallOrEqual() {
        Polynomial p1 = new Polynomial(new int[] {2, 3});
        Polynomial p2 = new Polynomial(new int[] {4, 3, 6, 7});
        assertArrayEquals(new int[] {6, 6, 6, 7}, p1.plus(p2).cfs);
    }

    @Test
    void checkSum_p2Small() {
        Polynomial p1 = new Polynomial(new int[] {4, 3, 6, 7});
        Polynomial p2 = new Polynomial(new int[] {1, 2, 3});
        p1.plus(p2);
        assertArrayEquals(new int[] {5, 5, 9, 7}, p1.cfs);
    }

    @Test
    void checkSub_p1SmallOrEqual() {
        Polynomial p1 = new Polynomial(new int[] {2, 3});
        Polynomial p2 = new Polynomial(new int[] {4, 3, 6, 7});
        assertArrayEquals(new int[] {2, 0, 6, 7}, p1.minus(p2).cfs);
    }

    @Test
    void checkSub_p2Small() {
        Polynomial p1 = new Polynomial(new int[] {4, 3, 6, 7});
        Polynomial p2 = new Polynomial(new int[] {1, 2, 3});
        p1.minus(p2);
        assertArrayEquals(new int[] {3, 1, 3, 7}, p1.cfs);
    }

    @Test
    void checkMul_p1SmallOrEqual() {
        Polynomial p1 = new Polynomial(new int[] {2, 3});
        Polynomial p2 = new Polynomial(new int[] {2, 3});
        assertArrayEquals(new int[] {4, 12, 9}, p1.times(p2).cfs);
    }

    @Test
    void checkMul_p2Small() {
        Polynomial p1 = new Polynomial(new int[] {4, 3, 6, 7});
        Polynomial p2 = new Polynomial(new int[] {1, 2, 3});
        p1.times(p2);
        assertArrayEquals(new int[] {4, 11, 24, 28, 32, 21}, p1.cfs);
    }

    @Test
    void checkDifferentiate1() {
        Polynomial p1 = new Polynomial(new int[] {4, 3, 6, 7});

        assertArrayEquals(new int[] {3, 12, 21}, p1.differentiate(1).cfs);
    }

    @Test
    void checkDifferentiate2() {
        Polynomial p1 = new Polynomial(new int[] {4, 3, 6, 7});

        assertArrayEquals(new int[] {12, 42}, p1.differentiate(2).cfs);
    }

    @Test
    void checkEqualityTrue() {
        Polynomial p1 = new Polynomial(new int[] {4, 3, 6, 7});
        Polynomial p2 = new Polynomial(new int[] {4, 3, 6, 7});

        if (p1.equals(p2)) {
            assertTrue(true);
        } else {
            assertFalse(false);
        }
    }

    @Test
    void checkEqualityFalse() {
        Polynomial p1 = new Polynomial(new int[] {4, 3, 6, 7});
        Polynomial p2 = new Polynomial(new int[] {2, 9});

        if (!p1.equals(p2)) {
            assertTrue(true);
        } else {
            assertFalse(false);
        }
    }

    @Test
    void checkStringView() {
        Polynomial p1 = new Polynomial(new int[] {4, 3, 6, 7});

        String res = p1.toString();

        Assertions.assertEquals("7x^3 + 6x^2 + 3x + 4", res);
    }

    @Test
    void checkStringViewWithZero() {
        Polynomial p1 = new Polynomial(new int[] {4, 3, 0, 7});

        String res = p1.toString();

        Assertions.assertEquals("7x^3 + 3x + 4", res);
    }

    @Test
    void checkComplicatedTest1() {
        Polynomial p1 = new Polynomial(new int[] {4, 3, 6, 7});
        Polynomial p2 = new Polynomial(new int[] {3, 2, 8});

        Assertions.assertEquals("7x^3 + 6x^2 + 19x + 6", p1.plus(p2.differentiate(1)).toString());
    }

    @Test
    void checkComplicatedTest2() {
        Polynomial p1 = new Polynomial(new int[] {4, 3, 6, 7});
        Polynomial p2 = new Polynomial(new int[] {3, 2, 8});

        Assertions.assertEquals(3510, p1.times(p2).evaluate(2));
    }
}
