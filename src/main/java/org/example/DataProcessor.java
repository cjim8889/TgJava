package org.example;

import it.tdlight.jni.TdApi;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DataProcessor implements Processor{

    private ExecutorService executorService;
    private List<TdApi.Message> stacks;
    private final Object lock;

    public DataProcessor() {
        executorService = Executors.newFixedThreadPool(5);
        stacks = new ArrayList<>();
        lock = new Object();


        for (int i = 0; i < 5; i ++) {
            executorService.execute(() -> {
                while (true) {
                    synchronized (lock) {
                        while (stacks.size() == 0) {
                            try {
                                lock.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                        var msg = stacks.remove(stacks.size() - 1);

                        if (msg.content.getClass() == TdApi.MessageText.class) {
                            var messageText = (TdApi.MessageText) msg.content;

                            System.out.println(messageText.text);
                        }
                    }
                }
            });
        }
    }

    @Override
    public void process(TdApi.Message message) {
        synchronized (lock) {
            stacks.add(message);
            lock.notify();
        }
    }
}
