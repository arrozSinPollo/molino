package mysqldal;

import java.sql.SQLException;

import dal.DalException;
import dal.ITipoDal;
import dal.Transactionable;
import domain.Tipo;

public class MySQLTipoGanadoDal extends Transactionable implements ITipoDal {
	private static final String tipoColumn = "tipo";
	private static final String tipoIDColumn = "ID";
	private static final String tableName = "tipo_de_ganado";

	public MySQLTipoGanadoDal() {
		super(new MySQLJDBCFacade());
	}
	
	public void insert(Tipo tip) throws DalException {
		try {
			getFacade().executeInsert(
					"INSERT INTO " + tableName + 
						" (" + tipoColumn + ") " + 
							"values ('" + tip.getTipo() + "')");
		} catch (SQLException e) {
			throw new DalException(e);
		} finally {
			getFacade().endCurrentOperation();
		}
		
	}

	public void modify(Tipo tip) throws DalException {
		try {
			getFacade().executeUpdate(
					"UPDATE " + tableName + 
						" SET " + tipoColumn + "='" + tip.getTipo() + "'," +					
					    " WHERE " + tipoIDColumn + "=" + tip.getTipoID());
		} catch (SQLException e) {
			throw new DalException(e);
		} finally {
			getFacade().endCurrentOperation();
		}	
	}

	public void delete(Tipo tip) throws DalException {
		try {
			getFacade().executeUpdate(
					"DELETE FROM " + tableName + 
						" WHERE " + tipoColumn + "='" + tip.getTipo() + "'");
		} catch (SQLException e) {
			throw new DalException(e);
		} finally {
			getFacade().endCurrentOperation();
		}
	}
}


