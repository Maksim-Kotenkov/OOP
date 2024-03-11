package ru.nsu.kotenkov.bakery.staff.management;


import java.util.ArrayList;
import ru.nsu.kotenkov.bakery.staff.Order;
import ru.nsu.kotenkov.bakery.staff.Staff;


/**
 * Runnable thread-like class for couriers.
 */
public class CourierThread extends Thread implements Staff {
    private Thread myself;
    public int id;
    private final int capacity;
    private int speed;
    private boolean ready = true;
    private ArrayList<Order> orders;

    /**
     * Constructor.
     *
     * @param id id of a courier
     * @param capacity how many Big Kahuna Burgers can be delivered
     * @param speed fast boi
     */
    public CourierThread (int id, int capacity, int speed) {
        this.id = id;
        this.capacity = capacity;
        this.speed = speed;
        this.orders = new ArrayList<>();
    }

    /**
     * Getter for capacity.
     *
     * @return capacity
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Getter for speed.
     *
     * @return speed
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Mandatory method to check readiness.
     *
     * @return ready steady go
     */
    @Override
    public boolean isReady() {
        return ready;
    }

    /**
     * What running thread is connected to this object.
     *
     * @return connected thread
     */
    public Thread getMyself() {
        return myself;
    }

    /**
     * After creating a thread from this courier we set myself.
     *
     * @param myself connected thread
     */
    public void setMyself(Thread myself) {
        this.myself = myself;
    }

    /**
     * Set readiness after giving an order.
     *
     * @param ready new state
     */
    @Override
    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public boolean canGetOrder() {
        return capacity > orders.size();
    }

    /**
     * Giving an order.
     *
     * @param order new order
     */
    public void setOrder(Order order) {
        this.orders.add(order);
        System.out.println("COURIER: Courier " + id + " took the order "
                + order.getId() + " with the time to deliver: " + order.getTimeToDeliver());
    }

    /**
     * Thread body that is run while delivering.
     */
    @Override
    public void run() {
        try {
            this.ready = false;
            for (Order order : orders) {
                Thread.sleep((order.getTimeToDeliver() / this.speed) * 1000L);
                System.out.println("COURIER: Courier " + id
                        + " has delivered order " + order.getId());
            }
            this.ready = true;
        } catch (InterruptedException e) {
            System.err.println("COURIER: Courier " + id + " was interrupted while delivering.");
        }

        this.orders = new ArrayList<>();
    }
}
