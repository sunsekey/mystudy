package com.sunsekey.algorithm.common;

public class OneWayLink<T> {
    private T value;
    private OneWayLink<T> next;

    public OneWayLink(T value, OneWayLink<T> next) {
        this.value = value;
        this.next = next;
    }
    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public OneWayLink<T> getNext() {
        return next;
    }

    public void setNext(OneWayLink<T> next) {
        this.next = next;
    }

    public static OneWayLink<Integer> initIntMockData(){
        OneWayLink<Integer> head = new OneWayLink<>(10
                , new OneWayLink<>(5
                , new OneWayLink<>(7
                , new OneWayLink<>(14
                , new OneWayLink<>(11, null)))));
        return head;
    }

    public static <T> void printLink(OneWayLink<T> headNode){
        OneWayLink<T> curNode = headNode;
        while(curNode != null){
            System.out.println(curNode.getValue());
            curNode = curNode.getNext();
        }
    }
}
