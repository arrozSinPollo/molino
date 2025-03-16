package utils;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

public class MyTableModel extends DefaultTableModel {
	
	// ATRIBUTOS
	private Object[] columnNames = new Object[] {"ID", "FECHA", "PRODUCTO", "PROVEEDOR", 
			"Nº  KG", "EN ALMACEN", "LOTE", "ALBARAN", "UBICACION",
			"OBSERVACIONES", "CONFORMIDAD"};
    private Object[][] data = null;//same as before...

    
    // CONSTRUCTORES
    public MyTableModel (){
		super();
	}
    public MyTableModel(Object[][] data2, Object[] headers) {
    	super();
	}
    
    
    // FUNCIONES
	public int getColumnCount() {
        return columnNames.length;
    }

    /*public int getRowCount() {
        return data.length;
    }*/

    public String getColumnName(int col) {
        return (String) columnNames[col];
    }

    public Object getValueAt(int row, int col) {
        return data[row][col];
    }

    public Class getColumnClass(int c) {
    	if (c==4) return Float.class;
    	if (c==8) return Boolean.class;
        return Object.class;//getValueAt(0, c).getClass();
    }
   
    public boolean isCellEditable(int row, int col) {
        return false;
    }

    public void setValueAt(Object value, int row, int col) {
        data[row][col] = value;
        fireTableCellUpdated(row, col);
    }
}