package secret_message;

/**
 * Class which presents how read an instance of a SecretMessage object from a binary file thank to the serialization process
 *
 */
public class ReadSecretMessage
{
    /**
     * Main procedure<br>
     * usage : ReadSecretMessage fileName<br>
     * fileName must contain the path to the message encoded by WriteSecretMessage
     *
     * @param args The command line parameters, only one parameter is needed.
     */
    public static void main(String[] args)
    {
	if(args.length!=1)
	    {
		System.out.println("Usage : ReadSecretMessage fileName ");
		System.out.println("        fileName must contain the message encoded by WriteSecretMessage");
		System.exit(10);
	    }

	java.io.ObjectInputStream in=null;
	SecretMessage message=null;
	
	try
	    {
		in=new java.io.ObjectInputStream(new java.io.FileInputStream(args[0]));
		message=(SecretMessage)in.readObject();
		System.out.println(message);

	    }
	
	catch(java.io.StreamCorruptedException sce)
	    {
		System.out.println("The stream header is incorrect during the opening of the stream or the object reading.");
		System.out.println(sce);
		System.exit(20);
	    }

	catch(java.lang.SecurityException se)
	    {
		System.out.println("An untrusted subclass illegally overrides security-sensitive methods.");
		System.out.println(se);
		System.exit(30);
	    }

	catch(java.lang.NullPointerException npe)
	    {
		System.out.println("The FileInputStream is null.");
		System.out.println(npe);
		System.exit(40);
	    }

	catch(java.lang.ClassNotFoundException cnfe)
	    {
		System.out.println("Class "+message.getClass().getName()+" of the serialized object cannot be found.");
		System.out.println(cnfe);
		System.exit(50);
	    }

	catch(java.io.InvalidClassException ice)
	    {
		System.out.println("Something is wrong with a class used by serialization.");
		System.out.println(ice);
		System.exit(60);
	    }

	catch(java.io.OptionalDataException ode)
	    {
		System.out.println("Primitive data was found in the stream instead of objects.");
		System.out.println(ode);
		System.exit(70);
	    }

	catch(java.io.IOException ioe)
	    {
		System.out.println("an I/O error occurs while reading stream header.");
		System.out.println(ioe);
		System.exit(80);
	    }
	
	finally
	    {
		if(in != null)
		    {
			try
			    {
				in.close();
			    }
			catch(java.io.IOException ioe)
			    {
				System.out.println("There is an I/O error ...");
				System.out.println(ioe);
				System.exit(90);
			    }
		    }
	    }
    }
}
