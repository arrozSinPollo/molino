package presentation.WindowsApp;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.view.JasperViewer;

import mysqldal.MySQLJDBCFacade;
import presentation.PresentationApp;
import utils.PropertiesUtil;
import dal.DalException;
import dal.Transactionable;


public class CantidadEnAlmacen extends Transactionable implements PresentationApp {

	private JScrollPane jTableScroller;
	private DefaultTableModel jTableDetailsModel;
	private JLabel jLabelTitulo;

	//Tabla que contiene los id's de las formulas buscadas
	private float cantidadTotal;
	private JButton jButtonImprimir;
	private JTable jTableRegistros;
	private static final String productoColumn = "producto";
	private static final String enAlmacenColumn = "enAlmacen";
	private static final String tableName = "registrosDeRecepcion";
		
	// Variables de impresión
	private static final String reportName = "Informe_Cantidad_En_Almacen.jrxml";
	JasperReport jasperReport;
    JasperPrint jasperPrint=null;
    Map<String, String> parameters = new HashMap<String, String>();
    JRTableModelDataSource ds = null;
    TableModel modelo = null;

	public CantidadEnAlmacen() {
		super(new MySQLJDBCFacade());
	}

	public void showMainMenu() {
		JFrame JFprincipal = new JFrame("Molino v2.0");
		GridBagConstraints constraints = new GridBagConstraints();
		
		//Indico el layout de JFprincipal
		GridBagLayout JFprincipalLayout = new GridBagLayout();
		JFprincipal.getContentPane().setLayout(JFprincipalLayout);
		JFprincipalLayout.rowHeights = new int[] {51, 20, 21, 20, 12, 20, 20, 20, 12, 20, 12, 20, 12, 20, 12, 21, 12, 21, 12, 21, 20, 7, 20, 7};
		JFprincipalLayout.rowWeights = new double[] {0.0, 0.1, 0.0, 0.0, 0.0, 0.0, 0.1, 0.1, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.1, 0.1, 0.1};
		JFprincipalLayout.columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.1, 0.0, 0.0, 0.0, 0.0, 0.1, 0.1};
		JFprincipalLayout.columnWidths = new int[] {35, 91, 112, 88, 20, -3, 140, 149, 36, 20, 7};
		
