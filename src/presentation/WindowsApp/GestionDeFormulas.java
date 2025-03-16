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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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

import mysqldal.MySQLDetailCreadoDal;
import mysqldal.MySQLFormulasCreadasDal;
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
import dal.DalException;
import dal.Transactionable;
import domain.DetailFormulaCreada;
import domain.FormulasCreadas;
import java.awt.Font;

public class GestionDeFormulas extends Transactionable implements PresentationApp {

	private JButton jButtonAddDetail;
	private JButton jButtonEliminar;
	private JButton jButtonModificar;
	private JButton jButtonCancelar;
	private JLabel jLabel2;
	private JComboBox jComboBoxProductos;
	private DefaultComboBoxModel jComboBoxProductosModel;
	private JLabel jLabel3;
	private JTable jTableDetails;
	private JScrollPane jTableScroller;
	private DefaultTableModel jTableDetailsModel;
	private JLabel jLabelTitulo;
	private MySQLFormulasCreadasDal fDal;
	private JLabel jLabel8;
	private JTextField jTextFieldFormula;
	private JLabel jLabel5;
	private JTextField jTextFieldKg;
	private JButton jButtonFormula;
	private JComboBox jComboBoxFormula;
	private JPanel jPanel;
	private JPanel jPanel1;
	private JButton jButtonModifyFormula;
	private FormulasCreadas f, fEliminar;
	private DetailFormulaCreada det, detEliminar;
	private MySQLDetailCreadoDal dDal;
	private JButton jButtonImprimir;
	private JComboBox jComboBoxTipo;
	private JLabel jLabel1;
	private JButton jButtonCancelarFormula;
	private JButton jButtonDeleteFormula;
	JFrame JFprincipal;
	private boolean showMainMenu=false;
	private boolean cargandoFormulas=false;
	
	//Constantes para el texto de jButtonFormula
	private String aceptar = "Aceptar";
	private String nueva = "Nueva Fórmula";
	private String modificar = "Modificar Fórmula";
	
//	 Variables de impresión
	private static final String reportName = "Informe_Formulas.jrxml";
	JasperReport jasperReport;
    JasperPrint jasperPrint=null;
    Map<String, String> parameters = new HashMap<String, String>();
    JRTableModelDataSource ds = null;
    TableModel modelo = null;
    ResultSet rsImprimir=null;
    private JLabel lblNewLabel;
	
