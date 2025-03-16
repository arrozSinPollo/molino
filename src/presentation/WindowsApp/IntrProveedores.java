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

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.view.JasperViewer;

import mysqldal.MySQLJDBCFacade;
import mysqldal.MySQLProveedorDal;
import presentation.PresentationApp;
import utils.PropertiesUtil;
import dal.DalException;
import dal.Transactionable;
import domain.Proveedor;


public class IntrProveedores extends Transactionable implements PresentationApp {

	private JButton jButtonProveedores;
	private JButton jButtonEliminar;
	private JLabel jLabel1;
	private JLabel jLabel3;
	private JTextField jTextFieldNombre;
	private JList jListProveedores;
	private JScrollPane jListScroller;
	private DefaultListModel jListProveedoresModel;
	private JLabel jLabelTitulo;
	private MySQLProveedorDal pDal;
	private JButton jButtonImprimir;
	private Proveedor p;
	private Proveedor pEliminar;
	
//	 Variables de impresión
	private static final String reportName = "Informe_Proveedores.jrxml";
	JasperReport jasperReport;
    JasperPrint jasperPrint=null;
    Map<String, String> parameters = new HashMap<String, String>();
    JRTableModelDataSource ds = null;
    TableModel modelo = null;
    ResultSet rsImprimir=null;

	public IntrProveedores() {
		super(new MySQLJDBCFacade());
		p = new Proveedor();
		pDal = new MySQLProveedorDal();
	}

