package business;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import dal.*;
import domain.*;


public class PosProcessor {

	IFormulaDal formulasDal;
	IDetailFormulaDal detailDal;

	public PosProcessor() throws SecurityException, IllegalArgumentException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException
	{
		detailDal=DalFactory.getInstance().createDetailDal();
		formulasDal=DalFactory.getInstance().createFormulaDal(detailDal);
		
	}
	
	public void placeOrder(Formula order) throws DalException, SecurityException, IllegalArgumentException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		
		ITransaction newTransaction=TransactionFactory.getInstance().createTransaction();
		newTransaction.beginTransaction();
		try {
			((Transactionable)formulasDal.getDetailsDal()).setContext(newTransaction);
			((Transactionable)formulasDal).setContext(newTransaction);
			formulasDal.insert(order);
			newTransaction.commit();
		} catch(Exception e) {
			newTransaction.rollback();
			throw new DalException(e);
		}
	}
	

	public Formula findOrderByID(String formulaID) throws DalException {
		Formula requestedOrder=(Formula)(formulasDal).findFormulaByNombre(formulaID);
		return requestedOrder;
	}
	
	public List findAllOrders() throws DalException {
		List orderList = formulasDal.findAllFormulas();
		return orderList;
	}

	

}
