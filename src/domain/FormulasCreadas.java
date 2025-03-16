package domain;

import java.util.ArrayList;
import java.util.List;

public class FormulasCreadas {

	//	ATRIBUTOS
	private int formulaID;;
	private String nombre;
	private String tipoDeGanado;
	private ArrayList detailList;
	
	//	CONSTRUCTORES
	public FormulasCreadas() {
		super();
		formulaID = -1;
		nombre = "";
		detailList = new ArrayList();
	}
	public FormulasCreadas(int id, String n, String t) {
		super();
		formulaID = id;
		nombre = n;
		tipoDeGanado = t;
		detailList = new ArrayList();
	}


	//	FUNCIONES
	public void addDetail(DetailFormulaCreada d){
		detailList.add(d);
	}
	public List getDetailList() {
		return detailList;
	}

	public void setDetailList(List detailList) {
		this.detailList = (ArrayList) detailList;
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
	
	public String getTipoDeGanado() {
		return tipoDeGanado;
	}
	
	public void setTipoDeGanado(String tipoDeGanado) {
		this.tipoDeGanado = tipoDeGanado;
	}
}
