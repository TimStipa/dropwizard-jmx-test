package com.example.helloworld;

import com.codahale.metrics.Gauge;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.jmx.JmxReporter;
import com.codahale.metrics.jvm.JmxAttributeGauge;
import com.codahale.metrics.servlets.MetricsServlet;
import com.example.helloworld.jmx.JmxTest;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

import java.lang.management.ManagementFactory;

public class JmxTestApplication extends Application<JmxTestConfiguration>
{
    /** The logger for this class. */
    private static Logger LOGGER = LoggerFactory.getLogger(JmxTestApplication.class);

    public static void main(String[] args) throws Exception
    {
        new JmxTestApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<JmxTestConfiguration> bootstrap)
    {
        // nothing to do yet
    }

    @Override
    public void run(JmxTestConfiguration configuration, Environment environment)
        throws MalformedObjectNameException, NotCompliantMBeanException,
            InstanceAlreadyExistsException, MBeanRegistrationException
    {
        LOGGER.warn("Starting Hello World Application...");

        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("test:type=Test");
        mbs.registerMBean(new JmxTest(configuration), name);

        MetricRegistry registry = environment.metrics();
        registry.register(MetricRegistry.name("test-name", "count"),
                new JmxAttributeGauge(new ObjectName("test:type=Test"), "ConversationCount"));
        registry.register(MetricRegistry.name("test-two", "hardCodedCount"),
                (Gauge<Integer>) () -> 1);

        JmxReporter reporter = JmxReporter.forRegistry(registry).build();
        reporter.start();

        LOGGER.warn("Registered test bean!!");
    }
}