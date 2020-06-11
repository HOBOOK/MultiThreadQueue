package com.company;

/**
 * 테스트 메시지
 * @author 박경호
 */
public class Message {
    public Message(){}
    private String[] getTestMessage000(){
        int count = (int)(Math.random() * 30);
        String[] arr = new String[count];
        for(int i = 0; i < count; i++){
            arr[i] = String.valueOf(i);
        }
        return arr;
    }
//    private String[] getTestMessage001(){
//        String[] arr = {"다","우","기","술"};
//        return arr;
//    }
//    private String[] getTestMessage002(){
//        String[] arr = {"대","한","민","국"};
//        return arr;
//    }

    public String[] getTestMessage(){
        return getTestMessage000();
//        int random = (int)(Math.random() * 3);
//        switch (random){
//            case 0:
//                return getTestMessage001();
//            case 1:
//                return getTestMessage002();
//            default:
//                return getTestMessage000();
//        }
    }
}
