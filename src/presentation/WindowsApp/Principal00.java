package presentation.WindowsApp;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.LineBorder;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import presentation.PresentationApp;


public class Principal00 implements PresentationApp{
	
	//Frames Auxiliares
	static PresentationApp productos;
	static PresentationApp proveedores;
	static PresentationApp ubicacion;
	static PresentationApp tipos;
	static PresentationApp tiposDeGanado;
	static PresentationApp registros;
	static PresentationApp formulas;
	static PresentationApp aplicarFormulas;
	static PresentationApp buscarFormula;
	static PresentationApp buscarFormulasDeUnTipoPorFechas;
	static PresentationApp buscarProducto;
	static PresentationApp cantidadEnAlmacen;
	public static Frame msgbox;
	private JPanel jPanelFondo;
	JFrame JFprincipal;
	

	//jComponents
	private JPanel jPanel1;
	private JLabel jLabel1;
	private JLabel jLabelTitulo;
	private JButton jButtonProductos;
	private JButton jButtonProveedores;
	private JButton jButtonTipos;
	private JButton jButtonTiposDeGanado;
	private JButton jButtonRegistros;
	private JButton jButtonFormula;
	private JButton jButtonAplicarFormula;
	private JButton jButtonBuscarFormula;
	private JButton jButtonSalir;
	private JButton jButtonCantidadEnAlmacen;
	private JButton jButtonBuscarProductoPorFechas;
    // Log
    private static Logger logger;
    
	public Principal00() {
		super();
		
		logger = Logger.getLogger(this.getClass().getName());
		logger.setLevel(Level.INFO);
	    try {
		    PropertyConfigurator.configure("log4j_console.properties");
	    }catch (Exception e) {
			System.out.println("ERROR: " + e.getMessage() + " - " + e.getCause());
	    }
   
	    logger.setLevel(Level.INFO);
		initForm();
	}

