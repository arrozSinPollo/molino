package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import dal.DalException;
import utils.PropertiesUtil;

public class JDBCConnectionPool {
	private static final int InitialConnections = 1;
	private List<Connection> freeConnections;
	private List<Connection> busyConnections;
	
	private static JDBCConnectionPool instance;
	
	private Connection createConnection() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
        // The newInstance() call is a work around for some
        // broken Java implementations
		PropertiesUtil properties = new PropertiesUtil();
		Class.forName(properties.getProperty(PropertiesUtil.JDBCDriver)).newInstance();
        String jdbc = properties.getProperty(PropertiesUtil.JDBCURL);
        String username = properties.getProperty(PropertiesUtil.JDBCUsername);
        String password = properties.getProperty(PropertiesUtil.JDBCPassword);
        
        return DriverManager.getConnection(jdbc,username,password);
	}
	
	private JDBCConnectionPool() throws DalException {
        
		try {
        	freeConnections = new ArrayList<Connection> ();
        	busyConnections = new ArrayList<Connection> ();
        	
            for(int i =0;i<InitialConnections;i++)
            {
            	freeConnections.add(createConnection());
            }
            
        } catch (Exception e) {
        	throw new DalException(e);
        }
	}
	
	public static JDBCConnectionPool getInstance() throws DalException {
		if(instance == null) {
			instance = new JDBCConnectionPool();
		}
		return instance;
	}
	
    public Connection getConnection() throws DalException {
    	
    	Connection result;
    	
    	if(freeConnections.isEmpty()) {
    		try {
				result = createConnection();
			} catch (Exception e) {
				throw new DalException(e);
			}
    	} else {
	    	result = (Connection) freeConnections.remove(0);
    	}
    	busyConnections.add(result);
    		
    	return result;
    }
    
    public void releaseConnection(Connection c){
    	
    	busyConnections.remove(c);
    	freeConnections.add(c);
    }
    
    protected void finalize() throws Throwable{
    	
    	Iterator it;
    	Connection con;
    	
    	it = freeConnections.iterator() ;
    	while(it.hasNext()){
    		con = (Connection) it.next();
    		con.close();
    	}
    	
    	it = busyConnections.iterator() ;
    	while(it.hasNext()){
    		con = (Connection) it.next();
    		con.close();	
    	}
   
    	super.finalize();
    }
}
