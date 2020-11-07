package com.qiyue.mq.core.producer.broker;

import com.qiyue.mq.rabbit.api.Message;

import java.util.ArrayList;
import java.util.List;

public class MessageHolder {

    private List<Message> messages = new ArrayList<>();


    private static ThreadLocal<MessageHolder> holder = ThreadLocal.withInitial(MessageHolder::new);

    public static boolean add(Message message) {
        return holder.get().messages.add(message);
    }

    public static List<Message> clear() {
        final ArrayList<Message> messages = new ArrayList<>(holder.get().messages);
        holder.remove();
        return messages;
    }
}
