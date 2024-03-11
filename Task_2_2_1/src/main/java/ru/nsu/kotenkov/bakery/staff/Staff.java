package ru.nsu.kotenkov.bakery.staff;


/**
 * Interface for based Big Kahuna Burger worker.
 */
public interface Staff {
    /**
     * Staff needs to be ready or not.
     *
     * @return readiness
     */
    boolean isReady();

    /**
     * Setter to set a worker as busy.
     *
     * @param ready new state
     */
    void setReady(boolean ready);

    /**
     * Setter for giving an order.
     *
     * @param order new order
     */
    void setOrder(Order order);

    /**
     * To run thread.
     */
    void run();
}
