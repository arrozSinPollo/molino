package dal;

import java.util.List;
import domain.DetailFormula;
import domain.DetailFormulaCreada;


public interface IDetailFormulaCreadaDal{
	public void insert(DetailFormulaCreada det) throws DalException;
	public void insert(List l) throws DalException;
	public void modify(DetailFormulaCreada det) throws DalException;
	public void delete(DetailFormulaCreada det) throws DalException;
	//public void updateDetailStatus(DetailFormula det, Formula form) throws DetailOutOfDateException, DalException;
	public void setOldestRegistro(DetailFormulaCreada det);
}
