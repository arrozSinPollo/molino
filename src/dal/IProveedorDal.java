package dal;

import domain.Proveedor;

public interface IProveedorDal {

	public void insert(Proveedor prov) throws DalException;
	public void modify(Proveedor prov) throws DalException;
	public void delete(Proveedor prov) throws DalException;
	
}