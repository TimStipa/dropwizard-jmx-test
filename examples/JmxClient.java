import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import java.io.IOException;
import java.net.MalformedURLException;

public class JmxClient
{
    private static final String JMX_RMI_FORMAT = "service:jmx:rmi://%s/jndi/rmi://%s/jmxrmi";
    private final JMXServiceURL jmxServiceURL;
    private JMXConnector jmxConnector;

    public JmxClient() throws MalformedURLException
    {
        this("localhost", 1088);
    }

    public JmxClient(String host, int port) throws MalformedURLException
    {
        if (host == null)
        {
            throw new NullPointerException("host cannot be null.");
        }

        String hostAndPort = String.format("%s:%d", host, port);
        jmxServiceURL = new JMXServiceURL(String.format(JMX_RMI_FORMAT, hostAndPort, hostAndPort));
    }

    public static Object invokeMBean(String objectName,
                                     String operationName,
                                     Object[] params,
                                     String[] signature) throws IOException
    {
        Object retVal = null;
        JmxClient client = new JmxClient();
        try
        {
            JMXConnector connector = client.beginJmxConnection();
            MBeanServerConnection mbs = connector.getMBeanServerConnection();
            retVal = mbs.invoke(new ObjectName(objectName), operationName, params, signature);
        }
        catch (Exception e)
        {
            System.out.println("Caught an exception invoking mBean: " + e);
        }
        finally
        {
            client.endJmxConnection();
        }

        return retVal;
    }

    public static Object getAttribute(String objectName,
                                     String attributeName) throws IOException
    {
        Object retVal = null;
        JmxClient client = new JmxClient();
        try
        {
            JMXConnector connector = client.beginJmxConnection();
            MBeanServerConnection mbs = connector.getMBeanServerConnection();
            retVal = mbs.getAttribute(new ObjectName(objectName), attributeName);
        }
        catch (Exception e)
        {
            System.out.println("Caught and exception getting attribute: " + e);
        }
        finally
        {
            client.endJmxConnection();
        }

        return retVal;
    }


    private JMXConnector beginJmxConnection()
    {
        try
        {
            jmxConnector = getJmxConnector();
        }
        catch (IOException ioe)
        {
            endJmxConnection();
        }

        return jmxConnector;
    }

    private void endJmxConnection()
    {
        try
        {
            if (jmxConnector != null)
            {
                jmxConnector.close();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private JMXConnector getJmxConnector() throws IOException
    {
        return JMXConnectorFactory.connect(jmxServiceURL);
    }
}