	/**
	 * 
	 */
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
		JFprincipal.getContentPane().add(jLabelTitulo, new GridBagConstraints(5, 0, 2, 1, 0.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		jLabelTitulo.setBackground(new java.awt.Color(0,0,160));
		jLabelTitulo.setFont(new java.awt.Font("Arial Black",1,18));
		jLabelTitulo.setText("Gestión de Proveedores");
		jLabelTitulo.setForeground(new java.awt.Color(0,0,160));
		jLabelTitulo.setBorder(new LineBorder(new java.awt.Color(0,0,0), 3, false));
		jLabelTitulo.setHorizontalTextPosition(SwingConstants.CENTER);
		jLabelTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		constraints.weighty = 0;
		
		jButtonProveedores = new JButton ("Proveedor");
		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		JFprincipal.getContentPane().add(jButtonProveedores, new GridBagConstraints(6, 5, 2, 2, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		jButtonProveedores.setText("Añadir   Proveedor");
		jButtonProveedores.setToolTipText("Añadir nuevo proveedor");

		jButtonEliminar = new JButton ("Proveedores");
		constraints.gridx = 1;
		constraints.gridy = 3;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		JFprincipal.getContentPane().add(jButtonEliminar, new GridBagConstraints(6, 15, 2, 2, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		jButtonEliminar.setText("Eliminar Proveedores");
		jButtonEliminar.setToolTipText("Eliminar Proveedor Seleccionado");
		jButtonEliminar.setEnabled(false);

		{
			jTextFieldNombre = new JTextField();
			JFprincipal.getContentPane().add(jTextFieldNombre, new GridBagConstraints(1, 3, 6, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		}
		{
			jLabel1 = new JLabel();
			JFprincipal.getContentPane().add(jLabel1, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			jLabel1.setText("Nombre del Proveedor");
		}
		{
			jLabel3 = new JLabel();
			JFprincipal.getContentPane().add(jLabel3, new GridBagConstraints(1, 6, 6, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			jLabel3.setText("Lista de Proveedores");
		}
		{
			jButtonImprimir = new JButton();
			JFprincipal.getContentPane().add(jButtonImprimir, new GridBagConstraints(5, 15, 1, 2, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			jButtonImprimir.setEnabled(true);
			jButtonImprimir.setText("Imprimir listado de Proveedores");
			jButtonImprimir.setToolTipText("Imprimir listado de Proveedores");
		}
		{
			jListProveedoresModel = new DefaultListModel();
			jListProveedores = new JList();
			JFprincipal.getContentPane().add(jListProveedores, new GridBagConstraints(1, 9, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			jListProveedores.setModel(jListProveedoresModel);
			jListProveedores.setBorder(new LineBorder(new java.awt.Color(0, 0,
					0), 0, false));
			jListProveedores.setToolTipText("Lista de Proveedores");
			jListProveedores
					.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			jListProveedores.setMaximumSize(new java.awt.Dimension(0, 0));
			jListScroller = new JScrollPane(jListProveedores);
			JFprincipal.getContentPane().add(jListScroller, new GridBagConstraints(1, 7, 6, 7, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
			jListScroller.setPreferredSize(new Dimension(250, 472));
			jListScroller.setBorder(new LineBorder(new java.awt.Color(0, 0, 0),
					3, true));
		}

		//Iniciador de eventos
		initEventos();
		
//		MUESTRO EL FRAME CENTRADO
		JFprincipal.pack();
		JFprincipal.setSize(658, 538);
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
		JFprincipal.setTitle("Gestión de Proveedores");
		JFprincipal.setPreferredSize(new java.awt.Dimension(658, 538));

		try {
			//cargarComboTipos();
			cargarListaProveedores();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private void initEventos(){
		
		jListProveedores.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent evt) {
				jListProductosValueChange(evt);
			}
		});
		
		jButtonEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButtonEliminarActionPerformed(evt);
			}
		});
		
		jButtonProveedores.addActionListener(new ActionListener() {
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
	
	private void cargarListaProveedores() throws SQLException, DalException{
		ResultSet rs;
		
		jListProveedoresModel = new DefaultListModel();
		
		try {
			rs=(ResultSet) getFacade().executeQuery("SELECT * FROM Proveedor ORDER BY proveedor");
		} catch (SQLException e) {
			throw new DalException(e);
		} //finally {
			//getFacade().endCurrentOperation();
		//}
		
		while (rs.next()){
			jListProveedoresModel.addElement(rs.getString("proveedor"));
			jListProveedores.setModel(jListProveedoresModel);
		}		
	}
	
	private void jButtonProductosActionPerformed(ActionEvent evt){
		
		if (jTextFieldNombre.getText().equals("")) {
			JOptionPane.showMessageDialog(Principal.msgbox,
				    "Debe introducir un NOMBRE para el nuevo proveedor",
				    "Campo Incompleto",
				    JOptionPane.WARNING_MESSAGE);
		} else if (this.jTextFieldNombre.getText() != "" && p.getProveedorID() == -1){
			p.setName(this.jTextFieldNombre.getText());
			try {
				pDal.insert(p);
				cargarListaProveedores();
				jTextFieldNombre.setText("");
				System.out.println("Proveedor añadido: " + p.getName());
			} catch (DalException e) {
				e.printStackTrace();
				System.out.println("ERROR al añadir el proveedor: " + p.getName());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("ERROR al añadir el proveedor: " + p.getName());
			}	
		}
	}
	
	private void jButtonEliminarActionPerformed(ActionEvent evt) {

		try {
			pDal.delete(pEliminar);
			cargarListaProveedores();
		} catch (DalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void jListProductosValueChange(ListSelectionEvent e) {
		pEliminar = new Proveedor();
		
		if (e.getValueIsAdjusting() == false) {
	        if (jListProveedores.getSelectedIndex() == -1) {
	        //No selection, deshabilito botón "Eliminar"
	            jButtonEliminar.setEnabled(false);

	        } else {
	        //Selection, Habilito botón "Eliminar" y creo el producto con nombre de la lista
	        	jButtonEliminar.setEnabled(true);
	        	pEliminar.setName(jListProveedores.getSelectedValue().toString());
	        }
	    }
	}
	
	private void jButtonImprimirActionPerformed(ActionEvent evt) throws SQLException, DalException{
		try
	    {
	        // Llenamos los parámetros
	    	
	    	// Llenamos los detalles uno a uno
	    	Vector<String> tableColumns = new Vector<String>();
            tableColumns.add("proveedor");
            //tableColumns.add("categoria");
            //tableColumns.add("Fecha alta");
            //tableColumns.add("Provincia");
            //tableColumns.add("Municipio");
            //tableColumns.add("NumExpIns");
      
            try {
            	rsImprimir=(ResultSet) getFacade().executeQuery("SELECT * FROM proveedor ORDER BY proveedor");
    		} catch (SQLException e) {
    			throw new DalException(e);
    		} //finally {
    			//getFacade().endCurrentOperation();
    		//}
    		int i = 0;
    		modelo = new DefaultTableModel(tableColumns,jListProveedoresModel.size());
    		while (rsImprimir.next()){
    			modelo.setValueAt(rsImprimir.getString("proveedor") , i, 0);
    			i++;
    			System.out.println("proveedor "+ i + ": " + rsImprimir.getString("proveedor"));
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