		//Añado los componentes a JFprincipal
		jLabelTitulo = new JLabel("Molino v2.0");
		constraints.gridx = 0; // El área de texto empieza en la columna cero.
		constraints.gridy = 0; // El área de texto empieza en la fila uno.
		constraints.gridwidth = 4; // El área de texto ocupa 4 columnas.
		constraints.gridheight = 1; // El área de texto ocupa 1 filas.
		constraints.weighty = 1.0; // La fila 1 debe estirarse, le ponemos 1.0
		JFprincipal.getContentPane().add(jLabelTitulo, new GridBagConstraints(6, 0, 2, 1, 0.0, 1.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		jLabelTitulo.setBackground(new java.awt.Color(0,0,160));
		jLabelTitulo.setFont(new java.awt.Font("Arial Black",1,18));
		jLabelTitulo.setText("Cantidad de Producto en Almacén");
		jLabelTitulo.setForeground(new java.awt.Color(0,0,160));
		jLabelTitulo.setBorder(new LineBorder(new java.awt.Color(0,0,0), 3, false));
		jLabelTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelTitulo.setHorizontalTextPosition(SwingConstants.CENTER);
		{
			jTableScroller = new JScrollPane();
			JFprincipal.getContentPane().add(jTableScroller, new GridBagConstraints(1, 1, 8, 20, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
			jTableScroller.setPreferredSize(new Dimension(250, 472));
			jTableScroller.setBorder(new LineBorder(
					new java.awt.Color(0, 0, 0), 3, true));
			{
				TableModel jTableRegistrosModel = new DefaultTableModel(
						new String[][] { { "One", "Two" }, { "Three", "Four" } },
						new String[] { "Column 1", "Column 2" });
				jTableRegistros = new JTable();
				jTableScroller.setViewportView(jTableRegistros);
				jTableRegistros.setModel(jTableRegistrosModel);
				jTableRegistros.setToolTipText("Lista de Registros de Entrada");
				jTableRegistros.setBorder(new LineBorder(new java.awt.Color(0,
						0, 0), 0, false));
				jTableRegistros.setMaximumSize(new java.awt.Dimension(0, 0));
			}
		}
		{
			jButtonImprimir = new JButton();
			JFprincipal.getContentPane().add(jButtonImprimir, new GridBagConstraints(6, 22, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
			jButtonImprimir.setText("Imprimir");
			jButtonImprimir.setVisible(false);
			jButtonImprimir.setToolTipText("Imprimir las cantidades totales actuales en el almacén");
		}
		constraints.weighty = 0;

		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;


		//Iniciador de eventos
		initEventos();
		
//		MUESTRO EL FRAME CENTRADO
		JFprincipal.pack();
		JFprincipal.setSize(818, 607);
        //Se obtienen las dimensiones en pixels de la pantalla.
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        //Se obtienen las dimensiones en pixels de la ventana.
        Dimension ventana = JFprincipal.getSize();
        //Calculo para situar la ventana en el centro de la pantalla.
        JFprincipal.setLocation((pantalla.width - ventana.width) / 2,
        						(pantalla.height - ventana.height) / 2);
        //Se visualiza la ventana.
		JFprincipal.setVisible(true);
		JFprincipal.setIconImage(new ImageIcon(getClass().getClassLoader().getResource("Imágenes/icono_silos_.JPG")).getImage());
		JFprincipal.setTitle("Cantidades de producto disponibles en el almacén");
		JFprincipal.setPreferredSize(new java.awt.Dimension(818, 607));

		//Cargo la tabla con las cantidades totales en el almacén
		try {
			cargarTableDetails();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (DalException e) {
			e.printStackTrace();
		}
	}
	
	private void initEventos(){
		
		//		Imprimir entradas
		jButtonImprimir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButtonImprimirActionPerformed(evt);
			}
		});
		
	}

	private void cargarTableDetails() throws SQLException, DalException{
		ResultSet rs, rsAux;
		
		Object[][] data = null;
		Object[] headers = new Object[] {"PRODUCTO", "Nº KG EN ALMACEN"};
		
		jTableDetailsModel = new DefaultTableModel(data,headers);
		
		try {
			//System.out.println("\n++++++++++++++++++++++++++++++++++++++++++++\nSELECT DISTINCT " + productoColumn + " FROM " + tableName +
			//											" ORDER BY " + productoColumn);
			rs=(ResultSet) getFacade().executeQuery("SELECT DISTINCT " + productoColumn + " FROM producto" +
														" ORDER BY " + productoColumn);
		} catch (SQLException e) {
			throw new DalException(e);
		} 
		
		//Por cada DISTINCT producto... selecciono todos los que tengan enAlmacenColumn > 0
		while (rs.next()){
			cantidadTotal = 0;
			String producto = rs.getString(productoColumn);
			try {
				//rsAux=(ResultSet) getFacade().executeQuery("SELECT * FROM " + tableName +
				//											" WHERE " + productoColumn + " = '" + producto +
				//												"' AND " + enAlmacenColumn + " > 0");
				rsAux=(ResultSet) getFacade().executeQuery("SELECT * FROM " + tableName +
															" WHERE " + productoColumn + " = '" + producto + "'");
			} catch (SQLException e) {
				throw new DalException(e);
			} 
			
			//RECORRO RSAUX Y SUMO CANTIDADES			
			while (rsAux.next()){
				cantidadTotal = cantidadTotal + rsAux.getFloat(enAlmacenColumn);
			}
			
			//IF cantidadTotal > 0 ==> añado 
			if (cantidadTotal > 0){
				Object [] row = new Object[]{producto, String.valueOf(cantidadTotal)};
				jTableDetailsModel.addRow(row);
				jTableRegistros.setModel(jTableDetailsModel);
			} else {
				Object [] row = new Object[]{producto, "0"};
				jTableDetailsModel.addRow(row);
				jTableRegistros.setModel(jTableDetailsModel);
			}
			jButtonImprimir.setVisible(true);
		}
	}
	
	
	private void jButtonImprimirActionPerformed(ActionEvent evt) {
		try
	    {
	        // Llenamos los parámetros
	    	
	    	// Llenamos los detalles uno a uno
	    	Vector<String> tableColumns = new Vector<String>();
            tableColumns.add("producto");
            tableColumns.add("cantidad");
            //tableColumns.add("Fecha alta");
            //tableColumns.add("Provincia");
            //tableColumns.add("Municipio");
            //tableColumns.add("NumExpIns");
      
            modelo = new DefaultTableModel(tableColumns,jTableDetailsModel.getRowCount());
            for(int i = 0; i < jTableDetailsModel.getRowCount() ;i++){
            	modelo.setValueAt(String.valueOf(jTableDetailsModel.getValueAt(i, 0)) , i, 0);
                modelo.setValueAt(String.valueOf(jTableDetailsModel.getValueAt(i, 1)) , i, 1);
            }
            ds = new JRTableModelDataSource(modelo);
     	    
            //1-Compilamos el archivo XML y lo cargamos en memoria
            PropertiesUtil properties = new PropertiesUtil();
            System.out.println("1-Compilamos el archivo XML y lo cargamos en memoria: " + properties.getProperty(PropertiesUtil.reportsPath) + reportName);
	    	jasperReport = JasperCompileManager.compileReport(properties.getProperty(PropertiesUtil.reportsPath) + reportName);
	    	
	        //2-Llenamos el reporte con la información y parámetros necesarios (En este caso nada)
	    	System.out.println("2-Llenamos el reporte con la información y parámetros necesarios");	        
	        jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, ds);

	        //3-Mostramos el resultado si todo ha ido bien.
   	        JasperViewer.viewReport(jasperPrint,false);

	    }
	    catch (JRException e)
	    {
	      e.printStackTrace();
	    }
	}
}
