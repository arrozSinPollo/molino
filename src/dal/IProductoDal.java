package dal;

import domain.Producto;

public interface IProductoDal {

	public void insert(Producto prod) throws DalException;
	public void modify(Producto prod) throws DalException;
	public void delete(Producto prod) throws DalException;
	
}