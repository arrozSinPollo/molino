package mysqldal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import dal.DalException;
import dal.IDetailFormulaDal;
import dal.Transactionable;
import domain.DetailFormula;
import domain.Formula;

public class MySQLDetailDal extends Transactionable implements IDetailFormulaDal {
	private static final String productoColumn = "NOMBRE";
	private static final String kgColumn = "KG";
	private static final String precioColumn = "PRECIO";
	private static final String formulaIDColumn= "IDFORMULA";
	private static final String detailIDColumn = "ID";
	private static final String loteColumn = "LOTE";
	private static final String fechaColumn = "FECHA";
	private static final String fechaCaducidadColumn = "FECHACADUCIDAD";
	private static final String tableName = "formula_det";

	public MySQLDetailDal() {
		super(new MySQLJDBCFacade());
	}

	public void insert(DetailFormula d) throws DalException {
		try {
			String insertSQL = "INSERT INTO " + tableName + " (" + productoColumn + ","
					+ kgColumn + "," + loteColumn + "," + formulaIDColumn + "," + fechaColumn + "," + precioColumn;
			
			if (!d.getFechaCaducidad().equals(""))
				insertSQL += ", " + fechaCaducidadColumn; 
			
			insertSQL += ") values ('" + d.getNombre() + "', "
							  		  + d.getKg()	+ ", '"
									  + d.getLote() + "', "
									  + d.getFormulaID() + ", '" 
									  + d.getFecha() + "', "
									  + d.getPrecio();
			
			if (!d.getFechaCaducidad().equals(""))
				insertSQL += ", '" + d.getFechaCaducidad() + "'";
			
			insertSQL += ")";
			
			getFacade().executeInsert(insertSQL);
			System.out.println("Detail OK: " +d.getNombre());
		} catch (SQLException e) {
			System.out.println("Detail ERROR: "+d.getNombre());
			throw new DalException(e);
		} finally {
			getFacade().endCurrentOperation();
		}
	}

	public void insert(List l) throws DalException {
		Iterator it = l.iterator();
		while (it.hasNext()) {
			insert((DetailFormula) it.next());
		}
	}

	/*
	 * Añadir la lista de detalles a una formula f seleccionada
	 */
	public void addDetailsToFormula(Formula f) throws DalException {
		ResultSet rs=null;
		int formulaID = f.getFormulaID();

		String sqlQuery = "SELECT * FROM " + tableName + 
								" WHERE " + formulaIDColumn + "="
										  + formulaID +
								" ORDER BY " + productoColumn;

		DetailFormula detail=null;
		try {
			rs= (ResultSet) getFacade().executeQuery(sqlQuery);
			do {
				detail=translateResulsetToDetail(rs,f);
			}while(detail != null);
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
	private DetailFormula translateResulsetToDetail(ResultSet rs, Formula f) throws DalException{
		DetailFormula d=null;
		int formulaID;
		int detailID;
		String product="";
		String lote="";
		float kg;
		Date fecha;
		Date fechaCaducidad;
		
		try{
			if(rs.next()){
				detailID=rs.getInt(detailIDColumn);
				formulaID=rs.getInt(formulaIDColumn);
				product = rs.getString(productoColumn);
				kg = rs.getFloat(kgColumn);
				lote = rs.getString(loteColumn);
				fecha = rs.getDate(fechaColumn);
				fechaCaducidad = rs.getDate(fechaCaducidadColumn);
				d = new DetailFormula(detailID, formulaID,product,fecha,lote,fechaCaducidad,kg);
				f.addDetail(d);
			}
		} catch (SQLException e) {
			throw new DalException(e);
		}

		return d;
	}

	public void modify(DetailFormula det) throws DalException {
		try {
			getFacade().executeUpdate(
					"UPDATE " + tableName + 
						" SET " + productoColumn + "='" + det.getNombre() + "'," 
								+ kgColumn + "=" + det.getKg() + ", "
								+ loteColumn + "='" + det.getLote() + "', " 
								+ fechaColumn + "='" + det.getFecha() + "', " 
								+ fechaCaducidadColumn + "='" + det.getFechaCaducidad() + "', "
								+ precioColumn + "=" + det.getPrecio() +
					    " WHERE " + formulaIDColumn + "=" + det.getFormulaID() +
					    		" AND " + detailIDColumn + "=" + det.getDetailFormulaID());
		} catch (SQLException e) {
			throw new DalException(e);
		} finally {
			getFacade().endCurrentOperation();
		}
	}

	public void delete(DetailFormula det) throws DalException {
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

	public void setOldestRegistro(DetailFormula det) {
		//float cantidad = det.getKg();
		
	}
	
}
