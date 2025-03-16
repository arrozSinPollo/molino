package domain;

public class Ubicacion {

	//ATRIBUTOS
	private int ubicacionID;
	private String nombre;
	
	//CONSTRUCTORES
	public Ubicacion(){
		super();
		this.ubicacionID = -1;
		this.nombre = "";
	}
	
	public Ubicacion(int id, String n){
		super();
		this.ubicacionID = id;
		this.nombre = n;
	}
	
	//FUNCIONES
	public String getName(){
		return this.nombre;
	}
	
	public void setName(String n){
		this.nombre = n;
	}
	public String toString(){
		return nombre;
	}

	public int getUbicacionID() {
		return ubicacionID;
	}

	public void setUbicacionID(int ubicacionID) {
		this.ubicacionID = ubicacionID;
	}
}
