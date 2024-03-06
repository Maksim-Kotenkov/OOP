package ru.nsu.kotenkov.bakery.staff.kitchen;

import ru.nsu.kotenkov.bakery.Bakery;
import ru.nsu.kotenkov.bakery.staff.Order;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static java.lang.Boolean.FALSE;

public class KitchenManager extends Thread {
    private final ArrayList<BakerThread> bakers;
    private final ArrayList<Order> orders;
    private final Bakery office;
    public boolean bakersWorkingHard = true;

    public KitchenManager(ArrayList<BakerThread> bakers, ArrayList<Order> orders, Bakery office) {
        this.bakers = bakers;
        this.orders = orders;
        this.office = office;
    }

    @Override
    public void run() {
        System.out.println("KITCHEN: Orders number:" + orders.size());

        int bakeId = 0;

        while (bakeId < orders.size()) {
            if (interrupted()) {
                synchronized (this) {
                    System.out.println("KITCHEN: Waiting for all working bakers to finish baking");
                    // TODO also store all orders that are left in json
                    // TODO create a class for saving orders
                    try {
                        for (BakerThread b : bakers) {
                            if (b.getMyself() != null) {
                                b.getMyself().join();
                            }
                        }
                    } catch (InterruptedException e) {
                        System.err.println("KITCHEN: Waiting for all the bakers was interrupted");
                    }
                    bakersWorkingHard = false;
                    notify();
                }
                break;
            }

            // give some work for all ready bakers
            BakerThread readyBaker = null;
            while ((readyBaker = bakers.stream().filter(BakerThread::isReady).findAny().orElse(null)) != null
                    && bakeId < orders.size()){
//                BakerThread readyBaker = bakers.stream().filter(BakerThread::isReady).findAny().orElse(null);
                readyBaker.setOrder(orders.get(bakeId));
                readyBaker.setReady(FALSE);
                readyBaker.setMyself(new Thread(readyBaker));
                readyBaker.getMyself().start();

                bakeId += 1;
            }
        }

        for (Thread t : bakers.stream().map(BakerThread::getMyself).collect(Collectors.toSet())) {
            try {
                t.join();
            } catch (InterruptedException e) {
                synchronized (this) {
                    System.out.println("KITCHEN: Waiting for all working bakers to finish baking");
                    try {
                        for (BakerThread b : bakers) {
                            if (b.getMyself() != null) {
                                b.getMyself().join();
                            }
                        }
                    } catch (InterruptedException ex) {
                        System.err.println("KITCHEN: Waiting for all the bakers was interrupted");
                    }
                    bakersWorkingHard = false;
                    notify();
                }
            }
        }
    }
}
