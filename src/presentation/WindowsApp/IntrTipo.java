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

import mysqldal.MySQLJDBCFacade;
import mysqldal.MySQLTipoDal;
import presentation.PresentationApp;
import dal.DalException;
import dal.Transactionable;
import domain.Tipo;


public class IntrTipo extends Transactionable implements PresentationApp {

	private JButton jButtonTipos;
	private JButton jButtonEliminar;
	private JLabel jLabel1;
	private JLabel jLabel3;
	private JTextField jTextFieldNombre;
	private JList jListTipos;
	private JScrollPane jListScroller;
	private DefaultListModel jListTiposModel;
	private JLabel jLabelTitulo;
	private MySQLTipoDal tDal;
	private Tipo t;
	private Tipo tEliminar;

	public IntrTipo() {
		super(new MySQLJDBCFacade());
		t = new Tipo();
		tDal = new MySQLTipoDal();
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
		jLabelTitulo.setText("Gestión de Categorías");
		jLabelTitulo.setForeground(new java.awt.Color(0,0,160));
		jLabelTitulo.setBorder(new LineBorder(new java.awt.Color(0,0,0), 3, false));
		jLabelTitulo.setHorizontalTextPosition(SwingConstants.CENTER);
		jLabelTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		constraints.weighty = 0;
		
		jButtonTipos = new JButton ("Tipo");
		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		JFprincipal.getContentPane().add(jButtonTipos, new GridBagConstraints(6, 5, 2, 2, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		jButtonTipos.setText("Añadir  Categoría");
		jButtonTipos.setToolTipText("Añadir nueva categoría de producto");
		
		jButtonEliminar = new JButton ("Tipo");
		constraints.gridx = 1;
		constraints.gridy = 3;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		JFprincipal.getContentPane().add(jButtonEliminar, new GridBagConstraints(6, 15, 2, 2, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		jButtonEliminar.setText("Eliminar Categoría");
		jButtonEliminar.setToolTipText("Eliminar la categoría seleccionado");
		jButtonEliminar.setEnabled(false);

		{
			jTextFieldNombre = new JTextField();
			JFprincipal.getContentPane().add(jTextFieldNombre, new GridBagConstraints(1, 3, 6, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		}
		/*{
			jComboBoxTiposModel = new DefaultComboBoxModel();
			jComboBoxTipos = new JComboBox();
			JFprincipal.getContentPane().add(jComboBoxTipos, new GridBagConstraints(1, 5, 6, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
			jComboBoxTipos.setModel(jComboBoxTiposModel);
			jComboBoxTipos.setToolTipText("Tipo de producto");
		}
		{
			jLabel2 = new JLabel();
			JFprincipal.getContentPane().add(jLabel2, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			jLabel2.setText("Tipo de Producto");
		}
		*/
		{
			jLabel1 = new JLabel();
			JFprincipal.getContentPane().add(jLabel1, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			jLabel1.setText("Nombre de la Categoría");
		}
		{
			jLabel3 = new JLabel();
			JFprincipal.getContentPane().add(jLabel3, new GridBagConstraints(1, 8, 6, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			jLabel3.setText("Lista de Categorías");
		}
		{
			jListTiposModel = new DefaultListModel();
			jListTipos = new JList();
			JFprincipal.getContentPane().add(jListTipos, new GridBagConstraints(1, 9, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			jListTipos.setModel(jListTiposModel);
			jListTipos.setBorder(new LineBorder(new java.awt.Color(0,0,0), 0, false));
			jListTipos.setToolTipText("Lista de Categorías");
			jListTipos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			jListTipos.setMaximumSize(new java.awt.Dimension(0, 0));
			jListScroller = new JScrollPane(jListTipos);
			JFprincipal.getContentPane().add(jListScroller, new GridBagConstraints(1, 9, 6, 5, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
			jListScroller.setPreferredSize(new Dimension(250, 472));
			jListScroller.setBorder(new LineBorder(new java.awt.Color(0,0,0), 3, true));
		}

		//Iniciador de eventos
		initEventos();
		
//		MUESTRO EL FRAME CENTRADO
		JFprincipal.pack();
		JFprincipal.setSize(533, 509);
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
		JFprincipal.setTitle("Gestión de Tipos (categorías) de Productos");
		
		try {
			//cargarComboTipos();
			cargarListaTipos();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private void initEventos(){
		
		//SelectedItem on jListTipos
		jListTipos.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent evt) {
				jListTiposValueChange(evt);
			}
		});
		
		//Click on jButtonEliminar
		jButtonEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButtonEliminarActionPerformed(evt);
			}
		});
		
		//Click on jButtonTipos
		jButtonTipos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButtonTiposActionPerformed(evt);
			}
		});
	}
	
	
	private void cargarListaTipos() throws SQLException, DalException{
		ResultSet rs;
		
		jListTiposModel = new DefaultListModel();
		
		try {
			rs=(ResultSet) getFacade().executeQuery("SELECT * FROM Tipo ORDER BY tipo");
		} catch (SQLException e) {
			throw new DalException(e);
		} //finally {
			//getFacade().endCurrentOperation();
		//}
		
		while (rs.next()){
			jListTiposModel.addElement(rs.getString("tipo"));
			jListTipos.setModel(jListTiposModel);
		}		
		jListTipos.setModel(jListTiposModel);
	}
	
	private void jButtonTiposActionPerformed(ActionEvent evt){
		if (jTextFieldNombre.getText().equals("")) {
			JOptionPane.showMessageDialog(Principal.msgbox,
				    "Debe introducir un NOMBRE para el nuevo tipo de producto",
				    "Campo Incompleto",
				    JOptionPane.WARNING_MESSAGE);
		} else if (this.jTextFieldNombre.getText() != "" && t.getTipoID() == -1){
			t.setTipo(this.jTextFieldNombre.getText());
			try {
				tDal.insert(t);
				cargarListaTipos();
				jTextFieldNombre.setText("");
				System.out.println("Tipo añadido: " + t.getTipo());
			} catch (DalException e) {
				e.printStackTrace();
				System.out.println("ERROR al añadir el tipo: " + t.getTipo());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("ERROR al añadir el tipo: " + t.getTipo());
			}	
		}
	}
	
	private void jButtonEliminarActionPerformed(ActionEvent evt) {

		try {
			tDal.delete(tEliminar);
			cargarListaTipos();
		} catch (DalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void jListTiposValueChange(ListSelectionEvent e) {
		tEliminar = new Tipo();
		
		if (e.getValueIsAdjusting() == false) {
	        if (jListTipos.getSelectedIndex() == -1) {
	        //No selection, deshabilito botón "Eliminar"
	            jButtonEliminar.setEnabled(false);

	        } else {
	        //Selection, Habilito botón "Eliminar" y creo el producto con nombre de la lista
	        	jButtonEliminar.setEnabled(true);
	        	tEliminar.setTipo(jListTipos.getSelectedValue().toString());
	        }
	    }
	}

}


