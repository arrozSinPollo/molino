package mysqldal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import dal.DalException;
import dal.IDetailFormulaCreadaDal;
import dal.Transactionable;
import domain.DetailFormulaCreada;
import domain.FormulasCreadas;

public class MySQLDetailCreadoDal extends Transactionable implements IDetailFormulaCreadaDal {
	private static final String productoColumn = "NOMBRE";
	private static final String kgColumn = "KG";
	private static final String formulaIDColumn= "IDFORMULA";
	private static final String detailIDColumn = "ID";
	private static final String tableName = "formula_det_creada";

	public MySQLDetailCreadoDal() {
		super(new MySQLJDBCFacade());
	}

	public void insert(DetailFormulaCreada d) throws DalException {
		try {
			getFacade().executeInsert(
					"INSERT INTO " + tableName + " (" + productoColumn + ","
							+ kgColumn + "," + formulaIDColumn + ") "
							+ "values ('" + d.getNombre() + "',"
										  + d.getKg()	+ ","
										  + d.getFormulaID() + ")");
		} catch (SQLException e) {
			throw new DalException(e);
		} finally {
			getFacade().endCurrentOperation();
		}
	}

	public void insert(List l) throws DalException {
		Iterator it = l.iterator();
		while (it.hasNext()) {
			insert((DetailFormulaCreada) it.next());
		}
	}

	/*
	 * Añadir la lista de detalles a una formula f seleccionada
	 */
	public void addDetailsToFormula(FormulasCreadas f) throws DalException {
		ResultSet rs=null;
		int formulaID = f.getFormulaID();

		String sqlQuery = "SELECT * FROM " + tableName + 
								" WHERE " + formulaIDColumn + "="
										  + formulaID +
								" ORDER BY " + productoColumn;

		DetailFormulaCreada detail=null;
		try {
			rs= (ResultSet) getFacade().executeQuery(sqlQuery);
			do {
				detail=translateResulsetToDetail(rs,f);
			} while(detail != null);
		} catch (SQLException e) {
			throw new DalException(e);
		}
		finally{
			getFacade().endCurrentOperation();
		}
	}

	/*
	 * Función auxiliar que utiliza addDetailsToFormula
	 */
	private DetailFormulaCreada translateResulsetToDetail(ResultSet rs, FormulasCreadas f) throws DalException{
		DetailFormulaCreada d=null;
		int formulaID;
		int detailID;
		String product="";
		float kg;
		
		try{
			if(rs.next()){
				detailID=rs.getInt(detailIDColumn);
				formulaID=rs.getInt(formulaIDColumn);
				product = rs.getString(productoColumn);
				kg = rs.getFloat(kgColumn);
				d = new DetailFormulaCreada(detailID, formulaID,product,kg);
				f.addDetail(d);
			}
		} catch (SQLException e) {
			throw new DalException(e);
		}

		return d;
	}

	public void modify(DetailFormulaCreada det) throws DalException {
		try {
			getFacade().executeUpdate(
					"UPDATE " + tableName + 
						" SET " + productoColumn + "='" + det.getNombre() + "'," 
								+ kgColumn + "=" + det.getKg() +						
					    " WHERE " + detailIDColumn + "=" + det.getDetailFormulaID());
			//			" WHERE " + formulaIDColumn + "=" + det.getFormulaID() + 
    							//" AND " + detailIDColumn + "=" + det.getDetailFormulaID());
		} catch (SQLException e) {
			throw new DalException(e);
		} finally {
			getFacade().endCurrentOperation();
		}
	}

	public void delete(DetailFormulaCreada det) throws DalException {
		try {
			getFacade().executeUpdate(
					"DELETE FROM " + tableName + 
						" WHERE " + detailIDColumn + "=" + det.getDetailFormulaID());
		} catch (SQLException e) {
			throw new DalException(e);
		} finally {
			getFacade().endCurrentOperation();
		}

	}

	public void setOldestRegistro(DetailFormulaCreada det) {
		//float cantidad = det.getKg();
		
	}
}
