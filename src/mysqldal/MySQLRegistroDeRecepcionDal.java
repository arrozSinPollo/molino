package mysqldal;

import java.sql.SQLException;

import dal.DalException;
import dal.IRegistroDeRecepcionDal;
import dal.Transactionable;
import domain.RegistroDeRecepcion;

public class MySQLRegistroDeRecepcionDal extends Transactionable implements IRegistroDeRecepcionDal {
	private static final String fechaColumn = "fecha";
	private static final String fechaCaducidadColumn = "fechaCaducidad";
	private static final String productoColumn = "producto";
	private static final String registroIDColumn = "ID";
	private static final String proveedorColumn = "proveedor";
	private static final String kgColumn = "kg";
	private static final String enAlmacenColumn = "enAlmacen";
	private static final String loteColumn = "lote";
	private static final String albaranColumn = "albaran";
	private static final String ubicacionColumn = "ubicacion";
	private static final String conformidadColumn = "conformidad";
	private static final String observacionesColumn = "observaciones";
	private static final String precioColumn = "precio";
	private static final String tableName = "registrosDeRecepcion";

	public MySQLRegistroDeRecepcionDal() {
		super(new MySQLJDBCFacade());
	}
	
	public void insert(RegistroDeRecepcion reg) throws DalException {
		try {
			String insertSQL = "INSERT INTO " + tableName + 
					" (" + fechaColumn + ", " + productoColumn + ", " + 
					proveedorColumn + ", " + kgColumn + ", " + enAlmacenColumn + ", " +
					loteColumn + ", " + albaranColumn + ", " +
					ubicacionColumn + ", " + conformidadColumn + ", " +
					observacionesColumn + ", " + precioColumn;
			
			if (!reg.getFechaCaducidad().equals(""))
				insertSQL += ", " + fechaCaducidadColumn; 
			
			insertSQL += ") values ('" + reg.getFecha() + "', '" 
								  + reg.getProducto().getName()	+ "', '" 
								  + reg.getProveedor().getName() + "', " 
								  + reg.getKg() + ", "
								  + reg.getEnAlmacen() + ", '" 
								  + reg.getLote() + "', '"
								  + reg.getAlbaran() + "', '"
								  + reg.getUbicacion() + "', "
								  + reg.isConformidad() + ", '"
								  + reg.getObservaciones() + "', "
								  + reg.getPrecio();
								  
			if (!reg.getFechaCaducidad().equals(""))
				insertSQL += ", '" + reg.getFechaCaducidad() + "'";
			
			insertSQL += ")";
			
			getFacade().executeInsert(insertSQL);
					
		} catch (SQLException e) {
			throw new DalException(e);
		} finally {
			getFacade().endCurrentOperation();
		}
		
	}

	public void modify(RegistroDeRecepcion reg) throws DalException {
		try {
			String updateSQL = "UPDATE " + tableName + 
								" SET " + fechaColumn + "='" + reg.getFecha() + "'," 
								+ productoColumn + "='" + reg.getProducto().getName() +	"'," 
								+ proveedorColumn + "='" + reg.getProveedor().getName() +	"'," 
								+ kgColumn + "=" + reg.getKg() + ","
								+ enAlmacenColumn + "=" + reg.getEnAlmacen() + ","
								+ loteColumn + "='" + reg.getLote() + "',"
								+ albaranColumn + "='" + reg.getAlbaran() + "',"
								+ ubicacionColumn + "='" + reg.getUbicacion() + "',"
								+ conformidadColumn + "=" + reg.isConformidad() + ","
								+ observacionesColumn + "='" + reg.getObservaciones() + "'," 
								+ precioColumn + "=" + reg.getPrecio() + "," 
								+ fechaCaducidadColumn + "='" + reg.getFechaCaducidad() + "'";
			
			updateSQL += " WHERE " + registroIDColumn + "=" + reg.getRegistroID();
				
			getFacade().executeUpdate(updateSQL);
					
		} catch (SQLException e) {
			throw new DalException(e);
		} finally {
			getFacade().endCurrentOperation();
		}	
	}
	
	public void modifySinFechaCaducidad(RegistroDeRecepcion reg) throws DalException {
		try {
			String updateSQL = "UPDATE " + tableName + 
								" SET " + fechaColumn + "='" + reg.getFecha() + "'," 
								+ productoColumn + "='" + reg.getProducto().getName() +	"'," 
								+ proveedorColumn + "='" + reg.getProveedor().getName() +	"'," 
								+ kgColumn + "=" + reg.getKg() + ","
								+ enAlmacenColumn + "=" + reg.getEnAlmacen() + ","
								+ loteColumn + "='" + reg.getLote() + "',"
								+ albaranColumn + "='" + reg.getAlbaran() + "',"
								+ ubicacionColumn + "='" + reg.getUbicacion() + "',"
								+ conformidadColumn + "=" + reg.isConformidad() + ","
								+ observacionesColumn + "='" + reg.getObservaciones() + "'," 
								+ precioColumn + "=" + reg.getPrecio(); 
								//+ fechaCaducidadColumn + "='" + reg.getFechaCaducidad() + "'";
			
			updateSQL += " WHERE " + registroIDColumn + "=" + reg.getRegistroID();
				
			getFacade().executeUpdate(updateSQL);
					
		} catch (SQLException e) {
			throw new DalException(e);
		} finally {
			getFacade().endCurrentOperation();
		}	
	}

	public void delete(RegistroDeRecepcion reg) throws DalException {
		try {
			getFacade().executeUpdate(
					"DELETE FROM " + tableName + 
						" WHERE " + registroIDColumn + "=" + reg.getRegistroID()
								/*+ productoColumn + "='" + reg.getProducto().getName() + "' AND " 
								  + proveedorColumn + "='" + reg.getProveedor().getName() + "' AND " 
								  + kgColumn + "=" + reg.getKg() + " AND " 
								  //+ enAlmacenColumn + "=" + reg.getEnAlmacen() + " AND " 
								  + loteColumn + "='" + reg.getLote() + "' AND " 
								  + albaranColumn + "='" + reg.getAlbaran() + "' AND " 
								  + ubicacionColumn + "='" + reg.getUbicacion() + "' AND " 
								  + conformidadColumn + "=" + reg.isConformidad() + " AND " 
								  + observacionesColumn + "='" + reg.getObservaciones() + "'"*/
								  );
		} catch (SQLException e) {
			throw new DalException(e);
		} finally {
			getFacade().endCurrentOperation();
		}
	}
}

