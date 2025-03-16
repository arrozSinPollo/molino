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

import mysqldal.MySQLDetailDal;
import mysqldal.MySQLFormulaDal;
import mysqldal.MySQLJDBCFacade;
import mysqldal.MySQLRegistroDeRecepcionDal;
import presentation.PresentationApp;
import utils.MyTableModel;

import com.toedter.calendar.JDateChooser;

import dal.DalException;
import dal.Transactionable;
import domain.DetailFormula;
import domain.Formula;
import domain.RegistroDeRecepcion;

public class AplicarFormula extends Transactionable implements PresentationApp {


	private JButton jButtonApplyFormula;
	private JButton jButtonEliminar;
	private JButton jButtonModificar;
	private JButton jButtonCancelar;
	private JDateChooser jDataChooserFecha;
	private JLabel jLabel3;
	private JTable jTableDetails;
	private JScrollPane jTableScroller;
	private DefaultTableModel jTableDetailsModel;
	private JLabel jLabelTitulo;
	private MySQLFormulaDal fDal;
	private JLabel jLabel4;
	private JLabel jLabel8;
	private JComboBox jComboBoxFormula;
	private JPanel jPanel1;
	private Formula f, fModificar;
	private JTextField jTextFieldPrecioTotal;
	private JCheckBox jCheckBoxGranel;
	private JLabel jLabel1;
	private DetailFormula det, detEliminar;
	private RegistroDeRecepcion reg;
	private MySQLRegistroDeRecepcionDal regDal;
	private MySQLDetailDal dDal;
	private JTextField jTextFieldSituacion;
	private JLabel jLabelSituacion;
	private JCheckBox jCheckBoxEnvasado;

	//Constantes para el texto de jButtonFormula
	private String modificar = "Modificar Fórmula";
	private float precioTotal = 0;
	
	//Variables locales
	private String tipoDeGanado = "";
	private String nombre = "";
	


	public AplicarFormula() {
		super(new MySQLJDBCFacade());
		f = new Formula();
		fDal = new MySQLFormulaDal();
		det = new DetailFormula();
		dDal = new MySQLDetailDal();
		reg = new RegistroDeRecepcion();
		regDal = new MySQLRegistroDeRecepcionDal();
	}

