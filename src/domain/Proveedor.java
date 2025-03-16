package domain;

public class Proveedor {

	//ATRIBUTOS
	private int proveedorID;
	private String nombre;
	
	//CONSTRUCTORES
	public Proveedor(){
		super();
		this.proveedorID = -1;
		this.nombre = "";
	}
	
	public Proveedor(int id, String n){
		super();
		this.proveedorID = id;
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

	public int getProveedorID() {
		return proveedorID;
	}

	public void setProveedorID(int proveedorID) {
		this.proveedorID = proveedorID;
	}
}
