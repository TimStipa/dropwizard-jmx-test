package com.example.helloworld;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;

/**
 * Configuration for JMX Test application.
 */
public class JmxTestConfiguration extends Configuration
{
    @NotEmpty
    private String template;

    @NotEmpty
    private String defaultName = "Stranger";

    @PositiveOrZero
    private int initialConversationCount;

    @JsonProperty
    public String getTemplate() {
        return template;
    }

    @JsonProperty
    public void setTemplate(String template)
    {
        this.template = template;
    }

    @JsonProperty
    public String getDefaultName()
    {
        return defaultName;
    }

    @JsonProperty
    public void setDefaultName(String name)
    {
        this.defaultName = name;
    }

    @JsonProperty
    public int getInitialConversationCount()
    {
        return initialConversationCount;
    }

    @JsonProperty
    public void setInitialConversationCount(int initialConversationCount)
    {
        if (initialConversationCount < 0)
        {
            throw new IllegalArgumentException("initialConversationCount cannot be less than zero.");
        }

        this.initialConversationCount = initialConversationCount;
    }
}
