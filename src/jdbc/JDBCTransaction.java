package jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import dal.DalException;
import dal.ITransaction;

public class JDBCTransaction implements ITransaction {

	Connection conn;
	
	public void beginTransaction() throws DalException {
		try {
			conn=JDBCConnectionPool.getInstance().getConnection();
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			throw new DalException(e);
		}
	}

	public void commit() throws DalException{
		try {
			conn.commit();
		} catch (SQLException e) {
			throw new DalException(e);
		}
		finally{
			JDBCConnectionPool.getInstance().releaseConnection(conn);
			conn=null;
		}

	}

	public void rollback() throws DalException{
		try {
			conn.rollback();
		} catch (SQLException e) {
			throw new DalException(e);
		}
		finally{
			JDBCConnectionPool.getInstance().releaseConnection(conn);
			conn=null;
		}
	}
	
	public Object getContextConnection(){
		return conn;
	}
	
	//Esta función sirve para que la facade compruebe si la transacción ha terminado o no
	public boolean isConnectionActive(){
		return (conn!=null);
	}

}