	public GestionDeFormulas() {
		super(new MySQLJDBCFacade());
		f = new FormulasCreadas();
		fEliminar = new FormulasCreadas();
		fDal = new MySQLFormulasCreadasDal();
		det = new DetailFormulaCreada();
		dDal = new MySQLDetailCreadoDal();
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	public void showMainMenu() {
		JFprincipal = new JFrame("Molino v2.0");
		GridBagConstraints constraints = new GridBagConstraints();
		
		//Indico el layout de JFprincipal
		GridBagLayout JFprincipalLayout = new GridBagLayout();
		JFprincipal.getContentPane().setLayout(JFprincipalLayout);
		JFprincipalLayout.rowHeights = new int[] {51, 21, 20, 12, 20, 21, 12, 12, 21, 12, 21, 12, 21, 12, 21, 12, 21, 12, 21, 20, 20, 7, 7, 7, 7, 7, 7, 7, 7, 7};
		JFprincipalLayout.rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.0, 0.1};
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
		jLabelTitulo.setText("Gestión de Fórmulas");
		jLabelTitulo.setForeground(new java.awt.Color(0,0,160));
		jLabelTitulo.setBorder(new LineBorder(new java.awt.Color(0,0,0), 3, false));
		jLabelTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelTitulo.setHorizontalTextPosition(SwingConstants.CENTER);
		constraints.weighty = 0;

		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;

		jButtonEliminar = new JButton ("Eliminar");
		constraints.gridx = 1;
		constraints.gridy = 3;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		JFprincipal.getContentPane().add(jButtonEliminar, new GridBagConstraints(7, 25, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		jButtonEliminar.setText("Eliminar Detalle");
		jButtonEliminar.setToolTipText("Eliminar Detalle de Entrada Seleccionado");
		jButtonEliminar.setEnabled(false);
		
		jButtonModificar = new JButton ("Eliminar");
		constraints.gridx = 1;
		constraints.gridy = 3;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		JFprincipal.getContentPane().add(jButtonModificar, new GridBagConstraints(6, 25, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		jButtonModificar.setText("Modificar Detalle");
		jButtonModificar.setToolTipText("Modificar Detalle Seleccionado");
		jButtonModificar.setEnabled(false);

		{
			jLabel3 = new JLabel();
			JFprincipal.getContentPane().add(jLabel3, new GridBagConstraints(2, 18, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
			jLabel3.setText("Líneas de detalle");
			jLabel3.setBackground(new java.awt.Color(192,192,192));
		}
		{
			jTableDetailsModel = new MyTableModel();
			jTableDetails = new JTable();
			//JFprincipal.getContentPane().add(jTableRegistros, new GridBagConstraints(1, 9, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			jTableDetails.setModel(jTableDetailsModel);
			jTableDetails.setBorder(new LineBorder(new java.awt.Color(0,0,0), 0, false));
			jTableDetails.setToolTipText("Lista de Registros de Entrada");
			jTableDetails.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			jTableDetails.setMaximumSize(new java.awt.Dimension(0, 0));
			jTableScroller = new JScrollPane(jTableDetails);
			JFprincipal.getContentPane().add(jTableScroller, new GridBagConstraints(1, 19, 8, 5, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
			jTableScroller.setPreferredSize(new Dimension(250, 472));
			jTableScroller.setBorder(new LineBorder(new java.awt.Color(0,0,0), 3, true));
		}
		{
			jLabel8 = new JLabel();
			JFprincipal.getContentPane().add(jLabel8, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			jLabel8.setText("Fórmula");
		}
		{
			jPanel = new JPanel();
			GridBagLayout jPanelLayout = new GridBagLayout();
			jPanelLayout.rowWeights = new double[] {0.1, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.1};
			jPanelLayout.rowHeights = new int[] {7, 0, 22, 10, 20, 28, 20, 7};
			jPanelLayout.columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.1};
			jPanelLayout.columnWidths = new int[] {119, 82, 153, 100, 154, 30, 7};
			jPanel.setLayout(jPanelLayout);
			JFprincipal.getContentPane().add(jPanel, new GridBagConstraints(1, 9, 8, 8, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
			jPanel.setBackground(new java.awt.Color(192,192,192));
			{
				lblNewLabel = new JLabel("GESTI\u00D3N DE DETALLE");
				lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
				GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
				gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
				gbc_lblNewLabel.gridx = 1;
				gbc_lblNewLabel.gridy = 1;
				jPanel.add(lblNewLabel, gbc_lblNewLabel);
			}
			{
				jLabel2 = new JLabel();
				jPanel.add(jLabel2, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
				jLabel2.setText("Producto");
			}
			{
				jLabel5 = new JLabel();
				jPanel.add(jLabel5, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
				jLabel5.setText("Kilogramos");
			}
			{
				jButtonAddDetail = new JButton("Registros");
				jPanel.add(jButtonAddDetail, new GridBagConstraints(4, 6, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
				jButtonAddDetail.setText("Añadir Detalle");
				jButtonAddDetail
						.setToolTipText("Añadir Nueva Entrada de Productos");
			}
			{
				jTextFieldKg = new JTextField();
				jPanel.add(jTextFieldKg, new GridBagConstraints(2, 5, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 5, 5), 0, 0));
				jTextFieldKg.setText("");
			}
			{
				jComboBoxProductosModel = new DefaultComboBoxModel();
				jComboBoxProductos = new JComboBox();
				jPanel.add(jComboBoxProductos, new GridBagConstraints(2, 3, 4, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 5, 5), 0, 0));
				jComboBoxProductos.setModel(jComboBoxProductosModel);
				jComboBoxProductos.setToolTipText("Tipo de producto");
			}
		}
		{
			ComboBoxModel jComboBoxFormulaModel = new DefaultComboBoxModel(
					new String[] { "Item One", "Item Two" });
			jComboBoxFormula = new JComboBox();
			JFprincipal.getContentPane().add(jComboBoxFormula, new GridBagConstraints(3, 2, 5, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
			jComboBoxFormula.setModel(jComboBoxFormulaModel);
			jComboBoxFormula.setVisible(false);
		}
		{
			jTextFieldFormula = new JTextField();
			JFprincipal.getContentPane().add(jTextFieldFormula, new GridBagConstraints(3, 2, 5, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
			//JFprincipal.getContentPane().add(jComboBoxFormula, new GridBagConstraints(3, 2, 5, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
			jTextFieldFormula.setText("");
		}
		{
			jPanel1 = new JPanel();
			JFprincipal.getContentPane().add(jPanel1, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		}
		{
			jButtonFormula = new JButton();
			JFprincipal.getContentPane().add(jButtonFormula, new GridBagConstraints(7, 6, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			jButtonFormula.setText(nueva);
		}
		{
			jButtonModifyFormula = new JButton();
			JFprincipal.getContentPane().add(jButtonModifyFormula, new GridBagConstraints(3, 6, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			jButtonModifyFormula.setText("Modificar Fórmula");
		}
		{
			jButtonCancelar = new JButton();
			JFprincipal.getContentPane().add(jButtonCancelar, new GridBagConstraints(3, 25, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			jButtonCancelar.setText("Cancelar");
			jButtonCancelar.setVisible(false);
			jButtonCancelar.setVerifyInputWhenFocusTarget(false);
		}
		{
			jButtonDeleteFormula = new JButton();
			JFprincipal.getContentPane().add(jButtonDeleteFormula, new GridBagConstraints(6, 6, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			jButtonDeleteFormula.setText("Eliminar Fórmula");
			//jButtonDeleteFormula.setEnabled(false);
		}
		{
			jButtonCancelarFormula = new JButton();
			JFprincipal.getContentPane().add(jButtonCancelarFormula, new GridBagConstraints(6, 6, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			jButtonCancelarFormula.setText("Cancelar");
			jButtonCancelarFormula.setVisible(false);
			jButtonCancelarFormula.setVisible(false);
		}
		{
			jLabel1 = new JLabel();
			JFprincipal.getContentPane().add(jLabel1, new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			jLabel1.setText("Tipo de Ganado");
		}
		{
			ComboBoxModel jComboBoxTipoModel = new DefaultComboBoxModel(
					new String[] { "Item One", "Item Two" });
			jComboBoxTipo = new JComboBox();
			JFprincipal.getContentPane().add(jComboBoxTipo, new GridBagConstraints(3, 4, 5, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
			jComboBoxTipo.setModel(jComboBoxTipoModel);
			jComboBoxTipo.setVisible(true);
		}
		{
			jButtonImprimir = new JButton();
			JFprincipal.getContentPane().add(jButtonImprimir, new GridBagConstraints(3, 25, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
			jButtonImprimir.setText("Imprimir");
			jButtonImprimir.setPreferredSize(new java.awt.Dimension(60, 21));
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
		JFprincipal.setTitle("Gestión para crear, modificar o eliminar nuevas fórmulas de pienso");
		
		try {
			cargarComboFormulas();
			cargarComboTipos();
			cargarComboProductos();
			//cargarListaDetalles();
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
		ListSelectionModel listMod =  jTableDetails.getSelectionModel();
		listMod.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listMod.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent evt) {
				jTableRegistrosValueChange(evt);
			}
		});
		
		//Cambia la lista de detalles al cambiar la fórmula seleccionada
		this.jComboBoxFormula.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				try {
					if(showMainMenu) {
						System.out.println("cargarComboFormulas		no estoy cargando...");
						if(cargandoFormulas) {
							cargarListaDetalles();
							cargarComboTipos();
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
		
		//Añade una nueva fórmula
		this.jButtonFormula.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButtonFormulaActionPerformed(evt);
			}
		});
		
		//Eliminar fórmula seleccionada
		this.jButtonDeleteFormula.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButtonDeleteFormulaActionPerformed(evt);
			}
		});
		
		//Modificar fórmula seleccionada
		this.jButtonModifyFormula.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButtonModifyFormulaActionPerformed(evt);
			}
		});
		
		//Cancelar la modificacion de una fórmula
		jButtonCancelarFormula.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButtonCancelarFormulaActionPerformed(evt);
			}
		});
		
		//Eliminar Detalle de la fórmula seleccionado en la lista
		jButtonEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButtonDeleteDetailActionPerformed(evt);
			}
		});
		
		//Modificar Detalle de la fórmula seleccionado en la lista
		jButtonModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButtonModifyDetailActionPerformed(evt);
			}
		});
		
		//Añadir Nuevo Detalle a la fórmula
		jButtonAddDetail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButtonAddDetailActionPerformed(evt);
			}
		});
		
		//Cancelar la modificacion de un detalle de la fórmula
		jButtonCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButtonCancelDetailActionPerformed(evt);
			}
		});
		
		// Imprimir entradas
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
	
	private void jButtonFormulaActionPerformed(ActionEvent evt){
		
		if (this.jButtonFormula.getText().equals(aceptar)) { //Estoy añadiendo una nueva fórmula
			if (this.jTextFieldFormula.getText().equals("")) {
				JOptionPane.showMessageDialog(Principal.msgbox,
					    "Debe introducir un nombre para la nueva fórmula que desea crear",
					    "Campo Incompleto",
					    JOptionPane.WARNING_MESSAGE);
			} else {
				if (this.jTextFieldFormula.getText() != ""){
					f.setNombre(this.jTextFieldFormula.getText());
					f.setTipoDeGanado(this.jComboBoxTipo.getSelectedItem().toString());
					
					try {
						fDal.insert(f);
						System.out.println("Insertar ");
						this.jButtonFormula.setText(nueva);
						this.jComboBoxTipo.setEnabled(false);
						this.jButtonModifyFormula.setVisible(true);
						this.jButtonDeleteFormula.setVisible(true);
						this.jButtonCancelarFormula.setVisible(false);
						cargarComboFormulas();
						System.out.println("cargarComboFormulas		jButtonFormulaActionPerformed");
						cargarComboTipos();
						//cargarListaDetalles();
					} catch (DalException e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(Principal.msgbox,
							    e.getMessage(),
							    "ERROR al crear la nueva fórmula",
							    JOptionPane.WARNING_MESSAGE);
					} catch (SQLException e) {
						JOptionPane.showMessageDialog(Principal.msgbox,
								e.getMessage(),
							    "ERROR al crear la nueva fórmula",
							    JOptionPane.WARNING_MESSAGE);
						e.printStackTrace();
					} 
				}
			}
		} else { //Estoy preparando el formulario para añadir una nueva fórmula
			//try {
				this.jComboBoxFormula.setVisible(false);
				this.jTextFieldFormula.setVisible(true);
				jTextFieldFormula.setText("");
				this.jComboBoxTipo.setEnabled(true);
				//TODO: setFocuns en jtextFieldFormula
				this.jButtonFormula.setText(aceptar);
				this.jButtonDeleteFormula.setVisible(false);
				this.jButtonModifyFormula.setVisible(false);
				this.jButtonCancelarFormula.setVisible(true);
				//cargarListaDetalles();
//			} catch (SQLException e) {
//			
//				e.printStackTrace();
//			} catch (DalException e) {
//	
//				e.printStackTrace();
//			}
		}
	}

	private void jButtonCancelarFormulaActionPerformed(ActionEvent evt) {

			if (jTextFieldFormula.getText().equals(aceptar)){
				this.jTextFieldFormula.setVisible(false);
				this.jComboBoxFormula.setVisible(true);
				this.jComboBoxTipo.setEnabled(true);
				this.jButtonDeleteFormula.setVisible(true);
				this.jButtonModifyFormula.setVisible(true);
			} else {
				this.jTextFieldFormula.setVisible(false);
				this.jComboBoxFormula.setVisible(true);
				this.jComboBoxTipo.setEnabled(false);
				this.jButtonModifyFormula.setVisible(true);
				this.jButtonModifyFormula.setText(modificar);
				this.jButtonDeleteFormula.setVisible(true);
				this.jButtonFormula.setVisible(true);
			}
			this.jButtonFormula.setText(nueva);
	}
	
	private void jButtonDeleteFormulaActionPerformed(ActionEvent evt) {
	
		try {
			int n = JOptionPane.showConfirmDialog(Principal.msgbox,
				    "¿Desee eliminar la fórmula del sistema?",
				    "Confirmar",
				    JOptionPane.YES_NO_OPTION);
			if (n==JOptionPane.YES_OPTION) {
				fEliminar.setFormulaID(obtenerID());
				fDal.delete(fEliminar);
				cargarComboFormulas();
				cargarComboTipos();
				//cargarListaDetalles();
			}
		} catch (DalException e) {
			JOptionPane.showMessageDialog(Principal.msgbox,
					e.getMessage(),
				    "ERROR al borrar la fórmula",
				    JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(Principal.msgbox,
					e.getMessage(),
				    "ERROR al borrar la fórmula",
				    JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		}
	}
	
	private void jButtonModifyFormulaActionPerformed(ActionEvent evt) {

		if (this.jButtonModifyFormula.getText().equals(aceptar)) { //Estoy añadiendo una nueva fórmula
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
						f.setTipoDeGanado(this.jComboBoxTipo.getSelectedItem().toString());
						
						try {
							fDal.modify(f);
							this.jButtonModifyFormula.setText(modificar);
							this.jButtonFormula.setVisible(true);
							this.jButtonDeleteFormula.setVisible(true);
							this.jButtonCancelarFormula.setVisible(false);
							System.out.println("\n cargarComboFormulas		jButtonModifyFormulaActionPerformed");
							cargarComboFormulas();
							System.out.println(" cargarComboTipos		jButtonModifyFormulaActionPerformed");
							//cargarComboTipos();
						} catch (DalException e) {
							e.printStackTrace();
							JOptionPane.showMessageDialog(Principal.msgbox,
								    e.getMessage(),
								    "ERROR al modificar la fórmula",
								    JOptionPane.WARNING_MESSAGE);
						} catch (SQLException e) {
							JOptionPane.showMessageDialog(Principal.msgbox,
									e.getMessage(),
								    "ERROR al modificar la fórmula",
								    JOptionPane.WARNING_MESSAGE);
							e.printStackTrace();
						} 
					}
				}
			}
		} else { //Estoy preparando el formulario para modificar la fórmula seleccionada
			String aux = jComboBoxFormula.getSelectedItem().toString();
			this.jComboBoxFormula.setVisible(false);
			this.jTextFieldFormula.setVisible(true);
			jTextFieldFormula.setText(aux);
			jComboBoxTipo.setEnabled(true);
			//TODO: setFocus en jtextFieldFormula
			this.jButtonModifyFormula.setText(aceptar);
			this.jButtonDeleteFormula.setVisible(false);
			this.jButtonFormula.setVisible(false);
			this.jButtonCancelarFormula.setVisible(true);
		}
	}	
	
	private void jButtonAddDetailActionPerformed(ActionEvent evt) {
		
		if (jTextFieldKg.getText().equals("")) {
			JOptionPane.showMessageDialog(Principal.msgbox,
				    "Debe introducir una cantidad (KG) de entrada",
				    "Campo Incompleto",
				    JOptionPane.WARNING_MESSAGE);
		} else {
	
			if (this.jComboBoxProductos.getSelectedItem().toString() != ""){
		
				try {
					f.setFormulaID(obtenerID());
					det.setFormulaID(f.getFormulaID());
					det.setNombre(this.jComboBoxProductos.getSelectedItem().toString());
					det.setKg(Float.valueOf(jTextFieldKg.getText()).floatValue());
					
					dDal.insert(det);
					cargarListaDetalles();
					jTextFieldKg.setText("");
					System.out.println("Producto para la fórmula añadido: " + det.getNombre() + " - " + det.getKg() + " - " + det.getFormulaID());
				} catch (DalException e) {
					e.printStackTrace();
					System.out.println("ERROR al añadir el producto: " + det.getNombre() + " - " + det.getKg() + " - "+ det.getFormulaID());
				} catch (SQLException e) {
					e.printStackTrace();
					System.out.println("ERROR al añadir el producto: " + det.getNombre() + " - " + det.getKg() + " - "+ det.getFormulaID());
				}	
			}
		}
	}
	
	private void jButtonDeleteDetailActionPerformed(ActionEvent evt) {

		try {
			detEliminar.setDetailFormulaID( (int)Integer.valueOf((String) jTableDetailsModel.getValueAt(jTableDetails.getSelectedRow(), 0)));
			dDal.delete(detEliminar);
			cargarListaDetalles();
		} catch (DalException e) {
			
			e.printStackTrace();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	
	private void jButtonCancelDetailActionPerformed(ActionEvent evt) {

		try {
			this.jButtonAddDetail.setEnabled(true);
			this.jButtonEliminar.setEnabled(false);
			this.jButtonModificar.setEnabled(false);
			this.jButtonCancelar.setVisible(false);
			this.jButtonImprimir.setVisible(true);
			cargarListaDetalles();
		} catch (SQLException e) {
			
			e.printStackTrace();
		} catch (DalException e) {
			
			e.printStackTrace();
		}
	}
	
	private void jButtonModifyDetailActionPerformed(ActionEvent evt) {

		try {
			detEliminar.setDetailFormulaID( (int)Integer.valueOf((String) jTableDetailsModel.getValueAt(jTableDetails.getSelectedRow(), 0)));
			f.setFormulaID(obtenerID());
			detEliminar.setFormulaID(f.getFormulaID());
			detEliminar.setNombre(this.jComboBoxProductos.getSelectedItem().toString());
			detEliminar.setKg(Float.valueOf(jTextFieldKg.getText()).floatValue());
			dDal.modify(detEliminar);
			cargarListaDetalles();
		} catch (DalException e) {
			
			e.printStackTrace();
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
	}

	public void jTableRegistrosValueChange(ListSelectionEvent e) {
		detEliminar = new DetailFormulaCreada();
		
		if (e.getValueIsAdjusting() == false) {
	        if (jTableDetails.getSelectedRow() == -1) {
	        //No selection, deshabilito botón "Eliminar"
	            jButtonEliminar.setEnabled(false);
	            jButtonModificar.setEnabled(false);
	            jButtonCancelar.setVisible(false);
	            jButtonAddDetail.setEnabled(true);
	            jButtonImprimir.setVisible(true);
	        
	        } else {
		        //Selection, Habilito botón "Eliminar"/"Modificar" y creo el producto en la tabla
	        	jButtonEliminar.setEnabled(true);
	        	jButtonModificar.setEnabled(true);
	        	jButtonCancelar.setVisible(true);
	        	jButtonAddDetail.setEnabled(false);
	        	jButtonImprimir.setVisible(false);
	        	
	        	int rowSelected = jTableDetails.getSelectedRow();
	        	detEliminar.setDetailFormulaID(Integer.parseInt(jTableDetails.getValueAt(rowSelected,0).toString()));
	        	detEliminar.setFormulaID(Integer.parseInt(jTableDetails.getValueAt(rowSelected,1).toString()));
	        	asignarComboBox(jTableDetails.getValueAt(rowSelected,2),this.jComboBoxProductos);
				jTextFieldKg.setText(jTableDetails.getValueAt(rowSelected,3).toString());
			}
	    }
	}
	
	private void jButtonImprimirActionPerformed(ActionEvent evt) throws SQLException, DalException{
		try
	    {
	        // Llenamos los parámetros
			parameters.put("nombre", this.jComboBoxFormula.getSelectedItem().toString());
	    	parameters.put("tipo_de_ganado", this.jComboBoxTipo.getSelectedItem().toString());
	    	System.out.println(this.jComboBoxFormula.getSelectedItem().toString()+" "+this.jComboBoxTipo.getSelectedItem().toString());
	    	// Llenamos los detalles uno a uno
	    	Vector<String> tableColumns = new Vector<String>();
            tableColumns.add("nombre");
            tableColumns.add("kg");
            //tableColumns.add("Fecha alta");
            //tableColumns.add("Provincia");
            //tableColumns.add("Municipio");
            //tableColumns.add("NumExpIns");
      
    		modelo = new DefaultTableModel(tableColumns,jTableDetailsModel.getRowCount());
    		for(int i = 0; i < jTableDetailsModel.getRowCount() ;i++){
            	modelo.setValueAt(String.valueOf(jTableDetailsModel.getValueAt(i, 2)) , i, 0);
                modelo.setValueAt(String.valueOf(jTableDetailsModel.getValueAt(i, 3)) , i, 1);
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

	
	private void cargarComboProductos() throws SQLException, DalException{
		ResultSet rs;
		
		jComboBoxProductos.removeAllItems();
		
		try {
			rs=(ResultSet) getFacade().executeQuery("SELECT * FROM Producto ORDER BY producto");
		} catch (SQLException e) {
			System.out.println("ERROR cargando ComboBox Productos");
			throw new DalException(e);
		} 
		
		while (rs.next()){
			jComboBoxProductos.addItem(rs.getString("producto"));	
		}	
	}
	
	private void cargarComboFormulas() throws SQLException, DalException{
		ResultSet rs;
		
		jTextFieldFormula.setVisible(false);
		jComboBoxFormula.setVisible(true);
		cargandoFormulas = false;
		jComboBoxFormula.removeAllItems();
		
		try {
			rs=(ResultSet) getFacade().executeQuery("SELECT * FROM formula_creada ORDER BY nombre desc");
		} catch (SQLException e) {
			System.out.println("ERROR cargando ComboBox Formulas");
			throw new DalException(e);
		} 
		System.out.println("cargarComboFormulas	-> " + showMainMenu);
		while (rs.next()){
			jComboBoxFormula.addItem(rs.getString("nombre"));
		}
		cargandoFormulas = true;
		cargarComboTipos();
		cargarListaDetalles();
	}
	
	private void cargarComboTipos() throws SQLException, DalException{
		ResultSet rs;
		
		jComboBoxTipo.setEnabled(true);
		jComboBoxTipo.removeAllItems();
		
		try {
			rs=(ResultSet) getFacade().executeQuery("SELECT * FROM tipo_de_ganado ORDER BY tipo");
		} catch (SQLException e) {
			System.out.println("ERROR cargando ComboBox Tipo_de_ganado");
			throw new DalException(e);
		} 
		
		while (rs.next()){
			jComboBoxTipo.addItem(rs.getString("tipo"));
		}
		
		try {
			rs=(ResultSet) getFacade().executeQuery("SELECT * FROM formula_creada "+
														"WHERE nombre='"+ jComboBoxFormula.getSelectedItem().toString()+"'");
		} catch (SQLException e) {
			System.out.println("ERROR cargando ComboBox Formulas");
			throw new DalException(e);
		}
		
		//Selecciono el tipo de la formula seleccionada
		while (rs.next()){
			System.out.println("cargarComboTipos		"+rs.getString("tipo_de_ganado"));
			for (int i=0;i<jComboBoxTipo.getItemCount();i++){
				if (jComboBoxTipo.getItemAt(i).toString().equals(rs.getString("tipo_de_ganado"))){
					jComboBoxTipo.setSelectedIndex(i);
				}
			}
		}
		
		jComboBoxTipo.setEnabled(false);
		
	}
	
	private void cargarListaDetalles() throws SQLException, DalException{
		ResultSet rs;
		
		Object[][] data = null;
		Object[] headers = new Object[] {"ID", "IDFORMULA", "PRODUCTO", "Nº KG"};

		jTableDetailsModel = null;
		jTableDetailsModel = new DefaultTableModel(data,headers);

		try {
			int id = obtenerID();
			f.setFormulaID(id);
			System.out.println("SELECT * FROM formula_det_creada " +
														"WHERE idformula = " + id +
														" ORDER BY id");
			rs=(ResultSet) getFacade().executeQuery("SELECT * FROM formula_det_creada " +
														"WHERE idformula = " + id +
														" ORDER BY id");
		} catch (SQLException e) {
			throw new DalException(e);
		} 
		while (rs.next()){
			Object [] row = new Object[]{String.valueOf(rs.getInt("id")),
										String.valueOf(rs.getInt("idformula")),
										rs.getString("nombre"), 
										String.valueOf(rs.getFloat("kg"))};
			jTableDetailsModel.addRow(row);
		}
		jTableDetails.setModel(jTableDetailsModel);
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

	private int obtenerID() throws SQLException, DalException{
		int i=-1;
		ResultSet rs=null;
		if (jComboBoxFormula.getSelectedItem() != null){
						
			try {
				rs=(ResultSet) getFacade().executeQuery("SELECT ID FROM formula_creada "+
															"WHERE Nombre = '" + jComboBoxFormula.getSelectedItem().toString() + "' "+
															"AND tipo_de_ganado = '" + jComboBoxTipo.getSelectedItem().toString() + "'");
			} catch (SQLException e) {
			//	System.out.println("obtenerID " + jComboBoxFormula.getSelectedItem().toString() + " = " + rs.getInt("id"));
				throw new DalException(e);
			}
			
			while (rs.next()){
				System.out.println("INTENTO obtenerID " + i);
				i=rs.getInt("id");	
			}
		}
		return i;
	}	

}

