package dal;

import java.util.List;

import domain.Formula;
import domain.FormulasCreadas;

public interface IFormulaCreadaDal {
	
	public void insert(FormulasCreadas form) throws DalException;
	public void modify(FormulasCreadas form) throws DalException;
	public void delete(FormulasCreadas form) throws DalException;
	public IDetailFormulaDal getDetailsDal();
	public FormulasCreadas findFormula(String nombre, String tipoDeGanado) throws DalException;
	public List findAllFormulas() throws DalException;

}
