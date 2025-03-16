package domain;

import java.util.Date;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class Formula {

	//	ATRIBUTOS
	private int formulaID;
	private Date fecha;
	private String nombre;
	private String tipoDeGanado;
	private String situacion;
	private String estado; 
	private float precio;
	private ArrayList detailList;
	
	//	CONSTRUCTORES
	public Formula() {
		super();
		formulaID = -1;
		fecha =null;
		nombre = "";
		tipoDeGanado = "";
		situacion = "";
		estado = "";
		detailList = new ArrayList();
	}
	public Formula(int id, Date f, String n, String t, String s, String e) {
		super();
		formulaID = id;
		fecha =f;
		nombre = n;
		tipoDeGanado = t;
		situacion = s;
		estado = e;
		detailList = new ArrayList();
	}
	
	public Formula(int id, Date f, String n, String t, String s, String e, ArrayList list) {
		super();
		formulaID = id;
		fecha =f;
		nombre = n;
		tipoDeGanado = t;
		situacion = s;
		estado = e;
		detailList = list;
	}

	public Formula(int id, Date f, String n, String t, String s) {
		this(id, f,n,t,s,"",new ArrayList());
	}
	
	public Formula(int id, Date f, String n, String t) {
		this(id,f,n,t,"","",new ArrayList());
	}

	//	FUNCIONES
	public void addDetail(DetailFormula d){
		detailList.add(d);
	}
	public List getDetailList() {
		return detailList;
	}

	public void setDetailList(List detailList) {
		this.detailList = (ArrayList) detailList;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getFechaToString() {
		java.text.DateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(this.fecha);
	}
	
	public String getFecha() {
		java.text.DateFormat sdf = new java.text.SimpleDateFormat("yyyy/MM/dd");
        return sdf.format(this.fecha);
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	public void setFecha(int ano, int mes, int dia) {
		GregorianCalendar gcAux = new GregorianCalendar(ano,mes,dia); 
	    this.fecha = gcAux.getTime();
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getSituacion() {
		return situacion;
	}

	public void setSituacion(String situacion) {
		this.situacion = situacion;
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
	
	public String getTipoDeGanado() {
		return tipoDeGanado;
	}
	
	public void setTipoDeGanado(String tipoDeGanado) {
		this.tipoDeGanado = tipoDeGanado;
	}

}
