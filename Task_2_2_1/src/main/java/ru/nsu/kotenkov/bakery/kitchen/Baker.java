package ru.nsu.kotenkov.bakery.kitchen;


import ru.nsu.kotenkov.bakery.exceptions.BakerInterrupted;


public class Baker {
    public int id;
    public int efficiency;

    public void setId(int id) {
        this.id = id;
    }

    public void setEfficiency(int efficiency) {
        this.efficiency = efficiency;
    }
}
