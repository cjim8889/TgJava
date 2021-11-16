package org.example;

import it.tdlight.jni.TdApi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class MessageProcessor {

    private final List<TdApi.Message> messageList;

    public MessageProcessor() {
        messageList = Collections.synchronizedList(new ArrayList<>());
    }

    public void process(TdApi.Message msg) {
        var clazz = msg.content.getClass();
        if (msg.content instanceof TdApi.MessageText) {
            processText(msg);
        }
//        } else if (msg.content instanceof TdApi.MessagePhoto) {
//            processPhoto(msg);
//        }
    }

    public void processPhoto(TdApi.Message msg) {
//        var content = (TdApi.MessagePhoto) msg.content;
//
////        System.out.println("It's a photo " + content.caption);
    }

    public void processText(TdApi.Message msg) {
//        var content = (TdApi.MessageText) msg.content;
        messageList.add(msg);
        System.out.println("We have received " + messageList.size() + " of messages");
//
//
////        TdApi.Send
//        System.out.println(content.text.text);
//        System.out.println(msg.sender);
//        return new TextEntry();
    }
}
