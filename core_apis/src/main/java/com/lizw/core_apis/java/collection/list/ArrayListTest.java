package com.lizw.core_apis.java.collection.list;

import android.os.Build;

import java.util.ArrayList;
import java.util.function.Predicate;

/**
 *
 */
public class ArrayListTest {
    public static void main(String[] args) {
        ArrayList<Integer> numbers = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            numbers.removeIf(new Predicate<Number>() {
                @Override
                public boolean test(Number number) {
                    return false;
                }
            });
        }
    }
}
