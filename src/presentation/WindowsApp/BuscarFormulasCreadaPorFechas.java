package presentation.WindowsApp;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.ComboBoxModel;
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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import mysqldal.MySQLFormulaDal;
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
import domain.Formula;

public class BuscarFormulasCreadaPorFechas extends Transactionable implements PresentationApp {

	private JDateChooser jDateChooserFecha;
	private JButton jButtonImprimir;
	private JButton jButtonDeleteDetail;
	private JButton jButtonModifyDetail;
	private JButton jButtonCancelFormula;
	private JButton jButtonModifyFormula;
	private JButton jButtonCancelDetail;
	private JLabel jLabel3;
	private JTable jTableDetails;
	private JScrollPane jTableScroller;
	private DefaultTableModel jTableDetailsModel;
	private DefaultTableModel jTableBusquedaModel;
	private JLabel jLabelTitulo;
	private MySQLFormulaDal fDal;
	private JLabel jLabel4;
	private JLabel jLabel8;
	private JTextField jTextFieldFormula;
	private JButton jButtonBuscar;
	private JPanel jPanelDetalles;
	private Formula f;
	private JLabel jLabelFechaFinal;
	private JLabel jLabelFechaInicial;
	private JDateChooser jDateChooserFinal;
	private JButton jButtonImprimirLista;
	private JLabel jLabel7;
	private JComboBox jComboBoxFormula;
	private JTextField jTextFieldLote;
	private JLabel jLabel6;
	private JTable jTableBusqueda;
	private JCheckBox jCheckBoxGranel;
	private JTextField jTextFieldTipo;
	private JLabel jLabel5;
	private JComboBox jComboBoxTipo;
	private JLabel jLabel2;
	private JLabel jLabel1;
	private JTextField jTextFieldPrecioTotal;
	private JScrollPane jScrollPane1;
	private JDateChooser jDateChooserInicial;
	private JPanel jPanelBusqueda;
	private JTextField jTextFieldSituacion;
	private JLabel jLabelSituacion;
	private JCheckBox jCheckBoxEnvasado;
	
	//Para asociar el combo de Formulas al de Tipo de Ganado
	private boolean showMainMenu=false;
	private boolean cargandoFormulas=false;
	
	//Variables de impresión
	private static final String reportNameFormula = "Informe_Formulas_Aplicadas.jrxml";
	private static final String reportNameListFormulas = "Informe_Listado_de_Formulas_Aplicadas.jrxml";
	JasperReport jasperReport;
    JasperPrint jasperPrint=null;
    Map<String, String> parameters = new HashMap<String, String>();
    JRTableModelDataSource ds = null;
    TableModel modelo = null;
	
    // Log
    private static Logger logger;

	public BuscarFormulasCreadaPorFechas() {
		super(new MySQLJDBCFacade());
		f = new Formula();
		fDal = new MySQLFormulaDal();
		
		logger = Logger.getLogger(this.getClass().getName());
		logger.setLevel(Level.INFO);
	    try {
		    PropertyConfigurator.configure("log4j_console.properties");
	    }catch (Exception e) {
			System.out.println("ERROR: " + e.getMessage() + " - " + e.getCause());
	    }
   
	    logger.setLevel(Level.INFO);
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
		JFprincipalLayout.rowHeights = new int[] {51, 20, 21, 20, 12, 20, 20, 20, 12, 20, 12, 20, 12, 20, 12, 21, 12, 21, 12, 21, 20, 20, 20, 25, 34, 33, 21, 7, 7};
		JFprincipalLayout.rowWeights = new double[] {0.0, 0.1, 0.0, 0.0, 0.0, 0.0, 0.1, 0.1, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.1, 0.0, 0.0, 0.0, 0.0, 0.0, 0.1, 0.1};
		JFprincipalLayout.columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.1, 0.0, 0.0, 0.0, 0.0, 0.1};
		JFprincipalLayout.columnWidths = new int[] {35, 91, 112, 88, 20, -3, 140, 149, 95, 7};
		
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
		jLabelTitulo.setText("Buscar Fórmulas Creadas");
		jLabelTitulo.setForeground(new java.awt.Color(0,0,160));
		jLabelTitulo.setBorder(new LineBorder(new java.awt.Color(0,0,0), 3, false));
		jLabelTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelTitulo.setHorizontalTextPosition(SwingConstants.CENTER);
		constraints.weighty = 0;

		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;

