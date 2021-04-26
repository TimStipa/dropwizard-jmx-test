import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Optional;

public class JmxClient
{
    private static final String JMX_RMI_FORMAT = "service:jmx:rmi://%s/jndi/rmi://%s/jmxrmi";
    private final JMXServiceURL jmxServiceURL;
    private JMXConnector jmxConnector;

    public JmxClient() throws MalformedURLException
    {
        this(System.getProperty("jmxclient.test.host","localhost"),
                Integer.getInteger("jmxclient.test.port", 1088));
    }

    public JmxClient(String host, int port) throws MalformedURLException
    {
        if (host == null)
        {
            throw new NullPointerException("host cannot be null.");
        }

        String hostAndPort = String.format("%s:%d", host, port);
        jmxServiceURL = new JMXServiceURL(String.format(JMX_RMI_FORMAT, hostAndPort, hostAndPort));
        System.out.println("JMX Service URL is: " + jmxServiceURL);
    }

    public static Object invokeMBean(String objectName,
                                     String operationName,
                                     Object[] params,
                                     String[] signature) throws IOException
    {
        Object retVal = null;
        JmxClient client = new JmxClient();
        Optional<JMXConnector> maybeConnector = client.getJmxConnector();

        if (maybeConnector.isPresent())
        {
            try (JMXConnector connector = maybeConnector.get())
            {
                MBeanServerConnection mbs = connector.getMBeanServerConnection();
                retVal = mbs.invoke(new ObjectName(objectName), operationName, params, signature);
            }
            catch (Exception e)
            {
                System.out.println("Caught an exception invoking mBean: " + e);
            }
        }

        return retVal;
    }

    public static Object getAttribute(String objectName,
                                     String attributeName) throws IOException
    {
        Object retVal = null;
        JmxClient client = new JmxClient();
        Optional<JMXConnector> maybeConnector = client.getJmxConnector();

        if (maybeConnector.isPresent())
        {
            try (JMXConnector connector = maybeConnector.get())
            {
                MBeanServerConnection mbs = connector.getMBeanServerConnection();
                retVal = mbs.getAttribute(new ObjectName(objectName), attributeName);
            }
            catch (Exception e)
            {
                System.out.println("Caught and exception getting attribute: " + e);
            }
        }

        return retVal;
    }

    private Optional<JMXConnector> getJmxConnector()
    {
        JMXConnector connector = null;
        try
        {
            connector = JMXConnectorFactory.connect(jmxServiceURL);
        }
        catch (IOException ioe)
        {
            System.out.println("Encountered an IO Exception creating JMX Connector: " + ioe);
        }

        return Optional.ofNullable(connector);
    }
}
