package dal;

public class Transactionable {
	
	ITransaction trans;
	IDalFacade facade; //TODO: concretar en una fachada generica
	
	public Transactionable(IDalFacade usedFacade)
	{
		facade=usedFacade;
		facade.setTransaction(trans);
	}
	
	public final void setContext(ITransaction currentTransaction)
	{
		trans=currentTransaction;
	}
	
	public IDalFacade getFacade()
	{
		return facade;
	}
	
	
}
