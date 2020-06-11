package com.company;

public class Main {

    public static void main(String[] args) {
        HoQueue<String> hoQueue = new HoQueue(5,200);

        Publisher publisher = new Publisher(hoQueue, "공급자 1", 100);
        Publisher publisher1 = new Publisher(hoQueue, "공급자 2", 150);
        Publisher publisher2 = new Publisher(hoQueue, "공급자 3", 180);
        Publisher publisher3 = new Publisher(hoQueue, "공급자 4", 200);
        Publisher publisher4 = new Publisher(hoQueue, "공급자 5", 120);

        Consumer consumer = new Consumer(hoQueue, "소비자 1",200);
        Consumer consumer1 = new Consumer(hoQueue, "소비자 2", 100);
        Consumer consumer2 = new Consumer(hoQueue, "소비자 3", 120);

        publisher.start();
        consumer.start();
        publisher1.start();
        publisher2.start();
        consumer1.start();
        publisher3.start();
        publisher4.start();
        consumer2.start();
    }
}


