package com.lizw.designpattern.chain_of_responsibility.example1;

public class Test1 {
    public static void main(String[] args) {
        Handler c1 = new ChildHandler01();
        Handler c2 = new ChildHandler02();
        c1.setNext(c2);
        // c2.setNext(c1);

        c1.handleRequest("ChildHandler02");
    }
}
