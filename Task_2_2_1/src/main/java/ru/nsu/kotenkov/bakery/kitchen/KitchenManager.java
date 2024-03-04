package ru.nsu.kotenkov.bakery.kitchen;

import ru.nsu.kotenkov.bakery.Order;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static java.lang.Boolean.FALSE;

public class KitchenManager extends Thread {
    private final ArrayList<BakerThread> bakers;
    private final ArrayList<Order> orders;

    public KitchenManager(ArrayList<BakerThread> bakers, ArrayList<Order> orders) {
        this.bakers = bakers;
        this.orders = orders;
    }

    @Override
    public void run() {
        System.out.println("Orders number:" + orders.size());

        int bakeId = 0;

        while (bakeId < orders.size()) {
            // give some work for all ready bakers
            BakerThread readyBaker = null;
            while ((readyBaker = bakers.stream().filter(BakerThread::isReady).findAny().orElse(null)) != null
                    && bakeId < orders.size()){
//                BakerThread readyBaker = bakers.stream().filter(BakerThread::isReady).findAny().orElse(null);
                readyBaker.setOrder(orders.get(bakeId));
                readyBaker.setMyself(new Thread(readyBaker));
                readyBaker.setReady(FALSE);
                readyBaker.getMyself().start();

                bakeId += 1;
            }

        }

        for (Thread t : bakers.stream().map(BakerThread::getMyself).collect(Collectors.toSet())) {
            try {
                t.join();
            } catch (InterruptedException e) {
                System.err.println("Baker interrupted");
            }
        }
    }
}
