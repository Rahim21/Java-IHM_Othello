package secret_message;

/**
 * Class which saves several messages and implements java.io.Serializable 
 *
 */
public class SecretMessage implements java.io.Serializable
{
    /** Indicate the current version of the class*/
    private static final long serialVersionUID = 11111111L;

    /** A random int value in [0,1024[*/
    private int specialValue;

    /** An array of String for the user messages*/
    private java.util.ArrayList<String> messages;

    /** A random int value in [0,1024[ which cannot be serialized due to <b>transient</b> keyword */ 
    private transient int oddValue; 

    /**
     * Classical initialization Constructor 
     */
    public SecretMessage()
    {
	messages = new java.util.ArrayList<String>();
	specialValue=(int)(Math.random()*1024);
	oddValue=(int)(Math.random()*1024);
    }

    /**
     * Procedure to save a new message
     *
     * @param message The new message to save
     */
    public void addMessage(String message)
    {
	messages.add(message);
    }

    /**
     * Specialized version of toString that prints the value of all the internal attributes.
     *
     * @return String version of the SecretMessage Object.
     */
    @Override
    public String toString()
    {
	String secretMessage="=============================";
	secretMessage+="\nThe special value is "+specialValue;
	secretMessage+="\nThe odd value is "+oddValue;
	secretMessage+="\nThe secret message is ";

	java.util.Iterator<String> it=messages.iterator();
	while(it.hasNext())
	    {
		secretMessage+="\n\t"+it.next();
	    }

	secretMessage+="\n=============================";

	return secretMessage;
    }
}
