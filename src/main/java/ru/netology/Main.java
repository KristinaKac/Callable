package ru.netology;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int result = 0;
        List<Callable<Integer>> tasks = new ArrayList<>();
        Callable<Integer> myCallable_1 = new MyCallable("task_1");
        Callable<Integer> myCallable_2 = new MyCallable("task_2");
        Callable<Integer> myCallable_3 = new MyCallable("task_3");
        Callable<Integer> myCallable_4 = new MyCallable("task_4");
        tasks.add(myCallable_1);
        tasks.add(myCallable_2);
        tasks.add(myCallable_3);
        tasks.add(myCallable_4);


        final ExecutorService threadPool = Executors.newFixedThreadPool(3);

        List<Future<Integer>> list = new ArrayList<>();
        list.add(threadPool.submit(myCallable_1));
        list.add(threadPool.submit(myCallable_2));
        list.add(threadPool.submit(myCallable_3));
        list.add(threadPool.submit(myCallable_4));

        Integer invokeAny = threadPool.invokeAny(tasks);
        System.out.println("Результат задачи с наименьшей задержкой: " + invokeAny);


        for (Future<Integer> fut : list) {
            try {
                int res = Integer.parseInt(fut.get().toString());
                result = result + res;
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Количество выведенных сообщений: " + result);
        threadPool.shutdown();
    }

    public static class MyCallable implements Callable<Integer> {
        int amount = 5;
        String name;

        MyCallable(String task) {
            name = task;
        }

        @Override
        public Integer call() throws Exception {
            int j = 0;
            for (int i = 0; i < amount; i++, j++) {
                System.out.println("Всем привет!" + name);
                Thread.sleep(2000);
            }
            return j;
        }
    }
}