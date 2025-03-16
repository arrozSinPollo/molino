package dal;

import domain.*;

public class DetailOutOfDateException extends Exception {
	DetailFormula desynchronizedDetail;
	
	public DetailOutOfDateException(DetailFormula desynchronizedDetail)
	{
		this.desynchronizedDetail=desynchronizedDetail;
	}
	
	public DetailFormula getDesynchronizedDetail()
	{
		return desynchronizedDetail;
	}
	
	public String toString()
	{
		String result="Los datos de " + desynchronizedDetail.toString() + " no están sincronizados con la base de datos.";
		return result;
	}
}
