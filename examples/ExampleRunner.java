public class ExampleRunner
{
    private static void invokeMBean(String mBean,
                                    String method)
    {
        invokeMBean(mBean, method, null, null);
    }

    private static void invokeMBean(String mBean,
                                    String method,
                                    Object[] params,
                                    String[] signature)
    {
        System.out.println("Entering invokeMBean(): mBean = " + mBean + ", method = " + method +
                ", params = " + params + ", signature = " + signature);
        try
        {
            Object response = JmxClient.invokeMBean(
                    mBean,
                    method,
                    params,
                    signature);
            System.out.println("Response: " + response);
        }
        catch (Exception e)
        {
            System.out.println("Caught an exception invoking mBean: " + e);
        }
    }

    private static void getAttribute(String mBean, String method)
    {
        System.out.println("Entering getAttribute(): mBean = " + mBean + ", method = " + method);

        try
        {
            Object response = JmxClient.getAttribute(
                    mBean,
                    method);
            System.out.println("Response: " + response);
        }
        catch (Exception e)
        {
            System.out.println("Caught an exception getting attributes: " + e);
        }
    }

    /**
     * Test runner to simulate calls to the JmxTestApplication mBean.
     */
    public static final void main(String[] args)
    {
        invokeMBean(
                "test:type=Test",
                "sayHello");

        getAttribute(
                "test:type=Test",
                "ConversationCount");

        invokeMBean(
                "test:type=Test",
                "sayHello");

        getAttribute(
                "test:type=Test",
                "ConversationCount");

        invokeMBean(
                "test:type=Test",
                "addConversations",
                new Object[] { 5 },
                new String[] {"int"}
        );

        getAttribute(
                "test:type=Test",
                "ConversationCount");
    }
}
