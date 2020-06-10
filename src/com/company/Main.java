package com.company;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {

    public static void main(String[] args) {
        HoQueue hoQueue = new HoQueue(3);

        Publisher publisher = new Publisher(hoQueue);
        Publisher publisher1 = new Publisher(hoQueue);
        Consumer consumer = new Consumer(hoQueue);

        publisher.start();
        consumer.start();
        publisher1.start();

    }
}


class HoQueue{
    Queue<String> queue;
    int maxSize;
    public HoQueue(int maxSize){
        queue = new LinkedList<>();
        this.maxSize = maxSize;
    }
    public synchronized void enqueue(String str){
        if(!isPushAble()){
            try{
                wait();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        notify();
        queue.add(str);
        System.out.println("큐 삽입 : " + str);
    }
    public synchronized String dequeue(){
        if(!isPollAble()){
            try{
                wait();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        notify();
        System.out.println("큐 제거 : " + queue.peek());
        return queue.poll();
    }
    public boolean isPushAble(){
        return queue.size() < maxSize;
    }

    public boolean isPollAble(){
        return queue.size() > 0;
    }

    public boolean isEmpty(){
        return queue.isEmpty();
    }
}


class Publisher extends Thread{
    List<String> pockets;
    HoQueue hoQueue;
    int index;
    public Publisher(HoQueue hoQueue){
        this.pockets = new ArrayList<>();
        pockets.add("가");
        pockets.add("나");
        pockets.add("다");
        pockets.add("라");
        pockets.add("바");
        index = pockets.size()-1;
        this.hoQueue = hoQueue;
    }

    @Override
    public void run() {
        while(index>=0){
            hoQueue.enqueue(pockets.get(index));
            index--;
        }
    }
}

class Consumer extends Thread{
    public boolean isRun = true;
    List<String> pockets;
    HoQueue hoQueue;
    public Consumer(HoQueue hoQueue){
        this.pockets = new ArrayList<>();
        this.hoQueue = hoQueue;
    }

    @Override
    public void run() {
        while (isRun){
            pockets.add(hoQueue.dequeue());
        }
    }
}

