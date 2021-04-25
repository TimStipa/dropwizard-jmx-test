package com.example.helloworld.jmx;

/**
 * Interface for JMX Test MBean. This interface allows users to track and
 * update statistics about conversations.
 */
public interface JmxTestMBean
{
    /**
     * Initiates a conversation.
     */
    public void sayHello();

    /**
     * Adds the provided number of conversations to the current statistics.
     *
     * @param newConversations The number of conversations to add.
     */
    public void addConversations(int newConversations);

    /**
     * Gets the conversation count.
     *
     * @return The number of conversations that have occurred.
     */
    public int getConversationCount();

    /**
     * Sets the conversation count. This can be used to reset the statistics
     * to the provide value.
     *
     * @param conversationCount The new conversation count to use.
     */
    public void setConversationCount(int conversationCount);
}
