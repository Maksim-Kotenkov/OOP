package ru.nsu.kotenkov.bakery.staff.kitchen;


/**
 * Baker that can be deserialized from json.
 * AS we cannot deserialize something that is extended from Thread, this class is used.
 */
public class Baker {
    public int id;
    public int efficiency;

    /**
     * Setters are used by ObjectMapper from Jackson.
     *
     * @param id for id init
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Setters are used by ObjectMapper from Jackson.
     *
     * @param efficiency for efficiency init
     */
    public void setEfficiency(int efficiency) {
        this.efficiency = efficiency;
    }
}
