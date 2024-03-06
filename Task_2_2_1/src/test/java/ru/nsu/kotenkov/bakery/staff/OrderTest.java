package ru.nsu.kotenkov.bakery.staff;


import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;


public class OrderTest {
    @Test
    public void OrderStructTest() {
        Order testOrder = new Order();
        testOrder.setId(1000);
        testOrder.setTimeToCook(10);
        testOrder.setTimeToDeliver(18);

        assertEquals(1000, testOrder.getId());
        assertEquals(10, testOrder.getTimeToCook());
        assertEquals(18, testOrder.getTimeToDeliver());
    }
}
