package org.example;

import it.tdlight.jni.TdApi;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DataProcessor implements Processor{

    private final ExecutorService executorService;
    private final MessageProcessor messageProcessor;

    public DataProcessor() {
        executorService = Executors.newFixedThreadPool(5);
        messageProcessor = AppFactory.getMessageProcessor();

        Runtime.getRuntime().addShutdownHook(new Thread(executorService::shutdownNow));
    }

    @Override
    public void process(TdApi.Message message) {
        executorService.execute(() -> {
            messageProcessor.process(message);
        });
    }
}