		jButtonDeleteDetail = new JButton ("Eliminar");
		constraints.gridx = 1;
		constraints.gridy = 3;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		JFprincipal.getContentPane().add(jButtonDeleteDetail, new GridBagConstraints(7, 26, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		jButtonDeleteDetail.setText("Eliminar Detalle");
		jButtonDeleteDetail.setToolTipText("Eliminar Detalle de Entrada Seleccionado");
		jButtonDeleteDetail.setEnabled(false);
		jButtonDeleteDetail.setVisible(false);

		jButtonModifyDetail = new JButton ("Eliminar");
		constraints.gridx = 1;
		constraints.gridy = 3;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		JFprincipal.getContentPane().add(jButtonModifyDetail, new GridBagConstraints(6, 26, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		jButtonModifyDetail.setText("Modificar Detalle");
		jButtonModifyDetail.setToolTipText("Modificar Detalle Seleccionado");
		jButtonModifyDetail.setEnabled(false);
		jButtonModifyDetail.setVisible(false);

		{
			jLabel3 = new JLabel();
			JFprincipal.getContentPane().add(jLabel3, new GridBagConstraints(2, 20, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
			jLabel3.setText("Líneas de detalle");
			jLabel3.setBackground(new java.awt.Color(192,192,192));
		}
		{
			jTableDetailsModel = new MyTableModel();
			jTableDetails = new JTable();
			jTableDetails.setModel(jTableDetailsModel);
			jTableDetails.setBorder(new LineBorder(new java.awt.Color(0,0,0), 0, false));
			jTableDetails.setToolTipText("Lista de Registros de Entrada");
			jTableDetails.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			jTableDetails.setMaximumSize(new java.awt.Dimension(0, 0));
			jTableScroller = new JScrollPane(jTableDetails);
			JFprincipal.getContentPane().add(jTableScroller, new GridBagConstraints(1, 21, 8, 5, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
			jTableScroller.setPreferredSize(new Dimension(250, 472));
			jTableScroller.setBorder(new LineBorder(new java.awt.Color(0,0,0), 3, true));
		}
		{
			jLabel4 = new JLabel();
			JFprincipal.getContentPane().add(jLabel4, new GridBagConstraints(2, 15, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			jLabel4.setText("Fecha de Creación");
		}
		{
			jDateChooserFecha = new JDateChooser();
			JFprincipal.getContentPane().add(jDateChooserFecha, new GridBagConstraints(3, 15, 2, 1, 0.0, 0.0, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));			
		}
		{
			jLabel8 = new JLabel();
			JFprincipal.getContentPane().add(jLabel8, new GridBagConstraints(2, 11, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			jLabel8.setText("Fórmula");
		}
		{
			jTextFieldFormula = new JTextField();
			JFprincipal.getContentPane().add(jTextFieldFormula, new GridBagConstraints(3, 11, 5, 1, 0.0, 0.0, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
			jTextFieldFormula.setText("");
			jTextFieldFormula.setEnabled(false);
		}
		{
			jPanelDetalles = new JPanel();
			JFprincipal.getContentPane().add(jPanelDetalles, new GridBagConstraints(1, 9, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		}
		{
			jCheckBoxEnvasado = new JCheckBox();
			JFprincipal.getContentPane().add(jCheckBoxEnvasado, new GridBagConstraints(2, 18, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			jCheckBoxEnvasado.setText("Envasado");
		}
		{
			jCheckBoxGranel = new JCheckBox();
			JFprincipal.getContentPane().add(jCheckBoxGranel, new GridBagConstraints(3, 18, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			jCheckBoxGranel.setText("Granel");
		}
		{
			jLabelSituacion = new JLabel();
			JFprincipal.getContentPane().add(jLabelSituacion, new GridBagConstraints(2, 17, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			jLabelSituacion.setText("Situación");
		}
		{
			jTextFieldSituacion = new JTextField();
			JFprincipal.getContentPane().add(jTextFieldSituacion, new GridBagConstraints(3, 17, 5, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		}
		{
			jPanelBusqueda = new JPanel();
			GridBagLayout jPanel2Layout = new GridBagLayout();
			jPanel2Layout.rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
			jPanel2Layout.rowHeights = new int[] {27, 20, 10, 20, 10, 24, 10, 24, 14, 20, 27};
			jPanel2Layout.columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.1};
			jPanel2Layout.columnWidths = new int[] {104, 144, 36, 150, 75, 150, 62, 7};
			jPanelBusqueda.setLayout(jPanel2Layout);
			JFprincipal.getContentPane().add(jPanelBusqueda, new GridBagConstraints(1, 1, 8, 7, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
			jPanelBusqueda.setBackground(new java.awt.Color(192,192,192));
			{
				jButtonBuscar = new JButton();
				jPanelBusqueda.add(jButtonBuscar, new GridBagConstraints(1, 9, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
				jButtonBuscar.setText("Buscar");
			}
			{
				jDateChooserInicial = new JDateChooser();
				jPanelBusqueda.add(jDateChooserInicial, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
			}
			{
				jDateChooserFinal = new JDateChooser();
				jPanelBusqueda.add(jDateChooserFinal, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
			}
			{
				jLabelFechaInicial = new JLabel();
				jPanelBusqueda.add(jLabelFechaInicial, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jLabelFechaInicial.setText("Fecha Inicial: ");
			}
			{
				jLabelFechaFinal = new JLabel();
				jPanelBusqueda.add(jLabelFechaFinal, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jLabelFechaFinal.setText("Fecha Final: ");
			}
			{
				jLabel2 = new JLabel();
				jPanelBusqueda.add(jLabel2, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jLabel2.setText("Tipo de Ganado:");
			}
			{
				jLabel7 = new JLabel();
				jPanelBusqueda.add(jLabel7, new GridBagConstraints(0, 7, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jLabel7.setText("Tipo de Fórmula:");
			}
			{
				ComboBoxModel jComboBoxTipoModel = new DefaultComboBoxModel(
						new String[] { "Item One", "Item Two" });
				jComboBoxTipo = new JComboBox();
				jPanelBusqueda.add(jComboBoxTipo, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
				jComboBoxTipo.setModel(jComboBoxTipoModel);
				jComboBoxTipo.setEnabled(true);
				jComboBoxTipo.setVisible(true);
			}
			{
				ComboBoxModel jComboBoxFormulaModel = new DefaultComboBoxModel(
						new String[] { "Item One", "Item Two" });
				jComboBoxFormula = new JComboBox();
				jPanelBusqueda.add(jComboBoxFormula, new GridBagConstraints(1, 7, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
				jComboBoxFormula.setModel(jComboBoxFormulaModel);
				jComboBoxFormula.setEnabled(true);
				jComboBoxFormula.setVisible(true);
			}
			{
				jTableBusquedaModel = new MyTableModel();
				jTableBusqueda = new JTable();
				jTableBusqueda.setModel(jTableBusquedaModel);
				jTableBusqueda.setBorder(new LineBorder(new java.awt.Color(0,0,0), 0, false));
				jTableBusqueda.setToolTipText("Lista de fómulas creadas");
				jTableBusqueda.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				jTableBusqueda.setMaximumSize(new java.awt.Dimension(0, 0));
				jScrollPane1 = new JScrollPane(jTableBusqueda);
				jPanelBusqueda.add(jScrollPane1, new GridBagConstraints(3, 1, 4, 7, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
				jPanelBusqueda.add(getJButtonImprimirLista(), new GridBagConstraints(4, 9, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jScrollPane1.setPreferredSize(new Dimension(250, 472));
				jScrollPane1.setBorder(new LineBorder(new java.awt.Color(0,0,0), 3, true));
				jScrollPane1.setWheelScrollingEnabled(false);
			}
		}
		{
			jButtonCancelDetail = new JButton();
			JFprincipal.getContentPane().add(jButtonCancelDetail, new GridBagConstraints(3, 26, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			jButtonCancelDetail.setText("Cancelar");
			jButtonCancelDetail.setVisible(false);
		}
		{
			jButtonModifyFormula = new JButton();
			JFprincipal.getContentPane().add(jButtonModifyFormula, new GridBagConstraints(7, 19, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			jButtonModifyFormula.setText("Modificar Fórmula");
		}
		{
			jButtonCancelFormula = new JButton();
			JFprincipal.getContentPane().add(jButtonCancelFormula, new GridBagConstraints(6, 19, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			jButtonCancelFormula.setText("Cancelar");
			jButtonCancelFormula.setVisible(false);
		}
		{
			jTextFieldPrecioTotal = new JTextField();
			JFprincipal.getContentPane().add(jTextFieldPrecioTotal, new GridBagConstraints(8, 27, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		}
		{
			jLabel1 = new JLabel();
			JFprincipal.getContentPane().add(jLabel1, new GridBagConstraints(7, 27, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.VERTICAL, new Insets(0, 0, 0, 0), 0, 0));
			jLabel1.setText("Precio Total:    ");
			jLabel1.setBackground(new java.awt.Color(192, 192, 192));
		}
		{
			jLabel5 = new JLabel();
			JFprincipal.getContentPane().add(jLabel5, new GridBagConstraints(2, 13, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			jLabel5.setText("Tipo de Ganado");
		}
		{
			jLabel6 = new JLabel();
			JFprincipal.getContentPane().add(jLabel6, new GridBagConstraints(2, 9, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			jLabel6.setText("Lote");
		}
		{
			jTextFieldTipo = new JTextField();
			JFprincipal.getContentPane().add(jTextFieldTipo, new GridBagConstraints(3, 13, 5, 1, 0.0, 0.0, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
			jTextFieldTipo.setText("");
			jTextFieldTipo.setEnabled(false);
		}
		{
			jTextFieldLote = new JTextField();
			JFprincipal.getContentPane().add(jTextFieldLote, new GridBagConstraints(3, 9, 2, 1, 0.0, 0.0, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
			jTextFieldLote.setText("");
			jTextFieldLote.setEnabled(false);
		}
		{
			JFprincipal.getContentPane().add(getJButtonImprimir(), new GridBagConstraints(4, 27, 3, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		}

		//Iniciador de eventos
		initEventos();
		
//		MUESTRO EL FRAME CENTRADO
		JFprincipal.pack();
		JFprincipal.setSize(840, 800);
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
		JFprincipal.setTitle("Visualizar-Imprimir las piensos creados");

		System.out.println("Abriendo ShowMainMenu");
		jButtonImprimirLista.setVisible(false);
		jButtonImprimir.setVisible(false);
		

		try {
			cargarComboTipos();
			cargarComboFormulas();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (DalException e) {
			e.printStackTrace();
		}
	}
	
	private void initEventos(){
		
		//Seleccionar un registro en la lista de fórmulas buscadas
		ListSelectionModel listMod =  jTableBusqueda.getSelectionModel();
		listMod.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listMod.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent evt) {
				jTableBusquedaValueChange(evt);
			}
		});
		
		//Cambia la lista de detalles al cambiar la fórmula seleccionada
		this.jComboBoxTipo.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				try {
					if(showMainMenu) {
						System.out.println("cargarComboFormulas		no estoy cargando...");
						if(cargandoFormulas) {
							cargarComboFormulas();
						}
					} else {
						showMainMenu = true;
						cargandoFormulas = true;
					} 
				
					
				} catch (SQLException e) {
					
					e.printStackTrace();
				} catch (DalException e) {
					
					e.printStackTrace();
				}
			}
		});
		
		//Realiza la busqueda entre las fechas introducidas
		jButtonBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButtonBuscarActionPerformed(evt);
			}
		});
		
		//Eliminar Detalle de la fórmula seleccionado en la lista
		jButtonDeleteDetail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButtonDeleteDetailActionPerformed(evt);
			}
		});
		
		//Modificar Detalle de la fórmula seleccionado en la lista
		jButtonModifyDetail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButtonModifyDetailActionPerformed(evt);
			}
		});
		
		//Cancelar la modificacion de un detalle
		jButtonCancelDetail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButtonCancelDetailActionPerformed(evt);
			}
		});
		
		//Modificar Detalle de la fórmula seleccionado en la lista
		jButtonModifyFormula.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButtonModifyFormulaActionPerformed(evt);
			}
		});
		
		//Cancelar la modificacion de una fórmula buscada
		jButtonCancelFormula.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButtonCancelFormulaActionPerformed(evt);
			}
		});
		
		//Imprimir fórmula seleccionada
		jButtonImprimir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButtonImprimirActionPerformed(evt);
			}
		});
		
		//Imprimir lista de fórmulas encontradas en la búsqueda
		jButtonImprimirLista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButtonImprimirListaActionPerformed(evt);
			}
		});
		
		//Controlar que no este los dos CheckBox activados a la vez
		jCheckBoxEnvasado.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent evt) {
				jCheckBoxEnvasadoActionPerformed(evt);
			}
		});
		
		//Controlar que no este los dos CheckBox activados a la vez
		jCheckBoxGranel.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent evt) {
				jCheckBoxGranelActionPerformed(evt);
			}
		});
		
	}
	
	public void jTableBusquedaValueChange(ListSelectionEvent e) {
		
    	if (e.getValueIsAdjusting() == false) {
	        if (jTableBusqueda.getSelectedRow() != -1) {
		             	
	        	try {
					cargarTableDetails();
				} catch (SQLException e1) {
					e1.printStackTrace();
				} catch (DalException e1) {
					e1.printStackTrace();
				}
			}
    	}	        	
	}
	
	private void jCheckBoxEnvasadoActionPerformed(ChangeEvent evt) {
		if (jCheckBoxEnvasado.isSelected() && jCheckBoxGranel.isSelected()){
			jCheckBoxGranel.setSelected(false);
		} 
	}
	
	private void jCheckBoxGranelActionPerformed(ChangeEvent evt) {
		if (jCheckBoxGranel.isSelected() && jCheckBoxEnvasado.isSelected()){
			jCheckBoxEnvasado.setSelected(false);
		} 
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
				
				cargarTableBusqueda();
				System.out.println("Busqueda realizada con éxito");
			} catch (DalException e) {
				e.printStackTrace();
				System.out.println("ERROR al añadir la fórmula: " + f.getNombre() );
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("ERROR al añadir la fórmula: " + f.getNombre() );
			} 
		}
	}
	
	private void jButtonDeleteDetailActionPerformed(ActionEvent evt) {

	}
	
	private void jButtonModifyDetailActionPerformed(ActionEvent evt) {

	}
	
	private void jButtonCancelDetailActionPerformed(ActionEvent evt) {

		this.jButtonDeleteDetail.setEnabled(false);
		this.jButtonModifyDetail.setEnabled(false);
		this.jButtonCancelDetail.setVisible(false);
	}
	
	private void jButtonModifyFormulaActionPerformed(ActionEvent evt) {
		if (this.jTextFieldFormula.getText().equals("")) {
			JOptionPane.showMessageDialog(Principal.msgbox,
				    "Debe introducir un nombre para la fórmula",
				    "Campo Incompleto",
				    JOptionPane.WARNING_MESSAGE);
		} else {
			if (this.jTextFieldFormula.getText() != ""){
				int n = JOptionPane.showConfirmDialog(Principal.msgbox,
					    "¿Desee modificar los datos de la fórmula?",
					    "Confirmar",
					    JOptionPane.YES_NO_OPTION);
				if (n==JOptionPane.YES_OPTION) {
					f.setNombre(this.jTextFieldFormula.getText());
					f.setTipoDeGanado(this.jTextFieldTipo.getText());
					f.setEstado(getEstado());
					f.setSituacion(this.jTextFieldSituacion.getText());
					f.setPrecio(Float.valueOf(jTextFieldPrecioTotal.getText()).floatValue());
					f.setFecha(this.jDateChooserFecha.getDate());
					f.setFormulaID(Integer.valueOf((String) jTableBusquedaModel.getValueAt(jTableBusqueda.getSelectedRow(), 0)));
					try {
						fDal.modify(f);
		
					} catch (DalException e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(Principal.msgbox,
							    e.getMessage(),
							    "ERROR al modificar la fórmula",
							    JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		}
	}
	
	private void jButtonCancelFormulaActionPerformed(ActionEvent evt) {

		this.jButtonModifyFormula.setEnabled(false);
		this.jButtonCancelFormula.setVisible(false);			
	}
	
	private void jButtonImprimirListaActionPerformed(ActionEvent evt) {
		try
	    {
	        // Llenamos los parámetros
			java.text.DateFormat sdfAux = new java.text.SimpleDateFormat("dd/MM/yyyy");
			parameters.put("fecha_inicial", sdfAux.format(this.jDateChooserInicial.getDate()));
			parameters.put("fecha_final", sdfAux.format(this.jDateChooserFinal.getDate()));
	    	
	    	// Llenamos los detalles uno a uno
	    	Vector<String> tableColumns = new Vector<String>();
            tableColumns.add("formula");
            tableColumns.add("ganado");
            tableColumns.add("fecha");
            tableColumns.add("lote");
            tableColumns.add("kg");
            
            //tableColumns.add("Municipio");
            //tableColumns.add("NumExpIns");
            modelo = new DefaultTableModel(tableColumns,jTableBusquedaModel.getRowCount());
            for(int i = 0; i < jTableBusquedaModel.getRowCount() ;i++){
				System.out.println("\n i: " + i + "\n\t lote: " + String.valueOf(jTableBusquedaModel.getValueAt(i, 0)) + "\n\t formula: " + String.valueOf(jTableBusquedaModel.getValueAt(i, 1)));
                modelo.setValueAt(String.valueOf(jTableBusquedaModel.getValueAt(i, 1)) , i, 0);
                modelo.setValueAt(String.valueOf(jTableBusquedaModel.getValueAt(i, 2)) , i, 1);
                modelo.setValueAt(String.valueOf(jTableBusquedaModel.getValueAt(i, 0)) , i, 2);
                modelo.setValueAt(getFechaCreacion(String.valueOf(jTableBusquedaModel.getValueAt(i, 0))) , i, 3);
                modelo.setValueAt(getKgTotales(String.valueOf(jTableBusquedaModel.getValueAt(i, 0))) , i, 4);
            }
            ds = new JRTableModelDataSource(modelo);
     	    
            //1-Compilamos el archivo XML y lo cargamos en memoria
            PropertiesUtil properties = new PropertiesUtil();
            System.out.println("1-Compilamos el archivo XML y lo cargamos en memoria: " + properties.getProperty(PropertiesUtil.reportsPath) + reportNameListFormulas);
	    	jasperReport = JasperCompileManager.compileReport(properties.getProperty(PropertiesUtil.reportsPath) + reportNameListFormulas);
            
	        //2-Llenamos el reporte con la información y parámetros necesarios (En este caso nada)
	    	System.out.println("2-Llenamos el reporte con la información y parámetros necesarios");	        
	        jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, ds);

	        //3-Mostramos el resultado si todo ha ido bien.
   	        JasperViewer.viewReport(jasperPrint,false);
   	        
   	        
   	        
	        /*JFileChooser filechooser = new JFileChooser();
	        JFrame frame = new JFrame();
            int returnVal = filechooser.showSaveDialog(frame);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
            	File file = filechooser.getSelectedFile();
//            	3-Exportamos el reporte a pdf y lo guardamos en disco
    	        System.out.println("3-Exportamos el reporte a pdf y lo guardamos en disco: " + file.getName());
    	        JasperExportManager.exportReportToPdfFile(jasperPrint, file.getName());
            } */
	        
	    }
	    catch (Exception e)
	    {
	    	System.out.println("ERROR jButtonImprimirListaActionPerformed: " + e.getMessage());
	    	e.printStackTrace();
	    }

	}
	
	private String getFechaCreacion(String id) {
		String result = "";
		ResultSet rs = null;
		
		try {
			rs=(ResultSet) getFacade().executeQuery("SELECT fecha FROM formula WHERE ID = " + id);
		} catch (Exception e) {
			System.out.println("ERROR getFechaCreacion("+id+"): " + e.getMessage());
		} 
		
		
		// Obtenemos la fecha de la consulta realizada (rs)
		try {
			rs.next();
			java.text.DateFormat sdfAux = new java.text.SimpleDateFormat("dd/MM/yyyy");
			result =  sdfAux.format(rs.getDate("fecha"));
			
		} catch (Exception e) {
			System.out.println("ERROR getFechaCreacion("+id+"): " + e.getMessage());
			e.printStackTrace();
		}
		
		return result;
	}
	
	private String getKgTotales(String id) {
		float kgTotales = 0;
		ResultSet rs = null;
		
		try {
			rs=(ResultSet) getFacade().executeQuery("SELECT kg FROM formula_det WHERE IDFORMULA = " + id);
		} catch (Exception e) {
			System.out.println("ERROR getKgTotales("+id+"): " + e.getMessage());
		} 
		
		
		// Obtenemos la fecha de la consulta realizada (rs)
		try {
			
			while(rs.next()) {
				kgTotales = kgTotales + rs.getFloat("kg");
			}
			
		} catch (Exception e) {
			System.out.println("ERROR getKgTotales("+id+"): " + e.getMessage());
			e.printStackTrace();
		}
		
		return String.valueOf(kgTotales);
	}

	private void jButtonImprimirActionPerformed(ActionEvent evt) {
		try
	    {
	        // Llenamos los parámetros
			java.text.DateFormat sdfAux = new java.text.SimpleDateFormat("dd/MM/yyyy");
			parameters.put("id", this.jTextFieldLote.getText());
	    	parameters.put("nombre", this.jTextFieldFormula.getText());
	    	parameters.put("tipo_de_ganado", this.jTextFieldTipo.getText());
	    	parameters.put("fecha", sdfAux.format(this.jDateChooserFecha.getDate()));
	    	parameters.put("estado", getEstado());
	    	parameters.put("situacion", this.jTextFieldSituacion.getText());
	    	parameters.put("precio", this.jTextFieldPrecioTotal.getText());
	    	
	    	// Llenamos los detalles uno a uno
	    	Vector<String> tableColumns = new Vector<String>();
            tableColumns.add("nombre");
            tableColumns.add("fecha");
            tableColumns.add("lote");
            tableColumns.add("fechacaducidad");
            tableColumns.add("kg");
            tableColumns.add("precio");
            //tableColumns.add("Municipio");
            //tableColumns.add("NumExpIns");
            modelo = new DefaultTableModel(tableColumns,jTableDetailsModel.getRowCount());
            for(int i = 0; i < jTableDetailsModel.getRowCount() ;i++){
				System.out.println("\n i: " + i + "\n\t producto: " + String.valueOf(jTableDetailsModel.getValueAt(i, 0)) + "\n\t cantidad: " + String.valueOf(jTableDetailsModel.getValueAt(i, 1)));
                modelo.setValueAt(String.valueOf(jTableDetailsModel.getValueAt(i, 2)) , i, 0);
                modelo.setValueAt(String.valueOf(jTableDetailsModel.getValueAt(i, 3)) , i, 1);
                modelo.setValueAt(String.valueOf(jTableDetailsModel.getValueAt(i, 4)) , i, 2);
                if (String.valueOf(jTableDetailsModel.getValueAt(i, 5)).equals("null"))
                	modelo.setValueAt("", i, 3);
                else 
                	modelo.setValueAt(String.valueOf(jTableDetailsModel.getValueAt(i, 5)) , i, 3);
                modelo.setValueAt(String.valueOf(jTableDetailsModel.getValueAt(i, 6)) , i, 4);
                modelo.setValueAt(String.valueOf(jTableDetailsModel.getValueAt(i, 7)) , i, 5);
            }
            ds = new JRTableModelDataSource(modelo);
     	    
            //1-Compilamos el archivo XML y lo cargamos en memoria
            PropertiesUtil properties = new PropertiesUtil();
            logger.info("Compilamos el archivo XML y lo cargamos en memoria: " + properties.getProperty(PropertiesUtil.reportsPath) + reportNameFormula);
            System.out.println("1-Compilamos el archivo XML y lo cargamos en memoria: " + properties.getProperty(PropertiesUtil.reportsPath) + reportNameFormula);
	    	jasperReport = JasperCompileManager.compileReport(properties.getProperty(PropertiesUtil.reportsPath) + reportNameFormula);
           
            //2-Llenamos el reporte con la información y parámetros necesarios (En este caso nada)
	    	logger.info("Llenamos el reporte con la información y parámetros necesarios");
	    	System.out.println("2-Llenamos el reporte con la información y parámetros necesarios");	        
	        jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, ds);

	        //3-Mostramos el resultado si todo ha ido bien.
	        logger.info("Visualizamos el informe");
   	        JasperViewer.viewReport(jasperPrint,false);
   	        
   	        
   	        
	        /*JFileChooser filechooser = new JFileChooser();
	        JFrame frame = new JFrame();
            int returnVal = filechooser.showSaveDialog(frame);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
            	File file = filechooser.getSelectedFile();
//            	3-Exportamos el reporte a pdf y lo guardamos en disco
    	        System.out.println("3-Exportamos el reporte a pdf y lo guardamos en disco: " + file.getName());
    	        JasperExportManager.exportReportToPdfFile(jasperPrint, file.getName());
            } */
	        
	    }
	    catch (JRException e)
	    {
	      logger.error(e.getMessage());
	    }

	}
	
	private void cargarComboTipos() throws SQLException, DalException{
		ResultSet rs;

		cargandoFormulas = false;
		jComboBoxTipo.removeAllItems();
		
		try {
			rs=(ResultSet) getFacade().executeQuery("SELECT * FROM tipo_de_ganado ORDER BY tipo DESC");
		} catch (SQLException e) {
			System.out.println("ERROR cargando ComboBox Tipo_de_ganado");
			throw new DalException(e);
		} 
		
		// Añadimos opción inicial "TODOS"
		jComboBoxTipo.addItem("Todos");
		
		// Añadimos los tipos de la consulta realizada (rs)
		while (rs.next()){
			jComboBoxTipo.addItem(rs.getString("tipo"));
		}
		
		cargandoFormulas = true;
		
	}
	
	private void cargarComboFormulas() throws SQLException, DalException{
		ResultSet rs;
		String query = null;
		
		jComboBoxFormula.removeAllItems();
		
		try {
			query = "SELECT * FROM formula_creada ";
			
			// Incluimos el tipo de ganado indicado en caso de ser != de "Todos"
			if (!jComboBoxTipo.getSelectedItem().toString().equals("Todos")) {
				query = query + " WHERE tipo_de_ganado='" + jComboBoxTipo.getSelectedItem().toString() + "'";
			}
			
			query = query + " ORDER BY nombre";
			rs=(ResultSet) getFacade().executeQuery(query);
			
		} catch (SQLException e) {
			System.out.println("ERROR cargando ComboBox Tipo_de_Fórmula");
			throw new DalException(e);
		} 
		
		// Añadimos opción inicial "TODAS"
		jComboBoxFormula.addItem("Todas");
		
		// Añadimos los tipos de la consulta realizada (rs)
		while (rs.next()){
			jComboBoxFormula.addItem(rs.getString("nombre"));
		}
		
	}

	private DefaultTableModel initTableDetailsModel() {
		Object[][] data = null;
		Object[] headers = new Object[] {"ID", "IDFORMULA", "PRODUCTO", "FECHA ENTRADA", "LOTE", "FECHA CADUC.", "Nº KG", "PRECIO  (€/KG)"};
		
		return new DefaultTableModel(data,headers);
	}
	
	private void cargarTableDetails() throws SQLException, DalException{
		ResultSet rs, rsAux;
		
		jTableDetailsModel = initTableDetailsModel();
		
		try {
			rs=(ResultSet) getFacade().executeQuery("SELECT * FROM Formula_det " + 
														"WHERE IDFORMULA = " + Integer.valueOf((String) jTableBusquedaModel.getValueAt(jTableBusqueda.getSelectedRow(), 0)) +
														" ORDER BY id");
			rsAux=(ResultSet) getFacade().executeQuery("SELECT * FROM Formula " + 
														"WHERE ID = " + Integer.valueOf((String) jTableBusquedaModel.getValueAt(jTableBusqueda.getSelectedRow(), 0))+
														" ORDER BY id");
		} catch (SQLException e) {
			throw new DalException(e);
		} 
		
		if (rsAux.next()){
			this.jTextFieldLote.setText(String.valueOf(rsAux.getInt("id")));
			this.jTextFieldFormula.setText(rsAux.getString("nombre"));
			this.jTextFieldTipo.setText(rsAux.getString("tipo_de_ganado"));
			this.jTextFieldSituacion.setText(rsAux.getString("situacion"));
			setEstado(rsAux.getString("estado"));
			this.jDateChooserFecha.setDate(rsAux.getDate("fecha"));
			this.jTextFieldPrecioTotal.setText(String.valueOf(rsAux.getFloat("precio")));
			
			while (rs.next()){
				Object [] row = new Object[]{String.valueOf(rs.getInt("id")),
											String.valueOf(rs.getInt("idformula")),
											rs.getString("nombre"), 
											rs.getDate("fecha"),
											rs.getString("lote"),
											rs.getDate("fechacaducidad"),
											String.valueOf(rs.getFloat("kg")),
											String.valueOf(rs.getFloat("precio") * rs.getFloat("kg")) + "  (" + String.valueOf(rs.getFloat("precio")) + ")"};
				jTableDetailsModel.addRow(row);
				jTableDetails.setModel(jTableDetailsModel);
			}	
		}

		jButtonImprimir.setVisible(true);
		jButtonDeleteDetail.setEnabled(true);
    	jButtonModifyDetail.setEnabled(true);  
	}
	
	private DefaultTableModel initTableBusquedaModel() {
		Object[][] data = null;
		Object[] headers = new Object[] {"ID", "TIPO_DE_FÓRMULA", "TIPO_DE_GANADO"};
		
		return new DefaultTableModel(data,headers);
	}
	
	private void cargarTableBusqueda() throws SQLException, DalException{
		int i;
		ResultSet rs;
		String fechaInicial, fechaFinal, query;
		
		// Limpiamos la tabla de fórmulas buscadas
		jTableBusquedaModel = initTableBusquedaModel();
		
		// Limpiamos el resto de campos
		jButtonImprimir.setVisible(false);
		this.jTextFieldFormula.setText("");
		this.jTextFieldTipo.setText("");
		this.jTextFieldSituacion.setText("");
		this.jTextFieldPrecioTotal.setText("");
		this.jTextFieldLote.setText("");
		this.jDateChooserFecha.setDate(null);
		
		// Limpiamos la tabla de detalles de una fórmula seleccionada
		jTableDetailsModel = initTableDetailsModel();
		jTableDetails.setModel(jTableDetailsModel);
		
		setEstado("");
		
		try {
			java.text.DateFormat sdfAux = new java.text.SimpleDateFormat("yyyy/MM/dd");
			fechaInicial =  sdfAux.format(jDateChooserInicial.getDate());
			fechaFinal =  sdfAux.format(jDateChooserFinal.getDate());
			
			query = "SELECT * FROM Formula " +
						"WHERE ((FECHA >= '" + fechaInicial + "' " +
							"AND FECHA <= '" + fechaFinal + "') ";
							
			// Incluimos el tipo de ganado indicado en caso de ser != de "Todos"
			if (!jComboBoxTipo.getSelectedItem().toString().equals("Todos")) {
				query = query + "AND tipo_de_ganado = '" + this.jComboBoxTipo.getSelectedItem().toString() + "' ";
			}
			// Incluimos la formula indicada en caso de ser != de "Todas"
			if (!jComboBoxFormula.getSelectedItem().toString().equals("Todas")) {
				query = query + "AND nombre = '" + this.jComboBoxFormula.getSelectedItem().toString() + "'";
			}
			query = query + ") ORDER BY fecha ASC";
			
			rs=(ResultSet) getFacade().executeQuery(query);
			
		} catch (SQLException e) {
			System.out.println("\n**************");
			System.out.println("error buscando");
			System.out.println("**************\n");
			throw new DalException(e);
		} 
		i=0;
		
		try {
			while (rs.next()){
			
				if (i== 2500){
					JOptionPane.showMessageDialog(Principal.msgbox,
						    "Se han encontrado más de 2500 fórmulas, realice otra busqueda sino encuentra la deseada.",
						    "Demasiados registros encontrados",
						    JOptionPane.WARNING_MESSAGE);
					break;
				}
				i++;
				
				Object [] row = new Object[]{String.valueOf(rs.getInt("id")),
						rs.getString("nombre"), 
						rs.getString("TIPO_DE_GANADO")};
						jTableBusquedaModel.addRow(row);
						jTableBusqueda.setModel(jTableBusquedaModel);
			}
			
			jButtonImprimirLista.setVisible(true);
		} catch (Exception e) {
			jButtonImprimirLista.setVisible(false);
			System.out.println("\n*************************************************");
			System.out.println("error añadiendo registros en la tabla de búsqueda");
			System.out.println("*************************************************\n");
			throw new DalException(e);
		}
		if (i==0){
			jButtonImprimirLista.setVisible(false);
			jTableBusqueda.setModel(jTableBusquedaModel);
		}
	}
	
	private String getEstado() {
		String estado ="";
		if (jCheckBoxEnvasado.isSelected()){
			estado = jCheckBoxEnvasado.getText();
		} else if (jCheckBoxGranel.isSelected()){
			estado = jCheckBoxGranel.getText();
		}
		
		return estado;
	}
	
	private void setEstado(String estado) {

		jCheckBoxGranel.setSelected(false);
		jCheckBoxEnvasado.setSelected(false);
		
		if (estado.equals("Envasado")){
			jCheckBoxEnvasado.setSelected(true);
		} else if (estado.equals("Granel")){
			jCheckBoxGranel.setSelected(true);
		} 

	}

	private JButton getJButtonImprimir() {
		if (jButtonImprimir == null) {
			jButtonImprimir = new JButton();
			jButtonImprimir.setText("Imprimir Fórmula Seleccionada");
			jButtonImprimir.setVisible(false);
		}
		return jButtonImprimir;
	}
	
	private JButton getJButtonImprimirLista() {
		if(jButtonImprimirLista == null) {
			jButtonImprimirLista = new JButton();
			jButtonImprimirLista.setText("Imprimir Fórmulas Encontradas");
			jButtonImprimirLista.setVisible(false);
		}
		return jButtonImprimirLista;
	}

}

