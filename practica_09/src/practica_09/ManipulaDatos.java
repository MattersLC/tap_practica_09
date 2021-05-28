package practica_09;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.BorderFactory;
//import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
//import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
//import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Orlando Lucero
 */
public class ManipulaDatos extends JFrame{
    
    //Elementos para leer los datos
    //****************
    private JTextField nombre;
    private JSpinner fechaNac;
    private JSpinner estatura;
    private ButtonGroup gSexo;
    private JRadioButton hombre;
    private JRadioButton mujer;
    private JTextField peso;
    private JTextField cintura;
    private JTextField cadera;
    private JLabel descripcionActividad; // Describe la actividad
    private JLabel imagenActividad; // Describe la actividad
    private final String ACTIVIDADES[] ={"Sedentario", "Ligeramente Activas", "Moderadamente Activas", "Muy Activas"};
    private JComboBox actividad;
    private int edad = 20;
    //****************
    // Atributos para control de las acciones
    //****************
    private JButton insertaPer; // Activa la accion para registrar una persona
    private JButton limpiar; // Activa borrrar valores de los campos
    private JButton eliminar; // Activa elimina registro persona
    private JButton agregaMed; // Toma datos de personas para agregar medidas
    private JButton insertaMed; // Inserta registro a la tabla mediciones
    private JButton limpiaMed; // Limpia los campos de mediciones
    private JButton eliminaMed; // Elimina registro de mediciones de la persona seleccionada
    //****************
    // Atributos que permiten acceder y visualizar los datos de la Base de Datos
    //****************
    private ManejoDatos manejoDatos = new ManejoDatos(); //
    //private ManejoDatos manejoDatos;
    private ModeloTablaPersona modeloTablaPersona = new ModeloTablaPersona();
    //private ModeloTablaPersona modeloTablaPersona;
    private JTable tablaPersona;
    //private ModeloTablaMedidas modeloTablaMedidas;    
    private ModeloTablaMedidas modeloTablaMedidas = new ModeloTablaMedidas();
    private JTable tablaMedidas;
    private int idPerSel; // ID de persona seleccionada
    private String nps;
    private JLabel nombrePerSel; // Indicacion de validacion de nombre
    //****************
    // Declaracion de los datos que permiten controlar la edicion y validacion de algunos datos
    //****************
    private final int MIN_EDAD=20; // Edad minima
    private final int MAX_EDAD=65; // Edad maima
    private final double MIN_ESTATURA=1.40; // Estatura minima
    private final double MAX_ESTATURA=1.95; // Estatura maxima
    private final String DA_NOMBRE = "Digita tu nombre"; // titulo
    private final String DA_PESO = "Tu peso"; // titulo
    private boolean validaNombre = false; // Indicacion de validacion de nombre
    //****************
    
    public ManipulaDatos() {
        Container base = getContentPane();
        base.setLayout(new BorderLayout());
        JTabbedPane panelPrincipal = new JTabbedPane();
        JPanel panelPersona = new JPanel();
        JPanel panelMedidas = new JPanel();
        base.add(panelPrincipal);

        JPanel datosC = new JPanel();
        JLabel titulo = new JLabel("Manipulación de datos de personas");
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setFont(new Font("Serif", Font.BOLD, 18));
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        datosC.add(formarCamposPersona(), BorderLayout.WEST);
        panelPersona.add(titulo, BorderLayout.NORTH);
        panelPersona.add(datosC, BorderLayout.CENTER);
        panelPersona.add(formarPanelResultados(), BorderLayout.SOUTH);
        
        //tablaPersona.repaint();
        
        JPanel pAux = new JPanel();
        pAux.add(formarCamposMediciones(), BorderLayout.WEST);
        pAux.add(crearSeleccionActividad(), BorderLayout.EAST);
        JPanel pTituloP2 = new JPanel();
        JLabel titulo2 = new JLabel("Manipulación de datos de mediciones para:");
        titulo2.setHorizontalAlignment(SwingConstants.CENTER);
        pTituloP2.add(titulo2, BorderLayout.NORTH);
        nombrePerSel = new JLabel("");
        pTituloP2.add(nombrePerSel, BorderLayout.CENTER);
        titulo2.setFont(new Font("Serif", Font.BOLD, 18));
        titulo2.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panelMedidas.add(pTituloP2, BorderLayout.NORTH);
        panelMedidas.add(pAux, BorderLayout.CENTER);
        panelMedidas.add(formarPanelMedidas(), BorderLayout.SOUTH);
        
        panelPrincipal.add(panelPersona);
        panelPrincipal.add(panelMedidas);
        panelPrincipal.setTitleAt(0, "Personas");
        panelPrincipal.setTitleAt(1, "Medidas");
        
    }
    
