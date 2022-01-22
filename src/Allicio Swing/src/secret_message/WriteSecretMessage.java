package secret_message;

/**
 * Class which presents how write an instance of a SecretMessage object thanks the serialization process
 *
 */
public class WriteSecretMessage
{
    /**
     * Main procedure<br>
     * usage : WriteSecretMessage fileName value1 value2 ... <br>
     * fileName is the path to the file that will be created<br>
     * value1, value2, ... are the messages to put in the objet Secret Message
     *
     * @param args The command line parameters
     */
    public static void main(String[] args)
    {
	if(args.length<2)
	    {
		System.out.println("Usage : WriteSecretMessage fileName value1 value2 ... ");
		System.out.println("        into the file fileName, save all the remaining ");
		System.out.println("        values of the command line");
		return;
	    }

	
	SecretMessage secret=new SecretMessage();
	
	for(int i=1;i<args.length;i++)
	    {
		secret.addMessage(args[i]);
	    }

	System.out.println(secret);
	
	java.io.ObjectOutputStream out=null;

	try
	    {
		out=new java.io.ObjectOutputStream(new java.io.FileOutputStream(args[0]));
	    }
	catch(java.io.IOException ioe)
	    {
		System.out.println("An I/O error occurs while writing stream header");
		System.out.println(ioe);
		System.exit(10);
	    }
	catch(java.lang.SecurityException se)
	    {
		System.out.println("An untrusted subclass illegally overrides security-sensitive methods");
		System.out.println(se);
		System.exit(20);
	    }
	catch(java.lang.NullPointerException npe)
	    {
		System.out.println("The FileOutputStream is null");
		System.out.println(npe);
		System.exit(30);
	    }
	
	
	try
	    {
		out.writeObject(secret);
	    }
	catch(java.io.InvalidClassException ice)
	    {
		System.out.println("Something is wrong with "+secret.getClass().getName()+" class during serialization");
		System.out.println(ice);
		System.exit(40);
	    }
	catch(java.io.NotSerializableException nse)
	    {
		System.out.println("Some object to be serialized does not implement the java.io.Serializable interface");
		System.out.println(nse);
		System.exit(50);
	    }
	catch(java.io.IOException ioe)
	    {
		System.out.println("Any exception thrown by the underlying OutputStream.");
		System.out.println(ioe);
		System.exit(60);
	    }
	
	finally
	    {
		try
		    {
			out.flush();
			out.close();
		    }
		catch(java.io.IOException ioe)
		    {
			System.out.println("There is an I/O error during flush or close process.");
			System.out.println(ioe);
			System.exit(70);
		    }
		
	    }
    }
}
