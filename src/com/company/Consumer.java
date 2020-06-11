package com.company;

import java.util.ArrayList;
import java.util.List;

/**
 * 소비자 클래스
 * @author 박경호
 */
class Consumer extends Thread{
    private final String name; // 소비자 이름
    private final int consumeTime; // 소비 인터벌 시간
    private List<String> pockets; // 소비자 아이템
    HoQueue<String> hoQueue;

    public Consumer(HoQueue hoQueue, String name, int consumeTime){
        this.pockets = new ArrayList<>();
        this.hoQueue = hoQueue;
        this.name = name;
        this.consumeTime = consumeTime;

        hoQueue.addConsumerList(this);
    }

    @Override
    public void run() {
        while (!hoQueue.isConsumeEnd()){
            try{
                Thread.sleep(consumeTime);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            String obj = hoQueue.dequeue(name);
            if(obj == null)
                break;
            pockets.add(obj);
        }
    }

    public String getConsumerName(){
        return this.name;
    }

    public int getPocketsSize(){
        return pockets.size();
    }

    public String getPocketsItem(int index){
        return pockets.get(index);
    }
}
