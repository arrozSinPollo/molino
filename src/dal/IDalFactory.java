package dal;

public interface IDalFactory {

	public IFormulaDal createFormulaDal(IDetailFormulaDal detailDal);
	
	public IDetailFormulaDal createDetailDal();
	
	public void databaseStartup();
	
}
