package domain;

public class Tipo {
	
	private int tipoID;
	private String tipo;
	
	public Tipo(String t){
		super();
		tipo = t;
	}

	public Tipo(){
		super();
		tipoID = -1;
		tipo="";
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getTipoID() {
		return tipoID;
	}
	
	public void setTipoID (int id) {
		tipoID = id;
	}

	public String toString(){
		return tipo;
	}

}
