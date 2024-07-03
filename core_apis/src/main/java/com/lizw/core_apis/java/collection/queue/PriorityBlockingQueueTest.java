package com.lizw.core_apis.java.collection.queue;

import java.util.Iterator;
import java.util.concurrent.PriorityBlockingQueue;

/**
 *
 */
public class PriorityBlockingQueueTest {
    public static void main(String[] args) {
        /**
         * 1、PriorityBlockingQueue(int capacity)
         * 使用指定的初始容量创建一个 PriorityBlockingQueue，并根据元素的自然顺序对其元素进行排序。
         * 2、PriorityBlockingQueue()
         * 用默认的初始容量 (11) 创建一个 PriorityBlockingQueue，并根据元素的自然顺序对其元素进行排序。
         */
        PriorityBlockingQueue<Integer> priorityBlockingQueue = new PriorityBlockingQueue<>(5);
        //1、add(E e)：将指定元素插入此优先级队列。
        boolean addBoolean = priorityBlockingQueue.add(15);
        boolean addBoolean1 = priorityBlockingQueue.add(11);
        boolean addBoolean2 = priorityBlockingQueue.add(9);
        boolean addBoolean3 = priorityBlockingQueue.add(20);
        for (Integer result : priorityBlockingQueue) {
            System.out.println("for result: " + result);
        }
        System.out.println("result: " + priorityBlockingQueue.poll());
        System.out.println("result: " + priorityBlockingQueue.poll());
        System.out.println("result: " + priorityBlockingQueue.poll());
        System.out.println("result: " + priorityBlockingQueue.poll());

        //2、peek()：获取但不移除此队列的头；如果此队列为空，则返回 null。
        Integer peekResult = priorityBlockingQueue.peek();
        System.out.println("peekResult: " + peekResult);

        /**
         * poll()：获取并移除此队列的头，如果此队列为空，则返回 null。
         * poll(long timeout, TimeUnit unit)：获取并移除此队列的头部，在指定的等待时间前等待可用的元素（如果有必要）。
         */
        Integer pollResult = priorityBlockingQueue.poll();
        System.out.println("pollResult: " + pollResult);
        Integer afterPollResult = priorityBlockingQueue.poll();
        System.out.println("poll后的结果: " + afterPollResult);

        //4、put(E e)：将指定元素插入此优先级队列。
        try {
            priorityBlockingQueue.put(6);
            System.out.println("put后：" + priorityBlockingQueue.peek());
        } catch (Exception e) {
            e.printStackTrace();
        }

        /**
         * offer(E e) ：将指定元素插入此优先级队列，返回值为Boolean。
         * offer(E e, long timeout, TimeUnit unit)：将指定元素插入此优先级队列,返回值为Boolean。
         */
        boolean offerBoolean = priorityBlockingQueue.offer(7);
        System.out.println("是否成功offer: " + offerBoolean);

        //6、remove(Object o): 从此队列中移除指定元素的单个实例（如果存在）。返回值为Boolean
        boolean remove8Boolean = priorityBlockingQueue.remove(8);
        System.out.println("移除8是否成功：" + remove8Boolean);
        boolean remove7Boolean = priorityBlockingQueue.remove(7);
        System.out.println("移除7是否成功：" + remove7Boolean);

        //7、size()：返回此 collection 中的元素数。
        int sizeQueue = priorityBlockingQueue.size();
        System.out.println("arrayBlockingQueue.size = " + sizeQueue);

        //8、contains(Object o) 如果此队列包含指定的元素，则返回 true。
        boolean containsBoolean = priorityBlockingQueue.contains(9);
        System.out.println("arrayBlockingQueue是否包含9: " + containsBoolean);

        //9、iterator(): 返回在此队列元素上进行迭代的迭代器，返回值为Iterator<E>
        Iterator<Integer> iterator = priorityBlockingQueue.iterator();
        while (iterator.hasNext()) {
            Integer result = iterator.next();
            System.out.println("iterator的结果result: " + result);
        }
        // 用 for 循环遍历，和上边的效果一样
        for (Integer result : priorityBlockingQueue) {
            System.out.println("iterator的结果result: " + result);
        }

        //10、take()：获取并移除此队列的头部，在元素变得可用之前一直等待（如果有必要）。
        try {
            Integer takeResult = priorityBlockingQueue.take();
            System.out.println("takeResult: " + takeResult);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //11、clear()：完全移除此队列中的所有元素。
        priorityBlockingQueue.clear();
        System.out.println("看一下是否还存在元素：" + priorityBlockingQueue.peek());
    }
}