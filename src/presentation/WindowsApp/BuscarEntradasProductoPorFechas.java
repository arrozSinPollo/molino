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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import mysqldal.MySQLJDBCFacade;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.view.JasperViewer;
import presentation.PresentationApp;
import utils.MyTableModel;
import utils.PropertiesUtil;

import com.toedter.calendar.JDateChooser;

import dal.DalException;
import dal.Transactionable;
/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class BuscarEntradasProductoPorFechas extends Transactionable implements PresentationApp {

	private JTable jTableRegistros;
	private JScrollPane jTableScroller;
	private DefaultTableModel jTableDetailsModel;
	private JLabel jLabelTitulo;
	private JButton jButtonBuscar;
	private JPanel jPanelDetalles;
	private JLabel jLabelFechaFinal;
	private JLabel jLabelFechaInicial;
	private JDateChooser jDateChooserFinal;
	private JButton jButtonImprimir;
	private JDateChooser jDateChooserInicial;
	private JPanel jPanelBusqueda;
	
	private String fechaInicial, fechaFinal;

	// Valores de los campos de la tabla
	private static final String fechaColumn = "fecha";
	private static final String productoColumn = "producto";
	private static final String kgColumn = "kg";
	private static final String tableName = "registrosDeRecepcion";

	// Variables de impresión
	private static final String reportName = "Informe_Productos_Entrada.jrxml";
	JasperReport jasperReport;
    JasperPrint jasperPrint=null;
    Map<String, String> parameters = new HashMap<String, String>();
    JRTableModelDataSource ds = null;
    TableModel modelo = null;
    
	public BuscarEntradasProductoPorFechas() {
		super(new MySQLJDBCFacade());
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	public void showMainMenu() {
		JFrame JFprincipal = new JFrame("Molino v2.0");
		GridBagConstraints constraints = new GridBagConstraints();
		
		//Indico el layout de JFprincipal
		GridBagLayout JFprincipalLayout = new GridBagLayout();
		JFprincipal.getContentPane().setLayout(JFprincipalLayout);
		JFprincipalLayout.rowHeights = new int[] {51, 20, 21, 20, 12, 20, 20, 20, 12, 20, 12, 20, 12, 20, 12, 21, 12, 21, 12, 21, 107, 124, 14, 20, 0, 7, 7};
		JFprincipalLayout.rowWeights = new double[] {0.0, 0.1, 0.0, 0.0, 0.0, 0.0, 0.1, 0.1, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.1, 0.1};
		JFprincipalLayout.columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.1};
		JFprincipalLayout.columnWidths = new int[] {48, 91, 112, 98, 10, -3, 192, 216, 16, 7};
		
		//Añado los componentes a JFprincipal
		jLabelTitulo = new JLabel("Molino v2.0");
		constraints.gridx = 0; // El área de texto empieza en la columna cero.
		constraints.gridy = 0; // El área de texto empieza en la fila uno.
		constraints.gridwidth = 4; // El área de texto ocupa 4 columnas.
		constraints.gridheight = 1; // El área de texto ocupa 1 filas.
		constraints.weighty = 1.0; // La fila 1 debe estirarse, le ponemos 1.0
		JFprincipal.getContentPane().add(jLabelTitulo, new GridBagConstraints(4, 0, 4, 1, 0.0, 1.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		jLabelTitulo.setBackground(new java.awt.Color(0,0,160));
		jLabelTitulo.setFont(new java.awt.Font("Arial Black",1,18));
		jLabelTitulo.setText("Entradas de Producto entre Fechas");
		jLabelTitulo.setForeground(new java.awt.Color(0,0,160));
		jLabelTitulo.setBorder(new LineBorder(new java.awt.Color(0,0,0), 3, false));
		jLabelTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelTitulo.setHorizontalTextPosition(SwingConstants.CENTER);
		constraints.weighty = 0;

		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;

		{
			jTableDetailsModel = new MyTableModel();
			jTableRegistros = new JTable();
			//JFprincipal.getContentPane().add(jTableRegistros, new GridBagConstraints(1, 9, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			jTableRegistros.setModel(jTableDetailsModel);
			jTableRegistros.setBorder(new LineBorder(new java.awt.Color(0,0,0), 0, false));
			jTableRegistros.setToolTipText("Lista de Registros de Entrada");
			jTableRegistros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			jTableRegistros.setMaximumSize(new java.awt.Dimension(0, 0));
			jTableScroller = new JScrollPane(jTableRegistros);
			JFprincipal.getContentPane().add(jTableScroller, new GridBagConstraints(1, 8, 7, 14, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
			jTableScroller.setPreferredSize(new Dimension(250, 472));
			jTableScroller.setBorder(new LineBorder(new java.awt.Color(0,0,0), 3, true));
		}
		{
			jPanelDetalles = new JPanel();
			JFprincipal.getContentPane().add(jPanelDetalles, new GridBagConstraints(1, 9, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		}
		{
			jPanelBusqueda = new JPanel();
			GridBagLayout jPanel2Layout = new GridBagLayout();
			jPanel2Layout.rowWeights = new double[] {0.0, 0.0, 0.0};
			jPanel2Layout.rowHeights = new int[] {27, 10, 21};
			jPanel2Layout.columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.1};
			jPanel2Layout.columnWidths = new int[] {104, 144, 36, 150, 144, 62, 7};
			jPanelBusqueda.setLayout(jPanel2Layout);
			JFprincipal.getContentPane().add(jPanelBusqueda, new GridBagConstraints(1, 2, 7, 5, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
			jPanelBusqueda.setBackground(new java.awt.Color(192,192,192));
			{
				jButtonBuscar = new JButton();
				jPanelBusqueda.add(jButtonBuscar, new GridBagConstraints(4, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
				jButtonBuscar.setText("Buscar");
			}
			{
				jDateChooserInicial = new JDateChooser();
				jPanelBusqueda.add(jDateChooserInicial, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
			}
			{
				jDateChooserFinal = new JDateChooser();
				jPanelBusqueda.add(jDateChooserFinal, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
			}
			{
				jLabelFechaInicial = new JLabel();
				jPanelBusqueda.add(jLabelFechaInicial, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jLabelFechaInicial.setText("Fecha Inicial: ");
			}
			{
				jLabelFechaFinal = new JLabel();
				jPanelBusqueda.add(jLabelFechaFinal, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jLabelFechaFinal.setText("Fecha Final: ");
			}
		}
		{
			jButtonImprimir = new JButton();
			JFprincipal.getContentPane().add(jButtonImprimir, new GridBagConstraints(7, 23, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
			jButtonImprimir.setText("Imprimir");
			jButtonImprimir.setVisible(false);
			jButtonImprimir.setToolTipText("Imprimir las entradas de productos visualizadas");
		}

		//Iniciador de eventos
		initEventos();
		
//		MUESTRO EL FRAME CENTRADO
		JFprincipal.pack();
		JFprincipal.setSize(818, 754);
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
		JFprincipal.setTitle("Cantidad de entrada de productos entre fechas");
		JFprincipal.setPreferredSize(new java.awt.Dimension(818, 754));

	}
	
	private void initEventos(){
		
		//Realiza la busqueda entre las fechas introducidas
		jButtonBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButtonBuscarActionPerformed(evt);
			}
		});
		
		//Imprimir entradas
		jButtonImprimir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButtonImprimirActionPerformed(evt);
			}
		});
		
	}
	

	private void jButtonBuscarActionPerformed(ActionEvent evt){	
		if ((this.jDateChooserInicial.getDate() == null) || (this.jDateChooserFinal.getDate() == null)){
			JOptionPane.showMessageDialog(Principal.msgbox,
				    "Debe introducir las FECHAS de inicio y fin para realizar la busqueda",
				    "Campo Incompleto",
				    JOptionPane.WARNING_MESSAGE);
		} else if (this.jDateChooserFinal.getDate().before(this.jDateChooserInicial.getDate())){
			JOptionPane.showMessageDialog(Principal.msgbox,
				    "La fecha INICIAL introducida debe ser menor o igual que la FINAL",
				    "Campo Incompleto",
				    JOptionPane.WARNING_MESSAGE);
		} else {
			
			try {
				jButtonImprimir.setVisible(false);
				cargarTableDetails();
				System.out.println("Busqueda realizada con éxito");
			} catch (DalException e) {
				e.printStackTrace();
				System.out.println("ERROR al buscar el producto" );
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("ERROR  al buscar el producto");
			} 
		}
	}
	
	private void cargarTableDetails() throws SQLException, DalException{
		ResultSet rs, rsAux=null;
		float cantidadTotal;
		int numRegistros=0;
		Object[][] data = null;
		Object[] headers = new Object[] {"PRODUCTO", "Nº KG"};
		jTableDetailsModel = new DefaultTableModel(data,headers);
		java.text.DateFormat sdfAux = new java.text.SimpleDateFormat("yyyy/MM/dd");
		fechaInicial =  sdfAux.format(jDateChooserInicial.getDate());
		fechaFinal =  sdfAux.format(jDateChooserFinal.getDate());
	    
		try {
			rs=(ResultSet) getFacade().executeQuery("SELECT DISTINCT " + productoColumn + " FROM " + tableName +
														" WHERE ("+fechaColumn+" >= '" + fechaInicial + "' " +
															"AND "+fechaColumn+" <= '" + fechaFinal + "') " +
														" ORDER BY " + productoColumn);
		} catch (SQLException e) {
			throw new DalException(e);
		} 
		
		
		//Por cada DISTINCT producto... selecciono todos los que estén entre las fechas indicadas
		while (rs.next()){
			cantidadTotal = 0;
			String producto = rs.getString(productoColumn);
			
			try {
				rsAux=(ResultSet) getFacade().executeQuery("SELECT * FROM " + tableName + 
															" WHERE " + productoColumn + " = '" + producto + "'" +
																"AND ("+fechaColumn+" >= '" + fechaInicial + "' " +
																	"AND "+fechaColumn+" <= '" + fechaFinal + "')");
			} catch (SQLException e) {
				throw new DalException(e);
			} 
			
			
			//RECORRO RSAUX Y SUMO CANTIDADES	
			while (rsAux.next()){
				cantidadTotal = cantidadTotal + rsAux.getFloat(kgColumn);
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
			numRegistros++;
			jButtonImprimir.setVisible(true);
		}
		if (jTableDetailsModel.getRowCount()<=0){
			JOptionPane.showMessageDialog(Principal.msgbox,
				    "No hay entradas de producto entre las FECHAS indicadas - del " +fechaInicial+" al " + fechaFinal+" -",
				    "Campo Incompleto",
				    JOptionPane.WARNING_MESSAGE);
		}
	}
	
	private void jButtonImprimirActionPerformed(ActionEvent evt) {
		try
	    {
	        // Llenamos los parámetros
			java.text.DateFormat sdfAux = new java.text.SimpleDateFormat("dd/MM/yyyy");
			//fechaInicial =  sdfAux.format(jDateChooserInicial.getDate());
			//fechaFinal =  sdfAux.format(jDateChooserFinal.getDate());
	    	parameters.put("fecha_inicial", sdfAux.format(jDateChooserInicial.getDate()));
	    	parameters.put("fecha_final", sdfAux.format(jDateChooserFinal.getDate()));
	    	
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

