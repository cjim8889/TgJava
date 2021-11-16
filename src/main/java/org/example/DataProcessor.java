package org.example;

import it.tdlight.jni.TdApi;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DataProcessor implements Processor{

    private final ExecutorService executorService;
    private final List<MessageProcessor> messageProcessorList;

    public DataProcessor() {
        executorService = Executors.newFixedThreadPool(5);
        messageProcessorList = new ArrayList<>();

        messageProcessorList.add(new MessageProcessor());

        Runtime.getRuntime().addShutdownHook(new Thread(executorService::shutdownNow));
    }

    @Override
    public void process(TdApi.Message message) {
        for (var messageProcessor : messageProcessorList) {
            executorService.execute(() -> {
                messageProcessor.process(message);
            });
        }
    }
}
