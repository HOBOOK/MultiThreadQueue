package com.company;

import java.util.ArrayList;
import java.util.List;

/**
 * 멀티 쓰레드 큐 구현을 위한 큐 클래스
 * @author 박경호
 */
class HoQueue<T>{
    private final T[] queue; // 큐에 담을 제네릭 배열
    private int front; // 포인터
    private int rear; // 꼬리 위치
    public int maxSize; // 큐 사이즈

    private final int totalProcessingCount; // 큐가 최대로 처리할 수 있는 횟수
    private int publishCount; // 공급 횟수
    private int consumeCount; // 소비 횟수

    private boolean isEndProcess;

    List<Publisher> publisherList; // 큐의 공급자 목록
    List<Consumer> consumerList; // 큐의 소비자 목록
    
    public HoQueue(int maxSize, int totalProcessingCount){
        this.isEndProcess = false;
        this.queue = (T[])new Object[maxSize];
        this.maxSize = maxSize;
        this.front = -1;
        this.rear = -1;
        this.publishCount = 0;
        this.consumeCount = 0;
        this.totalProcessingCount = totalProcessingCount;
        publisherList = new ArrayList<Publisher>();
        consumerList = new ArrayList<Consumer>();
    }
    
    // 멀티 쓰레드 블로킹 큐 삽입
    public synchronized void enqueue(T obj, String name){
        while(isFull()){
            try{
                wait();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        if(isPublishEnd()){
            notifyAll();
            return;
        }
        queue[++rear]=obj;
        publishCount++;
        printQueue(name + "님이 공급(+)  "," 공급:"+publishCount);
        notifyAll();
    }
    
    // 멀티 쓰레드 블로킹 큐 제거
    public synchronized T dequeue(String name){
        while(isEmpty() && !isConsumeEnd()){
            try{
                wait();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        if(isConsumeEnd()){
            notifyAll();
            printConsumerResult();
            return null;
        }
        ++front;
        T temp = queue[front];
        queue[front] = null;
        consumeCount++;
        if(isEmpty()){
            front=-1;
            rear=-1;
        }
        printQueue(name + "님이 소비(-)  "," 소비:"+consumeCount);
        notifyAll();
        return temp;
    }

    public boolean isFull(){
        return (rear == maxSize-1);
    }

    public boolean isEmpty(){
        return (rear == front);
    }

    // 공급자 추가
    public void addPublishList(Publisher publisher){
        publisherList.add(publisher);
    }

    // 소비자 추가
    public void addConsumerList(Consumer consumer){
        consumerList.add(consumer);
    }

    // 공급 종료 조건
    public synchronized boolean isPublishEnd(){
        if(publishCount>=totalProcessingCount)
            return true;
        else{
            for(Publisher p : publisherList){
                if(!p.isEnd())
                    return false;
            }
            return true;
        }
    }
    
    // 소비 종료 조건
    public synchronized boolean isConsumeEnd(){
        if((isPublishEnd() && publishCount<=consumeCount)){
            notifyAll();
            return true;
        }
        return false;
    }

    // 현재 큐 상황 출력
    public void printQueue(String prefix, String suffix){
        System.out.print(prefix +"[");
        for(int i = rear; i >front && i >= 0; i--){
            if(i!=rear){
                System.out.print(",");
            }
            System.out.print(queue[i].toString());
        }
        System.out.print("] " + suffix);
        System.out.println();
    }

    // 소비자 결과 상황 출력
    public void printConsumerResult(){
        if(isEndProcess)
            return;
        for(Consumer consumer : consumerList){
            System.out.println();
            System.out.print(consumer.getConsumerName()+"님은 총:" +consumer.getPocketsSize()+ "개 [");
            for(int i = 0; i < consumer.getPocketsSize(); i++){
                if(i!=0)
                    System.out.print(",");
                System.out.print(consumer.getPocketsItem(i));
            }
            System.out.print("]");
            System.out.println();
        }
        isEndProcess = true;
    }
}
