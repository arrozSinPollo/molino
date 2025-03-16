package domain;

import java.util.Date;
import java.util.GregorianCalendar;
public class RegistroDeRecepcion {

	//	ATRIBUTOS
	private int registroID;
	private Date fecha;
	private Producto producto;
	private Proveedor proveedor;
	private float kg;
	private float enAlmacen;
	private String lote;
	private String albaran;
	private Ubicacion ubicacion;
	private String observaciones;
	private boolean conformidad;
	private float precio;
	private Date fechaCaducidad;
	
	//	CONSTRUCTORES
	public RegistroDeRecepcion() {
		super();
		this.registroID = -1;
		this.fecha = new Date();
		this.producto = new Producto();
		this.proveedor = new Proveedor();
		this.kg = 0;
		this.enAlmacen = 0;
		this.lote = "";
		this.albaran = "";
		this.ubicacion = new Ubicacion();
		this.observaciones = "";
		this.conformidad = false;
		this.precio = 0;
		this.fechaCaducidad = new Date();
	}
	
	public RegistroDeRecepcion(int id, Date fecha, Producto producto, Proveedor proveedor, float kg, float enAlmacen, String lote, String albaran, Ubicacion ubicacion, String observaciones, boolean conformidad, float precio, Date fechaCaducidad) {
		super();
		this.registroID = id;
		this.fecha = fecha;
		this.producto = producto;
		this.proveedor = proveedor;
		this.kg = kg;
		this.enAlmacen = enAlmacen;
		this.lote = lote;
		this.albaran = albaran;
		this.ubicacion = ubicacion;
		this.observaciones = observaciones;
		this.conformidad = conformidad;
		this.precio = precio;
		this.fechaCaducidad = fechaCaducidad;
	}
	
	//	FUNCIONES
	public String getAlbaran() {
		return albaran;
	}

	public void setAlbaran(String albaran) {
		this.albaran = albaran;
	}

	public boolean isConformidad() {
		return conformidad;
	}

	public void setConformidad(boolean conformidad) {
		this.conformidad = conformidad;
	}
	
	public Date getFechaDate() {
        return fecha;
	}

	public String getFecha() {
		java.text.DateFormat sdf = new java.text.SimpleDateFormat("yyyy/MM/dd");
        return sdf.format(this.fecha);
	}
	
	public String getFechaToString() {
		java.text.DateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(this.fecha);
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	public void setFecha(int ano, int mes, int dia) {
		GregorianCalendar gcAux = new GregorianCalendar(ano,mes,dia); 
	    this.fecha = gcAux.getTime();
	}

	public float getKg() {
		return kg;
	}

	public void setKg(float kg) {
		this.kg = kg;
	}

	public float getPrecio() {
		return precio;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
	}
	
	public float getEnAlmacen() {
		return enAlmacen;
	}

	public void setEnAlmacen(float kg) {
		this.enAlmacen = kg;
	}
	
	public String getLote() {
		return lote;
	}

	public void setLote(String lote) {
		this.lote = lote;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public Ubicacion getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(Ubicacion ubicacion) {
		this.ubicacion = ubicacion;
	}
	
	public Date getFechaCaducidadDate() {
        return fechaCaducidad;
	}
	
	public String getFechaCaducidad() {
		if (this.fechaCaducidad == null) {
			return "";
		} else {
			java.text.DateFormat sdf = new java.text.SimpleDateFormat("yyyy/MM/dd");
	        return sdf.format(this.fechaCaducidad);
		}
	}
	
	public String getFechaCaducidadToString() {
		if (this.fechaCaducidad == null) {
			return null;
		} else {
			java.text.DateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
	        return sdf.format(this.fechaCaducidad);
		}
	}

	public void setFechaCaducidad(Date fechaCaducidad) {
//		if (fechaCaducidad == null)
//			this.fechaCaducidad = new Date();
//		else 
			this.fechaCaducidad = fechaCaducidad;
	}

	public String toString(){
		return "\nProducto de entrada: " + getProducto() + ", " + getKg() + " kg," +
				"\nel día " + getFecha() + " por parte de " + getProveedor() + 
				"\n\t Lote: " + getLote() +
				"\n\t Albarán: " + getAlbaran() +
				"\n\t Ubicación: " + getUbicacion() +
				"\n\t Observaciones: " + getObservaciones() +
				"\n\t Conformidad: " + isConformidad() +
				"\n\t Fecha de Caducidad: " + getFechaCaducidad();
	}

	public int getRegistroID() {
		return registroID;
	}

	public void setRegistroID(int registroID) {
		this.registroID = registroID;
	}
}
