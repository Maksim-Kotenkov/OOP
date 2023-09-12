package polynomial;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class PolynomialTest {
    @Test
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
        if (p1.evaluate(3) == 160) {
            assertTrue(true);
        } else {
            fail("Wrong evaluate result");
        }
    }

    @Test
    void checkSum_p1SmallOrEqual() {
        Polynomial p1 = new Polynomial(new int[] {2, 3});
        Polynomial p2 = new Polynomial(new int[] {4, 3, 6, 7});
        assertArrayEquals(new int[] {4, 3, 8, 10}, p1.plus(p2));
    }

    @Test
    void checkSum_p2Small() {
        Polynomial p1 = new Polynomial(new int[] {4, 3, 6, 7});
        Polynomial p2 = new Polynomial(new int[] {1, 2, 3});
        p1.minus(p2);
        assertArrayEquals(new int[] {4, 2, 4, 4}, p1.cfs);
    }

    @Test
    void checkSub_p1SmallOrEqual() {
        Polynomial p1 = new Polynomial(new int[] {2, 3});
        Polynomial p2 = new Polynomial(new int[] {4, 3, 6, 7});
        assertArrayEquals(new int[] {4, 3, 4, 4}, p1.minus(p2));
    }

    @Test
    void checkSub_p2Small() {
        Polynomial p1 = new Polynomial(new int[] {4, 3, 6, 7});
        Polynomial p2 = new Polynomial(new int[] {1, 2, 3});
        p1.minus(p2);
        assertArrayEquals(new int[] {4, 2, 4, 4}, p1.cfs);
    }

    @Test
    void checkMul_p1SmallOrEqual() {
        Polynomial p1 = new Polynomial(new int[] {2, 3});
        Polynomial p2 = new Polynomial(new int[] {4, 3, 6, 7});
        assertArrayEquals(new int[] {4, 3, 12, 21}, p1.times(p2));
    }

    @Test
    void checkMul_p2Small() {
        Polynomial p1 = new Polynomial(new int[] {4, 3, 6, 7});
        Polynomial p2 = new Polynomial(new int[] {1, 2, 3});
        p1.times(p2);
        assertArrayEquals(new int[] {4, 3, 12, 21}, p1.cfs);
    }
}
