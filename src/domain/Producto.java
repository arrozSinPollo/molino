package domain;

public class Producto {
	
	//	ATRIBUTOS
	private int productoID;
	private String nombre;
	private String tipo;
	
	
	//	CONSTRUCTORES
	public Producto(){
		super();
		this.productoID = -1;
		this.nombre = "";
		this.tipo = "";
	}
	
	public Producto(int id, String n, String t){
		super();
		this.productoID = id;
		this.nombre = n;
		this.tipo = t;
	}

	public Producto(String n, String t){
		this(-1,n,t);
	}
	
	//	FUNCIONES
	public String getName(){
		return this.nombre;
	} 
	
	public void setName(String n){
		this.nombre = n;
	}
	
	public String getTipo(){
		return this.tipo;
	} 
	
	public void setTipo(String t){
		this.tipo = t;
	}
	
	public String toString(){
		return nombre + "\t\t" + tipo.toString();
	}

	public int getProductoID() {
		return productoID;
	}

	public void setProductoID(int productoID) {
		this.productoID = productoID;
	}

}
