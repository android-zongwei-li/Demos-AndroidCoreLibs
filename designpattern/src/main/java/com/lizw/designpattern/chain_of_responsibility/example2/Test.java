package com.lizw.designpattern.chain_of_responsibility.example2;

public class Test {
    public static void main(String[] args) {
        Handler2 c1 = new Child01Handler2();
        Handler2 c2 = new Child02Handler2(c1);

        Request request = new Request("child01");
        c2.dispatch(request);
    }
}
