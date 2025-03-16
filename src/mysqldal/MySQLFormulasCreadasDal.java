package mysqldal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dal.DalException;
import dal.IDetailFormulaDal;
import dal.IFormulaCreadaDal;
import dal.Transactionable;
import domain.FormulasCreadas;

public class MySQLFormulasCreadasDal extends Transactionable implements IFormulaCreadaDal {
	private static final String nombreColumn = "NOMBRE";
	private static final String tipoColumn = "tipo_de_ganado";
	private static final String formulaIDColumn = "ID";
	private static final String tableName = "formula_creada";

	private IDetailFormulaDal detailDal;

	public MySQLFormulasCreadasDal() {
		super(new MySQLJDBCFacade());
		detailDal = new MySQLDetailDal();
	}
	
	public MySQLFormulasCreadasDal(IDetailFormulaDal d) {
		super(new MySQLJDBCFacade());
		detailDal = d;
	}

	public IDetailFormulaDal getDetailsDal() {
		return detailDal;
	}

	public void insert(FormulasCreadas f) throws DalException {
		try {
			getFacade().executeInsert(
					"INSERT INTO " + tableName + " (" + nombreColumn +  ", " 
													  + tipoColumn + ") "
							+ "values ('" + f.getNombre() + "', '" + f.getTipoDeGanado() + "')");
		} catch (SQLException e) {
			throw new DalException(e);
		} finally {
			getFacade().endCurrentOperation();
		}

		detailDal.insert(f.getDetailList());
	}

	public FormulasCreadas findFormula(String n, String t) throws DalException {
		String sqlRequest = "SELECT * FROM " + tableName + 
								" WHERE " + nombreColumn + "='" + n + "'" +
									" AND " + tipoColumn + "='" + t + "'";
		FormulasCreadas result = null;
		ResultSet rs=null;

		try {
			rs = (ResultSet) getFacade().executeQuery(sqlRequest);
			result = translateResultSetToFormula(rs);
		} catch (SQLException e) {
			throw new DalException(e);
		} finally {
			getFacade().endCurrentOperation();
		}

		return result;
	}
	
	public FormulasCreadas translateResultSetToFormula(ResultSet rs) throws DalException {
		FormulasCreadas result = null;
		MySQLDetailCreadoDal detailDal = new MySQLDetailCreadoDal();
		try {
			if (rs.next()) {
				String nombre = getFacade().getStringColumn(nombreColumn);
				String tipoDeGanado = getFacade().getStringColumn(tipoColumn);
				int formulaID = getFacade().getIntColumn(formulaIDColumn);
				result = new FormulasCreadas(formulaID,nombre,tipoDeGanado);
				detailDal.addDetailsToFormula(result);
			}
		} catch (SQLException e) {
			throw new DalException(e);
		}

		return result;
	}
	
	public List findAllFormulas() throws DalException {
		List<FormulasCreadas> formulaList = new ArrayList<FormulasCreadas>();
		
		String sqlRequest = "SELECT * FROM " + tableName +
								" ORDER BY " + nombreColumn;
		FormulasCreadas f=null;
		ResultSet rs=null;

		try {
			rs = (ResultSet) getFacade().executeQuery(sqlRequest);
			do {
				f=translateResultSetToFormula(rs);
				if(f != null)
					formulaList.add(f);
			} while(f!=null);
		} catch (SQLException e) {
			throw new DalException(e);
		} finally {
			getFacade().endCurrentOperation();
		}
		
		return formulaList;
	}

	public void modify(FormulasCreadas form) throws DalException {

		try {
			getFacade().executeUpdate(
					"UPDATE " + tableName + " SET " + nombreColumn + "='" + form.getNombre() + "', " + 		
													tipoColumn + "='" + form.getTipoDeGanado() + "' " + 
						" WHERE " + formulaIDColumn + "=" + form.getFormulaID());
		} catch (SQLException e) {
			throw new DalException(e);
		} finally {
			getFacade().endCurrentOperation();
		}
	}

	public void delete(FormulasCreadas form) throws DalException {
		try {
			getFacade().executeUpdate(
					"DELETE FROM " + tableName + 
						" WHERE " + formulaIDColumn + "=" + form.getFormulaID());
		} catch (SQLException e) {
			throw new DalException(e);
		} finally {
			getFacade().endCurrentOperation();
		}

	}

}

