package dal;

import java.util.List;
import domain.DetailFormula;


public interface IDetailFormulaDal{
	public void insert(DetailFormula det) throws DalException;
	public void insert(List l) throws DalException;
	public void modify(DetailFormula det) throws DalException;
	public void delete(DetailFormula det) throws DalException;
	//public void updateDetailStatus(DetailFormula det, Formula form) throws DetailOutOfDateException, DalException;
	public void setOldestRegistro(DetailFormula det);
}
