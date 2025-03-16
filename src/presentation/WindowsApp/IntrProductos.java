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

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import mysqldal.MySQLJDBCFacade;
import mysqldal.MySQLProductoDal;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.view.JasperViewer;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import presentation.PresentationApp;
import utils.PropertiesUtil;
import dal.DalException;
import dal.Transactionable;
import domain.Producto;


public class IntrProductos extends Transactionable implements PresentationApp {

	private JButton jButtonProductos;
	private JButton jButtonEliminar;
	private JLabel jLabel2;
	private JLabel jLabel1;
	private JComboBox jComboBoxTipos;
	private DefaultComboBoxModel jComboBoxTiposModel;
	private JLabel jLabel3;
	private JButton jButtonImprimir;
	private JTextField jTextFieldNombre;
	private JList jListProductos;
	private JScrollPane jListScroller;
	private DefaultListModel jListProductosModel;
	private JLabel jLabelTitulo;
	private MySQLProductoDal pDal;
	private Producto p, pEliminar;
	
//	 Variables de impresión 
	private static final String reportName = "Informe_Productos.jrxml";
	JasperReport jasperReport;
    JasperPrint jasperPrint=null;
    Map<String, String> parameters = new HashMap<String, String>();
    JRTableModelDataSource ds = null;
    TableModel modelo = null;
    ResultSet rsImprimir=null;
    
    // Log
    private static Logger logger;

	public IntrProductos() {
		super(new MySQLJDBCFacade());
		p = new Producto();
		pDal = new MySQLProductoDal();
		
		logger = Logger.getLogger(this.getClass().getName());
		logger.setLevel(Level.INFO);
	    try {
		    PropertyConfigurator.configure("log4j_console.properties");
	    }catch (Exception e) {
			System.out.println("ERROR: " + e.getMessage() + " - " + e.getCause());
	    }
   
	    logger.setLevel(Level.INFO);
	}