    private JPanel formarCamposPersona() {
        JPanel camposPanel = new JPanel();
        
        // Los títulos de los campos inician su nombre con t
        JLabel tNombre = new JLabel("Nombre:");
        JLabel tFechaNac = new JLabel("Fecha de nacimiento:");
        tFechaNac.setHorizontalAlignment(SwingConstants.RIGHT);
        JLabel tAnios = new JLabel(" Años: "+MIN_EDAD);
        tAnios.setHorizontalAlignment(SwingConstants.LEFT);
        
        //crear los titutlos de los otros campos de datos
        nombre = new JTextField(DA_NOMBRE, 20);
        JLabel tSexo = new JLabel("Sexo:");
        hombre = new JRadioButton("Hombre");
        mujer = new JRadioButton ("Mujer");
        gSexo = new ButtonGroup();
        gSexo.add(hombre);
        gSexo.add(mujer);
        hombre.setSelected(true);
        
        // Creación del campo para la fecha de nacimiento, se crean los límites que serán usados
        Calendar fechaa = Calendar.getInstance(); // Fecha actual
        Calendar fechai = Calendar.getInstance(); // Fecha inicial
        fechai.add(Calendar.YEAR, -MIN_EDAD); // Fijar la fecha mínima más reciente
        Calendar fechaf = Calendar.getInstance(); // Fecha final o más antigua
        fechaf.add(Calendar.YEAR, -MAX_EDAD); // Fijar la fecha máxima o más antigua
        
        Date dateI = fechai.getTime();
        Date dateF = fechaf.getTime();
        SpinnerDateModel dma = new SpinnerDateModel(dateI, dateF, dateI, Calendar.DAY_OF_MONTH);
        fechaNac = new JSpinner(dma);
        JSpinner.DateEditor fdma = new JSpinner.DateEditor(fechaNac, "yyyy/MM/dd");
        fechaNac.setEditor(fdma);
        
        GridBagLayout gridbag = new GridBagLayout();
        camposPanel.setLayout(gridbag);
        GridBagConstraints r = new GridBagConstraints();
        r.anchor = GridBagConstraints.WEST;
        r.fill = GridBagConstraints.HORIZONTAL;
        r.gridwidth = 1;
        r.insets = new Insets(0,5,10,0);
        camposPanel.add(tNombre,r);
        r.gridwidth = GridBagConstraints.REMAINDER;
        r.ipadx = 0;
        camposPanel.add(nombre, r);
        r.gridwidth = 1;
        r.fill = GridBagConstraints.NONE;
        camposPanel.add(tFechaNac, r);
        camposPanel.add(fechaNac, r);
        r.gridwidth = GridBagConstraints.REMAINDER;
        r.fill = GridBagConstraints.NONE;
        camposPanel.add(tAnios, r);
        r.gridwidth = 1;
        
        JPanel ps = new JPanel();
        ps.add(tSexo);
        ps.add(hombre);
        ps.add(mujer);
        
        camposPanel.add(ps, r);
        camposPanel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder("Proporciona los datos"),
                        BorderFactory.createEmptyBorder(5, 5, 10, 5)
                )
        );

        fechaNac.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                Calendar fecha = Calendar.getInstance(); // Usada como auxiliar
                fecha.setTime((Date)fechaNac.getValue()); // Se le asigna la fecha del
                edad = fechaa.get(Calendar.YEAR) - fecha.get(Calendar.YEAR);
                // Se le asigna el valor de la edad
                if(fecha.get(Calendar.MONTH)<fechaa.get(Calendar.MONTH)){
                    edad--;
                }else if(fecha.get(Calendar.MONTH)==fechaa.get(Calendar.MONTH)&&fecha.get(Calendar.DATE)<fechaa.get(Calendar.DATE)){
                    edad--;
                }else if(edad<MIN_EDAD){
                    fechaNac.repaint();
                }else
                    tAnios.setText(" Años: "+edad);
            }
        });
        
        AdmoAccion admoAccion = new AdmoAccion();
        AdmoFocus admoFocus = new AdmoFocus();
        AdmoEvenTeclado admoEvenTeclado = new AdmoEvenTeclado();
        AdmoMouseAdap admoMouseAdap = new AdmoMouseAdap();

        nombre.setActionCommand("nombre");
        nombre.addActionListener(admoAccion);
        nombre.addFocusListener(admoFocus);
        nombre.addKeyListener(admoEvenTeclado);
        nombre.addMouseListener(admoMouseAdap);
        
        return camposPanel;
    }
    
    public JPanel formarPanelResultados() {
        //manejoDatos = new ManejoDatos();
        //modeloTablaPersona = new ModeloTablaPersona();
        cargaDatosPersona();
        //tablaPersona = new JTable(modeloTablaPersona);
        tablaPersona.setRowSelectionAllowed(true);
        
        JPanel panelResultados = new JPanel();
        JPanel pBotones = new JPanel();
        AdmoAccion admoAccion = new AdmoAccion();
        panelResultados.setLayout(new BorderLayout());
        
        insertaPer = new JButton("Agregar registro");
        insertaPer.setActionCommand("bInsertar");
        insertaPer.addActionListener(admoAccion);
        limpiar = new JButton("Iniciar valores");
        limpiar.setActionCommand("bIniciar");
        limpiar.addActionListener(admoAccion);
        eliminar = new JButton("Eliminar reg. seleccionado");
        eliminar.setActionCommand("bEliminar");
        eliminar.addActionListener(admoAccion);
        agregaMed = new JButton("Agregar medidas");
        agregaMed.setActionCommand("bMedidas");
        agregaMed.addActionListener(admoAccion);
        
        pBotones.add(insertaPer);
        pBotones.add(limpiar);
        pBotones.add(eliminar);
        pBotones.add(agregaMed);
        
        panelResultados.add(pBotones, BorderLayout.NORTH);
        panelResultados.add(new JScrollPane(tablaPersona), BorderLayout.CENTER);
        
        return panelResultados;
    }
    
    public JPanel formarCamposMediciones() {
        JPanel camposMediciones = new JPanel();
        
        JLabel tEstatura = new JLabel("Estatura (Mts.): ");
        tEstatura.setHorizontalAlignment(SwingConstants.LEFT);
        JLabel tPeso = new JLabel("Peso (Kgs.): ");
        peso = new JTextField("Tu peso", 5);
        JLabel tCintura = new JLabel("Cintura (cms.): ");
        cintura = new JTextField(5);
        JLabel tCadera = new JLabel("Cadera (cms.): ");
        cadera = new JTextField(5);
        
        SpinnerNumberModel modEst = new SpinnerNumCiclico(MIN_ESTATURA, MAX_ESTATURA);
        estatura = new JSpinner(modEst);
        JSpinner.NumberEditor nmed = new JSpinner.NumberEditor(estatura, "#.##");
        estatura.setEditor(nmed);
        
        GridBagLayout gridbag = new GridBagLayout();
        camposMediciones.setLayout(gridbag);
        GridBagConstraints r = new GridBagConstraints();
        r.anchor = GridBagConstraints.WEST;
        r.fill = GridBagConstraints.HORIZONTAL;
        r.gridwidth = 1;
        r.insets = new Insets(0, 5, 10, 0);
        camposMediciones.add(tEstatura, r);
        r.gridwidth = GridBagConstraints.REMAINDER;
        camposMediciones.add(estatura, r);
        r.gridwidth = 1;
        camposMediciones.add(tPeso, r);
        r.gridwidth = GridBagConstraints.REMAINDER;
        camposMediciones.add(peso, r);
        r.gridwidth = 1;
        camposMediciones.add(tCintura, r);
        r.gridwidth = GridBagConstraints.REMAINDER;
        camposMediciones.add(cintura, r);
        r.gridwidth = 1;
        camposMediciones.add(tCadera, r);
        r.gridwidth = GridBagConstraints.REMAINDER;
        camposMediciones.add(cadera, r);
        
        camposMediciones.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder("Proporciona los datos"),
                        BorderFactory.createEmptyBorder(5, 5, 10, 5)
                )
        );
        
        AdmoAccion admoAccion = new AdmoAccion();
        AdmoFocus admoFocus = new AdmoFocus();
        AdmoEvenTeclado admoEvenTeclado = new AdmoEvenTeclado();
        AdmoMouseAdap admoMouseAdap = new AdmoMouseAdap();
        
        peso.setActionCommand("peso");
        peso.addActionListener(admoAccion);
        peso.addFocusListener(admoFocus);
        peso.addKeyListener(admoEvenTeclado);
        peso.addMouseListener(admoMouseAdap);
        
        return camposMediciones;
    }
    
    public ImageIcon imagenActividad(int a) {
        String ruta = "/imagenes/";
        String[] nombreImagen = {"act_fisica_nada.jpg", "act_fisica_poco.jpg", "act_fisica_medio.jpg", "act_fisica_mucho.jpg"};
        URL url = getClass().getResource(ruta+nombreImagen[a]);
        ImageIcon img = new ImageIcon(url);
        
        return img;
    }
    
    public String textoActividad(int a) {
        String[] mensaje = {"No haces nada de ejercicio", "Haces poco ejercicio", "Sigue así", "Haces mucho ejercicio"};

        return mensaje[a];
    }
    
    public JPanel crearSeleccionActividad() {
        JPanel seleccion = new JPanel();
        seleccion.setLayout(new BorderLayout());
        JPanel panelAct = new JPanel();
        panelAct.setLayout(new FlowLayout());
        JPanel descripActiv = new JPanel();
        descripActiv.setLayout(new BorderLayout());
        
        actividad = new JComboBox(ACTIVIDADES);
        actividad.addItemListener(new ItemListener(){
        //maneja evento JComboBox
            public void itemStateChanged(ItemEvent evento){
            //determina si está seleccionada la casilla de verificación
                if(evento.getStateChange()==ItemEvent.SELECTED) {
                    // Cambia el texto y la imágen por defecto a las indicadas según la opción seleccionada del JComboBox
                    imagenActividad.setIcon(imagenActividad(actividad.getSelectedIndex()));
                    descripcionActividad.setText(textoActividad(actividad.getSelectedIndex()));
                }
            }
        });
        panelAct.add(actividad);
        imagenActividad = new JLabel(imagenActividad(0));
        descripcionActividad = new JLabel(textoActividad(0), SwingConstants.CENTER);
        descripActiv.add(descripcionActividad, BorderLayout.NORTH);
        descripActiv.add(imagenActividad, BorderLayout.CENTER);        

        panelAct.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder("Selecciona la actividad"),
                        BorderFactory.createEmptyBorder(5, 5, 10, 5)
                )
        );
        descripActiv.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder(""),
                        BorderFactory.createEmptyBorder(5, 5, 10, 5)
                )
        );

        seleccion.add(panelAct, BorderLayout.NORTH);
        seleccion.add(descripActiv, BorderLayout.SOUTH);

        return seleccion;
    }
    
    public JPanel formarPanelMedidas() {
        //manejoDatos = new ManejoDatos();
        //modeloTablaMedidas = new ModeloTablaMedidas();
        cargaDatosGrlesMedidas();
        //tablaMedidas = new JTable(modeloTablaMedidas);
        
        JPanel panelMedidas = new JPanel();
        JPanel pBotones = new JPanel();
        AdmoAccion admoAccion = new AdmoAccion();
        panelMedidas.setLayout(new BorderLayout());
        
        insertaMed = new JButton("Agregar Registro");
        insertaMed.setActionCommand("bInsertarMed");
        insertaMed.addActionListener(admoAccion);
        limpiaMed = new JButton("Iniciar valores");
        limpiaMed.setActionCommand("bIniciarMed");
        limpiaMed.addActionListener(admoAccion);
        eliminaMed = new JButton("Eliminar reg. seleccionado");
        eliminaMed.setActionCommand("bEliminarMed");
        eliminaMed.addActionListener(admoAccion);
        
        pBotones.add(insertaMed);
        pBotones.add(limpiaMed);
        pBotones.add(eliminaMed);
        
        panelMedidas.add(pBotones, BorderLayout.NORTH);
        panelMedidas.add(new JScrollPane(tablaMedidas), BorderLayout.CENTER);
        
        return panelMedidas;
    }
    
    private class AdmoEvenTeclado extends KeyAdapter {
        @Override
        public void keyTyped(KeyEvent ke) {
            char k = ke.getKeyChar();
            JTextField productor = (JTextField) ke.getSource();
            if (productor==nombre) {
                if (nombre.getSelectedText()!=null) {
                    nombre.setText("");
                }
                if (!Character.isAlphabetic(k) && k!=KeyEvent.VK_SPACE) {
                    ke.consume();
                }
            } else if (productor==peso) {
                if (!Character.isDigit(k) && k!=KeyEvent.VK_SPACE && k!='.') {
                    ke.consume();
                }
            }
        }
    }

    private class AdmoFocus extends FocusAdapter {
        @Override
        public void focusGained(FocusEvent evt) {
            Object o = evt.getSource();
            if (o instanceof JTextField) {
                JTextField txt = (javax.swing.JTextField) o;
                txt.setSelectionStart(0);
                txt.setSelectionEnd(nombre.getText().length());
                // Seleccionar el texto txt usando setSelectionStart() y setSelectionEnd()
            }
        }
    }
    
    private class AdmoMouseAdap extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent me) {
            JTextField productor = (JTextField) me.getSource();
            if (productor==nombre) {
                if (nombre.getSelectedText()!=null) {
                    nombre.setText("");
                }
            } else if (productor==peso) {
                if (peso.getSelectedText()!=null) {
                    peso.setText("");
                }
            }
        }
    }
    
    private class AdmoAccion implements ActionListener {
        // Clase utilizada para administración eventos de acción
        @Override
        public void actionPerformed(ActionEvent ae) {
            //AbstractTableModel dm = (AbstractTableModel)tablaPersona.getModel();
            //tablaPersona.setModel(dm);
            //tablaMedidas.setModel(dm);
            
            switch(ae.getActionCommand()) {
                case "bInsertar":
                    if(validaNombre || nombre.getText()!="" && !nombre.getText().equals(DA_NOMBRE)) {
                        SimpleDateFormat ff = new SimpleDateFormat("YYYY-MM-dd");
                        String vSexo = "";
                        if (hombre.isSelected()) {
                            vSexo = "H";
                        } else if (mujer.isSelected()) {
                            vSexo = "M";
                        }
                        String datos = "INSERT INTO ROOT.PERSONA"
                                     + "(nombre, fechanac, sexo) values"
                                     + "('" + nombre.getText() + "', '" + ff.format(fechaNac.getValue()) + "', '" + vSexo + "')";
                        manejoDatos.actualizaDatos(datos);
                        cargaDatosPersona();
                        AbstractTableModel dm = (AbstractTableModel)tablaPersona.getModel();
                        tablaPersona.setModel(dm);
                        dm.fireTableDataChanged();
                    }
                    break;
                    
                case "bIniciar":
                    Calendar fechai = Calendar.getInstance(); // Fecha inicial
                    fechai.add(Calendar.YEAR, -MIN_EDAD); // Fijar la fecha mínima más reciente
                    Date DateI = fechai.getTime(); // Convertir la fecha mínima requerida a una variable de tipo Date
                    fechaNac.setValue(DateI); // Fijar el valor en el JSpinner
                    nombre.setText(DA_NOMBRE);
                    hombre.setSelected(true);
                    break;

                case "bEliminar":
                    String de = "";
                    int rs = tablaPersona.getSelectedRow();
                    System.out.println("rs: "+rs);
                    if (tablaPersona.getSelectedRow()>0) {
                        int idPersona = Integer.parseInt((String) tablaPersona.getValueAt(tablaPersona.getSelectedRow(), 0));
                        System.out.println("idPersona: "+idPersona);
                        String nombrePer = String.valueOf((String) tablaPersona.getValueAt(tablaPersona.getSelectedRow(), 1));
                        System.out.println("nombrePer: "+nombrePer);
                        int confirmado = JOptionPane.showConfirmDialog(null, "¿Deseas eliminar el registro de "+nombrePer+"?");
                        if (JOptionPane.OK_OPTION == confirmado) {
                            de = "DELETE FROM ROOT.PERSONA WHERE IDPERSONA ="+idPersona;
                            manejoDatos.actualizaDatos(de);
                            cargaDatosPersona();
                            AbstractTableModel dm = (AbstractTableModel)tablaPersona.getModel();
                            tablaPersona.setModel(dm);
                            dm.fireTableDataChanged();
                            tablaPersona.setRowSelectionAllowed(true);
                            System.out.println("segundo rs: "+rs);
                        } else {
                            tablaPersona.clearSelection();
                        }
                    }
                    break;
                    
                case "bMedidas":
                    if (tablaPersona.getSelectedRow() >= 0) {
                        idPerSel = Integer.parseInt((String) tablaPersona.getValueAt(tablaPersona.getSelectedRow(), 0));
                        String seleccionMedPer = "SELECT * FROM ROOT.MEDICIONES WHERE IDPERSONA = "+idPerSel;
                        modeloTablaMedidas.setDatos(manejoDatos.conexionConsultaMedicion(seleccionMedPer));
                        // Haz que se refresquen los valores de la tablaMedidas
                        //cargaDatosPersona();
                        cargaDatosGrlesMedidas();
                        AbstractTableModel dm = (AbstractTableModel)tablaMedidas.getModel();
                        tablaMedidas.setModel(dm);
                        dm.fireTableDataChanged();
                        nps = String.valueOf((String) tablaPersona.getValueAt(tablaPersona.getSelectedRow(), 1)); // Obten el valor del nombre de la persona del renglon seleccionado de la tablaPersona
                        System.out.println("nps: "+nps);
                        nombrePerSel.setText("---- "+nps+"----");
                    }
                    
                    break;
                case "bInsertarMed":
                    if (idPerSel > 0) {
                        Calendar fecha = Calendar.getInstance();
                        Date fechaD = fecha.getTime();
                        java.sql.Date date = new java.sql.Date(fechaD.getTime());
                        SimpleDateFormat ff = new SimpleDateFormat("YYYY-MM-dd");
                        
                        //int estaturaL = Integer.parseInt((String) estatura.getValue())*100;
                        //Long estaturaL = ((Double)estatura.getValue()).longValue()*100;
                        Double estaturaL = ((Double)estatura.getValue()).doubleValue()*100;
                        int estaturaI = (int) ((double) estaturaL);
                        //idPerSel = Integer.parseInt((String) tablaPersona.getValueAt(tablaPersona.getSelectedRow(), 0));
                        int idAct = actividad.getSelectedIndex()+1;
                        if (peso.getText()!="" && cintura.getText()!="" && cadera.getText()!="") {
                            Integer pesoE = Integer.parseInt(peso.getText());
                            double pesoEE = Double.parseDouble(peso.getText());
                            Integer cinturaE = Integer.parseInt(cintura.getText());
                            Integer caderaE = Integer.parseInt(cadera.getText());
                            System.out.println("Insercción de "+nps+": Fecha:"+date+" Estatura: "+estaturaI+" Peso: "+pesoEE+" Cintura: "+cinturaE+" Cadera: "+caderaE+" IDACT: "+idAct+" IDPERSEL: "+idPerSel);
                            String medidas = "INSERT INTO ROOT.MEDICIONES" 
                                           + "(fecha, estatura, peso, cintura, cadera, idtipoact, idpersona) "
                                           + "values('"+date+"', '"+estaturaI+"', '"+pesoEE+"', '"+cinturaE+"', '"+caderaE+"', '"+idAct+"', '"+idPerSel+"')";
                            manejoDatos.actualizaDatos(medidas);
                            cargaDatosMedidas(idPerSel);
                            AbstractTableModel dm = (AbstractTableModel)tablaMedidas.getModel();
                            tablaMedidas.setModel(dm);
                            dm.fireTableDataChanged();
                        }
                    }
                    break;
                    
                case "bIniciarMed":
                    estatura.setValue(MIN_ESTATURA);
                    estatura.repaint();
                    peso.setText(DA_PESO);
                    cintura.setText("");
                    cadera.setText("");
                    actividad.setSelectedIndex(0);
                    break;
                    
                case "bEliminarMed":
                    String eliminarMed = "";
                    int rsMedidas = tablaMedidas.getSelectedRow();
                    if (rsMedidas>0) {
                        //int idPersona = Integer.parseInt((String) tablaPersona.getValueAt(rsMedidas, 0));
                        int idMedidas = Integer.parseInt((String) tablaMedidas.getValueAt(rsMedidas, 0));
                        String nombrePer = String.valueOf((String) tablaMedidas.getValueAt(rsMedidas, 7));
                        int confirmado = JOptionPane.showConfirmDialog(null, "¿Deseas eliminar el registro de "+nombrePer+"?");
                        if (JOptionPane.OK_OPTION == confirmado) {
                            eliminarMed = "DELETE FROM ROOT.MEDIDAS WHERE IDMEDIDAS ="+idMedidas;
                            manejoDatos.actualizaDatos(eliminarMed);
                            cargaDatosGrlesMedidas();
                            AbstractTableModel dm = (AbstractTableModel)tablaMedidas.getModel();
                            tablaMedidas.setModel(dm);
                            dm.fireTableDataChanged();
                        } else {
                            tablaMedidas.clearSelection();
                        }
                    }
                    break;
                
                default:
                    JOptionPane.showMessageDialog(null, "Algo inesperado ha ocurrido");
                    break;
            }
        }
    }
    
    public class SpinnerNumCiclico extends SpinnerNumberModel{
        private Number vMin;
        private Number vMax;

        public SpinnerNumCiclico(Number min, Number max) {
            super();
            vMin = min;
            vMax = max;
            setValue(min);
            setStepSize(0.01);
        }

        @Override
        public Number getNextValue() {
            Number sv = (Number) super.getNextValue();

            if (sv.doubleValue()>vMax.doubleValue()) {
                return vMin;
            }

            return sv;
        }

        @Override
        public Number getPreviousValue() {
            Number va = (Number) super.getPreviousValue();

            if (va.doubleValue()>vMin.doubleValue()) {
                return va;
            }

            return vMax;
        }
    }
    
    public void cargaDatosPersona() {
        String consultaPersona = "SELECT * FROM ROOT.PERSONA";
        modeloTablaPersona.setDatos(manejoDatos.conexionConsultaPersona(consultaPersona));
        tablaPersona = new JTable(modeloTablaPersona);
        tablaPersona.setRowSelectionAllowed(true);
    }
    
    public void cargaDatosMedidas(int idPer) {
        String consultaMedidas = "SELECT * FROM ROOT.MEDICIONES WHERE idpersona="+idPer;
        modeloTablaMedidas.setDatos(manejoDatos.conexionConsultaMedicion(consultaMedidas));
    }
    
    public void cargaDatosGrlesMedidas() {
        String consultaMedidas = "SELECT * FROM ROOT.MEDICIONES";
        modeloTablaMedidas.setDatos(manejoDatos.conexionConsultaMedicion(consultaMedidas));
        tablaMedidas = new JTable(modeloTablaMedidas);
    }
}
