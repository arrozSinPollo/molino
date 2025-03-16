package mysqldal;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import dal.DalException;
import dal.IDalFacade;
import dal.ITransaction;
import jdbc.JDBCConnectionPool;

public class MySQLJDBCFacade implements IDalFacade {
	private ResultSet rs;
	private Statement stmt;
	private Connection conn;
	private ITransaction trans;
	
	public void setTransaction(ITransaction currentContext){
		trans=currentContext;
	}
	
	public void getConnection() throws SQLException, DalException {
		if(conn==null){
			//isConnectionActive es para comprobar que no se ha terminado la transacci�n
			if (trans!=null && trans.isConnectionActive()) 
				conn=(Connection)trans.getContextConnection();
			else
				conn=JDBCConnectionPool.getInstance().getConnection();
		}
	}
	
	public void endCurrentOperation() throws DalException{
		// cerramos el ResultSet si hace falta
		try {
			if (rs != null){
				rs.close();
				rs=null;
			}
		} catch(SQLException e) {
			throw new DalException(e);
		}
		// cerramos el Statement si hace falta
		try {
			if (stmt != null){
				stmt.close();
				rs=null;
			}
		} catch(SQLException e) {
			throw new DalException(e);
		}
		// Si no estamos en una transacci�n nosotros liberamos la conexi�n
		if (conn != null && (trans==null || !trans.isConnectionActive())){
			JDBCConnectionPool.getInstance().releaseConnection(conn);
			conn=null;
		}
	}
	
	/* (non-Javadoc)
	 * @see mysqldal.IDalFacade#getStringColumn(java.lang.String)
	 */
	public String getStringColumn(String columnName) throws SQLException{
		String result=null;
		result=rs.getString(columnName);
		
		return result;
	}
	
	/* (non-Javadoc)
	 * @see mysqldal.IDalFacade#getIntColumn(java.lang.String)
	 */
	public int getIntColumn(String columnName) throws SQLException{
		int result=-1;
		result=rs.getInt(columnName);
		
		return result;
	}
	
	public Date getDateColumn(String columnName) throws SQLException{
		Date result=null;
		result=rs.getDate(columnName);
		
		return result;
	}
	
	public boolean getBooleanColumn(String columnName) throws SQLException{
		boolean result=false;
		result=rs.getBoolean(columnName);
		
		return result;
	}

	/* (non-Javadoc)
	 * @see mysqldal.IDalFacade#executeQuery(java.lang.String)
	 */
	public Object executeQuery(String query) throws SQLException, DalException {
		getConnection();
		stmt = conn.createStatement();
		rs = stmt.executeQuery(query);
		
		return rs;
	}
	
	public int executeInsert(String query) throws SQLException, DalException {
		int generatedKey=0;
		getConnection();
		stmt = conn.createStatement();
		stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
		ResultSet rs = stmt.getGeneratedKeys();
		if (rs.next()) 
			 generatedKey=rs.getInt(1);
		return generatedKey;
	}
		
	
	/* (non-Javadoc)
	 * @see mysqldal.IDalFacade#executeUpdate(java.lang.String)
	 */
	public int executeUpdate(String query) throws SQLException, DalException {
		int rowsAffected=0;
		getConnection();
		stmt = conn.createStatement();
		rowsAffected=stmt.executeUpdate(query);
		
		return rowsAffected;
	}
	
}
