
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import mysqldal.MySQLJDBCFacade;
import mysqldal.MySQLRegistroDeRecepcionDal;
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
import domain.RegistroDeRecepcion;


public class IntrRegistrosDeRecepcionTable extends Transactionable implements PresentationApp {

	private JDateChooser jDataChooserFecha;
	private JDateChooser jDataChooserFechaCaducidad;
	private JLabel jLabelFechaCaducidad;
	private JButton jButtonRegistro;
	private JButton jButtonEliminar;
	private JButton jButtonModificar;
	private JButton jButtonCancelar;
	private JButton jButtonCerrar;
	private JButton jButtonVerInforme;
	private JLabel jLabel2;
	private JLabel jLabel1;
	private JComboBox jComboBoxProductos;
	private DefaultComboBoxModel jComboBoxProductosModel;
	private JLabel jLabel3;
	private JTable jTableRegistros;
	private JScrollPane jTableScroller;
	private DefaultTableModel jTableRegistrosModel;
	private JLabel jLabelTitulo;
	private MySQLRegistroDeRecepcionDal rDal;
	private JLabel jLabel4;
	private JCheckBox jCheckBoxConformidad;
	private JLabel jLabel8;
	private JComboBox jComboBoxUbicacion;
	private DefaultComboBoxModel jComboBoxUbicacionModel;
	//private JTextField jTextFieldUbicacion;
	private JTextField jTextFieldObservaciones;
	private JLabel jLabel10;
	private JLabel jLabel9;
	private JTextField jTextFieldAlbaran;
	private JTextField jTextFieldLote;
	private JLabel jLabel7;
	private JTextField jTextFieldEnAlmacen;
	private JLabel jLabel6;
	private JLabel jLabel5;
	private JTextField jTextFieldKg;
	private JComboBox jComboBoxProveedores;
	private DefaultComboBoxModel jComboBoxProveedoresModel;
	private JButton jButtonImprimir;
	private JLabel jLabel13;
	private JDateChooser jDateChooserFechaFinal;
	private JDateChooser jDateChooserFechaInicial;
	private JLabel jLabel12;
	private JLabel jLabel11;
	private JPanel jPanelImprimir;
	private JTextField jTextFieldPrecio;
	private JLabel jLabelPrecio;
	private RegistroDeRecepcion r, rEliminar;

//	 Variables de impresión 
	private static final String reportName = "Informe_Reg_Entrada.jrxml";
	private String fechaInicial, fechaFinal;
	JasperReport jasperReport;
    JasperPrint jasperPrint=null;
    Map<String, String> parameters = new HashMap<String, String>();
    JRTableModelDataSource ds = null;
    TableModel modelo = null;
    private JCheckBox jCheckBoxBusquedaFechas;
    private JCheckBox jCheckBoxBusquedaLote;
    private JTextField jTextFieldBusquedaLote;
    private JLabel lblLote;
    private JCheckBox jCheckBoxBusquedaUbicacion;
    private JTextField jTextFieldBusquedaUbicacion;
    private JLabel lblUbicacin;
    
	
	public IntrRegistrosDeRecepcionTable() {
		super(new MySQLJDBCFacade());
		r = new RegistroDeRecepcion();
		rDal = new MySQLRegistroDeRecepcionDal();
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
		JFprincipalLayout.rowHeights = new int[] {51, 21, 8, 21, 8, 21, 8, 21, 8, 21, 8, 21, 8, 21, 8, 21, 8, 21, 8, 21, 8, 21, 8, 23, 7, 7, 7, 7, 7, 55, 8, 7, 7};
		JFprincipalLayout.rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.1, 0.1, 0.1, 0.1, 0.1, 0.0, 0.0, 0.0, 0.1};
		JFprincipalLayout.columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.1, 0.0, 0.0, 0.0, 0.0, 0.1};
		JFprincipalLayout.columnWidths = new int[] {35, 91, 112, 88, 20, -3, 140, 149, 95, 7};
		
		//Añado los componentes a JFprincipal
		jLabelTitulo = new JLabel("Molino v2.0");
		constraints.gridx = 0; // El área de texto empieza en la columna cero.
		constraints.gridy = 0; // El área de texto empieza en la fila uno.
		constraints.gridwidth = 4; // El área de texto ocupa 4 columnas.
		constraints.gridheight = 1; // El área de texto ocupa 1 filas.
		constraints.weighty = 1.0; // La fila 1 debe estirarse, le ponemos 1.0
		JFprincipal.getContentPane().add(jLabelTitulo, new GridBagConstraints(6, 0, 2, 1, 0.0, 1.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 5, 5), 0, 0));
		jLabelTitulo.setBackground(new java.awt.Color(0,0,160));
		jLabelTitulo.setFont(new java.awt.Font("Arial Black",1,18));
		jLabelTitulo.setText("Registros de Recepción");
		jLabelTitulo.setForeground(new java.awt.Color(0,0,160));
		jLabelTitulo.setBorder(new LineBorder(new java.awt.Color(0,0,0), 3, false));
		jLabelTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelTitulo.setHorizontalTextPosition(SwingConstants.CENTER);
		constraints.weighty = 0;
		
		jButtonRegistro = new JButton ("Registros");
		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		{
			jLabelFechaCaducidad = new JLabel();
			JFprincipal.getContentPane().add(jLabelFechaCaducidad, new GridBagConstraints(6, 1, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
			jLabelFechaCaducidad.setText("Fecha de Caducidad");
		}
		JFprincipal.getContentPane().add(jButtonRegistro, new GridBagConstraints(7, 21, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 5, 5), 0, 0));
		jButtonRegistro.setText("Añadir Entrada");
		jButtonRegistro.setToolTipText("Añadir Nueva Entrada de Productos");

		jButtonEliminar = new JButton ("Eliminar");
		constraints.gridx = 1;
		constraints.gridy = 3;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		JFprincipal.getContentPane().add(jButtonEliminar, new GridBagConstraints(7, 31, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
		jButtonEliminar.setText("Eliminar Entrada");
		jButtonEliminar.setToolTipText("Eliminar Registro de Entrada Seleccionado");
		jButtonEliminar.setEnabled(false);
		
		jButtonModificar = new JButton ("Eliminar");
		constraints.gridx = 1;
		constraints.gridy = 3;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		JFprincipal.getContentPane().add(jButtonModificar, new GridBagConstraints(6, 31, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
		jButtonModificar.setText("Modificar Entrada");
		jButtonModificar.setToolTipText("Modificar Registro de Entrada Seleccionado");
		jButtonModificar.setEnabled(false);
		
		{
			jComboBoxProductosModel = new DefaultComboBoxModel();
			jComboBoxProductos = new JComboBox();
			JFprincipal.getContentPane().add(jComboBoxProductos, new GridBagConstraints(3, 3, 5, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 5, 5), 0, 0));
			jComboBoxProductos.setModel(jComboBoxProductosModel);
			jComboBoxProductos.setToolTipText("Tipo de producto");
		}
		{
			jLabel1 = new JLabel();
			JFprincipal.getContentPane().add(jLabel1, new GridBagConstraints(2, 5, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
			jLabel1.setText("Proveedor");
		}
		{
			jLabel2 = new JLabel();
			JFprincipal.getContentPane().add(jLabel2, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 5, 5), 0, 0));
			jLabel2.setText("Producto");
		}
		{
			jLabel3 = new JLabel();
			JFprincipal.getContentPane().add(jLabel3, new GridBagConstraints(2, 23, 6, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
			jLabel3.setText("Lista de Productos");
		}
		{
			jTableRegistrosModel = new MyTableModel();
			jTableRegistros = new JTable();
			//JFprincipal.getContentPane().add(jTableRegistros, new GridBagConstraints(1, 9, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			jTableRegistros.setModel(jTableRegistrosModel);
			jTableRegistros.setBorder(new LineBorder(new java.awt.Color(0,0,0), 0, false));
			jTableRegistros.setToolTipText("Lista de Registros de Entrada");
			jTableRegistros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			jTableRegistros.setMaximumSize(new java.awt.Dimension(0, 0));
			jTableScroller = new JScrollPane(jTableRegistros);
			JFprincipal.getContentPane().add(jTableScroller, new GridBagConstraints(1, 24, 8, 6, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 5, 5), 0, 0));
			jTableScroller.setPreferredSize(new Dimension(250, 472));
			jTableScroller.setBorder(new LineBorder(new java.awt.Color(0,0,0), 3, true));
		}
		{
			jLabel4 = new JLabel();
			JFprincipal.getContentPane().add(jLabel4, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
			jLabel4.setText("Fecha de Entrada");
		}
		{
			jDataChooserFecha = new JDateChooser();
			JFprincipal.getContentPane().add(jDataChooserFecha, new GridBagConstraints(3, 1, 2, 1, 0.0, 0.0, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 5, 5), 0, 0));			
		}
		{
			jDataChooserFechaCaducidad = new JDateChooser();
			JFprincipal.getContentPane().add(jDataChooserFechaCaducidad, new GridBagConstraints(7, 1, 1, 1, 0.0, 0.0, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 5, 5), 0, 0));			
		}
		{
			jComboBoxProveedoresModel = new DefaultComboBoxModel();
			jComboBoxProveedores = new JComboBox();
			JFprincipal.getContentPane().add(jComboBoxProveedores, new GridBagConstraints(3, 5, 5, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 5, 5), 0, 0));
			jComboBoxProveedores.setModel(jComboBoxProveedoresModel);
			jComboBoxProveedores.setToolTipText("Proveedor que suministra el producto");
		}
		{
			jTextFieldKg = new JTextField();
			JFprincipal.getContentPane().add(jTextFieldKg, new GridBagConstraints(3, 7, 1, 1, 0.0, 0.0, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 5, 5), 0, 0));
			jTextFieldKg.setText("");
		}
		{
			jLabel5 = new JLabel();
			JFprincipal.getContentPane().add(jLabel5, new GridBagConstraints(2, 7, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
			jLabel5.setText("Kilogramos");
		}
		{
			jLabel6 = new JLabel();
			JFprincipal.getContentPane().add(jLabel6, new GridBagConstraints(2, 9, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
			jLabel6.setText("Actualmente en el almacén");
		}
		{
			jTextFieldEnAlmacen = new JTextField();
			JFprincipal.getContentPane().add(jTextFieldEnAlmacen, new GridBagConstraints(3, 9, 1, 1, 0.0, 0.0, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 5, 5), 0, 0));
			jTextFieldEnAlmacen.setText("");
		}
		{
			jLabel7 = new JLabel();
			JFprincipal.getContentPane().add(jLabel7, new GridBagConstraints(2, 11, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
			jLabel7.setText("Lote");
		}
		{
			jTextFieldLote = new JTextField();
			JFprincipal.getContentPane().add(jTextFieldLote, new GridBagConstraints(3, 11, 1, 1, 0.0, 0.0, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 5, 5), 0, 0));
			jTextFieldLote.setText("");
		}
		{
			jLabel8 = new JLabel();
			JFprincipal.getContentPane().add(jLabel8, new GridBagConstraints(2, 13, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
			jLabel8.setText("Albarán");
		}
		{
			jTextFieldAlbaran = new JTextField();
			JFprincipal.getContentPane().add(jTextFieldAlbaran, new GridBagConstraints(3, 13, 1, 1, 0.0, 0.0, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 5, 5), 0, 0));
			jTextFieldAlbaran.setText("");
		}
		{
			jLabel9 = new JLabel();
			JFprincipal.getContentPane().add(jLabel9, new GridBagConstraints(2, 17, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
			jLabel9.setText("Ubicación");
		}
		{
			jComboBoxUbicacionModel = new DefaultComboBoxModel();
			jComboBoxUbicacion = new JComboBox();
			//JFprincipal.getContentPane().add(jComboBoxUbicacion, new GridBagConstraints(3, 5, 5, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
			JFprincipal.getContentPane().add(jComboBoxUbicacion, new GridBagConstraints(3, 17, 5, 1, 0.0, 0.0, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 5, 5), 0, 0));
			jComboBoxUbicacion.setModel(jComboBoxUbicacionModel);
			jComboBoxUbicacion.setToolTipText("Ubicación donde se almacenará el producto");
		}
		{
			jLabel10 = new JLabel();
			JFprincipal.getContentPane().add(jLabel10, new GridBagConstraints(2, 19, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
			jLabel10.setText("Observaciones");
		}
		{
			jTextFieldObservaciones = new JTextField();
			JFprincipal.getContentPane().add(jTextFieldObservaciones, new GridBagConstraints(3, 19, 5, 1, 0.0, 0.0, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 5, 5), 0, 0));
			jTextFieldObservaciones.setText("");
		}
		{
			jCheckBoxConformidad = new JCheckBox();
			JFprincipal.getContentPane().add(jCheckBoxConformidad, new GridBagConstraints(2, 21, 2, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
			jCheckBoxConformidad.setText("Conformidad en la entrega");
		}
		{
			jButtonCancelar = new JButton();
			JFprincipal.getContentPane().add(jButtonCancelar, new GridBagConstraints(3, 31, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 5, 5), 0, 0));
			jButtonCancelar.setText("Cancelar");
			jButtonCancelar.setVisible(false);
		}
		{
			jLabelPrecio = new JLabel();
			JFprincipal.getContentPane().add(jLabelPrecio, new GridBagConstraints(2, 15, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
			jLabelPrecio.setText("Precio (\u20ac/kg)");
		}
		{
			jTextFieldPrecio = new JTextField();
			JFprincipal.getContentPane().add(jTextFieldPrecio, new GridBagConstraints(3, 15, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 5, 5), 0, 0));
		}
		{
			jPanelImprimir = new JPanel();
			GridBagLayout jPanelImprimirLayout = new GridBagLayout();
			jPanelImprimirLayout.rowWeights = new double[] {0.0, 0.0, 0.0, 0.1, 0.1, 0.1, 0.1, 0.1};
			jPanelImprimirLayout.rowHeights = new int[] {128, 30, 71, 20, 20, 20, 20, 7};
			jPanelImprimirLayout.columnWeights = new double[] {0.1, 0.1, 1.0, 0.0, 0.0, 0.1};
			jPanelImprimirLayout.columnWidths = new int[] {7, 7, 7, 82, 146, 20};
			jPanelImprimir.setLayout(jPanelImprimirLayout);
			JFprincipal.getContentPane().add(jPanelImprimir, new GridBagConstraints(2, 6, 6, 18, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 5, 5), 0, 0));
			jPanelImprimir.setBorder(new LineBorder(new java.awt.Color(0,0,0), 2, false));
			jPanelImprimir.setVisible(false);
			{
				jLabel11 = new JLabel();	
				jPanelImprimir.add(jLabel11, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 0, 5, 5), 0, 0));
				jLabel11.setText("Fecha Inicial");
			}
			{
				jLabel12 = new JLabel();
				jPanelImprimir.add(jLabel12, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 0, 5, 5), 0, 0));
				jLabel12.setText("Fecha Final");
			}
			{
				jDateChooserFechaInicial = new JDateChooser();
				jPanelImprimir.add(jDateChooserFechaInicial, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 5, 5), 0, 0));
			}
			{
				jDateChooserFechaFinal = new JDateChooser();
				jPanelImprimir.add(jDateChooserFechaFinal, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 5, 5), 0, 0));
			}
			{
				jCheckBoxBusquedaFechas = new JCheckBox("Aplicar criterio por fechas");
				GridBagConstraints gbc_chckbxFechas = new GridBagConstraints();
				gbc_chckbxFechas.anchor = GridBagConstraints.WEST;
				gbc_chckbxFechas.insets = new Insets(0, 0, 5, 5);
				gbc_chckbxFechas.gridx = 4;
				gbc_chckbxFechas.gridy = 2;
				jPanelImprimir.add(jCheckBoxBusquedaFechas, gbc_chckbxFechas);
			}
			{
				lblLote = new JLabel("Lote");
				GridBagConstraints gbc_lblLote = new GridBagConstraints();
				gbc_lblLote.fill = GridBagConstraints.HORIZONTAL;
				gbc_lblLote.insets = new Insets(0, 0, 5, 5);
				gbc_lblLote.gridx = 1;
				gbc_lblLote.gridy = 3;
				jPanelImprimir.add(lblLote, gbc_lblLote);
			}
			{
				jTextFieldBusquedaLote = new JTextField();
				GridBagConstraints gbc_jTextFieldBusquedaLote = new GridBagConstraints();
				gbc_jTextFieldBusquedaLote.insets = new Insets(0, 0, 5, 5);
				gbc_jTextFieldBusquedaLote.fill = GridBagConstraints.HORIZONTAL;
				gbc_jTextFieldBusquedaLote.gridx = 2;
				gbc_jTextFieldBusquedaLote.gridy = 3;
				jPanelImprimir.add(jTextFieldBusquedaLote, gbc_jTextFieldBusquedaLote);
				jTextFieldBusquedaLote.setColumns(10);
			}
			{
				jCheckBoxBusquedaLote = new JCheckBox("Aplicar criterio por lote");
				GridBagConstraints gbc_jCheckBoxLote = new GridBagConstraints();
				gbc_jCheckBoxLote.anchor = GridBagConstraints.WEST;
				gbc_jCheckBoxLote.insets = new Insets(0, 0, 5, 5);
				gbc_jCheckBoxLote.gridx = 4;
				gbc_jCheckBoxLote.gridy = 3;
				jPanelImprimir.add(jCheckBoxBusquedaLote, gbc_jCheckBoxLote);
			}
			{
				jLabel13 = new JLabel();
				jPanelImprimir.add(jLabel13, new GridBagConstraints(3, 0, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 5, 5), 0, 0));
				jLabel13.setText("Imprimir Registros de Entrada");
				jLabel13.setBorder(new LineBorder(new java.awt.Color(0,0,0), 2, false));
				jLabel13.setFont(new java.awt.Font("Arial Black",1,14));
				jLabel13.setForeground(new java.awt.Color(0,0,160));
				jLabel13.setHorizontalAlignment(SwingConstants.CENTER);
			}
			{
				lblUbicacin = new JLabel("Ubicaci\u00F3n");
				GridBagConstraints gbc_lblUbicacin = new GridBagConstraints();
				gbc_lblUbicacin.insets = new Insets(0, 0, 5, 5);
				gbc_lblUbicacin.anchor = GridBagConstraints.WEST;
				gbc_lblUbicacin.gridx = 1;
				gbc_lblUbicacin.gridy = 4;
				jPanelImprimir.add(lblUbicacin, gbc_lblUbicacin);
			}
			{
				jTextFieldBusquedaUbicacion = new JTextField();
				GridBagConstraints gbc_jTextFieldBusquedaUbicacion = new GridBagConstraints();
				gbc_jTextFieldBusquedaUbicacion.insets = new Insets(0, 0, 5, 5);
				gbc_jTextFieldBusquedaUbicacion.fill = GridBagConstraints.HORIZONTAL;
				gbc_jTextFieldBusquedaUbicacion.gridx = 2;
				gbc_jTextFieldBusquedaUbicacion.gridy = 4;
				jPanelImprimir.add(jTextFieldBusquedaUbicacion, gbc_jTextFieldBusquedaUbicacion);
				jTextFieldBusquedaUbicacion.setColumns(10);
			}
			{
				jCheckBoxBusquedaUbicacion = new JCheckBox("Aplicar criterio por ubicaci\u00F3n");
				GridBagConstraints gbc_jCheckBoxBusquedaUbicacion = new GridBagConstraints();
				gbc_jCheckBoxBusquedaUbicacion.anchor = GridBagConstraints.WEST;
				gbc_jCheckBoxBusquedaUbicacion.insets = new Insets(0, 0, 5, 5);
				gbc_jCheckBoxBusquedaUbicacion.gridx = 4;
				gbc_jCheckBoxBusquedaUbicacion.gridy = 4;
				jPanelImprimir.add(jCheckBoxBusquedaUbicacion, gbc_jCheckBoxBusquedaUbicacion);
			}
			{
				jButtonVerInforme = new JButton();
				jPanelImprimir.add(jButtonVerInforme, new GridBagConstraints(2, 6, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 5, 5), 0, 0));
				jButtonVerInforme.setText("Ver Informe");
				jButtonVerInforme.setToolTipText("Visualiza el informe para su impresión");
			}
			{
				jButtonCerrar = new JButton();
				jPanelImprimir.add(jButtonCerrar, new GridBagConstraints(4, 6, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
				jButtonCerrar.setText("Cerrar");
				jButtonCerrar.setMaximumSize(new java.awt.Dimension(17, 7));
				jButtonCerrar.setToolTipText("Cierra panel de búsqueda");
			}
		}
		{
			jButtonImprimir = new JButton();
			JFprincipal.getContentPane().add(jButtonImprimir, new GridBagConstraints(2, 31, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
			jButtonImprimir.setText("Imprimir");
			jButtonImprimir.setToolTipText("Mostrar panel de búsqueda para imprimir registros de entrada");
		}

		//Iniciador de eventos
		initEventos();
		
//		MUESTRO EL FRAME CENTRADO
		JFprincipal.pack();
		JFprincipal.setSize(818, 708);
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
		JFprincipal.setTitle("Registros de Recepción");
		
		try {
			cargarComboUbicaciones();
			cargarComboProveedores();
			cargarComboProductos();
			cargarTablaRegistros();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private void initEventos(){
		
		//Seleccionar un registro en la lista de entrada
		ListSelectionModel listMod =  jTableRegistros.getSelectionModel();
		listMod.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listMod.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent evt) {
				jTableRegistrosValueChange(evt);
			}
		});
		
		//Eliminar Registro de Entrada seleccionado en la lista
		jButtonEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButtonEliminarActionPerformed(evt);
			}
		});
		
		//Modificar Registro de Entrada seleccionado en la lista
		jButtonModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButtonModificarActionPerformed(evt);
			}
		});
		
		//Añadir Nuevo Registro de Entrada
		jButtonRegistro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButtonRegistrosActionPerformed(evt);
			}
		});
		
		//Cancelar la modificacion de un registro de entrada
		jButtonCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButtonCancelarActionPerformed(evt);
			}
		});
		
		//Muestra el panel para imprimir el informe
		jButtonImprimir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButtonImprimirActionPerformed(evt);
			}
		});
		
