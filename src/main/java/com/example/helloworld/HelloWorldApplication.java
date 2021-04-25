package com.example.helloworld;

import com.example.helloworld.jmx.JmxTest;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.*;
import java.lang.management.ManagementFactory;

public class HelloWorldApplication extends Application<HelloWorldConfiguration> {
    private static Logger LOGGER = LoggerFactory.getLogger(HelloWorldApplication.class);

    public static void main(String[] args) throws Exception {
        new HelloWorldApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<HelloWorldConfiguration> bootstrap) {
        // nothing to do yet
    }

    @Override
    public void run(HelloWorldConfiguration configuration, Environment environment)
        throws MalformedObjectNameException, NotCompliantMBeanException,
            InstanceAlreadyExistsException, MBeanRegistrationException {
        LOGGER.warn("Starting Hello World Application...");

        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("test:type=Test");
        mbs.registerMBean(new JmxTest(configuration), name);

        LOGGER.warn("Registered test bean!");
    }
}