	public void showMainMenu() {
		JFrame JFprincipal = new JFrame("Molino v2.0");
		GridBagConstraints constraints = new GridBagConstraints();
		
		//Indico el layout de JFprincipal
		GridBagLayout JFprincipalLayout = new GridBagLayout();
		JFprincipal.getContentPane().setLayout(JFprincipalLayout);
		JFprincipalLayout.rowHeights = new int[] {51, 30, 14, 20, 14, 27, 20, 20, 20, 7, 7, 7, 7, 7, 7, 7, 7, 7};
		JFprincipalLayout.rowWeights = new double[] {0.0, 0.0, 0.0, 0.1, 0.0, 0.0, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.0, 0.1};
		JFprincipalLayout.columnWeights = new double[] {0.1, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.1};
		JFprincipalLayout.columnWidths = new int[] {20, 33, 66, -3, 17, 140, 149, 9, 7};
		
		//Añado los componentes a JFprincipal
		jLabelTitulo = new JLabel("Molino v2.0");
		constraints.gridx = 0; // El área de texto empieza en la columna cero.
		constraints.gridy = 0; // El área de texto empieza en la fila uno.
		constraints.gridwidth = 4; // El área de texto ocupa 4 columnas.
		constraints.gridheight = 1; // El área de texto ocupa 1 filas.
		constraints.weighty = 1.0; // La fila 1 debe estirarse, le ponemos 1.0
		JFprincipal.getContentPane().add(jLabelTitulo, new GridBagConstraints(5, 0, 2, 1, 0.0, 1.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		jLabelTitulo.setBackground(new java.awt.Color(0,0,160));
		jLabelTitulo.setFont(new java.awt.Font("Arial Black",1,18));
		jLabelTitulo.setText("Gestión de Productos");
		jLabelTitulo.setForeground(new java.awt.Color(0,0,160));
		jLabelTitulo.setBorder(new LineBorder(new java.awt.Color(0,0,0), 3, false));
		jLabelTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelTitulo.setHorizontalTextPosition(SwingConstants.CENTER);
		constraints.weighty = 0;
		
		jButtonProductos = new JButton ("Productos");
		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		JFprincipal.getContentPane().add(jButtonProductos, new GridBagConstraints(6, 6, 2, 2, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		jButtonProductos.setText("Añadir   Producto");
		jButtonProductos.setToolTipText("Añadir Nuevo Producto");

		jButtonEliminar = new JButton ("Proveedores");
		constraints.gridx = 1;
		constraints.gridy = 3;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		JFprincipal.getContentPane().add(jButtonEliminar, new GridBagConstraints(6, 15, 2, 2, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		jButtonEliminar.setText("Eliminar Producto");
		jButtonEliminar.setToolTipText("Eliminar Producto Seleccionado");
		jButtonEliminar.setEnabled(false);

		{
			jTextFieldNombre = new JTextField();
			JFprincipal.getContentPane().add(jTextFieldNombre, new GridBagConstraints(1, 3, 6, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		}
		{
			jComboBoxTiposModel = new DefaultComboBoxModel();
			jComboBoxTipos = new JComboBox();
			JFprincipal.getContentPane().add(jComboBoxTipos, new GridBagConstraints(1, 5, 6, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
			jComboBoxTipos.setModel(jComboBoxTiposModel);
			jComboBoxTipos.setToolTipText("Tipo de producto");
		}
		{
			jLabel1 = new JLabel();
			JFprincipal.getContentPane().add(jLabel1, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			jLabel1.setText("Nombre del Producto");
		}
		{
			jLabel2 = new JLabel();
			JFprincipal.getContentPane().add(jLabel2, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			jLabel2.setText("Tipo de Producto");
		}
		{
			jLabel3 = new JLabel();
			JFprincipal.getContentPane().add(jLabel3, new GridBagConstraints(1, 7, 6, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			jLabel3.setText("Lista de Productos");
		}
		{
			jListProductosModel = new DefaultListModel();
			jListProductos = new JList();
			JFprincipal.getContentPane().add(jListProductos, new GridBagConstraints(1, 9, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			jListProductos.setModel(jListProductosModel);
			jListProductos.setBorder(new LineBorder(new java.awt.Color(0,0,0), 0, false));
			jListProductos.setToolTipText("Lista de Productos");
			jListProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			jListProductos.setMaximumSize(new java.awt.Dimension(0, 0));
			jListScroller = new JScrollPane(jListProductos);
			JFprincipal.getContentPane().add(jListScroller, new GridBagConstraints(1, 8, 6, 6, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
			jListScroller.setPreferredSize(new Dimension(250, 472));
			jListScroller.setBorder(new LineBorder(new java.awt.Color(0,0,0), 3, true));
		}
		{
			jButtonImprimir = new JButton();
			JFprincipal.getContentPane().add(jButtonImprimir, new GridBagConstraints(5, 15, 1, 2, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			jButtonImprimir.setEnabled(true);
			jButtonImprimir.setText("Imprimir listado de Productos");
			jButtonImprimir.setToolTipText("Imprimir listado de Productos");
		}

		//Iniciador de eventos
		initEventos();
		
		//MUESTRO EL FRAME CENTRADO
		JFprincipal.pack();
		JFprincipal.setSize(633, 531);
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
		JFprincipal.setTitle("Gestión de Productos");
		JFprincipal.setPreferredSize(new java.awt.Dimension(633, 531));

		try {
			cargarComboTipos();
			cargarListaProductos();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private void initEventos(){
		
		jListProductos.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent evt) {
				jListProductosValueChange(evt);
			}
		});
		
		jButtonEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButtonEliminarActionPerformed(evt);
			}
		});
		
		jButtonProductos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButtonProductosActionPerformed(evt);
			}
		});
		
//		Imprimir entradas
		jButtonImprimir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				try {
					jButtonImprimirActionPerformed(evt);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (DalException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
	}
	
	private void cargarComboTipos() throws SQLException, DalException{
		ResultSet rs;
		
		jComboBoxTipos.removeAllItems();
		
		try {
			rs=(ResultSet) getFacade().executeQuery("SELECT * FROM Tipo ORDER BY tipo");
		} catch (SQLException e) {
			throw new DalException(e);
		} //finally {
			//getFacade().endCurrentOperation();
		//}
		
		while (rs.next()){
			jComboBoxTipos.addItem(rs.getString("tipo"));
			
		}	
	}
	
	private void cargarListaProductos() throws SQLException, DalException{
		ResultSet rs;
		
		jListProductosModel = new DefaultListModel();
		
		try {
			rs=(ResultSet) getFacade().executeQuery("SELECT * FROM Producto ORDER BY producto");
		} catch (SQLException e) {
			throw new DalException(e);
		} //finally {
			//getFacade().endCurrentOperation();
		//}
		
		while (rs.next()){
			jListProductosModel.addElement(rs.getString("producto"));
			jListProductos.setModel(jListProductosModel);
		}		
		jListProductos.setModel(jListProductosModel);
	}
	
	private void jButtonProductosActionPerformed(ActionEvent evt){
		
		if (jTextFieldNombre.getText().equals("")) {
			JOptionPane.showMessageDialog(Principal.msgbox,
				    "Debe introducir un NOMBRE para el nuevo producto",
				    "Campo Incompleto",
				    JOptionPane.WARNING_MESSAGE);
		} else if (this.jTextFieldNombre.getText() != "" && p.getProductoID() == -1){
			p.setName(this.jTextFieldNombre.getText());
			p.setTipo(this.jComboBoxTipos.getSelectedItem().toString());
			try {
				pDal.insert(p);
				cargarListaProductos();
				jTextFieldNombre.setText("");
				logger.info("Producto añadido: " + p.getName() + " - " + p.getTipo());
				
			} catch (DalException e) {
				logger.error("ERROR al añadir el producto: " + p.getName() + " - " + p.getTipo());
				System.out.println("ERROR al añadir el producto: " + p.getName() + " - " + p.getTipo());
			} catch (SQLException e) {
				logger.error("ERROR al añadir el producto: " + p.getName() + " - " + p.getTipo());
				System.out.println("ERROR al añadir el producto: " + p.getName() + " - " + p.getTipo());
			}	
		}
	}
	
	private void jButtonEliminarActionPerformed(ActionEvent evt) {

		try {
			pDal.delete(pEliminar);
			logger.info("Producto eliminado: " + pEliminar.getName());
			cargarListaProductos();
			
		} catch (DalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void jListProductosValueChange(ListSelectionEvent e) {
		pEliminar = new Producto();
		
		if (e.getValueIsAdjusting() == false) {
	        if (jListProductos.getSelectedIndex() == -1) {
	        //No selection, deshabilito botón "Eliminar"
	            jButtonEliminar.setEnabled(false);

	        } else {
	        //Selection, Habilito botón "Eliminar" y creo el producto con nombre de la lista
	        	jButtonEliminar.setEnabled(true);
	        	pEliminar.setName(jListProductos.getSelectedValue().toString());
	        }
	    }
	}


	private void jButtonImprimirActionPerformed(ActionEvent evt) throws SQLException, DalException{
		try
	    {
	        // Llenamos los parámetros
	    	
	    	// Llenamos los detalles uno a uno
	    	Vector<String> tableColumns = new Vector<String>();
            tableColumns.add("producto");
            tableColumns.add("categoria");
            //tableColumns.add("Fecha alta");
            //tableColumns.add("Provincia");
            //tableColumns.add("Municipio");
            //tableColumns.add("NumExpIns");
      
            try {
            	rsImprimir=(ResultSet) getFacade().executeQuery("SELECT * FROM Producto ORDER BY producto");
    		} catch (SQLException e) {
    			throw new DalException(e);
    		} //finally {
    			//getFacade().endCurrentOperation();
    		//}
    		int i = 0;
    		modelo = new DefaultTableModel(tableColumns,jListProductosModel.size());
    		while (rsImprimir.next()){
    			modelo.setValueAt(rsImprimir.getString("producto") , i, 0);
    			modelo.setValueAt(rsImprimir.getString("tipo") , i, 1);
    			i++;
    		}	
            
            ds = new JRTableModelDataSource(modelo);
     	    
            //1-Compilamos el archivo XML y lo cargamos en memoria
            PropertiesUtil properties = new PropertiesUtil();
            logger.info("Compilamos el archivo XML y lo cargamos en memoria: " + properties.getProperty(PropertiesUtil.reportsPath) + reportName);
            //System.out.println("1-Compilamos el archivo XML y lo cargamos en memoria: " + properties.getProperty(PropertiesUtil.reportsPath) + reportName);
	    	jasperReport = JasperCompileManager.compileReport(properties.getProperty(PropertiesUtil.reportsPath) + reportName);
	    	
	        //2-Llenamos el reporte con la información y parámetros necesarios (En este caso nada)
	    	logger.info("Llenamos el reporte con la información y parámetros necesarios");
	    	//System.out.println("2-Llenamos el reporte con la información y parámetros necesarios");	        
	        jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, ds);

	        //3-Mostramos el resultado si todo ha ido bien.
	        logger.info("Visualizamos el informe");
	        JasperViewer.viewReport(jasperPrint,false);

	    }
	    catch (Exception e)
	    {
	      logger.error(e.getMessage());
	    }
	}

}
