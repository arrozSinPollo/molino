package dal;

import domain.Tipo;

public interface ITipoDal {

	public void insert(Tipo t) throws DalException;
	public void modify(Tipo t) throws DalException;
	public void delete(Tipo t) throws DalException;
	
}
