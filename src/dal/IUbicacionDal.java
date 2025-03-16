package dal;

import domain.Ubicacion;

public interface IUbicacionDal {

	public void insert(Ubicacion prov) throws DalException;
	public void modify(Ubicacion prov) throws DalException;
	public void delete(Ubicacion prov) throws DalException;
	
}