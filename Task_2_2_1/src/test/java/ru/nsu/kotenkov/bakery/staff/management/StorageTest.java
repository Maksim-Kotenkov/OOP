package ru.nsu.kotenkov.bakery.staff.management;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.nsu.kotenkov.bakery.staff.Order;

import static org.junit.jupiter.api.Assertions.*;

public class StorageTest {
    @Test
    @DisplayName("Init")
    public void checkInit() {
        Storage st = new Storage(120);

        assertNotNull(st);
        assertTrue(st.canStore());
    }

    @Test
    @DisplayName("Useless storage")
    public void checkUselessStorage() {
        Storage st = new Storage(-111);

        assertNotNull(st);
        assertFalse(st.canStore());
    }

    @Test
    @DisplayName("Adding one order without ability to add more")
    public void checkChangingFreeSpace() {
        Storage st = new Storage(1);

        Order ord = new Order();
        st.addOrder(ord);

        assertTrue(st.notEmpty());
        assertFalse(st.canStore());
    }

    @Test
    @DisplayName("Removing order from the store increases free space")
    public void checkGettingOrder() {
        Storage st = new Storage(1);

        Order ord = new Order();
        st.addOrder(ord);
        ord = st.getOrder();

        assertNotNull(ord);
        assertTrue(st.canStore());
    }
}
