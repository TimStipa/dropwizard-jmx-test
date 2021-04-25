package com.example.helloworld.jmx;

import com.example.helloworld.JmxTestConfiguration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Test class to verify JMX MBean behavior in Dropwizard.
 */
public class JmxTest implements JmxTestMBean
{
    /** The logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(JmxTest.class);

    /** The values for the messages, pulled from the application configuration. */
    private final String template;
    private final String name;

    /** The counter to track conversations and interactions with the MBean. */
    private final AtomicInteger conversations = new AtomicInteger();

    /**
     * Creates a JMX Test Bean.
     *
     * @param configuration The configuration to use for initial setup details.
     */
    public JmxTest(JmxTestConfiguration configuration)
    {
        if (configuration == null)
        {
            throw new NullPointerException("configuration cannot be null.");
        }

        this.template = configuration.getTemplate();
        this.name = configuration.getDefaultName();
        conversations.set(configuration.getInitialConversationCount());
    }

    @Override
    public void sayHello()
    {
        LOGGER.debug("Entering sayHello()");

        conversations.getAndIncrement();
        LOGGER.warn(String.format(template, name, conversations.get()));
    }

    @Override
    public void addConversations(int newConversations)
    {
        LOGGER.debug("Entering addConversation(): newConversations = {}", newConversations);

        conversations.getAndAdd(newConversations);
    }

    @Override
    public int getConversationCount()
    {
        LOGGER.debug("Entering getConversationCount()");

        return conversations.get();
    }

    @Override
    public void setConversationCount(int conversationsCount)
    {
        LOGGER.debug("Entering setConversationCount(): conversationCount = {}", conversationsCount);

        conversations.set(conversationsCount);
    }
}
