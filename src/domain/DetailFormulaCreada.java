package domain;

public class DetailFormulaCreada {
	
	
	//	ATRIBUTOS
	private int DetailFormulaID;
	private int formulaID;
	private String nombre;
	private float kg;

	
	//	CONSTRUCTORES
	public DetailFormulaCreada() {
		super();
		this.DetailFormulaID = -1;
		this.formulaID = -1;
		this.nombre = "";
		this.kg = 0;
	}
	
	public DetailFormulaCreada(int id_det, int id, String n, float kg) {
		super();
		this.DetailFormulaID = id_det;
		this.formulaID = id;
		this.nombre = n;
		this.kg = kg;
	}


	//	FUNCIONES
	public int getDetailFormulaID() {
		return DetailFormulaID;
	}

	public void setDetailFormulaID(int detailFormulaID) {
		DetailFormulaID = detailFormulaID;
	}

	public int getFormulaID() {
		return formulaID;
	}

	public void setFormulaID(int formulaID) {
		this.formulaID = formulaID;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public float getKg() {
		return kg;
	}

	public void setKg(float kg) {
		this.kg = kg;
	}

}
