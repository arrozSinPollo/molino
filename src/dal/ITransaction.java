package dal;

public interface ITransaction {
	
	public void beginTransaction() throws DalException;
	
	public void commit() throws DalException;
	
	public void rollback() throws DalException;

	public Object getContextConnection();
	
	public boolean isConnectionActive();
}
