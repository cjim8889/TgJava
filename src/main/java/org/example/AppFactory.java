package org.example;


import it.tdlight.client.APIToken;
import it.tdlight.jni.TdApi;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class AppFactory {

    private static final ArrayList<DataProcessor> processors = new ArrayList<>();
    private static final Object messageProcessorLock = new Object();
    private static MessageProcessor messageProcessor;

    public static DataProcessor createProcessor() {
        var processor = new DataProcessor();


        processors.add(processor);
        return processor;
    }

    public static MessageProcessor getMessageProcessor() {
        synchronized (messageProcessorLock) {
            if (messageProcessor == null) {
                messageProcessor = new MessageProcessor();
            }

            return messageProcessor;
        }
    }

    private static class _APIToken implements Serializable {
        public int ApiID;
        public String ApiHash;
        private static final long serialVersionUID = 6529203098292757690L;

        public _APIToken(APIToken token) {
            ApiID = token.getApiID();
            ApiHash = token.getApiHash();
        }

        public APIToken toAPIToken() {
            return new APIToken(ApiID, ApiHash);
        }
    }

    public static void dumpAPIToken(APIToken token, String filename) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(Paths.get(filename)))) {
            out.writeObject(new _APIToken(token));
        }
    }

    public static APIToken loadAPIToken(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(Files.newInputStream(Paths.get(filename)))){
            var token = (_APIToken) in.readObject();
            return token.toAPIToken();
        }
    }

    public static void process(TdApi.Message message) {
        for (DataProcessor processor : AppFactory.getProcessors()) {
            processor.process(message);
        }
    }

    public static DataProcessor[] getProcessors() {
        return processors.toArray(DataProcessor[]::new);
    }
}
