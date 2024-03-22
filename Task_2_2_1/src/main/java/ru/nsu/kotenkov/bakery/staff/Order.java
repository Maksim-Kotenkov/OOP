package ru.nsu.kotenkov.bakery.staff;


/**
 * Order class for deserializing and then for use.
 */
public class Order {
    private int id;
    private int timeToCook;
    private int timeToDeliver;

    /**
     * Setter for deserializing.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Setter for deserializing.
     *
     * @param timeToCook time to cook the order
     */
    public void setTimeToCook(int timeToCook) {
        this.timeToCook = timeToCook;
    }

    /**
     * Setter for deserializing.
     *
     * @param timeToDeliver time to deliver the order
     */
    public void setTimeToDeliver(int timeToDeliver) {
        this.timeToDeliver = timeToDeliver;
    }

    /**
     * Getter for baker.
     *
     * @return time to cook the order
     */
    public int getTimeToCook() {
        return timeToCook;
    }

    /**
     * Getter for courier.
     *
     * @return time to deliver the order
     */
    public int getTimeToDeliver() {
        return timeToDeliver;
    }

    /**
     * Getter for tracing operations with this order.
     *
     * @return id
     */
    public int getId() {
        return id;
    }
}
