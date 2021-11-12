package org.example;

import it.tdlight.jni.TdApi;

public interface Processor {
    public void process(TdApi.Message message);
}
