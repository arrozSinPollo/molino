package dal;

import domain.RegistroDeRecepcion;

public interface IRegistroDeRecepcionDal  {

	public void insert(RegistroDeRecepcion reg) throws DalException;
	public void modify(RegistroDeRecepcion reg) throws DalException;
	public void delete(RegistroDeRecepcion reg) throws DalException;
	
}