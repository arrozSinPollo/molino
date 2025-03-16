package dal;

import java.sql.Date;
import java.sql.SQLException;

public interface IDalFacade {

	public void endCurrentOperation() throws DalException;
	
	public Object executeQuery(String query) throws SQLException,
			DalException;

	public String getStringColumn(String columnName) throws SQLException;

	public int getIntColumn(String columnName) throws SQLException;
	
	public Date getDateColumn(String columnName) throws SQLException;
	
	public boolean getBooleanColumn(String columnName) throws SQLException;

	public int executeInsert(String query) throws SQLException,
			DalException;

	public int executeUpdate(String query) throws SQLException,
			DalException;
	
	public void setTransaction(ITransaction currentContext);

}