//		Oculta el panel para imprimir el informe
		jButtonCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButtonCerrarActionPerformed(evt);
			}
		});
		
//		Oculta el panel para imprimir el informe
		jButtonVerInforme.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButtonVerInformeActionPerformed(evt);
			}
		});
	}
	
	private void cargarComboUbicaciones() throws SQLException, DalException{
		ResultSet rs;
		
		jComboBoxUbicacion.removeAllItems();
		
		try {
			rs=(ResultSet) getFacade().executeQuery("SELECT * FROM ubicacion ORDER BY ubicacion");
		} catch (SQLException e) {
			throw new DalException(e);
		} //finally {
			//getFacade().endCurrentOperation();
		//}
		
		while (rs.next()){
			jComboBoxUbicacion.addItem(rs.getString("ubicacion"));
		}	
	}
	
	private void cargarComboProductos() throws SQLException, DalException{
		ResultSet rs;
		
		jComboBoxProductos.removeAllItems();
		
		try {
			rs=(ResultSet) getFacade().executeQuery("SELECT * FROM Producto ORDER BY producto");
		} catch (SQLException e) {
			throw new DalException(e);
		} //finally {
			//getFacade().endCurrentOperation();
		//}
		
		while (rs.next()){
			jComboBoxProductos.addItem(rs.getString("producto"));	
		}	
	}
	
	private void cargarComboProveedores() throws SQLException, DalException{
		ResultSet rs;
		
		jComboBoxProveedores.removeAllItems();
		
		try {
			rs=(ResultSet) getFacade().executeQuery("SELECT * FROM proveedor ORDER BY proveedor");
		} catch (SQLException e) {
			throw new DalException(e);
		} //finally {
			//getFacade().endCurrentOperation();
		//}
		
		while (rs.next()){
			jComboBoxProveedores.addItem(rs.getString("proveedor"));
		}	
	}
	
	private void cargarTablaRegistros() throws SQLException, DalException{
		ResultSet rs;
		String auxFecha;
		String auxFechaCaducidad;
		
		Object[][] data = null;
		Object[] headers = new Object[] {"ID", "FECHA", "FECHA CADUCIDAD", "PRODUCTO", "PROVEEDOR", 
										"Nº KG", "EN ALMACEN", "PRECIO", "LOTE", "ALBARAN", "UBICACION",
										"OBSERVACIONES", "CONFORMIDAD"};
		
		jTableRegistrosModel = new DefaultTableModel(data,headers);
		
		try {
			rs=(ResultSet) getFacade().executeQuery("SELECT * FROM registrosDeRecepcion ORDER BY fecha DESC LIMIT 300");
		} catch (SQLException e) {
			throw new DalException(e);
		} //finally {
			//getFacade().endCurrentOperation();
		//}
		
		rs.beforeFirst();
		while (rs.next()){
			java.text.DateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
			auxFecha =  sdf.format(rs.getDate("fecha"));
			try {
				auxFechaCaducidad = sdf.format(rs.getDate("fechaCaducidad"));
			} catch (Exception e) {
				auxFechaCaducidad = "";
			}
			Object [] row = new Object[]{String.valueOf(rs.getInt("id")), auxFecha, auxFechaCaducidad, 
										rs.getString("producto"), rs.getString("proveedor"), 
										String.valueOf(rs.getFloat("kg")), String.valueOf(rs.getFloat("enAlmacen")),
										String.valueOf(rs.getFloat("precio")), rs.getString("lote"), rs.getString("albaran"), rs.getString("ubicacion"),
										rs.getString("observaciones"), rs.getBoolean("conformidad") ? "Sí" : "No"}; //String.valueOf(rs.getBoolean("conformidad"))};
			jTableRegistrosModel.addRow(row);//rs.getString("producto"));
			jTableRegistros.setModel(jTableRegistrosModel);
		}		
	}
	
	private void cargarTablaRegistrosByWhere(String where) throws SQLException, DalException{
		ResultSet rs;
		String auxFecha;
		String auxFechaCaducidad;
		
		Object[][] data = null;
		Object[] headers = new Object[] {"ID", "FECHA", "FECHA CADUCIDAD", "PRODUCTO", "PROVEEDOR", 
										"Nº KG", "EN ALMACEN", "PRECIO", "LOTE", "ALBARAN", "UBICACION",
										"OBSERVACIONES", "CONFORMIDAD"};
		
		jTableRegistrosModel = new DefaultTableModel(data,headers);
		
		try {
			rs=(ResultSet) getFacade().executeQuery("SELECT * FROM registrosDeRecepcion " + 
														" WHERE " + where +
														"ORDER BY fecha DESC");
		} catch (SQLException e) {
			throw new DalException(e);
		} //finally {
			//getFacade().endCurrentOperation();
		//}
		
		rs.afterLast();
		while (rs.previous()){
			java.text.DateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
			auxFecha =  sdf.format(rs.getDate("fecha"));
			try {
				auxFechaCaducidad = sdf.format(rs.getDate("fechaCaducidad"));
			} catch (Exception e) {
				auxFechaCaducidad = "";
			}
			Object [] row = new Object[]{String.valueOf(rs.getInt("id")), auxFecha, auxFechaCaducidad, 
										rs.getString("producto"), rs.getString("proveedor"), 
										String.valueOf(rs.getFloat("kg")), String.valueOf(rs.getFloat("enAlmacen")),
										String.valueOf(rs.getFloat("precio")), rs.getString("lote"), rs.getString("albaran"), rs.getString("ubicacion"),
										rs.getString("observaciones"), String.valueOf(rs.getBoolean("conformidad"))};
			jTableRegistrosModel.addRow(row);//rs.getString("producto"));
			jTableRegistros.setModel(jTableRegistrosModel);
		}		
	}
	
	private void jButtonRegistrosActionPerformed(ActionEvent evt){
		
		if (jTextFieldKg.getText().equals("")) {
			JOptionPane.showMessageDialog(Principal.msgbox,
				    "Debe introducir una cantidad (KG) de entrada",
				    "Campo Incompleto",
				    JOptionPane.WARNING_MESSAGE);
		} else if (jTextFieldLote.getText().equals("")){
			JOptionPane.showMessageDialog(Principal.msgbox,
				    "Debe introducir a que LOTE pertenece el registro de entrada",
				    "Campo Incompleto",
				    JOptionPane.WARNING_MESSAGE);
		} else if (jTextFieldPrecio.getText().equals("")){
			JOptionPane.showMessageDialog(Principal.msgbox,
				    "Debe introducir un PRECIO para el registro de entrada",
				    "Campo Incompleto",
				    JOptionPane.WARNING_MESSAGE);
		} else if (jDataChooserFecha.getDate() == null){
			JOptionPane.showMessageDialog(Principal.msgbox,
				    "Debe introducir una FECHA para el registro de entrada",
				    "Campo Incompleto",
				    JOptionPane.WARNING_MESSAGE);
		} else {
			if (this.jComboBoxProductos.getSelectedItem().toString() != "" && r.getRegistroID() == -1){
				
				boolean formatoNumerico = true;
				float precio = 0;
				float kg = 0;
				
				// Comprobamos que PRECIO es numerico
				try {
					precio = Float.valueOf(jTextFieldPrecio.getText()).floatValue();
				} catch (NumberFormatException e) {
					formatoNumerico = false;
					JOptionPane.showMessageDialog(Principal.msgbox,
						    "Debe introducir un PRECIO en formato númerico. Recuerde utiliza el carácter '.' para indicar decimales",
						    "Formato erróneo",
						    JOptionPane.WARNING_MESSAGE);
				}
				
				// Comprobamos que KG es numerico
				try {
					kg = Float.valueOf(jTextFieldKg.getText()).floatValue();
				} catch (NumberFormatException e) {
					formatoNumerico = false;
					JOptionPane.showMessageDialog(Principal.msgbox,
						    "Debe introducir KILOGRAMOS en formato númerico. Recuerde utiliza el carácter '.' para indicar decimales",
						    "Formato erróneo",
						    JOptionPane.WARNING_MESSAGE);
				}
				
				
				// Guardamos si formatoNumerico = true
				if (formatoNumerico) {
					r.getProducto().setName(this.jComboBoxProductos.getSelectedItem().toString());
					r.setFecha(jDataChooserFecha.getDate());
					r.setFechaCaducidad(jDataChooserFechaCaducidad.getDate());
					r.getProveedor().setName(this.jComboBoxProveedores.getSelectedItem().toString());
					r.setKg(kg);
					r.setEnAlmacen(Float.valueOf(jTextFieldEnAlmacen.getText()).floatValue());
					r.setLote(jTextFieldLote.getText());
					r.setAlbaran(jTextFieldAlbaran.getText());
					r.getUbicacion().setName(this.jComboBoxUbicacion.getSelectedItem().toString());
					r.setObservaciones(jTextFieldObservaciones.getText());
					r.setConformidad(jCheckBoxConformidad.isSelected());
					r.setPrecio(precio);
					
					try {
						rDal.insert(r);
						cargarTablaRegistros();
						jTextFieldKg.setText("");
						this.jTextFieldEnAlmacen.setText("");
						this.jTextFieldLote.setText("");
						this.jTextFieldPrecio.setText("");
						//jTextFieldUbicacion.setText("");
						jTextFieldObservaciones.setText("");
						System.out.println("Producto añadido: " + r.getProducto().getName() + " - " + r.getProveedor().getName() + " el día " + r.getFechaToString());
					} catch (DalException e) {
						e.printStackTrace();
						System.out.println("ERROR al añadir el producto: " + r.getProducto().getName() + " - " + r.getProveedor().getName());
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						System.out.println("ERROR al añadir el producto: " + r.getProducto().getName() + " - " + r.getProveedor().getName());
					}	
				}
			}
		}
	}
	
	private void jButtonEliminarActionPerformed(ActionEvent evt) {

		try {
			rDal.delete(rEliminar);
			cargarTablaRegistros();
		} catch (DalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void jButtonCancelarActionPerformed(ActionEvent evt) {

		try {
			this.jButtonRegistro.setEnabled(true);
			this.jButtonEliminar.setEnabled(false);
			this.jButtonModificar.setEnabled(false);
			this.jButtonCancelar.setVisible(false);
			cargarTablaRegistros();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void jButtonImprimirActionPerformed(ActionEvent evt) {
		setEstadoControles(false);
	}
	
	private void jButtonCerrarActionPerformed(ActionEvent evt) {
		setEstadoControles(true);
	}
	
	private void jButtonVerInformeActionPerformed(ActionEvent evt) {
		
		if (!jCheckBoxBusquedaFechas.isSelected() && !jCheckBoxBusquedaLote.isSelected()
				&& !jCheckBoxBusquedaUbicacion.isSelected()) {
			JOptionPane.showMessageDialog(Principal.msgbox,
				    "Debe marcar al menos un criterio de búsqueda",
				    "Criterios de búsqueda sin marcar",
				    JOptionPane.WARNING_MESSAGE);
		} else {
		
			String where = "";
			boolean primerWhere = true;
			java.text.DateFormat sdfAux=null; 
			boolean hayWarning = false;
			
			if (jCheckBoxBusquedaFechas.isSelected()){		
				if (jDateChooserFechaInicial.getDate() == null){
					JOptionPane.showMessageDialog(Principal.msgbox,
						    "Debe introducir una FECHA INICIAL para realizar el informe",
						    "Campo Incompleto",
						    JOptionPane.WARNING_MESSAGE);
					hayWarning = true;
				} else if (jDateChooserFechaFinal.getDate() == null){
					JOptionPane.showMessageDialog(Principal.msgbox,
						    "Debe introducir una FECHA FINAL para realizar el informe",
						    "Campo Incompleto",
						    JOptionPane.WARNING_MESSAGE);
					hayWarning = true;
				} else if (jDateChooserFechaInicial.getDate().after(jDateChooserFechaFinal.getDate())){
					JOptionPane.showMessageDialog(Principal.msgbox,
						    "La FECHA INICIAL debe ser menor que la FECHA FINAL",
						    "Fechas Erroneas",
						    JOptionPane.WARNING_MESSAGE);
					hayWarning = true;
				} else {
					
					sdfAux = new java.text.SimpleDateFormat("yyyy/MM/dd");
					fechaInicial =  sdfAux.format(jDateChooserFechaInicial.getDate());
					fechaFinal =  sdfAux.format(jDateChooserFechaFinal.getDate());
					
					where =  "(fecha >= '" + fechaInicial + "' " +
							"AND fecha <= '" + fechaFinal + "') ";
					primerWhere = false;
				}
			}
			
			if (jCheckBoxBusquedaLote.isSelected()){	
				
				if (jTextFieldBusquedaLote.getText().equals("")){
					JOptionPane.showMessageDialog(Principal.msgbox,
						    "Si marca el criterio de búsqueda por LOTE no puede dejar su campo vacío",
						    "Lote Vacío",
						    JOptionPane.WARNING_MESSAGE);
					hayWarning = true;
				} else {
					if (primerWhere) { 
						where =  "lote LIKE '%" + jTextFieldBusquedaLote.getText() + "%'";
						primerWhere = false;
					} else {
						where =  where + "AND (lote LIKE '%" + jTextFieldBusquedaLote.getText() + "%')";
					}
				}
				
			}
			
			if (jCheckBoxBusquedaUbicacion.isSelected()){	
				
				if (jTextFieldBusquedaUbicacion.getText().equals("")){
					JOptionPane.showMessageDialog(Principal.msgbox,
						    "Si marca el criterio de búsqueda por UBICACIÓN no puede dejar su campo vacío",
						    "Ubicación Vacía",
						    JOptionPane.WARNING_MESSAGE);
					hayWarning = true;
				} else {
					if (primerWhere) { 
						where =  "ubicacion LIKE '%" + jTextFieldBusquedaUbicacion.getText() + "%'";
						primerWhere = false;
					} else {
						where =  where + "AND (ubicacion LIKE '%" + jTextFieldBusquedaUbicacion.getText() + "%')";
					}
				}
				
			}
			
			if (!hayWarning) {
				
				try {
					cargarTablaRegistrosByWhere(where);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (DalException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				try
			    {
			        // Llenamos los parámetros
					sdfAux = new java.text.SimpleDateFormat("dd/MM/yyyy");
					//fechaInicial =  sdfAux.format(jDateChooserInicial.getDate());
					//fechaFinal =  sdfAux.format(jDateChooserFinal.getDate());
			    	if (jDateChooserFechaFinal.getDate() != null && jDateChooserFechaInicial.getDate() != null) {
						parameters.put("fecha_inicial", sdfAux.format(jDateChooserFechaInicial.getDate()));
				    	parameters.put("fecha_final", sdfAux.format(jDateChooserFechaFinal.getDate()));
			    	} else {
			    		parameters.put("fecha_inicial", String.valueOf(jTableRegistrosModel.getValueAt(0, 1)));
				    	parameters.put("fecha_final", String.valueOf(jTableRegistrosModel.getValueAt(jTableRegistrosModel.getRowCount()-1, 1)));
			    	}
			    	
			    	// Llenamos los detalles uno a uno
			    	Vector<String> tableColumns = new Vector<String>();
		            tableColumns.add("fecha");
		            tableColumns.add("fechacaducidad");
		            tableColumns.add("producto");
		            tableColumns.add("proveedor");
		            tableColumns.add("lote");
		            tableColumns.add("albaran");
		            tableColumns.add("observaciones");
		            tableColumns.add("ubicacion");
		            tableColumns.add("cantidad");
		            tableColumns.add("conformidad");
		      
		            modelo = new DefaultTableModel(tableColumns,jTableRegistrosModel.getRowCount());
		            for(int i = 0; i < jTableRegistrosModel.getRowCount() ;i++){
		            	modelo.setValueAt(String.valueOf(jTableRegistrosModel.getValueAt(i, 1)) , i, 0);
		            	modelo.setValueAt(String.valueOf(jTableRegistrosModel.getValueAt(i, 2)) , i, 1);
		                modelo.setValueAt(String.valueOf(jTableRegistrosModel.getValueAt(i, 3)) , i, 2);
		                modelo.setValueAt(String.valueOf(jTableRegistrosModel.getValueAt(i, 4)) , i, 3);
		                modelo.setValueAt(String.valueOf(jTableRegistrosModel.getValueAt(i, 8)) , i, 4);
		                modelo.setValueAt(String.valueOf(jTableRegistrosModel.getValueAt(i, 9)) , i, 5);
		                modelo.setValueAt(String.valueOf(jTableRegistrosModel.getValueAt(i, 11)) , i, 6);
		                modelo.setValueAt(String.valueOf(jTableRegistrosModel.getValueAt(i, 10)) , i, 7);
		                modelo.setValueAt(String.valueOf(jTableRegistrosModel.getValueAt(i, 5)) , i, 8);
		                if(String.valueOf(jTableRegistrosModel.getValueAt(i, 12)).equals("true"))
		                	modelo.setValueAt( "SI", i, 9);
		                else
		                	modelo.setValueAt( "NO", i, 9);
		            
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
			    catch (Exception e)
			    {
			    	JOptionPane.showMessageDialog(Principal.msgbox,
						    "Error al generar el informe. Posiblemente no haya registros con los criterios de búsqueda indicados.",
						    "Error al generar el informe",
						    JOptionPane.WARNING_MESSAGE);
			      e.printStackTrace();
			    }
			}
		}
	}
	
	private void jButtonModificarActionPerformed(ActionEvent evt) {

		if (jDataChooserFecha.getDate() == null){
			JOptionPane.showMessageDialog(Principal.msgbox,
				    "Introduzca la FECHA de nuevo por favor, \nSi el error continua cierre el formulario y vuelva a intentarlo",
				    "Campo Incompleto",
				    JOptionPane.WARNING_MESSAGE);
		} else if (jTextFieldKg.getText().equals("")) {
			JOptionPane.showMessageDialog(Principal.msgbox,
				    "Debe introducir una cantidad (KG) de entrada",
				    "Campo Incompleto",
				    JOptionPane.WARNING_MESSAGE);
		} else if (jTextFieldLote.getText().equals("")){
			JOptionPane.showMessageDialog(Principal.msgbox,
				    "Debe introducir a que LOTE pertenece el registro de entrada",
				    "Campo Incompleto",
				    JOptionPane.WARNING_MESSAGE);
		} else if (jTextFieldPrecio.getText().equals("")){
			JOptionPane.showMessageDialog(Principal.msgbox,
				    "Debe introducir un PRECIO para el registro de entrada",
				    "Campo Incompleto",
				    JOptionPane.WARNING_MESSAGE);
		} else {
			if (this.jComboBoxProductos.getSelectedItem().toString() != "" && rEliminar != null){
				
				boolean formatoNumerico = true;
				float precio = 0;
				float kg = 0;
				
				// Comprobamos que PRECIO es numerico
				try {
					precio = Float.valueOf(jTextFieldPrecio.getText()).floatValue();
				} catch (NumberFormatException e) {
					formatoNumerico = false;
					JOptionPane.showMessageDialog(Principal.msgbox,
						    "Debe introducir un PRECIO en formato númerico. Recuerde utiliza el carácter '.' para indicar decimales",
						    "Formato erróneo",
						    JOptionPane.WARNING_MESSAGE);
				}
				
				// Comprobamos que KG es numerico
				try {
					kg = Float.valueOf(jTextFieldKg.getText()).floatValue();
				} catch (NumberFormatException e) {
					formatoNumerico = false;
					JOptionPane.showMessageDialog(Principal.msgbox,
						    "Debe introducir KILOGRAMOS en formato númerico. Recuerde utiliza el carácter '.' para indicar decimales",
						    "Formato erróneo",
						    JOptionPane.WARNING_MESSAGE);
				}
				
				
				// Guardamos si formatoNumerico = true
				if (formatoNumerico) {
					try {
						rEliminar.getProducto().setName(this.jComboBoxProductos.getSelectedItem().toString());
						rEliminar.setFecha(jDataChooserFecha.getDate());
						rEliminar.setFechaCaducidad(jDataChooserFechaCaducidad.getDate());
						rEliminar.getProveedor().setName(this.jComboBoxProveedores.getSelectedItem().toString());
						rEliminar.setKg(Float.valueOf(jTextFieldKg.getText()).floatValue());
						rEliminar.setEnAlmacen(Float.valueOf(jTextFieldEnAlmacen.getText()).floatValue());
						rEliminar.setLote(jTextFieldLote.getText());
						rEliminar.setAlbaran(jTextFieldAlbaran.getText());
						rEliminar.getUbicacion().setName(this.jComboBoxUbicacion.getSelectedItem().toString());
						rEliminar.setObservaciones(jTextFieldObservaciones.getText());
						rEliminar.setConformidad(jCheckBoxConformidad.isSelected());
						if (jTextFieldPrecio.getText().equals("")){
							rEliminar.setPrecio(0);
						} else {
							rEliminar.setPrecio(Float.valueOf(jTextFieldPrecio.getText()).floatValue());
						}
			
						rDal.modify(rEliminar);
						
					} catch (DalException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					try {
						
						jTextFieldKg.setText("");
						this.jTextFieldEnAlmacen.setText("");
						this.jTextFieldLote.setText("");
						this.jTextFieldPrecio.setText("");
						jTextFieldObservaciones.setText("");
						cargarTablaRegistros();
									
					} catch (SQLException e) {
						e.printStackTrace();
					} catch (DalException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public void jTableRegistrosValueChange(ListSelectionEvent e) {
		rEliminar = new RegistroDeRecepcion();
		
		if (e.getValueIsAdjusting() == false) {
	        if (jTableRegistros.getSelectedRow() == -1) {
	        //No selection, deshabilito botón "Eliminar"
	            jButtonEliminar.setEnabled(false);
	            jButtonModificar.setEnabled(false);
	            jButtonCancelar.setVisible(false);
	            jButtonRegistro.setEnabled(true);
	        } else {
	
	        //Selection, Habilito botón "Eliminar" y creo el producto con nombre de la lista
	        	jButtonEliminar.setEnabled(true);
	        	jButtonModificar.setEnabled(true);
	        	jButtonCancelar.setVisible(true);
	        	jButtonRegistro.setEnabled(false);
	        	
	        	Date f=null;
	        	Date fCaducidad=null;
	        	int rowSelected = jTableRegistros.getSelectedRow();
	        	
	        	rEliminar.setRegistroID(Integer.parseInt(jTableRegistros.getValueAt(rowSelected,0).toString()));
	        	DateFormat convertDate = new SimpleDateFormat("dd/MM/yyyy");
	        	try {
					f = convertDate.parse(jTableRegistros.getValueAt(rowSelected,1).toString());
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	        	
	        	try {
					fCaducidad = convertDate.parse(jTableRegistros.getValueAt(rowSelected,2).toString());
				} catch (ParseException e1) {
					fCaducidad = null;
				}

	        	asignarComboBox(jTableRegistros.getValueAt(rowSelected,3),this.jComboBoxProductos);
	        	asignarComboBox(jTableRegistros.getValueAt(rowSelected,4),this.jComboBoxProveedores);
	        	jDataChooserFecha.setDate(f);
	        	jDataChooserFechaCaducidad.setDate(fCaducidad);
				jTextFieldKg.setText(jTableRegistros.getValueAt(rowSelected,5).toString());
				jTextFieldEnAlmacen.setText(jTableRegistros.getValueAt(rowSelected,6).toString());
				jTextFieldPrecio.setText(jTableRegistros.getValueAt(rowSelected,7).toString());
				jTextFieldLote.setText(jTableRegistros.getValueAt(rowSelected,8).toString());
				jTextFieldAlbaran.setText(jTableRegistros.getValueAt(rowSelected,9).toString());
				//jTextFieldUbicacion.setText(jTableRegistros.getValueAt(rowSelected,9).toString());
				asignarComboBox(jTableRegistros.getValueAt(rowSelected,10),this.jComboBoxUbicacion);
				jTextFieldObservaciones.setText(jTableRegistros.getValueAt(rowSelected,11).toString());
				if (jTableRegistros.getValueAt(rowSelected,12).toString().equals("true")) {
					jCheckBoxConformidad.setSelected(true);
				} else {
					jCheckBoxConformidad.setSelected(false);
				}

	        }
	    }
	}

	private void asignarComboBox(Object valueAt, JComboBox box) {
		String value = String.valueOf(valueAt);
		int i = 0;
		
		for (i=0;i<box.getItemCount();i++){
			if (box.getItemAt(i).toString().equals(value)){
				box.setSelectedIndex(i);
				i=box.getItemCount();
			}
		}
	}
	
	private void setEstadoControles(boolean bool){
		
		//informe
		jDataChooserFecha.setVisible(bool);
		jButtonRegistro.setVisible(bool);
		jButtonCancelar.setVisible(bool);
		jButtonEliminar.setVisible(bool);
		jButtonModificar.setVisible(bool);
		jButtonImprimir.setVisible(bool);
		jComboBoxProductos.setVisible(bool);
		jTableRegistros.setVisible(bool);
		jTableScroller.setVisible(bool);
		jLabelTitulo.setVisible(bool);
		jCheckBoxConformidad.setVisible(bool);
		//jTextFieldUbicacion.setVisible(bool);
		jComboBoxUbicacion.setVisible(bool);
		jTextFieldObservaciones.setVisible(bool);
		jTextFieldAlbaran.setVisible(bool);
		jTextFieldLote.setVisible(bool);
		jTextFieldEnAlmacen.setVisible(bool);
		jTextFieldKg.setVisible(bool);
		jComboBoxProveedores.setVisible(bool);
		jTextFieldPrecio.setVisible(bool);
		jLabel1.setVisible(bool);
		jLabel2.setVisible(bool);
		jLabel3.setVisible(bool);
		jLabel4.setVisible(bool);
		jLabel5.setVisible(bool);
		jLabel6.setVisible(bool);
		jLabel7.setVisible(bool);
		jLabel8.setVisible(bool);
		jLabel9.setVisible(bool);
		jLabel10.setVisible(bool);
		jLabelPrecio.setVisible(bool);
		jLabelFechaCaducidad.setVisible(bool);
		jDataChooserFechaCaducidad.setVisible(bool);
		
		//panel de búsqueda
		this.jPanelImprimir.setVisible(!bool);
		//this.jButtonCerrar.setVisible(!bool);
		//this.jButtonVerInforme.setVisible(!bool);
		//this.jLabel11.setVisible(!bool);
		//this.jDateChooserFechaFinal.setVisible(!bool);
		//this.jDateChooserFechaInicial.setVisible(!bool);
		
	}

}
