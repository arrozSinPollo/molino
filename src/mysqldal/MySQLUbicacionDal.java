package mysqldal;

import java.sql.SQLException;

import dal.DalException;
import dal.IUbicacionDal;
import dal.Transactionable;
import domain.Ubicacion;

public class MySQLUbicacionDal extends Transactionable implements IUbicacionDal{
	private static final String nombreColumn = "UBICACION";
	private static final String prooveedorIDColumn = "ID";
	private static final String tableName = "UBICACION";

	public MySQLUbicacionDal() {
		super(new MySQLJDBCFacade());
	}
	
	public void insert(Ubicacion ubicacion) throws DalException {
		try {
			getFacade().executeInsert(
					"INSERT INTO " + tableName + 
						" (" + nombreColumn + ") " + 
							"values ('" + ubicacion.getName() + "')");
		} catch (SQLException e) {
			throw new DalException(e);
		} finally {
			getFacade().endCurrentOperation();
		}
		
	}

	public void modify(Ubicacion ubicacion) throws DalException {
		try {
			getFacade().executeUpdate(
					"UPDATE " + tableName + 
						" SET " + nombreColumn + "='" + ubicacion.getName() + "'," +					
					    " WHERE " + prooveedorIDColumn + "=" + ubicacion.getUbicacionID());
		} catch (SQLException e) {
			throw new DalException(e);
		} finally {
			getFacade().endCurrentOperation();
		}	
	}

	public void delete(Ubicacion ubicacion) throws DalException {
		try {
			getFacade().executeUpdate(
					"DELETE FROM " + tableName + 
						" WHERE " + nombreColumn + "='" + ubicacion.getName() + "'");
		} catch (SQLException e) {
			throw new DalException(e);
		} finally {
			getFacade().endCurrentOperation();
		}
	}
}

