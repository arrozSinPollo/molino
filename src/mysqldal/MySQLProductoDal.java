package mysqldal;

import java.sql.SQLException;

import dal.DalException;
import dal.IProductoDal;
import dal.Transactionable;
import domain.Producto;

public class MySQLProductoDal extends Transactionable implements IProductoDal {
	private static final String nombreColumn = "PRODUCTO";
	private static final String tipoColumn = "TIPO";
	private static final String productoIDColumn = "ID";
	private static final String tableName = "producto";

	public MySQLProductoDal() {
		super(new MySQLJDBCFacade());
	}
	
	public void insert(Producto prod) throws DalException {
		try {
			getFacade().executeInsert(
					"INSERT INTO " + tableName + 
						" (" + nombreColumn + "," + tipoColumn +") " + 
							"values ('" + prod.getName() + "','"
										  + prod.getTipo()	+ "')");
		} catch (SQLException e) {
			throw new DalException(e);
		} finally {
			getFacade().endCurrentOperation();
		}
		
	}

	public void modify(Producto prod) throws DalException {
		try {
			getFacade().executeUpdate(
					"UPDATE " + tableName + 
						" SET " + nombreColumn + "='" + prod.getName() + "'," 
								+ tipoColumn + "='" + prod.getTipo() +	"'," +					
					    " WHERE " + productoIDColumn + "=" + prod.getProductoID());
		} catch (SQLException e) {
			throw new DalException(e);
		} finally {
			getFacade().endCurrentOperation();
		}	
	}

	public void delete(Producto prod) throws DalException {
		try {
			getFacade().executeUpdate(
					"DELETE FROM " + tableName + 
						" WHERE " + nombreColumn + "='" + prod.getName() + "'");
		} catch (SQLException e) {
			throw new DalException(e);
		} finally {
			getFacade().endCurrentOperation();
		}
	}
}
