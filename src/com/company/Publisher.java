package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 공급자 클래스
 * @author 박경호
 */
class Publisher extends Thread{
    private final String name; // 공급자 이름
    private final int publishTime; // 공급 인터벌 시간
    private List<String> pockets; // 공급자가 소유한 아이템
    private int index; // 아이템 인덱스
    private boolean isEnd; // 공급 완료
    private Message message; // 테스트 메시지
    HoQueue<String> hoQueue;

    public Publisher(HoQueue hoQueue, String name, int publishTime){
        this.pockets = new ArrayList<>();
        message = new Message();
        pockets = Arrays.asList(message.getTestMessage());
        index = pockets.size()-1;
        this.hoQueue = hoQueue;
        this.name = name;
        this.publishTime = publishTime;
        this.isEnd = false;

        hoQueue.addPublishList(this);
    }

    public boolean isEnd(){
        return isEnd;
    }

    @Override
    public void run() {
        while(index>=0 && !hoQueue.isPublishEnd()){
            try{
                Thread.sleep(publishTime);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            hoQueue.enqueue(pockets.get(index),name);
            index--;
        }
        if(index==-1){
            isEnd = true;
        }

    }
}
