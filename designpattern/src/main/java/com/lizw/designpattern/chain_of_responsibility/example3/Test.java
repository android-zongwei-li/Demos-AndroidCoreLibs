package com.lizw.designpattern.chain_of_responsibility.example3;

public class Test {
    public static void main(String[] args) {
        Handler3 c1 = new Child01Handler3();
        Handler3 c2 = new Child02Handler3(c1);

        Request request = new Request("02");
        boolean result = c2.dispatch(request);
        System.out.println(result);
    }
}