	public void showMainMenu() {
		JFrame JFprincipal = new JFrame("Molino v2.0");
		GridBagConstraints constraints = new GridBagConstraints();
		
		//Indico el layout de JFprincipal
		GridBagLayout JFprincipalLayout = new GridBagLayout();
		JFprincipal.getContentPane().setLayout(JFprincipalLayout);
		JFprincipalLayout.rowHeights = new int[] {51, 21, 20, 12, 20, 12, 20, 12, 20, 12, 21, 12, 21, 12, 21, 20, 20, 7, 7, 7, 7, 20, 7, 7, 7, 7};
		JFprincipalLayout.rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.1, 0.1, 0.1, 0.1, 0.1, 0.0, 0.1, 0.1, 0.0, 0.1};
		JFprincipalLayout.columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.1, 0.0, 0.0, 0.0, 0.0, 0.1};
		JFprincipalLayout.columnWidths = new int[] {35, 91, 112, 88, 20, -3, 140, 129, 95, 7};
		
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
		jLabelTitulo.setText("Creación de Fórmulas");
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
		JFprincipal.getContentPane().add(jButtonEliminar, new GridBagConstraints(7, 21, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		jButtonEliminar.setText("Eliminar Detalle");
		jButtonEliminar.setToolTipText("Eliminar Detalle de Entrada Seleccionado");
		jButtonEliminar.setEnabled(false);
		jButtonEliminar.setVisible(false);

		jButtonModificar = new JButton ("Eliminar");
		constraints.gridx = 1;
		constraints.gridy = 3;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		JFprincipal.getContentPane().add(jButtonModificar, new GridBagConstraints(6, 12, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		jButtonModificar.setText("Modificar Detalle");
		jButtonModificar.setToolTipText("Modificar Detalle Seleccionado");
		jButtonModificar.setEnabled(false);
		jButtonModificar.setVisible(false);

		{
			jLabel3 = new JLabel();
			JFprincipal.getContentPane().add(jLabel3, new GridBagConstraints(2, 14, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
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
			JFprincipal.getContentPane().add(jTableScroller, new GridBagConstraints(1, 15, 8, 5, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
			jTableScroller.setPreferredSize(new Dimension(250, 472));
			jTableScroller.setBorder(new LineBorder(new java.awt.Color(0,0,0), 3, true));
		}
		{
			jLabel4 = new JLabel();
			JFprincipal.getContentPane().add(jLabel4, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			jLabel4.setText("Fecha de Creación");
		}
		{
			jDataChooserFecha = new JDateChooser();
			JFprincipal.getContentPane().add(jDataChooserFecha, new GridBagConstraints(3, 2, 2, 1, 0.0, 0.0, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));			
		}
		{
			jLabel8 = new JLabel();
			JFprincipal.getContentPane().add(jLabel8, new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			jLabel8.setText("Fórmula");
		}
		{
			ComboBoxModel jComboBoxFormulaModel = new DefaultComboBoxModel(
					new String[] { "Item One", "Item Two" });
			jComboBoxFormula = new JComboBox();
			JFprincipal.getContentPane().add(jComboBoxFormula, new GridBagConstraints(3, 4, 5, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
			jComboBoxFormula.setModel(jComboBoxFormulaModel);
			jComboBoxFormula.setVisible(false);
		}
		{
			jPanel1 = new JPanel();
			JFprincipal.getContentPane().add(jPanel1, new GridBagConstraints(1, 6, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		}
		{
			jButtonApplyFormula = new JButton();
			JFprincipal.getContentPane().add(jButtonApplyFormula, new GridBagConstraints(7, 12, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			jButtonApplyFormula.setText("Aplicar Fórmula");
		}
		{
			jButtonCancelar = new JButton();
			JFprincipal.getContentPane().add(jButtonCancelar, new GridBagConstraints(3, 12, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			jButtonCancelar.setText("Cancelar");
			jButtonCancelar.setVisible(false);
			jButtonCancelar.setVisible(false);
		}
		{
			jCheckBoxEnvasado = new JCheckBox();
			JFprincipal.getContentPane().add(jCheckBoxEnvasado, new GridBagConstraints(2, 8, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			jCheckBoxEnvasado.setText("Envasado");
		}
		{
			jLabelSituacion = new JLabel();
			JFprincipal.getContentPane().add(jLabelSituacion, new GridBagConstraints(2, 6, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			jLabelSituacion.setText("Situación");
		}
		{
			jTextFieldSituacion = new JTextField();
			JFprincipal.getContentPane().add(jTextFieldSituacion, new GridBagConstraints(3, 6, 5, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		}
		{
			jLabel1 = new JLabel();
			JFprincipal.getContentPane().add(jLabel1, new GridBagConstraints(7, 21, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
			jLabel1.setText("Precio Total");
			jLabel1.setBackground(new java.awt.Color(192,192,192));
		}
		{
			jTextFieldPrecioTotal = new JTextField();
			JFprincipal.getContentPane().add(jTextFieldPrecioTotal, new GridBagConstraints(8, 21, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		}
		{
			jCheckBoxGranel = new JCheckBox();
			JFprincipal.getContentPane().add(jCheckBoxGranel, new GridBagConstraints(3, 8, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			jCheckBoxGranel.setText("Granel");
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
		JFprincipal.setTitle("Aplicación de fórmulas diseñadas para crear pienso");
		
		try {
			cargarComboFormulas();
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
				
				//EN PRINCIPIO NO SE PODRÁ SELECCIONAR NINGÚNA LÍNEA DE DETALLE,
				//SÓLO SE PORDRÁ SELECCIONAR LAS FÓRMULAS (COMBOBOX)
				//jTableRegistrosValueChange(evt);
			}
		});
		
		//Añade una nueva fórmula
		this.jButtonApplyFormula.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButtonApplyFormulaActionPerformed(evt);
			}
		});
		
		//Cancelar la modificacion de una fórmula
		jButtonCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButtonCancelarActionPerformed(evt);
			}
		});
		
		//Eliminar Detalle de la fórmula seleccionado en la lista
		jButtonEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButtonDeleteActionPerformed(evt);
			}
		});
		
		//Modificar la fórmula seleccionada
		jButtonModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButtonModificarActionPerformed(evt);
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
	
	private void jButtonApplyFormulaActionPerformed(ActionEvent evt){
			
		if ((this.jDataChooserFecha.getDate() == null)){
			JOptionPane.showMessageDialog(Principal.msgbox,
				    "Debe introducir la FECHA para poder aplicar la fórmula",
				    "Campo Incompleto",
				    JOptionPane.WARNING_MESSAGE);
		} else {
			int n = JOptionPane.showConfirmDialog(Principal.msgbox,
					"¿Está seguro de que desea aplicar la fórmula '" + jComboBoxFormula.getSelectedItem().toString() + "'?\n\n" +
				    "Recuerde que los cambios afectarán a los datos de almacén.\nSi desea continuar los cambios no se podrán restaurar.",
				    "Confirmar",
				    JOptionPane.YES_NO_OPTION);
			
			if (n==JOptionPane.YES_OPTION) {

				try {
					cargarListaDetalles();
					System.out.println("Fórmula aplicar: " + f.getNombre());
				} catch (DalException e) {
					e.printStackTrace();
					System.out.println("ERROR al aplicar la fórmula: " + f.getNombre() );
				} catch (SQLException e) {
					e.printStackTrace();
					System.out.println("ERROR al aplicar la fórmula: " + f.getNombre() );
				} 
			}
		}
	}

	private void jButtonCancelarActionPerformed(ActionEvent evt) {
	
		this.jButtonModificar.setText(modificar);
		jButtonCancelar.setVisible(false);
	}
	
	private void jButtonDeleteActionPerformed(ActionEvent evt) {

		/*try {
			dDal.delete(detEliminar);
			cargarListaDetalles();
		} catch (DalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	
	private void jButtonModificarActionPerformed(ActionEvent evt) {

		int n = JOptionPane.showConfirmDialog(Principal.msgbox,
				"¿Está seguro de que desea modificar los datos de la fórmula? '" + jComboBoxFormula.getSelectedItem().toString() + "'?\n\n" +
			    "Recuerde que los cambios afectarán sólo a los datos de la cabecera,\nNO a los datos de almacén.",
			    "Confirmar",
			    JOptionPane.YES_NO_OPTION);
		
		if (n==JOptionPane.YES_OPTION) {
			try {
				fModificar.setFormulaID(obtenerID());
				fDal.modify(fModificar);
				//cargarListaDetalles();
			} catch (DalException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void jTableRegistrosValueChange(ListSelectionEvent e) {
		detEliminar = new DetailFormula();
		
		if (e.getValueIsAdjusting() == false) {
	        if (jTableDetails.getSelectedRow() == -1) {
	        //No selection, deshabilito botón "Eliminar"
	            jButtonEliminar.setEnabled(false);
	            jButtonModificar.setEnabled(false);
	        
	        } else {
		        //Selection, Habilito botón "Eliminar"/"Modificar" y creo el producto en la tabla
	        	jButtonEliminar.setEnabled(true);
	        	jButtonModificar.setEnabled(true);
	        	
	        	int rowSelected = jTableDetails.getSelectedRow();
	        	detEliminar.setDetailFormulaID(Integer.parseInt(jTableDetails.getValueAt(rowSelected,0).toString()));
	        	detEliminar.setFormulaID(Integer.parseInt(jTableDetails.getValueAt(rowSelected,1).toString()));
	        	asignarComboBox(jTableDetails.getValueAt(rowSelected,2),this.jComboBoxFormula);
			}
	    }
	}
	
	private void cargarComboFormulas() throws SQLException, DalException{
		ResultSet rs;
		
		jComboBoxFormula.setVisible(true);
		jComboBoxFormula.removeAllItems();
		
		try {
			rs=(ResultSet) getFacade().executeQuery("SELECT * FROM formula_creada ORDER BY nombre desc");
		} catch (SQLException e) {
			System.out.println("ERROR cargando ComboBox Formulas");
			throw new DalException(e);
		} //finally {
			//getFacade().endCurrentOperation();
		//}
		while (rs.next()){
			jComboBoxFormula.addItem(rs.getString("nombre") + " - " + rs.getString("tipo_de_ganado"));
		}
		
	}
	
	private void cargarListaDetalles() throws SQLException, DalException{
		int i;
		boolean ok=false, aplicar=false;
		ResultSet rs, rsAux, rsID, rsSUM;
		float cantidad = 0;
		precioTotal = 0;
		
		Object[][] data = null;
		Object[] headers = new Object[] {"ID", "IDFORMULA", "PRODUCTO", "FECHA ENTRADA", "LOTE", "FECHA CADUC.", "Nº KG", "PRECIO (€/KG)"};

		jTableDetailsModel = null;
		jTableDetailsModel = new DefaultTableModel(data,headers);

		try {
			
			nombre = jComboBoxFormula.getSelectedItem().toString().split(" - ")[0];
			tipoDeGanado = jComboBoxFormula.getSelectedItem().toString().split(" - ")[1];
			
			
			rsID=(ResultSet) getFacade().executeQuery("SELECT * FROM formula_creada " +
														"WHERE nombre = '" + nombre + "' "+
															"AND tipo_de_ganado = '" + tipoDeGanado + "'");
			
			//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			//
			// INSERTO LA FORMULA A APLICAR
			// 
			/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			if (rsID.next()){
				rs=(ResultSet) getFacade().executeQuery("SELECT * FROM formula_det_creada " +
												"WHERE idformula = " + rsID.getInt("id"));
				
				aplicar = true;
				
				/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				//
				// COMPRUEBO QUE EXISTE MATERIAL SUFICIENTE COMO PARA APLICAR LA FORMULA
				//
				/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				
				java.text.DateFormat sdfAux = new java.text.SimpleDateFormat("yyyy/MM/dd");
				String fechaCreacion =  sdfAux.format(jDataChooserFecha.getDate());
				
				
				// Recorremos los detalles de la fórmula
				while (rs.next()){	
					rsSUM = (ResultSet) getFacade().executeQuery("SELECT enAlmacen FROM registrosderecepcion " +
																	"WHERE producto = '" + rs.getString("nombre") + "' " +
																		"AND enAlmacen > 0 AND fecha <= '" + fechaCreacion + "'");
					ok=true;
					cantidad = 0;
					while (rsSUM.next() && ok){
						cantidad = cantidad + rsSUM.getFloat("enAlmacen");
					}
					
					if (cantidad < rs.getFloat("kg")) {
						ok = false;
						aplicar = false;
						JOptionPane.showMessageDialog(Principal.msgbox,
							    "No se puede aplicar la fórmula porque no hay la cantidad suficiente\nde " + rs.getString("nombre").toUpperCase() 
							    	+ " en el almacén (faltan " + (rs.getFloat("kg") - cantidad) + " kg)",
							    "Falta material",
							    JOptionPane.WARNING_MESSAGE);
						System.out.println("\nNO HAY CANTIDAD SUFICIENTE DE: " + rs.getString("nombre") + "   " + cantidad + 
												" < " + rs.getFloat("kg"));
					} else {
						System.out.println("\nCANTIDAD DE: " + rs.getString("nombre") + "   " + cantidad + 
												" >= " + rs.getFloat("kg"));
					}
				}
				
				/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				//
				// SI EXISTE EL MATERIAL, INSERTO LA FÓRMULA, LOS DETALLES (LOTES) Y ACTUALIZO LA CANTIDAD EN EL ALMACÉN
				//
				/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				if (aplicar) {
					
					f.setNombre(nombre);
					f.setTipoDeGanado(tipoDeGanado);
					f.setEstado(getEstado());
					f.setSituacion(jTextFieldSituacion.getText());
					f.setFecha(jDataChooserFecha.getDate());
					fDal.insert(f);
					int id = obtenerID();
					
					rs=(ResultSet) getFacade().executeQuery("SELECT * FROM formula_det_creada " +
							"WHERE idformula = " + rsID.getInt("id"));
					while (rs.next()){
						i=0;
						cantidad = rs.getFloat("kg");
						
						rsAux=(ResultSet) getFacade().executeQuery("SELECT * FROM registrosderecepcion " +
																	"WHERE producto = '" + rs.getString("nombre") + "' " +
																		"AND enAlmacen > 0 " +  
																			"ORDER BY Fecha ASC");
						while (rsAux.next()){	
							
							reg.setRegistroID(rsAux.getInt("id"));
							reg.setFecha(rsAux.getDate("fecha"));
							try {
								reg.setFechaCaducidad(rsAux.getDate("fechaCaducidad"));
							} catch (Exception e) {
								reg.setFechaCaducidad(null);
							}
							reg.getProducto().setName(rsAux.getString("producto"));
							reg.getProveedor().setName(rsAux.getString("proveedor"));
							reg.setAlbaran(rsAux.getString("albaran"));
							reg.setKg(rsAux.getFloat("kg"));
							reg.setLote(rsAux.getString("lote"));
							reg.setConformidad(rsAux.getBoolean("conformidad"));
							reg.setObservaciones(rsAux.getString("observaciones"));
							reg.getUbicacion().setName(rsAux.getString("ubicacion"));
							reg.setEnAlmacen(rsAux.getFloat("enAlmacen"));
							reg.setPrecio(rsAux.getFloat("precio"));
							
							if (reg.getEnAlmacen()>=cantidad){
								
								//Sólo actualizo un registro de entrada
								reg.setEnAlmacen(reg.getEnAlmacen() - cantidad);
								try {
									regDal.modifySinFechaCaducidad(reg);
								} catch (Exception e) {
									
								}
								
								rsAux.last();
								
								//Insertar Detail into Formula
								det.setFormulaID(id);
								det.setKg(cantidad);
								det.setFecha(reg.getFechaDate());
								det.setLote(reg.getLote());
								det.setFechaCaducidad(reg.getFechaCaducidadDate());
								det.setNombre(reg.getProducto().getName());
								det.setPrecio(reg.getPrecio());
								dDal.insert(det);
								
					
								Object [] row = new Object[]{String.valueOf(obtenerIDFormula()),
										String.valueOf(det.getFormulaID()),
										det.getNombre(), 
										det.getFecha(),
										det.getLote(),
										det.getFechaCaducidad(),
										String.valueOf(det.getKg()),
										String.valueOf(det.getKg() * det.getPrecio()) + " (" + String.valueOf(det.getPrecio()) + ")"};
			
								jTableDetailsModel.addRow(row);
								precioTotal = (det.getKg() * det.getPrecio()) + precioTotal;
								
							} else {
								
								//Necesito AL MENOS un registro de entrada más para completar la fórmula
								det.setFormulaID(id);
								det.setKg(reg.getEnAlmacen());
								det.setFecha(reg.getFechaDate());
								det.setLote(reg.getLote());
								det.setFechaCaducidad(reg.getFechaCaducidadDate());
								det.setNombre(reg.getProducto().getName());
								det.setPrecio(reg.getPrecio());
								dDal.insert(det);
								
								cantidad = cantidad - reg.getEnAlmacen();
								reg.setEnAlmacen(0);
								regDal.modifySinFechaCaducidad(reg);
								Object [] row = new Object[]{String.valueOf(obtenerIDFormula()),
										String.valueOf(det.getFormulaID()),
										det.getNombre(), 
										det.getFecha(),
										det.getLote(),
										det.getFechaCaducidad(),
										String.valueOf(det.getKg()),
										String.valueOf(det.getKg() * det.getPrecio()) + " (" + String.valueOf(det.getPrecio()) + ")"};
			
								jTableDetailsModel.addRow(row);
								precioTotal = (det.getKg() * det.getPrecio()) + precioTotal;
							}
							System.out.println("Producto: " + reg.getProducto().getName() + "  Cantidad: " + cantidad + "  i: " + i);
							i++;
						}//Fin de busqueda en los registros de entrada
						i++;
						
					}//Fin de los detalles:  while (rs.next())
					
					jTextFieldPrecioTotal.setText(String.valueOf(precioTotal));
					
					//Inserto la fórmula con su precio total
					f.setFormulaID(id);
					f.setPrecio(Float.valueOf(jTextFieldPrecioTotal.getText()));
					fDal.modify(f);
				} else {
					/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
					//
					// NO EXISTE EL MATERIAL -> BORRO LA FORMULA INSERTADA
					//
					/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
					
					System.out.println("***********************************************");
					System.out.println("No puedo crear la formula por falta de material");
					System.out.println("***********************************************");
					//f.setFormulaID(id);
					//fDal.delete(f);
					jTableDetailsModel = null;
					jTableDetailsModel = new DefaultTableModel(data,headers);
				}
				jTextFieldPrecioTotal.setText(String.valueOf(precioTotal));
				jTableDetails.setModel(jTableDetailsModel);
				
			} else {
				JOptionPane.showMessageDialog(Principal.msgbox,
					    "No existe ninguna fórmula con nombre " + jComboBoxFormula.getSelectedItem().toString().toUpperCase() + " creada",
					    "ERROR al aplicar",
					    JOptionPane.WARNING_MESSAGE);
			}
		} catch (SQLException e) {
			System.out.println("ERROR: aplicando formula");
			throw new DalException(e);
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

	private int obtenerID() throws SQLException, DalException{
		int i=-1;
		ResultSet rs=null;
		if (jComboBoxFormula.getSelectedItem() != null){
						
			try {
				System.out.println("\nBUSCO EL ID DE LA FORMULA QUE ACABO DE INSERTAR\nSELECT ID FROM formula "+
						"WHERE Nombre = '" +  f.getNombre() + "' " +
						" AND tipo_de_ganado = '" +  f.getTipoDeGanado() + "' " +
						" AND Fecha = '" + f.getFecha() + "' " +
							"ORDER BY ID ASC");
				rs=(ResultSet) getFacade().executeQuery("SELECT ID FROM formula "+
															"WHERE Nombre = '" + f.getNombre() + "' " +
																" AND tipo_de_ganado = '" +  f.getTipoDeGanado() + "' " +
																" AND Fecha = '" + f.getFecha() + "' " +
																	"ORDER BY ID ASC");
			} catch (SQLException e) {
	
				throw new DalException(e);
			}
			
			while (rs.next()){
				i=rs.getInt("id");	
			}
		}
		return i;
	}
	
	private int obtenerIDFormula() throws SQLException, DalException{
		int i=-1;
		ResultSet rs=null;
		if (jComboBoxFormula.getSelectedItem() != null){
						
			try {
				System.out.println("\nBUSCO EL ID DEL DETALLE QUE ACABO DE INSERTAR\nSELECT ID FROM formula_DET "+
						"WHERE Nombre = '" + det.getNombre() + "' " +
						" AND idformula = " + det.getFormulaID() +
							" ORDER BY ID ASC");
				rs=(ResultSet) getFacade().executeQuery("SELECT ID FROM formula_det "+
															"WHERE Nombre = '" + det.getNombre() + "' " +
																" AND idformula = " + det.getFormulaID() +
																	" ORDER BY ID ASC");
			} catch (SQLException e) {
	
				throw new DalException(e);
			}
			
			while (rs.next()){
				i=rs.getInt("id");	
			}
		}
		return i;
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
}

