package mysqldal;

import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;


import dal.*;
import domain.*;

public class MySQLFormulaDal extends Transactionable implements IFormulaDal {
	private static final String fechaColumn = "FECHA";
	private static final String nombreColumn = "NOMBRE";
	private static final String tipoDeGanadoColumn = "TIPO_DE_GANADO";
	private static final String formulaIDColumn = "ID";
	private static final String situacionColumn = "SITUACION";
	private static final String estadoColumn = "ESTADO";
	private static final String precioColumn = "PRECIO";
	private static final String tableName = "formula";
	private static final String tableNameDetail = "formula_det";
	private static final String detailFormulaIDColumn = "IDFORMULA";

	private IDetailFormulaDal detailDal;

	public MySQLFormulaDal() {
		super(new MySQLJDBCFacade());
		detailDal = new MySQLDetailDal();
	}
	
	public MySQLFormulaDal(IDetailFormulaDal d) {
		super(new MySQLJDBCFacade());
		detailDal = d;
	}

	public IDetailFormulaDal getDetailsDal() {
		return detailDal;
	}

	public void insert(Formula f) throws DalException {
		try {
			getFacade().executeInsert(
					"INSERT INTO " + tableName + " (" + fechaColumn + ","
							+ nombreColumn + "," + tipoDeGanadoColumn + "," + situacionColumn + ","
							+ estadoColumn + ", " + precioColumn + ") "
							+ "values ('" + f.getFecha() + "', '" 
										  + f.getNombre() + "', '"  
										  + f.getTipoDeGanado() + "', '"  
										  + f.getSituacion() + "', '"
										  + f.getEstado() + "', "
										  + f.getPrecio() + ")");
		} catch (SQLException e) {
			throw new DalException(e);
		} finally {
			getFacade().endCurrentOperation();
		}

		//detailDal.insert(f.getDetailList());
	}

	public Formula findFormulaByNombre(String n) throws DalException {
		String sqlRequest = "SELECT * FROM " + tableName + 
								" WHERE " + nombreColumn + "='" + n + "'" +
								" ORDER BY " + nombreColumn;
		Formula result = null;
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
	
	public Formula translateResultSetToFormula(ResultSet rs) throws DalException {
		Formula result = null;
		MySQLDetailDal detailDal = new MySQLDetailDal();
		try {
			if (rs.next()) {
				Date fecha = getFacade().getDateColumn(fechaColumn);
				String nombre = getFacade().getStringColumn(nombreColumn);
				String tipoDeGanado = getFacade().getStringColumn(tipoDeGanadoColumn);
				String situacion = getFacade().getStringColumn(situacionColumn);
				String estado = getFacade().getStringColumn(estadoColumn);
				int formulaID = getFacade().getIntColumn(formulaIDColumn);
				result = new Formula(formulaID,fecha,nombre,tipoDeGanado,situacion,estado);
				detailDal.addDetailsToFormula(result);
			}
		} catch (SQLException e) {
			throw new DalException(e);
		}

		return result;
	}
	
	public List findAllFormulas() throws DalException {
		List<Formula> formulaList = new ArrayList<Formula>();
		
		String sqlRequest = "SELECT * FROM " + tableName +
								" ORDER BY " + nombreColumn;;
		Formula f=null;
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

	public void modify(Formula form) throws DalException {
		try {
			getFacade().executeUpdate(
					"UPDATE " + tableName + " SET " + fechaColumn + "='" + form.getFecha() + "'," +
						nombreColumn + "='" + form.getNombre() + "'," + 
						tipoDeGanadoColumn + "='" + form.getTipoDeGanado() + "'," + 
						situacionColumn + "='" + form.getSituacion() + "'," + 
						estadoColumn + "='" +form.getEstado() + "', " + 
						precioColumn + "=" + form.getPrecio() +
						" WHERE " + formulaIDColumn + "=" + form.getFormulaID());
		} catch (SQLException e) {
			throw new DalException(e);
		} finally {
			getFacade().endCurrentOperation();
		}
	}

	public void delete(Formula form) throws DalException {
		try {
			getFacade().executeUpdate(
					"DELETE FROM " + tableNameDetail + 
						" WHERE " + detailFormulaIDColumn + "=" + form.getFormulaID());
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
