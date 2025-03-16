package mysqldal;

import java.sql.SQLException;

import dal.DalException;
import dal.IProveedorDal;
import dal.Transactionable;
import domain.Proveedor;

public class MySQLProveedorDal extends Transactionable implements IProveedorDal {
	private static final String nombreColumn = "PROVEEDOR";
	private static final String prooveedorIDColumn = "ID";
	private static final String tableName = "PROVEEDOR";

	public MySQLProveedorDal() {
		super(new MySQLJDBCFacade());
	}
	
	public void insert(Proveedor prov) throws DalException {
		try {
			getFacade().executeInsert(
					"INSERT INTO " + tableName + 
						" (" + nombreColumn + ") " + 
							"values ('" + prov.getName() + "')");
		} catch (SQLException e) {
			throw new DalException(e);
		} finally {
			getFacade().endCurrentOperation();
		}
		
	}

	public void modify(Proveedor prov) throws DalException {
		try {
			getFacade().executeUpdate(
					"UPDATE " + tableName + 
						" SET " + nombreColumn + "='" + prov.getName() + "'," +					
					    " WHERE " + prooveedorIDColumn + "=" + prov.getProveedorID());
		} catch (SQLException e) {
			throw new DalException(e);
		} finally {
			getFacade().endCurrentOperation();
		}	
	}

	public void delete(Proveedor prov) throws DalException {
		try {
			getFacade().executeUpdate(
					"DELETE FROM " + tableName + 
						" WHERE " + nombreColumn + "='" + prov.getName() + "'");
		} catch (SQLException e) {
			throw new DalException(e);
		} finally {
			getFacade().endCurrentOperation();
		}
	}
}

