package dal;

import java.sql.Date;
import java.util.List;

import domain.Formula;
import domain.FormulasCreadas;

public interface IFormulaDal{
	
	public void insert(Formula form) throws DalException;
	public void modify(Formula form) throws DalException;
	public void delete(Formula form) throws DalException;
	public IDetailFormulaDal getDetailsDal();
	public Formula findFormulaByNombre(String nombre) throws DalException;
	public List findAllFormulas() throws DalException;
	
}
