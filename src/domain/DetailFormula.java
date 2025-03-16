package domain;

import java.util.Date;
import java.util.GregorianCalendar;

public class DetailFormula {

	//	ATRIBUTOS
	private int DetailFormulaID;
	private int formulaID;
	private String nombre;
	private Date fecha;
	private Date fechaCaducidad;
	private String lote;
	private float precio;
	private float kg;

	//	CONSTRUCTORES
	public DetailFormula() {
		super();
		this.DetailFormulaID = -1;
		this.formulaID = -1;
		this.lote = "";
		this.nombre = "";
		this.kg = 0;
	}
	
	public DetailFormula(int id_det, int id, String n, Date f, String l, Date fCaducidad, float kg) {
		super();
		this.DetailFormulaID = id_det;
		this.formulaID = id;
		this.nombre = n;
		this.lote = l;
		this.kg = kg;
		this.fecha = f;
		this.fechaCaducidad = fCaducidad;
	}

	//	FUNCIONES
	public int getDetailFormulaID() {
		return DetailFormulaID;
	}

	public void setDetailFormulaID(int detailFormulaID) {
		DetailFormulaID = detailFormulaID;
	}
	
	public float getKg() {
		return kg;
	}

	public void setKg(float kg) {
		this.kg = kg;
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

	public String getLote() {
		return lote;
	}

	public void setLote(String lote) {
		this.lote = lote;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public int getFormulaID() {
		return formulaID;
	}

	public void setFormulaID(int formulaID) {
		this.formulaID = formulaID;
	}
	
	public float getPrecio() {
		return precio;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
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

	public void setFechaCaducidad(Date fecha) {
		this.fechaCaducidad = fecha;
	}
	
}
