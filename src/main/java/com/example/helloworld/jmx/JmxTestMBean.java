package com.example.helloworld.jmx;

public interface JmxTestMBean {
    public void sayHello();
    public void addConversations(int newConversations);
    public int getConversationCount();
    public void setConversationCount(int conversationCount);
}
