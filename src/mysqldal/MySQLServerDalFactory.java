package mysqldal;

import dal.*;

public class MySQLServerDalFactory implements IDalFactory{

	private static MySQLServerDalFactory instance;
	
	
	public static MySQLServerDalFactory getInstance()
	{
		if (instance==null)
			instance=new MySQLServerDalFactory();
		
		return instance;
	}
	
	public IFormulaDal createFormulaDal(IDetailFormulaDal detailDal) {
		return new MySQLFormulaDal(detailDal);
	}

	public IDetailFormulaDal createDetailDal() {
		return new MySQLDetailDal();
	}

	public void databaseStartup() {
		// TODO Auto-generated method stub
		
	}
}