	public void showMainMenu() {
		
		JFprincipal = new JFrame("Molino v2.0");
		GridBagConstraints constraints = new GridBagConstraints();
		
		//Indico el layout de JFprincipal
		GridBagLayout JFprincipalLayout = new GridBagLayout();
		JFprincipal.getContentPane().setLayout(JFprincipalLayout);
		JFprincipalLayout.rowHeights = new int[] {20, 232, 10, 20, 22, 8, 22, 8, 22, 8, 22, 8, 22, 8, 22, 8, 22, 8, 22, 8, 22, 8, 22, 20};
		JFprincipalLayout.rowWeights = new double[] {0.1, 0.0, 0.0, 0.1, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.1};
		JFprincipalLayout.columnWeights = new double[] {0.1, 0.0, 0.0, 0.0, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1};
		JFprincipalLayout.columnWidths = new int[] {20, 110, 29, 23, 7, 20, 20, 20, 20, 7};

		//Acción de cerrar el formulario
		JFprincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Añado los componentes a JFprincipal
		jLabelTitulo = new JLabel("Molino v2.0");
		constraints.gridx = 0; // El área de texto empieza en la columna cero.
		constraints.gridy = 0; // El área de texto empieza en la fila uno.
		constraints.gridwidth = 4; // El área de texto ocupa 4 columnas.
		constraints.gridheight = 1; // El área de texto ocupa 1 filas.
		constraints.weighty = 1.0; // La fila 1 debe estirarse, le ponemos 1.0
		JFprincipal.getContentPane().add(jLabelTitulo, new GridBagConstraints(5, 1, 3, 1, 0.0, 1.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(1, 0, 0, 0), 0, 0));
		jLabelTitulo.setBackground(new java.awt.Color(196,242,241));
		jLabelTitulo.setFont(new java.awt.Font("Arial Black",1,26));
		jLabelTitulo.setText("Molino v2.0");
		jLabelTitulo.setForeground(new java.awt.Color(0,0,160));
		jLabelTitulo.setBorder(new LineBorder(new java.awt.Color(0,0,0), 3, false));
		constraints.weighty = 0;
		
		jButtonProductos = new JButton ("Productos");
		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.gridwidth = 2;
		constraints.gridheight = 2;
		JFprincipal.getContentPane().add(jButtonProductos, new GridBagConstraints(4, 4, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		jButtonProductos.setToolTipText("Gestión de productos");

		jButtonProveedores = new JButton ("Proveedores");
		constraints.gridx = 1;
		constraints.gridy = 3;
		constraints.gridwidth = 2;
		constraints.gridheight = 2;
		JFprincipal.getContentPane().add(jButtonProveedores, new GridBagConstraints(4, 6, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		jButtonProveedores.setToolTipText("Getión de proveedores");

		jButtonTipos = new JButton ("Categorías de Productos");
		constraints.gridx = 1;
		constraints.gridy = 5;
		constraints.gridwidth = 2;
		constraints.gridheight = 2;
		JFprincipal.getContentPane().add(jButtonTipos, new GridBagConstraints(4, 8, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		jButtonTipos.setToolTipText("Gestión de tipos de productos");

		jButtonRegistros = new JButton ("Registros de Entrada");
		constraints.gridx =1;
		constraints.gridy = 7;
		constraints.gridwidth = 2;
		constraints.gridheight =2;
		JFprincipal.getContentPane().add(jButtonRegistros, new GridBagConstraints(4, 12, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		jButtonRegistros.setToolTipText("Formulario de entrada de nuevos registros");

		jPanel1 = new JPanel();
		JFprincipal.getContentPane().add(jPanel1, new GridBagConstraints(1, 4, 2, 19, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		jPanel1.setBackground(new java.awt.Color(0,0,160));
		jPanel1.setBorder(new LineBorder(new java.awt.Color(0,0,0), 3, true));
		
		
		jLabel1 = new JLabel();
		JFprincipal.getContentPane().add(jLabel1, new GridBagConstraints(1, 1, 2, 2, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		jLabel1.setBorder(new LineBorder(new java.awt.Color(0,0,0), 3, false));
		jLabel1.setIcon(new ImageIcon(getClass().getClassLoader().getResource("Imágenes/icono_silos_aceite.png")));
		jLabel1.setForeground(new java.awt.Color(255,255,255));
		jLabel1.setBackground(new java.awt.Color(255,255,255));
		jLabel1.setOpaque(true);
		
		jButtonFormula = new JButton();
		JFprincipal.getContentPane().add(jButtonFormula, new GridBagConstraints(4, 14, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		jButtonFormula.setText("Gestión de Fórmulas");
		jButtonFormula.setToolTipText("Creación y gestión de fórmulas");
		{
			jButtonAplicarFormula = new JButton();
			JFprincipal.getContentPane().add(jButtonAplicarFormula, new GridBagConstraints(4, 16, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
			jButtonAplicarFormula.setText("Aplicar Fórmulas");
			jButtonAplicarFormula.setToolTipText("Aplicación de fórmulas a partir de las creadas");
		}
		{
			jButtonBuscarFormula = new JButton();
			JFprincipal.getContentPane().add(jButtonBuscarFormula, new GridBagConstraints(4, 18, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
			jButtonBuscarFormula.setText("Buscar Fórmulas Creadas");
			jButtonBuscarFormula.setToolTipText("Formulario para buscar fórmulas entre todas las creadas");
		}
		{
			jButtonSalir = new JButton();
			JFprincipal.getContentPane().add(jButtonSalir, new GridBagConstraints(7, 22, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
			jButtonSalir.setText("Salir");
			jButtonSalir.setToolTipText("Salir de la aplicación");
		}
		{
			jButtonBuscarProductoPorFechas = new JButton();
			JFprincipal.getContentPane().add(jButtonBuscarProductoPorFechas, new GridBagConstraints(4, 20, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
			jButtonBuscarProductoPorFechas.setText("Buscar Producto por Fechas");
			jButtonBuscarProductoPorFechas.setToolTipText("Formulario para buscar entradas totales de productos por fechas");
		}
		{
			jButtonCantidadEnAlmacen = new JButton();
			JFprincipal.getContentPane().add(jButtonCantidadEnAlmacen, new GridBagConstraints(4, 22, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
			jButtonCantidadEnAlmacen.setText("Cantidad en Almacén");
			jButtonCantidadEnAlmacen.setToolTipText("Cantidad total de KG en el almacén por productos");
		}
		{
			jPanelFondo = new JPanel();
			JFprincipal.getContentPane().add(jPanelFondo, new GridBagConstraints(4, 1, 5, 2, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
			jPanelFondo.setBackground(new java.awt.Color(192,192,192));
		}
		{
			jButtonTiposDeGanado = new JButton();
			JFprincipal.getContentPane().add(jButtonTiposDeGanado, new GridBagConstraints(4, 10, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
			jButtonTiposDeGanado.setText("Categorías de Ganado");
			jButtonTiposDeGanado.setToolTipText("Tipos de ganado");
		}

		//Iniciador de eventos
		initEventos();
		
		//MUESTRO EL JFrame PRINCIPAL CENTRADO
		JFprincipal.pack();
		JFprincipal.setSize(501, 550);
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
		JFprincipal.setPreferredSize(new java.awt.Dimension(622, 613));
		JFprincipal.getContentPane().setBackground(new java.awt.Color(236,233,213));
		JFprincipal.setSize(622, 613);

	}
	
	
	//////////////////////////////////////////////////////////////////
	//
	//	INICIAR FORMULARIOS
	//
	//////////////////////////////////////////////////////////////////
		
	private void initForm(){
		try {
			productos = new IntrProductos();
			proveedores = new IntrProveedores();
			ubicacion = new IntrUbicacion();
			tipos = new IntrTipo();
			tiposDeGanado = new IntrTipoGanado();
			registros = new IntrRegistrosDeRecepcionTable();
			formulas = new GestionDeFormulas();
			aplicarFormulas = new AplicarFormula();
			buscarFormula = new BuscarFormulasCreadaPorFechas();
			buscarProducto = new BuscarEntradasProductoPorFechas();
			cantidadEnAlmacen = new CantidadEnAlmacen();
		} catch (Exception e) {
			logger.error("Iniciando formularios: " + e.getMessage()); 
		}
		
	}
	
	
	//////////////////////////////////////////////////////////////////
	//
	//	INICIAR EVENTOS
	//
	//////////////////////////////////////////////////////////////////
	
	private void initEventos(){
		//Productos
		jButtonProductos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButtonProductosActionPerformed(evt);
			}
		});
		
		//Proveedores
		jButtonProveedores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButtonProveedoresActionPerformed(evt);
			}
		});
		
		//Tipos de Productos
		jButtonTipos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButtonTiposActionPerformed(evt);
			}
		});
		
		//Tipos de Ganados
		jButtonTiposDeGanado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButtonTiposDeGanadoActionPerformed(evt);
			}
		});
		
		//Registros de Recepción
		jButtonRegistros.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButtonRegistrosActionPerformed(evt);
			}
		});
		
		//Fórmulas
		jButtonFormula.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButtonFormulasActionPerformed(evt);
			}
		});
		
		//Aplicar Fórmulas
		jButtonAplicarFormula.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButtonAplicarFormulasActionPerformed(evt);
			}
		});
		
		//Buscar Fórmulas
		jButtonBuscarFormula.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButtonBuscarFormulasActionPerformed(evt);
			}
		});
		
		//Buscar productos por fecha de entrada
		jButtonBuscarProductoPorFechas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButtonBuscarProductoPorFechasActionPerformed(evt);
			}
		});
		
		//Cantidad de productos en el almacén
		jButtonCantidadEnAlmacen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButtonCantidadEnAlmacenActionPerformed(evt);
			}
		});
		
		//Salir de la aplicación
		jButtonSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JFprincipal.dispose();
			}
		});
		
		}
		
	//////////////////////////////////////////////////////////////////
	//
	//	EVENTOS
	//
	//////////////////////////////////////////////////////////////////
	
	private void jButtonProductosActionPerformed(ActionEvent evt) {
		//System.out.println("jButtonProductos.actionPerformed, event=" + evt);
		productos.showMainMenu();		
	}
	
	private void jButtonProveedoresActionPerformed(ActionEvent evt) {
		//System.out.println("jButtonProductos.actionPerformed, event=" + evt);
		proveedores.showMainMenu();
		
	}

	private void jButtonTiposActionPerformed(ActionEvent evt) {
		//System.out.println("jButtonProductos.actionPerformed, event=" + evt);
		tipos.showMainMenu();
		
	}
	
	private void jButtonTiposDeGanadoActionPerformed(ActionEvent evt) {
		//System.out.println("jButton1.actionPerformed, event=" + evt);
		tiposDeGanado.showMainMenu();
		
	}
	
	private void jButtonRegistrosActionPerformed(ActionEvent evt) {
		//System.out.println("jButtonProductos.actionPerformed, event=" + evt);
		registros.showMainMenu();
		
	}
	
	private void jButtonFormulasActionPerformed(ActionEvent evt) {
		//System.out.println("jButtonProductos.actionPerformed, event=" + evt);
		formulas.showMainMenu();
		
	}
	
	private void jButtonAplicarFormulasActionPerformed(ActionEvent evt) {
		//System.out.println("jButtonProductos.actionPerformed, event=" + evt);
		aplicarFormulas.showMainMenu();
		
	}
	
	private void jButtonBuscarFormulasActionPerformed(ActionEvent evt) {
		//System.out.println("jButtonProductos.actionPerformed, event=" + evt);
		buscarFormula.showMainMenu();
		
	}
	
	private void jButtonBuscarProductoPorFechasActionPerformed(ActionEvent evt) {
		//System.out.println("jButtonProductos.actionPerformed, event=" + evt);
		buscarProducto.showMainMenu();
		
	}

	private void jButtonCantidadEnAlmacenActionPerformed(ActionEvent evt) {
		//System.out.println("jButton2.actionPerformed, event=" + evt);
		cantidadEnAlmacen.showMainMenu();
	}
}
