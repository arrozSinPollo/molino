package dal;

import java.io.PrintStream;
import java.io.PrintWriter;

public class DalException extends Exception {

	Exception e;
	
	public DalException(Exception concreteException)
	{
		e=concreteException;
	}

	public String getMessage() {
		return e.getMessage();
	}

	public void printStackTrace() {
		e.printStackTrace();
	}

	public void printStackTrace(PrintStream arg0) {
		e.printStackTrace(arg0);
	}

	public void printStackTrace(PrintWriter arg0) {
		e.printStackTrace(arg0);
	}

	public String toString() {
		return "DalException - " + e.toString();
	}
}

