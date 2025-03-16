package dal;

import domain.*;

public interface IRegistroDal {
	public void insert(RegistroDeRecepcion r) throws DalException;
	public void delete(RegistroDeRecepcion r) throws DalException;
	public void modify(RegistroDeRecepcion r) throws DalException;
	public void updateDetailStatus(DetailFormula detail, Formula order) throws DetailOutOfDateException, DalException;
	public RegistroDeRecepcion getOldestDetailFormula(DetailFormula detail);
}
