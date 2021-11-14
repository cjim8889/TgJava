package org.example;

import it.tdlight.jni.TdApi;

public class MessageProcessor {

    public void process(TdApi.Message msg) {
        var clazz = msg.content.getClass();
        if (clazz == TdApi.MessageText.class) {
            processText(msg);
        } else if (clazz == TdApi.MessagePhoto.class) {
            processPhoto(msg);
        }
    }

    public void processPhoto(TdApi.Message msg) {
        System.out.println("It's a photo " + msg.sender);
    }

    public TextEntry processText(TdApi.Message msg) {
        var content = (TdApi.MessageText) msg.content;

        System.out.println(content.text.text);

        return new TextEntry();
    }
}
